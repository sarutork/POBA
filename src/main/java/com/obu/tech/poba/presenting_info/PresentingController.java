package com.obu.tech.poba.presenting_info;

import com.obu.tech.poba.authenticate.MemberAccess;
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
@RolesAllowed(ROLE_PRESENTING_ACCESS)
@RequestMapping("/presenting")
public class PresentingController {
    static final String FRAGMENT_PRESENTING_INFO = "presenting-info/presenting :: presenting";
    static final String FRAGMENT_PRESENTING_FORM = "presenting-info/presenting-form :: presenting-form";

    @Autowired NameConverterUtils nameConverter;

    @Autowired PresentingService presentingService;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_PRESENTING_INFO);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }

    @RolesAllowed(ROLE_PRESENTING_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<Presenting>> search(@ModelAttribute Presenting presenting) {
        return ResponseEntity.ok().body(presentingService.findBySearchCriteria(presenting));
    }

    @RolesAllowed(ROLE_PRESENTING_ADD)
    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request) {return formAdd(new Presenting(),request);}

    @RolesAllowed({ROLE_PRESENTING_ADD,ROLE_PRESENTING_EDIT})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("presenting") @Valid Presenting presenting,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(presenting,request), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(presenting.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(presenting.getName());
                presenting.setName(fullName[0]);
                presenting.setSurname(fullName[1]);
            }

            Presenting presentingRes = presentingService.save(presenting);
            presentingRes.setName(presentingRes.getName()+" "+presentingRes.getSurname());

            return viewSuccess(presentingRes,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            ModelAndView view = new ModelAndView(FRAGMENT_PRESENTING_INFO);
            MemberAccess member = memberAccessUtils.getMemberAccess(request);
            view.addObject("user",member.getMember());
            view.addObject("roles",member.getRoles());
            view.addObject("responseMessage", "ไม่สำเร็จ");
            return view;

        }
    }

    @RolesAllowed({ROLE_PRESENTING_SEARCH,ROLE_PRESENTING_EDIT})
    @GetMapping(value = "/{id}")
    public ModelAndView showPresentingInfo(@PathVariable String id,HttpServletRequest request){
        Presenting presenting = presentingService.findById(id);
        presenting.setName(presenting.getName()+" "+presenting.getSurname());
        return view(presenting,request);
    }

    private ModelAndView formAdd(Presenting data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Presenting data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_PRESENTING_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("presenting", data);
        return view;
    }

    private ModelAndView viewSuccess(Presenting data,HttpServletRequest request) {
        return view(data,request)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(Presenting data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_PRESENTING_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("presenting", data);
        return view;

    }

}
