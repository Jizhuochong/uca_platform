package com.uca.archives.vo;

public class UcaArchivesVo implements java.io.Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private Integer archivesId;

    private String archivesNum;

    private String projectName;

    private String orderNum;

    private String uploadTime;

    private Integer checkStatus;

    private Integer copyStatus;

    private String userName;

    private String instruction;

    private String checkTime;

    private String updateTime;

    private Integer queryStatus;

    private String applyTime;

    private String fileUrl;

    private String sourceFileName;

    private Integer orgId;

    private Integer type;
    private Integer renameStatus;

    private Integer handingPersonId;
    private String fileUrlOther;
    private String sourceFileNameOther;
    private String handingPersonName;

    public String getFileUrlOther() {
        return fileUrlOther;
    }

    public void setFileUrlOther(String fileUrlOther) {
        this.fileUrlOther = fileUrlOther;
    }

    public String getSourceFileNameOther() {
        return sourceFileNameOther;
    }

    public void setSourceFileNameOther(String sourceFileNameOther) {
        this.sourceFileNameOther = sourceFileNameOther;
    }

    public UcaArchivesVo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public UcaArchivesVo(Integer archivesId, String archivesNum, String projectName, String orderNum,
            String uploadTime, Integer checkStatus, Integer copyStatus, String userName, String instruction,
            String checkTime, String updateTime, Integer queryStatus, String applyTime, String fileUrl,
            String sourceFileName, Integer orgId, Integer type, Integer handingPersonId) {
        super();
        this.archivesId = archivesId;
        this.archivesNum = archivesNum;
        this.projectName = projectName;
        this.orderNum = orderNum;
        this.uploadTime = uploadTime;
        this.checkStatus = checkStatus;
        this.copyStatus = copyStatus;
        this.userName = userName;
        this.instruction = instruction;
        this.checkTime = checkTime;
        this.updateTime = updateTime;
        this.queryStatus = queryStatus;
        this.applyTime = applyTime;
        this.fileUrl = fileUrl;
        this.sourceFileName = sourceFileName;
        this.orgId = orgId;
        this.type = type;
        this.handingPersonId = handingPersonId;
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

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Integer getCopyStatus() {
        return copyStatus;
    }

    public void setCopyStatus(Integer copyStatus) {
        this.copyStatus = copyStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(Integer queryStatus) {
        this.queryStatus = queryStatus;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRenameStatus() {
        return renameStatus;
    }

    public void setRenameStatus(Integer renameStatus) {
        this.renameStatus = renameStatus;
    }

    public Integer getHandingPersonId() {
        return handingPersonId;
    }

    public void setHandingPersonId(Integer handingPersonId) {
        this.handingPersonId = handingPersonId;
    }

    public String getArchivesNum() {
        return archivesNum;
    }

    public void setArchivesNum(String archivesNum) {
        this.archivesNum = archivesNum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getHandingPersonName() {
        return handingPersonName;
    }

    public void setHandingPersonName(String handingPersonName) {
        this.handingPersonName = handingPersonName;
    }

}
