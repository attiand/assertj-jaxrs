package com.github.attiand.assertj.jaxrs.ext;

import java.io.InputStream;

public interface EntityExtension<T> {

	Class<T> type();

	T load(InputStream is);
}
