package com.rotabug.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.rotabug.client.AppController;
import com.rotabug.client.ViewBox;
import com.rotabug.client.event.RotabugEvent;
import com.rotabug.client.presenter.AlertPresenter;
import com.rotabug.client.presenter.Presenter;
import com.rotabug.client.view.View;

public class AlertView extends View implements AlertPresenter.AlertDisplay {
	public static final String COUNT_TOKEN = ">>COUNT<<";

	private final Button okButton;
	private final HTML html;
	private Timer alertTimer = null;
	protected int timeout = 0;
	protected int count;
	private Timer countTimer = null;
	private int delta_time = 0;
	private String text = null;

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
	}

	public void setText(String text) {
		this.text = text;
		format();
	}

	public HasClickHandlers getOKButton() {
		return okButton;
	}

	public void setTimeout(int timeout) {
		setTimeout(timeout, 0.0);
	}

	public void setTimeout(int timeout, double delta) {
		this.timeout = timeout;
		if (timeout > 0) {
			delta_time = (int) delta * 1000;
			if (delta_time > 0) {
				count = timeout;
				format();

			} else {
				delta_time = 0;
			}
		} else {
			delta_time = 0;
		}
	}

	protected void format() {
		String msg;
		if (delta_time > 0) {
			msg = text.replaceAll(COUNT_TOKEN, Integer.toString(count));
		} else {
			msg = text;
		}
		html.setHTML(msg);
	}

	public void onShow(ViewBox container) {
		if (timeout > 0) {
			final ViewBox cont = container;
			alertTimer = new Timer() {
				public void run() {
					AppController.eventBus.fireEvent(new RotabugEvent(
							Presenter.CLOSE, cont, false));
				}
			};
			alertTimer.schedule(timeout * 1000);

			if (delta_time > 0) {
				countTimer = new Timer() {
					public void run() {
						count--;
						format();
						if (countTimer != null)
							countTimer.schedule(delta_time);
					}
				};
				countTimer.schedule(delta_time);
			}
		}
	}

	public void onClose(ViewBox container) {
		if (alertTimer != null) {
			alertTimer.cancel();
			alertTimer = null;
		}
		if (countTimer != null) {
			countTimer.cancel();
			countTimer = null;
		}
	}

}
