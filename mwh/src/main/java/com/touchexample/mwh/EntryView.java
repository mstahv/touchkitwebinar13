package com.touchexample.mwh;

import org.vaadin.addon.beanbinder.BeanBinder;
import org.vaadin.addon.touchkitstuff.BeanSelector;
import org.vaadin.addon.touchkitstuff.calendarview.CalendarViewNavigationView;

import com.example.domain.BillingType;
import com.example.domain.WorkEntry;
import com.vaadin.addon.touchkit.gwt.client.theme.StyleNames;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickEvent;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickListener;
import com.vaadin.addon.touchkit.ui.NumberField;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextArea;

public class EntryView extends CalendarViewNavigationView {

	private WorkEntry we;

	private TextArea description = new TextArea("Description");
	private NumberField duration = new NumberField("Duration");
	private BeanSelector<BillingType> billingType = new BeanSelector<BillingType>(
			WorkHoursUI.getService().getBillingTypes());

	public EntryView(WorkEntry e) {
		super(null);
		we = e;
		setCaption(e.toString());
		billingType.setVisibleProperties("customer", "name", "hourPrice");
		billingType.setCaption("Billing type");
	}

	@Override
	public void buildView() {
		
		VerticalComponentGroup verticalComponentGroup = new VerticalComponentGroup("Edit entry");
		verticalComponentGroup.addComponents(billingType, duration, description);
		
		for (Component component : verticalComponentGroup) {
			component.setWidth("100%");
		}
		
		BeanBinder.bind(we, this);
		
		
		NavigationButton leftComponent = (NavigationButton) getNavigationBar().getLeftComponent();
		leftComponent.addClickListener(new NavigationButtonClickListener() {
			
			@Override
			public void buttonClick(NavigationButtonClickEvent event) {
				WorkHoursUI.getService().saveOrPersist(we);
				getNavigationManager().getDayView().buildView();
			}
		});
		
		Button button = new Button("Delete");
		button.setStyleName(StyleNames.BUTTON_RED);
		button.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				WorkHoursUI.getService().delete(we);
				getNavigationManager().getDayView().buildView();
				getNavigationManager().navigateBack();
			}
		});
		
		button.setWidth("100%");
		
		setContent(new CssLayout(verticalComponentGroup, button));
	
	}

}
