package com.obu.tech.poba.user_management;

import com.obu.tech.poba.authenticate.*;
import com.obu.tech.poba.utils.MemberAccessUtils;
import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
@RolesAllowed(ROLE_USER_MANAGEMENT_ACCESS)
@RequestMapping("/user-management")
public class UserManagementController {
    static final String FRAGMENT_USER_MANAGEMENT = "user-management/user-management :: user-management";
    static final String FRAGMENT_USER_MANAGEMENT_FORM = "user-management/user-management-from :: user-management-from";

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @Autowired
    private POBAUserService pobaUserService;
    @Autowired
    private POBAUserRolesRepository pobaUserRolesRepository;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_USER_MANAGEMENT);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }

    @RolesAllowed(ROLE_USER_MANAGEMENT_SEARCH)
    @GetMapping(value = "/search")
    public ResponseEntity<List<POBAUser>> search() {
        return ResponseEntity.ok().body(pobaUserService.findAll());
    }

    @RolesAllowed(ROLE_USER_MANAGEMENT_ADD)
    @GetMapping(value = "/add")
    public ModelAndView showAddView(HttpServletRequest request) {return formAdd(new UserDto(),request);}

    private ModelAndView formAdd(UserDto data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    @RolesAllowed({ROLE_USER_MANAGEMENT_EDIT,ROLE_USER_MANAGEMENT_ADD})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("userDto") @Valid UserDto userDto,
                             BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(userDto,request), bindingResult);
        }

        try{
            POBAUser pobaUser = new POBAUser();
            BeanUtils.copyProperties(userDto, pobaUser);
            pobaUserService.save(pobaUser);
            return viewSuccess(userDto,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            ModelAndView view = new ModelAndView(FRAGMENT_USER_MANAGEMENT_FORM);
            MemberAccess member = memberAccessUtils.getMemberAccess(request);
            view.addObject("user",member.getMember());
            view.addObject("roles",member.getRoles());
            view.addObject("responseMessage", "ไม่สำเร็จ");
            return view;

        }
    }

    @RolesAllowed({ROLE_USER_MANAGEMENT_SEARCH,ROLE_USER_MANAGEMENT_EDIT})
    @GetMapping(value = "/{id}")
    public ModelAndView showEditView(@PathVariable String id, HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_USER_MANAGEMENT_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        List<String> rolesList = pobaUserRolesRepository.findDistinctRoles();
        view.addObject("roleList", rolesList);

        POBAUser pobaUser = pobaUserService.findById(id);
        view.addObject("pobaUser",pobaUser);

        return view;
    }

    private ModelAndView form(UserDto data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_USER_MANAGEMENT_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        List<String> rolesList = pobaUserRolesRepository.findDistinctRoles();
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("roleList", rolesList);
        view.addObject("pobaUser", data);
        return view;
    }

    private ModelAndView viewSuccess(UserDto data,HttpServletRequest request) {
        return view(data,request)
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(UserDto data,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_USER_MANAGEMENT_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("pobaUser", data);
        return view;
    }

}
