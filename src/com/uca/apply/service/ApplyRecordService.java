package com.uca.apply.service;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.apply.pojos.UcaApplyQueryRecord;
import com.uca.apply.vo.UcaApplyQueryRecordVo;
import com.uca.archives.pojos.UcaArchives;
import com.uca.archives.vo.UcaArchivesVo;

public interface ApplyRecordService {

    UcaApplyQueryRecord getObjById(int aqId);

    void findApplyRecordAuditing(JSONObject object, Page<UcaApplyQueryRecord> page, int type);

    void pass(JSONObject object, int aqId, String passTxt);

    void unpass(JSONObject object, int aqId, String unPassTxt);

    void findArchivesForApply(JSONObject object, Page<UcaArchivesVo> page, Integer type);

    void findApplyRecordForQuery(JSONObject object, Page<UcaArchivesVo> page, Integer type);

    void updateApplyArchives(JSONObject object, UcaArchives po);

    void find(JSONObject object, Page<UcaApplyQueryRecordVo> page, String sd, String ed, int archivesType);
}
