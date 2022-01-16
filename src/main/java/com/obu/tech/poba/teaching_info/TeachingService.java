package com.obu.tech.poba.teaching_info;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class TeachingService {
    @Autowired TeachingRepository teachingRepository;
    List<Teaching> findBySearchCriteria(Teaching teaching){
        return teachingRepository.findAll(new SearchConditionBuilder<Teaching>()
                .ifNotNullThenAnd("name", LIKE, teaching.getName())
                .build()
        );
    }
}
