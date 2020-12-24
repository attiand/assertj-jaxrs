package com.github.attiand.assertj.jaxrs.jupiter;

import java.util.Optional;

import javax.ws.rs.client.WebTarget;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

import com.github.attiand.assertj.jaxrs.TestTargetBuilder;

public class AssertjJaxrsExtension implements ParameterResolver, BeforeAllCallback {

	private WebTarget target;

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		Optional<AssertjJaxrsSettings> settings = AnnotationSupport.findAnnotation(context.getElement(), AssertjJaxrsSettings.class);

		target = settings.map(s -> TestTargetBuilder.newBuilder().baseURI(s.baseUri()).build())
				.orElseGet(() -> TestTargetBuilder.newBuilder().build());
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
		throws ParameterResolutionException {
		return WebTarget.class.isAssignableFrom(parameterContext.getParameter().getType());
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
		throws ParameterResolutionException {
		return target;
	}
}
