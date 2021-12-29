package com.obu.tech.poba.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/personnel-info")
public class PersonnelInfoController {
    @GetMapping(value = {"/further-study-info", "/further-study-info/{id}"})
    public ModelAndView furtherStudyInfo(HttpServletRequest request, @PathVariable(required = false) String id) throws Exception {
        System.out.println("ID: "+id);
        ModelAndView view = null;
        if(StringUtils.isEmpty(id)) {
            view = new ModelAndView("further-study-info");
        }else{
            view = new ModelAndView("further-study-info-view");
        }
        return view;
    }
    @GetMapping(value = {"/further-study-info/manage","/further-study-info/manage/{id}"})
    public ModelAndView furtherStudyInfoManage(HttpServletRequest request, @PathVariable(required = false) String id) throws Exception {
        System.out.println("ID: "+id);
        ModelAndView view = null;
        if(StringUtils.isEmpty(id)) {
            view = new ModelAndView("further-study-info-add");
        }else{
            view = new ModelAndView("further-study-info-edit");
        }
        return view;
    }
}
