package org.vaadin.addon.touchkitstuff.calendarview;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.vaadin.addon.touchkit.gwt.client.theme.StyleNames;
import com.vaadin.addon.touchkit.ui.HorizontalButtonGroup;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickEvent;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class WeekView extends CalendarViewNavigationView {

	SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
	SimpleDateFormat dfDetail = (SimpleDateFormat) SimpleDateFormat
			.getDateInstance();
	{
		df.applyPattern("EEEE");
		dfDetail.applyPattern("MMM d");
	}

	public VerticalComponentGroup group = new VerticalComponentGroup();
	private int year;

	public WeekView(long timeInMillis) {
		super(null);

		HorizontalButtonGroup horizontalButtonGroup = new HorizontalButtonGroup();
		Button prev = new Button();
		prev.setStyleName(StyleNames.NAVBAR_BUTTON_ARROW_UP);
		prev.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getCalendar().set(Calendar.DATE,
						getCalendar().get(Calendar.DATE) - 7);
				buildView();
				checkYearChange();
			}

		});
		Button next = new Button();
		next.setStyleName(StyleNames.NAVBAR_BUTTON_ARROW_DOWN);
		next.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getCalendar().set(Calendar.DATE,
						getCalendar().get(Calendar.DATE) + 7);
				buildView();
				checkYearChange();
			}
		});
		horizontalButtonGroup.addComponents(prev, next);
		setRightComponent(horizontalButtonGroup);

		setTime(timeInMillis);
	}

	protected void setTime(long timeInMillis) {
		getCalendar().setTimeInMillis(timeInMillis);
		year = getCalendar().get(Calendar.YEAR);
		buildView();

	}

	public int getYear() {
		return year;
	}

	private void checkYearChange() {
		int newYear = getCalendar().get(Calendar.YEAR);
		if (newYear != year) {
			getNavigationManager().setYear(newYear);
			year = newYear;
			NavigationButton backButton = (NavigationButton) getNavigationBar()
					.getLeftComponent();
			backButton.setCaption("Year " + year);
		}
	}

	public void buildView() {
		String weekStr = "Week " + getCalendar().get(Calendar.WEEK_OF_YEAR);
		setCaption(weekStr);

		getCalendar().set(Calendar.DAY_OF_WEEK,
				getCalendar().getFirstDayOfWeek());
		group.removeAllComponents();
		for (int i = 0; i < 7; i++) {
			final Calendar cal = (Calendar) getCalendar().clone();
			final String dateStr = df.format(getCalendar().getTime());
			NavigationButton navigationButton = new NavigationButton(dateStr);
			navigationButton.setDescription(dfDetail.format(getCalendar()
					.getTime()));
			navigationButton
					.addClickListener(new NavigationButtonClickListener() {
						@Override
						public void buttonClick(NavigationButtonClickEvent event) {
							getNavigationManager().navigateToDay(cal);
						}
					});
			group.addComponent(navigationButton);
			getCalendar().set(Calendar.DATE,
					getCalendar().get(Calendar.DATE) + 1);
		}
		getCalendar().set(Calendar.DATE, getCalendar().get(Calendar.DATE) - 7); // roll
																				// back
																				// to
		// current week
		setContent(group);
	}

	public WeekView(Calendar calendar) {
		this(calendar.getTimeInMillis());
	}
	
	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();
		getNavigationManager().setNextComponent(null);
	}

}