package com.github.attiand.assertj.jaxrs.asserts;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.ws.rs.core.NewCookie;

import org.junit.jupiter.api.Test;

class CookieAssertTest {

	@Test
	void shouldAcceptValues() {
		NewCookie cookie = new NewCookie("mycookie", "cookievalue", "cookiepath", "cookiedomain", "cookiecomment", 10, true, true);

		CookieAssert.assertThat(cookie)
				.hasName("mycookie")
				.hasValue("cookievalue")
				.hasPath("cookiepath")
				.hasDomain("cookiedomain")
				.hasMaxAge(10)
				.isSecure()
				.isHttpOnly();
	}

	@Test
	void shouldAcceptDefaultValues() {
		NewCookie cookie = new NewCookie("mycookie", "cookievalue");

		CookieAssert.assertThat(cookie)
				.hasName("mycookie")
				.hasValue("cookievalue")
				.hasNoPath()
				.hasNoDomain()
				.hasNoMaxAge()
				.isInsecure()
				.isNotHttpOnly();
	}

	@Test
	void shouldAssertIncorrectValue() {
		NewCookie cookie = new NewCookie("mycookie", "cookievalue");

		CookieAssert cut = CookieAssert.assertThat(cookie);

		assertThatThrownBy(() -> {
			cut.hasValue("faultyvalue");
		}).isInstanceOf(AssertionError.class).hasMessageContaining("Expected cookie value to be <faultyvalue> but was <cookievalue>");
	}
}
