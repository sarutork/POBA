package com.obu.tech.poba.personnel_info.research;

import com.obu.tech.poba.teaching_info.Teaching;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
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
        ModelAndView view = new ModelAndView(VIEW_RESEARCHER_FORM);
        view.addObject("viewName", "แก้ไขข้อมูล");
        view.addObject("researcher", researcher);
        return view;
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Researcher>> search(@ModelAttribute Researcher researcher) {
        System.out.println(researcher.getWorkEndDate());
        System.out.println(researcher.getWorkStartDate());
        List<Researcher> researchers = researcherService.search(researcher.getName(), researcher.getWorkStartDate(), researcher.getWorkEndDate());
        return ResponseEntity.ok().body(researchers);
    }

    @PostMapping(value = "/add")
    public ModelAndView add(Researcher inputData, BindingResult bindingResult) {
        ModelAndView view = new ModelAndView(VIEW_RESEARCHERS);

        if (bindingResult.hasErrors()) {
            //TODO: Handle error
            System.out.println("Error: "+bindingResult.getAllErrors());
            return view;
        }

        String[] fullNameArr = inputData.getName().split(" ");
        inputData.setName(fullNameArr[0].trim());
        String surname ="";
        for(int i=1 ;i< fullNameArr.length; i++) {
            surname += fullNameArr[i] + " ";
        }
        inputData.setSurname(surname.trim());

        Researcher researcher = researcherService.save(inputData);
        view.addObject("responseMessage", "บันทึกสำเร็จ");
        view.addObject("researcher", researcher);
        return view;
    }

    @PutMapping(value = "/{id}")
    public ModelAndView edit(@PathVariable("id") String id, @RequestBody Researcher updateData) {
        Researcher researcher = researcherService.findById(id);
        ModelAndView view = new ModelAndView(VIEW_RESEARCHER_DETAIL);
        view.addObject("researcher", researcher);
        return view;
    }
}
