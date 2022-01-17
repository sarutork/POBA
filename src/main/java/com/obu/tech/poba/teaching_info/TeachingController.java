package com.obu.tech.poba.teaching_info;

import com.obu.tech.poba.personnel_info.education.StudyInfo;
import com.obu.tech.poba.personnel_info.education.StudyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/teaching")
public class TeachingController {
    static final String VIEW_TEACHING_INFO = "teaching-info/teaching";
    static final String VIEW_TEACHING_FORM = "teaching-info/teaching-form";


    @Autowired
    private TeachingService teachingService;

    @GetMapping
    public ModelAndView overview() {
        ModelAndView view = new ModelAndView(VIEW_TEACHING_INFO);
        view.addObject("user", "Ekamon");
        return view;
    }
    @GetMapping("/search")
    public ResponseEntity<List<Teaching>> search(@ModelAttribute Teaching teaching) {
        System.out.println(">>>>>>>>>>>>>>Search");
        return ResponseEntity.ok().body(teachingService.findBySearchCriteria(teaching));
    }

    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView view = new ModelAndView(VIEW_TEACHING_FORM);
        view.addObject("user", "Ekamon");
        view.addObject("viewName", "เพิ่มข้อมูล");
        Teaching teaching = new Teaching();
        view.addObject("teaching", teaching);
        return view;
    }
}
