package com.obu.tech.poba.textbook;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class TextbookService {
    @Autowired TextbookRepository textbookRepository ;
    @Autowired TextbookPhaseRepository textbookPhaseRepository;

    List<Textbook> findBySearchCriteria(Textbook textbook){
        return textbookRepository.findAll(new SearchConditionBuilder<Textbook>()
                .ifNotNullThenAnd("name", LIKE, textbook.getName())
                .ifNotNullThenOr("surname", LIKE, textbook.getName())
                .ifNotNullThenAnd("textbookLevel", LIKE, textbook.getTextbookLevel())
                .ifNotNullThenAnd("textbookType", LIKE, textbook.getTextbookType())
                .build()
        );
    }

    public Textbook findById(String id) {
        if (id.matches("\\d+")) {
            return textbookRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            System.out.println("Invalid textbookid: '" + id + "'");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public Textbook save(Textbook textbook) {

        return textbookRepository.saveAndFlush(textbook);
    }

    public List<TextbookPhase> saveTextbookPhase(List<TextbookPhase> textbookPhase){
        return  textbookPhaseRepository.saveAllAndFlush(textbookPhase);
    }

    public List<TextbookPhase> findByTextbookId(String id){
        if (id.matches("\\d+")) {
            return textbookPhaseRepository.findByTextbookId(Long.parseLong(id));
        } else {
            System.out.println("Invalid textbook_id: '" + id + "'");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public List<TextbookPhase> removePhase(long phaseId,long textbookId){
        textbookPhaseRepository.deleteById(phaseId);
        return textbookPhaseRepository.findByTextbookId(textbookId);
    }
}