package com.rotabug.client.view;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.rotabug.client.AppController;
import com.rotabug.client.ViewBox;
import com.rotabug.client.presenter.AlertPresenter;
import com.rotabug.client.presenter.AlertPresenter.AlertDisplay;

public class AlertView extends OKView implements AlertPresenter.AlertDisplay {
	private final HTML html;
	protected int count;
	private Timer countTimer = null;
	private String text = null;
	private boolean hasCount;

	public AlertView() {
		super(false);
		html = new HTML("");
		setBody(html);
		setTitle("Alert");
		hasCount = false;
	}

	public void setText(String text) {
		this.text = text;
		hasCount = (text.indexOf(AlertDisplay.COUNT_TOKEN) >= 0);
		count = AppController.user.getTimeout();
		format();
	}

	protected void format() {
		String msg;
		if (hasCount) {
			msg = text.replaceAll(AlertDisplay.COUNT_TOKEN,
					Integer.toString(count));
		} else {
			msg = text;
		}
		html.setHTML(msg);
	}

	public void onShow(ViewBox container) {
		if (hasCount) {
			count = AppController.user.getTimeout();
			countTimer = new Timer() {
				public void run() {
					count--;
					format();
					if (countTimer != null)
						countTimer.schedule(1000);
				}
			};
			countTimer.schedule(1000);
		}
	}

	public void onClose(ViewBox container) {
		if (countTimer != null) {
			countTimer.cancel();
			countTimer = null;
		}
	}
}
