package com.education.system.sys.entity;

import java.util.Date;

public class BusOrg {
    private Integer id;

    private String orgName;

    private String orgAddress;

    private String orgCode;

    private String orgIcon;

    private String orgImg;

    private Integer orgPid;

    private String orgPerson;

    private Integer orgStage;

    private String orgPhone;

    private Integer orgComputer;

    private String orgBoss;

    private String orgProvince;

    private String orgCity;

    private String orgCounty;

    private Integer orgSort;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress == null ? null : orgAddress.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getOrgIcon() {
        return orgIcon;
    }

    public void setOrgIcon(String orgIcon) {
        this.orgIcon = orgIcon == null ? null : orgIcon.trim();
    }

    public String getOrgImg() {
        return orgImg;
    }

    public void setOrgImg(String orgImg) {
        this.orgImg = orgImg == null ? null : orgImg.trim();
    }

    public Integer getOrgPid() {
        return orgPid;
    }

    public void setOrgPid(Integer orgPid) {
        this.orgPid = orgPid;
    }

    public String getOrgPerson() {
        return orgPerson;
    }

    public void setOrgPerson(String orgPerson) {
        this.orgPerson = orgPerson == null ? null : orgPerson.trim();
    }

    public Integer getOrgStage() {
        return orgStage;
    }

    public void setOrgStage(Integer orgStage) {
        this.orgStage = orgStage;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone == null ? null : orgPhone.trim();
    }

    public Integer getOrgComputer() {
        return orgComputer;
    }

    public void setOrgComputer(Integer orgComputer) {
        this.orgComputer = orgComputer;
    }

    public String getOrgBoss() {
        return orgBoss;
    }

    public void setOrgBoss(String orgBoss) {
        this.orgBoss = orgBoss == null ? null : orgBoss.trim();
    }

    public String getOrgProvince() {
        return orgProvince;
    }

    public void setOrgProvince(String orgProvince) {
        this.orgProvince = orgProvince == null ? null : orgProvince.trim();
    }

    public String getOrgCity() {
        return orgCity;
    }

    public void setOrgCity(String orgCity) {
        this.orgCity = orgCity == null ? null : orgCity.trim();
    }

    public String getOrgCounty() {
        return orgCounty;
    }

    public void setOrgCounty(String orgCounty) {
        this.orgCounty = orgCounty == null ? null : orgCounty.trim();
    }

    public Integer getOrgSort() {
        return orgSort;
    }

    public void setOrgSort(Integer orgSort) {
        this.orgSort = orgSort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}