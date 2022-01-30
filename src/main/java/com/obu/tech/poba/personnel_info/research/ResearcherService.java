package com.obu.tech.poba.personnel_info.research;

import com.obu.tech.poba.utils.exceptions.NotFoundException;
import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.*;

@Slf4j
@Service
public class ResearcherService {

    @Autowired
    private ResearcherRepository researcherRepository;

    public List<Researcher> search(Researcher researcher) {
        return researcherRepository
                .findAll(new SearchConditionBuilder<Researcher>()
                        .ifNotNullThenAnd("name", LIKE, researcher.getName())
                        .ifNotNullThenOr("surname", LIKE, researcher.getName())
                        .ifNotNullThenAnd("workStartDate", DATE_AFTER_OR_EQUAL, researcher.getWorkStartDate())
                        .ifNotNullThenAnd("workEndDate", DATE_BEFORE_OR_EQUAL, researcher.getWorkEndDate())
                        .build());
    }

    public List<Researcher> findAll() {
        return researcherRepository.findAll();
    }

    public Researcher findById(String id) {
        if (id.matches("\\d+")) {
            return researcherRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new NotFoundException("Not found Researcher.staffId [" + id + "]"));
        } else {
            throw new NotFoundException("Invalid Researcher.staffId [" + id + "]");
        }
    }

    public Researcher save(Researcher researcher) {
        String fullName = researcher.getName().replaceAll("\\s+", " ").trim();
        int firstSpace = fullName.contains(" ") ? fullName.indexOf(" ") : fullName.length();
        researcher.setName(fullName.substring(0, firstSpace));
        researcher.setSurname(fullName.substring(firstSpace).trim());
        log.info("Saving " + researcher);
        return researcherRepository.saveAndFlush(researcher);
    }

    public Researcher update(String id, Researcher updateData) throws NotFoundException {
        Researcher researcher = findById(id);
        researcher.setPrefix        (updateData.getPrefix());
        researcher.setName          (updateData.getName()); // full name
        researcher.setStatus        (updateData.getStatus());
        researcher.setType          (updateData.getType());
        researcher.setWorkStartDate (updateData.getWorkStartDate());
        researcher.setWorkEndDate   (updateData.getWorkEndDate());
        researcher.setNoteOfWork    (updateData.getNoteOfWork());
        researcher.setDocOfWork     (updateData.getDocOfWork()); // TODO: join filenames "file1.pdf;file2.pdf;file3.pdf"
        // TODO: upload files to server
        return save(researcher);
    }
}
