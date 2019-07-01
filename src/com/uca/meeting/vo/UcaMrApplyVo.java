package com.uca.meeting.vo;

public class UcaMrApplyVo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer applyId;
	
	private Integer applyUserId;
	
	private Integer mrId;
	
	private Integer winTime;
	
	private String winTimeShow;
	
	private Integer applyStatus;
	
	private Integer beloneStatus;

	public UcaMrApplyVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UcaMrApplyVo(Integer applyId, Integer applyUserId, Integer mrId,
			Integer winTime, String winTimeShow, Integer applyStatus, Integer beloneStatus) {
		super();
		this.applyId = applyId;
		this.applyUserId = applyUserId;
		this.mrId = mrId;
		this.winTime = winTime;
		this.winTimeShow = winTimeShow;
		this.applyStatus = applyStatus;
		this.beloneStatus = beloneStatus;
	}

	public Integer getApplyId() {
		return applyId;
	}

	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}

	public Integer getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(Integer applyUserId) {
		this.applyUserId = applyUserId;
	}

	public Integer getMrId() {
		return mrId;
	}

	public void setMrId(Integer mrId) {
		this.mrId = mrId;
	}

	public Integer getWinTime() {
		return winTime;
	}

	public void setWinTime(Integer winTime) {
		this.winTime = winTime;
	}

	public String getWinTimeShow() {
		return winTimeShow;
	}

	public void setWinTimeShow(String winTimeShow) {
		this.winTimeShow = winTimeShow;
	}

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public Integer getBeloneStatus() {
		return beloneStatus;
	}

	public void setBeloneStatus(Integer beloneStatus) {
		this.beloneStatus = beloneStatus;
	}
}
