package com.obu.tech.poba.consultant_info.thesis;

import com.obu.tech.poba.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultantThesisService {

    @Autowired
    ConsultantThesisRepository consultantThesisRepository;

    @Autowired
    CommonUtils commonUtils;

    List<ConsultantThesis> findBySearchCriteria(ConsultantThesis consultantThesis){

        List<Object[]> dataList = consultantThesisRepository.findConsultantThesisInfo("%"+consultantThesis.getName()+"%",
                consultantThesis.getThesisType(),
                consultantThesis.getStudentsLevel());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<ConsultantThesis> resData = new ArrayList<>();
        if (!dataList.isEmpty() && dataList.size() >0){
            for(final Object[] e : dataList){
                final ConsultantThesis result = new ConsultantThesis();
                result.setThesisId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : commonUtils.chkNullStrObj(e[2]));
                result.setName(commonUtils.chkNullStrObj(e[3])+" "+commonUtils.chkNullStrObj(e[4]));
                result.setThesisType(commonUtils.chkNullStrObj(e[5]));
                result.setStudentPrefix( !e[6].toString().equals("อื่นๆ")? e[6].toString() : commonUtils.chkNullStrObj(e[7]));
                result.setStudentName(commonUtils.chkNullStrObj(e[8])+" "+commonUtils.chkNullStrObj(e[9]));
                result.setStudentsLevel(commonUtils.chkNullStrObj(e[10]));
                result.setThesisAssessment(commonUtils.chkNullStrObj(e[11]));

                if(e[12] != null) {
                    result.setThesisStartdate(LocalDate.parse(commonUtils.chkNullStrObj(e[12])));

                }
                if(e[13] != null) {
                    result.setThesisSuccessDate(LocalDate.parse(commonUtils.chkNullStrObj(e[13])));

                }

                resData.add(result);
            }
        }
       return resData;
    }

    public ConsultantThesis save(ConsultantThesis consultantThesis) {
        return consultantThesisRepository.saveAndFlush(consultantThesis);
    }

    public ConsultantThesis findById(String id) {
        if (id.matches("\\d+")) {
            return consultantThesisRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
