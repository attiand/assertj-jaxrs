package se.uhr.assertj.jaxrs.json.ext;

import java.io.InputStream;
import java.util.Collections;

import jakarta.json.Json;
import jakarta.json.JsonReader;
import jakarta.json.JsonReaderFactory;
import jakarta.json.JsonStructure;

import se.uhr.assertj.jaxrs.ext.EntityExtension;
import se.uhr.assertj.jaxrs.json.EntityTypes;

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
