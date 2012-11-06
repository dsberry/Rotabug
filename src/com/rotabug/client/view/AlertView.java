package com.rotabug.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.rotabug.client.AppController;
import com.rotabug.client.ViewBox;
import com.rotabug.client.presenter.AlertPresenter;
import com.rotabug.client.presenter.AlertPresenter.AlertDisplay;
import com.rotabug.client.view.View;

public class AlertView extends View implements AlertPresenter.AlertDisplay {
	private final Button okButton;
	private final HTML html;
	protected int count;
	private Timer countTimer = null;
	private String text = null;
	private boolean hasCount;

	public AlertView() {
		super();
		html = new HTML("");
		okButton = new Button("OK");
		VerticalPanel vp = new VerticalPanel();
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		viewPanel.add(html);
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		vp.add(okButton);
		viewPanel.add(vp);
		setTitle("Alert");
		hasCount = false;
	}

	public void setText(String text) {
		this.text = text;
		hasCount = (text.indexOf(AlertDisplay.COUNT_TOKEN) >= 0);
		count = AppController.user.getTimeout();
		format();
	}

	public HasClickHandlers getOKButton() {
		return okButton;
	}

	protected void format() {
		String msg;
		if (hasCount) {
			msg = text.replaceAll(AlertDisplay.COUNT_TOKEN, Integer.toString(count));
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
