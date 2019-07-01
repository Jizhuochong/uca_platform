package com.uca.ucasecurity.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户扩展属性表
 * @author mamm
 * 2014年12月1日
 *
 */
public class UmUserExpand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 用户类型
	 */
	private Integer type;
	/**
	 * 机构ID
	 */
	private Integer orgId;
	/**
	 * 手机
	 */
	private String phoneNum;
	/**
	 * 联系电话
	 */
	private String telephone;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 建设单位
	 */
	private String devOrg;
	/**
	 * 单位地址
	 */
	private String devOrgAddress;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 申请激活时间
	 */
	private Date applyActivateTime;
	
	public UmUserExpand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UmUserExpand(Integer userId, Integer type, Integer orgId,
			String phoneNum, String telephone, Integer sex, String email,
			String devOrg, String devOrgAddress, String remark,
			Date applyActivateTime) {
		super();
		this.userId = userId;
		this.type = type;
		this.orgId = orgId;
		this.phoneNum = phoneNum;
		this.telephone = telephone;
		this.sex = sex;
		this.email = email;
		this.devOrg = devOrg;
		this.devOrgAddress = devOrgAddress;
		this.remark = remark;
		this.applyActivateTime = applyActivateTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDevOrg() {
		return devOrg;
	}

	public void setDevOrg(String devOrg) {
		this.devOrg = devOrg;
	}

	public String getDevOrgAddress() {
		return devOrgAddress;
	}

	public void setDevOrgAddress(String devOrgAddress) {
		this.devOrgAddress = devOrgAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getApplyActivateTime() {
		return applyActivateTime;
	}

	public void setApplyActivateTime(Date applyActivateTime) {
		this.applyActivateTime = applyActivateTime;
	}
}
