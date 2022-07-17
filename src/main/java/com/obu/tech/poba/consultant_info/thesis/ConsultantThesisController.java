package com.obu.tech.poba.consultant_info.thesis;

import com.obu.tech.poba.authenticate.MemberAccess;
import com.obu.tech.poba.personnel_info.profile.Profile;
import com.obu.tech.poba.personnel_info.profile.ProfileService;
import com.obu.tech.poba.students.Students;
import com.obu.tech.poba.students.StudentsService;
import com.obu.tech.poba.utils.MemberAccessUtils;
import com.obu.tech.poba.utils.NameConverterUtils;
import com.obu.tech.poba.utils.YearGeneratorUtils;
import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import com.obu.tech.poba.utils.upload.Upload;
import com.obu.tech.poba.utils.upload.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import static com.obu.tech.poba.utils.upload.UploadService.UPLOAD_GROUP_THESIS;

@Slf4j
@Controller
@RolesAllowed(ROLE_CONSULTANT_THESIS_ACCESS)
@RequestMapping("/consultant/thesis")
public class ConsultantThesisController {
    static final String FRAGMENT_CONSULTANT_THESIS = "consultant-info/consultant-thesis :: consultant-thesis";
    static final String FRAGMENT_CONSULTANT_THESIS_FORM = "consultant-info/consultant-thesis-form :: consultant-thesis-form";

    @Autowired
    ConsultantThesisService consultantThesisService;

    @Autowired
    JournalService journalService;

    @Autowired
    AcademicConfService academicConfService;

    @Autowired
    UploadService uploadService;

    @Autowired
    NameConverterUtils nameConverter;

    @Autowired
    private YearGeneratorUtils yearGeneratorUtils;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private StudentsService studentsService;

    @Value("${poba.upload.thesis}")
    private String UPLOAD_THESIS_PATH;

    @Autowired
    private MemberAccessUtils memberAccessUtils;

    @GetMapping
    public ModelAndView showListView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_THESIS);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        return view;
    }

    @RolesAllowed(ROLE_CONSULTANT_THESIS_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<ConsultantThesis>> search(@ModelAttribute ConsultantThesis consultantThesis) {
        return ResponseEntity.ok().body(consultantThesisService.findBySearchCriteria(consultantThesis));
    }

    @RolesAllowed(ROLE_CONSULTANT_THESIS_ADD)
    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request) {
       ConsultantThesis thesis = new ConsultantThesis();
       Journal journal = new Journal();
       AcademicConference acdConf = new AcademicConference();

       return formAdd(thesis,journal,acdConf,request);
    }
    @RolesAllowed({ROLE_CONSULTANT_THESIS_ADD,ROLE_CONSULTANT_THESIS_EDIT})
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView save(@ModelAttribute("thesis") @Valid ConsultantThesis thesis, BindingResult bindingResultThesis,
                             @ModelAttribute("journal") @Valid Journal journal, BindingResult bindingResultJournal,
                             @ModelAttribute("acdConf") @Valid AcademicConference acdConf,
                             BindingResult bindingResultAcdConf,
                             HttpServletRequest request) {
        if (bindingResultThesis.hasErrors() || bindingResultJournal.hasErrors() || bindingResultAcdConf.hasErrors()) {
            throw new InvalidInputException(
                    formAdd(thesis,journal,acdConf,request), bindingResultThesis, bindingResultJournal, bindingResultAcdConf);
        }

        try {
            ConsultantThesis thesisRes = consultantThesisService.save(thesis);

            journal.setThesisId(thesisRes.getThesisId());

            Journal journalRes = journalService.save(journal);

            acdConf.setThesisId(thesisRes.getThesisId());
            acdConf.setJournalId(journalRes.getJournalId());

            long acdConfId = acdConf.getConferenceId();

            if(acdConfId != 0) {
                List<Upload> remains = uploadService.delete(
                        acdConf.getFilesToKeep(),
                        UPLOAD_GROUP_THESIS,
                        acdConf.getConferenceId()
                );
                List<Upload> uploads = uploadService.upload(
                        acdConf.getNewFiles(),
                        UPLOAD_GROUP_THESIS,
                        acdConf.getConferenceId(),
                        UPLOAD_THESIS_PATH
                );
                remains.addAll(uploads);
                acdConf.setUploads(remains);
            }

            AcademicConference acdConfRes = academicConfService.save(acdConf);

            if(acdConfId == 0) {
                List<Upload> uploads = uploadService.upload(
                        acdConf.getNewFiles(),
                        UPLOAD_GROUP_THESIS,
                        acdConfRes.getConferenceId(),
                        UPLOAD_THESIS_PATH
                );
            }
            thesisRes.setPrefix(thesis.getPrefix());
            thesisRes.setName(thesis.getName());
            thesisRes.setStudentPrefix(thesis.getStudentPrefix());
            thesisRes.setStudentName(thesis.getStudentName());
            thesisRes.setStudentsLevel(thesis.getStudentsLevel());
            thesisRes.setCourse(thesis.getCourse());

            return viewSuccess(thesisRes, journalRes, acdConfRes,request);

        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_THESIS);
            MemberAccess member = memberAccessUtils.getMemberAccess(request);
            view.addObject("user",member.getMember());
            view.addObject("roles",member.getRoles());
            view.addObject("responseMessage", "ไม่สำเร็จ");
            return view;


        }
    }

    @RolesAllowed({ROLE_CONSULTANT_THESIS_SEARCH,ROLE_CONSULTANT_THESIS_EDIT})
    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id,HttpServletRequest request){
        ConsultantThesis thesis = consultantThesisService.findById(id);
        if(thesis.getPersNo() !=null) {
            Profile profile = profileService.findByPersNo(thesis.getPersNo());
            thesis.setPrefix(profile.getPrefix().equals("อื่นๆ") ? profile.getPrefixOther() : profile.getPrefix());
            thesis.setName(profile.getName() + " " + profile.getSurname());
        }

        if(thesis.getStudentsId() != null){
            Students students = studentsService.findByStudentId(thesis.getStudentsId());
            thesis.setStudentPrefix(students.getStudentsPrefix().equals("อื่นๆ")? students.getStudentsPrefixOther() :
                    students.getStudentsPrefix());
            thesis.setStudentName(students.getStudentsName()+" "+students.getStudentsSurname());

            thesis.setStudentsLevel(students.getStudentsLevel());
            thesis.setCourse(students.getStudentsCourse());
        }

        Journal journal = journalService.findByThesisId(id);

        AcademicConference academicConference = academicConfService.findByThesisId(id);
        List<Upload> uploads = uploadService.getByGroupAndReference(UPLOAD_GROUP_THESIS, academicConference.getConferenceId());
        academicConference.setUploads(uploads);

        return view(thesis, journal, academicConference,request);
    }

    private ModelAndView formAdd(ConsultantThesis thesis, Journal journal,
                                 AcademicConference acdConf,HttpServletRequest request) {
        return form(thesis,journal,acdConf,request).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(ConsultantThesis thesis, Journal journal,
                              AcademicConference acdConf,HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_THESIS_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("thesis", thesis);
        view.addObject("journal",journal);
        view.addObject("acdConf",acdConf);
        view.addObject("years", years);
        return view;
    }

    private ModelAndView viewSuccess(ConsultantThesis thesis,
                                     Journal journal,
                                     AcademicConference acdConf,
                                     HttpServletRequest request) {
        return view(thesis,journal,acdConf,request)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(ConsultantThesis thesis,
                              Journal journal,
                              AcademicConference acdConf,
                              HttpServletRequest request) {
        List<Integer> years = yearGeneratorUtils.genYears();
        ModelAndView view = new ModelAndView(FRAGMENT_CONSULTANT_THESIS_FORM);
        MemberAccess member = memberAccessUtils.getMemberAccess(request);
        view.addObject("user",member.getMember());
        view.addObject("roles",member.getRoles());
        view.addObject("viewName", "ดูข้อมูล");
        view.addObject("thesis", thesis);
        view.addObject("journal",journal);
        view.addObject("acdConf",acdConf);
        view.addObject("years", years);
        return view;
    }


}
