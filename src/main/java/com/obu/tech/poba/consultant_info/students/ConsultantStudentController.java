package com.obu.tech.poba.consultant_info.students;

import com.obu.tech.poba.personnel_info.research.Researcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
        view.addObject("viewName", "สรุปข้อมูลรายปี");
        ConsultantStudent consultantStudent = new ConsultantStudent();
        view.addObject("consultantStudent", consultantStudent);
        return view;
    }
    @GetMapping("/search/yearly-report")
    public ResponseEntity<List<ConsultantDto>> yearlyReport(@ModelAttribute ConsultantDto consultantDto) {
        ModelAndView view = new ModelAndView(VIEW_CONSULTANT_STUDENTS_FORM_SUM_YEARLY);
        view.addObject("user", "Ekamon");
        view.addObject("viewName", "สรุปข้อมูลรายปี");
        ConsultantStudent consultantStudent = new ConsultantStudent();
        view.addObject("consultantStudent", consultantStudent);
        int yearStart = Integer.parseInt(consultantDto.getYearStart());
        int yearEnd = Integer.parseInt(consultantDto.getYearEnd());

        List<ConsultantDto> consultantDtos = consultantStudentService.findConsultantByNameStdLevelAdmissionStatus(consultantDto);
        List<ConsultantDto> yearlyReportList = new ArrayList<>();
        for(ConsultantDto c : consultantDtos){
            ConsultantDto cstDto = new ConsultantDto();
            BeanUtils.copyProperties(c,cstDto);
            int yearIndex=0;
            for(int i = yearStart; i<=yearEnd; i++){
                String studentCount = "";
                studentCount = consultantStudentService.findConsultantSumStudentReport(c,i);
                if(yearIndex == 0){
                    cstDto.setSumYear1(studentCount);
                }else if(yearIndex == 1){
                    cstDto.setSumYear2(studentCount);
                }else if(yearIndex == 2){
                    cstDto.setSumYear3(studentCount);
                }else if(yearIndex == 3){
                    cstDto.setSumYear4(studentCount);
                }else if(yearIndex == 4){
                    cstDto.setSumYear5(studentCount);
                }else if(yearIndex == 5){
                    cstDto.setSumYear6(studentCount);
                }else if(yearIndex == 6){
                    cstDto.setSumYear7(studentCount);
                }else if(yearIndex == 7){
                    cstDto.setSumYear8(studentCount);
                }else if(yearIndex == 8){
                    cstDto.setSumYear9(studentCount);
                }else if(yearIndex == 9){
                    cstDto.setSumYear10(studentCount);
                }
                yearIndex++;
            }
            yearlyReportList.add(cstDto);
        }
        return ResponseEntity.ok().body(yearlyReportList);
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
        String fullName = consultantStudent.getName().replaceAll("\\s+", " ").trim();
        int firstSpace = fullName.contains(" ") ? fullName.indexOf(" ") : fullName.length();
        consultantStudent.setName(fullName.substring(0, firstSpace));
        consultantStudent.setSurname(fullName.substring(firstSpace).trim());

        String stdFullName = consultantStudent.getStudentName().replaceAll("\\s+", " ").trim();
        int stdFirstSpace = stdFullName.contains(" ") ? stdFullName.indexOf(" ") : stdFullName.length();
        consultantStudent.setStudentName(stdFullName.substring(0, stdFirstSpace));
        consultantStudent.setStudentSurname(stdFullName.substring(stdFirstSpace).trim());

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
