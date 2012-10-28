package com.rotabug.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserAlert implements UserRequest {

	protected String message;

	private Timer alertTimer = null;
	protected int timeout = 0;

	public UserAlert() {
		this.message = "";
	}

	public UserAlert(String message) {
		this.message = message;
	}

	public UserAlert(String message, int timeout) {
		this.message = message;
		setTimeout(timeout);
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getTitle() {
		return "message";
	}

	public Widget getBody() {
		return new HTML(message);
	}

	public void addButtons(HorizontalPanel panel) {

		Button okButton = new Button("OK", new ClickHandler() {
			public void onClick(ClickEvent event) {
				AppController.user.endRequest(true);
			}
		});
		panel.add(okButton);
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			panel.setCellHorizontalAlignment(okButton,
					HasHorizontalAlignment.ALIGN_LEFT);

		} else {
			panel.setCellHorizontalAlignment(okButton,
					HasHorizontalAlignment.ALIGN_RIGHT);
		}

	}

	public void onShow() {
		if (timeout > 0) {
			alertTimer = new Timer() {
				public void run() {
					AppController.user.endRequest(false);
				}
			};

			alertTimer.schedule(timeout * 1000);
		}
	}
}
