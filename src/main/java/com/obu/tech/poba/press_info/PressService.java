package com.obu.tech.poba.press_info;

import com.obu.tech.poba.presenting_info.Presenting;
import com.obu.tech.poba.presenting_info.PresentingRepository;
import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.DATE_AFTER_OR_EQUAL;
import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class PressService {
    @Autowired
    PressRepository pressRepository;

    List<Press> findBySearchCriteria(Press press){
        return pressRepository.findAll(new SearchConditionBuilder<Press>()
                .ifNotNullThenAnd("name", LIKE, press.getName())
                .ifNotNullThenOr("surname", LIKE, press.getName())
                .ifNotNullThenAnd("pressDate", DATE_AFTER_OR_EQUAL, press.getPressDate())
                .build()
        );
    }

    public Press save(Press press) {
        return pressRepository.saveAndFlush(press);
    }

    public Press findById(String id) {
        if (id.matches("\\d+")) {
            return pressRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
