package com.obu.tech.poba.personnel_info.research;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/personnel-info")
public class ResearchController {

    static final String VIEW_RESEARCHERS = "personnel-info/research";

    @GetMapping(value = "/researchers")
    public ModelAndView researchers(HttpSession session) {
        return new ModelAndView(VIEW_RESEARCHERS);
    }
}
