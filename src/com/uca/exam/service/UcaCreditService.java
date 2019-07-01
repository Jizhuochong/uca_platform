package com.uca.exam.service;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.exam.pojos.UcaCredit;
import com.uca.exam.vo.UcaCreditStatisticsVo;

public interface UcaCreditService {

	void find(JSONObject object, Page<UcaCredit> page, String epName, String userName);

	UcaCredit getObjByEpIdAnswerUserId(Integer epId, Integer answerUserId);

	void saveOrUpdate(UcaCredit po);

	void find(JSONObject object, Page<UcaCreditStatisticsVo> page, String type,
			String sd, String ed);

}
