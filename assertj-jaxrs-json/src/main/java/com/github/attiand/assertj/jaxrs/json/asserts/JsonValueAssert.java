package com.github.attiand.assertj.jaxrs.json.asserts;

import jakarta.json.JsonValue;

public class JsonValueAssert extends AbstractJsonValueAssert<JsonValueAssert> {

	public JsonValueAssert(JsonValue actual) {
		super(actual, JsonValueAssert.class);
	}

	public static JsonValueAssert assertThat(JsonValue value) {
		return new JsonValueAssert(value);
	}
}
