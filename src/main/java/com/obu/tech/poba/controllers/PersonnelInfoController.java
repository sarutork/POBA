package com.obu.tech.poba.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/personnel-info")
public class PersonnelInfoController {

    @GetMapping(value = "/education-info")
    public ModelAndView educationInfo(HttpServletRequest request) throws Exception {
        ModelAndView view = new ModelAndView("personnel-info/education-info");
        return view;
    }

    @GetMapping(value = {"/education-info/management", "/education-info/management/{id}"})
    public ModelAndView educationInfoManagement(HttpServletRequest request,  @PathVariable(required = false) String id) throws Exception {
        System.out.println("ID: " + id);
        ModelAndView view = null;
        if (StringUtils.isBlank(id)) {
            view = new ModelAndView("personnel-info/education-info-add");
        } else {
            view = new ModelAndView("personnel-info/education-info-edit");
        }
        return view;
    }

    @GetMapping(value = "/history-info")
    public ModelAndView historyInfo(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("personnel-info/history-info");
        view.addObject("user", "จิราภรณ์ รักไทรทอง");
        return view;
    }
}
