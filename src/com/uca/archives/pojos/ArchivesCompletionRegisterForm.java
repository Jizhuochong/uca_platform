package com.uca.archives.pojos;

import java.util.Date;

import javax.persistence.Transient;

public class ArchivesCompletionRegisterForm implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer type;// 登记表类型:1建筑工程,2市政公用,3市政管线
    private String archiveWord;// 档字
    private String buildWord;// 建字:1建筑工程特有字段
    private String buildUnit;// 建设单位
    private String mailingAddress;// 通讯地址
    private String zipCode;// 邮政编码
    private String registerPerson;// 登记人
    private String registerDate;// 登记日期
    private String phone;// 电话
    private String projectName;// 工程名称
    private String projectLocation;// 工程地点
    private String designUnit;// 设计单位
    private String constructionUnit;// 施工单位
    private String measureUnit;// 测量单位:2市政公用3市政管线特有字段
    private String projectType;// 工程类型:2市政公用3市政管线特有字段
    private String startDate;// 开工
    private String completionDate;// 竣工
    private Double totalInvestment;// 总投资(元):3市政管线特有字段
    private String remark;// 备注
    private String archivesManagers;// 档案馆经办人
    private Integer createUserId;// 创建人
    private Date createTime;// 创建时间

    @Transient
    private String createUserName;// 创建人

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public ArchivesCompletionRegisterForm() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getArchiveWord() {
        return archiveWord;
    }

    public void setArchiveWord(String archiveWord) {
        this.archiveWord = archiveWord;
    }

    public String getBuildWord() {
        return buildWord;
    }

    public void setBuildWord(String buildWord) {
        this.buildWord = buildWord;
    }

    public String getBuildUnit() {
        return buildUnit;
    }

    public void setBuildUnit(String buildUnit) {
        this.buildUnit = buildUnit;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getRegisterPerson() {
        return registerPerson;
    }

    public void setRegisterPerson(String registerPerson) {
        this.registerPerson = registerPerson;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
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

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public Double getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(Double totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getArchivesManagers() {
        return archivesManagers;
    }

    public void setArchivesManagers(String archivesManagers) {
        this.archivesManagers = archivesManagers;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
