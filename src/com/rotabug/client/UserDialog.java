package com.rotabug.client;

import java.util.LinkedList;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.rotabug.client.presenter.Display;

// An object that displays a series of "views" within a pop-up dialog box. The views are 
// queued, the next view being displayed when the previous view is closed.

public class UserDialog implements ViewBox {
	private DialogBox dbox;
	private VerticalPanel bodyPanel;
	private LinkedList<Display> requests;
	private Widget body;
	private boolean inUse;
	private Display display;

	public UserDialog() {
		requests = new LinkedList<Display>();
		dbox = new DialogBox();
		dbox.setGlassEnabled(true);
		dbox.setAnimationEnabled(true);
		bodyPanel = new VerticalPanel();
		bodyPanel.setSpacing(4);
		body = new Label("");
		bodyPanel.add(body);
		dbox.setWidget(bodyPanel);
		inUse = false;
		display = null;
	}

	public void close() {
		dbox.hide();
		display.onClose(this);
		display = null;
		if (requests.isEmpty()) {
			inUse = false;
		} else {
			show(requests.removeFirst());
		}
	}

	public void put(Display display) {
		if (!inUse) {
			show(display);
		} else {
			requests.add(display);
		}
	}

	private void show(Display display) {
		inUse = true;
		dbox.setText(Rotabug.APP_NAME + ": " + display.getTitle());
		bodyPanel.clear();
		bodyPanel.add(display.asWidget());
		dbox.center();
		dbox.show();
		this.display = display;
		display.onShow(this);
	}

}
