package com.github.attiand.assertj.jaxrs.asserts;

import java.util.Map;

import javax.ws.rs.core.NewCookie;

import org.assertj.core.api.AbstractAssert;

public class CookiesAssert extends AbstractAssert<CookiesAssert, Map<String, NewCookie>> {

	public CookiesAssert(Map<String, NewCookie> actual) {
		super(actual, CookiesAssert.class);
	}

	public static CookiesAssert assertThat(Map<String, NewCookie> actual) {
		return new CookiesAssert(actual);
	}

	public CookieAssert extractCookie(String name) {
		isNotNull();

		if (!actual.containsKey(name)) {
			failWithMessage("Expecting:%n <%s>%nto contain cookie:%n <%s>", actual.keySet(), name);
		}

		return new CookieAssert(actual.get(name));
	}
}
