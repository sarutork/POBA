package com.obu.tech.poba.students;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.personnel_info.profile.Profile;
import com.obu.tech.poba.personnel_info.profile.ProfileService;
import com.obu.tech.poba.utils.MemberAccessUtils;
import com.obu.tech.poba.utils.NameConverterUtils;
import com.obu.tech.poba.utils.YearGeneratorUtils;
import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.obu.tech.poba.utils.role.Roles.*;

@Slf4j
@Controller
@RolesAllowed(ROLE_STUDENT_ACCESS)
@RequestMapping("/students")
public class StudentsController {
    static final String FRAGMENT_STUDENT = "students/student :: student";
    static final String FRAGMENT_STUDENT_FORM = "students/student-form :: student-form";

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private NameConverterUtils nameConverter;

    @Autowired
    private YearGeneratorUtils yearGeneratorUtils;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @Autowired
    ProfileService profileService;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_STUDENT);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("years", years);
        return view;
    }

    @RolesAllowed(ROLE_STUDENT_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<Students>> search(@ModelAttribute Students students) {
        List<Students> data = studentsService.findBySearchCriteria(students);
        List<Students> resStd = new ArrayList<>();
        for(Students s : data) {
            Students std = new Students();
            BeanUtils.copyProperties(s,std);
            Profile profile = profileService.findByPersNo(s.getPersNo());
            std.setPrefix(profile.getPrefix().equals("อื่นๆ") ? profile.getPrefixOther() : profile.getPrefix());
            std.setName(std.getPrefix()+" "+profile.getName() + " " + profile.getSurname());
            resStd.add(std);
        }

        return ResponseEntity.ok().body(resStd);
    }

    @RolesAllowed(ROLE_STUDENT_SEARCH)
    @GetMapping("/search-txt")
    public ResponseEntity<List<Students>> findByTxt(String searchTxt) {
        return ResponseEntity.ok().body(studentsService.findByNameOrId(searchTxt));
    }

    @RolesAllowed(ROLE_STUDENT_SEARCH)
    @GetMapping("/search-txt-level1")
    public ResponseEntity<List<Students>> findByTxt1(String searchTxt) {
        return ResponseEntity.ok().body(studentsService.findByNameOrIdAndLevel1(searchTxt));
    }

    @RolesAllowed(ROLE_STUDENT_SEARCH)
    @GetMapping("/search-txt-level23")
    public ResponseEntity<List<Students>> findByTxt23(String searchTxt) {
        return ResponseEntity.ok().body(studentsService.findByNameOrIdAndLevel23(searchTxt));
    }

    @RolesAllowed(ROLE_STUDENT_ADD)
    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request) {
        Students students = new Students();
        students.setStudentsUpdate(LocalDate.now());
        return formAdd(students,request);
    }

    @RolesAllowed({ROLE_STUDENT_ADD,ROLE_STUDENT_EDIT})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("students") @Valid Students students,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(students,request), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(students.getStudentsName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(students.getStudentsName());
                students.setStudentsName(fullName[0]);
                students.setStudentsSurname(fullName[1]);
            }

            Students studentsRes = studentsService.save(students);
            studentsRes.setStudentsName(students.getStudentsName()+" "+students.getStudentsSurname());
            studentsRes.setPrefix(students.getPrefix());
            studentsRes.setName(students.getName());

            return viewSuccess(studentsRes,request);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            ModelAndView view = new ModelAndView(FRAGMENT_STUDENT_FORM);
            MemberAccess member = memberAccessUtils.getMemberAccess(request);
            view.addObject("user",member.getMember());
            view.addObject("roles",member.getRoles());
            view.addObject("responseMessage", "ไม่สำเร็จ");
            return view;

        }
    }

    @RolesAllowed({ROLE_STUDENT_SEARCH,ROLE_STUDENT_EDIT})
    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id,HttpServletRequest request){
        Students students = studentsService.findById(id);
        students.setStudentsName(students.getStudentsName()+" "+students.getStudentsSurname());

        Profile profile = profileService.findByPersNo(students.getPersNo());
        students.setPrefix(profile.getPrefix().equals("อื่นๆ")? profile.getPrefixOther() : profile.getPrefix());
        students.setName(profile.getName()+" "+profile.getSurname());

        students.setStudentsUpdate(LocalDate.now());
        return view(students,request);
    }

    private ModelAndView formAdd(Students data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Students data,HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_STUDENT_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("years", years);
        view.addObject("student", data);
        return view;
    }

    private ModelAndView viewSuccess(Students data,HttpServletRequest request) {
        return view(data,request)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(Students data,HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_STUDENT_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("years", years);
        view.addObject("student", data);
        return view;
    }
}
