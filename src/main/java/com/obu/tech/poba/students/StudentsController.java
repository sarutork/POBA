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

    @GetMapping
    public ModelAndView showListView() {
        List<Integer> years = yearGeneratorUtils.genYears();
        return new ModelAndView(FRAGMENT_STUDENT).addObject("years", years);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Students>> search(@ModelAttribute Students students) {
        return ResponseEntity.ok().body(studentsService.findBySearchCriteria(students));
    }

    @GetMapping("/add")
    public ModelAndView add() {
        Students students = new Students();
        students.setStudentsUpdate(LocalDate.now());
        return formAdd(students);
    }

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("training") @Valid Students students, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(students), bindingResult);
        }
        try{
            if(!StringUtils.isBlank(students.getStudentsName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(students.getStudentsName());
                students.setStudentsName(fullName[0]);
                students.setStudentsSurname(fullName[1]);
            }

            if(!StringUtils.isBlank(students.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(students.getName());
                students.setName(fullName[0]);
                students.setSurname(fullName[1]);
            }

            Students studentsRes = studentsService.save(students);
            studentsRes.setStudentsName(students.getStudentsName()+" "+students.getStudentsSurname());
            studentsRes.setName(students.getName()+" "+students.getSurname());

            return viewSuccess(studentsRes);
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_STUDENT_FORM).addObject("responseMessage", "ไม่สำเร็จ");
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showTeachingInfo(@PathVariable String id){
        Students students = studentsService.findById(id);
        students.setStudentsName(students.getStudentsName()+" "+students.getStudentsSurname());
        students.setName(students.getName()+" "+students.getSurname());
        students.setStudentsUpdate(LocalDate.now());
        return view(students);
    }

    private ModelAndView formAdd(Students data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(Students data) {
        List<Integer> years = yearGeneratorUtils.genYears();
        return new ModelAndView(FRAGMENT_STUDENT_FORM)
                .addObject("years", years)
                .addObject("student", data);
    }

    private ModelAndView viewSuccess(Students data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(Students data) {
        List<Integer> years = yearGeneratorUtils.genYears();
        return new ModelAndView(FRAGMENT_STUDENT_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("years", years)
                .addObject("student", data);
    }
}
