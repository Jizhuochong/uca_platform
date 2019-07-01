package com.uca.survey.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.uca.survey.pojos.UcaSurveyResult;

public interface UcaSurveyResultService {

	void saveOrUpdate(JSONObject object, UcaSurveyResult po);
	
	List<UcaSurveyResult> findSurveyResultBySurveyId(int surveyId);
}
