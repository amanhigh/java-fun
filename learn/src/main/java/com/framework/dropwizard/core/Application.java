package com.framework.dropwizard.core;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import com.framework.dropwizard.core.resources.HealthCheckResource;
import com.framework.dropwizard.core.resources.ProductServiceResource;
import com.framework.dropwizard.models.StartupConfig;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author amanpreet.singh
 * @version 1.0.0
 * @since 01/09/15 5:33 PM.
 */
public class Application extends io.dropwizard.Application<StartupConfig> {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		LOGGER.info("Before Run");
		new Application().run(args);
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		context.reset();
		ContextInitializer initializer = new ContextInitializer(context);
		initializer.autoConfig();
		LOGGER.info("After Run");
	}

	@Override
	public void run(StartupConfig startupConfig, Environment environment) {
		LOGGER.info("Starting Dropwizard Experiment");
		ProductServiceResource resource = new ProductServiceResource(startupConfig.getName(),startupConfig.getOwner());
		environment.jersey().register(resource);
		environment.healthChecks().register("healthCheck",new HealthCheckResource());
	}
}
