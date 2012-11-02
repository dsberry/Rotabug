package com.rotabug.client;

import java.util.Iterator;
import java.util.LinkedList;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserDialog implements HasWidgets {
	private static UserDialog instance = null;
	private DialogBox dbox;
	private VerticalPanel bodyPanel;
	private LinkedList<Widget> requests;
	private Widget body;
	private boolean inUse;

	private UserDialog() {
		requests = new LinkedList<UserRequest>();
		dbox = new DialogBox();
		dbox.setGlassEnabled(true);
		dbox.setAnimationEnabled(true);
		bodyPanel = new VerticalPanel();
		bodyPanel.setSpacing(4);
		body = new Label("");
		bodyPanel.add(body);
		dbox.setWidget(bodyPanel);
		inUse = false;
	}

	public static final UserDialog getInstance() {
		if (instance == null)
			instance = new UserDialog();
		return instance;
	}

	public void next() {
		bodyPanel.clear();

	}

	// This method adds a widget to the queue of widgets to be displayed - it
	// does not add the widget to the container itself.
	public void add(Widget w) {
		bodyPanel.add(w);
	}

	// Clearing is performed just before the next widget is displayed.
	public void clear() {
	}

	public Iterator<Widget> iterator() {
		return bodyPanel.iterator();
	}

	// Not sure when, or if, it is appropriate to remove  awidget from the User
	public boolean remove(Widget w) {
	}
}
