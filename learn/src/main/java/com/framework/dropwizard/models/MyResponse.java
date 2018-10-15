package com.framework.dropwizard.models;

/**
 * @author amanpreet.singh
 * @version 1.0.0
 * @since 01/09/15 5:41 PM.
 */
public class MyResponse {
	private String status;
	private String message;

	public MyResponse(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
