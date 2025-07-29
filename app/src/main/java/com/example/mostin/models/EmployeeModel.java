package com.example.mostin.models;

import com.google.gson.annotations.SerializedName;

public class EmployeeModel {
    private String employeeId;
    private String employeeName;
    private String employeeType;
    private String workPlaceName;
    private String phoneNum;
    @SerializedName("employeePwd")
    private String password;
    private String address;

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

    public String getEmployeeId() { return employeeId; }
    public String getEmployeeName() { return employeeName; }
    public String getEmployeeType() { return employeeType; }
    public String getWorkPlaceName() { return workPlaceName; }
    public String getPhoneNum() { return phoneNum; }
    public String getPassword() { return password; }
    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }

    public void setEmployeeName(String employeeName) { 
        this.employeeName = employeeName; 
    }
    
    public void setEmployeeType(String employeeType) { 
        this.employeeType = employeeType; 
    }
    
    public void setWorkPlaceName(String workPlaceName) { 
        this.workPlaceName = workPlaceName; 
    }

    @Override
    public String toString() {
        return employeeName + " (" + employeeId + ")";
    }
} 