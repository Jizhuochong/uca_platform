package com.uca.ucasecurity.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 机构表
 * 
 * @author mamm 2014年12月1日
 * 
 */
public class UmOrg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 机构ID
	 */
	private Integer orgId;
	/**
	 * 机构父ID
	 */
	private Integer parentOrgId;
	/**
	 * 机构编码
	 */
	private String orgCode;
	/**
	 * 父机构编码
	 */
	private String parentOrgCode;
	/**
	 * 机构名称
	 */
	private String orgName;

	/**
	 * 机构描述
	 */
	private String description;

	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;
	
	/**
	 * 创建人
	 */
	private Integer createUserId;
	
	/**
	 * 修改人
	 */
	private Integer updateUserId;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 机构类型
	 * 1：市档案馆机构
	 * 2：区县机构
	 */
	private Integer type;
	
	/**
	 * 子列表
	 */
	private List<UmOrg> childrenList = new ArrayList<UmOrg>(0);

	public UmOrg() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UmOrg(Integer orgId, Integer parentOrgId, String orgCode,
			String parentOrgCode, String orgName, String description,
			Date createTime, Date updateTime, Integer createUserId,
			Integer updateUserId, Integer sort,
			List<UmOrg> childrenList, Integer type) {
		super();
		this.orgId = orgId;
		this.parentOrgId = parentOrgId;
		this.orgCode = orgCode;
		this.parentOrgCode = parentOrgCode;
		this.orgName = orgName;
		this.description = description;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createUserId = createUserId;
		this.updateUserId = updateUserId;
		this.sort = sort;
		this.childrenList = childrenList;
		this.type = type;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Integer parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getParentOrgCode() {
		return parentOrgCode;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<UmOrg> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<UmOrg> childrenList) {
		this.childrenList = childrenList;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
