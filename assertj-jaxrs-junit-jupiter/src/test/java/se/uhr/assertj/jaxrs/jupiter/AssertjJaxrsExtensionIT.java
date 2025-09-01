package se.uhr.assertj.jaxrs.jupiter;

import static se.uhr.assertj.jaxrs.asserts.ResponseAssert.assertThat;
import static org.mockserver.model.HttpRequest.request;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

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
