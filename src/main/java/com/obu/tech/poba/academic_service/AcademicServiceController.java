package com.obu.tech.poba.academic_service;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.personnel_info.profile.Profile;
import com.obu.tech.poba.personnel_info.profile.ProfileService;
import com.obu.tech.poba.teaching_info.Teaching;
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
@RolesAllowed(ROLE_PERSONNEL_INFO_ACADEMIC_SERVICE_ACCESS)
@RequestMapping("/academic-service")
public class AcademicServiceController {
    static final String FRAGMENT_ACADEMIC_SERVICE = "academic-services/academic-service :: academic-service";
    static final String FRAGMENT_ACADEMIC_SERVICE_FORM = "academic-services/academic-service-form :: academic-service-form";

    @Autowired AcademicServiceService academicServiceService;
    
    @Autowired
    NameConverterUtils nameConverter;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @Autowired
    ProfileService profileService;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_ACADEMIC_SERVICE);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }

    @RolesAllowed(ROLE_PERSONNEL_INFO_ACADEMIC_SERVICE_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<AcademicService>> search(@ModelAttribute AcademicService academicService) {
        return ResponseEntity.ok().body(academicServiceService.findBySearchCriteria(academicService));
    }

    @RolesAllowed(ROLE_PERSONNEL_INFO_ACADEMIC_SERVICE_ADD)
    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request) {return formAdd(new AcademicService(),request);}

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("academicService") @Valid AcademicService academicService,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(academicService,request), bindingResult);
        }
        try{
            AcademicService academicServiceRes = academicServiceService.save(academicService);
            academicServiceRes.setPrefix(academicService.getPrefix());
            academicServiceRes.setName(academicService.getName());

            return viewSuccess(academicServiceRes,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_ACADEMIC_SERVICE).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @RolesAllowed({ROLE_PERSONNEL_INFO_ACADEMIC_SERVICE_SEARCH,ROLE_PERSONNEL_INFO_ACADEMIC_SERVICE_EDIT})
    @GetMapping(value = "/{id}")
    public ModelAndView showTeachingInfo(@PathVariable String id,HttpServletRequest request){
        AcademicService academicService = academicServiceService.findById(id);

        Profile profile = profileService.findByPersNo(academicService.getPersNo());
        academicService.setPrefix(profile.getPrefix().equals("อื่นๆ")? profile.getPrefixOther() : profile.getPrefix());
        academicService.setName(profile.getName()+" "+profile.getSurname());

        return view(academicService,request);
    }

    private ModelAndView formAdd(AcademicService data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(AcademicService data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_ACADEMIC_SERVICE_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("service", data);
        return view;
    }

    private ModelAndView viewSuccess(AcademicService data,HttpServletRequest request) {
        return view(data,request)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(AcademicService data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_ACADEMIC_SERVICE_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("service", data);
        return view;

    }


}
