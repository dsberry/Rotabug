package com.rotabug.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public interface UserRequest {
	public String getTitle();

	public Widget getBody();

	public void addButtons(HorizontalPanel panel);

	public void onShow();
}
