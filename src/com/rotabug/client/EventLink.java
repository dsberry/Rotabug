package com.rotabug.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.rotabug.client.event.RotabugEvent;
import com.rotabug.client.event.RotabugEventType;

public class EventLink extends Anchor {
	protected RotabugEventType etype;
	protected ViewBox container;
	protected boolean activity;

	public EventLink(String text, RotabugEventType eventType) {
		this(text, eventType, null, true);
	}

	public EventLink(String text, RotabugEventType eventType,
			ViewBox cont, boolean act) {
		super(text);
		this.etype = eventType;
		this.container = cont;
		this.activity = act;
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				AppController.eventBus.fireEvent(new RotabugEvent(etype,
						container, activity));
			}
		});
	}
}
