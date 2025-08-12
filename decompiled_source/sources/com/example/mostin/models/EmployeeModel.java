package com.example.mostin.models;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes6.dex */
public class EmployeeModel {
    private String address;
    private String employeeId;
    private String employeeName;
    private String employeeType;

    @SerializedName("employeePwd")
    private String password;
    private String phoneNum;
    private String workPlaceName;

    public EmployeeModel(String employeeId, String employeeName, String employeeType, String workPlaceName) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeType = employeeType;
        this.workPlaceName = workPlaceName;
    }

    public EmployeeModel(String employeeId, String employeeName, String password, String phone, String type, String address, String workPlace) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.password = password;
        this.phoneNum = phone;
        this.employeeType = type;
        this.address = address;
        this.workPlaceName = workPlace;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public String getEmployeeType() {
        return this.employeeType;
    }

    public String getWorkPlaceName() {
        return this.workPlaceName;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public void setWorkPlaceName(String workPlaceName) {
        this.workPlaceName = workPlaceName;
    }

    public String toString() {
        return this.employeeName + " (" + this.employeeId + ")";
    }
}
