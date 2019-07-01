package com.uca.notice.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.uca.notice.pojos.UcaNoticeUser;

public interface NoticeUserService {
	/**
	 * 保存通知发布用户
	 * @param object
	 * @param po
	 */
	void saveNoticeUser(JSONObject object, int noticeId, List<UcaNoticeUser> liPo);
	
	/**
	 * 根据ID获取通知发布用户
	 * @param object
	 * @param id
	 */
	void getNoticeUserById(JSONObject object, Integer id);
	
	/**
	 * 根据ID删除通知发布用户
	 * @param object
	 * @param noticeId
	 */
	void deleteNoticeUserById(JSONObject object, int nUserId);
	
	/**
	 * 根据消息ID删除通知发布用户
	 * @param object
	 * @param noticeId
	 */
	void deleteNoticeUserByNoticeId(int noticeId);
	
	/**
	 * 根据用户id查询最新的通知
	 * @param object
	 */
	void findNotReadNoticeCountByUserId(JSONObject object);
	
	List<Integer> findReleaseNoticeForOrgList(Integer noticeId);

	void updateNoticeUserForRead(Integer nId);
}
