package com.obu.tech.poba.presenting_info;

import com.obu.tech.poba.press_info.Press;
import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class PresentingService {
    @Autowired PresentingRepository presentingRepository;

    List<Presenting> findBySearchCriteria(Presenting presenting){
        List<Object[]> data = presentingRepository.findPresentInfo("%"+presenting.getName()+"%"
                ,presenting.getPresentLevel()
                ,presenting.getPresentDateStart()
                ,presenting.getPresentDateEnd());

        List<Presenting> presentingList = new ArrayList<>();
        if (!data.isEmpty() && data.size() >0){
            for(final Object[] e : data){
                final Presenting result = new Presenting();
                result.setPresentId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName(e[3].toString()+" "+e[4].toString());
                if (e[5] != null ) {
                    result.setPresentTopic(e[5].toString());
                }

                if(e[6] != null){
                    result.setPresentLevel(e[6].toString());
                }

                presentingList.add(result);
            }
        }
        return presentingList;
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
