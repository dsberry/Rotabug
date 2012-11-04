package com.rotabug.client;

import com.rotabug.client.presenter.Display;

// An object that displays a view
public interface ViewBox {
	void put(Display display);
	void close();
}
