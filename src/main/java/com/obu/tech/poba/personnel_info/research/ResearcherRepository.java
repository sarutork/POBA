package com.obu.tech.poba.personnel_info.research;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ResearcherRepository extends JpaRepository<Researcher, Long>, JpaSpecificationExecutor<Researcher> {
}
