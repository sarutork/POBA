package com.obu.tech.poba.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingPhaseRepository extends JpaRepository<TrainingPhase, Long>, JpaSpecificationExecutor<TrainingPhase> {
    @Query("FROM TrainingPhase t WHERE t.trainingId LIKE :trainingId" )
    List<TrainingPhase> findByTrainingId(@Param("trainingId") Long id);
}
