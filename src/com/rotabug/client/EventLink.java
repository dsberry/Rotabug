package com.rotabug.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.rotabug.client.event.RotabugEvent;
import com.rotabug.client.event.RotabugEventType;

public class EventLink extends Anchor {
	protected RotabugEventType etype;

	public EventLink(String text, RotabugEventType eventType) {
		super(text);
		etype = eventType;
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				AppController.eventBus.fireEvent(new RotabugEvent(etype));
			}
		});
	}

}
