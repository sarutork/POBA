package com.obu.tech.poba.consultant_info.thesis;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;
import static com.obu.tech.poba.utils.search.SearchOperator.EQUAL;
import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class ConsultantThesisService {

    @Autowired
    ConsultantThesisRepository consultantThesisRepository;

    List<ConsultantThesis> findBySearchCriteria(ConsultantThesis consultantThesis){
        return consultantThesisRepository.findAll(new SearchConditionBuilder<ConsultantThesis>()
                .ifNotNullThenAnd("name", LIKE, consultantThesis.getName())
                .ifNotNullThenOr("surname", LIKE, consultantThesis.getName())
                .ifNotNullThenOr("studentName",LIKE,consultantThesis.getName())
                .ifNotNullThenOr("studentSurname",LIKE,consultantThesis.getName())
                .ifNotNullThenAnd("thesisType",LIKE,consultantThesis.getThesisType())
                .ifNotNullThenAnd("studentLevel",LIKE,consultantThesis.getStudentLevel())
                .build()
        );
    }

    public ConsultantThesis save(ConsultantThesis consultantThesis) {
        return consultantThesisRepository.saveAndFlush(consultantThesis);
    }

    public ConsultantThesis findById(String id) {
        if (id.matches("\\d+")) {
            return consultantThesisRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
