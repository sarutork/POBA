package com.obu.tech.poba.textbook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TextbookRepository extends JpaRepository<Textbook, Long>, JpaSpecificationExecutor<Textbook> {
    @Query("SELECT t.textbookId, p.prefix, p.prefixOther, p.name, p.surname, t.textbookType, t.textbookTopic, t.textbookLevel " +
            "FROM Textbook t JOIN Profile p ON t.persNo = p.persNo " +
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name)" +
            " and (:textbookLevel is null or :textbookLevel = '' or t.textbookLevel = :textbookLevel)" +
            " and (:textbookType is null or :textbookType = '' or t.textbookType = :textbookType)"+
            " and (:textbookDateFrom is null or t.textbookDateFrom >= :textbookDateFrom)" +
            " and (:textbookDateTo is null or t.textbookDateTo <= :textbookDateTo)")
    List<Object[]> findTextbookInfo(@Param("name") String name,
                                    @Param("textbookLevel") String textbookLevel,
                                    @Param("textbookType") String textbookType,
                                    @Param("textbookDateFrom") LocalDate textbookDateFrom,
                                    @Param("textbookDateTo") LocalDate textbookDateTo);
}
