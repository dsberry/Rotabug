package com.rotabug.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

public class ServerRequest implements RequestCallback {
	protected int status;
	protected ServerRequest nextRequest = null;
	int opcode;

	public ServerRequest(int opcode) {
		this.opcode = opcode;
		this.status = 0;
		this.nextRequest = null;
	}

	public int getOpcode() {
		return opcode;
	}

	public void chainRequest(ServerRequest request) {
		nextRequest = request;
	}

	// Called if a response is received from the server. If the response
	// indicates success, call the RotabugRequest's "onSuccess" method, then
	// submit any chained request to the server. If the response indicates
	// failure, give a suitable error message.
	public void onResponseReceived(Request request, Response response) {
		ServerRequest next = null;
		status = response.getStatusCode();
		if (status == 200) {
			onSuccess(response.getText());
			next = nextRequest;

		} else if (status == 401) {
			AppController
					.displayError("Your session has been inactive for too "
							+ "long. Try reloading the page.");

		} else {
			AppController.displayError("HTTP error " + Integer.toString(status)
					+ " occurred: " + response.getStatusText());
		}
		AppController.server.requestCompleted(next);

	}

	// Called if no response was received from the server.
	public void onError(Request request, Throwable exception) {
		AppController.displayError("Operation could not be completed: "
				+ exception.getMessage());
		AppController.server.requestCompleted(null);

	}

	// Any data that the server will require to perform the request should be
	// added to the StringBuffer by this method. It should use the other
	// addToPOST methods to add each primitive value to the StringBuffer.
	public void addToPOST(StringBuffer sb) {
	}

	// This function is invoked if the request succeeds. The text returned by
	// the server is supplied in "result".
	public void onSuccess(String result) {
	}

	// Add an integer value to the POST request associating it with a specified
	// key.
	public static StringBuffer addToPOST(StringBuffer sb, String key, int value) {
		return addToPOST(sb, key, Integer.toString(value));
	}

	// Add a String value to the POST request associating it with a specified
	// key.
	public static StringBuffer addToPOST(StringBuffer sb, String key,
			String value) {
		StringBuffer result;
		if (sb == null) {
			result = new StringBuffer();
		} else {
			result = sb;
			result.append("&");
		}
		result.append(URL.encodeQueryString(key));
		result.append("=");
		result.append(URL.encodeQueryString(value));
		return result;
	}

	public int getStatus() {
		return status;
	}
}
