package com.education.system.bus.entity;

import java.math.BigDecimal;
import java.util.Date;

public class BusTextbook {
    private Integer id;

    private String textbookName;

    private String textbookTitle;

    private Integer levelId;

    private String textbookImg;

    private Integer totalCourses;

    private String semesterId;

    private String press;

    private BigDecimal textbookPrice;

    private String programTools;

    private Integer textbookState;

    private Date createTime;

    private String textbookIntroduction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTextbookName() {
        return textbookName;
    }

    public void setTextbookName(String textbookName) {
        this.textbookName = textbookName == null ? null : textbookName.trim();
    }

    public String getTextbookTitle() {
        return textbookTitle;
    }

    public void setTextbookTitle(String textbookTitle) {
        this.textbookTitle = textbookTitle == null ? null : textbookTitle.trim();
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getTextbookImg() {
        return textbookImg;
    }

    public void setTextbookImg(String textbookImg) {
        this.textbookImg = textbookImg == null ? null : textbookImg.trim();
    }

    public Integer getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Integer totalCourses) {
        this.totalCourses = totalCourses;
    }

    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId == null ? null : semesterId.trim();
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press == null ? null : press.trim();
    }

    public BigDecimal getTextbookPrice() {
        return textbookPrice;
    }

    public void setTextbookPrice(BigDecimal textbookPrice) {
        this.textbookPrice = textbookPrice;
    }

    public String getProgramTools() {
        return programTools;
    }

    public void setProgramTools(String programTools) {
        this.programTools = programTools == null ? null : programTools.trim();
    }

    public Integer getTextbookState() {
        return textbookState;
    }

    public void setTextbookState(Integer textbookState) {
        this.textbookState = textbookState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTextbookIntroduction() {
        return textbookIntroduction;
    }

    public void setTextbookIntroduction(String textbookIntroduction) {
        this.textbookIntroduction = textbookIntroduction == null ? null : textbookIntroduction.trim();
    }
}