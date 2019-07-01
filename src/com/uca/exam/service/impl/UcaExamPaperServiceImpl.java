package com.uca.exam.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.constants.UcaConstants;
import com.uca.exam.dao.UcaExamPaperDao;
import com.uca.exam.pojos.UcaExaminationPaper;
import com.uca.exam.pojos.UcaExaminationQuestion;
import com.uca.exam.service.UcaExamPaperService;
import com.uca.exam.service.UcaExamQuestionService;
import com.uca.exam.vo.UcaExamQuestionVo;

@Service
public class UcaExamPaperServiceImpl implements UcaExamPaperService{
	private SecurityIdentifyUtils secUtils = new SecurityUtils();
	
	@Autowired
	private UcaExamPaperDao ucaExamPaperDao;
	
	@Autowired
    private UcaExamQuestionService ucaExamQuestionService;
	
	@Override
	public void find(JSONObject object, Page<UcaExaminationPaper> page, String epName, String sd, String ed) {
		// TODO Auto-generated method stub
		Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }
        
        Criteria crit = ucaExamPaperDao.getSession().createCriteria(UcaExaminationPaper.class);
//        crit.add(Restrictions.eq("createUserId", secUtils.getUser().getUserId()));
        if(ParameterUtil.isNotNull(epName)) {
            crit.add(Restrictions.like("epName", epName, MatchMode.ANYWHERE));
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
        List<UcaExaminationPaper> lists = crit.list();
        
        if(lists != null && lists.size() > 0) {
        	for(UcaExaminationPaper po : lists) {
        		po.setUcaExaminationQuestions(null);
        	}
            object.put("total", total);
            object.put("rows", lists);
        } else {
            object.put("total", 0);
            object.put("rows", new ArrayList<UcaExaminationPaper>());
        }
	}
	
	@Override
	public void saveOrUpdate(JSONObject object, UcaExaminationPaper po) {
		// TODO Auto-generated method stub
		if(po.getEpId() == null){
			this.saveObj(object, po);
		}else{
			this.updateObj(object, po);
		}
	}
	
	private void saveObj(JSONObject object, UcaExaminationPaper po) {
		po.setStatus(UcaConstants.EXAM_PAPER_STATUS_NO_PUBLISH);
		po.setCreateUserId(secUtils.getUser().getUserId());
		po.setUpdateUserId(secUtils.getUser().getUserId());
		Date time = new Date();
		po.setCreateTime(time);
		po.setUpdateTime(time);
		ucaExamPaperDao.saveOrUpdate(po);
		
		if(po.getEpId() > 0) {
			object.put("success", true);
	        object.put("msg", "添加成功！");
		} else {
			object.put("success", false);
	        object.put("msg", "添加失败！");
		}
	}
	
	private void updateObj(JSONObject object, UcaExaminationPaper po) {
		UcaExaminationPaper uep = ucaExamPaperDao.queryById(po.getEpId());
		if(null == uep) {
			object.put("success", false);
			object.put("msg", "修改的数据记录不存在！");
			return;
		}
		
		if(null != uep.getCreateUserId() && uep.getCreateUserId().intValue() != secUtils.getUser().getUserId()) {
			object.put("success", false);
			object.put("msg", "当前用户与选择的数据记录创建人不匹配，不能修改数据！");
			return;
		}
		
		uep.setEpName(po.getEpName());
		uep.setExamTime(po.getExamTime());
		uep.setPassScore(po.getPassScore());
		uep.setPeriod(po.getPeriod());
		if(UcaConstants.EXAM_PAPER_STATUS_PUBLISH == uep.getStatus()) {
			uep.setStatus(UcaConstants.EXAM_PAPER_STATUS_NO_PUBLISH);
		}
		uep.setUpdateUserId(secUtils.getUser().getUserId());
		uep.setUpdateTime(new Date());
		ucaExamPaperDao.saveOrUpdate(uep);
		
		object.put("success", true);
        object.put("msg", "修改成功！");
	}

	@Override
	public void getById(JSONObject object, int id) {
		// TODO Auto-generated method stub
		UcaExaminationPaper po = ucaExamPaperDao.queryById(id);
		if(null == po) {
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
			return;
		}
		
		po.setUcaExaminationQuestions(null);
		
		object.put("success", true);
		object.put("objPo", po);
	}
	
	@Override
	public void delete(JSONObject object, int epId) {
		// TODO Auto-generated method stub
		UcaExaminationPaper po = ucaExamPaperDao.queryById(epId);
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
		
		ucaExamPaperDao.delete(po);
		
		object.put("success", true);
        object.put("msg", "删除成功！");
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void publish(JSONObject object, int epId) {
		// TODO Auto-generated method stub
		UcaExaminationPaper po = ucaExamPaperDao.queryById(epId);
		if(null == po) {
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
			return;
		}
		
		if(null != po.getCreateUserId() && po.getCreateUserId().intValue() != secUtils.getUser().getUserId()) {
			object.put("success", false);
			object.put("msg", "当前用户与选择的数据记录创建人不匹配，不能发布数据！");
			return;
		}
		
		po.setStatus(UcaConstants.EXAM_PAPER_STATUS_PUBLISH);
		po.setUpdateUserId(secUtils.getUser().getUserId());
		po.setUpdateTime(new Date());
		ucaExamPaperDao.saveOrUpdate(po);
		
		object.put("success", true);
        object.put("msg", "发布成功！");
	}
	
	@Override
	public void findOnlineExam(JSONObject object, Page<UcaExaminationPaper> page) {
		// TODO Auto-generated method stub
        
		StringBuilder sql = new StringBuilder(
				"SELECT u.ep_id,u.ep_name,u.exam_time FROM uca_examination_paper u where u.status=2 and "
						+ "u.ep_id not in (SELECT distinct(ua.ep_id) FROM uca_answer ua where ua.answer_user_id="
						+ secUtils.getUser().getUserId()+") order by u.create_time desc");
		
		String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
		SQLQuery query = ucaExamPaperDao.getSession().createSQLQuery(sql.append(pagingSql).toString());

		List<Object[]> list = query.list();
		List<UcaExaminationPaper> listResult = new ArrayList<UcaExaminationPaper>(list.size());
		UcaExaminationPaper vo = null;
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				vo = new UcaExaminationPaper();
				vo.setEpId(Integer.valueOf(obj[0].toString()));
				vo.setEpName(obj[1] != null ? obj[1].toString() : "");
				vo.setExamTime(obj[2] != null ? Integer.valueOf(obj[2].toString()) : 0);
				listResult.add(vo);
			}
		}

		page.setListResult(listResult);

		pagingSql = "SELECT count(1) FROM uca_examination_paper u "
				+ "where u.status=2 and u.ep_id not in (SELECT distinct(ua.ep_id) FROM "
				+ "uca_answer ua where ua.answer_user_id="+secUtils.getUser().getUserId()+")";
		
		query = ucaExamPaperDao.getSession().createSQLQuery(pagingSql);

		List countList = query.list();
		object.put("rows", page.getListResult());
		object.put("total", (BigInteger) countList.get(0));
		
		
//        Criteria crit = ucaExamPaperDao.getSession().createCriteria(UcaExaminationPaper.class);
//        crit.add(Restrictions.eq("status", UcaConstants.EXAM_PAPER_STATUS_PUBLISH));
//        int total = crit.list().size();
//        
//        crit.addOrder(Order.desc("createTime"));
//        crit.setFirstResult(page.getPageNo());
//        crit.setMaxResults(page.getPageSize());
//        List<UcaExaminationPaper> lists = crit.list();
//        
//        if(lists != null && lists.size() > 0) {
//        	for(UcaExaminationPaper po : lists) {
//        		po.setUcaExaminationQuestions(null);
//        	}
//            object.put("total", total);
//            object.put("rows", lists);
//        } else {
//            object.put("total", 0);
//            object.put("rows", new ArrayList<UcaExaminationPaper>());
//        }
	}

	@Override
	public UcaExaminationPaper getById(int epId) {
		// TODO Auto-generated method stub
		UcaExaminationPaper po = ucaExamPaperDao.queryById(epId);
		if(null == po) {
			return new UcaExaminationPaper();
		}
		
		po.setUcaExaminationQuestions(null);
		
		List<UcaExaminationQuestion> ucaExamQuestions = ucaExamQuestionService.getExamQuestionListByEpId(po.getEpId());
		if(null != ucaExamQuestions && ucaExamQuestions.size() > 0) {
			List<UcaExamQuestionVo> ucaExamQuestionVos = new ArrayList<UcaExamQuestionVo>();
			for(UcaExaminationQuestion examQuestion : ucaExamQuestions) {
				UcaExamQuestionVo vo = new UcaExamQuestionVo();
				vo.setEqId(examQuestion.getEqId());
				vo.setEqNumber(examQuestion.getEqNumber());
				vo.setName(examQuestion.getName());
				vo.setType(examQuestion.getType());
				
				String options = examQuestion.getOptions();
				String[] optionString = options.split("\\|");
				if(optionString.length > 0) {
					List<String> optionList = new ArrayList<String>();
					for(int i=0;i<optionString.length;i++) {
						optionList.add(optionString[i]);
					}
					vo.setOptionList(optionList);
				}
				ucaExamQuestionVos.add(vo);
			}
			po.setUcaExamQuestionVos(ucaExamQuestionVos);
		}
		return po;
	}

}
