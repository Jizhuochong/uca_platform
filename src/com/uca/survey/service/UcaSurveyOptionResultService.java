package com.uca.survey.service;

import com.alibaba.fastjson.JSONObject;

public interface UcaSurveyOptionResultService {

	void saveOrUpdate(JSONObject object, int surveyId, String surveyOptionIds);
}
