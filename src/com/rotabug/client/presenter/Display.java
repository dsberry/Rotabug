package com.rotabug.client.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.rotabug.client.ViewBox;

public interface Display {
	Widget asWidget();
	void onShow(ViewBox container);
	void onClose(ViewBox container);
	String getTitle();
	void setTitle(String title);

}
