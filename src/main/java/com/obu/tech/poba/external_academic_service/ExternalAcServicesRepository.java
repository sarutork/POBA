package com.obu.tech.poba.external_academic_service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExternalAcServicesRepository extends JpaRepository<ExternalAcServices,Long>, JpaSpecificationExecutor<ExternalAcServices> {
    @Query("SELECT ext.id, p.prefix, p.prefixOther, p.name, p.surname, ext.title, ext.level, ext.institution, ext.year " +
            "FROM ExternalAcServices ext JOIN Profile p ON ext.persNo = p.persNo " +
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name)" +
            " and (:level is null or :level = '' or ext.level = :level)"+
            " and (:startDate is null or ext.startDate >= :startDate)" +
            " and (:endDate is null or ext.endDate <= :endDate)")
    List<Object[]> findInfo(@Param("name") String name,
                            @Param("level") String level,
                            @Param("startDate") LocalDate startDate,
                            @Param("endDate") LocalDate endDate);
}
