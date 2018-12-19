package com.framework.dropwizard.core;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import com.framework.dropwizard.core.resources.HealthCheckResource;
import com.framework.dropwizard.core.resources.ProductServiceResource;
import com.framework.dropwizard.core.util.DropwizardModule;
import com.framework.dropwizard.models.StartupConfig;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.util.injection.FunInjector;
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
		final Injector injector = FunInjector.Get().createChildInjector(new DropwizardModule(startupConfig));

		environment.jersey().register(injector.getInstance(ProductServiceResource.class));
		environment.healthChecks().register("healthCheck",injector.getInstance(HealthCheckResource.class));
	}
}
