package com.github.attiand.assertj.jaxrs.jupiter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * Only for core test, see assertj-jaxts-junit-jupiter.
 */

public class AssertjJaxrsExtension implements ParameterResolver, BeforeAllCallback, AfterAllCallback {

	private static final String TARGET = "http://localhost:8081";
	private Client client;

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		client = ClientBuilder.newClient();
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
		return client.target(TARGET);
	}
}
