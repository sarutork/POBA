package com.obu.tech.poba.textbook;

import com.obu.tech.poba.presenting_info.Presenting;
import com.obu.tech.poba.utils.CommonUtils;
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
public class TextbookService {
    @Autowired TextbookRepository textbookRepository ;
    @Autowired TextbookPhaseRepository textbookPhaseRepository;
    @Autowired
    CommonUtils commonUtils;

    List<Textbook> findBySearchCriteria(Textbook textbook){
        List<Object[]> data = textbookRepository.findTextbookInfo("%"+textbook.getName()+"%",
                textbook.getTextbookLevel(),
                textbook.getTextbookType(),
                textbook.getTextbookDateFrom(),
                textbook.getTextbookDateTo());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Textbook> textbookList = new ArrayList<>();
        if (!data.isEmpty() && data.size() >0){
            for(final Object[] e : data){
                final Textbook result = new Textbook();
                result.setTextbookId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName(e[3].toString()+" "+e[4].toString());
                result.setTextbookType(commonUtils.chkNullStrObj(e[5]));
                result.setTextbookAnnounce(commonUtils.chkNullStrObj(e[6]));
                result.setTextbookContract(commonUtils.chkNullStrObj(e[7]));
                result.setTextbookTopic(commonUtils.chkNullStrObj(e[8]));
                result.setTextbookFund(commonUtils.chkNullStrObj(e[9]));
                if(e[10] != null) {
                    result.setTextbookAmountTotal(Double.parseDouble(e[10].toString()));
                }
                if(e[11] != null){
                    result.setTextbookDateFrom(LocalDate.parse(e[11].toString(),formatter));
                }
                if(e[12] != null){
                    result.setTextbookDateTo(LocalDate.parse(e[12].toString(),formatter));
                }
                if(e[13] != null) {
                    result.setTextbookExtendDate(LocalDate.parse(e[13].toString(),formatter));
                }
                if(e[14] != null){
                    result.setTextbookExtendDate2(LocalDate.parse(e[14].toString(),formatter));
                }
                if(e[15] != null){
                    result.setTextbookExtendDate3(LocalDate.parse(e[15].toString(),formatter));
                }
                result.setTextbookStatus(commonUtils.chkNullStrObj(e[16]));
                result.setTextbookPbType(commonUtils.chkNullStrObj(e[17]));
                result.setTextbookIssue(commonUtils.chkNullStrObj(e[18]));
                result.setTextbookYear(commonUtils.chkNullStrObj(e[19]));
                result.setTextbookInstitution(commonUtils.chkNullStrObj(e[20]));
                result.setTextbookDiffText(commonUtils.chkNullStrObj(e[21]));
                result.setTextbookRef(commonUtils.chkNullStrObj(e[22]));
                result.setTextbookTCI(commonUtils.chkNullStrObj(e[23]));
                result.setTextbookLevel(commonUtils.chkNullStrObj(e[24]));
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
