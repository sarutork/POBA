package com.obu.tech.poba.presenting_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentingRepository extends JpaRepository<Presenting, Long>, JpaSpecificationExecutor<Presenting> {
}
