package com.github.attiand.assertj.jaxrs;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * Helper to create a rest client web target.  
 */

public class TestTargetBuilder {

	private String baseUri = "http://localhost:8081";

	public static TestTargetBuilder newBuilder() {
		return new TestTargetBuilder();
	}

	/**
	 * Set the URI, if not set <tt>http://localhost:8081</tt> is the default
	 * 
	 * @param uri The URI to test against. 
	 * @return The builder instance.
	 */

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