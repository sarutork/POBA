package com.obu.tech.poba.personnel_info.history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryInfoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

}