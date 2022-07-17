package com.obu.tech.poba.personnel_info.study;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.utils.MemberAccessUtils;
import com.obu.tech.poba.utils.NameConverterUtils;
import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.function.Supplier;

import static com.obu.tech.poba.utils.role.Roles.*;

@Slf4j
@Controller
@RolesAllowed(ROLE_PERSONNEL_INFO_STUDY_ACCESS)
@RequestMapping("/personnel-info/study")
public class StudyInfoController {

    static final String FRAGMENT_STUDY = "personnel-info/study :: study";
    static final String FRAGMENT_STUDY_FORM = "personnel-info/study-form :: study-form";
    static final String FRAGMENT_STUDY_VIEW = "personnel-info/study-view :: study-view";

    @Autowired
    private StudyInfoService studyInfoService;

    @Autowired
    private NameConverterUtils nameConverterUtils;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_STUDY);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }

    @RolesAllowed(ROLE_PERSONNEL_INFO_STUDY_ADD)
    @GetMapping(value = "/add")
    public ModelAndView showAddView(HttpServletRequest request) {return formAdd(new StudyInfo(),request);}

    @RolesAllowed({ROLE_PERSONNEL_INFO_STUDY_EDIT,ROLE_PERSONNEL_INFO_STUDY_SEARCH})
    @GetMapping(value = "/{id}")
    public ModelAndView showStudyInfo(@PathVariable String id,HttpServletRequest request){
        StudyInfo studyInfo = studyInfoService.findById(id);
        studyInfo.setName(studyInfo.getName()+" "+studyInfo.getSurname());
        return view(studyInfo,request);
    }

    @RolesAllowed(ROLE_PERSONNEL_INFO_STUDY_EDIT)
    @GetMapping(value = "/{id}/edit")
    public ModelAndView showEditView(@PathVariable String id) {
        ModelAndView view = new ModelAndView(FRAGMENT_STUDY_FORM);
        view.addObject("viewName", "แก้ไขข้อมูล");
        StudyInfo studyInfo = studyInfoService.findById(id);
        studyInfo.setName(studyInfo.getName()+" "+studyInfo.getSurname());
        view.addObject("studyInfo",studyInfo);
        return view;
    }

    @RolesAllowed(ROLE_PERSONNEL_INFO_STUDY_SEARCH)
    @GetMapping(value = "/search")
    public ResponseEntity<List<StudyInfo>> search(@ModelAttribute StudyInfo studyInfo) {
        return ResponseEntity.ok().body(studyInfoService.findBySearchCriteria(studyInfo));
    }

    @RolesAllowed({ROLE_PERSONNEL_INFO_STUDY_ADD,ROLE_PERSONNEL_INFO_STUDY_EDIT})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("studyInfo") @Valid StudyInfo studyInfo,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(studyInfo,request), bindingResult);
        }
        try {
            if(!StringUtils.isBlank(studyInfo.getName())) {
                String[] fullName = nameConverterUtils.fullNameToNameNSurname(studyInfo.getName());
                studyInfo.setName(fullName[0]);
                studyInfo.setSurname(fullName[1]);
            }
            StudyInfo studyInfoRes = studyInfoService.save(studyInfo);
            studyInfoRes.setName(studyInfoRes.getName()+" "+studyInfoRes.getSurname());
            return viewSuccess(studyInfoRes,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_STUDY).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    private ModelAndView formAdd(StudyInfo data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(StudyInfo data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_STUDY_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("studyInfo", data);
        return view;
    }

    private ModelAndView viewSuccess(StudyInfo data,HttpServletRequest request) {
        return view(data,request)
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView viewError(StudyInfo data, String message,HttpServletRequest request) {
        return view(data,request).addObject("responseMessage", message);
    }

    private ModelAndView view(StudyInfo data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_STUDY_VIEW);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("studyInfo", data);
        return view;
    }

    private Supplier<HttpClientErrorException> notFoundException() {
        return () -> new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }
}
