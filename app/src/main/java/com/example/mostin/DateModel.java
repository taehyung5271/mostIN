package com.example.mostin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateModel {
    private int day;                // 날짜 (1, 2, 3, ...)
    private String date;            // 날짜 (YYYY-MM-DD)
    private boolean isCurrentMonth; // 현재 월 여부
    private String clockInTime;     // 출근 시간
    private String clockOutTime;    // 퇴근 시간

    // 기본 생성자
    public DateModel(int day, boolean isCurrentMonth, String clockInTime, String clockOutTime) {
        this.day = day;
        this.isCurrentMonth = isCurrentMonth;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;

        // YYYY-MM-DD 형식의 날짜를 자동 생성
        if (isCurrentMonth) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, day);
            this.date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.getTime());
        }
    }

    public DateModel(String date, String clockInTime) {
        this.date = date;
        this.clockInTime = clockInTime;
        this.clockOutTime = null; // 필요시 추가
    }

    // Getter 및 Setter
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(String clockInTime) {
        this.clockInTime = clockInTime;
    }

    public String getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(String clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    public void setCurrentMonth(boolean currentMonth) {
        isCurrentMonth = currentMonth;
    }
}