package com.obu.tech.poba.consultant_info.thesis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface JournalRepository extends JpaRepository<Journal,Long>, JpaSpecificationExecutor<Journal> {
    @Query("FROM Journal j WHERE j.thesisId = :thesisId" )
    Journal findByThesisId(@Param("thesisId") Long id);
}
