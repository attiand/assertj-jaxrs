package se.uhr.assertj.jaxrs.asserts;

import jakarta.ws.rs.core.MediaType;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.MapAssert;

public class MediaTypeAssert extends AbstractAssert<MediaTypeAssert, MediaType> {

	public MediaTypeAssert(MediaType actual) {
		super(actual, MediaTypeAssert.class);
	}

	public static MediaTypeAssert assertThat(MediaType mediaType) {
		return new MediaTypeAssert(mediaType);
	}

	public MediaTypeAssert hasType(String type) {
		isNotNull();

		if (!type.equals(actual.getType())) {
			failWithMessage("Expecting content type <%s> to be equal to <%s>", actual.getType(), type);
		}

		return this;
	}

	public MediaTypeAssert hasSubType(String subType) {
		isNotNull();

		if (!subType.equals(actual.getSubtype())) {
			failWithMessage("Expecting content sub type <%s> to be equal to <%s>", actual.getType(), subType);
		}

		return this;
	}

	public MapAssert<String, String> parameters() {
		return new MapAssert<>(actual.getParameters());
	}
}
