package se.uhr.assertj.jaxrs.json.asserts;

import static se.uhr.assertj.jaxrs.json.asserts.JsonObjectAssert.assertThat;
import static org.assertj.core.api.Assertions.within;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import org.junit.jupiter.api.Test;

class JsonObjectAssertTest {

	private static final JsonObject JSON_OBJ = Json.createObjectBuilder()
			.add("firstName", "John")
			.add("lastName", "Smith")
			.add("age", 25)
			.add("double", 2.5d)
			.add("male", true)
			.add("female", false)
			.add("address", Json.createObjectBuilder().add("streetAddress", "21 2nd Street").add("city", "New York").add("state", "NY"))
			.add("phoneNumber",
					Json.createArrayBuilder()
							.add(Json.createObjectBuilder().add("number", "212 555-1234"))
							.add(Json.createObjectBuilder().add("number", "646 555-4567")))
			.build();

	@Test
	void shouldAcceptJsonPointerAsObject() {
		assertThat(JSON_OBJ).pathValue("/phoneNumber/0").satisfies(o -> {
			assertThat(o.asJsonObject()).pathValue("/number").asString().isEqualTo("212 555-1234");
		});
	}

	@Test
	void shouldAcceptJsonPointerAsArray() {
		assertThat(JSON_OBJ).pathValue("/phoneNumber").satisfies(o -> {
			assertThat(o.asJsonArray()).pathValue("/1").asJsonObject().pathValue("/number").asString().isEqualTo("646 555-4567");
		});
	}

	@Test
	void shouldAcceptJsonPointerAsString() {
		assertThat(JSON_OBJ).pathValue("/phoneNumber/0/number").asString().isEqualTo("212 555-1234");
	}

	@Test
	void shouldAcceptJsonPointerAsInteger() {
		assertThat(JSON_OBJ).pathValue("/age").asInteger().isEqualTo(25);
	}

	@Test
	void shouldAcceptJsonPointerAsDouble() {
		assertThat(JSON_OBJ).pathValue("/double").asDouble().isEqualTo(2.5, within(0.1));
	}

	@Test
	void shouldAcceptJsonPointerAsTrue() {
		assertThat(JSON_OBJ).pathValue("/male").asBoolean().isTrue();
	}

	@Test
	void shouldAcceptJsonPointerAsFalse() {
		assertThat(JSON_OBJ).pathValue("/female").asBoolean().isFalse();
	}
}
