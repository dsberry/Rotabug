package com.rotabug.client.view;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.rotabug.client.EventLink;
import com.rotabug.client.Rotabug;
import com.rotabug.client.presenter.SignInPresenter;
import com.rotabug.client.presenter.Test1Presenter;

public class SignInView extends OKView implements SignInPresenter.SignInDisplay {

	public SignInView() {
		super(true);

		VerticalPanel vp = new VerticalPanel();

		HorizontalPanel hp1 = new HorizontalPanel();
		hp1.add(new Image("blue_arrow.png"));
		EventLink link1 = new EventLink("Create a new" + Rotabug.APP_NAME
				+ " account", Test1Presenter.PRESENT);
		link1.addStyleName("banner-link");
		link1.setTitle("Sign-in using a new " + Rotabug.APP_NAME + " account");
		hp1.add(link1);
		vp.add(hp1);

		HorizontalPanel hp2 = new HorizontalPanel();
		hp2.add(new Image("blue_arrow.png"));
		EventLink link2 = new EventLink("Login using...",
				Test1Presenter.PRESENT);
		link2.addStyleName("banner-link");
		link2.setTitle("Sign-in using an existing account");
		hp2.add(link2);
		vp.add(hp2);

		setBody(vp);
		setTitle("Sign-in");
	}

}