package com.obu.tech.poba.lecturer;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class LecturerService {
    @Autowired
    LecturerRepository lecturerRepository;
    List<Lecturer> findBySearchCriteria(Lecturer lecturer){
        return lecturerRepository.findAll(new SearchConditionBuilder<Lecturer>()
                .ifNotNullThenAnd("name", LIKE, lecturer.getName())
                .ifNotNullThenOr("surname", LIKE, lecturer.getName())
                .ifNotNullThenAnd("studyYear", LIKE, lecturer.getStudyYear())
                .ifNotNullThenAnd("semester", LIKE, lecturer.getSemester())
                .build()
        );
    }

    public Lecturer save(Lecturer lecturer) {
        return lecturerRepository.saveAndFlush(lecturer);
    }

    public Lecturer findById(String id) {
        if (id.matches("\\d+")) {
            return lecturerRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
