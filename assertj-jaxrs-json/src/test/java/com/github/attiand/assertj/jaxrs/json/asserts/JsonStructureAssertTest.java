package com.github.attiand.assertj.jaxrs.json.asserts;

import static com.github.attiand.assertj.jaxrs.json.asserts.JsonStructureAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

import org.junit.jupiter.api.Test;

class JsonStructureAssertTest {

	private static final JsonObject JSON_OBJ = Json.createObjectBuilder().add("firstName", "John").build();

	private static final JsonArray JSON_ARRAY = Json.createArrayBuilder().add("one").add("two").add("tree").build();

	@Test
	void shouldAcceptJsonObject() {
		assertThat(JSON_OBJ).pathValue("/firstName").asString().isEqualTo("John");
	}

	@Test
	void shouldAcceptJsonArray() {
		assertThat(JSON_ARRAY).pathValue("/0").asString().isEqualTo("one");
		assertThat(JSON_ARRAY).pathValue("/2").asString().isEqualTo("tree");
	}

	@Test
	void shouldFailOnNonExistingPath() {
		assertThatThrownBy(() -> {
			assertThat(JSON_OBJ).pathValue("/nonexistent");
		}).isInstanceOf(AssertionError.class).hasMessageContaining("Expected json pointer expression to contain to a value");

	}

	@Test
	void shouldCheckExistingPath() {
		assertThat(JSON_OBJ).containsPath("/firstName");
	}

	@Test
	void shouldCheckForNonExistingPath() {
		assertThat(JSON_OBJ).doesNotContainPath("/nonexistent");
	}

}
