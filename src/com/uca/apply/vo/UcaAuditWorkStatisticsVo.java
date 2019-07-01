package com.uca.apply.vo;

public class UcaAuditWorkStatisticsVo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String projectNameTotal;
    private String userName;
    private Integer picNumTotal;
    private Double picSizeTotal;
    private Integer movMinuteTotal;
    private Double movSizeTotal;

    public UcaAuditWorkStatisticsVo() {
    }

    public String getProjectNameTotal() {
        return projectNameTotal;
    }

    public void setProjectNameTotal(String projectNameTotal) {
        this.projectNameTotal = projectNameTotal;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPicNumTotal() {
        return picNumTotal;
    }

    public void setPicNumTotal(Integer picNumTotal) {
        this.picNumTotal = picNumTotal;
    }

    public Double getPicSizeTotal() {
        return picSizeTotal;
    }

    public void setPicSizeTotal(Double picSizeTotal) {
        this.picSizeTotal = picSizeTotal;
    }

    public Integer getMovMinuteTotal() {
        return movMinuteTotal;
    }

    public void setMovMinuteTotal(Integer movMinuteTotal) {
        this.movMinuteTotal = movMinuteTotal;
    }

    public Double getMovSizeTotal() {
        return movSizeTotal;
    }

    public void setMovSizeTotal(Double movSizeTotal) {
        this.movSizeTotal = movSizeTotal;
    }

}
