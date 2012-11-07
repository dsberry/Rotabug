package com.rotabug.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.rotabug.client.ServerRequester;
import com.rotabug.client.ViewBox;
import com.rotabug.client.event.RotabugEventType;

public class SignInPresenter extends OKPresenter {

		// A unique name for the page of the application's user interface that is
		// presented by this presenter.
		public static final String PLACE = "signin";
		
		// Define app. events that are services by this presenter.
		public static final RotabugEventType PRESENT = new RotabugEventType();

		// This interface defines the functionality required of the corresponding View.
		protected SignInDisplay display;
		public interface SignInDisplay extends OKDisplay {

		}

		public SignInPresenter(ServerRequester server, ViewBox user,
				HandlerManager eventBus, Display view) {
			super(server, user, eventBus, view);
			this.display = (SignInDisplay) super.display;
		}

		// Store the call-backs in the supplied display/view.
		public void bind() {
			super.bind();
		}

	}
