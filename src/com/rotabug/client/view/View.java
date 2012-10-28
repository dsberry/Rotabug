package com.rotabug.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class View extends Composite {
		protected final FlowPanel viewPanel;

		public View() {
			viewPanel = new FlowPanel();
			initWidget(viewPanel);
			viewPanel.setStyleName( "view" );
		}

		public Widget asWidget() {
			return this;
		}
}
