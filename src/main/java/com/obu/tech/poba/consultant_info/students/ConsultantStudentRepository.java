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

    @Query("SELECT p.prefix, p.prefixOther, p.name, p.surname, s.studentsYear, s.studentsLevel, s.studentsCourse, c.department, COUNT(*) as countStudent" +
            " FROM ConsultantStudent AS c" +
            " JOIN Profile AS p on p.persNo = c.persNo" +
            " JOIN Students AS s on s.studentsId = c.studentsId" +
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name)" +
            " and (:yearOfStudy is null or :yearOfStudy = ''  or s.studentsYear = :yearOfStudy)" +
            " and (:studentsLevel is null or :studentsLevel = '' or s.studentsLevel = :studentsLevel)" +
            " and (:course is null or :course = '' or s.studentsCourse = :course)" +
            " GROUP BY p.prefix, p.prefixOther,p.name,p.surname,s.studentsYear, s.studentsLevel, s.studentsCourse, c.department  ORDER BY s.studentsYear DESC")
    List<Object[]> findConsultantSummary(@Param("name") String name,
                                       @Param("yearOfStudy") String yearOfStudy,
                                       @Param("studentsLevel") String studentsLevel,
                                       @Param("course") String course);

    @Query("SELECT s.studentsId, s.studentsPrefix, s.studentsPrefixOther, s.studentsName, s.studentsSurname, s.studentsHow" +
            " FROM ConsultantStudent AS c" +
            " JOIN Profile AS p on p.persNo = c.persNo" +
            " JOIN Students AS s on s.studentsId = c.studentsId" +
            " WHERE (p.name = :name" +
            " and p.surname = :surname)" +
            " and (:yearOfStudy is null or :yearOfStudy = ''  or s.studentsYear = :yearOfStudy)" +
            " and (:studentsLevel is null or :studentsLevel = '' or s.studentsLevel = :studentsLevel)" +
            " and (:course is null or :course = '' or s.studentsCourse = :course)" +
            " ORDER BY s.studentsYear DESC")
    List<Object[]> findStudentByConsultant(@Param("name") String name,
                                        @Param("surname") String surname,
                                         @Param("yearOfStudy") String yearOfStudy,
                                         @Param("studentsLevel") String studentsLevel,
                                         @Param("course") String course);

    @Query("SELECT p.prefix, p.prefixOther, p.name, p.surname, s.studentsLevel,s.studentsHow" +
            " FROM ConsultantStudent AS c" +
            " JOIN Profile AS p on p.persNo = c.persNo" +
            " JOIN Students AS s on s.studentsId = c.studentsId" +
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name)" +
            " and (:admissionStatus is null or :admissionStatus = ''  or s.studentsHow LIKE :admissionStatus)" +
            " and (:studentsLevel is null or :studentsLevel = '' or s.studentsLevel = :studentsLevel)" +
            " GROUP BY p.prefix, p.prefixOther, p.name, p.surname, s.studentsLevel, s.studentsHow")
    List<Object[]> findConsultantByNameStdLevelAdmissionStatus(@Param("name") String name,
                                                               @Param("admissionStatus") String admissionStatus,
                                                               @Param("studentsLevel") String studentsLevel);

    @Query("SELECT COUNT(*) as countStudent" +
            " FROM ConsultantStudent AS c" +
            " JOIN Profile AS p on p.persNo = c.persNo" +
            " JOIN Students AS s on s.studentsId = c.studentsId" +
            " WHERE (p.name = :name" +
            " and p.surname = :surname)" +
            " and s.studentsYear = :yearOfStudy" +
            " and s.studentsHow = :admissionStatus" +
            " and s.studentsLevel = :studentsLevel" +
            " GROUP BY p.prefix, p.prefixOther, p.name, p.surname, s.studentsYear, s.studentsHow, s.studentsLevel")
    List<Object[]> findConsultantSumStudentReport(@Param("name") String name,
                                         @Param("surname") String surname,
                                         @Param("yearOfStudy") String yearOfStudy,
                                         @Param("admissionStatus") String admissionStatus,
                                         @Param("studentsLevel") String studentsLevel);
}