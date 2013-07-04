package org.vaadin.addon.touchkitstuff;

import java.util.Collection;
import java.util.List;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * A simple "Select alternative" that displays items using a Popover and Table.
 * Also contains built in filtering and sorting.
 * 
 * @param <T> type of beans from which value is selected
 */
public class BeanSelector<T> extends CustomField<T> implements
		ItemClickListener, LayoutClickListener {

	private Label display = new Label();
	private List<T> list;
	private Popover popover;
	private Object[] visibleProperties;
	private TextField searchField;
	private BeanItemContainer<T> container;
	private Table table;

	/**
	 * Constructs a new bean selector with given bean list
	 * 
	 * @param beans non empty list of beans from which the value is chosen
	 */
	public BeanSelector(List<T> beans) {
		this.list = beans;
		setStyleName("beansel");
		display.setStyleName("grey-title");
		display.setSizeUndefined();
		display.setValue(getNullRepresentationString());
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public void setVisibleProperties(Object... visibleProperties) {
		this.visibleProperties = visibleProperties;
	}

	@Override
	protected Component initContent() {
		CssLayout cssLayout = new CssLayout(display) {
			@Override
			protected String getCss(Component c) {
				return "margin:0;";
			}
		};
		cssLayout.addLayoutClickListener(this);
		return cssLayout;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends T> getType() {
		return getPropertyDataSource().getType();
	}

	@Override
	protected void setInternalValue(T newValue) {
		super.setInternalValue(newValue);
		if (newValue == null) {
			display.setValue(getNullRepresentationString());
		} else {
			display.setValue(getDisplayString(newValue));
		}

	}

	protected String getDisplayString(T newValue) {
		return newValue.toString();
	}

	protected String getNullRepresentationString() {
		return "Choose...";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void itemClick(ItemClickEvent event) {
		setValue((T) event.getItemId());
		popover.close();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void layoutClick(LayoutClickEvent event) {
		popover = new Popover();

		table = new Table();
		popover.setWidth("400px");
		popover.setHeight("200px");
		table.setSizeFull();
		container = new BeanItemContainer<T>((Collection<T>) list);
		table.setContainerDataSource(container);
		table.addItemClickListener(this);
		if (getValue() != null) {
			table.setValue(getValue());
			table.setPageLength(4);
			table.setCurrentPageFirstItemId(getValue());
		}
		if (visibleProperties != null) {
			table.setVisibleColumns(visibleProperties);
		}

		searchField = new TextField();
		searchField.setInputPrompt("filter...");
		searchField.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(TextChangeEvent event) {
				final String text = event.getText();
				container.removeAllContainerFilters();
				container.addContainerFilter(new Filter() {

					@Override
					public boolean passesFilter(Object itemId, Item item)
							throws UnsupportedOperationException {
						StringBuilder sb = new StringBuilder();
						for (Object o : item.getItemPropertyIds()) {
							sb.append(item.getItemProperty(o).getValue());
						}
						return sb.toString().toLowerCase()
								.contains(text.toLowerCase());
					}

					@Override
					public boolean appliesToProperty(Object propertyId) {
						return true;
					}
				});
			}
		});
		searchField.setVisible(false);
		searchField.setWidth("100%");
		table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);

		VerticalLayout l = new VerticalLayout(searchField, table);
		l.setSizeFull();
		l.setExpandRatio(table, 1);
		NavigationView content = new NavigationView(l);
		content.setCaption(getCaption());

		Button button = createSearchButton();
		button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				boolean visible = searchField.isVisible();
				if (!visible) {
					table.setColumnHeaderMode(ColumnHeaderMode.EXPLICIT_DEFAULTS_ID);
					searchField.setVisible(true);
				} else {
					searchField.setVisible(false);
					table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
				}
			}
		});
		content.setRightComponent(button);

		popover.setContent(content);
		popover.showRelativeTo(display);
	}

	protected Button createSearchButton() {
		return new Button("Search");
	}

}
