package com.touchexample.mwh;

import java.util.Calendar;
import java.util.List;

import org.vaadin.addon.touchkitstuff.calendarview.DayView;

import com.example.domain.WorkEntry;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickEvent;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickListener;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class MyDayView extends DayView {

	public MyDayView(Calendar calendar) {
		super(calendar);
	}

	@Override
	public void buildView() {
		super.buildView();

		float sum = 0;

		VerticalComponentGroup group = new VerticalComponentGroup();
		List<WorkEntry> workEntries = WorkHoursUI.getService().getWorkEntries(
				getCalendar().getTime());
		for (final WorkEntry workEntry : workEntries) {
			NavigationButton navigationButton = new NavigationButton(
					workEntry.toString());
			navigationButton
					.addClickListener(new NavigationButtonClickListener() {

						@Override
						public void buttonClick(NavigationButtonClickEvent event) {
							getNavigationManager().navigateTo(
									new EntryView(workEntry));

						}
					});
			group.addComponent(navigationButton);

			sum += workEntry.getDuration();
		}

		NavigationButton navigationButton = new NavigationButton("New entry");
		navigationButton.addClickListener(new NavigationButtonClickListener() {
			
			@Override
			public void buttonClick(NavigationButtonClickEvent event) {
				WorkEntry workEntry = new WorkEntry();
				workEntry.setDate(getCalendar().getTime());
				getNavigationManager().navigateTo(
						new EntryView(workEntry));
			}
		});

		group.addComponent(navigationButton);

		setContent(new CssLayout(group, new Label("Total today " + sum + "h")));
	}

}
