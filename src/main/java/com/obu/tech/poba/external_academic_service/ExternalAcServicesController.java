package com.obu.tech.poba.external_academic_service;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.utils.MemberAccessUtils;
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

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static com.obu.tech.poba.utils.role.Roles.*;

@Slf4j
@Controller
@RolesAllowed(ROLE_EXTERNAL_ACADEMIC_SERVICE_ACCESS)
@RequestMapping("/ext-academic-services")
public class ExternalAcServicesController {
    static final String FRAGMENT_EXT_ACADEMIC_SERVICES = "ext-academic-services/ext-academic-services :: ext-academic-services";
    static final String FRAGMENT_EXT_ACADEMIC_SERVICES_FORM = "ext-academic-services/ext-academic-services-form :: ext-academic-services-form";

    @Autowired
    ExternalAcServicesService externalAcServicesService;


    @Autowired
    NameConverterUtils nameConverter;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_EXT_ACADEMIC_SERVICES);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }

    @RolesAllowed(ROLE_EXTERNAL_ACADEMIC_SERVICE_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<ExternalAcServices>> search(@ModelAttribute ExternalAcServices externalAcServices) {
        return ResponseEntity.ok().body(externalAcServicesService.findBySearchCriteria(externalAcServices));
    }

    @RolesAllowed(ROLE_EXTERNAL_ACADEMIC_SERVICE_ADD)
    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request) {return formAdd(new ExternalAcServices(),request);}

    @RolesAllowed({ROLE_EXTERNAL_ACADEMIC_SERVICE_EDIT,ROLE_EXTERNAL_ACADEMIC_SERVICE_ADD})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("externalAcServices") @Valid ExternalAcServices externalAcServices,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(externalAcServices,request), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(externalAcServices.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(externalAcServices.getName());
                externalAcServices.setName(fullName[0]);
                externalAcServices.setSurname(fullName[1]);
            }

            ExternalAcServices externalAcServicesRes = externalAcServicesService.save(externalAcServices);
            externalAcServicesRes.setName(externalAcServicesRes.getName()+" "+externalAcServicesRes.getSurname());

            return viewSuccess(externalAcServicesRes,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            ModelAndView view = new ModelAndView(FRAGMENT_EXT_ACADEMIC_SERVICES_FORM);
            MemberAccess member = memberAccessUtils.getMemberAccess(request);
            view.addObject("user",member.getMember());
            view.addObject("roles",member.getRoles());
            view.addObject("responseMessage", "ไม่สำเร็จ");
            return view;

        }
    }

    @RolesAllowed({ROLE_EXTERNAL_ACADEMIC_SERVICE_SEARCH,ROLE_EXTERNAL_ACADEMIC_SERVICE_EDIT})
    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id,HttpServletRequest request){
        ExternalAcServices externalAcServices = externalAcServicesService.findById(id);
        externalAcServices.setName(externalAcServices.getName()+" "+externalAcServices.getSurname());
        return view(externalAcServices,request);
    }

    private ModelAndView formAdd(ExternalAcServices data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(ExternalAcServices data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_EXT_ACADEMIC_SERVICES_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("extService", data);
        return view;

    }

    private ModelAndView viewSuccess(ExternalAcServices data,HttpServletRequest request) {
        return view(data,request)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(ExternalAcServices data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_EXT_ACADEMIC_SERVICES_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("extService", data);
        return view;


    }
}
