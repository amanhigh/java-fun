package com.util.injection;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author amanpreet.singh
 * @version 1.0.0
 * @since 2018-12-18 13:51.
 */
public class FunInjector {
    private static final Injector injector = Guice.createInjector(new FunModule());

    public static Injector Get() {
        return injector;
    }
}
