package com.rotabug.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.rotabug.client.ServerRequester;
import com.rotabug.client.ViewBox;
import com.rotabug.client.event.RotabugEvent;

// A presenter that display an HTML message with an "OK" button to close it. Optionally, it 
// can close automatically after a given time-out period.
public class AlertPresenter extends Presenter {

	// A unique name for the page of the application's user interface that is
	// presented by this presenter.
	public static final String PLACE = "alert";

	protected AlertDisplay display;

	public interface AlertDisplay extends Display {
		public static final String COUNT_TOKEN = ">>COUNT<<";
		HasClickHandlers getOKButton();
		void setText(String text);
	}

	public AlertPresenter(ServerRequester server, ViewBox user,
			HandlerManager eventBus, Display view) {
		super(server, user, eventBus, view);
		this.display = (AlertDisplay) super.display;
	}

	// Set the text to be displayed in the view. The supplied string is treated
	// as HTML.
	public void setText(String text) {
		display.setText(text);
	}

	// Store the call-backs in the supplied display/view.
	public void bind() {

		// When the "OK" button is clicked, fire a CLOSE event to tell the
		// ViewBox to close the view.
		display.getOKButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new RotabugEvent(Presenter.CLOSE, container,
						true));
			}
		});
	}
}
