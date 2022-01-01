package com.obu.tech.poba.dao.impl;

import com.obu.tech.poba.dao.StudyInfoDao;
import com.obu.tech.poba.model.StudyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class StudyInfoDaoImpl implements StudyInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> findAll() {
        String findAllSql = "SELECT staff_id, " +
                "prefix, " +
                "name, " +
                "surname, " +
                "travel_order, " +
                "start_date, " +
                "end_date, " +
                "total_date, " +
                "activity_detail, " +
                "location, " +
                "location_type, " +
                "country, " +
                "fund " +
                "FROM study_info";

        return jdbcTemplate.queryForList(findAllSql,new Object[]{});
    }

    @Override
    public List<Map<String, Object>> findById(int id) {
        String findAllSql = "SELECT staff_id, " +
                "prefix, " +
                "name, " +
                "surname, " +
                "travel_order, " +
                "start_date, " +
                "end_date, " +
                "total_date, " +
                "activity_detail, " +
                "location, " +
                "location_type, " +
                "country, " +
                "fund " +
                "FROM study_info" +
                "WHERE staff_id = ?";

        return jdbcTemplate.queryForList(findAllSql,new Object[]{id});
    }

    @Override
    public void insert(StudyInfo studyInfo) {

    }

    @Override
    public void update(StudyInfo studyInfo) {

    }
}
