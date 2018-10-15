package com.framework.dropwizard.core.resources;

import com.codahale.metrics.health.HealthCheck;

/**
 * @author amanpreet.singh
 * @version 1.0.0
 * @since 01/09/15 5:48 PM.
 */
public class HealthCheckResource extends HealthCheck{
	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}
}
