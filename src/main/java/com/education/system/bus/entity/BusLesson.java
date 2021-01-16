package com.education.system.bus.entity;

import java.util.Date;

public class BusLesson {
    private Integer id;

    private Integer lessonSort;

    private String lessonLabel;

    private String lessonName;

    private String lessonKnowledge;

    private String lessonAbility;

    private Integer textbookId;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLessonSort() {
        return lessonSort;
    }

    public void setLessonSort(Integer lessonSort) {
        this.lessonSort = lessonSort;
    }

    public String getLessonLabel() {
        return lessonLabel;
    }

    public void setLessonLabel(String lessonLabel) {
        this.lessonLabel = lessonLabel == null ? null : lessonLabel.trim();
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName == null ? null : lessonName.trim();
    }

    public String getLessonKnowledge() {
        return lessonKnowledge;
    }

    public void setLessonKnowledge(String lessonKnowledge) {
        this.lessonKnowledge = lessonKnowledge == null ? null : lessonKnowledge.trim();
    }

    public String getLessonAbility() {
        return lessonAbility;
    }

    public void setLessonAbility(String lessonAbility) {
        this.lessonAbility = lessonAbility == null ? null : lessonAbility.trim();
    }

    public Integer getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(Integer textbookId) {
        this.textbookId = textbookId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}