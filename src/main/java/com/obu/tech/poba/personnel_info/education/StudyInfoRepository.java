package com.obu.tech.poba.personnel_info.education;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudyInfoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<StudyInfo> findAll() {
        String sql = "SELECT staff_id, " +
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

        return jdbcTemplate.query(sql, new StudyInfoRowMapper());
    }

    public Optional<StudyInfo> findById(String id) {
        return findById(Integer.parseInt(id));
    }

    public Optional<StudyInfo> findById(int id) {
        String sql = "SELECT staff_id, " +
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

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new StudyInfoRowMapper(), id));
    }

    public void insert(StudyInfo studyInfo) {

    }

    public void update(StudyInfo studyInfo) {

    }
}
