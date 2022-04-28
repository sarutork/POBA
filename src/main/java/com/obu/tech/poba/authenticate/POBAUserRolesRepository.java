package com.obu.tech.poba.authenticate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface POBAUserRolesRepository extends JpaRepository<POBAUserRoles, Long>, JpaSpecificationExecutor<POBAUserRoles> {
    @Query("SELECT r.fnName FROM roles r LEFT JOIN login AS l ON l.role = r.role WHERE l.username = :username")
    List<Object[]> findRoles(@Param("username") String username);

    @Query("FROM roles")
    List<Object[]> findRoles();
}
