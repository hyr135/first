package com.education.system.bus.entity;

import java.util.Date;

public class BusHardware {
    private Integer id;

    private String hardwareName;

    private Integer textbookId;

    private Integer levelId;

    private Integer hardwarePrice;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHardwareName() {
        return hardwareName;
    }

    public void setHardwareName(String hardwareName) {
        this.hardwareName = hardwareName == null ? null : hardwareName.trim();
    }

    public Integer getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(Integer textbookId) {
        this.textbookId = textbookId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getHardwarePrice() {
        return hardwarePrice;
    }

    public void setHardwarePrice(Integer hardwarePrice) {
        this.hardwarePrice = hardwarePrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}