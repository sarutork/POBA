package com.obu.tech.poba.consultant_info.students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConsultantStudentRepository extends JpaRepository<ConsultantStudent, Long>, JpaSpecificationExecutor<ConsultantStudent>{
    @Query("SELECT c.prefix, c.prefixOther, c.name, c.surname, c.yearOfStudy, c.studentsLevel, c.course,COUNT(*) as countStudent" +
            " FROM ConsultantStudent AS c" +
            " WHERE (:name is null or :name = '' or c.name LIKE :name" +
            " or c.surname LIKE :name)" +
            " and (:yearOfStudy is null or :yearOfStudy = ''  or c.yearOfStudy = :yearOfStudy)" +
            " and (:studentsLevel is null or :studentsLevel = '' or c.studentsLevel = :studentsLevel)" +
            " and (:course is null or :course = '' or c.course = :course)" +
            " GROUP BY c.prefix, c.prefixOther,c.name,c.surname,c.yearOfStudy, c.studentsLevel, c.course, c.department  ORDER BY c.yearOfStudy DESC")
    List<Object[]> findConsultantSummary(@Param("name") String name,
                                                           @Param("yearOfStudy") String yearOfStudy,
                                                           @Param("studentsLevel") String studentsLevel,
                                                           @Param("course") String course);
}