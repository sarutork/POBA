package com.obu.tech.poba.personnel_info.education;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import java.util.function.Supplier;

@Controller
@RequestMapping("/personnel-info/education")
public class StudyInfoController {

    static final String VIEW_STUDY_INFO = "personnel-info/education";
    static final String VIEW_STUDY_INFO_FORM = "personnel-info/education-form";
    static final String VIEW_STUDY_INFO_DETAIL = "personnel-info/education-view";

    @GetMapping
    public ModelAndView overview() {
        ModelAndView view = new ModelAndView(VIEW_STUDY_INFO);
        view.addObject("user", "Ekamon");
        return view;
    }

    @GetMapping(value = "/add")
    public ModelAndView showAddView() {
        ModelAndView view = new ModelAndView(VIEW_STUDY_INFO_FORM);
        view.addObject("user", "Ekamon");
        view.addObject("viewName", "เพิ่มข้อมูล");
        return view;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showStudyInfo(@PathVariable String id) {
        System.out.println("View study info, id: " + id);
        ModelAndView view = new ModelAndView(VIEW_STUDY_INFO_DETAIL);
        view.addObject("user", "Ekamon");
//        view.addObject("studyInfo", studyInfoRepository.findById(id).orElseThrow(notFoundException()));
        return view;
    }

    @GetMapping(value = "/{id}/edit")
    public ModelAndView showEditView(@PathVariable String id) {
        System.out.println("Edit study info, id: " + id);
        ModelAndView view = new ModelAndView(VIEW_STUDY_INFO_FORM);
        view.addObject("user", "Ekamon");
        view.addObject("viewName", "แก้ไขข้อมูล");
//        view.addObject("studyInfo", studyInfoRepository.findById(id).orElseThrow(notFoundException()));
        return view;
    }

    private Supplier<HttpClientErrorException> notFoundException() {
        return () -> new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }
}
