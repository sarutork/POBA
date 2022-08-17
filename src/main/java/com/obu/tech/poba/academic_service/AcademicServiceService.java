package com.obu.tech.poba.academic_service;

import com.obu.tech.poba.presenting_info.Presenting;
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
public class AcademicServiceService {
    @Autowired AcdemicServiceRepository acdemicServiceRepository;
    @Autowired CommonUtils commonUtils;
    List<AcademicService> findBySearchCriteria(AcademicService academicService){
        List<Object[]> data = acdemicServiceRepository.findInfo("%"+academicService.getName()+"%",academicService.getServiceLevel());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<AcademicService> academicServices = new ArrayList<>();
        if (!data.isEmpty() && data.size() >0){
            for(final Object[] e : data){
                final AcademicService result = new AcademicService();
                result.setServiceId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName(e[3].toString()+" "+e[4].toString());
                result.setServiceStatus(commonUtils.chkNullStrObj(e[5]));
                result.setServiceLevel(commonUtils.chkNullStrObj(e[6]));
                result.setServiceOrder(commonUtils.chkNullStrObj(e[7]));

                if(e[8] != null) {
                    result.setServiceDateFrom(LocalDate.parse(commonUtils.chkNullStrObj(e[8]),formatter));
                }

                if(e[9] != null) {
                    result.setServiceDateTo(LocalDate.parse(commonUtils.chkNullStrObj(e[9]),formatter));
                }

                result.setNoteOfService(commonUtils.chkNullStrObj(e[10]));
                result.setServicePosition(commonUtils.chkNullStrObj(e[11]));
                result.setServiceInstitution(commonUtils.chkNullStrObj(e[12]));

                academicServices.add(result);
            }
        }
        return academicServices;
    }

    public AcademicService save(AcademicService academicService) {
        return acdemicServiceRepository.saveAndFlush(academicService);
    }

    public AcademicService findById(String id) {
        if (id.matches("\\d+")) {
            return acdemicServiceRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
