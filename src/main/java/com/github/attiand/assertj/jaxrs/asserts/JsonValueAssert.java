package com.github.attiand.assertj.jaxrs.asserts;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.IntegerAssert;
import org.assertj.core.api.StringAssert;

public class JsonValueAssert extends AbstractAssert<JsonValueAssert, JsonValue> {

	public JsonValueAssert(JsonValue actual) {
		super(actual, JsonValueAssert.class);
	}

	@Override
	public StringAssert asString() {
		isNotNull();

		if (actual.getValueType() != ValueType.STRING) {
			failWithMessage("Expected status code to be STRING but was <%s>", actual.getValueType());
		}

		JsonString value = (JsonString) actual;

		return new StringAssert(value.getString());
	}

	public IntegerAssert asInteger() {
		isNotNull();

		if (actual.getValueType() != ValueType.NUMBER) {
			failWithMessage("Expected status code to be STRING but was <%s>", actual.getValueType());
		}

		JsonNumber value = (JsonNumber) actual;

		return new IntegerAssert(value.intValue());
	}
}
