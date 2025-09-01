package se.uhr.assertj.jaxrs;

import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

import se.uhr.assertj.jaxrs.asserts.CookieAssert;
import se.uhr.assertj.jaxrs.asserts.LinkAssert;
import se.uhr.assertj.jaxrs.asserts.MediaTypeAssert;
import se.uhr.assertj.jaxrs.asserts.MultivaluedMapAssert;
import se.uhr.assertj.jaxrs.asserts.ResponseAssert;

public interface Assertions {

	public static ResponseAssert assertThat(Response response) {
		return new ResponseAssert(response);
	}

	public static CookieAssert assertThat(NewCookie cookie) {
		return new CookieAssert(cookie);
	}

	public static MultivaluedMapAssert assertThat(MultivaluedMap<String, Object> actual) {
		return new MultivaluedMapAssert(actual);
	}

	public static MediaTypeAssert assertThat(MediaType mediaType) {
		return new MediaTypeAssert(mediaType);
	}

	public static LinkAssert assertThat(Link link) {
		return new LinkAssert(link);
	}
}
