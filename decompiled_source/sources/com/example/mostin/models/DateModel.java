package com.example.mostin.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/* loaded from: classes6.dex */
public class DateModel {
    private String clockInTime;
    private String clockOutTime;
    private String date;
    private int day;
    private boolean isCurrentMonth;

    public DateModel(int day, boolean isCurrentMonth, String clockInTime, String clockOutTime) {
        this.day = day;
        this.isCurrentMonth = isCurrentMonth;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
        if (isCurrentMonth) {
            Calendar cal = Calendar.getInstance();
            cal.set(5, day);
            this.date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.getTime());
        }
    }

    public DateModel(String date, String clockInTime) {
        this.date = date;
        this.clockInTime = clockInTime;
        this.clockOutTime = null;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClockInTime() {
        return this.clockInTime;
    }

    public void setClockInTime(String clockInTime) {
        this.clockInTime = clockInTime;
    }

    public String getClockOutTime() {
        return this.clockOutTime;
    }

    public void setClockOutTime(String clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    public boolean isCurrentMonth() {
        return this.isCurrentMonth;
    }

    public void setCurrentMonth(boolean currentMonth) {
        this.isCurrentMonth = currentMonth;
    }
}
