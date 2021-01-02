package com.github.attiand.assertj.jaxrs;

import static com.github.attiand.assertj.jaxrs.Assertions.assertThat;
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
class CookieIT {

	@Test
	void shouldAcceptExistingCookie(ClientAndServer server, WebTarget target) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response().withStatusCode(200).withCookie("sessionId", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW"));

		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK).hasCookie("sessionId").hasNoEntity();
		}
	}

	@Test
	void shouldAcceptSatisfiedCookie(ClientAndServer server, WebTarget target) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withCookie(new org.mockserver.model.Cookie("sessionId", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")));

		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK).cookiesSatisfies(c -> {
				assertThat(c).extractCookie("sessionId").hasValue("2By8LOhBmaW5nZXJwcmludCIlMDAzMW").hasNoDomain().hasNoPath();
			});
		}
	}
}
