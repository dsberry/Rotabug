package com.rotabug.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.rotabug.client.AppController;
import com.rotabug.client.ServerRequest;
import com.rotabug.client.ServerRequester;
import com.rotabug.client.ViewBox;
import com.rotabug.client.event.RotabugEvent;
import com.rotabug.client.event.RotabugEventType;
import com.rotabug.client.presenter.AlertPresenter.AlertDisplay;

public class Test2Presenter extends Presenter {
	public static final String PLACE = "test2";
	public static final RotabugEventType PRESENT = new RotabugEventType();
	protected Test2Display display;

	public interface Test2Display extends Display {
		HasClickHandlers getTestButton();
	}

	public Test2Presenter(ServerRequester server, ViewBox user,
			HandlerManager eventBus, Display view) {
		super(server, user, eventBus, view);
		this.display = (Test2Display) super.display;
	}

	// Store the call-backs in the supplied display/view.
	public void bind() {
		display.getTestButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new RotabugEvent(Test1Presenter.PRESENT,
						container, true));
				server.submitRequest(new ServerRequest(20) {
					public void onSuccess(String result) {
						AppController.displayMessage(0, "Test 2: " + result+AlertDisplay.COUNT_TOKEN );
					}
				});
			}
		});
	}

}
