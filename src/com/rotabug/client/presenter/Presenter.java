package com.rotabug.client.presenter;

import java.util.HashMap;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.rotabug.client.AppController;
import com.rotabug.client.ServerRequester;
import com.rotabug.client.UserRequester;
import com.rotabug.client.view.HomeView;
import com.rotabug.client.view.Test1View;
import com.rotabug.client.view.Test2View;
import com.rotabug.client.view.SignInView;

public abstract class Presenter {

	protected ServerRequester server;
	protected UserRequester user;
	protected HandlerManager eventBus;
	protected Display display;
	boolean bound = false;

	// Static instances of all known subclasses of Presenter
	private static HashMap<String, Presenter> singletons = new HashMap<String, Presenter>();

	protected Presenter(ServerRequester server, UserRequester user,
			HandlerManager eventBus, Display view) {
		this.server = server;
		this.user = user;
		this.eventBus = eventBus;
		setDisplay(view);
	}

	// Change the view associated with this presenter.
	public void setDisplay(Display view) {
		this.display = view;
		this.bound = false;
	}

	// Set up bindings between this presenter and the controls in the associated view.
	public abstract void bind();

	// Display the view associated with this Presenter
	public void go(final HasWidgets container) {
		if (!bound) {
			bind();
			bound = true;
		}
		container.clear();
		container.add(display.asWidget());
	}

	// Returns the Presenter that manages the view specified by "place".
	public static Presenter byPlace(String place) {
		Presenter result = singletons.get(place);
		if (result == null) {
			if (place.equals(HomePresenter.PLACE)) {
				result = new HomePresenter(AppController.server,
						AppController.user, AppController.eventBus,
						new HomeView());
			} else if (place.equals(Test1Presenter.PLACE)) {
				result = new Test1Presenter(AppController.server,
						AppController.user, AppController.eventBus,
						new Test1View());
			} else if (place.equals(Test2Presenter.PLACE)) {
				result = new Test2Presenter(AppController.server,
						AppController.user, AppController.eventBus,
						new Test2View());
			} else if (place.equals(SignInPresenter.PLACE)) {
				result = new SignInPresenter(AppController.server,
						AppController.user, AppController.eventBus,
						new SignInView());
			} else {
				UserRequester.displayError(0, "unknown interface component '"
						+ place + "'.");
			}
		}
		return result;
	}
}