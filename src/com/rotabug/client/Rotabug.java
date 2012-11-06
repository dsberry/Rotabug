package com.rotabug.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Element;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.rotabug.client.presenter.HomePresenter;
import com.rotabug.client.presenter.SignInPresenter;

public class Rotabug implements EntryPoint {

	// The application name as it appears in text seen by the user.
	public static final String APP_NAME = "Rotabug";

	// The logo
	public static final String LOGO_PNG = "Rotabug.png";
	public static final String LOGO_WIDTH = "100px";
	public static final String LOGO_HEIGHT = "94px";

	// Indicates how verbose we should be. Zero is for normal user-level
	// verbosity. Higher values produce more verbosity.
	public static final int VERBOSITY_LEVEL = 1;

	// Main entry. This follows (roughly) the MVP architecture described at
	// https://developers.google.com/web-toolkit/articles/mvp-architecture
	public void onModuleLoad() {

		// Get the outer most panel which contains the headers, footers, etc,
		// plus the application GUI.
		RootPanel root = RootPanel.get(APP_NAME);

		// Create the header panel, add it to the root, and set its CSS ID to
		// "header".
		HorizontalPanel banner = new HorizontalPanel();
		root.add(banner);
		setID(banner, "banner");

		// Add the logo to the left end of the header, followed by headline
		// text.
		Image logo = new Image(LOGO_PNG);
		setID(logo, "banner-logo");
		banner.add(logo);
		banner.setCellWidth(logo, LOGO_WIDTH);

		HTML title = new HTML(
				"<big><b>Rotabug</b></big><br>rota creation made easy");
		setID(title, "banner-title");
		banner.add(title);

		// Add links at the right end of the banner for sign-up, login and home.
		VerticalPanel links = new VerticalPanel();
		EventLink signInLink = new EventLink("Sign in", SignInPresenter.PRESENT);
		signInLink.addStyleName("banner-link");
		signInLink.setTitle("Sign in using a new or existing account");
		links.add(signInLink);

		EventLink homeLink = new EventLink("Home", HomePresenter.PRESENT);
		homeLink.addStyleName("banner-link");
		homeLink.setTitle("Go to the " + APP_NAME + " home page");
		links.add(homeLink);

		banner.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		banner.add(links);

		// Create a panel for the application GUI, set its CSS ID and add it to
		// the root panel.
		VerticalPanel appUI = new VerticalPanel();
		setID(appUI, "appUI");
		root.add(appUI);

		// Create an object for displaying the pages of the main application
		// user interface. This encapsulates the above VerticalPanel.
		AppUI appui = new AppUI(appUI);

		// Create an object for managing a dialog box used for user interaction.
		UserDialog user = new UserDialog();

		// Create an object to communicate with the server
		ServerRequester server = new ServerRequester();

		// Create an object for sending events from one presenter to another.
		HandlerManager eventBus = new HandlerManager(null);

		// This the main class for creating and controlling the GUI.
		AppController appViewer = new AppController(server, user, eventBus,
				appui);

		// Display the main home page for the app.
		appViewer.go();

	}

	// Set the ID for a widget
	public static void setID(Widget w, String id) {
		DOM.setElementAttribute(w.getElement(), "id", id);
	}

	// Add an attribute setting to an element's style tag.
	public static void addStyleAttribute(Widget w, String attr) {
		Element el = w.getElement();
		String style = DOM.getElementAttribute(el, "style");
		style = style + attr;
		DOM.setElementAttribute(el, "style", style);
	}

}
