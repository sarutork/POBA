package com.obu.tech.poba.consultant_info.thesis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultantThesisRepository extends JpaRepository<ConsultantThesis, Long>, JpaSpecificationExecutor<ConsultantThesis> {
}
