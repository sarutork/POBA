package com.obu.tech.poba.project;

import com.obu.tech.poba.textbook.TextbookPhase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant,Long>, JpaSpecificationExecutor<Participant> {
    @Query("FROM Participant p WHERE p.projectId LIKE :projectId" )
    List<Participant> findByProjectId(@Param("projectId") Long id);
}
