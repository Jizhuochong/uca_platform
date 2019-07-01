package com.uca.survey.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
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
import com.uca.survey.pojos.UcaSurvey;
import com.uca.survey.service.UcaSurveyService;

@Service
public class UcaSurveyServiceImpl implements UcaSurveyService{
	private SecurityIdentifyUtils secUtils = new SecurityUtils();
	
	@Autowired
	private UcaSurveyDao ucaSurveyDao;
	
	@Override
	public void find(JSONObject object, Page<UcaSurvey> page, int type, String sd, String ed) {
		// TODO Auto-generated method stub
		Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }
        
        Criteria crit = ucaSurveyDao.getSession().createCriteria(UcaSurvey.class);
//        crit.add(Restrictions.eq("createUserId", secUtils.getUser().getUserId()));
        if(type > 0) {
        	crit.add(Restrictions.eq("type", type));
        }
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
        List<UcaSurvey> lists = crit.list();
        
        if(lists != null && lists.size() > 0) {
            object.put("total", total);
            object.put("rows", lists);
        } else {
            object.put("total", 0);
            object.put("rows", new ArrayList<UcaSurvey>());
        }
	}
	
	@Override
	public void saveOrUpdate(JSONObject object, UcaSurvey po) {
		// TODO Auto-generated method stub
		if(po.getId() == null){
			this.saveObj(object, po);
		}else{
			this.updateObj(object, po);
		}
	}
	
	private void saveObj(JSONObject object, UcaSurvey po) {
		po.setCreateUserId(secUtils.getUser().getUserId());
		po.setUpdateUserId(secUtils.getUser().getUserId());
		Date time = new Date();
		po.setCreateTime(time);
		po.setUpdateTime(time);
		ucaSurveyDao.saveOrUpdate(po);
		
		if(po.getId() > 0) {
			object.put("success", true);
	        object.put("msg", "添加成功！");
		} else {
			object.put("success", false);
	        object.put("msg", "添加失败！");
		}
	}
	
	private void updateObj(JSONObject object, UcaSurvey po) {
		UcaSurvey us = ucaSurveyDao.queryById(po.getId());
		if(null == us) {
			object.put("success", false);
			object.put("msg", "修改的数据记录不存在！");
			return;
		}
		
		if(null != us.getCreateUserId() && us.getCreateUserId().intValue() != secUtils.getUser().getUserId()) {
			object.put("success", false);
			object.put("msg", "当前用户与选择的数据记录创建人不匹配，不能修改数据！");
			return;
		}
		
		us.setTitle(po.getTitle());
		us.setUpdateUserId(secUtils.getUser().getUserId());
		us.setUpdateTime(new Date());
		ucaSurveyDao.saveOrUpdate(us);
		
		object.put("success", true);
        object.put("msg", "修改成功！");
	}

	@Override
	public void getById(JSONObject object, int id) {
		// TODO Auto-generated method stub
		UcaSurvey po = ucaSurveyDao.queryById(id);
		if(null == po) {
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
			return;
		}
		
		object.put("success", true);
		object.put("objPo", po);
	}
	
	@Override
	public void delete(JSONObject object, int id) {
		// TODO Auto-generated method stub
		UcaSurvey po = ucaSurveyDao.queryById(id);
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
		
		ucaSurveyDao.delete(po);
		
		object.put("success", true);
        object.put("msg", "删除成功！");
	}
	
	@Override
	public UcaSurvey getById(int id) {
		// TODO Auto-generated method stub
		UcaSurvey po = ucaSurveyDao.queryById(id);
		return po;
	}

}
