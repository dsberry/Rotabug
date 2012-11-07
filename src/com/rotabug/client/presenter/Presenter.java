package com.rotabug.client.presenter;

import java.util.HashMap;

import com.google.gwt.event.shared.HandlerManager;
import com.rotabug.client.AppController;
import com.rotabug.client.ServerRequester;
import com.rotabug.client.ViewBox;
import com.rotabug.client.event.RotabugEventType;
import com.rotabug.client.view.AlertView;
import com.rotabug.client.view.HomeView;
import com.rotabug.client.view.Test1View;
import com.rotabug.client.view.Test2View;
import com.rotabug.client.view.SignInView;

public abstract class Presenter {

	protected ServerRequester server;
	protected ViewBox user;
	protected ViewBox container;
	protected HandlerManager eventBus;
	protected Display display;
	boolean bound = false;

	// Define app. events that are applicable to all classes of Presenter.
	public static final RotabugEventType CLOSE = new RotabugEventType();

	// Static instances of all known subclasses of Presenter
	private static HashMap<String, Presenter> singletons = new HashMap<String, Presenter>();

	protected Presenter(ServerRequester server, ViewBox user,
			HandlerManager eventBus, Display view) {
		this.server = server;
		this.user = user;
		this.eventBus = eventBus;
		this.container = null;
		setDisplay(view);
	}

	// Change the view associated with this presenter.
	public void setDisplay(Display view) {
		this.display = view;
		this.bound = false;
	}

	public void setTitle(String title) {
		display.setTitle(title);
	}

	// Set up bindings between this presenter and the controls in the associated
	// view.
	protected abstract void bind();

	// Display the view associated with this Presenter in a given ViewBox
	public void go(final ViewBox container) {
		if (!bound) {
			bind();
			bound = true;
		}
		container.put(display);
		this.container = container;
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
			} else if (place.equals(AlertPresenter.PLACE)) {
				result = new AlertPresenter(AppController.server,
						AppController.user, AppController.eventBus,
						new AlertView());
			} else {
				AppController.displayError( "unknown interface component '"
						+ place + "'.");
			}
		}
		return result;
	}
}