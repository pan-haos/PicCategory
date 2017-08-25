package com.we.piccategory.bean;

public class Task {
    private int userId;
    private int taskCount;
    private int taskCompleteCount;
    private int extraIntegral;
    private int integral;

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getExtraIntegral() {
        return extraIntegral;
    }

    public void setExtraIntegral(int extraIntegral) {
        this.extraIntegral = extraIntegral;
    }

    public Task() {

    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public int getTaskCompleteCount() {
        return taskCompleteCount;
    }

    public void setTaskCompleteCount(int taskCompleteCount) {
        this.taskCompleteCount = taskCompleteCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
