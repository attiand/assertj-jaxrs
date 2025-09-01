package se.uhr.assertj.jaxrs.json.asserts;

import static se.uhr.assertj.jaxrs.json.asserts.JsonValueAssert.assertThat;
import static org.assertj.core.api.Assertions.within;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.json.JsonValue.ValueType;

import org.junit.jupiter.api.Test;

class JsonValueAssertTest {

	@Test
	void shouldAcceptJsonString() {
		JsonValue value = Json.createValue("myvalue");
		assertThat(value).asString().isEqualTo("myvalue");
	}

	@Test
	void shouldAcceptJsonNumber() {
		JsonValue value = Json.createValue(77);
		assertThat(value).asInteger().isEqualTo(77);
	}

	@Test
	void shouldAcceptJsonDouble() {
		JsonValue value = Json.createValue(2.3);
		assertThat(value).asDouble().isEqualTo(2.3, within(0.1));
	}

	@Test
	void shouldAcceptJsonObject() {
		JsonObject value = Json.createObjectBuilder().add("firstName", "John").build();
		assertThat(value).asJsonObject().pathValue("/firstName").asString().isEqualTo("John");
	}

	@Test
	void shouldAcceptJsonNonNullValue() {
		JsonValue value = Json.createValue("myvalue");
		assertThat(value).isNotNull().hasType(ValueType.STRING).asString().isEqualTo("myvalue");
	}

	@Test
	void shouldHandleNullValue() {
		assertThat(JsonValue.NULL).isNull();
		assertThat(JsonValue.NULL).hasType(ValueType.NULL);
	}
}
