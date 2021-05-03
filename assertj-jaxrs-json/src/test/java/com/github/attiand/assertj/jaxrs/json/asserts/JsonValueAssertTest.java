package com.github.attiand.assertj.jaxrs.json.asserts;

import static org.assertj.core.api.Assertions.within;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

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

	@Test
	void shouldAcceptJsonObject() {
		JsonObject value = Json.createObjectBuilder().add("firstName", "John").build();
		JsonValueAssert.assertThat(value).asJsonObject().path("/firstName").asString().isEqualTo("John");
	}

	@Test
	void shouldAcceptJsonNonNullValue() {
		JsonValue value = Json.createValue("myvalue");
		JsonValueAssert.assertThat(value).isNotNull().hasType(ValueType.STRING).asString().isEqualTo("myvalue");
	}

	@Test
	void shouldHandleNullValue() {
		JsonValueAssert.assertThat(JsonValue.NULL).isNull();
		JsonValueAssert.assertThat(JsonValue.NULL).hasType(ValueType.NULL);
	}
}
