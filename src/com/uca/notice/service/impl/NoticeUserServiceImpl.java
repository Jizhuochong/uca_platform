package com.uca.notice.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.pojos.BaseUser;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.constants.UcaConstants;
import com.uca.notice.dao.NoticeUserDao;
import com.uca.notice.pojos.UcaNoticeUser;
import com.uca.notice.service.NoticeUserService;
import com.uca.utils.BuildFileNameUtil;

@Service
public class NoticeUserServiceImpl implements NoticeUserService {
	@Autowired
	private NoticeUserDao noticeUserDao;
	
	private SecurityIdentifyUtils secUtils = new SecurityUtils();
	
	@Override
	public void saveNoticeUser(JSONObject object, int noticeId, List<UcaNoticeUser> liPo) {
		// TODO Auto-generated method stub
		this.deleteNoticeUserByNoticeId(noticeId);
		this.noticeUserDao.saveOrUpdateAll(liPo);
	}
	
	@Override
	public void updateNoticeUserForRead(Integer nId){
		UcaNoticeUser po = this.noticeUserDao.queryById(nId);
		if(po != null){
			if(po.getIsRead() == UcaConstants.NOTICE_RELEASE_STATUS_TWO){
				po.setIsRead(UcaConstants.NOTICE_RELEASE_STATUS_ONE);
				po.setReadTime(new Date());
				this.noticeUserDao.saveOrUpdate(po);
			}
		}
	}

	@Override
	public void getNoticeUserById(JSONObject object, Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteNoticeUserById(JSONObject object, int nUserId) {
		// TODO Auto-generated method stub
		UcaNoticeUser po = this.noticeUserDao.queryById(nUserId);
		if(po == null){
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
		}else{
			this.noticeUserDao.delete(po);
			object.put("success", true);
	        object.put("msg", "删除成功！");
		}
	}
	
	@Override
	public void deleteNoticeUserByNoticeId(int noticeId){
		List<Criterion> queryList = new ArrayList<Criterion>();
		queryList.add(Restrictions.eq("noticeId",noticeId));
		List<UcaNoticeUser> rtnList = this.noticeUserDao.findList(queryList);
		this.noticeUserDao.deleteAll(rtnList);
	}

	@Override
	public void findNotReadNoticeCountByUserId(JSONObject object) {
		// TODO Auto-generated method stub
		String sql = "select count(1) from uca_notice_user where user_id =:userId and is_read = 2";
		BaseUser user = secUtils.getUser();
		SQLQuery query = this.noticeUserDao.getSession().createSQLQuery(sql);
		query.setParameter("userId", user.getUserId());
		Object count = query.uniqueResult();
		object.put("success", true);
		object.put("noticeCount", Integer.parseInt(String.valueOf(count)));
	} 

	@Override
	public List<Integer> findReleaseNoticeForOrgList(Integer noticeId){
		List<Integer> liOrg = new ArrayList(0);
		String sql = "select org_id from uca_notice_user where 1=1";
		if(noticeId != null){
			sql += " and notice_id =:noticeId";
		}
		sql += " group by org_id";
		SQLQuery query = this.noticeUserDao.getSession().createSQLQuery(sql);
//		BaseUser user = secUtils.getUser();
//		query.setParameter("userId", user.getUserId());
		if(noticeId != null){
			query.setParameter("noticeId", noticeId);
		}
		liOrg = query.list();
		return liOrg;
	}
}
