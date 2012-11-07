package com.rotabug.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.rotabug.client.ServerRequester;
import com.rotabug.client.ViewBox;
import com.rotabug.client.event.RotabugEvent;

public abstract class OKPresenter extends Presenter {

	public interface OKDisplay extends Display {
		HasClickHandlers getOKButton();
		HasClickHandlers getCancelButton();
	}

	protected OKDisplay display;

	protected OKPresenter(ServerRequester server, ViewBox user,
			HandlerManager eventBus, Display view) {
		super(server, user, eventBus, view);
		this.display = (OKDisplay) super.display;
	}

	// Store the call-backs in the supplied display/view.
	protected void bind() {
		bindOKButton();
		bindCancelButton();
	}

	// When the "OK" button is clicked, fire a CLOSE event to tell the
	// ViewBox to close the view.
	protected void bindOKButton() {
		display.getOKButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new RotabugEvent(Presenter.CLOSE, container,
						true));
			}
		});
	}

	// When the "Cancel" button is clicked, fire a CLOSE event to tell the
	// ViewBox to close the view.
	protected void bindCancelButton() {
		HasClickHandlers cancelButton = display.getCancelButton();
		if (cancelButton != null) {
			cancelButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					eventBus.fireEvent(new RotabugEvent(Presenter.CLOSE,
							container, true));
				}
			});
		}
	}

}
