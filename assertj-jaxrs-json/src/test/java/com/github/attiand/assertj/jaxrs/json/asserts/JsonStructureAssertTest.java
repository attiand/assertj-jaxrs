package com.github.attiand.assertj.jaxrs.json.asserts;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.jupiter.api.Test;

class JsonStructureAssertTest {

	private static final JsonObject JSON_OBJ = Json.createObjectBuilder().add("firstName", "John").build();

	private static final JsonArray JSON_ARRAY = Json.createArrayBuilder().add("one").add("two").add("tree").build();

	@Test
	void shouldAcceptJsonObject() {
		JsonStructureAssert.assertThat(JSON_OBJ).path("/firstName").asString().isEqualTo("John");
	}

	@Test
	void shouldAcceptJsonArray() {
		JsonStructureAssert.assertThat(JSON_ARRAY).path("/0").asString().isEqualTo("one");
		JsonStructureAssert.assertThat(JSON_ARRAY).path("/2").asString().isEqualTo("tree");
	}

}
