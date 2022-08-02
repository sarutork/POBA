package com.obu.tech.poba.students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsSummaryRepository extends JpaRepository<Students, Long>, JpaSpecificationExecutor<Students> {
    @Query("SELECT CASE WHEN p.prefix = 'อื่นๆ' THEN p.prefixOther ELSE p.prefix END AS title,p.name,p.surname,s.studentsYear as year, " +
            "COUNT(*) AS total " +
            "FROM Students AS s JOIN Profile p ON s.persNo = p.persNo " +
            "WHERE s.studentsYear BETWEEN  :fromYear AND :toYear " +
            "AND s.studentsLevel = :studentsLevel " +
            "GROUP BY title,p.name,p.surname,s.studentsYear " +
            "ORDER BY s.studentsYear ")
    List<Object[]> findYear(@Param("fromYear") String fromYear,
                            @Param("toYear") String toYear,
                            @Param("studentsLevel") String studentsLevel);
}
