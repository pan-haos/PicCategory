package com.we.piccategory.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Label {
    private Integer id;

    private Integer userId;

    private String imageUrl;

    private String imageLabel;

    private String useLabel;

    private String noUseLabel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Date updateTime;

    private Date lastTime;

    public Label() {
    }

    public Label(Integer userId, String imageUrl, String imageLabel, Date createTime) {
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.imageLabel = imageLabel;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getImageLabel() {
        return imageLabel;
    }

    public void setImageLabel(String imageLabel) {
        this.imageLabel = imageLabel == null ? null : imageLabel.trim();
    }

    public String getUseLabel() {
        return useLabel;
    }

    public void setUseLabel(String useLabel) {
        this.useLabel = useLabel == null ? null : useLabel.trim();
    }

    public String getNoUseLabel() {
        return noUseLabel;
    }

    public void setNoUseLabel(String noUseLabel) {
        this.noUseLabel = noUseLabel == null ? null : noUseLabel.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}