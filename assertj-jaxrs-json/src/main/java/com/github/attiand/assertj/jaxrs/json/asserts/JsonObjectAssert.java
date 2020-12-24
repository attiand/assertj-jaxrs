package com.github.attiand.assertj.jaxrs.json.asserts;

import javax.json.Json;
import javax.json.JsonObject;

import org.assertj.core.api.AbstractAssert;

public class JsonObjectAssert extends AbstractAssert<JsonObjectAssert, JsonObject> {

	public JsonObjectAssert(JsonObject actual) {
		super(actual, JsonObjectAssert.class);
	}

	public static JsonObjectAssert assertThat(JsonObject json) {
		return new JsonObjectAssert(json);
	}

	public JsonValueAssert path(String path) {
		return new JsonValueAssert(Json.createPointer(path).getValue(actual));
	}
}
