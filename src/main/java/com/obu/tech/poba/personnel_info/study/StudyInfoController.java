package com.obu.tech.poba.personnel_info.study;

import com.obu.tech.poba.personnel_info.research.Researcher;
import com.obu.tech.poba.utils.NameConverterUtils;
import com.obu.tech.poba.utils.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Controller
@RequestMapping("/personnel-info/study")
public class StudyInfoController {

    static final String FRAGMENT_STUDY = "personnel-info/study :: study";
    static final String FRAGMENT_STUDY_FORM = "personnel-info/study-form :: study-form";
    static final String FRAGMENT_STUDY_VIEW = "personnel-info/study-view :: study-view";

    @Autowired
    private StudyInfoService studyInfoService;

    @Autowired
    private NameConverterUtils nameConverterUtils;

    @GetMapping
    public ModelAndView showListView() {
        return new ModelAndView(FRAGMENT_STUDY);
    }

    @GetMapping(value = "/add")
    public ModelAndView showAddView() {return formAdd(new StudyInfo());}

    @GetMapping(value = "/{id}")
    public ModelAndView showStudyInfo(@PathVariable String id){return view(studyInfoService.findById(id));}

    @GetMapping(value = "/{id}/edit")
    public ModelAndView showEditView(@PathVariable String id) {
        ModelAndView view = new ModelAndView(FRAGMENT_STUDY_FORM);
        view.addObject("user", "Ekamon");
        view.addObject("viewName", "แก้ไขข้อมูล");
        StudyInfo studyInfo = studyInfoService.findById(id);
        view.addObject("studyInfo",studyInfo);
        return view;
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<StudyInfo>> search(@ModelAttribute StudyInfo studyInfo) {
        return ResponseEntity.ok().body(studyInfoService.findBySearchCriteria(studyInfo));
    }

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(@ModelAttribute("studyInfo") @Valid StudyInfo studyInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(formAdd(studyInfo), bindingResult);
        }
        try {
            String[] fullName = nameConverterUtils.fullNameToNameNSurname(studyInfo.getName());
            studyInfo.setName(fullName[0]);
            studyInfo.setSurname(fullName[1]);
            return viewSuccess(studyInfoService.save(studyInfo));
        }catch (Exception e){
            e.printStackTrace();
            log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            return new ModelAndView(FRAGMENT_STUDY).addObject("responseMessage", "ไม่สำเร็จ");        }
    }

    private ModelAndView formAdd(StudyInfo data) {
        return form(data).addObject("viewName", "เพิ่มข้อมูล");
    }

    private ModelAndView form(StudyInfo data) {
        return new ModelAndView(FRAGMENT_STUDY_FORM).addObject("studyInfo", data);
    }

    private ModelAndView viewSuccess(StudyInfo data) {
        return view(data)
                .addObject("responseMessage", "บันทึกสำเร็จ")
                .addObject("success", true) // success green, else red
                .addObject("timeout", true) // redirect after delay
                ;
    }

    private ModelAndView viewError(StudyInfo data, String message) {
        return view(data).addObject("responseMessage", message);
    }

    private ModelAndView view(StudyInfo data) {
        return new ModelAndView(FRAGMENT_STUDY_VIEW).addObject("studyInfo", data);
    }

    private Supplier<HttpClientErrorException> notFoundException() {
        return () -> new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }
}
