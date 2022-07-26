package com.obu.tech.poba.personnel_info.study;

import com.obu.tech.poba.personnel_info.research.Researcher;
import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.*;

@Service
public class StudyInfoService {
    @Autowired StudyInfoRepository studyInfoRepository;

    public List<StudyInfo> findBySearchCriteria(StudyInfo studyInfo){

        List<Object[]> data = studyInfoRepository.findInfo("%"+studyInfo.getName()+"%",studyInfo.getStartDate(),studyInfo.getEndDate());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<StudyInfo> studyInfos = new ArrayList<>();
        if (!data.isEmpty() && data.size() >0){
            for(final Object[] e : data){
                final StudyInfo result = new StudyInfo();
                result.setStaffId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName(e[3].toString()+" "+e[4].toString());
                if (e[5] != null ) {
                    result.setLocation(e[5].toString());
                }

                if(e[6] != null){
                    result.setCountry(e[6].toString());
                }

                if(e[7] != null){
                    result.setStartDate(LocalDate.parse(e[7].toString(),formatter));
                }
                if(e[8] != null){
                    result.setEndDate(LocalDate.parse(e[8].toString(),formatter));
                }

                studyInfos.add(result);
            }
        }
        return studyInfos;
    }

    public StudyInfo save(StudyInfo studyInfo) {
        return studyInfoRepository.saveAndFlush(studyInfo);
    }

    public StudyInfo findById(String id) {
        if (id.matches("\\d+")) {
            return studyInfoRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            System.out.println("Invalid staff_id: '" + id + "'");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

}
