package com.github.attiand.assertj.jaxrs.asserts;

import static org.assertj.core.api.Assertions.within;

import javax.json.Json;
import javax.json.JsonValue;

import org.junit.jupiter.api.Test;

class JsonValueAssertTest {

	@Test
	void shouldAcceptJsonString() {
		JsonValue value = Json.createValue("myvalue");
		JsonValueAssert.assertThat(value).asString().isEqualTo("myvalue");
	}

	@Test
	void shouldAcceptJsonNumber() {
		JsonValue value = Json.createValue(77);
		JsonValueAssert.assertThat(value).asInteger().isEqualTo(77);
	}

	@Test
	void shouldAcceptJsonDouble() {
		JsonValue value = Json.createValue(2.3);
		JsonValueAssert.assertThat(value).asDouble().isEqualTo(2.3, within(0.1));
	}
}
