package com.obu.tech.poba.personnel_info.profile;


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
@RequestMapping("/personnel-info/profile")
public class ProfileController {

    static final String FRAGMENT_HISTORY_INFO = "personnel-info/profile::profile";
    static final String FRAGMENT_HISTORY_INFO_FORM = "personnel-info/profile-form::profile-from";

    @Autowired
    ProfileService profileService;

    @Autowired
    NameConverterUtils nameConverter;

    @GetMapping
    public ModelAndView showListView() {return new ModelAndView(FRAGMENT_HISTORY_INFO);}

    @GetMapping("/search")
    public ResponseEntity<List<Profile>> search(@ModelAttribute Profile profile) {
        return ResponseEntity.ok().body(profileService.findBySearchCriteria(profile));
    }

    @GetMapping(value = "/add")
    public ModelAndView showAddView() { return formAdd(new Profile());}

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("profile") @Valid Profile profile, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(profile), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(profile.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(profile.getName());
                profile.setName(fullName[0]);
                profile.setSurname(fullName[1]);
            }

            Profile profileRes = profileService.save(profile);
            profileRes.setName(profileRes.getName()+" "+profileRes.getSurname());

            return viewSuccess(profileRes);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_HISTORY_INFO_FORM).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showDetailView(@PathVariable("id") String id) {
        Profile profile = profileService.findById(id);
        profile.setName(profile.getName()+" "+profile.getSurname());
        return view(profile);
    }

    private ModelAndView formAdd(Profile data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Profile data) {
        return new ModelAndView(FRAGMENT_HISTORY_INFO_FORM).addObject("profile", data);
    }

    private ModelAndView viewSuccess(Profile data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(Profile data) {
        return new ModelAndView(FRAGMENT_HISTORY_INFO_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("profile", data);
    }

}
