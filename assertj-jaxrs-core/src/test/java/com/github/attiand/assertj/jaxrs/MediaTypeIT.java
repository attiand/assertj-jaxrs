package com.github.attiand.assertj.jaxrs;

import static com.github.attiand.assertj.jaxrs.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.matchers.Times;
import org.mockserver.model.HttpResponse;

import com.github.attiand.assertj.jaxrs.jupiter.AssertjJaxrsExtension;

@ExtendWith(AssertjJaxrsExtension.class)
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
class MediaTypeIT {

	private final WebTarget target;

	public MediaTypeIT(Client client) {
		target = client.target("http://localhost:8081");
	}

	@Test
	void shouldAcceptExistingtMediaType(ClientAndServer server) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200).withContentType(org.mockserver.model.MediaType.APPLICATION_JSON));

		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK).hasMediaType(MediaType.APPLICATION_JSON_TYPE).hasEntity();
		}
	}

	@Test
	void shouldAcceptSatisfiedMediaType(ClientAndServer server) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withContentType(org.mockserver.model.MediaType.APPLICATION_JSON_UTF_8));

		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK).mediaTypeSatisfies(mt -> {
				assertThat(mt).hasType("application")
						.hasSubType("json")
						.parameters()
						.extractingByKey(MediaType.CHARSET_PARAMETER)
						.isEqualTo("utf-8");
			}).hasEntity();
		}
	}
}
