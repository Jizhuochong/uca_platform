package com.uca.notice.service;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.notice.pojos.UcaNotice;

public interface NoticeService {
	/**
	 * 保存和更新通知通告
	 * @param object
	 * @param po
	 */
	void saveOrUpdateNotice(JSONObject object, UcaNotice po);
	
	/**
	 * 通知通告分页查询
	 * @param object
	 * @param page 
	 * @param type 1：所有信息，2：文件信息
	 */
	void findNotice(JSONObject object, Page<UcaNotice> page, Integer type, String dTitle, String dContent);
	
	/**
	 * 根据ID获取通知通告
	 * @param object
	 * @param id
	 */
	void getNoticeById(JSONObject object, Integer id);
	
	/**
	 * 根据ID删除通知通告
	 * @param object
	 * @param noticeId
	 */
	void deleteNoticeById(JSONObject object, int noticeId);
	
	/**
	 * 根据用户id查询最新的通知通告
	 * @param object
	 */
	void findReleaseNoticeByUserId(JSONObject object, Page<UcaNotice> page, String dTitle, String dContent);

	/**
	 * 根据ID获取通知通告并设置已阅读状态
	 * @param object
	 * @param id
	 */
	void getNoticeByRead(JSONObject object, Integer id, Integer nId);
}
