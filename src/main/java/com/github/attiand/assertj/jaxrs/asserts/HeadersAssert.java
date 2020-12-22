package com.github.attiand.assertj.jaxrs.asserts;

import javax.ws.rs.core.MultivaluedMap;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.ListAssert;

public class HeadersAssert extends AbstractAssert<HeadersAssert, MultivaluedMap<String, Object>> {

	public HeadersAssert(MultivaluedMap<String, Object> actual) {
		super(actual, HeadersAssert.class);
	}

	public static HeadersAssert assertThat(MultivaluedMap<String, Object> actual) {
		return new HeadersAssert(actual);
	}

	public ListAssert<Object> extractHeader(String name) {
		isNotNull();

		if (!actual.containsKey(name)) {
			failWithMessage("Expecting:%n <%s>%nto contain header:%n <%s>", actual.keySet(), name);
		}

		return new ListAssert<>(actual.get(name));
	}
}
