package com.uca.exam.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;

import com.alibaba.fastjson.JSONObject;
import com.uca.exam.dao.UcaCreditDao;
import com.uca.exam.pojos.UcaCredit;
import com.uca.exam.service.UcaCreditService;
import com.uca.exam.vo.UcaCreditStatisticsVo;

@Service
public class UcaCreditServiceImpl implements UcaCreditService {

	@Autowired
	private UcaCreditDao ucaCreditDao;

	@Override
	public void find(JSONObject object, Page<UcaCredit> page, String epName, String userName) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder(
				"select uc.credit_id,uc.ep_id,uc.answer_user_id,uc.results,uc.credits,ep.ep_name,u.user_name "
						+ "from uca_credit as uc "
						+ "left join um_user as u on uc.answer_user_id = u.user_id "
						+ "left join uca_examination_paper as ep on uc.ep_id = ep.ep_id");
		
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
		sql.append(" order by uc.ep_id asc, uc.create_time desc");
		
		String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
		SQLQuery query = ucaCreditDao.getSession().createSQLQuery(sql.append(pagingSql).toString());

		List<Object[]> list = query.list();
		List<UcaCredit> listResult = new ArrayList<UcaCredit>(list.size());
		UcaCredit vo = null;
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				vo = new UcaCredit();
				vo.setCreditId(Integer.valueOf(obj[0].toString()));
				vo.setEpId(Integer.valueOf(obj[1].toString()));
				vo.setAnswerUserId(Integer.valueOf(obj[2].toString()));
				vo.setResults(Integer.valueOf(obj[3].toString()));
				vo.setCredits(Integer.valueOf(obj[4].toString()));
				vo.setEpName(obj[5] != null ? obj[5].toString() : "");
				vo.setUserName(obj[6] != null ? obj[6].toString() : "");
				listResult.add(vo);
			}
		}

		page.setListResult(listResult);

		pagingSql = "select count(1) from uca_credit as uc "
				+ "left join um_user as u on uc.answer_user_id = u.user_id "
				+ "left join uca_examination_paper as ep on uc.ep_id = ep.ep_id";
		
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
		
		query = ucaCreditDao.getSession().createSQLQuery(pagingSql);

		List countList = query.list();
		object.put("rows", page.getListResult());
		object.put("total", (BigInteger) countList.get(0));
	}

	@Override
	public UcaCredit getObjByEpIdAnswerUserId(Integer epId, Integer answerUserId) {
		List<UcaCredit> lists = new ArrayList<UcaCredit>();
		Criteria crit = ucaCreditDao.getSession().createCriteria(UcaCredit.class);
        crit.add(Restrictions.eq("epId", epId));
        crit.add(Restrictions.eq("answerUserId", answerUserId));
		lists = crit.list();
		if(null != lists && lists.size() > 0) {
			return lists.get(0);
		}
		return null;
	}

	@Override
	public void saveOrUpdate(UcaCredit po) {
		// TODO Auto-generated method stub
		ucaCreditDao.saveOrUpdate(po);
	}

	@Override
	public void find(JSONObject object, Page<UcaCreditStatisticsVo> page,
			String type, String sd, String ed) {
		// TODO Auto-generated method stub
		String sdate = null;
		String edate = null;
		String start = null;
		String end = null;
        
        if(ParameterUtil.isNotBlank(type)) {
        	if("1".equals(type)) {
        		if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
                    String monthS = new SimpleDateFormat("yyyy-MM").format(DateUtil.parseDate(sd));
                    sdate = monthS;
                    start = monthS+"-00 00:00:00";
                }
                if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
                	Calendar cal = Calendar.getInstance();
                	cal.setTime(DateUtil.parseDate(ed));
                	edate = new SimpleDateFormat("yyyy-MM").format(cal.getTime());
                	cal.add(Calendar.MONTH, 1);
                	String monthE = new SimpleDateFormat("yyyy-MM").format(cal.getTime());
                    end = monthE+"-00 00:00:00";
                }
        	}
        	if("2".equals(type)) {
        		if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
                    String yearS = new SimpleDateFormat("yyyy").format(DateUtil.parseDate(sd));
                    sdate = yearS;
                    start = yearS+"-00-00 00:00:00";
                }
                if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
                	Calendar cal = Calendar.getInstance();
                	cal.setTime(DateUtil.parseDate(ed));
                	edate = new SimpleDateFormat("yyyy").format(cal.getTime());
                	cal.add(Calendar.YEAR, 1);
                	String yearE = new SimpleDateFormat("yyyy").format(cal.getTime());
                    end = yearE+"-00-00 00:00:00";
                }
        	}
        }
        
		StringBuilder sql = new StringBuilder(
				"SELECT sum(uc.credits),u.user_name FROM uca_credit uc "
						+ "left join um_user u on uc.answer_user_id = u.user_id ");
		
		if(ParameterUtil.isNotBlank(start) && ParameterUtil.isNotBlank(end)){
			sql.append(" where uc.create_time >='").append(start)
				.append("' and uc.create_time <='").append(end).append("'");
        } else {
            if(ParameterUtil.isNotBlank(start)){
            	sql.append(" where uc.create_time >='").append(start).append("'");
            } else if(ParameterUtil.isNotBlank(end)){
            	sql.append(" where uc.create_time <='").append(end).append("'");
            }
        }
		sql.append(" group by uc.answer_user_id order by uc.answer_user_id asc");
		
		String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
		SQLQuery query = ucaCreditDao.getSession().createSQLQuery(sql.append(pagingSql).toString());

		List<Object[]> list = query.list();
		List<UcaCreditStatisticsVo> listResult = new ArrayList<UcaCreditStatisticsVo>(list.size());
		UcaCreditStatisticsVo vo = null;
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				vo = new UcaCreditStatisticsVo();
				vo.setSedate((ParameterUtil.isNotNull(sdate) ? sdate : "") + " -- " + (ParameterUtil.isNotNull(edate) ? edate : ""));
				vo.setCredits(Integer.valueOf(obj[0].toString()));
				vo.setUserName(obj[1] != null ? obj[1].toString() : "");
				listResult.add(vo);
			}
		}

		page.setListResult(listResult);

		pagingSql = "SELECT uc.answer_user_id FROM uca_credit uc "
				+ "left join um_user u on uc.answer_user_id = u.user_id";
		
		if(ParameterUtil.isNotBlank(start) && ParameterUtil.isNotBlank(end)){
			pagingSql += " where uc.create_time >='"+start+"' and uc.create_time <='"+end+"'";
        } else {
            if(ParameterUtil.isNotBlank(start)){
            	pagingSql += " where uc.create_time >='"+start+"'";
            } else if(ParameterUtil.isNotBlank(end)){
            	pagingSql += " where uc.create_time <='"+end+"'";
            }
        }
		pagingSql += " group by uc.answer_user_id";
		
		query = ucaCreditDao.getSession().createSQLQuery(pagingSql);

		List countList = query.list();
		object.put("rows", page.getListResult());
		object.put("total", countList.size());
	}

}
