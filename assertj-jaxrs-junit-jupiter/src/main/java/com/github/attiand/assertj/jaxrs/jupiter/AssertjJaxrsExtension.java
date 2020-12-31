package com.github.attiand.assertj.jaxrs.jupiter;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

/**
 * Provides a {@link WebTarget} Jupiter {@link ParameterResolver}. You may specify the target URI with {@link AssertjJaxrsSettings}.
 */

public class AssertjJaxrsExtension implements ParameterResolver, BeforeAllCallback, AfterAllCallback {

	private static final String DEFAULT_TARGET = "http://localhost:8081";

	private Client client;

	private Optional<AssertjJaxrsSettings> settings;

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		client = ClientBuilder.newClient();
		settings = AnnotationSupport.findAnnotation(context.getElement(), AssertjJaxrsSettings.class);
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		client.close();
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
		throws ParameterResolutionException {
		return WebTarget.class.isAssignableFrom(parameterContext.getParameter().getType());
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
		throws ParameterResolutionException {

		return settings.map(s -> client.target(s.baseUri())).orElseGet(() -> client.target(DEFAULT_TARGET));
	}
}
