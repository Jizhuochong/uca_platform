package com.uca.exam.service;

import java.util.List;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.exam.pojos.UcaAnswer;

public interface UcaAnswerService {

	void find(JSONObject object, Page<UcaAnswer> page, String epName, String userName);

	void saveOrUpdateList(List<UcaAnswer> answerList);
	
	List<UcaAnswer> getAnswerListByEpIdUserId(int epId, int userId);

	void getById(JSONObject object, int answerId);

	void commitScore(JSONObject object, int answerId, String score);

}
