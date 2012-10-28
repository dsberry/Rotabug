package com.rotabug.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface RotabugEventHandler extends EventHandler {
  void onRotabugEvent(RotabugEvent event);
}
