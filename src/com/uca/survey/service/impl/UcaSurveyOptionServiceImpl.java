package com.uca.survey.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.survey.dao.UcaSurveyDao;
import com.uca.survey.dao.UcaSurveyOptionDao;
import com.uca.survey.dao.UcaSurveyOptionResultDao;
import com.uca.survey.pojos.UcaSurvey;
import com.uca.survey.pojos.UcaSurveyOption;
import com.uca.survey.service.UcaSurveyOptionService;

@Service
public class UcaSurveyOptionServiceImpl implements UcaSurveyOptionService{
	private SecurityIdentifyUtils secUtils = new SecurityUtils();
	
	@Autowired
	private UcaSurveyOptionDao ucaSurveyOptionDao;
	
	@Autowired
	private UcaSurveyDao ucaSurveyDao;
	
	@Autowired
	private UcaSurveyOptionResultDao ucaSurveyOptionResultDao;
	
	@Override
	public void find(JSONObject object, Page<UcaSurveyOption> page, int surveyId, String sd, String ed) {
		// TODO Auto-generated method stub
		Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }
        
        Criteria crit = ucaSurveyOptionDao.getSession().createCriteria(UcaSurveyOption.class);
//        crit.add(Restrictions.eq("createUserId", secUtils.getUser().getUserId()));
        crit.add(Restrictions.eq("ucaSurvey.id", surveyId));
        if(null != start && null != end){
            crit.add(Restrictions.between("createTime", start, end));
        } else {
            if(null != start){
                crit.add(Restrictions.ge("createTime", start));
            } else if(null != end){
                crit.add(Restrictions.le("createTime", end));
            }
        }
        
        int total = crit.list().size();
        
        crit.addOrder(Order.desc("createTime"));
        crit.setFirstResult(page.getPageNo());
        crit.setMaxResults(page.getPageSize());
        List<UcaSurveyOption> lists = crit.list();
        
        if(lists != null && lists.size() > 0) {
        	for(UcaSurveyOption ucaSurveyOption : lists) {
        		ucaSurveyOption.setUcaSurvey(null);
        	}
            object.put("total", total);
            object.put("rows", lists);
        } else {
            object.put("total", 0);
            object.put("rows", new ArrayList<UcaSurveyOption>());
        }
	}
	
	@Override
	public void saveOrUpdate(JSONObject object, UcaSurveyOption po) {
		// TODO Auto-generated method stub
		if(po.getId() == null){
			this.saveObj(object, po);
		}else{
			this.updateObj(object, po);
		}
	}
	
	private void saveObj(JSONObject object, UcaSurveyOption po) {
		UcaSurvey ucaSurvey = ucaSurveyDao.queryById(po.getSurveyId());
		if(null == ucaSurvey) {
			object.put("success", false);
			object.put("msg", "问卷调查不存在！");
			return;
		}
		
		po.setUcaSurvey(ucaSurvey);
		
		po.setCreateUserId(secUtils.getUser().getUserId());
		po.setUpdateUserId(secUtils.getUser().getUserId());
		Date time = new Date();
		po.setCreateTime(time);
		po.setUpdateTime(time);
		ucaSurveyOptionDao.saveOrUpdate(po);
		
		if(po.getId() > 0) {
			object.put("success", true);
	        object.put("msg", "添加成功！");
		} else {
			object.put("success", false);
	        object.put("msg", "添加失败！");
		}
	}
	
	private void updateObj(JSONObject object, UcaSurveyOption po) {
		UcaSurveyOption uso = ucaSurveyOptionDao.queryById(po.getId());
		if(null == uso) {
			object.put("success", false);
			object.put("msg", "修改的数据记录不存在！");
			return;
		}
		
		if(null != uso.getCreateUserId() && uso.getCreateUserId().intValue() != secUtils.getUser().getUserId()) {
			object.put("success", false);
			object.put("msg", "当前用户与选择的数据记录创建人不匹配，不能修改数据！");
			return;
		}
		
		if(uso.getUcaSurvey().getId() != po.getSurveyId()) {
			object.put("success", false);
			object.put("msg", "当前选项所属的问卷调查与选中的问卷调查不匹配，不能修改数据！");
			return;
		}
		
		uso.setOptions(po.getOptions());
		
		uso.setUpdateUserId(secUtils.getUser().getUserId());
		uso.setUpdateTime(new Date());
		ucaSurveyOptionDao.saveOrUpdate(uso);
		
		object.put("success", true);
        object.put("msg", "修改成功！");
	}

	@Override
	public void getById(JSONObject object, int id) {
		// TODO Auto-generated method stub
		UcaSurveyOption po = ucaSurveyOptionDao.queryById(id);
		if(null == po) {
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
			return;
		}
		
		po.setSurveyId(po.getUcaSurvey().getId());
		
		po.setUcaSurvey(null);
		
		object.put("success", true);
		object.put("objPo", po);
	}
	
	@Override
	public void delete(JSONObject object, int id) {
		// TODO Auto-generated method stub
		UcaSurveyOption po = ucaSurveyOptionDao.queryById(id);
		if(null == po) {
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
			return;
		}
		
		if(null != po.getCreateUserId() && po.getCreateUserId().intValue() != secUtils.getUser().getUserId()) {
			object.put("success", false);
			object.put("msg", "当前用户与选择的数据记录创建人不匹配，不能删除数据！");
			return;
		}
		
		ucaSurveyOptionDao.delete(po);
		
		object.put("success", true);
        object.put("msg", "删除成功！");
	}

	@Override
	public List<UcaSurveyOption> getSurveyOptionListBySurveyId(int surveyId) {
		// TODO Auto-generated method stub
		List<UcaSurveyOption> lists = new ArrayList<UcaSurveyOption>();
		Criteria crit = ucaSurveyOptionDao.getSession().createCriteria(UcaSurveyOption.class);
//		crit.createAlias("ucaSurvey", "us");
        crit.add(Restrictions.eq("ucaSurvey.id", surveyId));
		crit.addOrder(Order.asc("id"));
		lists = crit.list();
		return lists;
	}

	@Override
	public List<UcaSurveyOption> getSurveyOptionStatisticsBySurveyId(
			int surveyId) {
		// TODO Auto-generated method stub
		List<UcaSurveyOption> lists = new ArrayList<UcaSurveyOption>();
		Criteria crit = ucaSurveyOptionDao.getSession().createCriteria(UcaSurveyOption.class);
//		crit.createAlias("ucaSurvey", "us");
        crit.add(Restrictions.eq("ucaSurvey.id", surveyId));
		crit.addOrder(Order.asc("id"));
		lists = crit.list();
		if(null != lists && lists.size() > 0) {
			for(UcaSurveyOption surveyOption : lists) {
//				ucaSurveyOptionResultDao
				int count = getOptionCount(surveyId, surveyOption.getId());
				surveyOption.setCount(count);
			}
			return lists;
		}
		return null;
	}
	
    private int getOptionCount(int surveyId, int optionId) {
        String sql = "SELECT count(*) FROM uca_survey_option_result u where u.survey_id=:surveyId and option_id=:optionId";
        SQLQuery query = ucaSurveyOptionResultDao.getSession().createSQLQuery(sql);
        query.setParameter("surveyId", surveyId);
        query.setParameter("optionId", optionId);
        Object count = query.uniqueResult();
        if(ParameterUtil.isNumber(String.valueOf(count))) {
            if(Integer.parseInt(String.valueOf(count)) > 0) {
                return Integer.parseInt(String.valueOf(count));
            }
        }
        return 0;
    }

}
