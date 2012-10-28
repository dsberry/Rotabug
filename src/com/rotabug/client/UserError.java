package com.rotabug.client;

public class UserError extends UserAlert {
	public UserError(String error) {
		super(error);
	}

	public UserError(String error, int timeout) {
		super(error, timeout);
	}

	public String getTitle() {
		return "error";
	}

}
