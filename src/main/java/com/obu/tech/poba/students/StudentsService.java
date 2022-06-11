package com.obu.tech.poba.students;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.obu.tech.poba.utils.search.SearchOperator.EQUAL;
import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class StudentsService {
    @Autowired
    StudentsRepository studentsRepository;

    @Autowired
    StudentsSummaryRepository studentsSummaryRepository;

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

    public List<Map<String,String>> findByYear(String year){
        List<Map<String,String>> maps = new ArrayList<>();
        List<StudentsSummary> results = studentsSummaryRepository.findRoles(year);
        results.stream().forEach(data->{
            Map<String,String> map = new HashMap<>();
            map.put("fullname",data.getFullName());
            map.put("year", data.getYear());
            map.put("total",data.getTotal());
            maps.add(map);
        });
        return maps;
    }

    public List<Students> findByNameOrId(String searchTxt){
        return studentsRepository.findAll(new SearchConditionBuilder<Students>()
                .ifNotNullThenAnd("studentsName", LIKE, searchTxt)
                .ifNotNullThenOr("studentsSurname", LIKE, searchTxt)
                .ifNotNullThenOr("studentsId", LIKE,searchTxt)
                .build()
        );
    }
}
