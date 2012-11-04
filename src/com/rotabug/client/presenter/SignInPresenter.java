package com.rotabug.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.rotabug.client.ServerRequester;
import com.rotabug.client.ViewBox;
import com.rotabug.client.event.RotabugEventType;

public class SignInPresenter extends Presenter {

		// A unique name for the page of the application's user interface that is
		// presented by this presenter.
		public static final String PLACE = "login";
		
		// Define app. events that are services by this presenter.
		public static final RotabugEventType PRESENT = new RotabugEventType();

		// This interface defines the functionality required of the corresponding View.
		protected LoginDisplay display;
		public interface LoginDisplay extends Display {

		}

		public SignInPresenter(ServerRequester server, ViewBox user,
				HandlerManager eventBus, Display view) {
			super(server, user, eventBus, view);
			this.display = (LoginDisplay) super.display;
		}

		// Store the call-backs in the supplied display/view.
		public void bind() {

		}

	}
