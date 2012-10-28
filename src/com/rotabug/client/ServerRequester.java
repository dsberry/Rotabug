package com.rotabug.client;

import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

public class ServerRequester {

	// URL of a page saying that the rotabug session has timed out
	private static final String CLIENT_TIMEDOUT_URL = GWT.getHostPageBaseURL()
			+ "timeout.html";

	// The URL for the main PHP script that handles most call-backs to the
	// server.
	private static final String PHP_URL = GWT.getHostPageBaseURL()
			+ "rotabug.php";

	// The key used by the PHP script for the requested Opcode.
	private static final String OP_KEY = "OP";

	// The key used by the PHP script for the Request ID that combines the
	// ticket number and session id.
	private static final String RID_KEY = "RID";

	// Opcodes for the functions that can be performed by the main PHP script on
	// the server.
	public static final int REQUEST_SESSION_ID = 1;
	public static final int KILL_SESSION = 2;

	// Server time out period in seconds.
	private static final int SERVER_TIMEOUT = 60;

	// Spins for each character in a randomised string.
	private static final int NSPIN = 16;
	private static final int[] SPINS = { 5, 6, 11, 11, 3, 13, 5, 7, 13, 4, 10,
			8, 7, 4, 5, 12 };

	// Indicies of the characters within the session ID that form the secret
	// code for the session.
	private static final int NSECRET = 5;
	private static final int[] SECRETS = { 9, 3, 6, 1, 7 };

	// The secret code itself.
	private int sCode;

	// A constant unique string that identifies this session to the server
	public String sessionId = null;

	// A integer supplied to the server with each request, which increments by
	// one each time a request is made to the server. To enhance session
	// security, ticket numbers can only be used once. The server will not
	// honour a request if the session has already used the supplied ticket
	// number.
	public int nextTicket;

	// HAre we waiting for a reply to a previously issued server request?
	private boolean inUse = false;

	// A timer that goes off when the client has been inactive for TIMEOUT1
	// seconds. This is just a warning - the session is killed a further
	// TIMEOUT2 seconds after the warning is issued - if there has been no
	// client activity. The "warned" field indicates if the warning has been
	// issued.
	private Timer sessionTimer = null;
	private static final int CLIENT_TIMEOUT1 = 600;
	private static final int CLIENT_TIMEOUT2 = 60;
	private boolean warned = false;

	// A FIFO queue of request to send to the server.
	private LinkedList<ServerRequest> requests = new LinkedList<ServerRequest>();

	// Requests a new Session ID from the server, and then perform the next
	// request.
	private void getSessionID(ServerRequest nextRequest) {

		// Create a request for a session ID
		ServerRequest request = new ServerRequest(REQUEST_SESSION_ID) {
			public void onSuccess(String result) {
				sessionId = JSONBuffer.stringFromJSON(result);
				UserRequester.displayMessage(1, "Session ID: " + sessionId);

				StringBuffer a = new StringBuffer();
				for (int i = 0; i < NSECRET; i++) {
					a.append(sessionId.charAt(SECRETS[i]));
				}
				sCode = Integer.parseInt(a.toString(), 16);
				UserRequester.displayMessage(2, "Secret code: " + sCode);

				// Create a timer that will go off a fixed time after the last
				// session activity to warn the user that the session is about
				// to expire. If no subsequent session activity occurs within a
				// further fixed period, then kill the session.
				sessionTimer = new Timer() {
					public void run() {
						if (warned) {
							killSession();
						} else {
							warnSession();
						}
					}
				};

				sessionTimer.schedule(CLIENT_TIMEOUT1 * 1000);

			}
		};

		// Indicate that the supplied request should be performed once the
		// request for the session ID has completed.
		request.chainRequest(nextRequest);

		// Submit the requests to the server.
		doRequest(request);
	}

	// Make a request to the server, waiting until the server is idle before
	// sending the request to the server.
	public void submitRequest(ServerRequest request) {
		if (!inUse) {
			doRequest(request);
		} else {
			requests.add(request);
		}
	}

	// Make a request to the server. "opcode" is an integer that tells the
	// server what function is being requested of it, and "request" is an object
	// that knows how to package the data required to fulfill the request and
	// how to handle the information returned by the request.
	private void doRequest(ServerRequest request) {
		inUse = true;

		// If we do not yet have a session ID, and the purpose of the supplied
		// request is not to create a session ID, then we first get a session ID
		// then perform the supplied request.
		if (sessionId == null && request.getOpcode() != REQUEST_SESSION_ID) {
			getSessionID(request);

			// Otherwise, send the supplied request to the server.
		} else {

			// Indicate there has been activity in this session.
			flagActivity();

			// Build the string to be stored as the data within the POST
			// request. If we currently have a session ID, combine it with
			// the ticket number to form the "Request ID" included in the
			// POST data. Then add the supplied opcode, and finally ask the
			// "request" object to add any other required data.
			StringBuffer sb = null;
			if (sessionId != null) {
				sb = ServerRequest.addToPOST(sb, RID_KEY,
						spin2(Integer.toHexString(nextTicket), sessionId));
			} else if (request.getOpcode() != REQUEST_SESSION_ID) {
				UserRequester.displayError(0,
						"waiting for the server - please repeat in a few seconds");
				return;
			}
			sb = ServerRequest.addToPOST(sb, OP_KEY, request.getOpcode());
			request.addToPOST(sb);

			// Build the actual POST request, telling it to invoke the methods
			// of the supplied "request" object when the server returns its
			// answer.
			RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
					URL.encode(PHP_URL));
			builder.setHeader("Content-Type",
					"application/x-www-form-urlencoded");
			builder.setRequestData(sb.toString());
			builder.setCallback(request);
			builder.setTimeoutMillis(SERVER_TIMEOUT * 1000);

			// Send the above POST request to the server.
			try {
				builder.send();
			} catch (RequestException e) {
				UserRequester.displayError(0,
						"An error occurred sending a request to the server: "
								+ e.getMessage());
			}

			// If we have started a new session, reset the ticket number to 1.
			// Otherwise, if the server accepted the ticket number as valid,
			// then we need to increment the number of the next ticket to send.
			if (request.getOpcode() == REQUEST_SESSION_ID) {
				nextTicket = 1;
			} else if (request.getStatus() != 401) {
				nextTicket++;
			}
		}
	}

	/*
	 * A copy of the spin2 function in rotabug.php. Returns a randomised string
	 * of hexadecimal characters from which the two supplied strings (which
	 * should also contain only hexadecimal characters) can be recovered using
	 * the "unspin2" function in rotabug.php.
	 */
	private static String spin2(String s1, String s2) {
		StringBuffer a = new StringBuffer();
		StringBuffer result = new StringBuffer();
		int len1 = s1.length();
		if (len1 < 16)
			a.append('0');
		a.append(Integer.toHexString(len1));
		a.append(s1);
		a.append(s2);
		int la = a.length();
		int ispin = Random.nextInt(16);
		result.append(Integer.toHexString(ispin));
		for (int i = 0; i < la; i++) {
			result.append(Integer.toHexString((Integer.parseInt(
					a.substring(i, i + 1), 16) + SPINS[ispin]) % 16));
			if (++ispin == NSPIN)
				ispin = 0;
		}
		return result.toString();
	}

	// Flag session activity - this causes the remaining session lifetime to be
	// extended.
	public void flagActivity() {
		warned = false;
		if (sessionTimer != null)
			sessionTimer.schedule(CLIENT_TIMEOUT1 * 1000);
	}

	// Warn the user if an inactive session is about to be killed.
	public void warnSession() {
		warned = true;
		sessionTimer.schedule(CLIENT_TIMEOUT2 * 1000);
		AppController.user.pushRequest(new UserCountdown(
				"Your session will expire if there is no activity within "
						+ "the next " + UserCountdown.COUNT_TOKEN + " seconds",
				CLIENT_TIMEOUT2));
	}

	public void killSession() {
		doRequest(new ServerRequest(KILL_SESSION) {
			public void onSuccess(String result) {
				Window.Location.assign(CLIENT_TIMEDOUT_URL);
			}
		});
		Window.Location.assign(CLIENT_TIMEDOUT_URL);
	}

	public void requestCompleted(ServerRequest nextRequest) {
		if (nextRequest != null) {
			doRequest(nextRequest);
		} else if (requests.isEmpty()) {
			inUse = false;
		} else {
			doRequest(requests.removeFirst());
		}
	}

}
