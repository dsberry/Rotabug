package com.rotabug.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.rotabug.client.event.RotabugEvent;
import com.rotabug.client.event.RotabugEventHandler;
import com.rotabug.client.event.RotabugEventType;
import com.rotabug.client.presenter.HomePresenter;
import com.rotabug.client.presenter.Presenter;
import com.rotabug.client.presenter.SignInPresenter;
import com.rotabug.client.presenter.Test1Presenter;
import com.rotabug.client.presenter.Test2Presenter;

public class AppController implements ValueChangeHandler<String> {

	public static ServerRequester server = null;
	public static UserRequester user = null;
	public static HandlerManager eventBus = null;
	public static UserDialog userDialog = null;

	// The container in which to display the application's user interface
	private HasWidgets appUI;

	@SuppressWarnings("static-access")
	public AppController(ServerRequester server, UserRequester user,
			HandlerManager eventBus) {
		if (this.server == null)
			this.server = server;
		if (this.user == null)
			this.user = user;
		if (this.eventBus == null)
			this.eventBus = eventBus;
		if (this.userDialog == null)
			this.userDialog = new UserDialog();
		bind();
	}

	public void go(final HasWidgets appUI) {
		this.appUI = appUI;
		if ("".equals(History.getToken())) {
			History.newItem(HomePresenter.PLACE);
		} else {
			History.fireCurrentHistoryState();
		}
	}

	// Called when a history event occurs (e.g. the "back" button)
	public void onValueChange(ValueChangeEvent<String> event) {
		newView(event.getValue(),Rotabug.APPUI);
	}

	private void bind() {
		History.addValueChangeHandler(this);

		// A handler for all types of RotabugEvents. These are fired by a
		// presenter. Each presenter can define one or more instances of the
		// RotabugEventType class to act as flags for different types of rotabug
		// event.
		eventBus.addHandler(RotabugEvent.TYPE, new RotabugEventHandler() {
			public void onRotabugEvent(RotabugEvent event) {
				RotabugEventType type = event.getType();
				if( type == Presenter.CLOSE ){
					int target = event.getTarget();
					if( target == Rotabug.APPUI ) {
						History.back();
					} else if( target == Rotabug.DIALOG ) {
						userDialog.next();
					} else {
						UserRequester.displayError(0, "Unknown Rotabug target.");
					}
				} else if (type == Test1Presenter.PRESENT) {
					History.newItem(Test1Presenter.PLACE);
				} else if (type == Test2Presenter.PRESENT) {
					History.newItem(Test2Presenter.PLACE);
				} else if (type == SignInPresenter.PRESENT) {
					History.newItem(SignInPresenter.PLACE);
				} else {
					UserRequester.displayError(0, "Unknown Rotabug event.");

				}
			}
		});
	}

	// Display a new page of the application's user interface.
	public void newView(String token, int target) {
		if (token != null) {
			HasWidgets widget = null;
			if( target == Rotabug.APPUI ) {
				widget = appUI;
			} else if( target == Rotabug.DIALOG ){
				widget = userDialog;
			}
			Presenter presenter = Presenter.byPlace(token);
			if (presenter != null)
				presenter.go(widget,target);
		}

	}
}
