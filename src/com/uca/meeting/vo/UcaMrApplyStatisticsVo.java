package com.uca.meeting.vo;

public class UcaMrApplyStatisticsVo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer mrId;
	
	private String name;
	
	private Integer applyCount;
	
	private String statisticsTime;

	public UcaMrApplyStatisticsVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UcaMrApplyStatisticsVo(Integer mrId, String name,
			Integer applyCount, String statisticsTime) {
		super();
		this.mrId = mrId;
		this.name = name;
		this.applyCount = applyCount;
		this.statisticsTime = statisticsTime;
	}

	public Integer getMrId() {
		return mrId;
	}

	public void setMrId(Integer mrId) {
		this.mrId = mrId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(Integer applyCount) {
		this.applyCount = applyCount;
	}

	public String getStatisticsTime() {
		return statisticsTime;
	}

	public void setStatisticsTime(String statisticsTime) {
		this.statisticsTime = statisticsTime;
	}
}
