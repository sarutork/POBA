package com.obu.tech.poba.training;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class TrainingService {
    @Autowired
    TrainingRepository trainingRepository;
    List<Training> findBySearchCriteria(Training training){
        return trainingRepository.findAll(new SearchConditionBuilder<Training>()
                .ifNotNullThenAnd("name", LIKE, training.getName())
                .ifNotNullThenOr("surname", LIKE, training.getName())
                .ifNotNullThenAnd("trainingLevel", LIKE, training.getTrainingLevel())
                .build()
        );
    }

    public Training save(Training training) {
        return trainingRepository.saveAndFlush(training);
    }

    public Training findById(String id) {
        if (id.matches("\\d+")) {
            return trainingRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
