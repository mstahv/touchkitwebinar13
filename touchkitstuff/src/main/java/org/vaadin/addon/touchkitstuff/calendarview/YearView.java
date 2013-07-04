package org.vaadin.addon.touchkitstuff.calendarview;

import java.util.Calendar;
import java.util.Locale;

import com.vaadin.addon.touchkit.gwt.client.theme.StyleNames;
import com.vaadin.addon.touchkit.ui.HorizontalButtonGroup;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickEvent;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class YearView extends CalendarViewNavigationView {
	public YearView(Calendar cal) {
		super(cal);

		HorizontalButtonGroup horizontalButtonGroup = new HorizontalButtonGroup();
		Button prev = new Button();
		prev.setStyleName(StyleNames.NAVBAR_BUTTON_ARROW_UP);
		prev.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getCalendar().roll(Calendar.YEAR, false);
				buildView();
			}
		});
		Button next = new Button();
		next.setStyleName(StyleNames.NAVBAR_BUTTON_ARROW_DOWN);
		next.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getCalendar().roll(Calendar.YEAR, true);
				buildView();
			}
		});
		horizontalButtonGroup.addComponents(prev, next);
		setRightComponent(horizontalButtonGroup);

		buildView();
	}

	public void buildView() {
		Calendar c = (Calendar) getCalendar().clone();
		final int year = c.get(Calendar.YEAR);
		setCaption("Year " + year);
		int currentMonth = 0;
		c.set(Calendar.MONTH, currentMonth);
		c.set(Calendar.DAY_OF_MONTH, 1);
		CssLayout layout = new CssLayout();
		VerticalComponentGroup monthGroup = null;
		int currentYear = year;
		while (currentYear <= year) {
			final long timeInMillis = c.getTimeInMillis();
			if (monthGroup == null
					|| currentMonth != c.get(Calendar.MONTH)) {
				String month = c.getDisplayName(Calendar.MONTH,
						Calendar.LONG, getLocale());
				monthGroup = new VerticalComponentGroup(month);
				layout.addComponent(monthGroup);
				currentMonth = c.get(Calendar.MONTH);
			}
			String week = "Week " + c.get(Calendar.WEEK_OF_YEAR);
			NavigationButton navigationButton = new NavigationButton(week);
			navigationButton
					.addClickListener(new NavigationButtonClickListener() {

						@Override
						public void buttonClick(NavigationButtonClickEvent event) {
							getNavigationManager().navigateToWeek(timeInMillis);
						}
					});
			monthGroup.addComponent(navigationButton);
			c.set(Calendar.DATE,
					c.get(Calendar.DATE) + 7);
			currentYear = c.get(Calendar.YEAR);
		}
		setContent(layout);
	}

	@Override
	public Locale getLocale() {
		Locale locale = super.getLocale();
		if (locale == null) {
			locale = Locale.ENGLISH;
		}
		return locale;
	}

	public void setYear(int year2) {
		getCalendar().set(Calendar.YEAR, year2);
		buildView();
	}
	
	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();
		getNavigationManager().setNextComponent(null);
	}


}