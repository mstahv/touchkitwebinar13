package org.vaadin.addon.touchkitstuff;

import com.vaadin.addon.touchkit.gwt.client.theme.StyleNames;
import com.vaadin.addon.touchkit.ui.HorizontalButtonGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * A Button group with next and previous button and simple listener. Uses
 * TouchKits built in style names.
 */
public class PrevNextButtonGroup extends HorizontalButtonGroup {

	public interface Listener {
		void onNextClick();

		void onPrevClick();
	}

	public PrevNextButtonGroup(final Listener listener) {
		Button prev = new Button();
		prev.setStyleName(StyleNames.NAVBAR_BUTTON_ARROW_UP);
		prev.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				listener.onPrevClick();
			}

		});
		Button next = new Button();
		next.setStyleName(StyleNames.NAVBAR_BUTTON_ARROW_DOWN);
		next.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				listener.onNextClick();
			}
		});
		addComponents(prev, next);
	}

}
