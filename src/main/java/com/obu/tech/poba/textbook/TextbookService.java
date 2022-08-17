package com.obu.tech.poba.textbook;

import com.obu.tech.poba.presenting_info.Presenting;
import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class TextbookService {
    @Autowired TextbookRepository textbookRepository ;
    @Autowired TextbookPhaseRepository textbookPhaseRepository;

    List<Textbook> findBySearchCriteria(Textbook textbook){
        List<Object[]> data = textbookRepository.findTextbookInfo("%"+textbook.getName()+"%",
                textbook.getTextbookLevel(),
                textbook.getTextbookType(),
                textbook.getTextbookDateFrom(),
                textbook.getTextbookDateTo());

        List<Textbook> textbookList = new ArrayList<>();
        if (!data.isEmpty() && data.size() >0){
            for(final Object[] e : data){
                final Textbook result = new Textbook();
                result.setTextbookId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName(e[3].toString()+" "+e[4].toString());
                if (e[5] != null ) {
                    result.setTextbookType(e[5].toString());
                }

                if(e[6] != null){
                    result.setTextbookTopic(e[6].toString());
                }
                if(e[7] != null){
                    result.setTextbookLevel(e[7].toString());
                }

                textbookList.add(result);
            }
        }
        return textbookList;
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
