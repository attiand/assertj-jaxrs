package com.github.attiand.assertj.jaxrs.json.asserts;

import javax.json.Json;
import javax.json.JsonStructure;

import org.assertj.core.api.AbstractAssert;

public class JsonStructureAssert extends AbstractAssert<JsonStructureAssert, JsonStructure> {

	public JsonStructureAssert(JsonStructure actual) {
		super(actual, JsonStructureAssert.class);
	}

	public static JsonStructureAssert assertThat(JsonStructure structure) {
		return new JsonStructureAssert(structure);
	}

	public JsonValueAssert path(String path) {
		return new JsonValueAssert(Json.createPointer(path).getValue(actual));
	}
}
