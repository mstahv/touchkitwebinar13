package com.touchexample.mwh;

import java.util.Calendar;

import org.vaadin.addon.touchkitstuff.calendarview.CalendarView;

import com.example.domain.WHService;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * The UI's "main" class
 */
@Widgetset("com.touchexample.mwh.gwt.AppWidgetSet")
@Theme("touchkit")
public class WorkHoursUI extends UI {


	@Override
	protected void init(VaadinRequest request) {
		getPage().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
			
			@Override
			public void browserWindowResized(BrowserWindowResizeEvent event) {
				int width2 = event.getWidth();
				Notification.show("width" + width2);
				
			}
		});
		
		setContent(new CalendarView( new MyDayView(Calendar.getInstance())));
		
		
	}

	private WHService s = new WHService();
	
	public static WHService getService() {
		return ((WorkHoursUI) WorkHoursUI.getCurrent()).s;
	}

}