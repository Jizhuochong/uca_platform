package com.uca.survey.pojos;

// Generated 2015-6-25 13:42:46 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * UcaSurveyOptionResult generated by hbm2java
 */
public class UcaSurveyOptionResult implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private UcaSurvey ucaSurvey;
	private UcaSurveyOption ucaSurveyOption;
	private Integer resultUserId;
	private Date resultTime;
	
	private Integer surveyId;
	private Integer optionId;
	private String userName;

	public UcaSurveyOptionResult() {
	}

	public UcaSurveyOptionResult(UcaSurvey ucaSurvey, UcaSurveyOption ucaSurveyOption, 
			Integer resultUserId, Date resultTime, Integer updateUserId, Date updateTime) {
		this.ucaSurvey = ucaSurvey;
		this.ucaSurveyOption = ucaSurveyOption;
		this.resultUserId = resultUserId;
		this.resultTime = resultTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UcaSurvey getUcaSurvey() {
		return ucaSurvey;
	}

	public void setUcaSurvey(UcaSurvey ucaSurvey) {
		this.ucaSurvey = ucaSurvey;
	}

	public UcaSurveyOption getUcaSurveyOption() {
		return ucaSurveyOption;
	}

	public void setUcaSurveyOption(UcaSurveyOption ucaSurveyOption) {
		this.ucaSurveyOption = ucaSurveyOption;
	}

	public Integer getResultUserId() {
		return resultUserId;
	}

	public void setResultUserId(Integer resultUserId) {
		this.resultUserId = resultUserId;
	}

	public Date getResultTime() {
		return resultTime;
	}

	public void setResultTime(Date resultTime) {
		this.resultTime = resultTime;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
