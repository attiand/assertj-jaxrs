package se.uhr.assertj.jaxrs.ext;

import java.io.InputStream;

public interface EntityExtension<T> {

	String id();

	Class<T> type();

	T create(InputStream is);
}
