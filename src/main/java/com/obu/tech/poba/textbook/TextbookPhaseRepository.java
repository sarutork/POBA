package com.obu.tech.poba.textbook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextbookPhaseRepository extends JpaRepository<TextbookPhase, Long>, JpaSpecificationExecutor<TextbookPhase> {
    @Query("FROM TextbookPhase p WHERE p.textbookId LIKE :textbookId" )
    List<TextbookPhase> findByTextbookId(@Param("textbookId") Long id);
}
