package com.obu.tech.poba.consultant_info.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/consultant/students")
public class ConsultantStudentController {

    static final String VIEW_CONSULTANT_STUDENTS = "consultant-info/consultant-student";
    static final String VIEW_CONSULTANT_STUDENTS_FORM = "consultant-info/consultant-student-form";
    static final String VIEW_CONSULTANT_STUDENTS_FORM_SUM_CST = "consultant-info/consultant-std-sum-cst";
    static final String VIEW_CONSULTANT_STUDENTS_FORM_SUM_CST_DTL = "consultant-info/consultant-std-sum-cst-detail";
    static final String VIEW_CONSULTANT_STUDENTS_FORM_SUM_YEARLY = "consultant-info/consultant-std-sum-yearly";


    @Autowired
    private ConsultantStudentService consultantStudentService;

    @GetMapping
    public ModelAndView overview() {
        ModelAndView view = new ModelAndView(VIEW_CONSULTANT_STUDENTS);
        view.addObject("user", "Ekamon");
        return view;
    }
    @GetMapping("/search")
    public ResponseEntity<List<ConsultantStudent>> search(@ModelAttribute ConsultantStudent consultantStudent) {
        return ResponseEntity.ok().body(consultantStudentService.findBySearchCriteria(consultantStudent));
    }

    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView view = new ModelAndView(VIEW_CONSULTANT_STUDENTS_FORM);
        view.addObject("user", "Ekamon");
        view.addObject("viewName", "เพิ่มข้อมูล");
        ConsultantStudent consultantStudent = new ConsultantStudent();
        view.addObject("consultantStudent", consultantStudent);
        return view;
    }

    @GetMapping("/sum/consultant")
    public ModelAndView sumConsultant() {
        ModelAndView view = new ModelAndView(VIEW_CONSULTANT_STUDENTS_FORM_SUM_CST);
        view.addObject("user", "Ekamon");
        view.addObject("viewName", "สรุปข้อมูลรายที่ปรึกษา");
        return view;
    }

    @GetMapping("/search/sum/consultant")
    public ResponseEntity<List<ConsultantStudentReportDto>> searchSumConsultant(@ModelAttribute ConsultantStudentReportDto consultantStudentReportDto) {
        return ResponseEntity.ok().body(consultantStudentService.findConsultantSummaryReport(consultantStudentReportDto));
    }

    @GetMapping("/search/sum/consultant/detail")
    public ModelAndView sumConsultant(@ModelAttribute ConsultantStudentReportDto consultantStudentReportDto, HttpSession session) {
        ModelAndView view = new ModelAndView(VIEW_CONSULTANT_STUDENTS_FORM_SUM_CST_DTL);
        view.addObject("user", "Ekamon");
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("cstDetail",consultantStudentReportDto);
        session.setAttribute("cstDetail",consultantStudentReportDto);

        return view;
    }

    @GetMapping("/search/student-by-consultant")
    public ResponseEntity<List<StudentDto>> searchStudentByConsultant(HttpSession session) {
        ConsultantStudentReportDto cst = (ConsultantStudentReportDto) session.getAttribute("cstDetail");
        return ResponseEntity.ok().body(consultantStudentService.findStudentByConsultant(cst));
    }

    @GetMapping("/sum/yearly")
    public ModelAndView sumYearly() {
        ModelAndView view = new ModelAndView(VIEW_CONSULTANT_STUDENTS_FORM_SUM_YEARLY);
        view.addObject("user", "Ekamon");
        view.addObject("viewName", "เพิ่มข้อมูล");
        ConsultantStudent consultantStudent = new ConsultantStudent();
        view.addObject("consultantStudent", consultantStudent);
        return view;
    }


    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(ConsultantStudent consultantStudent, BindingResult bindingResult) {
        ModelAndView view = new ModelAndView(VIEW_CONSULTANT_STUDENTS);
        view.addObject("user", "Ekamon");
        if (bindingResult.hasErrors()) {
            //TODO: Handle error
            System.out.println("Error: "+bindingResult.getAllErrors());
            return view;
        }
        String[] fullNameArr = consultantStudent.getName().split(" ");
        consultantStudent.setName(fullNameArr[0].trim());
        String surname ="";
        for(int i=1 ;i< fullNameArr.length; i++) {
            surname += fullNameArr[i] + " ";
        }
        consultantStudent.setSurname(surname.trim());

        String[] fullStudentNameArr = consultantStudent.getStudentName().split(" ");
        consultantStudent.setStudentName(fullStudentNameArr[0].trim());
        String studentSurname ="";
        for(int i=1 ;i< fullNameArr.length; i++) {
            studentSurname += fullStudentNameArr[i] + " ";
        }
        consultantStudent.setStudentSurname(studentSurname.trim());
        //TODO: Handle error
        consultantStudentService.save(consultantStudent);

        return view;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showTeachingInfo(@PathVariable String id){
        System.out.println("View teaching info, id: " + id);
        ModelAndView view = new ModelAndView(VIEW_CONSULTANT_STUDENTS_FORM);
        view.addObject("user", "Ekamon");
        view.addObject("viewName", "ดูข้อมูล");

        ConsultantStudent teaching = consultantStudentService.findById(id);
        view.addObject("consultantStudent",teaching);
        return view;
    }
}
