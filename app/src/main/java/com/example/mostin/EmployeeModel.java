package com.example.mostin;

public class EmployeeModel {
    private String employeeId;
    private String employeeName;
    private String employeeType;
    private String workPlaceName;
    private String phoneNum;

    public EmployeeModel(String employeeId, String employeeName, String employeeType, String workPlaceName) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeType = employeeType;
        this.workPlaceName = workPlaceName;
    }

    public String getEmployeeId() { return employeeId; }
    public String getEmployeeName() { return employeeName; }
    public String getEmployeeType() { return employeeType; }
    public String getWorkPlaceName() { return workPlaceName; }
    public String getPhoneNum() { return phoneNum; }
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