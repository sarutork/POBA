package com.obu.tech.poba.consultant_info.thesis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultantThesisRepository extends JpaRepository<ConsultantThesis, Long>, JpaSpecificationExecutor<ConsultantThesis> {
    @Query("SELECT ct.thesisId, p.prefix, p.prefixOther, p.name, p.surname, ct.thesisType, s.studentsPrefix, s.studentsPrefixOther, " +
            "s.studentsName, s.studentsSurname, s.studentsLevel, ct.thesisAssessment " +
            "FROM ConsultantThesis ct " +
            "JOIN Profile p ON p.persNo = ct.persNo " +
            "JOIN Students s ON s.studentsId = ct.studentsId "+
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name" +
            " or s.studentsName LIKE :name" +
            " or s.studentsSurname LIKE :name)" +
            " and (:thesisType is null or :thesisType = ''  or ct.thesisType = :thesisType)" +
            " and (:studentsLevel is null or :studentsLevel = '' or s.studentsLevel = :studentsLevel)")
    List<Object[]> findConsultantThesisInfo(@Param("name") String name,
                                            @Param("thesisType") String thesisType,
                                            @Param("studentsLevel") String studentsLevel);
}
