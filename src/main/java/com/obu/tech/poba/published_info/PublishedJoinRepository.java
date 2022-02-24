package com.obu.tech.poba.published_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PublishedJoinRepository extends JpaRepository<PublishedJoin, Long>, JpaSpecificationExecutor<PublishedJoin> {
    @Query("FROM PublishedJoin p WHERE p.publishedId LIKE :publishedId" )
    List<PublishedJoin> findByPublishedId(@Param("publishedId") Long id);
}
