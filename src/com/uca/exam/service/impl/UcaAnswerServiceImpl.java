package com.uca.exam.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.capinfo.auth.dao.UserDao;
import cn.com.capinfo.auth.pojos.User;
import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.constants.UcaConstants;
import com.uca.exam.dao.UcaAnswerDao;
import com.uca.exam.dao.UcaExamPaperDao;
import com.uca.exam.dao.UcaExamQuestionDao;
import com.uca.exam.pojos.UcaAnswer;
import com.uca.exam.pojos.UcaCredit;
import com.uca.exam.pojos.UcaExaminationPaper;
import com.uca.exam.pojos.UcaExaminationQuestion;
import com.uca.exam.service.UcaAnswerService;
import com.uca.exam.service.UcaCreditService;

@Service
public class UcaAnswerServiceImpl implements UcaAnswerService {
	private SecurityIdentifyUtils secUtils = new SecurityUtils();

	@Autowired
	private UcaAnswerDao ucaAnswerDao;
	
	@Autowired
	private UcaExamPaperDao ucaExamPaperDao;
	
	@Autowired
	private UcaExamQuestionDao ucaExamQuestionDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
    private UcaCreditService ucaCreditService;

	@Override
	public void find(JSONObject object, Page<UcaAnswer> page, String epName, String userName) {
		// TODO Auto-generated method stub
		/**
		 * select ua.answer_id,ua.ep_id,ua.eq_id,ua.answer_user_id,ua.answer_time,ep.ep_name,eq.name,u.user_name
from uca_answer as ua
left join uca_examination_paper as ep on ua.ep_id = ep.ep_id
left join uca_examination_question as eq on ua.eq_id = eq.eq_id
left join um_user as u on ua.answer_user_id = u.user_id
where ep.ep_name like '%ddd%' and u.user_name like '%ddd%'
order by ua.ep_id desc, ua.answer_time asc;
		 * */
		StringBuilder sql = new StringBuilder(
				"select ua.answer_id,ua.ep_id,ua.eq_id,ua.answer_user_id,ua.answer_time,ep.ep_name,"
						+ "eq.name,u.user_name,eq.eq_number,ua.score,ua.status "
						+ "from uca_answer as ua "
						+ "left join uca_examination_paper as ep on ua.ep_id = ep.ep_id "
						+ "left join uca_examination_question as eq on ua.eq_id = eq.eq_id "
						+ "left join um_user as u on ua.answer_user_id = u.user_id");
		
		if(ParameterUtil.isNotNull(epName) && ParameterUtil.isNotNull(userName)) {
			sql.append(" where ep.ep_name like '%").append(epName)
				.append("%' and u.user_name like '%").append(userName).append("%'");
		} else {
			if(ParameterUtil.isNotNull(epName)) {
				sql.append(" where ep.ep_name like '%").append(epName).append("%'");
			}
			if(ParameterUtil.isNotNull(userName)) {
				sql.append(" where u.user_name like '%").append(userName).append("%'");
			}
		}
		sql.append(" order by ua.ep_id desc, eq.eq_number asc, ua.answer_time asc");
		
		String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
		SQLQuery query = ucaAnswerDao.getSession().createSQLQuery(sql.append(pagingSql).toString());

		List<Object[]> list = query.list();
		List<UcaAnswer> listResult = new ArrayList<UcaAnswer>(list.size());
		UcaAnswer vo = null;
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				vo = new UcaAnswer();
				vo.setAnswerId(Integer.valueOf(obj[0].toString()));
				vo.setEpId(Integer.valueOf(obj[1].toString()));
				vo.setEqId(Integer.valueOf(obj[2].toString()));
				vo.setAnswerUserId(Integer.valueOf(obj[3].toString()));
				vo.setAnswerTimeStr(obj[4] != null?DateUtil.DateToStr((Date)obj[4]):"");
				vo.setEpName(obj[5] != null ? obj[5].toString() : "");
				vo.setEqName(obj[6] != null ? obj[6].toString() : "");
				vo.setUserName(obj[7] != null ? obj[7].toString() : "");
				vo.setEqNumber(obj[8] != null ? Integer.valueOf(obj[8].toString()) : 0);
				vo.setScore(obj[9] != null ? Integer.valueOf(obj[9].toString()) : 0);
				vo.setStatus(obj[10] != null ? Integer.valueOf(obj[10].toString()) : 0);
				listResult.add(vo);
			}
		}

		page.setListResult(listResult);

		/**
		 * select count(1) from uca_answer as ua
left join uca_examination_paper as ep on ua.ep_id = ep.ep_id
left join uca_examination_question as eq on ua.eq_id = eq.eq_id
left join um_user as u on ua.answer_user_id = u.user_id
where ep.ep_name like '%ddd%' and u.user_name like '%ddd%';
		 * */
		pagingSql = "select count(1) from uca_answer as ua "
				+ "left join uca_examination_paper as ep on ua.ep_id = ep.ep_id "
				+ "left join uca_examination_question as eq on ua.eq_id = eq.eq_id "
				+ "left join um_user as u on ua.answer_user_id = u.user_id";
		
		if(ParameterUtil.isNotNull(epName) && ParameterUtil.isNotNull(userName)) {
			pagingSql += " where ep.ep_name like '%"+epName+"%' and u.user_name like '%"+userName+"%'";
		} else {
			if(ParameterUtil.isNotNull(epName)) {
				pagingSql += " where ep.ep_name like '%"+epName+"%'";
			}
			if(ParameterUtil.isNotNull(userName)) {
				pagingSql += " where u.user_name like '%"+userName+"%'";
			}
		}
		
		query = ucaAnswerDao.getSession().createSQLQuery(pagingSql);

		List countList = query.list();
		object.put("rows", page.getListResult());
		object.put("total", (BigInteger) countList.get(0));
	}

	@Override
	public void saveOrUpdateList(List<UcaAnswer> answerList) {
		// TODO Auto-generated method stub
		ucaAnswerDao.saveOrUpdateAll(answerList);
	}

	@Override
	public List<UcaAnswer> getAnswerListByEpIdUserId(int epId, int userId) {
		// TODO Auto-generated method stub
		List<UcaAnswer> lists = new ArrayList<UcaAnswer>();
		Criteria crit = ucaAnswerDao.getSession().createCriteria(UcaAnswer.class);
        crit.add(Restrictions.eq("epId", epId));
        crit.add(Restrictions.eq("answerUserId", userId));
		lists = crit.list();
		return lists;
	}

	@Override
	public void getById(JSONObject object, int answerId) {
		// TODO Auto-generated method stub
		UcaAnswer po = ucaAnswerDao.queryById(answerId);
		if(null == po) {
			object.put("success", false);
			object.put("msg", "答案记录不存在！");
			return;
		}
		User user = userDao.queryById(po.getAnswerUserId());
		if(null == user) {
			object.put("success", false);
			object.put("msg", "答题人不存在！");
			return;
		}
		UcaExaminationPaper examPaper = ucaExamPaperDao.queryById(po.getEpId());
		if(null == examPaper) {
			object.put("success", false);
			object.put("msg", "试卷记录不存在！");
			return;
		}
		UcaExaminationQuestion examQuestion = ucaExamQuestionDao.queryById(po.getEqId());
		if(null == examQuestion) {
			object.put("success", false);
			object.put("msg", "试题记录不存在！");
			return;
		}
		
		object.put("answerId", po.getAnswerId());
		object.put("answers", po.getAnswers());
		
//		object.put("answerUserId", po.getAnswerUserId());
		object.put("answerUser", user.getUserName());
		
//		object.put("epId", examPaper.getEpId());
		object.put("epName", examPaper.getEpName());
		
//		object.put("eqId", examQuestion.getEqId());
//		object.put("eqNumber", examQuestion.getEqNumber());
		object.put("eqName", examQuestion.getName());
		object.put("eqOptions", examQuestion.getOptions());
		object.put("eqAnswer", examQuestion.getAnswer());
		object.put("eqScore", examQuestion.getScore());
		object.put("eqType", examQuestion.getType());
		
		object.put("success", true);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void commitScore(JSONObject object, int answerId, String score) {
		// TODO Auto-generated method stub
		UcaAnswer po = ucaAnswerDao.queryById(answerId);
		if(null == po) {
			object.put("success", false);
			object.put("msg", "答案记录不存在！");
			return;
		}
		UcaExaminationQuestion examQuestion = ucaExamQuestionDao.queryById(po.getEqId());
		if(null == examQuestion) {
			object.put("success", false);
			object.put("msg", "试题记录不存在！");
			return;
		}
		
		if(examQuestion.getType() != UcaConstants.EXAM_QUESTION_TYPE_WENDATI) {
			if(examQuestion.getAnswer().equals(po.getAnswers())) {
				po.setScore(examQuestion.getScore());
				po.setReadOverId(secUtils.getUser().getUserId());
				po.setReadOverTime(new Date());
				po.setStatus(UcaConstants.ANSWER_STATUS_READOVER);
				
				ucaAnswerDao.saveOrUpdate(po);
				
				UcaCredit ucaCredit = ucaCreditService.getObjByEpIdAnswerUserId(po.getEpId(), po.getAnswerUserId());
				if(null != ucaCredit) {
					int results = 0;
					if(null != ucaCredit.getResults()) {
						results = ucaCredit.getResults().intValue();
					}
					ucaCredit.setResults(results + (null != po.getScore() ? po.getScore().intValue() : 0));
					ucaCredit.setCredits(ucaCredit.getResults());
					ucaCreditService.saveOrUpdate(ucaCredit);
				} else {
					ucaCredit = new UcaCredit();
					ucaCredit.setEpId(po.getEpId());
					ucaCredit.setAnswerUserId(po.getAnswerUserId());
					ucaCredit.setCreateTime(new Date());
					int results = 0;
					if(null != ucaCredit.getResults()) {
						results = ucaCredit.getResults().intValue();
					}
					ucaCredit.setResults(results + (null != po.getScore() ? po.getScore().intValue() : 0));
					ucaCredit.setCredits(ucaCredit.getResults());
					ucaCreditService.saveOrUpdate(ucaCredit);
				}
				
				object.put("success", true);
				object.put("msg", "打分成功，答案正确，获得"+examQuestion.getScore()+"分！");
			} else {
				po.setScore(0);
				po.setReadOverId(secUtils.getUser().getUserId());
				po.setReadOverTime(new Date());
				po.setStatus(UcaConstants.ANSWER_STATUS_READOVER);
				
				ucaAnswerDao.saveOrUpdate(po);
				
				UcaCredit ucaCredit = ucaCreditService.getObjByEpIdAnswerUserId(po.getEpId(), po.getAnswerUserId());
				if(null != ucaCredit) {
					int results = 0;
					if(null != ucaCredit.getResults()) {
						results = ucaCredit.getResults().intValue();
					}
					ucaCredit.setResults(results + (null != po.getScore() ? po.getScore().intValue() : 0));
					ucaCredit.setCredits(ucaCredit.getResults());
					ucaCreditService.saveOrUpdate(ucaCredit);
				} else {
					ucaCredit = new UcaCredit();
					ucaCredit.setEpId(po.getEpId());
					ucaCredit.setAnswerUserId(po.getAnswerUserId());
					ucaCredit.setCreateTime(new Date());
					int results = 0;
					if(null != ucaCredit.getResults()) {
						results = ucaCredit.getResults().intValue();
					}
					ucaCredit.setResults(results + (null != po.getScore() ? po.getScore().intValue() : 0));
					ucaCredit.setCredits(ucaCredit.getResults());
					ucaCreditService.saveOrUpdate(ucaCredit);
				}
				
				object.put("success", true);
				object.put("msg", "打分成功，答案错误，获得零分！");
			}
		} else {
			po.setScore(Integer.parseInt(score));
			po.setReadOverId(secUtils.getUser().getUserId());
			po.setReadOverTime(new Date());
			po.setStatus(UcaConstants.ANSWER_STATUS_READOVER);
			
			ucaAnswerDao.saveOrUpdate(po);
			
			UcaCredit ucaCredit = ucaCreditService.getObjByEpIdAnswerUserId(po.getEpId(), po.getAnswerUserId());
			if(null != ucaCredit) {
				int results = 0;
				if(null != ucaCredit.getResults()) {
					results = ucaCredit.getResults().intValue();
				}
				ucaCredit.setResults(results + (null != po.getScore() ? po.getScore().intValue() : 0));
				ucaCredit.setCredits(ucaCredit.getResults());
				ucaCreditService.saveOrUpdate(ucaCredit);
			} else {
				ucaCredit = new UcaCredit();
				ucaCredit.setEpId(po.getEpId());
				ucaCredit.setAnswerUserId(po.getAnswerUserId());
				ucaCredit.setCreateTime(new Date());
				int results = 0;
				if(null != ucaCredit.getResults()) {
					results = ucaCredit.getResults().intValue();
				}
				ucaCredit.setResults(results + (null != po.getScore() ? po.getScore().intValue() : 0));
				ucaCredit.setCredits(ucaCredit.getResults());
				ucaCreditService.saveOrUpdate(ucaCredit);
			}
			
			object.put("success", true);
			object.put("msg", "打分成功，该题获得"+score+"分！");
		}
	}

}
