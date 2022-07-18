package com.obu.tech.poba.students;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.utils.MemberAccessUtils;
import com.obu.tech.poba.utils.NameConverterUtils;
import com.obu.tech.poba.utils.YearGeneratorUtils;
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
import java.time.LocalDate;
import java.util.List;

import static com.obu.tech.poba.utils.role.Roles.ROLE_STUDENT_SUMMARY_ACCESS;
import static com.obu.tech.poba.utils.role.Roles.ROLE_STUDENT_SUMMARY_SEARCH;

@Slf4j
@Controller
@RolesAllowed(ROLE_STUDENT_SUMMARY_ACCESS)
@RequestMapping("/students/summary")
public class StudentsSummaryController {
    static final String FRAGMENT_STUDENT_SUMMARY = "students/student-summary :: student-summary";

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private NameConverterUtils nameConverter;

    @Autowired
    private YearGeneratorUtils yearGeneratorUtils;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_STUDENT_SUMMARY);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("years",years);
        return view;
    }

    @RolesAllowed(ROLE_STUDENT_SUMMARY_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<?> search(@ModelAttribute StudentsSummary students) {
        return ResponseEntity.ok().body(studentsService.findByYear(students.getFromYear(),
                students.getToYear(),
                students.getLevel()));
    }
}
