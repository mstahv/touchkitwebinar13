package org.vaadin.addon.touchkitstuff.demoandtestapp;

import org.vaadin.addon.touchkitstuff.calendarview.CalendarView;

import com.vaadin.ui.Component;

public class BasicCalendarTest extends AbstractTest {

    @Override
    public String getDescription() {
        return "A simple test";
    }

    @Override
    Component getTestComponent() {
        CalendarView calendarView = new CalendarView();
        calendarView.setSizeFull();
		return calendarView;
    }

}
