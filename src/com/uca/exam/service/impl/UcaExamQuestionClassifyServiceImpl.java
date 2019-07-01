package com.uca.exam.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;

import com.alibaba.fastjson.JSONObject;
import com.uca.exam.dao.UcaExamQuestionClassifyDao;
import com.uca.exam.pojos.UcaExamQuestionClassify;
import com.uca.exam.service.UcaExamQuestionClassifyService;

@Service
public class UcaExamQuestionClassifyServiceImpl implements UcaExamQuestionClassifyService {
	
	@Autowired
	private UcaExamQuestionClassifyDao ucaExamQuestionClassifyDao;
	
	@Override
	public void find(JSONObject object, Page<UcaExamQuestionClassify> page, String name) {
		// TODO Auto-generated method stub
        Criteria crit = ucaExamQuestionClassifyDao.getSession().createCriteria(UcaExamQuestionClassify.class);
        if(ParameterUtil.isNotNull(name)) {
            crit.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        int total = crit.list().size();
        
        crit.setFirstResult(page.getPageNo());
        crit.setMaxResults(page.getPageSize());
        List<UcaExamQuestionClassify> lists = crit.list();
        
        if(lists != null && lists.size() > 0) {
            object.put("total", total);
            object.put("rows", lists);
        } else {
            object.put("total", 0);
            object.put("rows", new ArrayList<UcaExamQuestionClassify>());
        }
	}
	
	@Override
	public void saveOrUpdate(JSONObject object, UcaExamQuestionClassify po) {
		// TODO Auto-generated method stub
		if(po.getId() == null){
			this.saveObj(object, po);
		}else{
			this.updateObj(object, po);
		}
	}
	
	private void saveObj(JSONObject object, UcaExamQuestionClassify po) {
		ucaExamQuestionClassifyDao.saveOrUpdate(po);
		
		if(po.getId() > 0) {
			object.put("success", true);
	        object.put("msg", "添加成功！");
		} else {
			object.put("success", false);
	        object.put("msg", "添加失败！");
		}
	}
	
	private void updateObj(JSONObject object, UcaExamQuestionClassify po) {
		UcaExamQuestionClassify ucaExamQuestionClassify = ucaExamQuestionClassifyDao.queryById(po.getId());
		if(null == ucaExamQuestionClassify) {
			object.put("success", false);
			object.put("msg", "修改的数据记录不存在！");
			return;
		}
		
		ucaExamQuestionClassify.setName(po.getName());
		ucaExamQuestionClassifyDao.saveOrUpdate(ucaExamQuestionClassify);
		
		object.put("success", true);
        object.put("msg", "修改成功！");
	}

	@Override
	public void getById(JSONObject object, int id) {
		// TODO Auto-generated method stub
		UcaExamQuestionClassify po = ucaExamQuestionClassifyDao.queryById(id);
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
		UcaExamQuestionClassify po = ucaExamQuestionClassifyDao.queryById(id);
		if(null == po) {
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
			return;
		}
		
		ucaExamQuestionClassifyDao.delete(po);
		
		object.put("success", true);
        object.put("msg", "删除成功！");
	}
	
	@Override
	public List<UcaExamQuestionClassify> findDatas() {
		// TODO Auto-generated method stub
        Criteria crit = ucaExamQuestionClassifyDao.getSession().createCriteria(UcaExamQuestionClassify.class);
        return crit.list();
	}

}
