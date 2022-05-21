package com.obu.tech.poba.external_academic_service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalAcServicesRepository extends JpaRepository<ExternalAcServices,Long>, JpaSpecificationExecutor<ExternalAcServices> {
}
