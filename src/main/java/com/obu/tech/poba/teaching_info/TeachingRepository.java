package com.obu.tech.poba.teaching_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeachingRepository extends JpaRepository<Teaching, Long>, JpaSpecificationExecutor<Teaching> {
    @Query(
            value = "FROM Teaching t WHERE staff_id = :staffId"
    )
    Optional<Teaching> findById(@Param("staffId") Long id);
}
