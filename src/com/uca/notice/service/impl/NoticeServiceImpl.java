package com.uca.notice.service.impl;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.pojos.BaseUser;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.constants.UcaConstants;
import com.uca.notice.dao.NoticeDao;
import com.uca.notice.pojos.UcaNotice;
import com.uca.notice.pojos.UcaNoticeUser;
import com.uca.notice.service.NoticeService;
import com.uca.notice.service.NoticeUserService;
import com.uca.ucasecurity.pojos.UmUser;
import com.uca.ucasecurity.pojos.UmUserExpand;
import com.uca.ucasecurity.service.impl.UmUserServiceImpl;
import com.uca.utils.BuildFileNameUtil;

@Service
public class NoticeServiceImpl implements NoticeService{
	@Autowired
	private NoticeDao noticeDao;
	
	@Autowired
	private UmUserServiceImpl userService;
	
	@Autowired
	private NoticeUserService noticeUserService;
	
	private SecurityIdentifyUtils secUtils = new SecurityUtils();

	/**
	 * 保存和更新通知通告
	 * @param object
	 * @param po
	 */
	@Override
	public void saveOrUpdateNotice(JSONObject object, UcaNotice po) {
		// TODO Auto-generated method stub
		if(po.getNoticeId() == null){
			this.saveNotice(object, po);
		}else{
			this.updateNotice(object, po);
		}
		
		
		//保存通知发布用户信息
		this.saveNoticeUser(po.getNoticeId(), po.getReleaseOrg());
	}
	
	private void saveNoticeUser(Integer noticeId, String orgArrStr){
		if(StringUtils.isNotEmpty(orgArrStr)){
			//获取发布机构下的所有用户列表
			List<UmUser> userIdLi = userService.findUserListByOrgArr(orgArrStr.split(","));
			//构造通知发布用户保存集合对象
			List<UcaNoticeUser> saveList = new ArrayList<UcaNoticeUser>(0);
			for(UmUser user : userIdLi){
				UcaNoticeUser po = new UcaNoticeUser();
				po.setIsRead(UcaConstants.NOTICE_RELEASE_STATUS_TWO);
				po.setNoticeId(noticeId);
				po.setUserId(user.getUserId());
				po.setOrgId(user.getOrgId());
				saveList.add(po);
			}
			//调用保存通知发布用户方法
			noticeUserService.saveNoticeUser(null, noticeId, saveList);
		}
	}
	
	private void saveNotice(JSONObject object, UcaNotice po) {
		Date time = new Date();
		po.setCreateTime(time);
		BaseUser user = secUtils.getUser();
		po.setCreateUserId(user.getUserId());
		po.setUpdateTime(time);
		if(po.getReleaseStatus() == UcaConstants.NOTICE_RELEASE_STATUS_ONE){
			po.setReleaseUserId(user.getUserId());
			po.setReleaseTime(time);
		}
		
		this.noticeDao.save(po);
		object.put("msg", "通知通告保存成功！");
		object.put("success", true);
	}
	
	private void updateNotice(JSONObject object, UcaNotice po) {
		UcaNotice uPo = this.noticeDao.queryById(po.getNoticeId());
		if(uPo != null){
			//删除原有的文件
			if(uPo.getFileUrl() != null && !uPo.getFileUrl().equals(po.getFileUrl())){
				File file = new File(BuildFileNameUtil.UPLOAD_HOME + File.separator + BuildFileNameUtil.UPLOAD_URL + File.separator + uPo.getFileUrl());
				if(file.exists())
					file.delete();
				uPo.setFileUrl(po.getFileUrl());
				uPo.setSourceFileName(po.getSourceFileName());
				uPo.setFileFormat(po.getFileFormat());
				uPo.setFileSize(po.getFileSize());
			}
			uPo.setTitle(po.getTitle());
			uPo.setContent(po.getContent());
			
			BaseUser user = secUtils.getUser();
			Date updateTime = new Date();
			uPo.setUpdateUserId(user.getUserId());
			uPo.setUpdateTime(updateTime);
			uPo.setReleaseStatus(po.getReleaseStatus());
			if(po.getReleaseStatus() == UcaConstants.NOTICE_RELEASE_STATUS_ONE){
				uPo.setReleaseUserId(user.getUserId());
				uPo.setReleaseTime(updateTime);
			}
			uPo.setReleaseObj(po.getReleaseObj());
			uPo.setContactUser(po.getContactUser());
			uPo.setTel(po.getTel());
			uPo.setFax(po.getFax());
			uPo.setEmail(po.getEmail());
			this.noticeDao.update(uPo);
			object.put("success", true);
			object.put("msg", "通知通告保存成功！");
		}else{
			object.put("success", false);
			object.put("msg", "选择的记录不存在！");
		}
	}

	/**
	 * 通知通告分页查询
	 * @param object
	 * @param page 
	 * @param type 1：所有信息，2：文件信息
	 */
	@Override
	public void findNotice(JSONObject object, Page<UcaNotice> page, Integer type, String dTitle, String dContent) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder("select un.notice_id,un.title,un.source_file_name,un.file_url,un.file_format,u.user_name,un.release_status,un.release_time from uca_notice as un left join um_user as u on un.release_user_id = u.user_id where 1=1");
		if(type == 2){
			sql.append(" and un.file_url is not null and un.release_status = 1");
			//只有浏览文件信息的时候才判断用户的类型
			BaseUser user = secUtils.getUser();
			UmUserExpand exPo = (UmUserExpand)user.getProperty("userExpand");
			if(UcaConstants.UM_USER_EXPAND_TYPE_USER == exPo.getType()){//普通用户
				sql.append(" and un.release_obj in (1,3)");
			}else if(UcaConstants.UM_USER_EXPAND_TYPE_AREAUSER == exPo.getType()){//区县用户
				sql.append(" and un.release_obj in (1,2)");
			}
		}
		if(StringUtils.isNotEmpty(dTitle)){
			sql.append(" and un.title like :dTitle");
		}
		if(StringUtils.isNotEmpty(dContent)){
			sql.append(" and un.content like :dContent");
		}
		/*else if(UcaConstants.UM_USER_EXPAND_TYPE_CITYUSER == exPo.getType()){//市档案馆用户
			sql.append(" and un.type in (1)");
		}*/
		
		sql.append(" order by un.release_status asc,un.release_time desc");
		String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
		
		SQLQuery query = this.noticeDao.getSession().createSQLQuery(sql.append(pagingSql).toString());
//		query.setParameter("uploadUserId", user.getUserId());
//		query.setParameter("type", type);
		if(StringUtils.isNotEmpty(dTitle)){
			query.setParameter("dTitle", "%"+dTitle+"%");
		}
		if(StringUtils.isNotEmpty(dContent)){
			query.setParameter("dContent", "%"+dContent+"%");
		}
		List<Object[]> list = query.list();
		
		List<UcaNotice> listResult = new ArrayList<UcaNotice>(list.size());
		UcaNotice vo = null;
		if(list != null && list.size() > 0){
			for(Object[] obj : list){
				vo = new UcaNotice();
				vo.setNoticeId(Integer.valueOf(obj[0].toString()));
				vo.setTitle(obj[1] != null?obj[1].toString():"");
				vo.setSourceFileName(obj[2] != null?obj[2].toString():"");
				vo.setFileUrl(obj[3] != null?obj[3].toString():"");
				vo.setFileFormat(obj[4] != null?obj[4].toString():"");
				vo.setReleaseUserName(obj[5] != null?obj[5].toString():"");
				vo.setReleaseStatus(Integer.valueOf(obj[6].toString()));
				vo.setReleaseTime(obj[7] != null?(Date)obj[7]:null);
				listResult.add(vo);
			}
		}
		
		page.setListResult(listResult);
		
		pagingSql = "select count(1) " + sql.substring(sql.indexOf("from"),sql.lastIndexOf(" order"));
		query = this.noticeDao.getSession().createSQLQuery(pagingSql);
		if(StringUtils.isNotEmpty(dTitle)){
			query.setParameter("dTitle", "%"+dTitle+"%");
		}
		if(StringUtils.isNotEmpty(dContent)){
			query.setParameter("dContent", "%"+dContent+"%");
		}
		
		List countList = query.list();
		object.put("rows", page.getListResult());
		object.put("total", (BigInteger)countList.get(0));
	}
	
	public void findReleaseNoticeByUserId(JSONObject object, Page<UcaNotice> page, String dTitle, String dContent){
		StringBuilder sql = new StringBuilder("select un.notice_id,un.title,un.source_file_name,un.file_url,un.file_format,u.user_name,un.release_status,un.release_time,unu.is_read,unu.n_user_id from uca_notice as un left join um_user as u on un.release_user_id = u.user_id inner join uca_notice_user as unu on un.notice_id = unu.notice_id where 1=1 and un.release_status=1 and unu.user_id =:userId ");
		BaseUser user = secUtils.getUser();
//		UmUserExpand exPo = (UmUserExpand)user.getProperty("userExpand");
//		if(UcaConstants.UM_USER_EXPAND_TYPE_USER == exPo.getType()){//普通用户
//			sql.append(" and un.release_obj in (1,3)");
//		}else if(UcaConstants.UM_USER_EXPAND_TYPE_AREAUSER == exPo.getType()){//区县用户
//			sql.append(" and un.release_obj in (1,2)");
//		}
		if(StringUtils.isNotEmpty(dTitle)){
			sql.append(" and un.title like :dTitle");
		}
		if(StringUtils.isNotEmpty(dContent)){
			sql.append(" and un.content like :dContent");
		}
		sql.append(" order by release_time desc,is_read asc");
		String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
		SQLQuery query = this.noticeDao.getSession().createSQLQuery(sql.toString());
		query.setParameter("userId", user.getUserId());
		if(StringUtils.isNotEmpty(dTitle)){
			query.setParameter("dTitle", "%"+dTitle+"%");
		}
		if(StringUtils.isNotEmpty(dContent)){
			query.setParameter("dContent", "%"+dContent+"%");
		}
		List<Object[]> list = query.list();
		List<UcaNotice> listResult = new ArrayList<UcaNotice>(list.size());
		UcaNotice vo = null;
		if(list != null && list.size() > 0){
			for(Object[] obj : list){
				vo = new UcaNotice();
				vo.setNoticeId(Integer.valueOf(obj[0].toString()));
				vo.setTitle(obj[1] != null?obj[1].toString():"");
				vo.setSourceFileName(obj[2] != null?obj[2].toString():"");
				vo.setFileUrl(obj[3] != null?obj[3].toString():"");
				vo.setFileFormat(obj[4] != null?obj[4].toString():"");
				vo.setReleaseUserName(obj[5] != null?obj[5].toString():"");
				vo.setReleaseStatus(Integer.valueOf(obj[6].toString()));
				vo.setReleaseTime(obj[7] != null?(Date)obj[7]:null);
				vo.setIsRead(obj[8] != null?Integer.valueOf(obj[8].toString()):null);
				vo.setNoticeUserId(obj[9] != null?Integer.valueOf(obj[9].toString()):null);
				listResult.add(vo);
			}
		}
		
		page.setListResult(listResult);
		
		pagingSql = "select count(1) " + sql.substring(sql.indexOf("from"),sql.lastIndexOf(" order"));
		query = this.noticeDao.getSession().createSQLQuery(pagingSql);
		query.setParameter("userId", user.getUserId());
		if(StringUtils.isNotEmpty(dTitle)){
			query.setParameter("dTitle", "%"+dTitle+"%");
		}
		if(StringUtils.isNotEmpty(dContent)){
			query.setParameter("dContent", "%"+dContent+"%");
		}
		List countList = query.list();
		object.put("rows", page.getListResult());
		object.put("total", (BigInteger)countList.get(0));
	}

	/**
	 * 根据ID获取通知通告
	 * @param object
	 * @param id
	 */
	@Override
	public void getNoticeById(JSONObject object, Integer id) {
		// TODO Auto-generated method stub
		UcaNotice po = new UcaNotice();
		if(id != null){
			po = this.noticeDao.queryById(id);
		}else{
			po.setReleaseStatus(UcaConstants.NOTICE_RELEASE_STATUS_TWO);
			po.setReleaseObj(UcaConstants.NOTICE_RELEASE_STATUS_ONE);
		}
		
		object.put("success", true);
		object.put("objPo", po);
	}
	
	/**
	 * 根据ID获取通知通告并设置已阅读状态
	 * @param object
	 * @param id
	 */
	@Override
	public void getNoticeByRead(JSONObject object, Integer id, Integer nId){
		this.getNoticeById(object, id);
	}

	/**
	 * 根据ID删除通知通告
	 * @param object
	 * @param noticeId
	 */
	@Override
	public void deleteNoticeById(JSONObject object, int noticeId) {
		// TODO Auto-generated method stub
		UcaNotice po = this.noticeDao.queryById(noticeId);
		if(po == null){
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
		}else{
			this.noticeDao.delete(po);
			//删除文件
			if(po.getFileUrl() != null){
				File file = new File(BuildFileNameUtil.UPLOAD_HOME + File.separator + BuildFileNameUtil.UPLOAD_URL + File.separator + po.getFileUrl());
				if(file.exists())
					file.delete();
			}
			this.noticeUserService.deleteNoticeUserByNoticeId(noticeId);
			object.put("success", true);
	        object.put("msg", "删除成功！");
		}
	}
}
