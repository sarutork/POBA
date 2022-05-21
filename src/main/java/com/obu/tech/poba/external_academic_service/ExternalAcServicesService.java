package com.obu.tech.poba.external_academic_service;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;
import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class ExternalAcServicesService {
    @Autowired
    ExternalAcServicesRepository externalAcServicesRepository;

    List<ExternalAcServices> findBySearchCriteria(ExternalAcServices externalAcServices){
        return externalAcServicesRepository.findAll(new SearchConditionBuilder<ExternalAcServices>()
                .ifNotNullThenAnd("name", LIKE, externalAcServices.getName())
                .ifNotNullThenOr("surname", LIKE, externalAcServices.getName())
                .ifNotNullThenAnd("year", LIKE, externalAcServices.getYear())
                .build()
        );
    }

    public ExternalAcServices save(ExternalAcServices externalAcServices) {
        return externalAcServicesRepository.saveAndFlush(externalAcServices);
    }

    public ExternalAcServices findById(String id) {
        if (id.matches("\\d+")) {
            return externalAcServicesRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
