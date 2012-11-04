package com.rotabug.client;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.rotabug.client.presenter.Display;

// An object that displays a series of "views" within the main application window. The views are 
// not queued - each view is displayed immediately by the "put" method, replacing the previous view.
public class AppUI implements ViewBox {
	private HasWidgets container;
	private Display display;

	// "appui" is the widget that forms the main application window.
	public AppUI(HasWidgets appui) {
		this.container = appui;
		this.display = null;
	}

	public void put(Display display) {
		container.clear();
		container.add(display.asWidget());
		this.display = display;
		display.onShow(this);
	}

	public void close() {
		display.onClose(this);
		display = null;
		History.back();
	}

}
