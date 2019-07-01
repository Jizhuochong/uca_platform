package com.uca.meeting.pojos;

public class UcaMrApplyUser implements java.io.Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private Integer applyUserId;

    private UcaMrApply ucaMrApply;

    private Integer userId;

    private String userName;

    private Integer flagRead;

    public UcaMrApplyUser() {
        super();
        // TODO Auto-generated constructor stub
    }

    public UcaMrApplyUser(Integer applyUserId, UcaMrApply ucaMrApply, Integer userId, Integer flagRead) {
        super();
        this.applyUserId = applyUserId;
        this.ucaMrApply = ucaMrApply;
        this.userId = userId;
        this.flagRead = flagRead;
    }

    public Integer getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
    }

    public UcaMrApply getUcaMrApply() {
        return ucaMrApply;
    }

    public void setUcaMrApply(UcaMrApply ucaMrApply) {
        this.ucaMrApply = ucaMrApply;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getFlagRead() {
        return flagRead;
    }

    public void setFlagRead(Integer flagRead) {
        this.flagRead = flagRead;
    }
}
