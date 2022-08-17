package com.obu.tech.poba.personnel_info.research;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface ResearcherRepository extends JpaRepository<Researcher, Long>, JpaSpecificationExecutor<Researcher> {
    @Query("SELECT r.staffId, p.prefix, p.prefixOther, p.name, p.surname, r.status, r.type, r.teacher1, r.teacher2, r.subSegment, " +
            "r.workStartDate, r.workEndDate, r.noteOfWork " +
            "FROM Researcher r JOIN Profile p ON r.persNo = p.persNo " +
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name)" +
            " and (:workStartDate is null or r.workStartDate >= :workStartDate)" +
            " and (:workEndDate is null or r.workEndDate <= :workEndDate)")
    List<Object[]> findInfo(@Param("name") String name,
                            @Param("workStartDate") LocalDate workStartDate,
                            @Param("workEndDate") LocalDate workEndDate);
}
