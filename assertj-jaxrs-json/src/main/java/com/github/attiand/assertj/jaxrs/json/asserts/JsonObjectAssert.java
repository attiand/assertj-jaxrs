package com.github.attiand.assertj.jaxrs.json.asserts;

import javax.json.JsonObject;

public class JsonObjectAssert extends JsonStructureAssert {

	public JsonObjectAssert(JsonObject actual) {
		super(actual, JsonObjectAssert.class);
	}

	public static JsonObjectAssert assertThat(JsonObject json) {
		return new JsonObjectAssert(json);
	}
}
