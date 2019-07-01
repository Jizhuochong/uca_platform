package com.uca.survey.service;

import java.util.List;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.survey.pojos.UcaSurveyOption;

public interface UcaSurveyOptionService {

	void find(JSONObject object, Page<UcaSurveyOption> page, int surveyId, String sd, String ed);

	void saveOrUpdate(JSONObject object, UcaSurveyOption po);

	void getById(JSONObject object, int id);

	void delete(JSONObject object, int id);

	List<UcaSurveyOption> getSurveyOptionListBySurveyId(int surveyId);

	List<UcaSurveyOption> getSurveyOptionStatisticsBySurveyId(int surveyId);
}
