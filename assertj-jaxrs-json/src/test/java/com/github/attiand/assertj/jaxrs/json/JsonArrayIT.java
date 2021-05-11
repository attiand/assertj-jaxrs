package com.github.attiand.assertj.jaxrs.json;

import static com.github.attiand.assertj.jaxrs.Assertions.assertThat;
import static com.github.attiand.assertj.jaxrs.json.EntityTypes.JSON;
import static com.github.attiand.assertj.jaxrs.json.asserts.JsonStructureAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;

import javax.json.JsonString;
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

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
class JsonArrayIT {

	static Client client = ClientBuilder.newClient();

	@Test
	void shouldAcceptArrayExtracting(ClientAndServer server) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
						.withBody("[\"one\", \"two\", \"tree\"]"));

		try (Response response = client.target("http://localhost:8081/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK).entityAs(JSON).satisfies(o -> {
				assertThat(o).asJsonArray().extracting(JsonString::getString).hasSize(3).containsExactly("one", "two", "tree");

				assertThat(o).asJsonArray().extracting(JsonString::getString).isNotEmpty().allSatisfy(s -> {
					assertThat(s).hasSizeBetween(3, 4);
				});
			});
		}
	}

	@AfterAll
	static void afterAll() {
		client.close();
	}
}
