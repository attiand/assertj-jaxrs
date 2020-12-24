package com.github.attiand.assertj.jaxrs;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.github.attiand.assertj.jaxrs.asserts.ResponseAssert;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
class BodyIT {

	WebTarget target = TestTargetBuilder.newBuilder().build();

	@Test
	void shouldAssertBodyExists(ClientAndServer client) {
		client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN))
						.withBody("hello"));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).hasEntity();
		}
	}

	@Test
	void shouldAssertBodyDoesNotExists(ClientAndServer client) {
		client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).hasNoEntity();
		}
	}

	@Test
	void shouldAssertTextBody(ClientAndServer client) {
		client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN))
						.withBody("hello"));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).entityAsText().isEqualTo("hello");
		}
	}

	@Test
	void shouldAssertTypeBody(ClientAndServer client) {
		client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
						.withBody("{\"name\":\"myname\",\"value\":10}"));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).entityAs(ExampleRepresentation.class).satisfies(r -> {
				assertThat(r.getName()).isEqualTo("myname");
				assertThat(r.getValue()).isEqualTo(10);
			});
		}
	}

	public static class ExampleRepresentation {

		private String name;
		private int value;

		public ExampleRepresentation() {

		}

		public ExampleRepresentation(String name, int value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}
}