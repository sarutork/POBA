package com.obu.tech.poba.teaching_info;

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
public class TeachingService {
    @Autowired TeachingRepository teachingRepository;
    @Autowired
    CommonUtils commonUtils;
    List<Teaching> findBySearchCriteria(Teaching teaching){
        List<Object[]> data = teachingRepository.findInfo("%"+teaching.getName()+"%",teaching.getStudyYear(),"%"+teaching.getSemester()+"%");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Teaching> teachings = new ArrayList<>();
        if (!data.isEmpty() && data.size() >0){
            for(final Object[] e : data){
                final Teaching result = new Teaching();
                result.setStaffId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName(e[3].toString()+" "+e[4].toString());
                result.setTeachStatus(commonUtils.chkNullStrObj(e[5]));
                result.setInstitutionInfo(commonUtils.chkNullStrObj(e[6]));
                result.setTeachTopic(commonUtils.chkNullStrObj(e[7]));
                result.setTeachTimes(e[8] != null? Integer.parseInt(commonUtils.chkNullStrObj(e[8])):0 );
                result.setTeachDate(e[9]!=null?LocalDate.parse(commonUtils.chkNullStrObj(e[9]),formatter): null);
                result.setNoteOfTeach(commonUtils.chkNullStrObj(e[10]));
                result.setStudyType(commonUtils.chkNullStrObj(e[11]));
                result.setSemester(commonUtils.chkNullStrObj(e[12]));
                result.setStudyYear(commonUtils.chkNullStrObj(e[13]));
                result.setSubjectId(e[14]!=null? Integer.parseInt(commonUtils.chkNullStrObj(e[14])):0);
                result.setSubjectName(commonUtils.chkNullStrObj(e[15]));
                result.setSubjectCredit(e[16]!=null? Integer.parseInt(commonUtils.chkNullStrObj(e[16])):0);
                result.setCurrentCredit(e[17]!=null? Integer.parseInt(commonUtils.chkNullStrObj(e[17])):0);
                result.setTeachType(commonUtils.chkNullStrObj(e[18]));
                result.setTeachDay(commonUtils.chkNullStrObj(e[19]));
                result.setStudyStart(commonUtils.chkNullStrObj(e[20]));
                result.setStudyEnd(commonUtils.chkNullStrObj(e[21]));
                result.setTeachLocation(!"อื่นๆ".equals(commonUtils.chkNullStrObj(e[22]))? commonUtils.chkNullStrObj(e[22]): commonUtils.chkNullStrObj(e[23]));
                result.setTeachRoom(commonUtils.chkNullStrObj(e[24]));
                result.setTeachStyle(commonUtils.chkNullStrObj(e[25]));
                result.setTeachStyleDetail(commonUtils.chkNullStrObj(e[26]));
                result.setTotalOfStudents(commonUtils.chkNullStrObj(e[27]));
                result.setTotalStudentsRegister(commonUtils.chkNullStrObj(e[28]));
                result.setMidtermExamDate(e[29]!=null? LocalDate.parse(e[29].toString(),formatter):null);
                result.setMidtermExamTimeStart(commonUtils.chkNullStrObj(e[30]));
                result.setMidtermExamTimeEnd(commonUtils.chkNullStrObj(e[31]));
                result.setFinalExamDate(e[32]!=null? LocalDate.parse(e[32].toString(),formatter):null);
                result.setFinalExamTimeStart(commonUtils.chkNullStrObj(e[33]));
                result.setFinalExamTimeEnd(commonUtils.chkNullStrObj(e[34]));
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
