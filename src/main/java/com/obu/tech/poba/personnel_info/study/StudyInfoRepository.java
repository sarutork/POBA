package com.obu.tech.poba.personnel_info.study;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudyInfoRepository extends JpaRepository<StudyInfo, Long>, JpaSpecificationExecutor<StudyInfo> {
    @Query("SELECT s.staffId, p.prefix, p.prefixOther, p.name, p.surname, s.location, s.country, " +
            "s.startDate, s.endDate " +
            "FROM StudyInfo s JOIN Profile p ON s.persNo = p.persNo " +
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name)" +
            " and (:startDate is null or s.startDate >= :startDate)" +
            " and (:endDate is null or s.endDate <= :endDate)")
    List<Object[]> findInfo(@Param("name") String name,
                            @Param("startDate") LocalDate startDate,
                            @Param("endDate") LocalDate endDate);

}
