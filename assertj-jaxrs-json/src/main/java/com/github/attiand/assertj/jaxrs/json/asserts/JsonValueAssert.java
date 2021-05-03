package com.github.attiand.assertj.jaxrs.json.asserts;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
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
	public void isNull() {
		if (actual.getValueType() != ValueType.NULL) {
			failWithMessage("Expected json value not to be NULL, but was <%s>", actual.getValueType());
		}
	}

	@Override
	public JsonValueAssert isNotNull() {
		if (actual.getValueType() == ValueType.NULL) {
			failWithMessage("Expected json value not to be NULL, but was");
		}

		return this;
	}

	public JsonValueAssert hasType(ValueType type) {
		if (actual.getValueType() != type) {
			failWithMessage("Expected json value to have type <%s> but was <%s>", type, actual.getValueType());
		}

		return this;
	}

	@Override
	public StringAssert asString() {
		isNotNull();

		if (actual.getValueType() != ValueType.STRING) {
			failWithMessage("Expected json value to have type STRING but was <%s>", actual.getValueType());
		}

		var value = (JsonString) actual;

		return new StringAssert(value.getString());
	}

	public IntegerAssert asInteger() {
		isNotNull();

		if (actual.getValueType() != ValueType.NUMBER) {
			failWithMessage("Expected json value to have type NUMBER but was <%s>", actual.getValueType());
		}

		var value = (JsonNumber) actual;

		return new IntegerAssert(value.intValue());
	}

	public DoubleAssert asDouble() {
		isNotNull();

		if (actual.getValueType() != ValueType.NUMBER) {
			failWithMessage("Expected json value to have type NUMBER but was <%s>", actual.getValueType());
		}

		var value = (JsonNumber) actual;

		return new DoubleAssert(value.doubleValue());
	}

	public BooleanAssert asBoolean() {
		isNotNull();

		if (!(actual.getValueType() == ValueType.TRUE || actual.getValueType() == ValueType.FALSE)) {
			failWithMessage("Expected json value to have type TRUE or FALSE but was <%s>", actual.getValueType());
		}

		return new BooleanAssert(actual.getValueType() == ValueType.TRUE);
	}

	public JsonObjectAssert asJsonObject() {
		isNotNull();

		if (actual.getValueType() != ValueType.OBJECT) {
			failWithMessage("Expected json value to have type OBJECT but was <%s>", actual.getValueType());
		}

		var value = (JsonObject) actual;

		return new JsonObjectAssert(value);
	}

	public JsonArrayAssert asJsonArray() {
		isNotNull();

		if (actual.getValueType() != ValueType.ARRAY) {
			failWithMessage("Expected json value to have type ARRAY but was <%s>", actual.getValueType());
		}

		var value = (JsonArray) actual;

		return new JsonArrayAssert(value);
	}
}
