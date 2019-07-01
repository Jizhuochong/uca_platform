package com.uca.exam.service;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.exam.pojos.UcaExaminationPaper;

public interface UcaExamPaperService {

	void find(JSONObject object, Page<UcaExaminationPaper> page, String epName, String sd, String ed);

	void saveOrUpdate(JSONObject object, UcaExaminationPaper po);

	void getById(JSONObject object, int id);

	void delete(JSONObject object, int epId);

	void publish(JSONObject object, int epId);

	void findOnlineExam(JSONObject object, Page<UcaExaminationPaper> page);
	
	UcaExaminationPaper getById(int epId);

}
