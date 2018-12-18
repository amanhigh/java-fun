package com.framework.dropwizard.core.util;

import com.framework.dropwizard.core.resources.ProductServiceResource;
import com.framework.dropwizard.models.StartupConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * @author amanpreet.singh
 * @version 1.0.0
 * @since 2018-12-18 13:53.
 */
public class DropwizardModule extends AbstractModule {
    private final StartupConfig startupConfig;

    public DropwizardModule(StartupConfig startupConfig) {
        this.startupConfig = startupConfig;
    }

    @Override
    protected void configure() {

    }

    @Provides @Singleton
    ProductServiceResource providesProductServiceResource() {
        return new ProductServiceResource(startupConfig.getName(), startupConfig.getOwner());
    }
}
