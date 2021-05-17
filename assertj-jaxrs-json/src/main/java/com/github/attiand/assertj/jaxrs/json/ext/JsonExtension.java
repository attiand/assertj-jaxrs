package com.github.attiand.assertj.jaxrs.json.ext;

import java.io.InputStream;
import java.util.Collections;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonStructure;

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
