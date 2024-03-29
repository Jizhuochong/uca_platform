package com.uca.exam.vo;

// Generated 2015-6-25 13:42:46 by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.List;

/**
 * UcaExaminationQuestion generated by hbm2java
 */
public class UcaExamQuestionVo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer eqId;
	private Integer eqNumber;
	private Integer type;
	private String name;
	private List<String> optionList = new ArrayList<String>(0);

	public UcaExamQuestionVo() {
	}

	public Integer getEqId() {
		return this.eqId;
	}

	public void setEqId(Integer eqId) {
		this.eqId = eqId;
	}

	public Integer getEqNumber() {
		return eqNumber;
	}

	public void setEqNumber(Integer eqNumber) {
		this.eqNumber = eqNumber;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<String> optionList) {
		this.optionList = optionList;
	}

}
