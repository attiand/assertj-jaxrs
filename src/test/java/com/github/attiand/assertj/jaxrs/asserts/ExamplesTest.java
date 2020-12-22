package com.github.attiand.assertj.jaxrs.asserts;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.matchers.Times;
import org.mockserver.model.Header;
import org.mockserver.model.HttpResponse;

import com.github.attiand.assertj.jaxrs.TestTargetBuilder;

@ExtendWith(MockServerExtension.class)
class ExamplesTest {

	WebTarget target = TestTargetBuilder.newBuilder().baseURI("http://localhost:8081").build();

	@Nested
	@MockServerSettings(ports = { 8787, 8081 })
	class StatusCode {

		StatusCode(ClientAndServer client) {
			client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
					.respond(HttpResponse.response().withStatusCode(200));
		}

		@Test
		void shouldAssertStatusCodeAsInteger(ClientAndServer client) {
			ResponseAssert.assertThat(target.path("/resource").request().get()).hasStatusCode(200);
		}

		@Test
		void shouldAssertStatusCodeConstant() {
			ResponseAssert.assertThat(target.path("/resource").request().get()).hasStatusCode(Status.OK);
		}

		@Test
		void shouldAssertStatusCodeExpression() {
			ResponseAssert.assertThat(target.path("/resource").request().get()).hasStatusCodeFamily(SUCCESSFUL);
		}

	}

	@Nested
	@MockServerSettings(ports = { 8787, 8081 })
	class Body {

		@Test
		void shouldAssertBodyExists(ClientAndServer client) {
			client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
					.respond(HttpResponse.response()
							.withStatusCode(200)
							.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN))
							.withBody("hello"));

			ResponseAssert.assertThat(target.path("/resource").request().get()).hasStatusCode(Status.OK).hasEntity();
		}

		@Test
		void shouldAssertBodyDoesNotExists(ClientAndServer client) {
			client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
					.respond(HttpResponse.response().withStatusCode(200));

			ResponseAssert.assertThat(target.path("/resource").request().get()).hasStatusCode(Status.OK).hasNoEntity();
		}

		@Test
		void shouldAssertTextBody(ClientAndServer client) {
			client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
					.respond(HttpResponse.response()
							.withStatusCode(200)
							.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN))
							.withBody("hello"));

			ResponseAssert.assertThat(target.path("/resource").request().get())
					.hasStatusCode(Status.OK)
					.entityAsText()
					.isEqualTo("hello");
		}

		@Test
		void shouldAssertJsonPointerBody(ClientAndServer client) {
			client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
					.respond(HttpResponse.response()
							.withStatusCode(200)
							.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
							.withBody("{\"name\":\"myname\",\"value\":10}"));

			ResponseAssert.assertThat(target.path("/resource").request().get()).hasStatusCode(Status.OK).entityAsJson().satisfies(o -> {
				JsonObjectAssert.assertThat(o).path("/name").asString().isEqualTo("myname");
				JsonObjectAssert.assertThat(o).path("/value").asInteger().isEqualTo(10);
			});
		}

		@Test
		void shouldAssertTypeBody(ClientAndServer client) {
			client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
					.respond(HttpResponse.response()
							.withStatusCode(200)
							.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
							.withBody("{\"name\":\"myname\",\"value\":10}"));

			ResponseAssert.assertThat(target.path("/resource").request().get())
					.hasStatusCode(Status.OK)
					.entityAs(ExampleRepresentation.class)
					.satisfies(r -> {
						assertThat(r.getName()).isEqualTo("myname");
						assertThat(r.getValue()).isEqualTo(10);
					});
		}
	}

	@Nested
	@MockServerSettings(ports = { 8787, 8081 })
	class Headers {

		@Test
		void shouldAssertHeaderName(ClientAndServer client) {
			client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
					.respond(HttpResponse.response()
							.withStatusCode(200)
							.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML)));

			ResponseAssert.assertThat(target.path("/resource").request().get())
					.hasStatusCode(Status.OK)
					.containsHeader(HttpHeaders.CONTENT_TYPE)
					.hasEntity();
		}

		@Test
		void shouldAssertHeaderSatisfies(ClientAndServer client) {
			client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
					.respond(HttpResponse.response()
							.withStatusCode(200)
							.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML)));

			ResponseAssert.assertThat(target.path("/resource").request().get()).hasStatusCode(Status.OK).headersSatisfies(h -> {
				HeadersAssert.assertThat(h).extractHeader(HttpHeaders.CONTENT_TYPE).singleElement().isEqualTo(MediaType.TEXT_HTML);
			}).hasEntity();
		}

		@Test
		void shouldAssertMediaTypeHeader(ClientAndServer client) {
			client.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
					.respond(HttpResponse.response()
							.withStatusCode(200)
							.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML + "; charset=utf-8")));

			ResponseAssert.assertThat(target.path("/resource").request().get()).hasStatusCode(Status.OK).headersSatisfies(h -> {
				HeadersAssert.assertThat(h).extractHeader(HttpHeaders.CONTENT_TYPE).singleElement().satisfies(ct -> {
					ContentTypeHeaderAssert.assertThat(ct)
							.hasType("text")
							.hasSubType("html")
							.parameters()
							.extractingByKey(MediaType.CHARSET_PARAMETER)
							.isEqualTo("utf-8");
				});
			}).hasEntity();
		}
	}
}