package com.obu.tech.poba.personnel_info.research;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public String showAddView() {
        return VIEW_RESEARCHER_FORM;
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
        view.addObject("researcher", researcher);
        return view;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<List<Researcher>> search(@RequestParam(required = false) String name,
                                                   @RequestParam(required = false) LocalDate startDate,
                                                   @RequestParam(required = false) LocalDate endDate) {
        List<Researcher> researchers = researcherService.search(name, startDate, endDate);
        return researchers.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(researchers);
    }

    @PostMapping(value = "/add")
    public ModelAndView add(@RequestBody Researcher inputData) {
        Researcher researcher = researcherService.save(inputData);
        ModelAndView view = new ModelAndView(VIEW_RESEARCHERS);
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
