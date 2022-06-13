package com.obu.tech.poba.students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsSummaryRepository extends JpaRepository<Students, Long>, JpaSpecificationExecutor<Students> {
    @Query("FROM Students WHERE studentsYear = :year")
    List<StudentsSummary> findRoles(@Param("year") String year);
}
