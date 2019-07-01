package com.uca.meeting.service;

import java.util.List;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.meeting.pojos.UcaMeetingRoom;

public interface MeetingService {
	/**
	 * 保存和更新会议室
	 * @param object
	 * @param po
	 */
	void saveOrUpdateMeetingRoom(JSONObject object, UcaMeetingRoom po);
	
	/**
	 * 会议室分页查询
	 * @param object
	 * @param page
	 */
	void findMeetingRoom(JSONObject object, Page<UcaMeetingRoom> page, String dName, Integer dStatus);
	
	/**
	 * 查询可用的会议室
	 * @param object
	 */
	List<UcaMeetingRoom> findMeetingRoomForAvailable();
	
	/**
	 * 根据ID获取会议室
	 * @param object
	 * @param id
	 */
	void getMeetingRoomById(JSONObject object, Integer id);
	
	/**
	 * 根据ID删除会议室
	 * @param object
	 * @param noticeId
	 */
	void deleteMeetingRoomById(JSONObject object, int dId);
}
