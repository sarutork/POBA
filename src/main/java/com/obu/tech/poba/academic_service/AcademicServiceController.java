package com.obu.tech.poba.academic_service;

import com.obu.tech.poba.teaching_info.Teaching;
import com.obu.tech.poba.utils.NameConverterUtils;
import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
@Slf4j
@Controller
@RequestMapping("/academic-service")
public class AcademicServiceController {
    static final String FRAGMENT_ACADEMIC_SERVICE = "academic-services/academic-service :: academic-service";
    static final String FRAGMENT_ACADEMIC_SERVICE_FORM = "academic-services/academic-service-form :: academic-service-form";

    @Autowired AcademicServiceService academicServiceService;
    
    @Autowired
    NameConverterUtils nameConverter;

    @GetMapping
    public ModelAndView showListView() {
        return new ModelAndView(FRAGMENT_ACADEMIC_SERVICE);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AcademicService>> search(@ModelAttribute AcademicService academicService) {
        return ResponseEntity.ok().body(academicServiceService.findBySearchCriteria(academicService));
    }

    @GetMapping("/add")
    public ModelAndView add() {return formAdd(new AcademicService());}

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("academicService") @Valid AcademicService academicService, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(academicService), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(academicService.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(academicService.getName());
                academicService.setName(fullName[0]);
                academicService.setSurname(fullName[1]);
            }

            AcademicService academicServiceRes = academicServiceService.save(academicService);
            academicServiceRes.setName(academicServiceRes.getName()+" "+academicServiceRes.getSurname());

            return viewSuccess(academicServiceRes);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_ACADEMIC_SERVICE).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showTeachingInfo(@PathVariable String id){
        AcademicService academicService = academicServiceService.findById(id);
        academicService.setName(academicService.getName()+" "+academicService.getSurname());
        return view(academicService);
    }

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
