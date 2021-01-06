package com.github.attiand.assertj.jaxrs.asserts;

import static com.github.attiand.assertj.jaxrs.asserts.ResponseAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Date;
import java.util.Locale;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ResponseAssertTest {

	private static final Date EPOCH = new Date(0);

	@Nested
	class DateHeader {

		@Test
		void shouldAcceptValidDate() {
			var response = Response.ok().header(HttpHeaders.DATE, EPOCH).build();
			assertThat(response).hasDate().hasDate(EPOCH);
		}

		@Test
		void shouldAssertInvalidDate() {
			var response = Response.ok().header(HttpHeaders.DATE, EPOCH).build();

			var cut = assertThat(response);

			var invalidDate = new Date(1);

			assertThatThrownBy(() -> {
				cut.hasDate(invalidDate);
			}).isInstanceOf(AssertionError.class).hasMessageStartingWith("Expected date to be").hasMessageContaining("but was");
		}

		@Test
		void shouldAssertNoDate() {
			var response = Response.ok().build();

			var cut = assertThat(response).hasNoDate();

			assertThatThrownBy(() -> {
				cut.hasDate();
			}).isInstanceOf(AssertionError.class).hasMessage("Expected message to have a date but did not");
		}
	}

	@Nested
	class LastModifiedDateHeader {

		@Test
		void shouldAcceptValidLastModifiedDate() {
			var response = Response.ok().header(HttpHeaders.LAST_MODIFIED, EPOCH).build();
			assertThat(response).hasLastModifiedDate().hasLastModifiedDate(EPOCH);
		}

		@Test
		void shouldAssertInvalidLastModifiedDate() {
			var response = Response.ok().header(HttpHeaders.LAST_MODIFIED, EPOCH).build();

			var cut = assertThat(response);

			var invalidDate = new Date(1);

			assertThatThrownBy(() -> {
				cut.hasLastModifiedDate(invalidDate);
			}).isInstanceOf(AssertionError.class)
					.hasMessageStartingWith("Expected last modified date to be")
					.hasMessageContaining("but was");
		}

		@Test
		void shouldAssertNoLastModified() {
			var response = Response.ok().build();

			var cut = assertThat(response).hasNoLastModifiedDate();

			assertThatThrownBy(() -> {
				cut.hasLastModifiedDate();
			}).isInstanceOf(AssertionError.class).hasMessage("Expected message to have last modified date but did not");
		}
	}

	@Nested
	class MediaTypeHeader {

		@Test
		void shouldAcceptValidMediaType() {
			var response = Response.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).build();
			assertThat(response).hasMediaType().hasMediaType(MediaType.APPLICATION_JSON_TYPE);
		}

		@Test
		void shouldAssertInvalidMediaType() {
			var response = Response.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).build();

			var cut = assertThat(response);

			assertThatThrownBy(() -> {
				cut.hasMediaType(MediaType.APPLICATION_XML_TYPE);
			}).isInstanceOf(AssertionError.class).hasMessage("Expected media type to be <application/xml> but was <application/json>");
		}

		@Test
		void shouldAssertNoMediaType() {
			var response = Response.ok().build();

			var cut = assertThat(response).hasNoMediaType();

			assertThatThrownBy(() -> {
				cut.hasMediaType();
			}).isInstanceOf(AssertionError.class).hasMessage("Expected message to have media type but did not");
		}
	}

	@Nested
	class MessageLength {

		@Test
		void shouldAcceptValidLength() {
			var response = Response.ok().header(HttpHeaders.CONTENT_LENGTH, 10).build();
			assertThat(response).hasValidLength().hasLength(10);
		}

		@Test
		void shouldAssertWrongLength() {
			var response = Response.ok().header(HttpHeaders.CONTENT_LENGTH, 10).build();

			var cut = assertThat(response);

			assertThatThrownBy(() -> {
				cut.hasLength(9);
			}).isInstanceOf(AssertionError.class).hasMessage("Expected length to be <9> but was <10>");
		}

		@Test
		void shouldAssertInvalidLength() {
			var response = Response.ok().build();

			var cut = assertThat(response);

			assertThatThrownBy(() -> {
				cut.hasValidLength();
			}).isInstanceOf(AssertionError.class).hasMessage("Expected length to be valid but was not <-1>");
		}

		@Test
		void shouldUseIntegerAssert() {
			var response = Response.ok().header(HttpHeaders.CONTENT_LENGTH, 10).build();
			assertThat(response).hasValidLength().length().isBetween(9, 11);
		}
	}

	@Nested
	class Langugage {

		private static final String LOCALE = "en-US";

		@Test
		void shouldAcceptValidLangugage() {
			var response = Response.ok().header(HttpHeaders.CONTENT_LANGUAGE, LOCALE).build();
			assertThat(response).hasLanguage(Locale.US);
		}

		@Test
		void shouldAssertInvalidLanguage() {
			var response = Response.ok().header(HttpHeaders.CONTENT_LANGUAGE, LOCALE).build();

			var cut = assertThat(response);

			assertThatThrownBy(() -> {
				cut.hasLanguage(Locale.UK);
			}).isInstanceOf(AssertionError.class).hasMessage("Expected language to be <en_GB> but was <en_US>");
		}
	}

	@Nested
	class Location {

		private static final String LOCATION = "http://www.example.com/index.html";

		@Test
		void shouldAcceptValidLocation() {
			var response = Response.ok().header(HttpHeaders.LOCATION, LOCATION).build();
			assertThat(response).hasLocation().hasLocation(LOCATION);
		}

		@Test
		void shouldAcceptInValidLocation() {
			var response = Response.ok().build();
			assertThat(response).hasNoLocation();
		}

		@Test
		void shouldAssertInvalidLocation() {
			var response = Response.ok().header(HttpHeaders.LOCATION, LOCATION).build();

			var cut = assertThat(response);

			assertThatThrownBy(() -> {
				cut.hasLocation("http://www.example.com");
			}).isInstanceOf(AssertionError.class)
					.hasMessage("Expected location to be <http://www.example.com> but was <http://www.example.com/index.html>");
		}
	}
}
