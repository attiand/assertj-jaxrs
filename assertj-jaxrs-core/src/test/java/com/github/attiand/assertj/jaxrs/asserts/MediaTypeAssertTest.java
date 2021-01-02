package com.github.attiand.assertj.jaxrs.asserts;

import static com.github.attiand.assertj.jaxrs.Assertions.assertThat;
import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;

class MediaTypeAssertTest {

	@Test
	void shouldAcceptSatisfiedMediaType() {
		assertThat(MediaType.valueOf("text/html; charset=UTF-8")).satisfies(ct -> {
			assertThat(ct).hasType("text")
					.hasSubType("html")
					.parameters()
					.extractingByKey(MediaType.CHARSET_PARAMETER)
					.usingComparator(CASE_INSENSITIVE_ORDER)
					.isEqualTo("utf-8");
		});
	}

	@Test
	void shouldAssertSatisfiedMediaType() {
		assertThat(MediaType.valueOf("text/html")).satisfies(ct -> {
			MediaTypeAssert cut = assertThat(ct).hasType("text");

			assertThatThrownBy(() -> {
				cut.hasSubType("plain");
			}).isInstanceOf(AssertionError.class).hasMessage("Expecting content sub type <text> to be equal to <plain>");
		});
	}
}
