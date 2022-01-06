package com.obu.tech.poba.personnel_info.history;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/personnel-info/history")
public class HistoryInfoController {

    static final String VIEW_HISTORY_INFO = "personnel-info/history";

    @GetMapping
    public ModelAndView overview() {
        ModelAndView view = new ModelAndView(VIEW_HISTORY_INFO);
        view.addObject("user", "Duy");
        return view;
    }
}
