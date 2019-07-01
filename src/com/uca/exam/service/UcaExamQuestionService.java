package com.uca.exam.service;

import java.util.List;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.exam.pojos.UcaExaminationQuestion;

public interface UcaExamQuestionService {

	void find(JSONObject object, Page<UcaExaminationQuestion> page, int epId, String name, String classifyId, String type, String sd, String ed);

	void saveOrUpdate(JSONObject object, UcaExaminationQuestion po);

	void getById(JSONObject object, int id);

	void delete(JSONObject object, int eqId);

	List<UcaExaminationQuestion> getExamQuestionListByEpId(Integer epId);

}
