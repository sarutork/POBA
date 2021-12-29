package com.obu.tech.poba.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("")
public class WebController {

    private boolean isShowBtnUpdate = false;

    @GetMapping("/")
    public ModelAndView index() throws Exception {
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request) throws Exception {
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request) throws Exception {
        ModelAndView view = new ModelAndView("home");
        return view;
    }

    @RequestMapping(value = "/header", method = RequestMethod.POST)
    public ModelAndView header(HttpServletRequest request) throws Exception {
        ModelAndView view = new ModelAndView("layout/header");
        view.addObject("user", "One buck up");
        return view;
    }
    @RequestMapping(value = "/sidebar", method = RequestMethod.POST)
    public ModelAndView sidebar(HttpServletRequest request, HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("layout/sidebar");
        return view;
    }
}
