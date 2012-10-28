package com.rotabug.client;

import java.util.LinkedList;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserRequester {
	private static UserRequester instance = null;
	private DialogBox dbox;
	private VerticalPanel bodyPanel;
	private HorizontalPanel buttonPanel;
	private LinkedList<UserRequest> requests;
	private Widget body;
	private boolean inUse;

	private UserRequester() {
		requests = new LinkedList<UserRequest>();
		dbox = new DialogBox();
		dbox.setGlassEnabled(true);
		dbox.setAnimationEnabled(true);
		bodyPanel = new VerticalPanel();
		bodyPanel.setSpacing(4);
		body = new Label("");
		bodyPanel.add(body);
		buttonPanel = new HorizontalPanel();
		buttonPanel.setSpacing(4);
		bodyPanel.add(buttonPanel);
		dbox.setWidget(bodyPanel);
		inUse = false;
	}

	public static final UserRequester getInstance() {
		if (instance == null)
			instance = new UserRequester();
		return instance;
	}

	public void pushRequest(UserRequest request) {
		if (!inUse) {
			showRequest(request);
		} else {
			requests.add(request);
		}
	}

	private void showRequest(UserRequest request) {
		inUse = true;
		dbox.setText(Rotabug.APP_NAME + ": " + request.getTitle());
		bodyPanel.remove(body);
		body = request.getBody();
		bodyPanel.insert(body, 0);
		buttonPanel.clear();
		request.addButtons(buttonPanel);
		request.onShow();
		dbox.center();
		dbox.show();
	}

	public void endRequest(boolean activity) {
		if (activity)
			AppController.server.flagActivity();
		dbox.hide();
		if (requests.isEmpty()) {
			inUse = false;
		} else {
			showRequest(requests.removeFirst());
		}
	}

	// Display an error message.
	public static void displayError(int level, String error) {
		if (level <= Rotabug.DEBUG_LEVEL)
			instance.pushRequest(new UserError(error));
	}

	// Display an error message with timeout.
	public static void displayError(int level, String error, int timeout) {
		if (level <= Rotabug.DEBUG_LEVEL)
			instance.pushRequest(new UserError(error, timeout));
	}

	// Display an informational message.
	public static void displayMessage(int level, String msg) {
		if (level <= Rotabug.DEBUG_LEVEL)
			instance.pushRequest(new UserAlert(msg));
	}

	// Display an informational message with timeout.
	public static void displayMessage(int level, String msg, int timeout) {
		if (level <= Rotabug.DEBUG_LEVEL)
			instance.pushRequest(new UserAlert(msg, timeout));
	}

}
