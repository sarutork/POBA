package com.obu.tech.poba.consultant_info.students;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.personnel_info.profile.Profile;
import com.obu.tech.poba.personnel_info.profile.ProfileService;
import com.obu.tech.poba.students.Students;
import com.obu.tech.poba.students.StudentsService;
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
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.obu.tech.poba.utils.role.Roles.*;

@Slf4j
@Controller
@RolesAllowed(ROLE_CONSULTANT_STUDENT_MASTER_ACCESS)
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
    private ProfileService profileService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private NameConverterUtils nameConverterUtils;

    @Autowired
    private YearGeneratorUtils yearGeneratorUtils;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("years", years);
        return view;
    }

    @RolesAllowed(ROLE_CONSULTANT_STUDENT_MASTER_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<ConsultantStudent>> search(@ModelAttribute ConsultantStudent consultantStudent) {
        return ResponseEntity.ok().body(consultantStudentService.findBySearchCriteria(consultantStudent,consultantStudentService.studentsLevel_2));
    }

    @RolesAllowed(ROLE_CONSULTANT_STUDENT_MASTER_ADD)
    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request) {return formAdd(new ConsultantStudent(),request);}

    @RolesAllowed(ROLE_CONSULTANT_STUDENT_MASTER_SEARCH)
    @GetMapping("/sum/consultant")
    public ModelAndView sumConsultant(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS_FORM_SUM_CST);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "สรุปข้อมูลรายที่ปรึกษา");

        List<Integer> years = yearGeneratorUtils.genYears();
        view.addObject("years", years);

        return view;
    }

    @RolesAllowed(ROLE_CONSULTANT_STUDENT_MASTER_SEARCH)
    @GetMapping("/search/sum/consultant")
    public ResponseEntity<List<ConsultantStudentReportDto>> searchSumConsultant(@ModelAttribute ConsultantStudentReportDto consultantStudentReportDto, HttpSession session) {
        List<ConsultantStudentReportDto> ctsList = consultantStudentService.findConsultantSummaryReport(consultantStudentReportDto);
        session.setAttribute("cstlist",ctsList);
        return ResponseEntity.ok().body(ctsList);
    }

    @RolesAllowed({ROLE_CONSULTANT_STUDENT_MASTER_SEARCH,ROLE_CONSULTANT_STUDENT_MASTER_EDIT})
    @GetMapping("/search/sum/consultant/detail/{name}/{surname}")
    public ModelAndView sumConsultant(@PathVariable("name") String name,
                                      @PathVariable("surname") String surname,
                                      HttpSession session,
                                      HttpServletRequest request) {

        ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS_FORM_SUM_CST_DTL);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
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

    @RolesAllowed(ROLE_CONSULTANT_STUDENT_MASTER_SEARCH)
    @GetMapping("/search/student-by-consultant")
    public ResponseEntity<List<StudentDto>> searchStudentByConsultant(HttpSession session) {
        ConsultantStudentReportDto cst = (ConsultantStudentReportDto) session.getAttribute("cstDetail");
        return ResponseEntity.ok().body(consultantStudentService.findStudentByConsultant(cst));
    }

    @RolesAllowed(ROLE_CONSULTANT_STUDENT_MASTER_SEARCH)
    @GetMapping("/sum/yearly")
    public ModelAndView sumYearly(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS_FORM_SUM_YEARLY);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        ConsultantStudent consultantStudent = new ConsultantStudent();
        view.addObject("viewName", "สรุปข้อมูลรายปี");
        view.addObject("consultantStudent", consultantStudent);

        List<Integer> years = yearGeneratorUtils.genYears();
        view.addObject("years", years);

        return view;
    }
    @RolesAllowed(ROLE_CONSULTANT_STUDENT_MASTER_SEARCH)
    @GetMapping("/search/yearly-report")
    public ResponseEntity<List<ConsultantDto>> yearlyReport(@ModelAttribute ConsultantDto consultantDto,HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS_FORM_SUM_YEARLY);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
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

    @RolesAllowed({ROLE_CONSULTANT_STUDENT_MASTER_ADD,ROLE_CONSULTANT_STUDENT_MASTER_EDIT})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("consultantStudent") @Valid ConsultantStudent consultantStudent,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(consultantStudent,request), bindingResult);
        }
        try {

            ConsultantStudent consultantStudentRes = consultantStudentService.save(consultantStudent);
            consultantStudentRes.setPrefix(consultantStudent.getPrefix());
            consultantStudentRes.setName(consultantStudent.getName());
            consultantStudentRes.setStudentPrefix(consultantStudent.getStudentPrefix());
            consultantStudentRes.setStudentName(consultantStudent.getStudentName());
            consultantStudentRes.setYearOfStudy(consultantStudent.getYearOfStudy());
            consultantStudentRes.setStudentsLevel(consultantStudent.getStudentsLevel());
            consultantStudentRes.setAdmissionStatus(consultantStudent.getAdmissionStatus());
            consultantStudentRes.setCourse(consultantStudent.getCourse());

            return viewSuccess(consultantStudentRes,request);

        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS).addObject("responseMessage", "ไม่สำเร็จ");

        }
    }

    @RolesAllowed({ROLE_CONSULTANT_STUDENT_MASTER_SEARCH,ROLE_CONSULTANT_STUDENT_MASTER_EDIT})
    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id,HttpServletRequest request){
        ConsultantStudent consultantStudent = consultantStudentService.findById(id);
        Profile profile = profileService.findByPersNo(consultantStudent.getPersNo());
        Students students = studentsService.findByStudentId(consultantStudent.getStudentsId());
        consultantStudent.setPrefix(profile.getPrefix().equals("อื่นๆ")? profile.getPrefixOther() : profile.getPrefix());
        consultantStudent.setName(profile.getName()+" "+profile.getSurname());

        consultantStudent.setStudentPrefix(students.getStudentsPrefix().equals("อื่นๆ")? students.getStudentsPrefixOther() :
                students.getStudentsPrefix());
        consultantStudent.setStudentName(students.getStudentsName()+" "+students.getStudentsSurname());
        consultantStudent.setYearOfStudy(students.getStudentsYear());
        consultantStudent.setStudentsLevel(students.getStudentsLevel());
        consultantStudent.setAdmissionStatus(students.getStudentsStatus());
        consultantStudent.setCourse(students.getStudentsCourse());
        return view(consultantStudent,request);
    }

    private ModelAndView formAdd(ConsultantStudent data,HttpServletRequest request) {
        return form(data,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(ConsultantStudent data,HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("years", years);
        view.addObject("consultantStudent", data);
        return view;

    }

    private ModelAndView viewSuccess(ConsultantStudent data,HttpServletRequest request) {
        return view(data,request)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(ConsultantStudent data,HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_STUDENTS_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("years", years);
        view.addObject("consultantStudent", data);

        return view;
    }
}
