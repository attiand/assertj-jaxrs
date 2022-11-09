package com.github.attiand.assertj.jaxrs.asserts;

import static com.github.attiand.assertj.jaxrs.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.ws.rs.core.NewCookie;

import org.junit.jupiter.api.Test;

class CookieAssertTest {

	@Test
	void shouldAcceptValidValues() {
		var cookie = new NewCookie("mycookie", "cookievalue", "cookiepath", "cookiedomain", "cookiecomment", 10, true, true);

		assertThat(cookie).hasName("mycookie")
				.hasValue("cookievalue")
				.hasPath("cookiepath")
				.hasDomain("cookiedomain")
				.hasMaxAge(10)
				.isSecure()
				.isHttpOnly();
	}

	@Test
	void shouldAcceptDefaultValues() {
		var cookie = new NewCookie("mycookie", "cookievalue");

		assertThat(cookie).hasName("mycookie")
				.hasValue("cookievalue")
				.hasNoPath()
				.hasNoDomain()
				.hasNoMaxAge()
				.isInsecure()
				.isNotHttpOnly();
	}

	@Test
	void shouldAssertInvalidValue() {
		var cookie = new NewCookie("mycookie", "cookievalue");

		var cut = assertThat(cookie);

		assertThatThrownBy(() -> {
			cut.hasValue("faultyvalue");
		}).isInstanceOf(AssertionError.class).hasMessageContaining("Expected cookie value to be <faultyvalue> but was <cookievalue>");
	}
}
