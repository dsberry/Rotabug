package com.rotabug.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class UserCountdown extends UserAlert {

	public static final String COUNT_TOKEN = ">>COUNT<<";
	public static final int DELTA_TIME = 1000;

	protected HTML body;
	protected int count;
	private Timer countTimer = null;

	public UserCountdown(String msg, int timeout) {
		super(msg, timeout);

	}

	public Widget getBody() {
		count = timeout;
		body = new HTML();
		formatBody();
		return body;
	}

	protected void formatBody() {
		body.setHTML(message.replaceAll(COUNT_TOKEN, Integer.toString(count)));
	}

	public void onShow() {
		super.onShow();

		countTimer = new Timer() {
			public void run() {
				count--;
				formatBody();
				countTimer.schedule(DELTA_TIME);

			}
		};

		countTimer.schedule(DELTA_TIME);

	}
}
