package com.obu.tech.poba.presenting_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PresentingRepository extends JpaRepository<Presenting, Long>, JpaSpecificationExecutor<Presenting> {
    @Query("SELECT pr.presentId, p.prefix, p.prefixOther, p.name, p.surname, pr.presentTopic, pr.presentLevel " +
            "FROM Presenting pr JOIN Profile p ON pr.persNo = p.persNo " +
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name)" +
            " and (:presentLevel is null or :presentLevel = '' or pr.presentLevel = :presentLevel)")
    List<Object[]> findPresentInfo(@Param("name") String name, @Param("presentLevel") String presentLevel);
}
