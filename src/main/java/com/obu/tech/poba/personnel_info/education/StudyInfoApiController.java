package com.obu.tech.poba.personnel_info.education;

import com.obu.tech.poba.utils.NameConverterUtils;
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

    @Autowired
    private NameConverterUtils nameConverterUtils;

    @GetMapping
    public ResponseEntity<List<StudyInfo>> search(@ModelAttribute StudyInfo studyInfo) {
        System.out.println(String.format("studyInfo %s",studyInfo));
        return ResponseEntity.ok().body(studyInfoService.findBySearchCriteria(studyInfo));
    }

    @RequestMapping(path = "/save", method = { RequestMethod.POST, RequestMethod.PUT , RequestMethod.PATCH}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView save(StudyInfo studyInfo, BindingResult bindingResult) {
        ModelAndView view = new ModelAndView(VIEW_STUDY_INFO);
        try {
            view.addObject("user", "Ekamon");
            if (bindingResult.hasErrors()) {
                //TODO: Handle error
                return view;
            }
            String[] fullName = nameConverterUtils.fullNameToNameNSurname(studyInfo.getName());
            studyInfo.setName(fullName[0]);
            studyInfo.setSurname(fullName[1]);

            //TODO: Handle error
            studyInfoService.save(studyInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
}
