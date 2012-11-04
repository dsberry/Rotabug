package com.rotabug.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.rotabug.client.ViewBox;

public class RotabugEvent extends GwtEvent<RotabugEventHandler> {
	public static Type<RotabugEventHandler> TYPE = new Type<RotabugEventHandler>();
	protected RotabugEventType type;
	protected ViewBox container;
	protected boolean activity;
	
	public RotabugEvent(RotabugEventType type, ViewBox container, boolean activity) {
		super();
		this.type = type;
		this.container = container;
		this.activity = activity;
	}

	public Type<RotabugEventHandler> getAssociatedType() {
		return TYPE;
	}

	protected void dispatch(RotabugEventHandler handler) {
		handler.onRotabugEvent(this);
	}

	public RotabugEventType getType() {
		return type;
	}

	// null may be returned, indicating "AppUI".
	public ViewBox getContainer() {
		return container;
	}

	public boolean getActivity() {
		return activity;
	}
}
