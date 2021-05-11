package com.github.attiand.assertj.jaxrs.xml;

import static com.github.attiand.assertj.jaxrs.Assertions.assertThat;
import static com.github.attiand.assertj.jaxrs.xml.EntityTypes.XML;
import static com.github.attiand.assertj.jaxrs.xml.asserts.DocumentAssert.assertThat;
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

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
public class XmlAssertIT {

	static Client client = ClientBuilder.newClient();

	@Test
	void shouldAssertXmlObject(ClientAndServer server) {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML))
						.withBody("<test><str>hello</str><int>10</int><double>1.2</double></test>"));

		try (Response response = client.target("http://localhost:8081/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK).entityAs(XML).satisfies(o -> {
				assertThat(o).xpath("/test/str").asString().isEqualTo("hello");
				assertThat(o).xpath("/test/int").asInteger().isEqualTo(10);
				assertThat(o).xpath("/test/double").asDouble().isEqualTo(1.2);
				assertThat(o).xpath("/test/nonexist").asBoolean().isFalse();
				assertThat(o).xpath("/test/str").asBoolean().isTrue();
			});
		}
	}

	@AfterAll
	static void afterAll() {
		client.close();
	}
}
