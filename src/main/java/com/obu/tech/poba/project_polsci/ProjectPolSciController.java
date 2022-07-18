package com.obu.tech.poba.project_polsci;

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
@RolesAllowed(ROLE_POL_SCI_ACCESS)
@RequestMapping("/polsci")
public class ProjectPolSciController {
    static final String FRAGMENT_POLSCI = "project_polsci/polsci :: polsci";
    static final String FRAGMENT_POLSCI_FORM = "project_polsci/polsci-form :: polsci-form";

    @Autowired
    private ProjectPolSciService polSciService;

    @Autowired
    private NameConverterUtils nameConverter;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_POLSCI);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return new ModelAndView(FRAGMENT_POLSCI);
    }

    @RolesAllowed(ROLE_POL_SCI_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<ProjectPolSci>> search(@ModelAttribute ProjectPolSci polSci) {
        return ResponseEntity.ok().body(polSciService.findBySearchCriteria(polSci));
    }

    @RolesAllowed(ROLE_POL_SCI_ADD)
    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request) {return formAdd(new ProjectPolSci(),request);}

    @RolesAllowed({ROLE_POL_SCI_ADD,ROLE_POL_SCI_EDIT})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("polsci") @Valid ProjectPolSci polsci,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(polsci,request), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(polsci.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(polsci.getName());
                polsci.setName(fullName[0]);
                polsci.setSurname(fullName[1]);
            }

            ProjectPolSci polSciRest = polSciService.save(polsci);
            polSciRest.setName(polSciRest.getName()+" "+polSciRest.getSurname());

            return viewSuccess(polSciRest,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            ModelAndView view = new ModelAndView(FRAGMENT_POLSCI_FORM);
            MemberAccess member = memberAccessUtils.getMemberAccess(request);
            view.addObject("user",member.getMember());
            view.addObject("roles",member.getRoles());
            view.addObject("responseMessage", "ไม่สำเร็จ");
            return view;
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id,HttpServletRequest request){
        ProjectPolSci polSci = polSciService.findById(id);
        polSci.setName(polSci.getName()+" "+ polSci.getSurname());
        return view(polSci,request);
    }

    private ModelAndView formAdd(ProjectPolSci data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(ProjectPolSci data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_POLSCI_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("polsci", data);
        return view;
    }

    private ModelAndView viewSuccess(ProjectPolSci data,HttpServletRequest request) {
        return view(data,request)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(ProjectPolSci data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_POLSCI_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("polsci", data);
        return view;
    }
}
