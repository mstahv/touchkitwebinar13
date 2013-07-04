package com.touchexample.mwh;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.addon.touchkit.settings.ApplicationIcons;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;

@WebServlet("/*")
public class MyServlet extends TouchKitServlet {

	private MyUIProvider uiProvider = new MyUIProvider();

	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();
		getService().addSessionInitListener(new SessionInitListener() {
			@Override
			public void sessionInit(SessionInitEvent event)
					throws ServiceException {
				event.getSession().addUIProvider(uiProvider);
			}
		});
		ApplicationIcons applicationIcons = getTouchKitSettings()
				.getApplicationIcons();
		applicationIcons
				.addApplicationIcon("https://vaadin.com/vaadin-theme/images/addon-page/charts/icon-html5.png");
	}

}
