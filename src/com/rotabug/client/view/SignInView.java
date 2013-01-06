package com.rotabug.client.view;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.rotabug.client.EventLink;
import com.rotabug.client.Rotabug;
import com.rotabug.client.presenter.SignInPresenter;
import com.rotabug.client.presenter.Test1Presenter;

public class SignInView extends OKView implements SignInPresenter.SignInDisplay {

	public SignInView() {
		super(true);

		VerticalPanel vp = new VerticalPanel();
		vp.add(new Label("Click one of the following logos below to log into "+Rotabug.APP_NAME+" using your account with the selected site."));
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(new Image("google_logo.jpg"));
		buttonPanel.add(new Image("yahoo_logo.jpg"));
		buttonPanel.add(new Image("facebook_logo.jpg"));
		buttonPanel.add(new Image("twitter_logo.jpg"));
		vp.add( buttonPanel );

		setBody(vp);
		setTitle("Sign-in");
	}

}