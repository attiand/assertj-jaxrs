package com.github.attiand.assertj.jaxrs.asserts;

import static com.github.attiand.assertj.jaxrs.asserts.ResponseAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Date;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ResponseAssertTest {

	@Nested
	class DateHeader {

		@Test
		void shouldAcceptDate() {
			Response response = Response.ok().header(HttpHeaders.DATE, new Date(0)).build();
			assertThat(response).hasDate().hasDate(new Date(0));
		}

		@Test
		void shouldAssertWrongDate() {
			Response response = Response.ok().header(HttpHeaders.DATE, new Date(0)).build();

			ResponseAssert cut = assertThat(response);

			Date wrongDate = new Date(1);

			assertThatThrownBy(() -> {
				cut.hasDate(wrongDate);
			}).isInstanceOf(AssertionError.class).hasMessageStartingWith("Expected date to be").hasMessageContaining("but was");
		}

		@Test
		void shouldAssertNoDate() {
			Response response = Response.ok().build();

			ResponseAssert cut = assertThat(response).hasNoDate();

			assertThatThrownBy(() -> {
				cut.hasDate();
			}).isInstanceOf(AssertionError.class).hasMessage("Expected message to have a date but did not");
		}
	}

	@Nested
	class LastModifiedDateHeader {

		@Test
		void shouldAcceptLastModifiedDate() {
			Response response = Response.ok().header(HttpHeaders.LAST_MODIFIED, new Date(0)).build();
			assertThat(response).hasLastModifiedDate().hasLastModifiedDate(new Date(0));
		}

		@Test
		void shouldAssertWrongLastModifiedDate() {
			Response response = Response.ok().header(HttpHeaders.LAST_MODIFIED, new Date(0)).build();

			ResponseAssert cut = assertThat(response);

			Date wrongDate = new Date(1);

			assertThatThrownBy(() -> {
				cut.hasLastModifiedDate(wrongDate);
			}).isInstanceOf(AssertionError.class)
					.hasMessageStartingWith("Expected last modified date to be")
					.hasMessageContaining("but was");
		}

		@Test
		void shouldAssertNoLastModified() {
			Response response = Response.ok().build();

			ResponseAssert cut = assertThat(response).hasNoLastModifiedDate();

			assertThatThrownBy(() -> {
				cut.hasLastModifiedDate();
			}).isInstanceOf(AssertionError.class).hasMessage("Expected message to have last modified date but did not");
		}
	}

	@Nested
	class MediaTypeHeader {

		@Test
		void shouldAcceptMediaType() {
			Response response = Response.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).build();
			assertThat(response).hasMediaType().hasMediaType(MediaType.APPLICATION_JSON_TYPE);
		}

		@Test
		void shouldAssertWrongMediaType() {
			Response response = Response.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).build();

			ResponseAssert cut = assertThat(response);

			assertThatThrownBy(() -> {
				cut.hasMediaType(MediaType.APPLICATION_XML_TYPE);
			}).isInstanceOf(AssertionError.class).hasMessage("Expected media type to be <application/xml> but was <application/json>");
		}

		@Test
		void shouldAssertNoMediaType() {
			Response response = Response.ok().build();

			ResponseAssert cut = assertThat(response).hasNoMediaType();

			assertThatThrownBy(() -> {
				cut.hasMediaType();
			}).isInstanceOf(AssertionError.class).hasMessage("Expected message to have media type but did not");
		}
	}

	@Nested
	class MessageLength {

		@Test
		void shouldAcceptValidLength() {
			Response response = Response.ok().header(HttpHeaders.CONTENT_LENGTH, 10).build();
			assertThat(response).hasValidLength().hasLength(10);
		}

		@Test
		void shouldAssertWrongWrongLength() {
			Response response = Response.ok().header(HttpHeaders.CONTENT_LENGTH, 10).build();

			ResponseAssert cut = assertThat(response);

			assertThatThrownBy(() -> {
				cut.hasLength(9);
			}).isInstanceOf(AssertionError.class).hasMessage("Expected length to be <9> but was <10>");
		}

		@Test
		void shouldAssertInvalidLength() {
			Response response = Response.ok().build();

			ResponseAssert cut = assertThat(response);

			assertThatThrownBy(() -> {
				cut.hasValidLength();
			}).isInstanceOf(AssertionError.class).hasMessage("Expected length to be valid but was not <-1>");
		}

		@Test
		void shouldUseIntegerAssert() {
			Response response = Response.ok().header(HttpHeaders.CONTENT_LENGTH, 10).build();
			assertThat(response).hasValidLength().length().isBetween(9, 11);
		}
	}
}
