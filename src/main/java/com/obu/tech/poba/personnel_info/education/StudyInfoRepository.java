package com.obu.tech.poba.personnel_info.education;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudyInfoRepository extends JpaRepository<StudyInfo, Long>, JpaSpecificationExecutor<StudyInfo> {

}
