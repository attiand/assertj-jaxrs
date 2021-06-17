package com.github.attiand.assertj.jaxrs.xml;

import static com.github.attiand.assertj.jaxrs.Assertions.assertThat;
import static com.github.attiand.assertj.jaxrs.xml.EntityTypes.XML;
import static com.github.attiand.assertj.jaxrs.xml.asserts.NodeAssert.assertThat;
import static org.mockserver.model.HttpRequest.request;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

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
import org.w3c.dom.Node;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 8081 })
class NodeListAssertIT {

	static Client client = ClientBuilder.newClient();

	@Test
	void shouldAssertXmlObject(ClientAndServer server) throws IOException, URISyntaxException {
		server.when(request().withMethod("GET").withPath("/resource"), Times.exactly(1))
				.respond(HttpResponse.response()
						.withStatusCode(200)
						.withHeader(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML))
						.withBody(Files.readAllBytes(Path.of(this.getClass().getResource("/nodelist.xml").toURI()))));

		try (Response response = client.target("http://localhost:8081/resource").request().get()) {
			assertThat(response).hasStatusCode(Status.OK).entityAs(XML).satisfies(d -> {
				assertThat(d).xpath("/test/strings/str[2]").asString().isEqualTo("m2");

				assertThat(d).xpath("/test/strings/str")
						.asNodeList()
						.hasSize(3)
						.extracting(Node::getTextContent)
						.contains("m1", "m2", "m3");
			});
		}
	}

	@AfterAll
	static void afterAll() {
		client.close();
	}
}
