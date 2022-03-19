package com.obu.tech.poba.academic_service;

import com.obu.tech.poba.teaching_info.Teaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/academic-service")
public class AcademicServiceController {
    static final String FRAGMENT_ACADEMIC_SERVICE = "academic-services/academic-service :: academic-service";
    static final String FRAGMENT_ACADEMIC_SERVICE_FORM = "academic-services/academic-service-form :: academic-service-form";

    @GetMapping
    public ModelAndView showListView() {
        return new ModelAndView(FRAGMENT_ACADEMIC_SERVICE);
    }

    @GetMapping("/add")
    public ModelAndView add() {return formAdd(new AcademicService());}

    private ModelAndView formAdd(AcademicService data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(AcademicService data) {
        return new ModelAndView(FRAGMENT_ACADEMIC_SERVICE_FORM).addObject("service", data);
    }

    private ModelAndView viewSuccess(AcademicService data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(AcademicService data) {
        return new ModelAndView(FRAGMENT_ACADEMIC_SERVICE_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("service", data);
    }


}
