package com.obu.tech.poba.personnel_info.research;

import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import com.obu.tech.poba.utils.exceptions.UploadException;
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
@RequestMapping("/personnel-info/researchers")
public class ResearcherController {

    static final String FRAGMENT_RESEARCHERS = "personnel-info/researcher :: researchers";
    static final String FRAGMENT_RESEARCHER_FORM = "personnel-info/researcher-form :: researcher-form";
    static final String FRAGMENT_RESEARCHER_VIEW = "personnel-info/researcher-view :: researcher-view";

    @Autowired
    private ResearcherService researcherService;

    @GetMapping
    public ModelAndView showListView() {
        return new ModelAndView(FRAGMENT_RESEARCHERS);
    }

    @GetMapping(value = "/add")
    public ModelAndView showAddView() {
        return formAdd(new Researcher());
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showDetailView(@PathVariable("id") String id) {
        return view(researcherService.findById(id));
    }

    @GetMapping(value = "/{id}/edit")
    public ModelAndView showEditView(@PathVariable("id") String id) {
        Researcher researcher = researcherService.findById(id);

        if (StringUtils.isNotBlank(researcher.getSurname())) {
            researcher.setName(researcher.getName() + " " + researcher.getSurname());
        }

        return formUpdate(researcher);
    }

    @ResponseBody
    @GetMapping("/search")
    public ResponseEntity<List<Researcher>> search(@ModelAttribute Researcher researcher) {
        List<Researcher> researchers = researcherService.search(researcher);
        return ResponseEntity.ok().body(researchers);
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView add(@ModelAttribute("researcher") @Valid Researcher inputData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(inputData), bindingResult);
        }
        try {
            return viewSuccess(researcherService.add(inputData));

        } catch (UploadException ex) {
            return formAdd(inputData).addObject("responseMessage", ex.getMessage());

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            return new ModelAndView(FRAGMENT_RESEARCHERS).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView update(@PathVariable("id") String id,
                               @ModelAttribute("researcher") @Valid Researcher updateData,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formUpdate(updateData), bindingResult);
        }
        try {
            return viewSuccess(researcherService.update(id, updateData));

        } catch (UploadException ex) { // TODO: Handle UploadException
            return viewError(researcherService.findById(id), ex.getMessage());

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            return viewError(researcherService.findById(id), "ไม่สำเร็จ");
        }
    }

    private ModelAndView viewSuccess(Researcher data) {
        return view(data)
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView viewError(Researcher data, String message) {
        return view(data).addObject("responseMessage", message);
    }

    private ModelAndView view(Researcher data) {
        return new ModelAndView(FRAGMENT_RESEARCHER_VIEW).addObject("researcher", data);
    }

    private ModelAndView formAdd(Researcher data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView formUpdate(Researcher data) {
        return form(data).addObject("viewName", "แก้ไขข้อมูล");
    }

    private ModelAndView form(Researcher data) {
        return new ModelAndView(FRAGMENT_RESEARCHER_FORM).addObject("researcher", data);
    }
}
