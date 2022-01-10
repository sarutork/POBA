package com.obu.tech.poba.personnel_info.research;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.*;

@Service
public class ResearcherService {

    @Autowired
    private ResearcherRepository researcherRepository;

    public List<Researcher> search(String name, LocalDate workStartDate, LocalDate workEndDate) {
        return researcherRepository
                .findAll(new SearchConditionBuilder<Researcher>()
                        .ifNotNullThenAnd("name", LIKE, name)
                        .ifNotNullThenOr("surname", LIKE, name)
                        .ifNotNullThenAnd("workStartDate", DATE_AFTER_OR_EQUAL, workStartDate)
                        .ifNotNullThenAnd("workEndDate", DATE_BEFORE_OR_EQUAL, workEndDate)
                        .build());
    }

    public List<Researcher> findAll() {
        return researcherRepository.findAll();
    }

    public Researcher findById(String id) {
        if (id.matches("\\d+")) {
            return researcherRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            System.out.println("Invalid researcher.staff_id: '" + id + "'");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public Researcher save(Researcher researcher) {
        return researcherRepository.saveAndFlush(researcher);
    }
}
