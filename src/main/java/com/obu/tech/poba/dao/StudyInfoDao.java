package com.obu.tech.poba.dao;

import com.obu.tech.poba.model.StudyInfo;

import java.util.List;
import java.util.Map;

public interface StudyInfoDao {
    public List<Map<String,Object>> findAll();
    public List<Map<String,Object>> findById(int id);
    public void insert(StudyInfo studyInfo);
    public void update(StudyInfo studyInfo);
}
