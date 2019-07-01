package com.uca.notice.pojos;

import java.util.Date;

public class UcaNoticeUser implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer nUserId;
	private Integer noticeId;
	private Integer orgId;
	private Integer userId;
	private Integer isRead;
	private Date readTime;
	public UcaNoticeUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UcaNoticeUser(Integer nUserId, Integer noticeId, Integer orgId,
			Integer userId, Integer isRead, Date readTime) {
		super();
		this.nUserId = nUserId;
		this.noticeId = noticeId;
		this.orgId = orgId;
		this.userId = userId;
		this.isRead = isRead;
		this.readTime = readTime;
	}
	public Integer getnUserId() {
		return nUserId;
	}
	public void setnUserId(Integer nUserId) {
		this.nUserId = nUserId;
	}
	public Integer getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
}
