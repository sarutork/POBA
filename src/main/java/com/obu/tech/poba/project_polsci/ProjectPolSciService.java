package com.obu.tech.poba.project_polsci;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.DATE_AFTER_OR_EQUAL;
import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class ProjectPolSciService {
    @Autowired
    ProjectPolSciRepository projectPolSciRepository;
    List<ProjectPolSci> findBySearchCriteria(ProjectPolSci projectPolSci){
        return projectPolSciRepository.findAll(new SearchConditionBuilder<ProjectPolSci>()
                .ifNotNullThenAnd("name", LIKE, projectPolSci.getName())
                .ifNotNullThenOr("surname", LIKE, projectPolSci.getName())
                .ifNotNullThenAnd("polsciYear", LIKE, projectPolSci.getPolsciYear())
                .ifNotNullThenAnd("polsciDateFrom", DATE_AFTER_OR_EQUAL, projectPolSci.getPolsciDateFrom())
                .build()
        );
    }

    public ProjectPolSci save(ProjectPolSci projectPolSci) {
        return projectPolSciRepository.saveAndFlush(projectPolSci);
    }

    public ProjectPolSci findById(String id) {
        if (id.matches("\\d+")) {
            return projectPolSciRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
