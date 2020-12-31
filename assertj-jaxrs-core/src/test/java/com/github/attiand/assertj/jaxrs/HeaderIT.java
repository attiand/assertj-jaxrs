package com.github.attiand.assertj.jaxrs;

import static org.mockserver.model.HttpRequest.request;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.matchers.Times;
import org.mockserver.model.Header;
import org.mockserver.model.HttpResponse;

import com.github.attiand.assertj.jaxrs.asserts.HeadersAssert;
import com.github.attiand.assertj.jaxrs.asserts.ResponseAssert;
import com.github.attiand.assertj.jaxrs.jupiter.AssertjJaxrsExtension;

@ExtendWith(AssertjJaxrsExtension.class)
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
class HeaderIT {

	@Test
	void shouldAcceptExsistingHeader(ClientAndServer server, WebTarget target) {
		server.when(request().withMethod("OPTIONS").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200).withHeader(new Header(HttpHeaders.ALLOW, "GET")));

		try (Response response = target.path("/resource").request().options()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).hasHeader(HttpHeaders.ALLOW).hasNoEntity();
		}
	}

	@Test
	void shouldAcceptSatisfiedHeader(ClientAndServer server, WebTarget target) {
		server.when(request().withMethod("OPTIONS").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200).withHeader(new Header(HttpHeaders.ALLOW, "GET")));

		try (Response response = target.path("/resource").request().options()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).headersSatisfies(h -> {
				HeadersAssert.assertThat(h).extractHeader(HttpHeaders.ALLOW).singleElement().isEqualTo("GET");
			}).hasNoEntity();
		}
	}

	@Test
	void shouldAcceptSatisfiedHeaderMultipleValues(ClientAndServer server, WebTarget target) {
		server.when(request().withMethod("OPTIONS").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200).withHeader(new Header(HttpHeaders.ALLOW, "GET", "PUT")));

		try (Response response = target.path("/resource").request().options()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).headersSatisfies(h -> {
				HeadersAssert.assertThat(h).extractHeader(HttpHeaders.ALLOW).containsExactlyInAnyOrder("GET", "PUT");
			}).hasNoEntity();
		}
	}
}
