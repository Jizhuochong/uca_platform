package com.uca.archives.service;

import java.text.ParseException;
import java.util.List;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.archives.pojos.UcaArchives;
import com.uca.archives.vo.UcaArchivesVo;

public interface ArchivesService {
	/**
	 * 保存和更新档案
	 * @param object
	 * @param po
	 */
	void saveOrUpdateArchives(JSONObject object, UcaArchives po);
	
	/**
	 * 档案分页查询
	 * @param object
	 * @param page 
	 * @param type 档案类型（1：工程档案，0：声像档案）
	 */
	void findArchives(JSONObject object, Page<UcaArchivesVo> page, Integer type);
	
	/**
	 * 根据ID获取档案
	 * @param object
	 * @param id
	 */
	void getArchivesById(JSONObject object, Integer id, Integer type);
	
	/**
	 * 档案复制进度查询
	 * @param orderNum 调卷单编号
	 * @param page
	 */
	void findArchivesSchedule(String orderNum, Page<UcaArchives> page)throws ParseException;
	
	/**
	 * 退档通知查询
	 * @param page
	 * @throws ParseException
	 */
	void findArchivesScheduleBack(Page<UcaArchives> page) throws ParseException;
	
	/**
	 * 根据OrderNum获取档案
	 * @param orderNum
	 */
	UcaArchives getArchivesById(int archivesId);
	
	/**
	 * 档案分页查询
	 * @param object
	 * @param page 
	 * @param type 档案类型（1：工程档案，0：声像档案）
	 * @param checkStatus
	 */
	void findArchivesAuditing(JSONObject object, Page<UcaArchivesVo> page, Integer type, Integer checkStatus);

	void pass(JSONObject object, int archivesId);

	void unpass(JSONObject object, int archivesId, String unPassTxt, String fileUrl, String sourceFileName);
	
	/**
	 * 档案分页查询
	 * @param object
	 * @param page 
	 * @param type 档案类型（1：工程档案，0：声像档案）
	 * @param checkStatus
	 */
	void findArchivesSchedule(JSONObject object, Page<UcaArchivesVo> page, Integer type, Integer checkStatus);
	
	void copyFinish(JSONObject object, int archivesId);

	void editOrderNum(JSONObject object, int archivesId, String orderNum);
	
	int getOrderNumCount(String orderNum);

	UcaArchives getArchivesByArchivesNum(String archivesNum);
	UcaArchives getArchivesByArchivesId(String id);

	void auditing(JSONObject object, int archivesId, String result);

	void findArchivesPass(JSONObject object, Page<UcaArchivesVo> page, Integer type, Integer checkStatus);

	void renameArchives(JSONObject object, int archivesId, String archivesNum) throws Exception;

	List<UcaArchives> findArchivesSoundBookingWorkload(String status,
			String sd, String ed, int archivesType);

	List<UcaArchives> findArchivesSoundAuditingWorkload(String status,
			String sd, String ed, int archivesType);

    void checkresultAuditing(String id, String pass, String desc, JSONObject object);
}
