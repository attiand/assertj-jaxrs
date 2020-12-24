package com.github.attiand.assertj.jaxrs.asserts;

import javax.ws.rs.core.MediaType;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.MapAssert;

public class ContentTypeHeaderAssert extends AbstractAssert<ContentTypeHeaderAssert, MediaType> {

	public ContentTypeHeaderAssert(MediaType actual) {
		super(actual, ContentTypeHeaderAssert.class);
	}

	public static ContentTypeHeaderAssert assertThat(MediaType mediaType) {
		return new ContentTypeHeaderAssert(mediaType);
	}

	public static ContentTypeHeaderAssert assertThat(Object mediaType) {
		if (!(mediaType instanceof String)) {
			throw new AssertionError("Content type value is not a string");
		}

		String contentTypeValue = (String) mediaType;

		try {
			return new ContentTypeHeaderAssert(MediaType.valueOf(contentTypeValue));
		} catch (IllegalArgumentException e) {
			throw new AssertionError("Can't parse content type: contentTypeValue", e);
		}
	}

	public ContentTypeHeaderAssert hasType(String type) {
		isNotNull();

		if (!type.equals(actual.getType())) {
			failWithMessage("Expecting content type <%s> to be equals to <%s>", actual.getType(), type);
		}

		return this;
	}

	public ContentTypeHeaderAssert hasSubType(String subType) {
		isNotNull();

		if (!subType.equals(actual.getSubtype())) {
			failWithMessage("Expecting content sub type <%s> to be equals to <%s>", actual.getType(), subType);
		}

		return this;
	}

	public MapAssert<String, String> parameters() {
		return new MapAssert<>(actual.getParameters());
	}
}
