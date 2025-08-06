package com.example.mostin.models;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes6.dex */
public class CommuteModel {

    @SerializedName("commuteDay")
    private String commuteDay;

    @SerializedName("employeeId")
    private String employeeId;

    @SerializedName("employeeName")
    private String employeeName;

    @SerializedName("endTime")
    private String endTime;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("workPlaceName")
    private String workPlaceName;

    public CommuteModel(String commuteDay, String employeeId, String employeeName, String startTime, String endTime, String workPlaceName) {
        this.commuteDay = commuteDay;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workPlaceName = workPlaceName;
    }

    public String getCommuteDay() {
        return this.commuteDay;
    }

    public void setCommuteDay(String commuteDay) {
        this.commuteDay = commuteDay;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWorkPlaceName() {
        return this.workPlaceName;
    }

    public void setWorkPlaceName(String workPlaceName) {
        this.workPlaceName = workPlaceName;
    }
}
