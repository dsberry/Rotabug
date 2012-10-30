package com.rotabug.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.rotabug.client.Rotabug;

public class RotabugEvent extends GwtEvent<RotabugEventHandler> {
	public static Type<RotabugEventHandler> TYPE = new Type<RotabugEventHandler>();
	protected RotabugEventType type;
	protected int target;

	public RotabugEvent(RotabugEventType type) {
		this(type, Rotabug.APPUI);
	}

	public RotabugEvent(RotabugEventType type, int target) {
		super();
		this.type = type;
		this.target = target;
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

	public int getTarget() {
		return target;
	}
}
