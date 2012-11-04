package com.rotabug.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.rotabug.client.Rotabug;
import com.rotabug.client.presenter.HomePresenter;
import com.rotabug.client.presenter.Test2Presenter;
import com.rotabug.client.view.View;

public class HomeView extends View implements HomePresenter.HomeDisplay {
	private final Button testButton;
	private final TextBox normalText;

	public HomeView() {
		super();
		viewPanel.add(new Label("Home view"));
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		viewPanel.add(hp);
		testButton = new Button("Test 1");
		hp.add(testButton);
		Hyperlink lbl = new Hyperlink("Test2", Test2Presenter.PLACE);
		hp.add(lbl);
		Rotabug.addStyleAttribute(lbl, "padding-left:50px;");

		normalText = new TextBox();
		hp.add(normalText);
	}

	public HasClickHandlers getTestButton() {
		return testButton;
	}

}
