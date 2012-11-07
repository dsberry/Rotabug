package com.rotabug.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class OKView extends View {
	private final Button okButton;
	private final Button cancelButton;
	protected VerticalPanel vp;
	protected HorizontalPanel hp;
	private Widget oldBody;

	protected OKView(boolean hasCancelButton) {
		super();
		vp = new VerticalPanel();
		vp.setSpacing(4);
		oldBody = new Label();
		vp.add(oldBody);
		hp = new HorizontalPanel();
		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		hp.setSpacing(4);
		if (hasCancelButton) {
			cancelButton = new Button("Cancel");
			hp.add(cancelButton);
		} else {
			cancelButton = null;
		}
		okButton = new Button("OK");
		hp.add(okButton);
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		vp.add(hp);
		viewPanel.add(vp);
	}

	public HasClickHandlers getOKButton() {
		return okButton;
	}

	public HasClickHandlers getCancelButton() {
		return cancelButton;
	}

	protected void setBody(Widget body) {
		vp.remove(oldBody);
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		vp.insert(body, 0);
		oldBody = body;
	}
}
