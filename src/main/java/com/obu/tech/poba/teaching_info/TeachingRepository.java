package com.obu.tech.poba.teaching_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeachingRepository extends JpaRepository<Teaching, Long>, JpaSpecificationExecutor<Teaching> {
}
