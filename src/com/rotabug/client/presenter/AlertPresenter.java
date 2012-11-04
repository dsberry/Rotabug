package com.rotabug.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.rotabug.client.ServerRequester;
import com.rotabug.client.ViewBox;
import com.rotabug.client.event.RotabugEvent;

public class AlertPresenter extends Presenter {

	// A unique name for the page of the application's user interface that is
	// presented by this presenter.
	public static final String PLACE = "alert";

	protected AlertDisplay display;

	public interface AlertDisplay extends Display {
		HasClickHandlers getOKButton();
		void setText(String text);
		void setTimeout(int timeout);
		void setTimeout(int timeout, double delta);
	}

	public AlertPresenter(ServerRequester server, ViewBox user,
			HandlerManager eventBus, Display view) {
		super(server, user, eventBus, view);
		this.display = (AlertDisplay) super.display;
	}

	public void setText(String text) {
		display.setText(text);
	}

	// Store the call-backs in the supplied display/view.
	public void bind() {
		display.getOKButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new RotabugEvent(Presenter.CLOSE, container,
						true));
			}
		});
	}
	public void setTimeout(int timeout) {
		display.setTimeout(timeout);
	}

	public void setTimeout(int timeout, double delta) {
		display.setTimeout(timeout,delta);
	}
}
