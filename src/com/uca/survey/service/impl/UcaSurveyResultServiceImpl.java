package com.uca.survey.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.survey.dao.UcaSurveyDao;
import com.uca.survey.dao.UcaSurveyResultDao;
import com.uca.survey.pojos.UcaSurvey;
import com.uca.survey.pojos.UcaSurveyResult;
import com.uca.survey.service.UcaSurveyResultService;

@Service
public class UcaSurveyResultServiceImpl implements UcaSurveyResultService{
	private SecurityIdentifyUtils secUtils = new SecurityUtils();
	
	@Autowired
	private UcaSurveyResultDao ucaSurveyResultDao;
	
	@Autowired
	private UcaSurveyDao ucaSurveyDao;
	
	@Override
	public void saveOrUpdate(JSONObject object, UcaSurveyResult po) {
		UcaSurvey ucaSurvey = ucaSurveyDao.queryById(po.getSurveyId());
		if(null == ucaSurvey) {
			object.put("success", false);
			object.put("msg", "问卷调查不存在！");
			return;
		}
		
		UcaSurveyResult usr = getObjBySurveyIdUserId(po.getSurveyId(), secUtils.getUser().getUserId());
		if(null != usr) {
			object.put("success", false);
			object.put("msg", "已经提交该问卷调查，无需重复提交！");
			return;
		}
		
		po.setUcaSurvey(ucaSurvey);
		
		po.setResultUserId(secUtils.getUser().getUserId());
		po.setResultTime(new Date());
		ucaSurveyResultDao.saveOrUpdate(po);
		
		if(po.getId() > 0) {
			object.put("success", true);
	        object.put("msg", "提交成功！");
		} else {
			object.put("success", false);
	        object.put("msg", "提交失败！");
		}
	}
	
	private UcaSurveyResult getObjBySurveyIdUserId(int surveyId, int userId) {
		String sql = "select * from uca_survey_result where survey_id=:surveyId and result_user_id=:userId";
		SQLQuery query = ucaSurveyResultDao.getSession().createSQLQuery(sql).addEntity(UcaSurveyResult.class);
		query.setParameter("surveyId", surveyId);
		query.setParameter("userId", userId);
		List<UcaSurveyResult> list = query.list();
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
		
//		String sql = "from UcaSurveyResult where ucaSurvey.id = ? and resultUserId = ?";
//		UcaSurveyResult po = ucaSurveyResultDao.queryForObject(sql, new Object[]{surveyId, userId});
//		return po;
	}

	@Override
	public List<UcaSurveyResult> findSurveyResultBySurveyId(int surveyId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder(
				"SELECT usr.id,usr.result,usr.result_time,u.user_name " +
				"FROM uca_survey_result usr left join um_user u on usr.result_user_id=u.user_id " +
				"where usr.survey_id="+surveyId);
		
		SQLQuery query = ucaSurveyResultDao.getSession().createSQLQuery(sql.toString());

		List<Object[]> list = query.list();
		List<UcaSurveyResult> listResult = new ArrayList<UcaSurveyResult>(list.size());
		UcaSurveyResult vo = null;
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				vo = new UcaSurveyResult();
				vo.setId(Integer.valueOf(obj[0].toString()));
				vo.setResult(obj[1] != null ? obj[1].toString() : "");
				vo.setResultTimeStr(obj[2] != null?DateUtil.DateToStr((Date)obj[2]):"");
				vo.setUserName(obj[3] != null ? obj[3].toString() : "");
				listResult.add(vo);
			}
		}

		return listResult;
	}

}
