package com.github.attiand.assertj.jaxrs.json;

import static org.mockserver.model.HttpRequest.request;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.matchers.Times;
import org.mockserver.model.Header;
import org.mockserver.model.HttpResponse;

import com.github.attiand.assertj.jaxrs.asserts.ResponseAssert;
import com.github.attiand.assertj.jaxrs.json.asserts.JsonObjectAssert;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
class JsonPointerIT {

	static Client client = ClientBuilder.newClient();

	@Test
	void shouldAssertJsonPointerBody(ClientAndServer server) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
						.withBody("{\"name\":\"myname\",\"value\":10}"));

		try (Response response = client.target("http://localhost:8081/resource").request().get()) {
			ResponseAssert.assertThat(response).hasStatusCode(Status.OK).entityAsJson().satisfies(o -> {
				JsonObjectAssert.assertThat(o).path("/name").asString().isEqualTo("myname");
				JsonObjectAssert.assertThat(o).path("/value").asInteger().isEqualTo(10);
			});
		}
	}

	@AfterAll
	static void afterAll() {
		client.close();
	}
}
