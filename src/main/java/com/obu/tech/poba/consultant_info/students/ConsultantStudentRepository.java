package com.obu.tech.poba.consultant_info.students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConsultantStudentRepository extends JpaRepository<ConsultantStudent, Long>, JpaSpecificationExecutor<ConsultantStudent>{
    @Query("SELECT cs.consultantStudentId, p.prefix, p.prefixOther, p.name, p.surname, s.studentsPrefix, s.studentsPrefixOther, " +
            "s.studentsName, s.studentsSurname, s.studentsYear, s.studentsLevel, s.studentsCourse " +
            "FROM ConsultantStudent cs " +
            "JOIN Profile p ON p.persNo = cs.persNo " +
            "JOIN Students s ON s.studentsId = cs.studentsId "+
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name" +
            " or s.studentsName LIKE :name" +
            " or s.studentsSurname LIKE :name)" +
            " and (:yearOfStudy is null or :yearOfStudy = ''  or s.studentsYear = :yearOfStudy)" +
            " and (:studentsLevel is null or :studentsLevel = '' or s.studentsLevel = :studentsLevel)")
    List<Object[]> findConsultantStudentInfo(@Param("name") String name,
                                   @Param("yearOfStudy") String yearOfStudy,
                                   @Param("studentsLevel") String studentsLevel);

    @Query("SELECT cs.consultantStudentId, p.prefix, p.prefixOther, p.name, p.surname, s.studentsPrefix, s.studentsPrefixOther, " +
            "s.studentsName, s.studentsSurname, s.studentsYear, s.studentsLevel, s.studentsCourse " +
            "FROM ConsultantStudent cs " +
            "JOIN Profile p ON p.persNo = cs.persNo " +
            "JOIN Students s ON s.studentsId = cs.studentsId "+
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name" +
            " or s.studentsName LIKE :name" +
            " or s.studentsSurname LIKE :name)" +
            " and (:yearOfStudy is null or :yearOfStudy = ''  or s.studentsYear = :yearOfStudy)" +
            " and (:studentsLevel is null or :studentsLevel = '' or s.studentsLevel != :studentsLevel)")
    List<Object[]> findConsultantStudentInfo2(@Param("name") String name,
                                             @Param("yearOfStudy") String yearOfStudy,
                                             @Param("studentsLevel") String studentsLevel);

//    @Query("SELECT c.prefix, c.prefixOther, c.name, c.surname, c.yearOfStudy, c.studentsLevel, c.course, c.department, COUNT(*) as countStudent" +
//            " FROM ConsultantStudent AS c" +
//            " WHERE (:name is null or :name = '' or c.name LIKE :name" +
//            " or c.surname LIKE :name)" +
//            " and (:yearOfStudy is null or :yearOfStudy = ''  or c.yearOfStudy = :yearOfStudy)" +
//            " and (:studentsLevel is null or :studentsLevel = '' or c.studentsLevel = :studentsLevel)" +
//            " and (:course is null or :course = '' or c.course = :course)" +
//            " GROUP BY c.prefix, c.prefixOther,c.name,c.surname,c.yearOfStudy, c.studentsLevel, c.course, c.department  ORDER BY c.yearOfStudy DESC")
//    List<Object[]> findConsultantSummary(@Param("name") String name,
//                                                           @Param("yearOfStudy") String yearOfStudy,
//                                                           @Param("studentsLevel") String studentsLevel,
//                                                           @Param("course") String course);
//
//    @Query("SELECT c.studentsId, c.studentPrefix, c.studentPrefixOther, c.studentName, c.studentSurname, c.admissionStatus" +
//            " FROM ConsultantStudent AS c" +
//            " WHERE (c.name = :name" +
//            " and c.surname = :surname)" +
//            " and (:yearOfStudy is null or :yearOfStudy = ''  or c.yearOfStudy = :yearOfStudy)" +
//            " and (:studentsLevel is null or :studentsLevel = '' or c.studentsLevel = :studentsLevel)" +
//            " and (:course is null or :course = '' or c.course = :course)" +
//            " ORDER BY c.yearOfStudy DESC")
//    List<Object[]> findStudentByConsultant(@Param("name") String name,
//                                        @Param("surname") String surname,
//                                         @Param("yearOfStudy") String yearOfStudy,
//                                         @Param("studentsLevel") String studentsLevel,
//                                         @Param("course") String course);
//
//    @Query("SELECT prefix, prefixOther, name, surname, studentsLevel,admissionStatus" +
//            " FROM ConsultantStudent AS c" +
//            " WHERE (:name is null or :name = '' or c.name LIKE :name" +
//            " or c.surname LIKE :name)" +
//            " and (:admissionStatus is null or :admissionStatus = ''  or c.admissionStatus LIKE :admissionStatus)" +
//            " and (:studentsLevel is null or :studentsLevel = '' or c.studentsLevel = :studentsLevel)" +
//            " GROUP BY prefix, prefix_other,name,surname, studentsLevel,admissionStatus")
//    List<Object[]> findConsultantByNameStdLevelAdmissionStatus(@Param("name") String name,
//                                                               @Param("admissionStatus") String admissionStatus,
//                                                               @Param("studentsLevel") String studentsLevel);
//
//    @Query("SELECT COUNT(*) as countStudent" +
//            " FROM ConsultantStudent AS c" +
//            " WHERE (c.name = :name" +
//            " and c.surname = :surname)" +
//            " and c.yearOfStudy = :yearOfStudy" +
//            " and c.admissionStatus = :admissionStatus" +
//            " and c.studentsLevel = :studentsLevel" +
//            " GROUP BY c.prefix,c.prefixOther,c.name,c.surname,c.yearOfStudy,c.admissionStatus,c.studentsLevel")
//    List<Object[]> findConsultantSumStudentReport(@Param("name") String name,
//                                         @Param("surname") String surname,
//                                         @Param("yearOfStudy") String yearOfStudy,
//                                         @Param("admissionStatus") String admissionStatus,
//                                         @Param("studentsLevel") String studentsLevel);
}