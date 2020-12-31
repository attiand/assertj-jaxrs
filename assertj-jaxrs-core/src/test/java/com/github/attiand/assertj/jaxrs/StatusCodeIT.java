package com.github.attiand.assertj.jaxrs;

import static com.github.attiand.assertj.jaxrs.asserts.ResponseAssert.assertThat;
import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
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
class StatusCodeIT {

	StatusCodeIT(ClientAndServer client) {
		client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200));
	}

	@Test
	void shouldAcceptStatusCodeAsInteger(WebTarget target) {
		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCode(200);
		}
	}

	@Test
	void shouldAcceptStatusCodeConstant(WebTarget target) {
		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK);
		}
	}

	@Test
	void shouldAcceptStatusCodeFamily(WebTarget target) {
		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCodeFamily(SUCCESSFUL);
		}
	}
}
