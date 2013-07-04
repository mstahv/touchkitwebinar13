package org.vaadin.addon.touchkitstuff.calendarview;

import java.util.Calendar;

import com.vaadin.addon.touchkit.ui.NavigationView;

public abstract class CalendarViewNavigationView extends NavigationView {

	private Calendar calendar;

	public CalendarViewNavigationView(Calendar c) {
		if (c == null) {
			c = Calendar.getInstance();
		}
		calendar = c;
	}

	/**
	 * @return the calendar instance used by this view
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	@Override
	public CalendarView getNavigationManager() {
		return (CalendarView) super.getNavigationManager();
	}

	/**
	 * Called when view needs to be built initially or rebuilt due to date
	 * change.
	 */
	public abstract void buildView();
	
	@Override
	public void attach() {
		super.attach();
		buildView();
	}

}