package com.example.mostin.models;

import com.google.gson.annotations.SerializedName;

public class CommuteModel {
    @SerializedName("commuteDay")
    private String commuteDay; // Assuming date is sent as String (YYYY-MM-DD)
    @SerializedName("employeeId")
    private String employeeId;
    @SerializedName("employeeName")
    private String employeeName;
    @SerializedName("startTime")
    private String startTime; // Assuming time is sent as String (HH:MM:SS)
    @SerializedName("endTime")
    private String endTime;   // Assuming time is sent as String (HH:MM:SS)
    @SerializedName("workPlaceName")
    private String workPlaceName;

    // Constructor
    public CommuteModel(String commuteDay, String employeeId, String employeeName, String startTime, String endTime, String workPlaceName) {
        this.commuteDay = commuteDay;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workPlaceName = workPlaceName;
    }

    // Getters and Setters
    public String getCommuteDay() {
        return commuteDay;
    }

    public void setCommuteDay(String commuteDay) {
        this.commuteDay = commuteDay;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWorkPlaceName() {
        return workPlaceName;
    }

    public void setWorkPlaceName(String workPlaceName) {
        this.workPlaceName = workPlaceName;
    }
}