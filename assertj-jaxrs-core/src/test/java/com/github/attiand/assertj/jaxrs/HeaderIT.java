package com.github.attiand.assertj.jaxrs;

import static org.mockserver.model.HttpRequest.request;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
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

import com.github.attiand.assertj.jaxrs.asserts.ContentTypeHeaderAssert;
import com.github.attiand.assertj.jaxrs.asserts.HeadersAssert;
import com.github.attiand.assertj.jaxrs.asserts.ResponseAssert;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
class HeaderIT {

	WebTarget target = TestTargetBuilder.newBuilder().build();

	@Test
	void shouldAssertHeaderName(ClientAndServer client) {
		client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200).withHeader(new Header(HttpHeaders.ACCEPT, MediaType.TEXT_HTML)));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).containHeader(HttpHeaders.ACCEPT).hasNoEntity();
		}
	}

	@Test
	void shouldAssertHeaderSatisfies(ClientAndServer client) {
		client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200).withHeader(new Header(HttpHeaders.ACCEPT, MediaType.TEXT_HTML)));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).headersSatisfies(h -> {
				HeadersAssert.assertThat(h).extractHeader(HttpHeaders.ACCEPT).singleElement().isEqualTo(MediaType.TEXT_HTML);
			}).hasNoEntity();
		}
	}

	@Test
	void shouldAssertMediaTypeHeader(ClientAndServer client) {
		client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withContentType(org.mockserver.model.MediaType.APPLICATION_JSON_UTF_8));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).headersSatisfies(h -> {
				HeadersAssert.assertThat(h).extractHeader(HttpHeaders.CONTENT_TYPE).singleElement().satisfies(ct -> {
					ContentTypeHeaderAssert.assertThat(ct)
							.hasType("application")
							.hasSubType("json")
							.parameters()
							.extractingByKey(MediaType.CHARSET_PARAMETER)
							.isEqualTo("utf-8");
				});
			}).hasEntity();
		}
	}
}
