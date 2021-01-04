package com.github.attiand.assertj.jaxrs;

import static com.github.attiand.assertj.jaxrs.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;

import java.net.URI;

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

import com.github.attiand.assertj.jaxrs.jupiter.AssertjJaxrsExtension;

@ExtendWith(AssertjJaxrsExtension.class)
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
class LinkIT {

	@Test
	void shouldAcceptExsistingHeader(ClientAndServer server, WebTarget target) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.LINK,
								"<http://localhost/root/customers/1234>; rel=\"update\"; type=\"text/plain\"")));

		try (Response response = target.path("/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK).linksSatisfies(ls -> {
				assertThat(ls).anySatisfy(l -> {
					assertThat(l).hasRel("update").hasUri(URI.create("http://localhost/root/customers/1234")).hasType("text/plain");
				});
			}).hasNoEntity();
		}
	}
}
