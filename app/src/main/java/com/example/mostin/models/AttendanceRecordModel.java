package com.example.mostin.models;

public class AttendanceRecordModel {
    private String date;
    private String employeeId;
    private String employeeName;
    private String clockIn;
    private String clockOut;
    private String workPlace;

    public AttendanceRecordModel(String date, String employeeId, String employeeName,
                                String clockIn, String clockOut, String workPlace) {
        this.date = date;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.workPlace = workPlace;
    }

    // Getters
    public String getDate() { return date; }
    public String getEmployeeId() { return employeeId; }
    public String getEmployeeName() { return employeeName; }
    public String getClockIn() { return clockIn; }
    public String getClockOut() { return clockOut; }
    public String getWorkPlace() { return workPlace; }
} 