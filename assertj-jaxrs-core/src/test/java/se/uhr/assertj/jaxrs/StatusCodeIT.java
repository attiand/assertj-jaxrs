package se.uhr.assertj.jaxrs;

import static se.uhr.assertj.jaxrs.asserts.ResponseAssert.assertThat;
import static jakarta.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static org.mockserver.model.HttpRequest.request;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.matchers.Times;
import org.mockserver.model.HttpResponse;

import se.uhr.assertj.jaxrs.jupiter.AssertjJaxrsExtension;

@ExtendWith(AssertjJaxrsExtension.class)
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
class StatusCodeIT {

	private final WebTarget target;

	StatusCodeIT(Client client, ClientAndServer server) {
		target = client.target("http://localhost:8081");

		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200));
	}

	@Test
	void shouldAcceptStatusCodeAsInteger() {
		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCode(200);
		}
	}

	@Test
	void shouldAcceptStatusCodeConstant() {
		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK);
		}
	}

	@Test
	void shouldAcceptStatusCodeFamily() {
		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCodeFamily(SUCCESSFUL);
		}
	}
}
