package com.obu.tech.poba.teaching_info;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class TeachingService {
    @Autowired TeachingRepository teachingRepository;
    List<Teaching> findBySearchCriteria(Teaching teaching){
        return teachingRepository.findAll(new SearchConditionBuilder<Teaching>()
                .ifNotNullThenAnd("name", LIKE, teaching.getName())
                .ifNotNullThenAnd("studyYear", LIKE, teaching.getStudyYear())
                .ifNotNullThenAnd("semester", LIKE, teaching.getSemester())
                .build()
        );
    }

    public Teaching save(Teaching teaching) {
        System.out.printf("teaching: "+teaching.name);
        return teachingRepository.saveAndFlush(teaching);
    }

    public Teaching findById(String id) {
        if (id.matches("\\d+")) {
            return teachingRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            System.out.println("Invalid researcher.staff_id: '" + id + "'");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

}
