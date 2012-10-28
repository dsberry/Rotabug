package com.rotabug.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.rotabug.client.presenter.Test2Presenter;
import com.rotabug.client.view.View;

public class Test2View extends View implements Test2Presenter.Test2Display {
	private final Button testButton;

	public Test2View() {
		super();
		viewPanel.add(new Label("Test 2 view"));
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		viewPanel.add(hp);
		testButton = new Button("Test 1");
		hp.add(testButton);
	}

	public HasClickHandlers getTestButton() {
		return testButton;
	}

}
