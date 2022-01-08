package com.obu.tech.poba.personnel_info.education;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudyInfoRowMapper implements RowMapper<StudyInfo> {

    @Override
    public StudyInfo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        StudyInfo studyInfo = new StudyInfo();
        studyInfo.setStaffId(resultSet.getInt("staff_id"));
        studyInfo.setPrefix(resultSet.getString("prefix"));
        studyInfo.setName(resultSet.getString("name"));
        studyInfo.setSurname(resultSet.getString("surname"));
        studyInfo.setFullName(studyInfo.getName()+" "+studyInfo.getSurname());
        studyInfo.setTravelOrder(resultSet.getString("travel_order"));
        studyInfo.setStartDate(resultSet.getString("start_date"));
        studyInfo.setEndDate(resultSet.getString("end_date"));
        studyInfo.setTotalDate(resultSet.getInt("total_date"));
        studyInfo.setActivityDetail(resultSet.getString("activity_detail"));
        studyInfo.setLocation(resultSet.getString("location"));
        studyInfo.setLocationType(resultSet.getString("location_type"));
        studyInfo.setCountry(resultSet.getString("country"));
        studyInfo.setFund(resultSet.getString("fund"));
        return studyInfo;
    }
}
