package com.obu.tech.poba.students;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class StudentsService {
    @Autowired
    StudentsRepository studentsRepository;
    List<Students> findBySearchCriteria(Students students){
        return studentsRepository.findAll(new SearchConditionBuilder<Students>()
                .ifNotNullThenAnd("studentsName", LIKE, students.getStudentsName())
                .ifNotNullThenOr("studentsSurname", LIKE, students.getStudentsName())
                .ifNotNullThenAnd("studentsYear", LIKE, students.getStudentsYear())
                .ifNotNullThenAnd("studentsLevel", LIKE, students.getStudentsLevel())
                .build()
        );
    }

    public Students save(Students students) {
        return studentsRepository.saveAndFlush(students);
    }

    public Students findById(String id) {
        if (id.matches("\\d+")) {
            return studentsRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
