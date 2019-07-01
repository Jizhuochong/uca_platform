package com.uca.duty.pojos;

import java.util.Date;

public class UcaDuty implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer dutyId;
	private Date dutyDate;
	private Date lunarCalendar;
	private String week;
	private String dayShiftUser;
	private String nightShiftUser;
	private String leader;
	private Integer createUserId;
	private Integer updateUserId;
	private Date createTime;
	private Date updateTime;
	
	public UcaDuty() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UcaDuty(Integer dutyId, Date dutyDate, Date lunarCalendar,
			String week, String dayShiftUser, String nightShiftUser,
			String leader, Integer createUserId, Integer updateUserId,
			Date createTime, Date updateTime) {
		super();
		this.dutyId = dutyId;
		this.dutyDate = dutyDate;
		this.lunarCalendar = lunarCalendar;
		this.week = week;
		this.dayShiftUser = dayShiftUser;
		this.nightShiftUser = nightShiftUser;
		this.leader = leader;
		this.createUserId = createUserId;
		this.updateUserId = updateUserId;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	public Integer getDutyId() {
		return dutyId;
	}
	public void setDutyId(Integer dutyId) {
		this.dutyId = dutyId;
	}
	public Date getDutyDate() {
		return dutyDate;
	}
	public void setDutyDate(Date dutyDate) {
		this.dutyDate = dutyDate;
	}
	public Date getLunarCalendar() {
		return lunarCalendar;
	}
	public void setLunarCalendar(Date lunarCalendar) {
		this.lunarCalendar = lunarCalendar;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getDayShiftUser() {
		return dayShiftUser;
	}
	public void setDayShiftUser(String dayShiftUser) {
		this.dayShiftUser = dayShiftUser;
	}
	public String getNightShiftUser() {
		return nightShiftUser;
	}
	public void setNightShiftUser(String nightShiftUser) {
		this.nightShiftUser = nightShiftUser;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
