package com.github.attiand.assertj.jaxrs;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class TestTargetBuilder {

	private String baseUri = "http://localhost:8080";

	public static TestTargetBuilder newBuilder() {
		return new TestTargetBuilder();
	}

	public TestTargetBuilder baseURI(String uri) {
		this.baseUri = uri;
		return this;
	}

	public WebTarget newWebTarget() {
		return newBuilder().build();
	}

	public WebTarget build() {
		Client client = ClientBuilder.newClient();
		return client.target(baseUri);
	}
}