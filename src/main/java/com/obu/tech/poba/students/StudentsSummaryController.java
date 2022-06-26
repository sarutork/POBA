package com.obu.tech.poba.students;

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

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/students/summary")
public class StudentsSummaryController {
    static final String FRAGMENT_STUDENT_SUMMARY = "students/student-summary :: student-summary";

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private NameConverterUtils nameConverter;

    @Autowired
    private YearGeneratorUtils yearGeneratorUtils;

    @GetMapping
    public ModelAndView showListView() {
        List<Integer> years = yearGeneratorUtils.genYears();
        return new ModelAndView(FRAGMENT_STUDENT_SUMMARY).addObject("years",years);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@ModelAttribute StudentsSummary students) {
        return ResponseEntity.ok().body(studentsService.findByYear(students.getFromYear(),
                students.getToYear(),
                students.getLevel()));
    }
}
