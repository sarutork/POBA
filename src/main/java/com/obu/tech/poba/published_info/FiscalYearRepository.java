package com.obu.tech.poba.published_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FiscalYearRepository extends JpaRepository<FiscalYear,Long>, JpaSpecificationExecutor<FiscalYear> {
    @Query("FROM FiscalYear f WHERE f.publishedId = :publishedId order by year desc" )
    List<FiscalYear> findByPublishedId(@Param("publishedId") Long id);

    @Transactional
    void deleteByPublishedId(long publishedId);
}
