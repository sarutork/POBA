package com.obu.tech.poba.project_polsci;

import com.obu.tech.poba.utils.NameConverterUtils;
import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/polsci")
public class ProjectPolSciController {
    static final String FRAGMENT_POLSCI = "project_polsci/polsci :: polsci";
    static final String FRAGMENT_POLSCI_FORM = "project_polsci/polsci-form :: polsci-form";

    @Autowired
    private ProjectPolSciService polSciService;

    @Autowired
    private NameConverterUtils nameConverter;

    @GetMapping
    public ModelAndView showListView() {return new ModelAndView(FRAGMENT_POLSCI);}

    @GetMapping("/search")
    public ResponseEntity<List<ProjectPolSci>> search(@ModelAttribute ProjectPolSci polSci) {
        return ResponseEntity.ok().body(polSciService.findBySearchCriteria(polSci));
    }

    @GetMapping("/add")
    public ModelAndView add() {return formAdd(new ProjectPolSci());}

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("polsci") @Valid ProjectPolSci polsci, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(polsci), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(polsci.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(polsci.getName());
                polsci.setName(fullName[0]);
                polsci.setSurname(fullName[1]);
            }

            ProjectPolSci polSciRest = polSciService.save(polsci);
            polSciRest.setName(polSciRest.getName()+" "+polSciRest.getSurname());

            return viewSuccess(polSciRest);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_POLSCI_FORM).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id){
        ProjectPolSci polSci = polSciService.findById(id);
        polSci.setName(polSci.getName()+" "+ polSci.getSurname());
        return view(polSci);
    }

    private ModelAndView formAdd(ProjectPolSci data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(ProjectPolSci data) {
        return new ModelAndView(FRAGMENT_POLSCI_FORM).addObject("polsci", data);
    }

    private ModelAndView viewSuccess(ProjectPolSci data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(ProjectPolSci data) {
        return new ModelAndView(FRAGMENT_POLSCI_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("polsci", data);
    }
}
