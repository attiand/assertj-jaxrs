package com.github.attiand.assertj.jaxrs;

import static org.mockserver.model.HttpRequest.request;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.matchers.Times;
import org.mockserver.model.HttpResponse;

import com.github.attiand.assertj.jaxrs.asserts.MediaTypeAssert;
import com.github.attiand.assertj.jaxrs.asserts.ResponseAssert;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
class MediaTypeIT {

	WebTarget target = TestTargetBuilder.newBuilder().build();

	@Test
	void shouldAcceptExistingtMediaType(ClientAndServer client) {
		client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200).withContentType(org.mockserver.model.MediaType.APPLICATION_JSON));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).hasMediaType(MediaType.APPLICATION_JSON_TYPE).hasEntity();
		}
	}

	@Test
	void shouldAcceptSatisfiedMediaType(ClientAndServer client) {
		client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withContentType(org.mockserver.model.MediaType.APPLICATION_JSON_UTF_8));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).mediaTypeSatisfies(mt -> {
				MediaTypeAssert.assertThat(mt)
						.hasType("application")
						.hasSubType("json")
						.parameters()
						.extractingByKey(MediaType.CHARSET_PARAMETER)
						.isEqualTo("utf-8");
			}).hasEntity();
		}
	}
}
