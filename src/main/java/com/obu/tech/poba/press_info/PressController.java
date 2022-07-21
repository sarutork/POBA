package com.obu.tech.poba.press_info;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.personnel_info.profile.Profile;
import com.obu.tech.poba.personnel_info.profile.ProfileService;
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
@RolesAllowed(ROLE_PRESS_ACCESS)
@RequestMapping("/press")
public class PressController {
    static final String FRAGMENT_PRESS_INFO = "press-info/press :: press";
    static final String FRAGMENT_PRESS_FORM = "press-info/press-form :: press-form";

    @Autowired
    NameConverterUtils nameConverter;

    @Autowired
    PressService pressService;

    @Autowired
    ProfileService profileService;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_PRESS_INFO);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }

    @RolesAllowed(ROLE_PRESS_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<Press>> search(@ModelAttribute Press press) {
        return ResponseEntity.ok().body(pressService.findBySearchCriteria(press));
    }

    @RolesAllowed(ROLE_PRESS_ADD)
    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request) {return formAdd(new Press(),request);}

    @RolesAllowed({ROLE_PRESS_EDIT,ROLE_PRESS_ADD})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("press")@Valid Press press,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(press,request), bindingResult);
        }
        try{

            if(!StringUtils.isBlank(press.getGuestName1())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(press.getGuestName1());
                press.setGuestName1(fullName[0]);
                press.setGuestSurname1(fullName[1]);
            }
            if(!StringUtils.isBlank(press.getGuestName2())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(press.getGuestName2());
                press.setGuestName2(fullName[0]);
                press.setGuestSurname2(fullName[1]);
            }
            if(!StringUtils.isBlank(press.getGuestName3())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(press.getGuestName3());
                press.setGuestName3(fullName[0]);
                press.setGuestSurname3(fullName[1]);
            }

            Press pressRes = pressService.save(press);
            pressRes.setPrefix(press.getPrefix());
            pressRes.setName(press.getName());

            if(!StringUtils.isBlank(pressRes.getGuestName1())) {
                pressRes.setGuestName1(pressRes.getGuestName1() + " " + pressRes.getGuestSurname1());
            }
            if(!StringUtils.isBlank(pressRes.getGuestName2())) {
                pressRes.setGuestName2(pressRes.getGuestName2() + " " + pressRes.getGuestSurname2());
            }
            if(!StringUtils.isBlank(pressRes.getGuestName3())) {
                pressRes.setGuestName3(pressRes.getGuestName3() + " " + pressRes.getGuestSurname3());
            }

            return viewSuccess(pressRes,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            ModelAndView view = new ModelAndView(FRAGMENT_PRESS_FORM);
            MemberAccess member = memberAccessUtils.getMemberAccess(request);
            view.addObject("user",member.getMember());
            view.addObject("roles",member.getRoles());
            view.addObject("responseMessage", "ไม่สำเร็จ");
            return view;

        }
    }

    @RolesAllowed({ROLE_PRESS_SEARCH,ROLE_PRESS_EDIT})
    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id,HttpServletRequest request){
        Press press = pressService.findById(id);

        Profile profile = profileService.findByPersNo(press.getPersNo());
        press.setPrefix(profile.getPrefix().equals("อื่นๆ")? profile.getPrefixOther() : profile.getPrefix());
        press.setName(profile.getName()+" "+profile.getSurname());

        if(!StringUtils.isBlank(press.getGuestName1())) {
            press.setGuestName1(press.getGuestName1() + " " + press.getGuestSurname1());
        }

        if(!StringUtils.isBlank(press.getGuestName2())) {
            press.setGuestName2(press.getGuestName2() + " " + press.getGuestSurname2());
        }

        if(!StringUtils.isBlank(press.getGuestName3())) {
            press.setGuestName3(press.getGuestName3() + " " + press.getGuestSurname3());
        }

        return view(press,request);
    }

    private ModelAndView formAdd(Press data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Press data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_PRESS_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("press", data);
        return view;
    }

    private ModelAndView viewSuccess(Press data,HttpServletRequest request) {
        return view(data,request)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }
    private ModelAndView view(Press data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_PRESS_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("press", data);
        return view;
    }
}
