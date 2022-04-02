package com.obu.tech.poba.press_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PressRepository extends JpaRepository<Press, Long>, JpaSpecificationExecutor<Press> {
}
