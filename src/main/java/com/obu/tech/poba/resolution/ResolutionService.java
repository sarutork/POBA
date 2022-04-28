package com.obu.tech.poba.resolution;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.*;

@Service
public class ResolutionService {
    @Autowired
    ResolutionRepository resolutionRepository;

    public List<Resolution> findBySearchCriteria(String bordNo1,String bordNo2, LocalDate bordDateStart, LocalDate bordDateEnd){
        return resolutionRepository.findAll(new SearchConditionBuilder<Resolution>()
                .ifNotNullThenAnd("bordNo1", EQUAL, bordNo1)
                .ifNotNullThenAnd("bordNo2", EQUAL, bordNo2)
                .ifNotNullThenAnd("bordDate", DATE_AFTER_OR_EQUAL, bordDateStart)
                .ifNotNullThenAnd("bordDate", DATE_BEFORE_OR_EQUAL, bordDateEnd)
                .build()
        );
    }

    public Resolution save(Resolution resolution) {
        return resolutionRepository.saveAndFlush(resolution);
    }

    public Resolution findById(String id) {
        if (id.matches("\\d+")) {
            return resolutionRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
