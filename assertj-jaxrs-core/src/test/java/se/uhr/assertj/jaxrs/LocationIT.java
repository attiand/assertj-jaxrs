package se.uhr.assertj.jaxrs;

import static se.uhr.assertj.jaxrs.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;

import jakarta.ws.rs.client.Client;
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
class LocationIT {

	private final static String BASE_URL = "http://localhost:8081";

	@Test
	void shouldAcceptValidLocation(Client client, ClientAndServer server) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(301)
						.withHeader(new Header(HttpHeaders.LOCATION, "http://www.example.com/index.html")));

		try (Response response = client.target(BASE_URL).path("/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.MOVED_PERMANENTLY).hasLocation("http://www.example.com/index.html").hasNoEntity();
		}
	}

	@Test
	void shouldCheckLocationURI(Client client, ClientAndServer server) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(301).withHeader(new Header(HttpHeaders.LOCATION, BASE_URL + "/new")));

		server.when(request().withMethod("GET").withPath("/new"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200));

		try (Response response = client.target(BASE_URL).path("/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.MOVED_PERMANENTLY).headersSatisfies(h -> {
				assertThat(h).extract(HttpHeaders.LOCATION).map(String.class::cast).first().satisfies(l -> {
					try (Response locationResponse = client.target(l).request().get()) {
						assertThat(locationResponse).hasStatusCode(Status.OK);
					}
				});
			});
		}
	}
}
