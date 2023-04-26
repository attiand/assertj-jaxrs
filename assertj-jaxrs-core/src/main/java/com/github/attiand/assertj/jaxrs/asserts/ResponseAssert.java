package com.github.attiand.assertj.jaxrs.asserts;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.Response.Status.Family;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.IntegerAssert;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.api.StringAssert;

import com.github.attiand.assertj.jaxrs.ext.EntityExtensionServiceLoader;
import com.github.attiand.assertj.jaxrs.ext.EntityType;

public class ResponseAssert extends AbstractAssert<ResponseAssert, Response> {

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

	public <U> ObjectAssert<U> entityAs(EntityType<U> type) {
		try (InputStream is = actual.readEntity(InputStream.class)) {
			return new ObjectAssert<>(EntityExtensionServiceLoader.load(type, is));
		} catch (IOException e) {
			throw new AssertionError("Could not read entity", e);
		}
	}

	public <U> ObjectAssert<U> entityAs(Class<U> clazz) {
		return new ObjectAssert<>(actual.readEntity(clazz));
	}

	public <U> ObjectAssert<U> entityAs(GenericType<U> entityType) {
		return new ObjectAssert<>(actual.readEntity(entityType));
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

	public ResponseAssert hasLocation() {
		isNotNull();

		if (actual.getLocation() == null) {
			failWithMessage("Expected message to have location but did not");
		}

		return this;
	}

	public ResponseAssert hasNoLocation() {
		isNotNull();

		if (actual.getLocation() != null) {
			failWithMessage("Expected message to have no location but did");
		}

		return this;
	}

	public ResponseAssert hasLocation(URI location) {
		isNotNull();

		if (!location.equals(actual.getLocation())) {
			failWithMessage("Expected location to be <%s> but was <%s>", location, actual.getLocation());
		}

		return this;
	}

	public ResponseAssert hasLocation(String location) {
		return hasLocation(URI.create(location));
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

	public ResponseAssert linksSatisfies(Consumer<Set<Link>> requirements) {
		isNotNull();

		requirements.accept(actual.getLinks());

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

	public ResponseAssert hasLanguage(Locale language) {
		isNotNull();

		if (!language.equals(actual.getLanguage())) {
			failWithMessage("Expected language to be <%s> but was <%s>", language, actual.getLanguage());
		}

		return this;
	}
}
