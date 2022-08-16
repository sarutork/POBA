package com.obu.tech.poba.published_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PublishedRepository extends JpaRepository<Published, Long>, JpaSpecificationExecutor<Published> {
    @Query("SELECT pu.publishedId, p.prefix, p.prefixOther, p.name, p.surname, pu.publishedStatus, pu.publishedTopic, " +
            "pu.publishedLevel " +
            "FROM Published pu JOIN Profile p ON pu.persNo = p.persNo " +
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name)" +
            " and (:publishedLevel is null or :publishedLevel = '' or pu.publishedLevel = :publishedLevel)" +
            " and (:publishedYear2 is null or :publishedYear2 = '' or pu.publishedYear2 = :publishedYear2)")
    List<Object[]> findPublishedInfo(@Param("name") String name,
                                     @Param("publishedLevel") String publishedLevel,
                                     @Param("publishedYear2") String publishedYear2);
}
