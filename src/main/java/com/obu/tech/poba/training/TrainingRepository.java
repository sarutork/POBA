package com.obu.tech.poba.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long>, JpaSpecificationExecutor<Training> {
    @Query("SELECT t.trainingId, p.prefix, p.prefixOther, p.name, p.surname, t.trainingStatus1, t.trainingLevel " +
            "FROM Training t JOIN Profile p ON t.persNo1 = p.persNo " +
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name)" +
            " and (:trainingLevel is null or :trainingLevel = '' or t.trainingLevel = :trainingLevel)"+
            " and (:trainingDateFrom is null or t.trainingDateFrom >= :trainingDateFrom)" +
            " and (:trainingDateTo is null or t.trainingDateTo <= :trainingDateTo)")
    List<Object[]> findInfo(@Param("name") String name,
                            @Param("trainingLevel") String trainingLevel,
                            @Param("trainingDateFrom") LocalDate trainingDateFrom,
                            @Param("trainingDateTo") LocalDate trainingDateTo);
}
