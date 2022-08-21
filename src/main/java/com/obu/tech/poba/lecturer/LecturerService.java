package com.obu.tech.poba.lecturer;

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
public class LecturerService {
    @Autowired
    LecturerRepository lecturerRepository;
    @Autowired
    CommonUtils commonUtils;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    List<Lecturer> findBySearchCriteria(Lecturer lecturer){
        List<Object[]> data = lecturerRepository.findInfo("%"+lecturer.getName()+"%",lecturer.getStudyYear(),"%"+lecturer.getSemester()+"%");

        List<Lecturer> lecturers = new ArrayList<>();
        if (!data.isEmpty() && data.size() >0){
            for(final Object[] e : data){
                final Lecturer result = new Lecturer();
                result.setLecturerId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName(e[3].toString()+" "+e[4].toString());
                result.setInstitutionInfo(commonUtils.chkNullStrObj(e[5]));
                result.setTeachTopic(commonUtils.chkNullStrObj(e[6]));
                result.setTeachTimes(e[7] != null? Integer.parseInt(commonUtils.chkNullStrObj(e[7])):0 );
                result.setTeachDate(e[8]!=null? LocalDate.parse(commonUtils.chkNullStrObj(e[8]),formatter): null);
                result.setNoteOfTeach(commonUtils.chkNullStrObj(e[9]));
                result.setStudyType(commonUtils.chkNullStrObj(e[10]));
                result.setSemester(commonUtils.chkNullStrObj(e[11]));
                result.setStudyYear(commonUtils.chkNullStrObj(e[12]));
                result.setSubjectId(e[13]!=null? Integer.parseInt(commonUtils.chkNullStrObj(e[13])):0);
                result.setSubjectName(commonUtils.chkNullStrObj(e[14]));
                result.setSubjectCredit(e[15]!=null? Integer.parseInt(commonUtils.chkNullStrObj(e[15])):0);
                result.setCurrentCredit(e[16]!=null? Integer.parseInt(commonUtils.chkNullStrObj(e[16])):0);
                result.setTeachType(commonUtils.chkNullStrObj(e[17]));
                result.setTeachDay(commonUtils.chkNullStrObj(e[18]));
                result.setStudyStart(commonUtils.chkNullStrObj(e[19]));
                result.setStudyEnd(commonUtils.chkNullStrObj(e[20]));
                result.setTeachLocation(!"อื่นๆ".equals(commonUtils.chkNullStrObj(e[21]))? commonUtils.chkNullStrObj(e[21]): commonUtils.chkNullStrObj(e[22]));
                result.setTeachRoom(commonUtils.chkNullStrObj(e[23]));
                result.setTeachStyle(commonUtils.chkNullStrObj(e[24]));
                result.setTeachStyleDetail(!"อื่นๆ".equals(commonUtils.chkNullStrObj(e[25]))? commonUtils.chkNullStrObj(e[25]): commonUtils.chkNullStrObj(e[26]));
                result.setTotalOfStudents(commonUtils.chkNullStrObj(e[27]));
                result.setTotalStudentsRegister(commonUtils.chkNullStrObj(e[28]));

                lecturers.add(result);
            }
        }
        return lecturers;
    }

    public Lecturer save(Lecturer lecturer) {
        return lecturerRepository.saveAndFlush(lecturer);
    }

    public Lecturer findById(String id) {
        if (id.matches("\\d+")) {
            return lecturerRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
