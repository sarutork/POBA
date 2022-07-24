package com.obu.tech.poba.teaching_info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeachingService {
    @Autowired TeachingRepository teachingRepository;
    List<Teaching> findBySearchCriteria(Teaching teaching){
        List<Object[]> data = teachingRepository.findInfo("%"+teaching.getName()+"%",teaching.getStudyYear(),"%"+teaching.getSemester()+"%");

        List<Teaching> teachings = new ArrayList<>();
        if (!data.isEmpty() && data.size() >0){
            for(final Object[] e : data){
                final Teaching result = new Teaching();
                result.setStaffId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName(e[3].toString()+" "+e[4].toString());
                if (e[5] != null ) {
                    result.setStudyYear(e[5].toString());
                }

                if(e[6] != null){
                    result.setSemester(e[6].toString());
                }

                if(e[7] != null){
                    result.setSubjectName(e[7].toString());
                }

                if(e[8] != null){
                    result.setStudyType(e[8].toString());
                }

                teachings.add(result);
            }
        }
        return teachings;
    }

    public Teaching save(Teaching teaching) {
        return teachingRepository.saveAndFlush(teaching);
    }

    public Teaching findById(String id) {
        if (id.matches("\\d+")) {
            return teachingRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            System.out.println("Invalid staff_id: '" + id + "'");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

}
