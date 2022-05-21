package com.obu.tech.poba.external_academic_service;

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
@RequestMapping("/ext-academic-services")
public class ExternalAcServicesController {
    static final String FRAGMENT_EXT_ACADEMIC_SERVICES = "ext-academic-services/ext-academic-services :: ext-academic-services";
    static final String FRAGMENT_EXT_ACADEMIC_SERVICES_FORM = "ext-academic-services/ext-academic-services-form :: ext-academic-services-form";

    @Autowired
    ExternalAcServicesService externalAcServicesService;


    @Autowired
    NameConverterUtils nameConverter;

    @GetMapping
    public ModelAndView showListView() {
        return new ModelAndView(FRAGMENT_EXT_ACADEMIC_SERVICES);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ExternalAcServices>> search(@ModelAttribute ExternalAcServices externalAcServices) {
        return ResponseEntity.ok().body(externalAcServicesService.findBySearchCriteria(externalAcServices));
    }

    @GetMapping("/add")
    public ModelAndView add() {return formAdd(new ExternalAcServices());}

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("externalAcServices") @Valid ExternalAcServices externalAcServices, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(externalAcServices), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(externalAcServices.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(externalAcServices.getName());
                externalAcServices.setName(fullName[0]);
                externalAcServices.setSurname(fullName[1]);
            }

            ExternalAcServices externalAcServicesRes = externalAcServicesService.save(externalAcServices);
            externalAcServicesRes.setName(externalAcServicesRes.getName()+" "+externalAcServicesRes.getSurname());

            return viewSuccess(externalAcServicesRes);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_EXT_ACADEMIC_SERVICES_FORM).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id){
        ExternalAcServices externalAcServices = externalAcServicesService.findById(id);
        externalAcServices.setName(externalAcServices.getName()+" "+externalAcServices.getSurname());
        return view(externalAcServices);
    }

    private ModelAndView formAdd(ExternalAcServices data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(ExternalAcServices data) {
        return new ModelAndView(FRAGMENT_EXT_ACADEMIC_SERVICES_FORM).addObject("extService", data);
    }

    private ModelAndView viewSuccess(ExternalAcServices data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(ExternalAcServices data) {
        return new ModelAndView(FRAGMENT_EXT_ACADEMIC_SERVICES_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("extService", data);
    }
}
