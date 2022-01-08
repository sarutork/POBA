package com.obu.tech.poba.personnel_info.education;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudyInfoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String SQL_STUDY_INFO = "SELECT staff_id," +
            " prefix," +
            " name," +
            " surname," +
            " travel_order," +
            " start_date," +
            " end_date," +
            " total_date," +
            " activity_detail," +
            " location," +
            " location_type," +
            " country," +
            " fund" +
            " FROM study_info" +
            " WHERE 1=1";
    public List<StudyInfo> findByCriteria(StudyInfo studyInfo) {
        StringBuilder sql = new StringBuilder(SQL_STUDY_INFO);
        List<Object> argumentList = new LinkedList<>();
        if(!StringUtils.isBlank(studyInfo.getName())){
                sql.append(" AND (name = ?");
                sql.append(" OR surname = ?)");
            argumentList.add(studyInfo.name);
            argumentList.add(studyInfo.name);
        }

        if(!StringUtils.isBlank(studyInfo.getStartDate())){
            sql.append(" AND start_date = ?");
            argumentList.add(studyInfo.startDate);
        }

        if(!StringUtils.isBlank(studyInfo.getEndDate())){
            sql.append(" AND end_date = ?");
            argumentList.add(studyInfo.endDate);

        }
        return jdbcTemplate.query(sql.toString(),argumentList.toArray(), new StudyInfoRowMapper());
    }
    public Optional<StudyInfo> findById(String id) {
        return findById(Integer.parseInt(id));
    }

    public Optional<StudyInfo> findById(int id) {
        StringBuilder sql = new StringBuilder(SQL_STUDY_INFO);
        sql.append(" AND staff_id = ?");
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql.toString(), new StudyInfoRowMapper(), id));
    }

    public void insert(StudyInfo studyInfo) {

    }

    public void update(StudyInfo studyInfo) {

    }
}
