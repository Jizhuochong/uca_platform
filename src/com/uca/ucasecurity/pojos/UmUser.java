package com.uca.ucasecurity.pojos;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cn.com.capinfo.auth.pojos.Role;
import cn.com.capinfo.core.pojos.BaseRole;
import cn.com.capinfo.core.pojos.BaseUser;
import cn.com.capinfo.core.utils.DateUtil;

import com.uca.ucasecurity.util.UserStatusEnums;

public class UmUser implements Serializable, BaseUser {

    private static final long serialVersionUID = -6514738924858633212L;

    private int userId;
    private String userAccount;
    private String userPassword;
    private String userName;
    private Date userRegistTime;
    private int userStatus;
    private int orgId;
    
    private String createtimeStr;
    private String statusStr;
    private String[] roleIds;
    private String roles;
    private Set<? extends BaseRole> umRoles = new HashSet<Role>(0);
    private String orgName;
    private String roleName;
    
    /**
	 * 用户类型
	 */
	private Integer type;
	
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
    
    public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreatetimeStr() {
        createtimeStr = DateUtil.timeFormat(userRegistTime);
        return createtimeStr;
    }

    public void setCreatetimeStr(String createtimeStr) {
        this.createtimeStr = createtimeStr;
    }

    public String getStatusStr() {
        statusStr = UserStatusEnums.getKeyDescriptionByValue(userStatus);
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
    
    public String[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public UmUser() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getUserRegistTime() {
        return userRegistTime;
    }

    public void setUserRegistTime(Date userRegistTime) {
        this.userRegistTime = userRegistTime;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public Set<? extends BaseRole> getUmRoles() {
		return this.umRoles;
	}

	public void setUmRoles(Set<? extends BaseRole> umRoles) {
		this.umRoles = umRoles;
	}

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	@Override
	public void setProperty(String key, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}