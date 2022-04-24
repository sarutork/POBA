package com.obu.tech.poba.consultant_info.thesis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicConfRepository extends JpaRepository<AcademicConference, Long>, JpaSpecificationExecutor<AcademicConference> {
    @Query("FROM AcademicConference a WHERE a.thesisId = :thesisId" )
    AcademicConference findByThesisId(@Param("thesisId") Long id);
}
