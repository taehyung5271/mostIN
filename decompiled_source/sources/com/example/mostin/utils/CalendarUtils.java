package com.example.mostin.utils;

import com.example.mostin.models.DateModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/* loaded from: classes6.dex */
public class CalendarUtils {
    public static List<DateModel> generateMonthlyCalendar(Calendar calendar) {
        List<DateModel> dates = new ArrayList<>();
        Calendar temp = (Calendar) calendar.clone();
        temp.set(5, 1);
        int firstDayOfWeek = temp.get(7) - 1;
        int daysInMonth = temp.getActualMaximum(5);
        temp.add(2, -1);
        int prevMonthDays = temp.getActualMaximum(5);
        for (int i = firstDayOfWeek - 1; i >= 0; i--) {
            dates.add(new DateModel(prevMonthDays - i, false, null, null));
        }
        for (int i2 = 1; i2 <= daysInMonth; i2++) {
            dates.add(new DateModel(i2, true, null, null));
        }
        int nextMonthDays = 42 - dates.size();
        for (int i3 = 1; i3 <= nextMonthDays; i3++) {
            dates.add(new DateModel(i3, false, null, null));
        }
        return dates;
    }

    public static List<DateModel> generateCalendarMonth(int year, int month) {
        new ArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        List<DateModel> calendarMonth = generateMonthlyCalendar(calendar);
        return calendarMonth;
    }
}
