package com.rotabug.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.rotabug.client.ViewBox;

public class View extends Composite {
	protected final FlowPanel viewPanel;

	public View() {
		viewPanel = new FlowPanel();
		initWidget(viewPanel);
		viewPanel.setStyleName("view");
		setTitle("");
	}

	public Widget asWidget() {
		return this;
	}

	// Called immediately after the view has been displayed.
	public void onShow(ViewBox container) {
	}

	// Called immediately after the view has been closed.
	public void onClose(ViewBox container) {
	}

}
