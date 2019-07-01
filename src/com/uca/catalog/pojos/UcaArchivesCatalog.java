package com.uca.catalog.pojos;

// Generated 2015-6-4 16:56:21 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Transient;

/**
 * UcaArchivesCatalog generated by hbm2java
 */
public class UcaArchivesCatalog implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer catalogId;
	private String archivesNum;
	private String projectName;
	private String devOrg;
	private String projectAddress;
	private String planPerNum;
	private Date createTime;
	private Date updateTime;
	private Integer createUserId;
	private Integer updateUserId;
	private String designUnit;
	private String constructionUnit;
	
	@Transient
	private Integer archivesId;
	@Transient
	private String orderNum;

	public UcaArchivesCatalog() {
	}

	public UcaArchivesCatalog(String archivesNum, String projectName,
			String devOrg, String projectAddress, String planPerNum,
			Date createTime, Date updateTime, Integer createUserId,
			Integer updateUserId) {
		this.archivesNum = archivesNum;
		this.projectName = projectName;
		this.devOrg = devOrg;
		this.projectAddress = projectAddress;
		this.planPerNum = planPerNum;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createUserId = createUserId;
		this.updateUserId = updateUserId;
	}

	public Integer getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}

	public String getArchivesNum() {
		return archivesNum;
	}

	public void setArchivesNum(String archivesNum) {
		this.archivesNum = archivesNum;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDevOrg() {
		return this.devOrg;
	}

	public void setDevOrg(String devOrg) {
		this.devOrg = devOrg;
	}

	public String getProjectAddress() {
		return this.projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}

	public String getPlanPerNum() {
		return this.planPerNum;
	}

	public void setPlanPerNum(String planPerNum) {
		this.planPerNum = planPerNum;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Integer getArchivesId() {
		return archivesId;
	}

	public void setArchivesId(Integer archivesId) {
		this.archivesId = archivesId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getDesignUnit() {
		return designUnit;
	}

	public void setDesignUnit(String designUnit) {
		this.designUnit = designUnit;
	}

	public String getConstructionUnit() {
		return constructionUnit;
	}

	public void setConstructionUnit(String constructionUnit) {
		this.constructionUnit = constructionUnit;
	}

}