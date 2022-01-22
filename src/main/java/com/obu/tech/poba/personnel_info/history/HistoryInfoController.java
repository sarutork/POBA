package com.obu.tech.poba.personnel_info.history;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/personnel-info/history")
public class HistoryInfoController {

    static final String VIEW_HISTORY_INFO = "personnel-info/history";
    static final String VIEW_HISTORY_INFO_FORM = "personnel-info/history-form";
    static final String VIEW_HISTORY_INFO_DETAIL = "personnel-info/history-view";

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


}
