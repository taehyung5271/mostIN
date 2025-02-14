package com.example.mostin;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarUtils {

    public static List<DateModel> generateMonthlyCalendar(Calendar calendar) {
        List<DateModel> dates = new ArrayList<>();

        Calendar temp = (Calendar) calendar.clone();
        temp.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfWeek = temp.get(Calendar.DAY_OF_WEEK) - 1; // 요일 보정 (일요일 시작)
        int daysInMonth = temp.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 이전 월 날짜 추가
        temp.add(Calendar.MONTH, -1);
        int prevMonthDays = temp.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = firstDayOfWeek - 1; i >= 0; i--) {
            dates.add(new DateModel(prevMonthDays - i, false, null, null));
        }

        // 현재 월 날짜 추가
        temp = (Calendar) calendar.clone();
        for (int i = 1; i <= daysInMonth; i++) {
            dates.add(new DateModel(i, true, null, null));
        }

        // 다음 월 날짜 추가
        int nextMonthDays = 42 - dates.size();
        for (int i = 1; i <= nextMonthDays; i++) {
            dates.add(new DateModel(i, false, null, null));
        }

        return dates;
    }

    // 특정 연도와 월의 달력을 생성
    public static List<DateModel> generateCalendarMonth(int year, int month) {
        List<DateModel> calendarMonth = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        // 연도와 월을 설정
        calendar.set(year, month - 1, 1);

        // 달력 생성
        calendarMonth = generateMonthlyCalendar(calendar);

        return calendarMonth;
    }

    // 출근/퇴근 데이터를 추가한 달력 생성


}