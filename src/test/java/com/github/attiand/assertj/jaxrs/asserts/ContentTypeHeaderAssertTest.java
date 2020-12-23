package com.github.attiand.assertj.jaxrs.asserts;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;

class ContentTypeHeaderAssertTest {

	@Test
	void shouldParseContentType() {
		ContentTypeHeaderAssert.assertThat("text/html; charset=UTF-8").satisfies(ct -> {
			ContentTypeHeaderAssert.assertThat(ct)
					.hasType("text")
					.hasSubType("html")
					.parameters()
					.extractingByKey(MediaType.CHARSET_PARAMETER)
					.usingComparator(CASE_INSENSITIVE_ORDER)
					.isEqualTo("utf-8");
		});
	}
}
