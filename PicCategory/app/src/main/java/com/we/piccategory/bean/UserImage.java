package com.we.piccategory.bean;

import java.util.Date;

public class UserImage {
    private String userName;
    private String headImage;
    private Integer userId;
    private String msg;
    private String imageUrl;
    private Date createTime;
    private String imageLabel;

    public String getImageLabel() {
        return imageLabel;
    }

    public void setImageLabel(String imageLabel) {
        this.imageLabel = imageLabel;
    }

    public UserImage() {

    }

    public UserImage(Integer userId, String msg, String imageUrl, Date createTime) {
        super();
        this.userId = userId;
        this.msg = msg;
        this.imageUrl = imageUrl;
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}
