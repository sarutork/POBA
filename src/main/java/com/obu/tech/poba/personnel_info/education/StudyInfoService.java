package com.obu.tech.poba.personnel_info.education;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.*;

@Service
public class StudyInfoService {
    @Autowired StudyInfoRepository studyInfoRepository;

    List<StudyInfo> findAll(){
        return studyInfoRepository.findAll();
    }
    List<StudyInfo> findBySearchCriteria(StudyInfo studyInfo){
        return studyInfoRepository.findAll(new SearchConditionBuilder<StudyInfo>()
                .ifNotNullThenAnd("name", LIKE, studyInfo.getName())
                .ifNotNullThenOr("surname", LIKE, studyInfo.getName())
                .ifNotNullThenAnd("startDate", DATE_AFTER_OR_EQUAL, studyInfo.getStartDate())
                .ifNotNullThenAnd("endDate", DATE_AFTER_OR_EQUAL, studyInfo.getEndDate())
                .build()
        );
    }
    public StudyInfo save(StudyInfo studyInfo) {
        return studyInfoRepository.saveAndFlush(studyInfo);
    }

}
