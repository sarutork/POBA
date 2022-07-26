package com.obu.tech.poba.academic_service;

import com.obu.tech.poba.presenting_info.Presenting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AcademicServiceService {
    @Autowired AcdemicServiceRepository acdemicServiceRepository;
    List<AcademicService> findBySearchCriteria(AcademicService academicService){
        List<Object[]> data = acdemicServiceRepository.findInfo("%"+academicService.getName()+"%",academicService.getServiceLevel());

        List<AcademicService> academicServices = new ArrayList<>();
        if (!data.isEmpty() && data.size() >0){
            for(final Object[] e : data){
                final AcademicService result = new AcademicService();
                result.setServiceId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName(e[3].toString()+" "+e[4].toString());
                if (e[5] != null ) {
                    result.setServiceStatus(e[5].toString());
                }

                if(e[6] != null){
                    result.setServiceLevel(e[6].toString());
                }
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
