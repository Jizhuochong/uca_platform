package com.uca.exam.service;

import java.util.List;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.exam.pojos.UcaExamQuestionClassify;

public interface UcaExamQuestionClassifyService {

	void find(JSONObject object, Page<UcaExamQuestionClassify> page, String name);

	void saveOrUpdate(JSONObject object, UcaExamQuestionClassify po);

	void getById(JSONObject object, int id);

	void delete(JSONObject object, int id);
	
	List<UcaExamQuestionClassify> findDatas();
}
