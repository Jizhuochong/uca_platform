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
import com.uca.exam.dao.UcaExamPaperDao;
import com.uca.exam.dao.UcaExamQuestionClassifyDao;
import com.uca.exam.dao.UcaExamQuestionDao;
import com.uca.exam.dao.UcaQuestionDao;
import com.uca.exam.pojos.UcaExamQuestionClassify;
import com.uca.exam.pojos.UcaExaminationPaper;
import com.uca.exam.pojos.UcaExaminationQuestion;
import com.uca.exam.pojos.UcaQuestion;
import com.uca.exam.service.UcaExamQuestionService;

@Service
public class UcaExamQuestionServiceImpl implements UcaExamQuestionService{
	private SecurityIdentifyUtils secUtils = new SecurityUtils();
	
	@Autowired
	private UcaExamQuestionDao ucaExamQuestionDao;
	
	@Autowired
	private UcaExamPaperDao ucaExamPaperDao;
	
	@Autowired
	private UcaQuestionDao ucaQuestionDao;
	
	@Autowired
	private UcaExamQuestionClassifyDao ucaExamQuestionClassifyDao;
	
	@Override
	public void find(JSONObject object, Page<UcaExaminationQuestion> page, 
			int epId, String name, String classifyId, String type, String sd, String ed) {
		// TODO Auto-generated method stub
		Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }
        
        Criteria crit = ucaExamQuestionDao.getSession().createCriteria(UcaExaminationQuestion.class);
//        crit.add(Restrictions.eq("createUserId", secUtils.getUser().getUserId()));
        crit.add(Restrictions.eq("ucaExaminationPaper.epId", epId));
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
        
        crit.addOrder(Order.asc("eqNumber"));
        crit.setFirstResult(page.getPageNo());
        crit.setMaxResults(page.getPageSize());
        List<UcaExaminationQuestion> lists = crit.list();
        
        if(lists != null && lists.size() > 0) {
        	for(UcaExaminationQuestion po : lists) {
        		po.setClassifyId(po.getUcaExamQuestionClassify().getId());
        		po.setClassifyName(po.getUcaExamQuestionClassify().getName());
        		
        		po.setUcaExamQuestionClassify(null);
        		po.setUcaExaminationPaper(null);
        		po.setUcaQuestion(null);
        	}
            object.put("total", total);
            object.put("rows", lists);
        } else {
            object.put("total", 0);
            object.put("rows", new ArrayList<UcaExaminationQuestion>());
        }
	}
	
	@Override
	public void saveOrUpdate(JSONObject object, UcaExaminationQuestion po) {
		// TODO Auto-generated method stub
		if(po.getEqId() == null){
			this.saveObj(object, po);
		}else{
			this.updateObj(object, po);
		}
	}
	
	private void saveObj(JSONObject object, UcaExaminationQuestion po) {
		UcaExaminationPaper ucaExaminationPaper = ucaExamPaperDao.queryById(po.getEpId());
		if(null == ucaExaminationPaper) {
			object.put("success", false);
			object.put("msg", "试卷不存在！");
			return;
		}
		UcaQuestion ucaQuestion = ucaQuestionDao.queryById(po.getQuestionId());
		if(null == ucaQuestion) {
			object.put("success", false);
			object.put("msg", "考题不存在！");
			return;
		}
		UcaExamQuestionClassify ucaExamQuestionClassify = ucaExamQuestionClassifyDao.queryById(po.getClassifyId());
		if(null == ucaExamQuestionClassify) {
			object.put("success", false);
			object.put("msg", "试题分类不存在！");
			return;
		}
		po.setUcaExamQuestionClassify(ucaExamQuestionClassify);
		po.setUcaExaminationPaper(ucaExaminationPaper);
		po.setUcaQuestion(ucaQuestion);
		
		po.setType(ucaQuestion.getType());
		po.setName(ucaQuestion.getName());
		po.setOptions(ucaQuestion.getOptions());
		po.setAnswer(ucaQuestion.getAnswer());
		
		po.setCreateUserId(secUtils.getUser().getUserId());
		po.setUpdateUserId(secUtils.getUser().getUserId());
		Date time = new Date();
		po.setCreateTime(time);
		po.setUpdateTime(time);
		ucaExamQuestionDao.saveOrUpdate(po);
		
		if(po.getEqId() > 0) {
			object.put("success", true);
	        object.put("msg", "添加成功！");
		} else {
			object.put("success", false);
	        object.put("msg", "添加失败！");
		}
	}
	
	private void updateObj(JSONObject object, UcaExaminationQuestion po) {
		UcaExaminationQuestion ueq = ucaExamQuestionDao.queryById(po.getEqId());
		if(null == ueq) {
			object.put("success", false);
			object.put("msg", "修改的数据记录不存在！");
			return;
		}
		
		if(null != ueq.getCreateUserId() && ueq.getCreateUserId().intValue() != secUtils.getUser().getUserId()) {
			object.put("success", false);
			object.put("msg", "当前用户与选择的数据记录创建人不匹配，不能修改数据！");
			return;
		}
		
		if(ueq.getUcaExaminationPaper().getEpId() != po.getEpId()) {
			object.put("success", false);
			object.put("msg", "当前试卷与选择的试题不匹配，不能修改数据！");
			return;
		}
		
		UcaQuestion ucaQuestion = ucaQuestionDao.queryById(po.getQuestionId());
		if(null == ucaQuestion) {
			object.put("success", false);
			object.put("msg", "考题不存在！");
			return;
		}
		
		UcaExamQuestionClassify ucaExamQuestionClassify = ucaExamQuestionClassifyDao.queryById(po.getClassifyId());
		if(null == ucaExamQuestionClassify) {
			object.put("success", false);
			object.put("msg", "试题分类不存在！");
			return;
		}
		ueq.setUcaExamQuestionClassify(ucaExamQuestionClassify);
		ueq.setUcaQuestion(ucaQuestion);
		
		ueq.setEqNumber(po.getEqNumber());
		ueq.setScore(po.getScore());
		
		ueq.setType(ucaQuestion.getType());
		ueq.setName(ucaQuestion.getName());
		ueq.setOptions(ucaQuestion.getOptions());
		ueq.setAnswer(ucaQuestion.getAnswer());
		
		ueq.setUpdateUserId(secUtils.getUser().getUserId());
		ueq.setUpdateTime(new Date());
		ucaExamQuestionDao.saveOrUpdate(ueq);
		
		object.put("success", true);
        object.put("msg", "修改成功！");
	}

	@Override
	public void getById(JSONObject object, int id) {
		// TODO Auto-generated method stub
		UcaExaminationQuestion po = ucaExamQuestionDao.queryById(id);
		if(null == po) {
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
			return;
		}
		
		po.setEpId(po.getUcaExaminationPaper().getEpId());
		po.setQuestionId(po.getUcaQuestion().getQuestionId());
		po.setClassifyId(po.getUcaExamQuestionClassify().getId());
		po.setClassifyName(po.getUcaExamQuestionClassify().getName());
		
		po.setUcaExamQuestionClassify(null);
		po.setUcaExaminationPaper(null);
		po.setUcaQuestion(null);
		
		object.put("success", true);
		object.put("objPo", po);
	}
	
	@Override
	public void delete(JSONObject object, int eqId) {
		// TODO Auto-generated method stub
		UcaExaminationQuestion po = ucaExamQuestionDao.queryById(eqId);
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
		
		ucaExamQuestionDao.delete(po);
		
		object.put("success", true);
        object.put("msg", "删除成功！");
	}

	@Override
	public List<UcaExaminationQuestion> getExamQuestionListByEpId(Integer epId) {
		// TODO Auto-generated method stub
		List<UcaExaminationQuestion> lists = new ArrayList<UcaExaminationQuestion>();
		Criteria crit = ucaExamQuestionDao.getSession().createCriteria(UcaExaminationQuestion.class);
		crit.createAlias("ucaExaminationPaper", "ep");
        crit.add(Restrictions.eq("ep.epId", epId));
		crit.addOrder(Order.asc("eqNumber"));
		lists = crit.list();
		return lists;
	}

}
