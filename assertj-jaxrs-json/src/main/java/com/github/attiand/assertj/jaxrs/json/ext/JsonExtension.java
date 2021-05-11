package com.github.attiand.assertj.jaxrs.json.ext;

import java.io.InputStream;
import java.util.Collections;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonStructure;

import com.github.attiand.assertj.jaxrs.ext.EntityExtension;

public class JsonExtension implements EntityExtension<JsonStructure> {

	private JsonReaderFactory jsonFactory = Json.createReaderFactory(Collections.emptyMap());

	@Override
	public Class<JsonStructure> type() {
		return JsonStructure.class;
	}

	@Override
	public JsonStructure load(InputStream is) {
		try (JsonReader reader = jsonFactory.createReader(is)) {
			return reader.read();
		}
	}
}
