package com.github.attiand.assertj.jaxrs.asserts;

import java.net.URI;

import javax.ws.rs.core.Link;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.MapAssert;

public class LinkAssert extends AbstractAssert<LinkAssert, Link> {

	public LinkAssert(Link actual) {
		super(actual, LinkAssert.class);
	}

	public static LinkAssert assertThat(Link link) {
		return new LinkAssert(link);
	}

	public LinkAssert hasRel(String rel) {
		isNotNull();

		if (!rel.equals(actual.getRel())) {
			failWithMessage("Expected link rel to be <%s> but was <%s>", rel, actual.getRel());
		}

		return this;
	}

	public LinkAssert hasTitle(String title) {
		isNotNull();

		if (!title.equals(actual.getTitle())) {
			failWithMessage("Expected link title to be <%s> but was <%s>", title, actual.getTitle());
		}

		return this;
	}

	public LinkAssert hasType(String type) {
		isNotNull();

		if (!type.equals(actual.getType())) {
			failWithMessage("Expected link type to be <%s> but was <%s>", type, actual.getType());
		}

		return this;
	}

	public LinkAssert hasUri(URI uri) {
		isNotNull();

		if (!uri.equals(actual.getUri())) {
			failWithMessage("Expected link uri to be <%s> but was <%s>", uri, actual.getUri());
		}

		return this;
	}

	public LinkAssert hasUri(String uri) {
		return hasUri(URI.create(uri));
	}

	public MapAssert<String, String> parameters() {
		return new MapAssert<>(actual.getParams());
	}
}
