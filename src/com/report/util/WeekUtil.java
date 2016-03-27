package com.report.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class WeekUtil {

	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

		return getWeekOfYear(c.getTime());
	}

	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	public static int getDaysOfMonth(String year, String month) {
		int days = 0;
		int monthValue = Integer.parseInt(month);
		if (monthValue == 2) {
			GregorianCalendar cal = new GregorianCalendar();
			if (cal.isLeapYear(Integer.parseInt(year))) {
				days = 28;
			} else {
				days = 29;
			}
		} else {
			switch (monthValue) {
			case 1:
				days = 31;
				break;
			case 3:
				days = 31;
				break;
			case 5:
				days = 31;
				break;
			case 7:
				days = 31;
				break;
			case 8:
				days = 31;
				break;
			case 10:
				days = 31;
				break;
			case 12:
				days = 31;
				break;
			case 4:
				days = 30;
				break;
			case 6:
				days = 30;
				break;
			case 9:
				days = 30;
				break;
			case 11:
				days = 30;
				break;
			}
		}
		return days;
	}
}
