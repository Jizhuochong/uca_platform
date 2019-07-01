package com.uca.arclass.pojos;

// Generated 2015-6-4 16:56:21 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.uca.archives.pojos.UcaArchives;

/**
 * UcaArchivesClass generated by hbm2java
 */
public class UcaArchivesClass implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int classId;
	private String name;
	private Integer parentClassId;
	private Date createTime;
	private Date updateTime;
	private Integer createUserId;
	private Integer updateUserId;
	private Set<UcaArchives> ucaArchiveses = new HashSet<UcaArchives>(0);

	public UcaArchivesClass() {
	}

	public UcaArchivesClass(int classId) {
		this.classId = classId;
	}

	public UcaArchivesClass(int classId, String name, Integer parentClassId,
			Date createTime, Date updateTime, Integer createUserId,
			Integer updateUserId, Set<UcaArchives> ucaArchiveses) {
		this.classId = classId;
		this.name = name;
		this.parentClassId = parentClassId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createUserId = createUserId;
		this.updateUserId = updateUserId;
		this.ucaArchiveses = ucaArchiveses;
	}

	public int getClassId() {
		return this.classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentClassId() {
		return this.parentClassId;
	}

	public void setParentClassId(Integer parentClassId) {
		this.parentClassId = parentClassId;
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

	public Set<UcaArchives> getUcaArchiveses() {
		return this.ucaArchiveses;
	}

	public void setUcaArchiveses(Set<UcaArchives> ucaArchiveses) {
		this.ucaArchiveses = ucaArchiveses;
	}

}
