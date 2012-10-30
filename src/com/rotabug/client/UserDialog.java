package com.rotabug.client;

import java.util.Iterator;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserDialog implements HasWidgets {
	VerticalPanel vp;

	public void next() {

	}

	public void add(Widget w) {
		vp.add(w);
	}

	public void clear() {
		vp.clear();
	}

	public Iterator<Widget> iterator() {
		return vp.iterator();
	}

	public boolean remove(Widget w) {
		return vp.remove(w);
	}
}
