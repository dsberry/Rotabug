package com.rotabug.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.rotabug.client.ServerRequest;
import com.rotabug.client.ServerRequester;
import com.rotabug.client.UserRequester;
import com.rotabug.client.event.RotabugEvent;
import com.rotabug.client.event.RotabugEventType;

public class Test1Presenter extends Presenter {

	// A unique name for the page of the application's user interface that is
	// presented by this presenter.
	public static final String PLACE = "test1";
	
	//
	public static final RotabugEventType PRESENT = new RotabugEventType();

	protected Test1Display display;

	public interface Test1Display extends Display {
		HasClickHandlers getTestButton();
	}

	public Test1Presenter(ServerRequester server, UserRequester user,
			HandlerManager eventBus, Display view) {
		super(server, user, eventBus, view);
		this.display = (Test1Display) super.display;
	}

	// Store the call-backs in the supplied display/view.
	public void bind() {
		display.getTestButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new RotabugEvent(Test2Presenter.PRESENT));
				server.submitRequest(new ServerRequest(20) {
					public void onSuccess(String result) {
						UserRequester.displayMessage(0, "Test 1: " + result);
					}
				});
			}
		});
	}

}
