package se.uhr.assertj.jaxrs;

import static se.uhr.assertj.jaxrs.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.matchers.Times;
import org.mockserver.model.Header;
import org.mockserver.model.HttpResponse;

import se.uhr.assertj.jaxrs.jupiter.AssertjJaxrsExtension;

@ExtendWith(AssertjJaxrsExtension.class)
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
class HeaderIT {

	private final WebTarget target;

	public HeaderIT(Client client) {
		target = client.target("http://localhost:8081");
	}

	@Test
	void shouldAcceptExsistingHeader(ClientAndServer server) {
		server.when(request().withMethod("OPTIONS").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200).withHeader(new Header(HttpHeaders.ALLOW, "GET")));

		try (Response response = target.path("/resource").request().options()) {
			assertThat(response).hasStatusCode(Status.OK).hasHeader(HttpHeaders.ALLOW).hasNoEntity();
		}
	}

	@Test
	void shouldAcceptSatisfiedHeader(ClientAndServer server) {
		server.when(request().withMethod("OPTIONS").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200).withHeader(new Header(HttpHeaders.ALLOW, "GET")));

		try (Response response = target.path("/resource").request().options()) {
			assertThat(response).hasStatusCode(Status.OK).headersSatisfies(h -> {
				assertThat(h).extract(HttpHeaders.ALLOW).singleElement().isEqualTo("GET");
			}).hasNoEntity();
		}
	}

	@Test
	void shouldAcceptSatisfiedHeaderMultipleValues(ClientAndServer server) {
		server.when(request().withMethod("OPTIONS").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200).withHeader(new Header(HttpHeaders.ALLOW, "GET", "PUT")));

		try (Response response = target.path("/resource").request().options()) {
			assertThat(response).hasStatusCode(Status.OK).headersSatisfies(h -> {
				assertThat(h).extract(HttpHeaders.ALLOW).containsExactlyInAnyOrder("GET", "PUT");
			}).hasNoEntity();
		}
	}
}
