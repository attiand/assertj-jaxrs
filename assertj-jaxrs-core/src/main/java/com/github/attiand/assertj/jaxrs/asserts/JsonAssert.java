package com.github.attiand.assertj.jaxrs.asserts;

import javax.json.JsonObject;

import org.assertj.core.api.AbstractAssert;

public class JsonAssert extends AbstractAssert<JsonAssert, JsonObject> {

	public JsonAssert(JsonObject actual) {
		super(actual, JsonAssert.class);
	}
}
