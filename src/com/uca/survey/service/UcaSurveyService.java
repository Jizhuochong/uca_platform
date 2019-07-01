package com.uca.survey.service;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.survey.pojos.UcaSurvey;

public interface UcaSurveyService {

	void find(JSONObject object, Page<UcaSurvey> page, int type, String sd, String ed);

	void saveOrUpdate(JSONObject object, UcaSurvey po);

	void getById(JSONObject object, int id);

	void delete(JSONObject object, int id);
	
	UcaSurvey getById(int id);
}
