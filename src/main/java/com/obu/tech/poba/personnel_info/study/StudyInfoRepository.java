package com.obu.tech.poba.personnel_info.study;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudyInfoRepository extends JpaRepository<StudyInfo, Long>, JpaSpecificationExecutor<StudyInfo> {

}
