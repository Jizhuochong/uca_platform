package com.uca.survey.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.survey.dao.UcaSurveyDao;
import com.uca.survey.dao.UcaSurveyOptionDao;
import com.uca.survey.dao.UcaSurveyOptionResultDao;
import com.uca.survey.pojos.UcaSurvey;
import com.uca.survey.pojos.UcaSurveyOption;
import com.uca.survey.pojos.UcaSurveyOptionResult;
import com.uca.survey.service.UcaSurveyOptionResultService;

@Service
public class UcaSurveyOptionResultServiceImpl implements UcaSurveyOptionResultService{
	private SecurityIdentifyUtils secUtils = new SecurityUtils();
	
	@Autowired
	private UcaSurveyOptionResultDao ucaSurveyOptionResultDao;
	
	@Autowired
	private UcaSurveyOptionDao ucaSurveyOptionDao;
	
	@Autowired
	private UcaSurveyDao ucaSurveyDao;
	
	@Override
	public void saveOrUpdate(JSONObject object, int surveyId,
			String surveyOptionIds) {
		// TODO Auto-generated method stub
		UcaSurvey ucaSurvey = ucaSurveyDao.queryById(surveyId);
		if(null == ucaSurvey) {
			object.put("success", false);
			object.put("msg", "问卷调查不存在！");
			return;
		}
		
		List<UcaSurveyOptionResult> lists = getObjBySurveyIdUserId(surveyId, secUtils.getUser().getUserId());
		if(null != lists && lists.size() > 0) {
			object.put("success", false);
			object.put("msg", "已经提交该问卷调查，无需重复提交！");
			return;
		}
		Date date = new Date();
		lists = new ArrayList<UcaSurveyOptionResult>();
		String[] optionIds = surveyOptionIds.split(",");
		for(String optionId : optionIds) {
			if(!ParameterUtil.isNumber(optionId)) {
				continue;
			}
			UcaSurveyOption ucaSurveyOption = ucaSurveyOptionDao.queryById(Integer.parseInt(optionId));
			if(null == ucaSurveyOption) {
				continue;
			}
			UcaSurveyOptionResult surveyOptionResult = new UcaSurveyOptionResult();
			surveyOptionResult.setUcaSurvey(ucaSurvey);
			surveyOptionResult.setUcaSurveyOption(ucaSurveyOption);
			surveyOptionResult.setResultUserId(secUtils.getUser().getUserId());
			surveyOptionResult.setResultTime(date);
			lists.add(surveyOptionResult);
		}
		
		ucaSurveyOptionResultDao.saveOrUpdateAll(lists);
		
		object.put("success", true);
        object.put("msg", "提交成功！");
	}
	
	private List<UcaSurveyOptionResult> getObjBySurveyIdUserId(int surveyId, int userId) {
		String sql = "select * from uca_survey_option_result where survey_id=:surveyId and result_user_id=:userId";
		SQLQuery query = ucaSurveyOptionResultDao.getSession().createSQLQuery(sql).addEntity(UcaSurveyOptionResult.class);
		query.setParameter("surveyId", surveyId);
		query.setParameter("userId", userId);
		return query.list();
	}

}
