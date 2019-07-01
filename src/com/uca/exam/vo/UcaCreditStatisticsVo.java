package com.uca.exam.vo;

public class UcaCreditStatisticsVo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String sedate;
	private String userName;
	private Integer credits;

	public UcaCreditStatisticsVo() {
	}

	public String getSedate() {
		return sedate;
	}

	public void setSedate(String sedate) {
		this.sedate = sedate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

}
