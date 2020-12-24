package com.github.attiand.assertj.jaxrs.json;

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

import com.github.attiand.assertj.jaxrs.TestTargetBuilder;
import com.github.attiand.assertj.jaxrs.asserts.ResponseAssert;
import com.github.attiand.assertj.jaxrs.json.asserts.JsonObjectAssert;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
class JsonPointerIT {

	WebTarget target = TestTargetBuilder.newBuilder().build();

	@Test
	void shouldAssertJsonPointerBody(ClientAndServer client) {
		client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
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
}
