package org.vaadin.addon.touchkitstuff.calendarview;

import java.util.Calendar;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationView;

/**
 * A simple {@link NavigationManager} and {@link NavigationView} based calendar.
 * Users should add their application related code (e.g. showing or adding new
 * date dependent data) to {@link YearView}, {@link WeekView} and
 * {@link DayView} or at least one of them.
 * <p>
 * Possible custom views showed in CalendarView may benefit extending
 * {@link CalendarViewNavigationView}.
 */
public class CalendarView extends NavigationManager {

	private YearView yearView;
	private DayView dayView;
	private WeekView weekView;

	public CalendarView() {
		yearView = new YearView(Calendar.getInstance());
		weekView = new WeekView(Calendar.getInstance());
		dayView = new DayView(Calendar.getInstance());
		init();
	}

	private void init() {
		navigateTo(yearView);
		navigateTo(weekView);
		navigateTo(dayView);
	}

	public CalendarView(YearView yearView, WeekView weekView, DayView dayView) {
		this.yearView = yearView;
		this.weekView = weekView;
		this.dayView = dayView;
		init();
	}

	public CalendarView(DayView customDayView) {
		yearView = new YearView(Calendar.getInstance());
		weekView = new WeekView(Calendar.getInstance());
		dayView = customDayView;
		init();
	}

	protected void navigateToWeek(long timeInMillis) {
		weekView.setTime(timeInMillis);
		navigateTo(weekView);
	}

	public void setWeek(long timeInMillis) {
		final int oldYear = weekView.getYear();
		weekView.setTime(timeInMillis);
		if (oldYear != weekView.getYear()) {
			setYear(weekView.getYear());
		}
	}

	protected void setYear(int year) {
		yearView.setYear(year);
	}

	public void navigateToDay(Calendar clone) {
		dayView.setTime(clone.getTimeInMillis());
		navigateTo(dayView);
	}

	public DayView getDayView() {
		return dayView;
	}

	public YearView getYearView() {
		return yearView;
	}

	public WeekView getWeekView() {
		return weekView;
	}

}
