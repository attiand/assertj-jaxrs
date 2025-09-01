package se.uhr.assertj.jaxrs.json.asserts;

import static se.uhr.assertj.jaxrs.json.asserts.JsonArrayAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonString;

import org.junit.jupiter.api.Test;

public class JsonArrayAssertTest {

	private static final JsonArray JSON_ARRAY = Json.createArrayBuilder().add("one").add("two").add("tree").build();

	@Test
	void shouldAcceptJsonArray() {
		assertThat(JSON_ARRAY).pathValue("/0").asString().isEqualTo("one");
		assertThat(JSON_ARRAY).pathValue("/2").asString().isEqualTo("tree");
	}

	@Test
	void shouldExtractValue() {
		assertThat(JSON_ARRAY).extracting(JsonString::getString).hasSize(3).containsExactly("one", "two", "tree");
	}

	@Test
	void shouldAcceptAllSatisfy() {
		assertThat(JSON_ARRAY).extracting(JsonString::getString).isNotEmpty().allSatisfy(v -> {
			assertThat(v).hasSizeBetween(3, 4);
		});
	}

	@Test
	void shouldAssertAllSatisfy() {
		assertThatThrownBy(() -> assertThat(JSON_ARRAY).extracting(JsonString::getString).isNotEmpty().allSatisfy(v -> {
			assertThat(v).hasSizeBetween(4, 5);
		})).isInstanceOf(AssertionError.class);
	}

	@Test
	void shouldHandleArraySizeAssertions() {
		assertThat(JSON_ARRAY).pathValue("")
				.asJsonArray()
				.isNotEmpty()
				.hasSize(3)
				.hasSizeGreaterThan(2)
				.hasSizeGreaterThanOrEqualTo(3)
				.hasSizeLessThan(4)
				.hasSizeLessThanOrEqualTo(3)
				.hasSizeBetween(2, 4);
	}

	@Test
	void shouldAcceptEmpty() {
		assertThat(Json.createArrayBuilder().build()).pathValue("").asJsonArray().isEmpty();
	}

	@Test
	void shouldAssertWrongSize() {
		assertThatThrownBy(() -> assertThat(JSON_ARRAY).pathValue("").asJsonArray().hasSize(4)).isInstanceOf(AssertionError.class)
				.hasMessageContaining("Expected size: 4 but was: 3 in:");
	}
}
