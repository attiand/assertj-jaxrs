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
import org.mockserver.model.Header;
import org.mockserver.model.HttpResponse;

import com.github.attiand.assertj.jaxrs.asserts.JsonObjectAssert;
import com.github.attiand.assertj.jaxrs.asserts.ResponseAssert;

@ExtendWith(MockServerExtension.class)
@ExtendWith(AssertjJaxrsExtension.class)
@MockServerSettings(ports = { 8081 })
class BodyIT {

	@Test
	void shouldAssertBodyExists(ClientAndServer client, WebTarget target) {
		client.when(request().withMethod("GET").withPath("/resource"))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN))
						.withBody("hello"));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).hasEntity();
		}
	}

	@Test
	void shouldAssertBodyDoesNotExists(ClientAndServer client, WebTarget target) {
		client.when(request().withMethod("GET").withPath("/resource")).respond(HttpResponse.response().withStatusCode(200));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).hasNoEntity();
		}
	}

	@Test
	void shouldAssertTextBody(ClientAndServer client, WebTarget target) {
		client.when(request().withMethod("GET").withPath("/resource"))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN))
						.withBody("hello"));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).entityAsText().isEqualTo("hello");
		}
	}

	@Test
	void shouldAssertJsonPointerBody(ClientAndServer client, WebTarget target) {
		client.when(request().withMethod("GET").withPath("/resource"))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
						.withBody("{\"name\":\"myname\",\"value\":10}"));

		try (Response response = target.path("/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).entityAsJson().satisfies(o -> {
				JsonObjectAssert.assertThat(o).path("/name").asString().isEqualTo("myname");
				JsonObjectAssert.assertThat(o).path("/value").asInteger().isEqualTo(10);
			});
		}
	}

	@Test
	void shouldAssertTypeBody(ClientAndServer client, WebTarget target) {
		client.when(request().withMethod("GET").withPath("/resource"))
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
