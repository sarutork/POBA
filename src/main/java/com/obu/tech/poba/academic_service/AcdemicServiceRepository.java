package com.obu.tech.poba.academic_service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcdemicServiceRepository extends JpaRepository<AcademicService, Long>, JpaSpecificationExecutor<AcademicService> {
    @Query("SELECT a.serviceId, p.prefix, p.prefixOther, p.name, p.surname, a.serviceStatus, a.serviceLevel " +
            "FROM AcademicService a JOIN Profile p ON a.persNo = p.persNo " +
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name)" +
            " and (:serviceLevel is null or :serviceLevel = '' or a.serviceLevel = :serviceLevel)")
    List<Object[]> findInfo(@Param("name") String name, @Param("serviceLevel") String serviceLevel);
}
