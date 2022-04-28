package com.obu.tech.poba.resolution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ResolutionRepository extends JpaRepository<Resolution,Long>, JpaSpecificationExecutor<Resolution> {
}
