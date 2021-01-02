package com.github.attiand.assertj.jaxrs.asserts;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Consumer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.IntegerAssert;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.api.StringAssert;

public class ResponseAssert extends AbstractAssert<ResponseAssert, Response> {

	private JsonReaderFactory jsonFactory = Json.createReaderFactory(Collections.emptyMap());

	public ResponseAssert(Response actual) {
		super(actual, ResponseAssert.class);
	}

	public static ResponseAssert assertThat(Response response) {
		return new ResponseAssert(response);
	}

	public ResponseAssert hasStatusCode(int statusCode) {
		isNotNull();

		if (actual.getStatus() != statusCode) {
			failWithMessage("Expected status code to be <%s> but was <%s>", statusCode, actual.getStatus());
		}

		return this;
	}

	public ResponseAssert hasStatusCode(Status status) {
		isNotNull();

		if (actual.getStatusInfo() != status) {
			failWithMessage("Expected status code to be <%s> but was <%s>", status, actual.getStatus());
		}

		return this;
	}

	public ResponseAssert hasStatusCodeFamily(Family family) {
		isNotNull();

		if (actual.getStatusInfo().getFamily() != family) {
			failWithMessage("Expected status code family to be <%s> but was <%s>", family, actual.getStatusInfo().getFamily());
		}

		return this;
	}

	public StringAssert entityAsText() {
		return new StringAssert(actual.readEntity(String.class));
	}

	public JsonAssert entityAsJson() {
		try (InputStream is = actual.readEntity(InputStream.class); JsonReader reader = jsonFactory.createReader(is)) {
			JsonObject jsonObject = reader.readObject();
			return new JsonAssert(jsonObject);
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not close entity", e);
		}
	}

	public <U> ObjectAssert<U> entityAs(Class<U> clazz) {
		return new ObjectAssert<>(actual.readEntity(clazz));
	}

	public ResponseAssert hasLength(int length) {
		isNotNull();

		if (actual.getLength() != length) {
			failWithMessage("Expected length to be <%s> but was <%s>", length, actual.getLength());
		}

		return this;
	}

	public ResponseAssert hasValidLength() {
		isNotNull();

		if (actual.getLength() == -1) {
			failWithMessage("Expected length to be valid but was not <%s>", actual.getLength());
		}

		return this;
	}

	public IntegerAssert length() {
		return new IntegerAssert(actual.getLength());
	}

	public ResponseAssert hasCookie(String cookie) {
		isNotNull();

		if (!actual.getCookies().containsKey(cookie)) {
			failWithMessage("Expected cookies <%s> to contain cookie <%s>", actual.getCookies(), cookie);
		}

		return this;
	}

	public ResponseAssert cookiesSatisfies(Consumer<Map<String, NewCookie>> requirements) {
		isNotNull();

		requirements.accept(actual.getCookies());

		return this;
	}

	public ResponseAssert hasEntity() {
		isNotNull();

		if (!actual.hasEntity()) {
			failWithMessage("Expected message to contain entity but did not");
		}

		return this;
	}

	public ResponseAssert hasNoEntity() {
		isNotNull();

		if (actual.hasEntity()) {
			failWithMessage("Expected message to contain no entity but did");
		}

		return this;
	}

	public ResponseAssert hasHeader(String headerName) {
		isNotNull();

		if (actual.getHeaderString(headerName) == null) {
			failWithMessage("Expected header <%s> to contain header <%s>", actual.getHeaders(), headerName);
		}

		return this;
	}

	public ResponseAssert headersSatisfies(Consumer<MultivaluedMap<String, Object>> requirements) {
		isNotNull();

		requirements.accept(actual.getHeaders());

		return this;
	}

	public ResponseAssert hasMediaType() {
		isNotNull();

		if (actual.getMediaType() == null) {
			failWithMessage("Expected message to have media type but did not");
		}

		return this;
	}

	public ResponseAssert hasNoMediaType() {
		isNotNull();

		if (actual.getMediaType() != null) {
			failWithMessage("Expected message to have no media type but did");
		}

		return this;
	}

	public ResponseAssert hasMediaType(MediaType mediaType) {
		isNotNull();

		if (!mediaType.equals(actual.getMediaType())) {
			failWithMessage("Expected media type to be <%s> but was <%s>", mediaType, actual.getMediaType());
		}

		return this;
	}

	public ResponseAssert mediaTypeSatisfies(Consumer<MediaType> requirements) {
		isNotNull();

		requirements.accept(actual.getMediaType());

		return this;
	}

	public ResponseAssert hasDate() {
		isNotNull();

		if (actual.getDate() == null) {
			failWithMessage("Expected message to have a date but did not");
		}

		return this;
	}

	public ResponseAssert hasNoDate() {
		isNotNull();

		if (actual.getDate() != null) {
			failWithMessage("Expected message to have no date but did");
		}

		return this;
	}

	public ResponseAssert hasDate(Date date) {
		isNotNull();

		if (!date.equals(actual.getDate())) {
			failWithMessage("Expected date to be <%s> but was <%s>", date, actual.getDate());
		}

		return this;
	}

	public ResponseAssert hasLastModifiedDate() {
		isNotNull();

		if (actual.getLastModified() == null) {
			failWithMessage("Expected message to have last modified date but did not");
		}

		return this;
	}

	public ResponseAssert hasNoLastModifiedDate() {
		isNotNull();

		if (actual.getLastModified() != null) {
			failWithMessage("Expected message to have no last modified date but did");
		}

		return this;
	}

	public ResponseAssert hasLastModifiedDate(Date date) {
		isNotNull();

		if (!date.equals(actual.getLastModified())) {
			failWithMessage("Expected last modified date to be <%s> but was <%s>", date, actual.getLastModified());
		}

		return this;
	}
}
