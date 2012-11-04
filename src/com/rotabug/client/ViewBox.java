package com.rotabug.client;

import com.rotabug.client.presenter.Display;

// An object within which a "view" can be displayed.
public interface ViewBox {

	// Cause the view specified by "display" to be displayed within the ViewBox
	// container.
	void put(Display display);

	// Close the view currently displayed within the ViewBox container. How
	// this is implemented depends on the nature of the ViewBox (e.g. it may
	// cause a pop-up to dissappear, or it may cause a History "back" event to
	// be fired).
	void close();
}
