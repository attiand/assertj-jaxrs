package com.github.attiand.assertj.jaxrs.json.asserts;

import javax.json.Json;
import javax.json.JsonStructure;

public class JsonStructureAssert extends AbstractJsonValueAssert<JsonStructureAssert> {

	private final JsonStructure actual;

	public JsonStructureAssert(JsonStructure actual) {
		super(actual, JsonStructureAssert.class);
		this.actual = actual;
	}

	protected JsonStructureAssert(JsonStructure actual, Class<?> clazz) {
		super(actual, clazz);
		this.actual = actual;
	}

	public static JsonStructureAssert assertThat(JsonStructure structure) {
		return new JsonStructureAssert(structure);
	}

	public JsonValueAssert pathValue(String path) {
		isNotNull();

		if (!Json.createPointer(path).containsValue(actual)) {
			failWithMessage("Expected json pointer expression to contain to a value");
		}

		return new JsonValueAssert(Json.createPointer(path).getValue(actual));
	}

	public JsonStructureAssert containsPath(String path) {
		isNotNull();

		if (!Json.createPointer(path).containsValue(actual)) {
			failWithMessage("Expected json pointer expression to contain a value");
		}

		return this;
	}

	public JsonStructureAssert doesNotContainPath(String path) {
		isNotNull();

		if (Json.createPointer(path).containsValue(actual)) {
			failWithMessage("Expected json pointer expression not to contain a value");
		}

		return this;
	}
}
