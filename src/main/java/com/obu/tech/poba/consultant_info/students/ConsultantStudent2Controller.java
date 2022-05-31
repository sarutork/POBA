package com.obu.tech.poba.consultant_info.students;

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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/consultant/students2")
public class ConsultantStudent2Controller {

    static final String FRAGMENT_CONSULTANT_STUDENTS = "consultant-info/consultant-student2 :: consultant-student2";
    static final String FRAGMENT_CONSULTANT_STUDENTS_FORM = "consultant-info/consultant-student-form2 :: consultant-student-form2";
    static final String FRAGMENT_CONSULTANT_STUDENTS_FORM_SUM_CST = "consultant-info/consultant-std-sum-cst2 :: consultant-std-sum-cst2";
    static final String  FRAGMENT_CONSULTANT_STUDENTS_FORM_SUM_CST_DTL = "consultant-info/consultant-std-sum-cst-detail2 :: consultant-std-sum-cst-detail2";
    static final String FRAGMENT_CONSULTANT_STUDENTS_FORM_SUM_YEARLY = "consultant-info/consultant-std-sum-yearly2 :: consultant-std-sum-yearly2";


    @Autowired
    private ConsultantStudentService consultantStudentService;

    @Autowired
    private NameConverterUtils nameConverterUtils;

    @Autowired
    private YearGeneratorUtils yearGeneratorUtils;

    @GetMapping
    public ModelAndView showListView() {
        List<Integer> years = yearGeneratorUtils.genYears();
        return new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS).addObject("years", years);}

    @GetMapping("/search")
    public ResponseEntity<List<ConsultantStudent>> search(@ModelAttribute ConsultantStudent consultantStudent) {
        return ResponseEntity.ok().body(consultantStudentService.findBySearchCriteria(consultantStudent,consultantStudentService.studentsLevel_2));
    }

    @GetMapping("/add")
    public ModelAndView add() {return formAdd(new ConsultantStudent());}

    @GetMapping("/sum/consultant")
    public ModelAndView sumConsultant() {
        ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS_FORM_SUM_CST);
        view.addObject("viewName", "สรุปข้อมูลรายที่ปรึกษา");

        List<Integer> years = yearGeneratorUtils.genYears();
        view.addObject("years", years);

        return view;
    }

    @GetMapping("/search/sum/consultant")
    public ResponseEntity<List<ConsultantStudentReportDto>> searchSumConsultant(@ModelAttribute ConsultantStudentReportDto consultantStudentReportDto, HttpSession session) {
        List<ConsultantStudentReportDto> ctsList = consultantStudentService.findConsultantSummaryReport(consultantStudentReportDto);
        session.setAttribute("cstlist",ctsList);
        return ResponseEntity.ok().body(ctsList);
    }

    @GetMapping("/search/sum/consultant/detail/{name}/{surname}")
    public ModelAndView sumConsultant(@PathVariable("name") String name, @PathVariable("surname") String surname, HttpSession session) {

        ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS_FORM_SUM_CST_DTL);
        List<ConsultantStudentReportDto> cstlist = (List<ConsultantStudentReportDto>) session.getAttribute("cstlist");
        Optional<ConsultantStudentReportDto> consultantStudentReportDto = cstlist.stream()
                .filter(o -> o.getName().equals(name) && o.getSurname().equals(surname)).findFirst();

        List<Integer> years = yearGeneratorUtils.genYears();
        view.addObject("years", years);

        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("cstDetail",consultantStudentReportDto.get());
        session.setAttribute("cstDetail",consultantStudentReportDto.get());

        return view;
    }

    @GetMapping("/search/student-by-consultant")
    public ResponseEntity<List<StudentDto>> searchStudentByConsultant(HttpSession session) {
        ConsultantStudentReportDto cst = (ConsultantStudentReportDto) session.getAttribute("cstDetail");
        return ResponseEntity.ok().body(consultantStudentService.findStudentByConsultant(cst));
    }

    @GetMapping("/sum/yearly")
    public ModelAndView sumYearly() {
        ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS_FORM_SUM_YEARLY);
        ConsultantStudent consultantStudent = new ConsultantStudent();
        view.addObject("viewName", "สรุปข้อมูลรายปี");
        view.addObject("consultantStudent", consultantStudent);

        List<Integer> years = yearGeneratorUtils.genYears();
        view.addObject("years", years);

        return view;
    }
    @GetMapping("/search/yearly-report")
    public ResponseEntity<List<ConsultantDto>> yearlyReport(@ModelAttribute ConsultantDto consultantDto) {
        ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS_FORM_SUM_YEARLY);
        ConsultantStudent consultantStudent = new ConsultantStudent();
        view.addObject("viewName", "สรุปข้อมูลรายปี");
        view.addObject("consultantStudent", consultantStudent);

        List<Integer> years = yearGeneratorUtils.genYears();
        view.addObject("years", years);

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
    public ModelAndView save(@ModelAttribute("consultantStudent") @Valid ConsultantStudent consultantStudent, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(consultantStudent), bindingResult);
        }
        try {
            if(!StringUtils.isBlank(consultantStudent.getName())) {
                String[] fullName = nameConverterUtils.fullNameToNameNSurname(consultantStudent.getName());
                consultantStudent.setName(fullName[0]);
                consultantStudent.setSurname(fullName[1]);
            }

            if(!StringUtils.isBlank(consultantStudent.getStudentName())) {
                String[] stdFullName = nameConverterUtils.fullNameToNameNSurname(consultantStudent.getStudentName());
                consultantStudent.setStudentName(stdFullName[0]);
                consultantStudent.setStudentSurname(stdFullName[1]);
            }

            ConsultantStudent consultantStudentRes = consultantStudentService.save(consultantStudent);
            consultantStudentRes.setName(consultantStudentRes.getName()+" "+consultantStudentRes.getSurname());
            consultantStudentRes.setStudentName(consultantStudentRes.getStudentName()+" "+consultantStudentRes.getStudentSurname());

            return viewSuccess(consultantStudentRes);

        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS).addObject("responseMessage", "ไม่สำเร็จ");

        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id){
        ConsultantStudent teaching = consultantStudentService.findById(id);
        teaching.setName(teaching.getName()+" "+teaching.getSurname());
        teaching.setStudentName(teaching.getStudentName()+" "+teaching.getStudentSurname());
        return view(teaching);
    }

    private ModelAndView formAdd(ConsultantStudent data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(ConsultantStudent data) {
        List<Integer> years = yearGeneratorUtils.genYears();

        return new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS_FORM)
                .addObject("years", years)
                .addObject("consultantStudent", data);
    }

    private ModelAndView viewSuccess(ConsultantStudent data) {
        return view(data)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(ConsultantStudent data) {
        List<Integer> years = yearGeneratorUtils.genYears();

        return new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("years", years)
                .addObject("consultantStudent", data);
    }
}
