package com.rotabug.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.rotabug.client.presenter.Test1Presenter;
import com.rotabug.client.view.View;

public class Test1View extends View implements Test1Presenter.Test1Display {
	private final Button testButton;

	public Test1View() {
		super();
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment( HasVerticalAlignment.ALIGN_MIDDLE );
		viewPanel.add(new Label("Test 1 view"));
		viewPanel.add( hp );
		testButton = new Button("Test 2");
		hp.add(testButton);
	}

	public HasClickHandlers getTestButton() {
		return testButton;
	}

}
