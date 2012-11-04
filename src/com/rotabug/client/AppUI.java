package com.rotabug.client;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.rotabug.client.presenter.Display;

public class AppUI implements ViewBox {
	private HasWidgets container;
	private Display display;

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
