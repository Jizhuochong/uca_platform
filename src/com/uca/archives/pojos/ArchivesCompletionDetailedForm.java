package com.uca.archives.pojos;

import java.util.Date;

import javax.persistence.Transient;

public class ArchivesCompletionDetailedForm implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer registerId;// 登记表
    private Integer registerType;// 登记表类型:1建筑工程,2市政公用,3市政管线
    private String projectPurpose;// 工程项目用途:1建筑工程特有字段
    private String categoryStructure;// 结构种类:1建筑工程特有字段
    private String floors;// 层数地上/地下:1建筑工程特有字段
    private Double coversArea;// 占地面积（平方米）:1建筑工程特有字段
    private Double constructionArea;// 建筑面积（平方米）:1建筑工程特有字段
    private String buildings;// 栋楼:1建筑工程特有字段
    private Double totalInvestment;// 总投资(元):1建筑工程2市政公用特有字段
    private String projectContent;// 工程内容:2市政公用特有字段
    private Integer projectQuantity;// 数量:2市政公用特有字段
    private String diameterSection;// 管径（断面）:3市政管线特有字段
    private Double diameterLength;// 长度（米）:3市政管线特有字段
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

    public ArchivesCompletionDetailedForm() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Integer registerId) {
        this.registerId = registerId;
    }

    public Integer getRegisterType() {
        return registerType;
    }

    public void setRegisterType(Integer registerType) {
        this.registerType = registerType;
    }

    public String getProjectPurpose() {
        return projectPurpose;
    }

    public void setProjectPurpose(String projectPurpose) {
        this.projectPurpose = projectPurpose;
    }

    public String getCategoryStructure() {
        return categoryStructure;
    }

    public void setCategoryStructure(String categoryStructure) {
        this.categoryStructure = categoryStructure;
    }

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public Double getCoversArea() {
        return coversArea;
    }

    public void setCoversArea(Double coversArea) {
        this.coversArea = coversArea;
    }

    public Double getConstructionArea() {
        return constructionArea;
    }

    public void setConstructionArea(Double constructionArea) {
        this.constructionArea = constructionArea;
    }

    public String getBuildings() {
        return buildings;
    }

    public void setBuildings(String buildings) {
        this.buildings = buildings;
    }

    public Double getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(Double totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public Integer getProjectQuantity() {
        return projectQuantity;
    }

    public void setProjectQuantity(Integer projectQuantity) {
        this.projectQuantity = projectQuantity;
    }

    public String getDiameterSection() {
        return diameterSection;
    }

    public void setDiameterSection(String diameterSection) {
        this.diameterSection = diameterSection;
    }

    public Double getDiameterLength() {
        return diameterLength;
    }

    public void setDiameterLength(Double diameterLength) {
        this.diameterLength = diameterLength;
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
