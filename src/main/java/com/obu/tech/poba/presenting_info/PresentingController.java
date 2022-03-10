package com.obu.tech.poba.presenting_info;

import com.obu.tech.poba.teaching_info.Teaching;
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
@RequestMapping("/presenting")
public class PresentingController {
    static final String FRAGMENT_PRESENTING_INFO = "presenting-info/presenting :: presenting";
    static final String FRAGMENT_PRESENTING_FORM = "presenting-info/presenting-form :: presenting-form";

    @Autowired NameConverterUtils nameConverter;

    @Autowired PresentingService presentingService;

    @GetMapping
    public ModelAndView showListView() {return new ModelAndView(FRAGMENT_PRESENTING_INFO);}

    @GetMapping("/search")
    public ResponseEntity<List<Presenting>> search(@ModelAttribute Presenting presenting) {
        return ResponseEntity.ok().body(presentingService.findBySearchCriteria(presenting));
    }

    @GetMapping("/add")
    public ModelAndView add() {return formAdd(new Presenting());}

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("presenting") @Valid Presenting presenting, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(presenting), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(presenting.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(presenting.getName());
                presenting.setName(fullName[0]);
                presenting.setSurname(fullName[1]);
            }

            Presenting presentingRes = presentingService.save(presenting);
            presentingRes.setName(presentingRes.getName()+" "+presentingRes.getSurname());

            return viewSuccess(presentingRes);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_PRESENTING_INFO).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showPresentingInfo(@PathVariable String id){
        Presenting presenting = presentingService.findById(id);
        presenting.setName(presenting.getName()+" "+presenting.getSurname());
        return view(presenting);
    }

    private ModelAndView formAdd(Presenting data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Presenting data) {
        return new ModelAndView(FRAGMENT_PRESENTING_FORM).addObject("presenting", data);
    }

    private ModelAndView viewSuccess(Presenting data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(Presenting data) {
        return new ModelAndView(FRAGMENT_PRESENTING_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("presenting", data);
    }

}
