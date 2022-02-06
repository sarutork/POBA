package com.obu.tech.poba.consultant_info.students;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static com.obu.tech.poba.utils.search.SearchOperator.LIKE;

@Service
public class ConsultantStudentService {
    @Autowired
    ConsultantStudentRepository consultantStudentRepository;
   public List<ConsultantStudent> findBySearchCriteria(ConsultantStudent consultantStudent){
        return consultantStudentRepository.findAll(new SearchConditionBuilder<ConsultantStudent>()
                .ifNotNullThenAnd("name", LIKE, consultantStudent.getName())
                .ifNotNullThenOr("surname", LIKE, consultantStudent.getName())
                .ifNotNullThenOr("studentName", LIKE, consultantStudent.getName())
                .ifNotNullThenOr("studentSurname", LIKE, consultantStudent.getName())
                .ifNotNullThenAnd("yearOfStudy", LIKE, consultantStudent.getYearOfStudy())
                .ifNotNullThenAnd("studentsLevel", LIKE, consultantStudent.getStudentsLevel())
                .build()
        );
    }

   public List<ConsultantStudentReportDto> findConsultantSummaryReport(ConsultantStudent consultantStudent){
        System.out.println("Name: "+consultantStudent.getName());
        System.out.println("getYearOfStudy: "+consultantStudent.getYearOfStudy());
        System.out.println("getStudentsLevel: "+consultantStudent.getStudentsLevel());
        System.out.println("getCourse: "+consultantStudent.getCourse());

        List<Object[]> consultantSummaryList = consultantStudentRepository.findConsultantSummary(
                    "%"+consultantStudent.getName()+"%",
                    consultantStudent.getYearOfStudy(),
                    consultantStudent.getStudentsLevel(),
                    consultantStudent.getCourse());
        List<ConsultantStudentReportDto> consultantStudentReportDtos = new ArrayList<>();
            if (!consultantSummaryList.isEmpty() && consultantSummaryList.size() >0){
                for(final Object[] e : consultantSummaryList){
                    final ConsultantStudentReportDto result = new ConsultantStudentReportDto();
                    result.setPrefix(e[0].toString());
                    result.setPrefixOther(e[1].toString());
                    result.setName(e[2].toString());
                    result.setSurname(e[3].toString());
                    result.setYearOfStudy(e[4].toString());
                    result.setStudentsLevel(e[5].toString());
                    result.setCourse(e[6].toString());
                    result.setCountStudent(e[7].toString());
                    consultantStudentReportDtos.add(result);
                }
            }
         return consultantStudentReportDtos;
    }

    public ConsultantStudent save(ConsultantStudent consultantStudent) {
        return consultantStudentRepository.saveAndFlush(consultantStudent);
    }

    public ConsultantStudent findById(String id) {
        if (id.matches("\\d+")) {
            return consultantStudentRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            System.out.println("Invalid staff_id: '" + id + "'");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

}
