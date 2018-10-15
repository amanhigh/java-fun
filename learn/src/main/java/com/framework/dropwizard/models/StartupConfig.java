package com.framework.dropwizard.models;

import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author amanpreet.singh
 * @version 1.0.0
 * @since 01/09/15 5:31 PM.
 */
public class StartupConfig extends Configuration {
	@NotEmpty
	private String name;
	@NotEmpty
	private String owner;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
