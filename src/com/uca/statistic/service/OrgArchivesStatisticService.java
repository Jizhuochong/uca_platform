package com.uca.statistic.service;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.statistic.pojos.UcaOrgArchivesStatistic;
import com.uca.statistic.vo.UcaOrgArchivesStatisticVo;

public interface OrgArchivesStatisticService {
    void find(JSONObject object, Page<UcaOrgArchivesStatisticVo> page);

    UcaOrgArchivesStatistic find(String sd, String ed);

    void saveOrUpdate(JSONObject object, UcaOrgArchivesStatistic po);
}
