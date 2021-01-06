package com.github.attiand.assertj.jaxrs.asserts;

import static com.github.attiand.assertj.jaxrs.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;

import java.net.URI;

import javax.ws.rs.core.Link;

import org.junit.jupiter.api.Test;

class LinkAssertTest {

	private static final Link LINK =
			Link.valueOf("<http://localhost/root/customers/1234>; rel=\"update\"; type=\"text/plain\"; myparam=\"myvalue\"");

	@Test
	void shouldAcceptValidValues() {
		assertThat(LINK).hasRel("update").hasType("text/plain").hasUri(URI.create("http://localhost/root/customers/1234"));
	}

	@Test
	void shouldAcceptValidValuesUriAsString() {
		assertThat(LINK).hasRel("update").hasType("text/plain").hasUri("http://localhost/root/customers/1234");
	}

	@Test
	void shouldAcceptValidParameter() {
		assertThat(LINK).parameters().contains(entry("myparam", "myvalue"));
	}

	@Test
	void shouldAssertInvalidRel() {
		var la = assertThat(LINK);

		assertThatThrownBy(() -> {
			la.hasRel("faultyvalue");
		}).isInstanceOf(AssertionError.class).hasMessageContaining("Expected link rel to be <faultyvalue> but was <update>");
	}
}
