package com.github.attiand.assertj.jaxrs.json.asserts;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.json.JsonArray;
import javax.json.JsonValue;

import org.assertj.core.api.AbstractListAssert;
import org.assertj.core.api.ListAssert;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.internal.Iterables;

public class JsonArrayAssert extends JsonStructureAssert {

	private final JsonArray actual;

	protected Iterables iterables = Iterables.instance();

	public JsonArrayAssert(JsonArray actual) {
		super(actual, JsonArrayAssert.class);
		this.actual = actual;
	}

	public static JsonArrayAssert assertThat(JsonArray json) {
		return new JsonArrayAssert(json);
	}

	public JsonArrayAssert allSatisfy(Consumer<? super JsonValue> requirements) {
		iterables.assertAllSatisfy(info, actual, requirements);
		return this;
	}

	public <V, K extends JsonValue> AbstractListAssert<?, List<? extends V>, V, ObjectAssert<V>> extracting(Function<K, V> extractor) {
		var tmp = actual.getValuesAs(extractor);
		return new ListAssert<>(tmp);
	}

	void isEmpty() {
		iterables.assertEmpty(info, actual);
	}

	public JsonArrayAssert isNotEmpty() {
		iterables.assertNotEmpty(info, actual);
		return this;
	}

	public JsonArrayAssert hasSize(int expected) {
		iterables.assertHasSize(info, actual, expected);
		return this;
	}

	public JsonArrayAssert hasSizeGreaterThan(int boundary) {
		iterables.assertHasSizeGreaterThan(info, actual, boundary);
		return this;
	}

	public JsonArrayAssert hasSizeGreaterThanOrEqualTo(int boundary) {
		iterables.assertHasSizeGreaterThanOrEqualTo(info, actual, boundary);
		return this;
	}

	public JsonArrayAssert hasSizeLessThan(int boundary) {
		iterables.assertHasSizeLessThan(info, actual, boundary);
		return this;

	}

	public JsonArrayAssert hasSizeLessThanOrEqualTo(int boundary) {
		iterables.assertHasSizeLessThanOrEqualTo(info, actual, boundary);
		return this;

	}

	public JsonArrayAssert hasSizeBetween(int lowerBoundary, int higherBoundary) {
		iterables.assertHasSizeBetween(info, actual, lowerBoundary, higherBoundary);
		return this;
	}
}
