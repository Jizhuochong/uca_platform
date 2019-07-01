package com.uca.exam.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
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
import com.uca.exam.dao.UcaExamQuestionClassifyDao;
import com.uca.exam.dao.UcaQuestionDao;
import com.uca.exam.pojos.UcaExamQuestionClassify;
import com.uca.exam.pojos.UcaQuestion;
import com.uca.exam.service.UcaQuestionService;

@Service
public class UcaQuestionServiceImpl implements UcaQuestionService{
	private SecurityIdentifyUtils secUtils = new SecurityUtils();
	
	@Autowired
	private UcaQuestionDao ucaQuestionDao;
	
	@Autowired
	private UcaExamQuestionClassifyDao ucaExamQuestionClassifyDao;
	
	@Override
	public void find(JSONObject object, Page<UcaQuestion> page, String name, String classifyId, String type, String sd, String ed) {
		// TODO Auto-generated method stub
		Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }
        
        Criteria crit = ucaQuestionDao.getSession().createCriteria(UcaQuestion.class);
//        crit.add(Restrictions.eq("createUserId", secUtils.getUser().getUserId()));
        if(ParameterUtil.isNotNull(classifyId) && ParameterUtil.isNumber(classifyId) && Integer.parseInt(classifyId) > 0) {
        	crit.add(Restrictions.eq("ucaExamQuestionClassify.id", Integer.parseInt(classifyId)));
        }
        if(ParameterUtil.isNotNull(type) && ParameterUtil.isNumber(type) && Integer.parseInt(type) > 0) {
        	crit.add(Restrictions.eq("type", Integer.parseInt(type)));
        }
        if(ParameterUtil.isNotNull(name)) {
            crit.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
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
        List<UcaQuestion> lists = crit.list();
        
        if(lists != null && lists.size() > 0) {
        	for(UcaQuestion po : lists) {
        		po.setClassifyId(po.getUcaExamQuestionClassify().getId());
        		po.setClassifyName(po.getUcaExamQuestionClassify().getName());
        		
        		po.setUcaExaminationQuestions(null);
        		po.setUcaExamQuestionClassify(null);
        	}
            object.put("total", total);
            object.put("rows", lists);
        } else {
            object.put("total", 0);
            object.put("rows", new ArrayList<UcaQuestion>());
        }
	}
	
	@Override
	public void saveOrUpdate(JSONObject object, UcaQuestion po) {
		// TODO Auto-generated method stub
		if(po.getQuestionId() == null){
			this.saveObj(object, po);
		}else{
			this.updateObj(object, po);
		}
	}
	
	private void saveObj(JSONObject object, UcaQuestion po) {
		UcaExamQuestionClassify ucaExamQuestionClassify = ucaExamQuestionClassifyDao.queryById(po.getClassifyId());
		if(null == ucaExamQuestionClassify) {
			object.put("success", false);
			object.put("msg", "试题分类不存在！");
			return;
		}
		po.setUcaExamQuestionClassify(ucaExamQuestionClassify);
		
		po.setCreateUserId(secUtils.getUser().getUserId());
		po.setUpdateUserId(secUtils.getUser().getUserId());
		Date time = new Date();
		po.setCreateTime(time);
		po.setUpdateTime(time);
		ucaQuestionDao.saveOrUpdate(po);
		
		if(po.getQuestionId() > 0) {
			object.put("success", true);
	        object.put("msg", "添加成功！");
		} else {
			object.put("success", false);
	        object.put("msg", "添加失败！");
		}
	}
	
	private void updateObj(JSONObject object, UcaQuestion po) {
		UcaQuestion uq = ucaQuestionDao.queryById(po.getQuestionId());
		if(null == uq) {
			object.put("success", false);
			object.put("msg", "修改的数据记录不存在！");
			return;
		}
		
		if(null != uq.getCreateUserId() && uq.getCreateUserId().intValue() != secUtils.getUser().getUserId()) {
			object.put("success", false);
			object.put("msg", "当前用户与选择的数据记录创建人不匹配，不能修改数据！");
			return;
		}
		
		UcaExamQuestionClassify ucaExamQuestionClassify = ucaExamQuestionClassifyDao.queryById(po.getClassifyId());
		if(null == ucaExamQuestionClassify) {
			object.put("success", false);
			object.put("msg", "试题分类不存在！");
			return;
		}
		uq.setUcaExamQuestionClassify(ucaExamQuestionClassify);
		
		uq.setType(po.getType());
		uq.setName(po.getName());
		uq.setOptions(po.getOptions());
		uq.setAnswer(po.getAnswer());
		uq.setUpdateUserId(secUtils.getUser().getUserId());
		uq.setUpdateTime(new Date());
		ucaQuestionDao.saveOrUpdate(uq);
		
		object.put("success", true);
        object.put("msg", "修改成功！");
	}

	@Override
	public void getById(JSONObject object, int id) {
		// TODO Auto-generated method stub
		UcaQuestion po = ucaQuestionDao.queryById(id);
		if(null == po) {
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
			return;
		}
		
		po.setClassifyId(po.getUcaExamQuestionClassify().getId());
		po.setClassifyName(po.getUcaExamQuestionClassify().getName());
		
		po.setUcaExaminationQuestions(null);
		po.setUcaExamQuestionClassify(null);
		
		object.put("success", true);
		object.put("objPo", po);
	}
	
	@Override
	public void delete(JSONObject object, int questionId) {
		// TODO Auto-generated method stub
		UcaQuestion po = ucaQuestionDao.queryById(questionId);
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
		
		ucaQuestionDao.delete(po);
		
		object.put("success", true);
        object.put("msg", "删除成功！");
	}

	@Override
	public List<UcaQuestion> getQuestionByType(int type, String classifyId) {
		// TODO Auto-generated method stub
		Criteria crit = ucaQuestionDao.getSession().createCriteria(UcaQuestion.class);
		if(ParameterUtil.isNotNull(classifyId) && ParameterUtil.isNumber(classifyId) && Integer.parseInt(classifyId) > 0) {
        	crit.add(Restrictions.eq("ucaExamQuestionClassify.id", Integer.parseInt(classifyId)));
        }
		crit.add(Restrictions.eq("type", type));
		crit.addOrder(Order.desc("createTime"));
		return crit.list();
	}

}
