package se.uhr.assertj.jaxrs.asserts;

import jakarta.ws.rs.core.NewCookie;

import org.assertj.core.api.AbstractAssert;

public class CookieAssert extends AbstractAssert<CookieAssert, NewCookie> {

	public CookieAssert(NewCookie actual) {
		super(actual, CookieAssert.class);
	}

	public static CookieAssert assertThat(NewCookie cookie) {
		return new CookieAssert(cookie);
	}

	public CookieAssert hasName(String name) {
		isNotNull();

		if (!name.equals(actual.getName())) {
			failWithMessage("Expected cookie name to be <%s> but was <%s>", name, actual.getName());
		}

		return this;
	}

	public CookieAssert hasValue(String value) {
		isNotNull();

		if (!value.equals(actual.getValue())) {
			failWithMessage("Expected cookie value to be <%s> but was <%s>", value, actual.getValue());
		}

		return this;
	}

	public CookieAssert hasDomain(String domain) {
		isNotNull();

		if (!domain.equals(actual.getDomain())) {
			failWithMessage("Expected cookie domain to be <%s> but was <%s>", domain, actual.getDomain());
		}

		return this;
	}

	public CookieAssert hasNoDomain() {
		isNotNull();

		if (actual.getDomain() != null) {
			failWithMessage("Expected no cookie domain but was <%s>", actual.getDomain());
		}

		return this;
	}

	public CookieAssert hasPath(String path) {
		isNotNull();

		if (!path.equals(actual.getPath())) {
			failWithMessage("Expected cookie path to be <%s> but was <%s>", path, actual.getPath());
		}

		return this;
	}

	public CookieAssert hasNoPath() {
		isNotNull();

		if (actual.getPath() != null) {
			failWithMessage("Expected no cookie path but was <%s>", actual.getPath());
		}

		return this;
	}

	public CookieAssert hasMaxAge(int age) {
		isNotNull();

		if (age != actual.getMaxAge()) {
			failWithMessage("Expected cookie max age to be <%s> but was <%s>", age, actual.getMaxAge());
		}

		return this;
	}

	public CookieAssert hasNoMaxAge() {
		isNotNull();

		if (actual.getMaxAge() > 0) {
			failWithMessage("Expected cookie to have no max age but has");
		}

		return this;
	}

	public CookieAssert isSecure() {
		isNotNull();

		if (!actual.isSecure()) {
			failWithMessage("Expected cookie to be secure but was not");
		}

		return this;
	}

	public CookieAssert isInsecure() {
		isNotNull();

		if (actual.isSecure()) {
			failWithMessage("Expected cookie to be insecure but was not");
		}

		return this;
	}

	public CookieAssert isHttpOnly() {
		isNotNull();

		if (!actual.isHttpOnly()) {
			failWithMessage("Expected cookie to be http only but was not");
		}

		return this;
	}

	public CookieAssert isNotHttpOnly() {
		isNotNull();

		if (actual.isHttpOnly()) {
			failWithMessage("Expected cookie not o be http only but was");
		}

		return this;
	}
}
