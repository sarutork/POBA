package com.obu.tech.poba.personnel_info.study;

import com.obu.tech.poba.personnel_info.research.Researcher;
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

import static com.obu.tech.poba.utils.search.SearchOperator.*;

@Service
public class StudyInfoService {
    @Autowired StudyInfoRepository studyInfoRepository;

    @Autowired CommonUtils commonUtils;

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
                result.setTravelOrder(commonUtils.chkNullStrObj(e[5]));
                if(e[6] != null) {
                    result.setStartDate(LocalDate.parse(commonUtils.chkNullStrObj(e[6]),formatter));
                }
                if(e[7] != null) {
                    result.setEndDate(LocalDate.parse(commonUtils.chkNullStrObj(e[7]),formatter));
                }
                result.setTotalYear(Integer.parseInt(commonUtils.chkNullStrObj(e[8])));
                result.setTotalMonth(Integer.parseInt(commonUtils.chkNullStrObj(e[9])));
                result.setTotalDay(Integer.parseInt(commonUtils.chkNullStrObj(e[10])));
                result.setActivityDetail(commonUtils.chkNullStrObj(e[11]));
                result.setLocation(commonUtils.chkNullStrObj(e[12]));
                result.setLocationType(commonUtils.chkNullStrObj(e[13]));
                result.setCountry(commonUtils.chkNullStrObj(e[14]));
                result.setFund(commonUtils.chkNullStrObj(e[15]));
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
