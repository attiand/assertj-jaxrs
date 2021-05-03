package com.github.attiand.assertj.jaxrs.asserts;

import javax.json.JsonStructure;

import org.assertj.core.api.AbstractAssert;

public class JsonStructureAssert extends AbstractAssert<JsonStructureAssert, JsonStructure> {

	public JsonStructureAssert(JsonStructure actual) {
		super(actual, JsonStructureAssert.class);
	}
}
