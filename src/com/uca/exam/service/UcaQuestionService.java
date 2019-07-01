package com.uca.exam.service;

import java.util.List;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.exam.pojos.UcaQuestion;

public interface UcaQuestionService {

	void find(JSONObject object, Page<UcaQuestion> page, String name, String classifyId, String type, String sd, String ed);

	void saveOrUpdate(JSONObject object, UcaQuestion po);

	void getById(JSONObject object, int id);

	void delete(JSONObject object, int questionId);

	List<UcaQuestion> getQuestionByType(int type, String classifyId);

}
