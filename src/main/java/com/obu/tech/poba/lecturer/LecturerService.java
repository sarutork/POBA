package com.obu.tech.poba.lecturer;

import com.obu.tech.poba.presenting_info.Presenting;
import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class LecturerService {
    @Autowired
    LecturerRepository lecturerRepository;
    List<Lecturer> findBySearchCriteria(Lecturer lecturer){
        List<Object[]> data = lecturerRepository.findInfo("%"+lecturer.getName()+"%",lecturer.getStudyYear(),"%"+lecturer.getSemester()+"%");

        List<Lecturer> lecturers = new ArrayList<>();
        if (!data.isEmpty() && data.size() >0){
            for(final Object[] e : data){
                final Lecturer result = new Lecturer();
                result.setLecturerId(Long.parseLong(e[0].toString()));
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
