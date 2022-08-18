package com.obu.tech.poba.teaching_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeachingRepository extends JpaRepository<Teaching, Long>, JpaSpecificationExecutor<Teaching> {
    @Query(value = "FROM Teaching t WHERE staff_id = :staffId")
    Optional<Teaching> findById(@Param("staffId") Long id);

    @Query("SELECT t.staffId, p.prefix, p.prefixOther, p.name, p.surname" +
            ",t.teachStatus" +
            ",t.institutionInfo" +
            ",t.teachTopic" +
            ",t.teachTimes" +
            ",t.teachDate" +
            ",t.noteOfTeach" +
            ",t.studyType" +
            ",t.semester" +
            ",t.studyYear" +
            ",t.subjectId" +
            ",t.subjectName" +
            ",t.subjectCredit" +
            ",t.currentCredit" +
            ",t.teachType" +
            ",t.teachDay" +
            ",t.studyStart" +
            ",t.studyEnd" +
            ",t.teachLocation" +
            ",t.teachLocationOther" +
            ",t.teachRoom" +
            ",t.teachStyle" +
            ",t.teachStyleDetail" +
            ",t.totalOfStudents" +
            ",t.totalStudentsRegister" +
            ",t.midtermExamDate" +
            ",t.midtermExamTimeStart" +
            ",t.midtermExamTimeEnd" +
            ",t.finalExamDate" +
            ",t.finalExamTimeStart" +
            ",t.finalExamTimeEnd " +
            "FROM Teaching t JOIN Profile p ON t.persNo = p.persNo " +
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name)" +
            " and (:studyYear is null or :studyYear = '' or t.studyYear = :studyYear) " +
            " and (:semester is null or :semester = '' or t.semester LIKE :semester)")
    List<Object[]> findInfo(@Param("name") String name, @Param("studyYear") String studyYear, @Param("semester") String semester);
}
