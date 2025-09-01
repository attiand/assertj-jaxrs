package se.uhr.assertj.jaxrs.jupiter;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * Provides a {@link Client} Jupiter {@link ParameterResolver}.
 */

public class AssertjJaxrsExtension implements BeforeAllCallback, ParameterResolver, ExtensionContext.Store.CloseableResource {

	private Client client;

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		context.getRoot().getStore(GLOBAL).put(this.getClass().getName(), this);
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
		throws ParameterResolutionException {
		return Client.class.isAssignableFrom(parameterContext.getParameter().getType());
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
		throws ParameterResolutionException {

		if (client == null) {
			client = ClientBuilder.newClient();
		}

		return client;
	}

	@Override
	public void close() throws Throwable {
		if (client != null) {
			client.close();
		}
	}
}