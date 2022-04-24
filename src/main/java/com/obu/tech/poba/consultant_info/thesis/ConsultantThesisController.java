package com.obu.tech.poba.consultant_info.thesis;

import com.obu.tech.poba.consultant_info.students.ConsultantStudent;
import com.obu.tech.poba.utils.NameConverterUtils;
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
import java.util.List;

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
    NameConverterUtils nameConverter;

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
    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("thesis") @Valid ConsultantThesis thesis, BindingResult bindingResultThesis,
                             @ModelAttribute("journal") @Valid Journal journal, BindingResult bindingResultJournal,
                             @ModelAttribute("acdConf") @Valid AcademicConference acdConf, BindingResult bindingResultAcdConf) {
        if (bindingResultThesis.hasErrors() || bindingResultJournal.hasErrors() || bindingResultAcdConf.hasErrors()) {
            throw new InvalidInputException(
                    formAdd(thesis,journal,acdConf), bindingResultThesis, bindingResultJournal, bindingResultAcdConf);
        }

        try {
            if(!StringUtils.isBlank(thesis.getName())) {
                String[] fullName = nameConverter.fullNameToNameNSurname(thesis.getName());
                thesis.setName(fullName[0]);
                thesis.setSurname(fullName[1]);
            }

            System.out.println("Name: "+thesis.getName());

            if(!StringUtils.isBlank(thesis.getStudentName())) {
                String[] stdFullName = nameConverter.fullNameToNameNSurname(thesis.getStudentName());
                thesis.setStudentName(stdFullName[0]);
                thesis.setStudentSurname(stdFullName[1]);
            }

            ConsultantThesis thesisRes = consultantThesisService.save(thesis);
            System.out.println("Name1: "+thesisRes.getName());

            journal.setThesisId(thesisRes.getThesisId());

            Journal journalRes = journalService.save(journal);

            acdConf.setThesisId(thesisRes.getThesisId());
            acdConf.setJournalId(journalRes.getJournalId());

            AcademicConference acdConfRes = academicConfService.save(acdConf);

            thesisRes.setName(thesisRes.getName()+" "+thesisRes.getSurname());
            thesisRes.setStudentName(thesisRes.getStudentName()+" "+thesisRes.getStudentSurname());

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
        thesis.setName(thesis.getName()+" "+thesis.getSurname());
        thesis.setStudentName(thesis.getStudentName()+" "+thesis.getStudentSurname());

        Journal journal = journalService.findByThesisId(id);
        AcademicConference academicConference = academicConfService.findByThesisId(id);
        return view(thesis, journal, academicConference);
    }

    private ModelAndView formAdd(ConsultantThesis thesis, Journal journal, AcademicConference acdConf) {
        return form(thesis,journal,acdConf).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(ConsultantThesis thesis, Journal journal, AcademicConference acdConf) {
        return new ModelAndView(FRAGMENT_CONSULTANT_THESIS_FORM)
                .addObject("thesis", thesis)
                .addObject("journal",journal)
                .addObject("acdConf",acdConf);
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
        return new ModelAndView(FRAGMENT_CONSULTANT_THESIS_FORM).addObject("viewName", "ดูข้อมูล")
                .addObject("thesis", thesis)
                .addObject("journal",journal)
                .addObject("acdConf",acdConf);
    }


}
