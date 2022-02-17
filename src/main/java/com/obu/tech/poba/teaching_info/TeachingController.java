package com.obu.tech.poba.teaching_info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(Teaching teaching, BindingResult bindingResult) {
        ModelAndView view = new ModelAndView(VIEW_TEACHING_INFO);
        view.addObject("user", "Ekamon");
        if (bindingResult.hasErrors()) {
            //TODO: Handle error
            System.out.println("Error: "+bindingResult.getAllErrors());
            return view;
        }
        String[] fullNameArr = teaching.getName().split(" ");
        teaching.setName(fullNameArr[0].trim());
        String surname ="";
        for(int i=1 ;i< fullNameArr.length; i++) {
            surname += fullNameArr[i] + " ";
        }
        teaching.setSurname(surname.trim());
        //TODO: Handle error
        teachingService.save(teaching);

        return view;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showTeachingInfo(@PathVariable String id){
        System.out.println("View teaching info, id: " + id);
        ModelAndView view = new ModelAndView(VIEW_TEACHING_FORM);
        view.addObject("user", "Ekamon");
        view.addObject("viewName", "ดูข้อมูล");

        Teaching teaching = teachingService.findById(id);
        view.addObject("teaching",teaching);
        return view;
    }
}
