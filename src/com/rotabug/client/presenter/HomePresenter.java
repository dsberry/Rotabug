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

public class HomePresenter extends Presenter {
	public static final String PLACE = "home";
	public static final RotabugEventType PRESENT = new RotabugEventType();

	protected HomeDisplay display;

	public interface HomeDisplay extends Display {
		HasClickHandlers getTestButton();
	}

	public HomePresenter(ServerRequester server, ViewBox user,
			HandlerManager eventBus, HomeDisplay view) {
		super(server, user, eventBus, view);
		this.display = (HomeDisplay) super.display;
	}

	// Store the call-backs in the supplied display/view.
	public void bind() {
		HasClickHandlers tb = display.getTestButton();
		tb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new RotabugEvent(Test1Presenter.PRESENT,
						container, true));
				server.submitRequest(new ServerRequest(20) {
					public void onSuccess(String result) {
						AppController.displayMessage(0, "TEST: " + result);
					}
				});
			}
		});
	}
}
