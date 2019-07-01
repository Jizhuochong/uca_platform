package com.uca.archives.service;

import java.text.ParseException;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.archives.pojos.UcaArchivesBorrow;

public interface ArchivesBorrowService {

	void findArchivesSchedule(String orderNum, String person, Page<UcaArchivesBorrow> page,JSONObject object) throws ParseException;

	void saveOrUpdate(JSONObject object, UcaArchivesBorrow ab);

	void delete(JSONObject object, int catalogId);

	void getById(JSONObject object, int id);

}
