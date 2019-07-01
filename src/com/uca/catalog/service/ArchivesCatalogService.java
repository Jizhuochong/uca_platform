package com.uca.catalog.service;

import java.text.ParseException;
import java.util.List;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.catalog.pojos.UcaArchivesCatalog;

public interface ArchivesCatalogService {
	/**
	 * 档案目录分页查询
	 * @param object
	 * @param page 
	 */
	void find(JSONObject object, Page<UcaArchivesCatalog> page, 
			String archivesId, String projectName, String devOrg, String projectAddress, 
			String planPerNum, String sd, String ed);
	
	/**
	 * 档案目录分页查询无需登录
	 * @param projectName 工程名称
	 * @param page
	 * @throws ParseException 
	 */
	void findCatalogForUser(String projectName, Page<UcaArchivesCatalog> page) throws ParseException;
	
	/**
	 * 保存和更新档案目录
	 * @param object
	 * @param po
	 */
	void saveOrUpdate(JSONObject object, UcaArchivesCatalog po);
	
	/**
	 * 根据ID获取档案目录
	 * @param object
	 * @param id
	 */
	void getById(JSONObject object, Integer id);
	
	void delete(JSONObject object, int catalogId);
	
	void saveCatalogs (List<UcaArchivesCatalog> list);
}
