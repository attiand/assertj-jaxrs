package com.github.attiand.assertj.jaxrs.json.asserts;

import javax.json.Json;
import javax.json.JsonArray;

import org.assertj.core.api.AbstractAssert;

public class JsonArrayAssert extends AbstractAssert<JsonArrayAssert, JsonArray> {

	public JsonArrayAssert(JsonArray actual) {
		super(actual, JsonArrayAssert.class);
	}

	public static JsonArrayAssert assertThat(JsonArray json) {
		return new JsonArrayAssert(json);
	}

	public JsonValueAssert path(String path) {
		return new JsonValueAssert(Json.createPointer(path).getValue(actual));
	}
}
