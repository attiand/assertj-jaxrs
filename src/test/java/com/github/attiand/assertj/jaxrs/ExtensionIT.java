package com.github.attiand.assertj.jaxrs;

import static com.github.attiand.assertj.jaxrs.asserts.ResponseAssert.assertThat;
import static org.mockserver.model.HttpRequest.request;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.HttpResponse;

@ExtendWith(MockServerExtension.class)
@ExtendWith(AssertjJaxrsExtension.class)
@MockServerSettings(ports = { 8088 })
@AssertjJaxrsSettings(baseUri = "http://localhost:8088")
class ExtensionIT {

	@Test
	void shouldAssertStatusCodeAsInteger(ClientAndServer client, WebTarget target) {
		client.when(request().withMethod("GET").withPath("/resource")).respond(HttpResponse.response().withStatusCode(200));

		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCode(200);
		}
	}
}
