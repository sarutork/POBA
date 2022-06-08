package com.obu.tech.poba.students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentsSummaryRepository extends JpaRepository<StudentsSummary, Long>, JpaSpecificationExecutor<StudentsSummary> {
    @Query("SELECT name,surname,students_year as year,COUNT(*) AS total,ANY_VALUE(CONCAT(name,' ',surname)) AS fullname FROM students WHERE students_year = :year GROUP BY name,surname,year")
    List<StudentsSummary> findRoles(@Param("year") String year);
}
