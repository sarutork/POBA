package com.obu.tech.poba.personnel_info.research;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.utils.MemberAccessUtils;
import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import com.obu.tech.poba.utils.exceptions.UploadException;
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
@RolesAllowed(ROLE_PERSONNEL_INFO_RESEARCHER_ACCESS)
@RequestMapping("/personnel-info/researchers")
public class ResearcherController {

    static final String FRAGMENT_RESEARCHERS = "personnel-info/researcher :: researchers";
    static final String FRAGMENT_RESEARCHER_FORM = "personnel-info/researcher-form :: researcher-form";
    static final String FRAGMENT_RESEARCHER_VIEW = "personnel-info/researcher-view :: researcher-view";

    @Autowired
    private ResearcherService researcherService;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_RESEARCHERS);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }

    @RolesAllowed(ROLE_PERSONNEL_INFO_RESEARCHER_ADD)
    @GetMapping(value = "/add")
    public ModelAndView showAddView(HttpServletRequest request) {
        return formAdd(new Researcher(),request);
    }

    @RolesAllowed({ROLE_PERSONNEL_INFO_RESEARCHER_EDIT,ROLE_PERSONNEL_INFO_RESEARCHER_SEARCH})
    @GetMapping(value = "/{id}")
    public ModelAndView showDetailView(@PathVariable("id") String id,HttpServletRequest request) {
        return view(researcherService.findById(id),request);
    }

    @RolesAllowed(ROLE_PERSONNEL_INFO_RESEARCHER_EDIT)
    @GetMapping(value = "/{id}/edit")
    public ModelAndView showEditView(@PathVariable("id") String id,HttpServletRequest request) {
        Researcher researcher = researcherService.findById(id);

        if (StringUtils.isNotBlank(researcher.getSurname())) {
            researcher.setName(researcher.getName() + " " + researcher.getSurname());
        }

        return formUpdate(researcher,request);
    }

    @ResponseBody
    @RolesAllowed(ROLE_PERSONNEL_INFO_RESEARCHER_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<Researcher>> search(@ModelAttribute Researcher researcher) {
        List<Researcher> researchers = researcherService.search(researcher);
        return ResponseEntity.ok().body(researchers);
    }

    @RolesAllowed(ROLE_PERSONNEL_INFO_RESEARCHER_ADD)
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView add(@ModelAttribute("researcher") @Valid Researcher inputData,
                            BindingResult bindingResult,
                            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(inputData,request), bindingResult);
        }
        try {
            return viewSuccess(researcherService.add(inputData),request);

        } catch (UploadException ex) {
            return formAdd(inputData,request).addObject("responseMessage", ex.getMessage());

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            return new ModelAndView(FRAGMENT_RESEARCHERS).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @RolesAllowed(ROLE_PERSONNEL_INFO_RESEARCHER_EDIT)
    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView update(@PathVariable("id") String id,
                               @ModelAttribute("researcher") @Valid Researcher updateData,
                               BindingResult bindingResult,
                               HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formUpdate(updateData,request), bindingResult);
        }
        try {
            return viewSuccess(researcherService.update(id, updateData),request);

        } catch (UploadException ex) { // TODO: Handle UploadException
            return viewError(researcherService.findById(id), ex.getMessage(),request);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            return viewError(researcherService.findById(id), "ไม่สำเร็จ",request);
        }
    }

    private ModelAndView viewSuccess(Researcher data,HttpServletRequest request) {
        return view(data,request)
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView viewError(Researcher data, String message,HttpServletRequest request) {
        return view(data,request).addObject("responseMessage", message);
    }

    private ModelAndView view(Researcher data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_RESEARCHER_VIEW);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("researcher", data);
        return view;
    }

    private ModelAndView formAdd(Researcher data,HttpServletRequest request) {

        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView formUpdate(Researcher data,HttpServletRequest request) {

        return form(data,request).addObject("viewName", "แก้ไขข้อมูล");
    }

    private ModelAndView form(Researcher data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_RESEARCHER_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("researcher", data);
        return view;
    }
}
