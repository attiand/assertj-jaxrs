package com.github.attiand.assertj.jaxrs.json;

import static com.github.attiand.assertj.jaxrs.Assertions.assertThat;
import static com.github.attiand.assertj.jaxrs.json.EntityTypes.JSON;
import static com.github.attiand.assertj.jaxrs.json.asserts.JsonStructureAssert.assertThat;
import static org.mockserver.model.HttpRequest.request;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.matchers.Times;
import org.mockserver.model.Header;
import org.mockserver.model.HttpResponse;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
class JsonPointerIT {

	static Client client = ClientBuilder.newClient();

	@Test
	void shouldAssertJsonObject(ClientAndServer server) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
						.withBody("{\"name\":\"myname\",\"value\":10}"));

		try (Response response = client.target("http://localhost:8081/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK).entityAs(JSON).satisfies(o -> {
				assertThat(o).pathValue("/name").asString().isEqualTo("myname");
				assertThat(o).pathValue("/value").asInteger().isEqualTo(10);
			});
		}
	}

	@Test
	void shouldAssertJsonArray(ClientAndServer server) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
						.withBody("[\"one\", \"two\", \"tree\"]"));

		try (Response response = client.target("http://localhost:8081/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK).entityAs(JSON).satisfies(o -> {
				assertThat(o).pathValue("/0").asString().isEqualTo("one");
				assertThat(o).pathValue("/2").asString().isEqualTo("tree");
			});
		}
	}

	@Test
	void shouldAssertSubJsonObject(ClientAndServer server) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
						.withBody("{\"a\": [{\"name\":\"myname\",\"value\":10}, {\"name\":\"myname2\",\"value\":11}]}"));

		try (Response response = client.target("http://localhost:8081/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK).entityAs(JSON).satisfies(e -> {
				assertThat(e).pathValue("/a/0").satisfies(v -> {
					assertThat(v.asJsonObject()).pathValue("/name").asString().isEqualTo("myname");
					assertThat(v.asJsonObject()).pathValue("/value").asInteger().isEqualTo(10);
				});
			});
		}
	}

	@Test
	void shouldHandleNullValue(ClientAndServer server) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
						.withBody("{\"name\":\"myname\",\"value\":null}"));

		try (Response response = client.target("http://localhost:8081/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK).entityAs(JSON).satisfies(o -> {
				assertThat(o).pathValue("/name").isNotNull().asString().isEqualTo("myname");
				assertThat(o).containsPath("/value").doesNotContainPath("/nonexist").pathValue("/value").isNull();
			});
		}
	}

	@AfterAll
	static void afterAll() {
		client.close();
	}
}
