package com.framework.dropwizard.models;

/**
 * @author amanpreet.singh
 * @version 1.0.0
 * @since 01/09/15 5:37 PM.
 */
public class Request {
	private String id;
	private String ask;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAsk() {
		return ask;
	}

	public void setAsk(String ask) {
		this.ask = ask;
	}
}
