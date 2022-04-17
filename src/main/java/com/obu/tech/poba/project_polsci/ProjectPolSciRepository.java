package com.obu.tech.poba.project_polsci;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectPolSciRepository extends JpaRepository<ProjectPolSci, Long>, JpaSpecificationExecutor<ProjectPolSci> {
}
