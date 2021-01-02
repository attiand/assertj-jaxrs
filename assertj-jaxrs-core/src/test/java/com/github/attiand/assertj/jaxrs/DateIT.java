package com.github.attiand.assertj.jaxrs;

import static com.github.attiand.assertj.jaxrs.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
class DateIT {

	@Test
	void shouldAcceptNonExistingDate(ClientAndServer server, WebTarget target) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200));

		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK).hasNoDate().hasNoEntity();
		}
	}

	@Test
	void shouldAcceptNonExistingLastModifiedDate(ClientAndServer server, WebTarget target) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200));

		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK).hasNoLastModifiedDate().hasNoEntity();
		}
	}
}
