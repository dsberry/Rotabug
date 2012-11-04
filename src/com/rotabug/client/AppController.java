package com.rotabug.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.rotabug.client.event.RotabugEvent;
import com.rotabug.client.event.RotabugEventHandler;
import com.rotabug.client.event.RotabugEventType;
import com.rotabug.client.presenter.AlertPresenter;
import com.rotabug.client.presenter.HomePresenter;
import com.rotabug.client.presenter.Presenter;
import com.rotabug.client.presenter.SignInPresenter;
import com.rotabug.client.presenter.Test1Presenter;
import com.rotabug.client.presenter.Test2Presenter;

public class AppController implements ValueChangeHandler<String> {

	public static ServerRequester server = null;
	public static HandlerManager eventBus = null;
	public static ViewBox user = null;
	public static ViewBox appUI = null;

	@SuppressWarnings("static-access")
	public AppController(ServerRequester server, ViewBox user,
			HandlerManager eventBus, ViewBox appui) {
		if (this.server == null)
			this.server = server;
		if (this.eventBus == null)
			this.eventBus = eventBus;
		if (this.user == null)
			this.user = user;
		if (this.appUI == null)
			this.appUI = appui;
		bind();
	}

	public void go() {
		if ("".equals(History.getToken())) {
			History.newItem(HomePresenter.PLACE);
		} else {
			History.fireCurrentHistoryState();
		}
	}

	// Called when a history event occurs (e.g. the "back" button)
	public void onValueChange(ValueChangeEvent<String> event) {
		newView(event.getValue(), appUI);
	}

	private void bind() {
		History.addValueChangeHandler(this);

		// A handler for all types of RotabugEvents. These are fired by a
		// presenter. Each presenter can define one or more instances of the
		// RotabugEventType class to act as flags for different types of rotabug
		// event.
		eventBus.addHandler(RotabugEvent.TYPE, new RotabugEventHandler() {
			public void onRotabugEvent(RotabugEvent event) {
				String place = null;

				RotabugEventType type = event.getType();
				ViewBox container = event.getContainer();
				if (container == null)
					container = appUI;

				if (type == Presenter.CLOSE) {
					container.close();
				} else if (type == Test1Presenter.PRESENT) {
					place = Test1Presenter.PLACE;
				} else if (type == Test2Presenter.PRESENT) {
					place = Test2Presenter.PLACE;
				} else if (type == SignInPresenter.PRESENT) {
					place = SignInPresenter.PLACE;
				} else {
					displayError("Unknown Rotabug event.");
				}

				if (place != null) {
					if (container == appUI) {
						History.newItem(place);
					} else {
						newView(place, container);
					}
				}

				if (event.getActivity())
					AppController.server.flagActivity();
			}
		});
	}

	// Display a new page of the application's user interface.
	public void newView(String token, ViewBox target) {
		if (token != null) {
			Presenter presenter = Presenter.byPlace(token);
			if (presenter != null)
				presenter.go(target);
		}
	}

	public static void displayMessage(int level, String text) {
		if (Rotabug.DEBUG_LEVEL >= level) {
			AlertPresenter presenter = (AlertPresenter) Presenter
					.byPlace(AlertPresenter.PLACE);
			presenter.setText(text);
			presenter.setTitle("Alert");
			presenter.go(user);
		}
	}

	public static void displayError(String text) {
		AlertPresenter presenter = (AlertPresenter) Presenter
				.byPlace(AlertPresenter.PLACE);
		presenter.setText(text);
		presenter.setTitle("Error");
		presenter.go(user);

	}
}
