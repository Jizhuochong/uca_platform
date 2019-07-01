package com.uca.archives.service;

import java.io.File;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.archives.pojos.ArchivesCompletionRegisterForm;

public interface ArchivesCompletionRegisterFormService {
    void find(JSONObject object, Page<ArchivesCompletionRegisterForm> page, String type, String sd, String ed,
            boolean isCurrentUser);

    void saveOrUpdate(JSONObject object, ArchivesCompletionRegisterForm po);

    void getById(JSONObject object, Integer id);

    void delete(JSONObject object, int id);

    ArchivesCompletionRegisterForm getById(Integer id);

	File getGeneratePhoneRecordExportFile(int docTypeVal, Integer id);

	File getGenerategyRecordExportFile(int type, int id);

	File getGenerateJZRecordExportFile(int type, int id);
}
