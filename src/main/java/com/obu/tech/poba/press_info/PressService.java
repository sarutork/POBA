package com.obu.tech.poba.press_info;

import com.obu.tech.poba.consultant_info.students.ConsultantDto;
import com.obu.tech.poba.presenting_info.Presenting;
import com.obu.tech.poba.presenting_info.PresentingRepository;
import com.obu.tech.poba.utils.CommonUtils;
import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.DATE_AFTER_OR_EQUAL;
import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class PressService {
    @Autowired
    PressRepository pressRepository;

    @Autowired
    CommonUtils commonUtils;

    List<Press> findBySearchCriteria(Press press){
        List<Object[]> preesList = pressRepository.findPressInfo("%"+press.getName()+"%",press.getPressDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Press> presses = new ArrayList<>();
        if (!preesList.isEmpty() && preesList.size() >0){
            for(final Object[] e : preesList){
                final Press result = new Press();
                result.setPressId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName(e[3].toString()+" "+e[4].toString());
                result.setPressName(commonUtils.chkNullStrObj(e[5]));

                if (e[6] != null ) {
                    result.setPressDate(LocalDate.parse(e[6].toString(), formatter));
                }

                result.setPressTopic(commonUtils.chkNullStrObj(e[7]));
                result.setPressSiteRef(commonUtils.chkNullStrObj(e[8]));
                result.setPressPrint(commonUtils.chkNullStrObj(e[9]));
                result.setPressTv(commonUtils.chkNullStrObj(e[10]));
                result.setPressSatellite(commonUtils.chkNullStrObj(e[11]));
                result.setPressOnline(commonUtils.chkNullStrObj(e[12]));
                result.setGuestPrefix1(!commonUtils.chkNullStrObj(e[13]).equals("อื่นๆ")? commonUtils.chkNullStrObj(e[13]) : e[14].toString());
                result.setGuestName1(commonUtils.chkNullStrObj(e[15])+" "+ commonUtils.chkNullStrObj(e[16]));
                result.setGuestPrefix2(!commonUtils.chkNullStrObj(e[17]).equals("อื่นๆ")? commonUtils.chkNullStrObj(e[17]) : e[18].toString());
                result.setGuestName2(commonUtils.chkNullStrObj(e[19])+" "+commonUtils.chkNullStrObj(e[20]));
                result.setGuestPrefix3(!commonUtils.chkNullStrObj(e[21]).equals("อื่นๆ")? commonUtils.chkNullStrObj(e[21]) : e[22].toString());
                result.setGuestName3(commonUtils.chkNullStrObj(e[23])+" "+commonUtils.chkNullStrObj(e[24]));
                presses.add(result);
            }
        }
        return presses;
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
