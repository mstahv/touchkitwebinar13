package org.vaadin.addon.touchkitstuff.calendarview;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.vaadin.addon.touchkitstuff.PrevNextButtonGroup;

import com.vaadin.addon.touchkit.ui.NavigationButton;

public class DayView extends CalendarViewNavigationView implements
		PrevNextButtonGroup.Listener {

	public DayView(Calendar calendar) {
		super(calendar);
		setRightComponent(new PrevNextButtonGroup(this));
		buildView();
	}

	private void rollDay(boolean forward) {
		int week = getCalendar().get(Calendar.WEEK_OF_YEAR);
		getCalendar().set(Calendar.DATE, getCalendar().get(Calendar.DATE)
				+ (forward ? 1 : -1));
		buildView();
		int newWeek = getCalendar().get(Calendar.WEEK_OF_YEAR);
		if (week != newWeek) {
			getNavigationManager().setWeek(getCalendar().getTimeInMillis());
			NavigationButton leftComponent = (NavigationButton) getNavigationBar()
					.getLeftComponent();
			leftComponent.setCaption("Week " + newWeek);
		}
	}

	@Override
	public void onNextClick() {
		rollDay(true);
	}

	@Override
	public void onPrevClick() {
		rollDay(false);
	}

	/**
	 * Hook to this method to build content for the day view.
	 */
	public void buildView() {
		SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat
				.getDateInstance(DateFormat.LONG);
		format.applyPattern("EEEE, MMM d, yyyy");
		setCaption(format.format(getCalendar().getTime()));
	}

	public void setTime(long timeInMillis) {
		getCalendar().setTimeInMillis(timeInMillis);
		buildView();
	}

}