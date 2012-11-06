package com.rotabug.client;

import java.util.LinkedList;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.rotabug.client.event.RotabugEvent;
import com.rotabug.client.presenter.Display;
import com.rotabug.client.presenter.Presenter;

// An object that displays a series of "views" within a pop-up dialog box. The views are 
// queued, the next view being displayed when the previous view is closed.

public class UserDialog implements ViewBox {

	// Default time-out period in seconds, after which the dialog box is closed
	// automatically.
	public static final int TIMEOUT = 600;

	private DialogBox dbox;
	private VerticalPanel bodyPanel;
	private LinkedList<Display> requests;
	private Widget body;
	private boolean inUse;
	private Display display;
	private Timer timeoutTimer;
	private int timeout;

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
		timeout = TIMEOUT;
		timeoutTimer = new Timer() {
			public void run() {
				AppController.eventBus.fireEvent(new RotabugEvent(
						Presenter.CLOSE, UserDialog.this, false));
			}
		};
	}

	public void close() {
		dbox.hide();
		display.onClose(this);
		display = null;
		timeoutTimer.cancel();
		timeout = TIMEOUT;

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
		if( timeout > 0 ) timeoutTimer.schedule(timeout * 1000);
		display.onShow(this);
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public int getTimeout() {
		return timeout;
	}
}
