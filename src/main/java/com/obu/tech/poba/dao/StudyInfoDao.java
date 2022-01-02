package com.obu.tech.poba.dao;

import com.obu.tech.poba.model.StudyInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudyInfoDao {
    public List<Map<String,Object>> findAll();
    public List<Map<String,Object>> findById(int id);
    public void insert(StudyInfo studyInfo);
    public void update(StudyInfo studyInfo);
}
