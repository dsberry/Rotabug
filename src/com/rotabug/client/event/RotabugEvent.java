package com.rotabug.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class RotabugEvent extends GwtEvent<RotabugEventHandler> {
	public static Type<RotabugEventHandler> TYPE = new Type<RotabugEventHandler>();
	protected RotabugEventType type;

	public RotabugEvent(RotabugEventType type) {
		super();
		this.type = type;
	}

	@Override
	public Type<RotabugEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RotabugEventHandler handler) {
		handler.onRotabugEvent(this);
	}

	public RotabugEventType getType() {
		return type;
	}
}
