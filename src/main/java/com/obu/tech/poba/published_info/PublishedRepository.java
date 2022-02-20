package com.obu.tech.poba.published_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishedRepository extends JpaRepository<Published, Long>, JpaSpecificationExecutor<Published> {

}
