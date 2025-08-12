package com.example.mostin.models;

/* loaded from: classes6.dex */
public class AttendanceRecordModel {
    private String clockIn;
    private String clockOut;
    private String date;
    private String employeeId;
    private String employeeName;
    private String workPlace;

    public AttendanceRecordModel(String date, String employeeId, String employeeName, String clockIn, String clockOut, String workPlace) {
        this.date = date;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.workPlace = workPlace;
    }

    public String getDate() {
        return this.date;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public String getClockIn() {
        return this.clockIn;
    }

    public String getClockOut() {
        return this.clockOut;
    }

    public String getWorkPlace() {
        return this.workPlace;
    }
}
