package com.obu.tech.poba.consultant_info.students;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class ConsultantStudentService {
    @Autowired
    ConsultantStudentRepository consultantStudentRepository;
    List<ConsultantStudent> findBySearchCriteria(ConsultantStudent consultantStudent){
        return consultantStudentRepository.findAll(new SearchConditionBuilder<ConsultantStudent>()
                .ifNotNullThenAnd("name", LIKE, consultantStudent.getName())
                .ifNotNullThenOr("surname", LIKE, consultantStudent.getName())
                .ifNotNullThenAnd("yearOfStudy", LIKE, consultantStudent.getYearOfStudy())
                .ifNotNullThenAnd("studentsLevel", LIKE, consultantStudent.getStudentsLevel())
                .build()
        );
    }

    public ConsultantStudent save(ConsultantStudent consultantStudent) {
        return consultantStudentRepository.saveAndFlush(consultantStudent);
    }

    public ConsultantStudent findById(String id) {
        if (id.matches("\\d+")) {
            return consultantStudentRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            System.out.println("Invalid staff_id: '" + id + "'");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

}
