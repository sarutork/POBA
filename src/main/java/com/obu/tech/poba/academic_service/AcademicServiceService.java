package com.obu.tech.poba.academic_service;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class AcademicServiceService {
    @Autowired AcdemicServiceRepository acdemicServiceRepository;
    List<AcademicService> findBySearchCriteria(AcademicService academicService){
        return acdemicServiceRepository.findAll(new SearchConditionBuilder<AcademicService>()
                .ifNotNullThenAnd("name", LIKE, academicService.getName())
                .ifNotNullThenOr("surname", LIKE, academicService.getName())
                .ifNotNullThenAnd("serviceLevel", LIKE, academicService.getServiceLevel())
                .build()
        );
    }

    public AcademicService save(AcademicService academicService) {
        return acdemicServiceRepository.saveAndFlush(academicService);
    }

    public AcademicService findById(String id) {
        if (id.matches("\\d+")) {
            return acdemicServiceRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
