package com.uca.archives.service;

import java.util.List;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.archives.pojos.ArchivesCompletionDetailedForm;

public interface ArchivesCompletionDetailedFormService {
    void find(JSONObject object, Page<ArchivesCompletionDetailedForm> page, int registerId);

    void saveOrUpdate(JSONObject object, ArchivesCompletionDetailedForm po);

    void getById(JSONObject object, Integer id);

    void delete(JSONObject object, int id);

    List<ArchivesCompletionDetailedForm> findByRegisterId(int registerId);
}
