package com.obu.tech.poba.external_academic_service;

import com.obu.tech.poba.presenting_info.Presenting;
import com.obu.tech.poba.press_info.Press;
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
public class ExternalAcServicesService {
    @Autowired
    ExternalAcServicesRepository externalAcServicesRepository;

    @Autowired
    CommonUtils commonUtils;

    List<ExternalAcServices> findBySearchCriteria(ExternalAcServices extAcServices){
        List<Object[]> data = externalAcServicesRepository.findInfo("%"+extAcServices.getName()+"%",
                extAcServices.getLevel(),
                extAcServices.getStartDate(),
                extAcServices.getEndDate());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        List<ExternalAcServices> externalAcServices = new ArrayList<>();
        if (!data.isEmpty() && data.size() >0){
            for(final Object[] e : data){
                final ExternalAcServices result = new ExternalAcServices();
                result.setId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName(e[3].toString()+" "+e[4].toString());
                result.setTitle(commonUtils.chkNullStrObj(e[5]));
                result.setType(commonUtils.chkNullStrObj(e[6]));
                result.setTypeOther(commonUtils.chkNullStrObj(e[7]));
                result.setLevel(commonUtils.chkNullStrObj(e[8]));
                result.setInstitution(commonUtils.chkNullStrObj(e[9]));
                result.setYear(commonUtils.chkNullStrObj(e[10]));
                if(e[11] != null){
                    result.setStartDate(LocalDate.parse(e[11].toString(),formatter));
                }
                result.setStartTime(commonUtils.chkNullStrObj(e[12]));
                if(e[13] != null){
                    result.setEndDate(LocalDate.parse(e[13].toString(),formatter));
                }
                result.setEndTime(commonUtils.chkNullStrObj(e[14]));
                result.setLocation(commonUtils.chkNullStrObj(e[15]));
                if(e[16] != null ) {
                    result.setNumOfParticipants(Integer.parseInt(e[16].toString()));
                }
                if(e[17] != null) {
                    result.setLetterReceivedDate(LocalDate.parse(e[17].toString(),formatter));
                }
                result.setLetterNo(commonUtils.chkNullStrObj(e[18]));
                if(e[19] != null) {
                    result.setLetterSentDate(LocalDate.parse(e[19].toString(),formatter));
                }
                result.setLetterSendingNo(commonUtils.chkNullStrObj(e[20]));
                result.setCoordinator(commonUtils.chkNullStrObj(e[21]));
                result.setCoordinatorTel(commonUtils.chkNullStrObj(e[22]));
                result.setConfirmationNo(commonUtils.chkNullStrObj(e[23]));
                result.setNote(commonUtils.chkNullStrObj(e[24]));

                externalAcServices.add(result);
            }
        }
        return externalAcServices;
    }

    public ExternalAcServices save(ExternalAcServices externalAcServices) {
        return externalAcServicesRepository.saveAndFlush(externalAcServices);
    }

    public ExternalAcServices findById(String id) {
        if (id.matches("\\d+")) {
            return externalAcServicesRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
