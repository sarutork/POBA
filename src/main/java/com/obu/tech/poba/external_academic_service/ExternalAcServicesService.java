package com.obu.tech.poba.external_academic_service;

import com.obu.tech.poba.presenting_info.Presenting;
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
public class ExternalAcServicesService {
    @Autowired
    ExternalAcServicesRepository externalAcServicesRepository;

    List<ExternalAcServices> findBySearchCriteria(ExternalAcServices extAcServices){
        List<Object[]> data = externalAcServicesRepository.findInfo("%"+extAcServices.getName()+"%",
                extAcServices.getLevel(),
                extAcServices.getStartDate(),
                extAcServices.getEndDate());

        List<ExternalAcServices> externalAcServices = new ArrayList<>();
        if (!data.isEmpty() && data.size() >0){
            for(final Object[] e : data){
                final ExternalAcServices result = new ExternalAcServices();
                result.setId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName(e[3].toString()+" "+e[4].toString());
                if (e[5] != null ) {
                    result.setTitle(e[5].toString());
                }

                if(e[6] != null){
                    result.setLevel(e[6].toString());
                }

                if(e[7] != null){
                    result.setInstitution(e[7].toString());
                }

                if(e[8] != null){
                    result.setYear(e[8].toString());
                }

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
