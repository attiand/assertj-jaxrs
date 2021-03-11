package com.github.attiand.assertj.jaxrs.jupiter;

import static com.github.attiand.assertj.jaxrs.asserts.ResponseAssert.assertThat;
import static org.mockserver.model.HttpRequest.request;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.matchers.Times;
import org.mockserver.model.HttpResponse;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8088 })
@ExtendWith(AssertjJaxrsExtension.class)
class AssertjJaxrsExtensionIT {

	@Test
	void shouldAssertStatusCodeAsInteger(ClientAndServer server, Client client) {
		WebTarget target = client.target("http://localhost:8088");

		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200));

		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCode(200);
		}
	}
}
