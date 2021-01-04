package com.github.attiand.assertj.jaxrs;

import java.util.Map;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import com.github.attiand.assertj.jaxrs.asserts.CookieAssert;
import com.github.attiand.assertj.jaxrs.asserts.CookiesAssert;
import com.github.attiand.assertj.jaxrs.asserts.HeadersAssert;
import com.github.attiand.assertj.jaxrs.asserts.LinkAssert;
import com.github.attiand.assertj.jaxrs.asserts.MediaTypeAssert;
import com.github.attiand.assertj.jaxrs.asserts.ResponseAssert;

public interface Assertions {

	public static ResponseAssert assertThat(Response response) {
		return new ResponseAssert(response);
	}

	public static CookieAssert assertThat(NewCookie cookie) {
		return new CookieAssert(cookie);
	}

	public static CookiesAssert assertThat(Map<String, NewCookie> actual) {
		return new CookiesAssert(actual);
	}

	public static HeadersAssert assertThat(MultivaluedMap<String, Object> actual) {
		return new HeadersAssert(actual);
	}

	public static MediaTypeAssert assertThat(MediaType mediaType) {
		return new MediaTypeAssert(mediaType);
	}

	public static LinkAssert assertThat(Link link) {
		return new LinkAssert(link);
	}
}
