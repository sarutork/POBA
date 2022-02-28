package com.obu.tech.poba.personnel_info.study;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.*;

@Service
public class StudyInfoService {
    @Autowired StudyInfoRepository studyInfoRepository;

    public List<StudyInfo> findBySearchCriteria(StudyInfo studyInfo){
        return studyInfoRepository.findAll(new SearchConditionBuilder<StudyInfo>()
                .ifNotNullThenAnd("name", LIKE, studyInfo.getName())
                .ifNotNullThenOr("surname", LIKE, studyInfo.getName())
                .ifNotNullThenAnd("startDate", DATE_AFTER_OR_EQUAL, studyInfo.getStartDate())
                .ifNotNullThenAnd("endDate", DATE_BEFORE_OR_EQUAL, studyInfo.getEndDate())
                .build()
        );
    }

    public StudyInfo save(StudyInfo studyInfo) {
        return studyInfoRepository.saveAndFlush(studyInfo);
    }

    public StudyInfo findById(String id) {
        if (id.matches("\\d+")) {
            return studyInfoRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            System.out.println("Invalid staff_id: '" + id + "'");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

}
