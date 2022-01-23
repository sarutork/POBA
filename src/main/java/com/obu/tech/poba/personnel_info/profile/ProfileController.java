package com.obu.tech.poba.personnel_info.profile;

import com.obu.tech.poba.teaching_info.Teaching;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.util.List;

@Controller
@RequestMapping("/personnel-info/profile")
public class ProfileController {

    static final String VIEW_HISTORY_INFO = "personnel-info/profile";
    static final String VIEW_HISTORY_INFO_FORM = "personnel-info/profile-form";
    static final String VIEW_HISTORY_INFO_DETAIL = "personnel-info/profile-view";

    @Autowired
    ProfileService profileService;

    @GetMapping
    public ModelAndView showListView() {
        ModelAndView view = new ModelAndView(VIEW_HISTORY_INFO);
        view.addObject("user", "Duy");
        return view;
    }

    @GetMapping(value = "/add")
    public ModelAndView showAddView() {
        ModelAndView view = new ModelAndView(VIEW_HISTORY_INFO_FORM);
        view.addObject("viewName", "เพิ่มข้อมูล");
        return view;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showDetailView(@PathVariable("id") String id) {
        ModelAndView view = new ModelAndView(VIEW_HISTORY_INFO_DETAIL);
        view.addObject("historyInfo", null);
        return view;
    }

    @GetMapping(value = "/{id}/edit")
    public ModelAndView showEditView(@PathVariable("id") String id) {
        ModelAndView view = new ModelAndView(VIEW_HISTORY_INFO_FORM);
        view.addObject("viewName", "แก้ไขข้อมูล");
        view.addObject("historyInfo", null);
        return view;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Profile>> search(@ModelAttribute Profile profile) {
        return ResponseEntity.ok().body(profileService.findBySearchCriteria(profile));
    }

}
