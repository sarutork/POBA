package com.obu.tech.poba.personnel_info.education;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/personnel-info/education")
public class StudyInfoApiController {

    @Autowired
    private StudyInfoRepository studyInfoRepository;

    @GetMapping
    public ResponseEntity<List<StudyInfo>> search(@ModelAttribute StudyInfo studyInfo) {
        return ResponseEntity.ok().body(studyInfoRepository.findByCriteria(studyInfo));
    }

    @PostMapping("/add")
    public ResponseEntity<StudyInfo> add(@Valid @RequestBody StudyInfo studyInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        studyInfoRepository.insert(studyInfo);
        return ResponseEntity.ok().body(studyInfo);
    }


}
