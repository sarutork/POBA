package com.obu.tech.poba.students;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;
import static com.obu.tech.poba.utils.search.SearchOperator.NOT_EQUAL;

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

    public Map<String,Object> findByYear(String fromYear,String toYear,String studentsLevel){
        List<StudentsSummary> result = new ArrayList<>();
        List<Object[]> results = studentsSummaryRepository.findYear(fromYear,toYear,studentsLevel);
        AtomicInteger i = new AtomicInteger(1);
        results.stream().forEach(data->{

            StudentsSummary studentsSummary = new StudentsSummary();
            String title = (String) data[0];
            String name = (String) data[1];
            String surname = (String) data[2];

            studentsSummary.setFullName(title.concat(" ")+name.concat(" ").concat(surname));
            studentsSummary.setYear((String) data[3]);
            studentsSummary.setTotal(String.valueOf(data[4]));

            studentsSummary.setRowNum(String.valueOf(i.getAndIncrement()));

            result.add(studentsSummary);
        });

        Map<String, List<StudentsSummary>> groupByNameMap =
                result.stream().collect(Collectors.groupingBy(StudentsSummary::getFullName));

        List<String> ls1 = new ArrayList<>();
        ls1.add("0");
        int fYear = Integer.parseInt(fromYear);
        int tYear = Integer.parseInt(toYear);
        int betweenYear = tYear - fYear;
        int pYear = 10 - betweenYear;
        for(int x = fYear; x <= tYear; x++){
            ls1.add(String.valueOf(x));
        }
        List<String> y = ls1.stream().distinct().sorted().collect(Collectors.toList());
        if(betweenYear < 10){
            for(int k =1 ;k<pYear;k++){
                y.add("-");
            }
        }

        List<Map<String,String>> rest = new ArrayList<>();
        groupByNameMap.forEach((k,v)->{
            Map<String,String> res = new HashMap<>();
            res.put("name",k);
            v.stream().forEach(sum->{
                res.put(sum.getYear(),sum.getTotal());
            });
            rest.add(res);
        });

        Map<String,Object> mapResult = new HashMap<>();
        mapResult.put("header",y);
        mapResult.put("body",rest);

        return mapResult;
    }

    public List<Students> findByNameOrId(String searchTxt){
        return studentsRepository.findAll(new SearchConditionBuilder<Students>()
                .ifNotNullThenAnd("studentsName", LIKE, searchTxt)
                .ifNotNullThenOr("studentsSurname", LIKE, searchTxt)
                .ifNotNullThenOr("studentsId", LIKE,searchTxt)
                .build()
        );
    }
    public List<Students> findByNameOrIdAndLevel1(String searchTxt){
        return studentsRepository.findAll(new SearchConditionBuilder<Students>()
                .ifNotNullThenAnd("studentsName", LIKE, searchTxt)
                .ifNotNullThenOr("studentsSurname", LIKE, searchTxt)
                .ifNotNullThenOr("studentsId", LIKE,searchTxt)
                .ifNotNullThenAnd("studentsLevel", LIKE,"ปริญญาตรี")
                .build()
        );
    }

    public List<Students> findByNameOrIdAndLevel23(String searchTxt){
        return studentsRepository.findAll(new SearchConditionBuilder<Students>()
                .ifNotNullThenAnd("studentsName", LIKE, searchTxt)
                .ifNotNullThenOr("studentsSurname", LIKE, searchTxt)
                .ifNotNullThenOr("studentsId", LIKE,searchTxt)
                .ifNotNullThenAnd("studentsLevel", NOT_EQUAL,"ปริญญาตรี")
                .build()
        );
    }

    public Students findByStudentId(String studentId){
        return studentsRepository.findStudentId(studentId);
    }
}
