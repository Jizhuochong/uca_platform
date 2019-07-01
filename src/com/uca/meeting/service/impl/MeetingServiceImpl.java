package com.uca.meeting.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.pojos.BaseUser;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.constants.UcaConstants;
import com.uca.meeting.dao.MeetingDao;
import com.uca.meeting.pojos.UcaMeetingRoom;
import com.uca.meeting.service.MeetingService;

@Service
public class MeetingServiceImpl implements MeetingService {
	@Autowired
	private MeetingDao meetingDao;
	
	private SecurityIdentifyUtils secUtils = new SecurityUtils();
	
	/**
	 * 保存和更新会议室
	 * @param object
	 * @param po
	 */
	@Override
	public void saveOrUpdateMeetingRoom(JSONObject object, UcaMeetingRoom po) {
		// TODO Auto-generated method stub
		if(po.getMrId() == null){
			this.saveMeetingRoom(object, po);
		}else{
			this.updateMeetingRoom(object, po);
		}
	}
	
	private void saveMeetingRoom(JSONObject object, UcaMeetingRoom po) {
		Date time = new Date();
		po.setCreateTime(time);
		BaseUser user = secUtils.getUser();
		po.setCreateUserId(user.getUserId());
		po.setUpdateTime(time);
		this.meetingDao.save(po);
		object.put("msg", "会议室保存成功！");
		object.put("success", true);
	}
	
	private void updateMeetingRoom(JSONObject object, UcaMeetingRoom po) {
		UcaMeetingRoom uPo = this.meetingDao.queryById(po.getMrId());
		if(uPo != null){
			uPo.setName(po.getName());
			uPo.setAddress(po.getAddress());
			uPo.setPeopleCount(po.getPeopleCount());
			uPo.setStatus(po.getStatus());
			
			BaseUser user = secUtils.getUser();
			Date updateTime = new Date();
			uPo.setUpdateUserId(user.getUserId());
			uPo.setUpdateTime(updateTime);
			
			this.meetingDao.update(uPo);
			object.put("success", true);
			object.put("msg", "会议室保存成功！");
		}else{
			object.put("success", false);
			object.put("msg", "选择的记录不存在！");
		}
	}

	/**
	 * 会议室分页查询
	 * @param object
	 * @param page
	 */
	@Override
	public void findMeetingRoom(JSONObject object, Page<UcaMeetingRoom> page,
			String dName, Integer dStatus) {
		List<Criterion> cnList = new ArrayList<Criterion>(0);
		if(StringUtils.isNotEmpty(dName))
			cnList.add(Restrictions.like("name", "%"+dName+"%"));
		if(dStatus != null)
			cnList.add(Restrictions.eq("status", dStatus));
		
		ProjectionList proList = Projections.projectionList();//设置投影集合  
		proList.add(Projections.property("t.mrId").as("mrId"));
	    proList.add(Projections.property("t.name").as("name"));
	    proList.add(Projections.property("t.address").as("address"));
	    proList.add(Projections.property("t.peopleCount").as("peopleCount"));
	    proList.add(Projections.property("t.status").as("status"));
		
		page.addOrderBy("status");
		page.addOrderDir("asc");
		
		this.meetingDao.findList(cnList, proList, page);
		
		object.put("rows", page.getListResult());
		object.put("total", page.getTotalRowCount());
	}

	/**
	 * 查询可用的会议室
	 * @param object
	 */
	@Override
	public List<UcaMeetingRoom> findMeetingRoomForAvailable() {
		// TODO Auto-generated method stub
		List<Criterion> cnList = new ArrayList<Criterion>(0);
		cnList.add(Restrictions.eq("status", UcaConstants.MEETING_ROOM_STATUS_ONE));
		return this.meetingDao.findList(cnList);
	}

	/**
	 * 根据ID获取会议室
	 * @param object
	 * @param id
	 */
	@Override
	public void getMeetingRoomById(JSONObject object, Integer id) {
		// TODO Auto-generated method stub
		UcaMeetingRoom po = new UcaMeetingRoom();
		if(id != null){
			po = this.meetingDao.queryById(id);
		}else{
			po.setStatus(UcaConstants.MEETING_ROOM_STATUS_ONE);
		}
		
		object.put("success", true);
		object.put("objPo", po);
	}

	/**
	 * 根据ID删除会议室
	 * @param object
	 * @param noticeId
	 */
	@Override
	public void deleteMeetingRoomById(JSONObject object, int dId) {
		// TODO Auto-generated method stub
		UcaMeetingRoom po = this.meetingDao.queryById(dId);
		if(po == null){
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
		}else{
			this.meetingDao.delete(po);
			object.put("success", true);
	        object.put("msg", "删除成功！");
		}
	}

}
