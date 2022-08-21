package com.obu.tech.poba.lecturer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer,Long>,JpaSpecificationExecutor<Lecturer> {
    @Query("SELECT l.lecturerId, p.prefix, p.prefixOther, p.name, p.surname"+
            ",l.institutionInfo" +
            ",l.teachTopic" +
            ",l.teachTimes" +
            ",l.teachDate" +
            ",l.noteOfTeach" +
            ",l.studyType" +
            ",l.semester" +
            ",l.studyYear" +
            ",l.subjectId" +
            ",l.subjectName" +
            ",l.subjectCredit" +
            ",l.currentCredit" +
            ",l.teachType" +
            ",l.teachDay" +
            ",l.studyStart" +
            ",l.studyEnd" +
            ",l.teachLocation" +
            ",l.teachLocationOther" +
            ",l.teachRoom" +
            ",l.teachStyle" +
            ",l.teachStyleDetail" +
            ",l.teachStyleDetailOther" +
            ",l.totalOfStudents" +
            ",l.totalStudentsRegister" +
            " FROM Lecturer l JOIN Profile p ON l.persNo = p.persNo " +
            " WHERE (:name is null or :name = '' or p.name LIKE :name" +
            " or p.surname LIKE :name)" +
            " and (:studyYear is null or :studyYear = '' or l.studyYear = :studyYear)" +
            " and (:semester is null or :semester = '' or l.semester LIKE :semester)")
    List<Object[]> findInfo(@Param("name") String name,
                                   @Param("studyYear") String studyYear,
                                   @Param("semester") String semester);
}
