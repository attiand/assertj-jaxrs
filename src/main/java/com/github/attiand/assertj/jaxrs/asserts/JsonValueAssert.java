package com.github.attiand.assertj.jaxrs.asserts;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.BooleanAssert;
import org.assertj.core.api.DoubleAssert;
import org.assertj.core.api.IntegerAssert;
import org.assertj.core.api.StringAssert;

public class JsonValueAssert extends AbstractAssert<JsonValueAssert, JsonValue> {

	public JsonValueAssert(JsonValue actual) {
		super(actual, JsonValueAssert.class);
	}

	public static JsonValueAssert assertThat(JsonValue value) {
		return new JsonValueAssert(value);
	}

	@Override
	public StringAssert asString() {
		isNotNull();

		if (actual.getValueType() != ValueType.STRING) {
			failWithMessage("Expected json value to have type STRING but was <%s>", actual.getValueType());
		}

		JsonString value = (JsonString) actual;

		return new StringAssert(value.getString());
	}

	public IntegerAssert asInteger() {
		isNotNull();

		if (actual.getValueType() != ValueType.NUMBER) {
			failWithMessage("Expected json value to have type NUMBER but was <%s>", actual.getValueType());
		}

		JsonNumber value = (JsonNumber) actual;

		return new IntegerAssert(value.intValue());
	}

	public DoubleAssert asDouble() {
		isNotNull();

		if (actual.getValueType() != ValueType.NUMBER) {
			failWithMessage("Expected json value to have type NUMBER but was <%s>", actual.getValueType());
		}

		JsonNumber value = (JsonNumber) actual;

		return new DoubleAssert(value.doubleValue());
	}

	public BooleanAssert asBoolean() {
		isNotNull();

		if (!(actual.getValueType() == ValueType.TRUE || actual.getValueType() == ValueType.FALSE)) {
			failWithMessage("Expected json value to have type TRUE or FALSE but was <%s>", actual.getValueType());
		}

		return new BooleanAssert(actual.getValueType() == ValueType.TRUE);
	}
}
