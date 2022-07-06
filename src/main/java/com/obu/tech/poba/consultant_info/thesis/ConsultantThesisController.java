package com.obu.tech.poba.consultant_info.thesis;

import com.obu.tech.poba.personnel_info.profile.Profile;
import com.obu.tech.poba.personnel_info.profile.ProfileService;
import com.obu.tech.poba.students.Students;
import com.obu.tech.poba.students.StudentsService;
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
import javax.validation.Valid;
import java.util.List;

import static com.obu.tech.poba.utils.upload.UploadService.UPLOAD_GROUP_THESIS;

@Slf4j
@Controller
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

    @GetMapping
    public ModelAndView showListView() {return new ModelAndView(FRAGMENT_CONSULTANT_THESIS);}

    @GetMapping("/search")
    public ResponseEntity<List<ConsultantThesis>> search(@ModelAttribute ConsultantThesis consultantThesis) {
        return ResponseEntity.ok().body(consultantThesisService.findBySearchCriteria(consultantThesis));
    }

    @GetMapping("/add")
    public ModelAndView add() {
       ConsultantThesis thesis = new ConsultantThesis();
       Journal journal = new Journal();
       AcademicConference acdConf = new AcademicConference();

       return formAdd(thesis,journal,acdConf);
    }
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView save(@ModelAttribute("thesis") @Valid ConsultantThesis thesis, BindingResult bindingResultThesis,
                             @ModelAttribute("journal") @Valid Journal journal, BindingResult bindingResultJournal,
                             @ModelAttribute("acdConf") @Valid AcademicConference acdConf, BindingResult bindingResultAcdConf) {
        if (bindingResultThesis.hasErrors() || bindingResultJournal.hasErrors() || bindingResultAcdConf.hasErrors()) {
            throw new InvalidInputException(
                    formAdd(thesis,journal,acdConf), bindingResultThesis, bindingResultJournal, bindingResultAcdConf);
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

            return viewSuccess(thesisRes, journalRes, acdConfRes);

        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_CONSULTANT_THESIS_FORM).addObject("responseMessage", "ไม่สำเร็จ");

        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView showInfo(@PathVariable String id){
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

        return view(thesis, journal, academicConference);
    }

    private ModelAndView formAdd(ConsultantThesis thesis, Journal journal, AcademicConference acdConf) {
        return form(thesis,journal,acdConf).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(ConsultantThesis thesis, Journal journal, AcademicConference acdConf) {
        List<Integer> years = yearGeneratorUtils.genYears();
        return new ModelAndView(FRAGMENT_CONSULTANT_THESIS_FORM)
                .addObject("thesis", thesis)
                .addObject("journal",journal)
                .addObject("acdConf",acdConf)
                .addObject("years", years);
    }

    private ModelAndView viewSuccess(ConsultantThesis thesis, Journal journal, AcademicConference acdConf) {
        return view(thesis,journal,acdConf)
                .addObject("viewName", "ดูข้อมูล")
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView view(ConsultantThesis thesis, Journal journal, AcademicConference acdConf) {
        List<Integer> years = yearGeneratorUtils.genYears();
        return new ModelAndView(FRAGMENT_CONSULTANT_THESIS_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("thesis", thesis)
                .addObject("journal",journal)
                .addObject("acdConf",acdConf)
                .addObject("years", years);
    }


}
