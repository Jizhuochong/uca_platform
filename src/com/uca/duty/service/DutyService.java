package com.uca.duty.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.catalog.pojos.UcaArchivesCatalog;
import com.uca.duty.pojos.UcaDuty;

public interface DutyService {
	/**
	 * 值班表分页查询
	 * @param object
	 * @param page
	 * @param bgTime
	 * @param endTime
	 */
	void findDuty(JSONObject object, Page<UcaDuty> page, String bgTime, String endTime);
	
	/**
	 * 值班列表分页查询（只查询近两周的数据）
	 * @param projectName 工程名称
	 * @param page
	 * @throws ParseException 
	 */
	void findDutyForUser(JSONObject object, String qDate) throws ParseException;
	
	/**
	 * 保存和更新值班信息
	 * @param object
	 * @param po
	 */
	void saveOrUpdateDuty(JSONObject object, UcaDuty po);
	
	/**
	 * 根据ID获取值班信息
	 * @param object
	 * @param id
	 */
	void getByDutyId(JSONObject object, Integer id);
	
	/**
	 * 根据ID删除值班信息
	 * @param object
	 * @param catalogId
	 */
	void deleteDutyById(JSONObject object, int catalogId);
	
	/**
	 * 导入值班表
	 * @param object
	 * @param file
	 * @throws Exception 
	 */
	void importDuty(JSONObject object, MultipartFile file) throws Exception;
}
