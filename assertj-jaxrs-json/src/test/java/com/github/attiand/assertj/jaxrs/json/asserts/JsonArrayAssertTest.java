package com.github.attiand.assertj.jaxrs.json.asserts;

import javax.json.Json;
import javax.json.JsonArray;

import org.junit.jupiter.api.Test;

public class JsonArrayAssertTest {

	private static final JsonArray JSON_ARRAY = Json.createArrayBuilder().add("one").add("two").add("tree").build();

	@Test
	void shouldAcceptJsonArray() {
		JsonArrayAssert.assertThat(JSON_ARRAY).path("/0").asString().isEqualTo("one");
		JsonArrayAssert.assertThat(JSON_ARRAY).path("/2").asString().isEqualTo("tree");
	}
}
