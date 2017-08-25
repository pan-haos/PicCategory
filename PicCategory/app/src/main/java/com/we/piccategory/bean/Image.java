package com.we.piccategory.bean;

import java.util.Date;

public class Image {
    private Integer id;

    private String imageUrl;

    private String label;

    private Integer count;

    private Integer isExport;

    private Date uploadTime;

    private Date lableLastTime;

    private String type;

    private String deleteLabel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getIsExport() {
        return isExport;
    }

    public void setIsExport(Integer isExport) {
        this.isExport = isExport;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getLableLastTime() {
        return lableLastTime;
    }

    public void setLableLastTime(Date lableLastTime) {
        this.lableLastTime = lableLastTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDeleteLabel() {
        return deleteLabel;
    }

    public void setDeleteLabel(String deleteLabel) {
        this.deleteLabel = deleteLabel == null ? null : deleteLabel.trim();
    }
}