package com.obu.tech.poba.personnel_info.research;

import com.obu.tech.poba.utils.exceptions.InvalidInputException;
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

@Controller
@RequestMapping("/personnel-info/researchers")
public class ResearcherController {

    static final String VIEW_RESEARCHERS = "personnel-info/researcher";
    static final String VIEW_RESEARCHER_FORM = "personnel-info/researcher-form";
    static final String VIEW_RESEARCHER_DETAIL = "personnel-info/researcher-view";

    @Autowired
    private ResearcherService researcherService;

    @GetMapping
    public ModelAndView showListView() {
        return new ModelAndView(VIEW_RESEARCHERS);
    }

    @GetMapping(value = "/add")
    public ModelAndView showAddView() {
        ModelAndView view = new ModelAndView(VIEW_RESEARCHER_FORM);
        view.addObject("viewName", "เพิ่มข้อมูล");
        view.addObject("researcher", new Researcher());
        return view;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showDetailView(@PathVariable("id") String id) {
        Researcher researcher = researcherService.findById(id);
        ModelAndView view = new ModelAndView(VIEW_RESEARCHER_DETAIL);
        view.addObject("researcher", researcher);
        return view;
    }

    @GetMapping(value = "/{id}/edit")
    public ModelAndView showEditView(@PathVariable("id") String id) {
        Researcher researcher = researcherService.findById(id);

        // why don't we just make 2 separate fields on UI? (- -")
        if (StringUtils.isNotBlank(researcher.getSurname())) {
            researcher.setName(researcher.getName() + " " + researcher.getSurname());
        }

        ModelAndView view = new ModelAndView(VIEW_RESEARCHER_FORM);
        view.addObject("viewName", "แก้ไขข้อมูล");
        view.addObject("researcher", researcher);
        return view;
    }

    @ResponseBody
    @GetMapping("/search")
    public ResponseEntity<List<Researcher>> search(@ModelAttribute Researcher researcher) {
        List<Researcher> researchers = researcherService.search(researcher);
        return ResponseEntity.ok().body(researchers);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView add(@ModelAttribute("researcher") @Valid Researcher inputData,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView view = new ModelAndView(VIEW_RESEARCHER_FORM);
            view.addObject("viewName", "เพิ่มข้อมูล");
            throw new InvalidInputException(view, inputData, bindingResult);
        }
        try {
            return new ModelAndView(VIEW_RESEARCHER_DETAIL)
                    .addObject("researcher", researcherService.save(inputData))
                    .addObject("responseMessage", "บันทึกสำเร็จ")
                    .addObject("success", true); // success green, else red
        } catch (Exception ex) {
            return new ModelAndView(VIEW_RESEARCHERS)
                    .addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView edit(@PathVariable("id") String id,
                             @ModelAttribute("researcher") @Valid Researcher updateData,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView view = new ModelAndView(VIEW_RESEARCHER_FORM);
            view.addObject("viewName", "แก้ไขข้อมูล");
            throw new InvalidInputException(view, updateData, bindingResult);
        }
        ModelAndView view = new ModelAndView(VIEW_RESEARCHER_DETAIL);
        try {
            view.addObject("researcher", researcherService.update(id, updateData));
            view.addObject("responseMessage", "บันทึกสำเร็จ");
            view.addObject("success", true);
        } catch (Exception ex) {
            view.addObject("researcher", researcherService.findById(id));
            view.addObject("responseMessage", "ไม่สำเร็จ");
        }
        return view;
    }
}
