package com.we.piccategory.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class User implements Parcelable {
    private Integer id;

    private String userName;

    private String password;

    private String phone;

    private String jop;

    private Integer integral;

    private String sex;

    private String preference;

    private String headImageUrl;

    private Integer completeCount;

    private Date registTime;

    private Integer taskCount;

    public User() {
    }

    public User(String userName, String sex, String jop, String preference) {
        this.userName = userName;
        this.sex = sex;
        this.jop = jop;
        this.preference = preference;
    }

    public User(String userName, String password, String phone, String jop, String sex, String preference, String headImageUrl) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.jop = jop;
        this.sex = sex;
        this.preference = preference;
        this.headImageUrl = headImageUrl;
    }

    protected User(Parcel in) {
        userName = in.readString();
        password = in.readString();
        phone = in.readString();
        jop = in.readString();
        sex = in.readString();
        preference = in.readString();
        headImageUrl = in.readString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }


    public String getJop() {
        return jop;
    }

    public void setJop(String jop) {
        this.jop = jop == null ? null : jop.trim();
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference == null ? null : preference.trim();
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl == null ? null : headImageUrl.trim();
    }

    public Integer getCompleteCount() {
        return completeCount;
    }

    public void setCompleteCount(Integer completeCount) {
        this.completeCount = completeCount;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(jop);
        dest.writeString(sex);
        dest.writeString(preference);
    }


    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            User mUser = new User();
            mUser.userName = in.readString();
            mUser.jop = in.readString();
            mUser.sex = in.readString();
            mUser.preference = in.readString();
            return mUser;
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}