package com.obu.tech.poba.personnel_info.education;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@RestController
@RequestMapping("api/personnel-info/education")
public class StudyInfoApiController {
    static final String VIEW_STUDY_INFO = "/personnel-info/education";

    @Autowired
    private StudyInfoService studyInfoService;

    @GetMapping
    public ResponseEntity<List<StudyInfo>> search(@ModelAttribute StudyInfo studyInfo) {
        return ResponseEntity.ok().body(studyInfoService.findBySearchCriteria(studyInfo));
    }

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(StudyInfo studyInfo, BindingResult bindingResult) {
        ModelAndView view = new ModelAndView(VIEW_STUDY_INFO);
        view.addObject("user", "Ekamon");
        if (bindingResult.hasErrors()) {
            //TODO: Handle error
            return view;
        }
        String[] fullNameArr = studyInfo.getName().split(" ");
        studyInfo.setName(fullNameArr[0].trim());
        String surname ="";
        for(int i=1 ;i< fullNameArr.length; i++) {
            surname += fullNameArr[i] + " ";
        }
        studyInfo.setSurname(surname.trim());
        //TODO: Handle error
        studyInfoService.save(studyInfo);

        return view;
    }
}
