package com.github.attiand.assertj.jaxrs;

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

import com.github.attiand.assertj.jaxrs.asserts.ResponseAssert;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
class DateIT {

	WebTarget target = TestTargetBuilder.newBuilder().build();

	@Test
	void shouldAcceptNonExistingDate(ClientAndServer client) {
		client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).hasNoDate().hasNoEntity();
		}
	}

	@Test
	void shouldAcceptNonExistingLastModifiedDate(ClientAndServer client) {
		client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).hasNoLastModifiedDate().hasNoEntity();
		}
	}
}
