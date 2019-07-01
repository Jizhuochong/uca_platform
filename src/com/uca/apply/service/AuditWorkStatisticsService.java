package com.uca.apply.service;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.apply.pojos.UcaAuditWorkStatistics;

public interface AuditWorkStatisticsService {
    void find(JSONObject object, Page<UcaAuditWorkStatistics> page, String projectName, String sd, String ed);

    void saveOrUpdate(JSONObject object, UcaAuditWorkStatistics po);

    void getById(JSONObject object, Integer id);

    void delete(JSONObject object, int id);

    void find(JSONObject object, String userId, String sd, String ed);
}
