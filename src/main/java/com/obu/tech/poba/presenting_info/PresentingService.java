package com.obu.tech.poba.presenting_info;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class PresentingService {
    @Autowired PresentingRepository presentingRepository;

    List<Presenting> findBySearchCriteria(Presenting presenting){
        return presentingRepository.findAll(new SearchConditionBuilder<Presenting>()
                .ifNotNullThenAnd("name", LIKE, presenting.getName())
                .ifNotNullThenOr("surname", LIKE, presenting.getName())
                .ifNotNullThenAnd("presentLevel", LIKE, presenting.getPresentLevel())
                .build()
        );
    }

    public Presenting save(Presenting presenting) {
        return presentingRepository.saveAndFlush(presenting);
    }

    public Presenting findById(String id) {
        if (id.matches("\\d+")) {
            return presentingRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
