package com.obu.tech.poba.consultant_info.students;

import com.obu.tech.poba.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultantStudentService {
    @Autowired
    ConsultantStudentRepository consultantStudentRepository;

    @Autowired
    CommonUtils commonUtils;

    public final String studentsLevel_1 = "ปริญญาตรี";
    public final String studentsLevel_2 = "ปริญญาโทเอก";
   public List<ConsultantStudent> findBySearchCriteria(ConsultantStudent consultantStudent,String studentsLevel){
       if(StringUtils.isBlank(consultantStudent.getStudentsLevel()) && studentsLevel_1.equals(studentsLevel)){
           List<Object[]> dataList = consultantStudentRepository.findConsultantStudentInfo(
                   consultantStudent.getName(),consultantStudent.getYearOfStudy(),studentsLevel_1);

           List<ConsultantStudent> resData = new ArrayList<>();
           if (!dataList.isEmpty() && dataList.size() >0){
               for(final Object[] e : dataList){
                   final ConsultantStudent result = new ConsultantStudent();
                   result.setConsultantStudentId(Long.parseLong(e[0].toString()));
                   result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : commonUtils.chkNullStrObj(e[2]));

                   result.setName(commonUtils.chkNullStrObj(e[3])+" "+commonUtils.chkNullStrObj(e[4]));

                   result.setStudentPrefix( !e[5].toString().equals("อื่นๆ")? e[5].toString() : commonUtils.chkNullStrObj(e[6]));
                   result.setStudentName(commonUtils.chkNullStrObj(e[7])+" "+commonUtils.chkNullStrObj(e[8]));

                   result.setYearOfStudy(commonUtils.chkNullStrObj(e[9]));
                   result.setStudentsLevel(commonUtils.chkNullStrObj(e[10]));
                   result.setCourse(commonUtils.chkNullStrObj(e[11]));
                   resData.add(result);
               }
           }
           return resData;

       }else if(StringUtils.isBlank(consultantStudent.getStudentsLevel()) && (studentsLevel_2.equals(studentsLevel))){
           List<Object[]> dataList = consultantStudentRepository.findConsultantStudentInfo2(
                   consultantStudent.getName(),consultantStudent.getYearOfStudy(),studentsLevel_1);

           List<ConsultantStudent> resData = new ArrayList<>();
           if (!dataList.isEmpty() && dataList.size() >0){
               for(final Object[] e : dataList){
                   final ConsultantStudent result = new ConsultantStudent();
                   result.setConsultantStudentId(Long.parseLong(e[0].toString()));
                   result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : commonUtils.chkNullStrObj(e[2]));

                   result.setName(commonUtils.chkNullStrObj(e[3])+" "+commonUtils.chkNullStrObj(e[4]));

                   result.setStudentPrefix( !e[5].toString().equals("อื่นๆ")? e[5].toString() : commonUtils.chkNullStrObj(e[6]));
                   result.setStudentName(commonUtils.chkNullStrObj(e[7])+" "+commonUtils.chkNullStrObj(e[8]));

                   result.setYearOfStudy(commonUtils.chkNullStrObj(e[9]));
                   result.setStudentsLevel(commonUtils.chkNullStrObj(e[10]));
                   result.setCourse(commonUtils.chkNullStrObj(e[11]));
                   resData.add(result);
               }
           }
           return resData;
       }else {
           List<Object[]> dataList = consultantStudentRepository.findConsultantStudentInfo(
                   consultantStudent.getName(),consultantStudent.getYearOfStudy(),consultantStudent.getStudentsLevel());

           List<ConsultantStudent> resData = new ArrayList<>();
           if (!dataList.isEmpty() && dataList.size() >0){
               for(final Object[] e : dataList){
                   final ConsultantStudent result = new ConsultantStudent();
                   result.setConsultantStudentId(Long.parseLong(e[0].toString()));
                   result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : commonUtils.chkNullStrObj(e[2]));

                   result.setName(commonUtils.chkNullStrObj(e[3])+" "+commonUtils.chkNullStrObj(e[4]));

                   result.setStudentPrefix( !e[5].toString().equals("อื่นๆ")? e[5].toString() : commonUtils.chkNullStrObj(e[6]));
                   result.setStudentName(commonUtils.chkNullStrObj(e[7])+" "+commonUtils.chkNullStrObj(e[8]));

                   result.setYearOfStudy(commonUtils.chkNullStrObj(e[9]));
                   result.setStudentsLevel(commonUtils.chkNullStrObj(e[10]));
                   result.setCourse(commonUtils.chkNullStrObj(e[11]));
                   resData.add(result);
               }
           }
           return resData;
       }

    }

   public List<ConsultantStudentReportDto> findConsultantSummaryReport(ConsultantStudentReportDto consultantStudent){
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
                    result.setDepartment(e[7].toString());
                    result.setCountStudent(e[8].toString());
                    consultantStudentReportDtos.add(result);
                }
            }
         return consultantStudentReportDtos;
    }

    public List<StudentDto> findStudentByConsultant(ConsultantStudentReportDto consultantStudentReportDto){
        List<Object[]> studentList = consultantStudentRepository.findStudentByConsultant(
                consultantStudentReportDto.getName(),
                consultantStudentReportDto.getSurname(),
                consultantStudentReportDto.getYearOfStudy(),
                consultantStudentReportDto.getStudentsLevel(),
                consultantStudentReportDto.getCourse());

        List<StudentDto> studentDtos = new ArrayList<>();
        if (!studentList.isEmpty() && studentList.size() > 0){
            for(final Object[] e : studentList){
                final StudentDto result = new StudentDto();
                result.setStudentsId(e[0].toString());
                result.setStudentPrefix(e[1].toString());
                result.setStudentPrefixOther(e[2].toString());
                result.setStudentName(e[3].toString());
                result.setStudentSurname(e[4].toString());
                result.setAdmissionStatus(e[5].toString());
                studentDtos.add(result);
            }
        }
        return studentDtos;
    }

    public List<ConsultantDto> findConsultantByNameStdLevelAdmissionStatus(ConsultantDto consultantDto){
        List<Object[]> consultantList = consultantStudentRepository.findConsultantByNameStdLevelAdmissionStatus(
                "%"+consultantDto.getName()+"%",
                "%"+consultantDto.getAdmissionStatus()+"%",
                consultantDto.getStudentsLevel());
        List<ConsultantDto> consultantDtos = new ArrayList<>();
        if (!consultantList.isEmpty() && consultantList.size() >0){
            for(final Object[] e : consultantList){
                final ConsultantDto result = new ConsultantDto();
                result.setPrefix(e[0].toString());
                result.setPrefixOther(e[1].toString());
                result.setName(e[2].toString());
                result.setSurname(e[3].toString());
                result.setStudentsLevel(e[4].toString());
                result.setAdmissionStatus(e[5].toString());
                consultantDtos.add(result);
            }
        }
        return consultantDtos;
    }

    public String findConsultantSumStudentReport(ConsultantDto consultantDto,int yearOfStudy){
        List<Object[]> sumStudentReport = consultantStudentRepository.findConsultantSumStudentReport(
                consultantDto.getName(),
                consultantDto.getSurname(),
                Integer.toString(yearOfStudy),
                consultantDto.getAdmissionStatus(),
                consultantDto.getStudentsLevel());
        String studentCount="";
        if (!sumStudentReport.isEmpty() && sumStudentReport.size() >0){
            for(final Object[] e : sumStudentReport){
               studentCount = e[0].toString();
            }
        }
        return studentCount;
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
