package com.obu.tech.poba.personnel_info.profile;


import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.authenticate.POBAUser;
import com.obu.tech.poba.authenticate.POBAUserService;
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
@Slf4j
@Controller
@RolesAllowed("ROLE_PERSONNEL_INFO_PROFILE_ACCESS")
@RequestMapping("/personnel-info/profile")
public class ProfileController {

    static final String FRAGMENT_HISTORY_INFO = "personnel-info/profile::profile";
    static final String FRAGMENT_HISTORY_INFO_FORM = "personnel-info/profile-form::profile-from";

    @Autowired
    ProfileService profileService;

    @Autowired
    NameConverterUtils nameConverter;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_HISTORY_INFO);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }

    @RolesAllowed("ROLE_PERSONNEL_INFO_PROFILE_SEARCH")
    @GetMapping("/search")
    public ResponseEntity<List<Profile>> search(@ModelAttribute Profile profile) {
        return ResponseEntity.ok().body(profileService.findBySearchCriteria(profile));
    }

    @RolesAllowed("ROLE_PERSONNEL_INFO_PROFILE_SEARCH")
    @GetMapping("/search-txt")
    public ResponseEntity<List<Profile>> findByTxt(String searchTxt) {
        return ResponseEntity.ok().body(profileService.findByNameOrId(searchTxt));
    }

    @RolesAllowed("ROLE_PERSONNEL_INFO_PROFILE_ADD")
    @GetMapping(value = "/add")
    public ModelAndView showAddView(HttpServletRequest request) { return formAdd(new Profile(),request);}

    @RolesAllowed({"ROLE_PERSONNEL_INFO_PROFILE_ADD","ROLE_PERSONNEL_INFO_PROFILE_EDIT"})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("profile")
                                 @Valid Profile profile, BindingResult bindingResult,HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(profile,request), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(profile.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(profile.getName());
                profile.setName(fullName[0]);
                profile.setSurname(fullName[1]);
            }

            Profile profileRes = profileService.save(profile);
            profileRes.setName(profileRes.getName()+" "+profileRes.getSurname());

            return viewSuccess(profileRes,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_HISTORY_INFO_FORM).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @RolesAllowed({"ROLE_PERSONNEL_INFO_PROFILE_ADD","ROLE_PERSONNEL_INFO_PROFILE_EDIT"})
    @GetMapping(value = "/{id}")
    public ModelAndView showDetailView(@PathVariable("id") String id,HttpServletRequest request) {
        Profile profile = profileService.findById(id);
        profile.setName(profile.getName()+" "+profile.getSurname());
        return view(profile,request);
    }

    private ModelAndView formAdd(Profile data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Profile data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_HISTORY_INFO_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("profile", data);
        return view;
    }

    private ModelAndView viewSuccess(Profile data,HttpServletRequest request) {
        return view(data,request)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(Profile data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_HISTORY_INFO_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("profile", data);
        return view;
    }

}
