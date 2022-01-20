package com.obu.tech.poba.consultant_info.students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConsultantStudentRepository extends JpaRepository<ConsultantStudent, Long>, JpaSpecificationExecutor<ConsultantStudent>{
}