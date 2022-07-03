package com.obu.tech.poba.students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepository extends JpaRepository<Students, Long>, JpaSpecificationExecutor<Students> {
    @Query("FROM Students s WHERE s.studentsId = :studentsId" )
    Students findStudentId(@Param("studentsId") String studentsId);
}
