package com.github.attiand.assertj.jaxrs.json.ext;

import java.io.InputStream;
import java.util.Collections;

import jakarta.json.Json;
import jakarta.json.JsonReader;
import jakarta.json.JsonReaderFactory;
import jakarta.json.JsonStructure;

import com.github.attiand.assertj.jaxrs.ext.EntityExtension;
import com.github.attiand.assertj.jaxrs.json.EntityTypes;

public class JsonExtension implements EntityExtension<JsonStructure> {

	private JsonReaderFactory jsonFactory = Json.createReaderFactory(Collections.emptyMap());

	@Override
	public String id() {
		return EntityTypes.JSON.id();
	}

	@Override
	public Class<JsonStructure> type() {
		return EntityTypes.JSON.type();
	}

	@Override
	public JsonStructure create(InputStream is) {
		try (JsonReader reader = jsonFactory.createReader(is)) {
			return reader.read();
		}
	}
}
