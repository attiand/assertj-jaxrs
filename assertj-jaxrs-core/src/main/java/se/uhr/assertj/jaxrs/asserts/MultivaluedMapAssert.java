package se.uhr.assertj.jaxrs.asserts;

import jakarta.ws.rs.core.MultivaluedMap;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.ListAssert;

public class MultivaluedMapAssert extends AbstractAssert<MultivaluedMapAssert, MultivaluedMap<String, Object>> {

	public MultivaluedMapAssert(MultivaluedMap<String, Object> actual) {
		super(actual, MultivaluedMapAssert.class);
	}

	public static MultivaluedMapAssert assertThat(MultivaluedMap<String, Object> actual) {
		return new MultivaluedMapAssert(actual);
	}

	public ListAssert<Object> extract(String name) {
		isNotNull();

		if (!actual.containsKey(name)) {
			failWithMessage("Expecting:%n <%s>%nto contain <%s>", actual.keySet(), name);
		}

		return new ListAssert<>(actual.get(name));
	}
}
