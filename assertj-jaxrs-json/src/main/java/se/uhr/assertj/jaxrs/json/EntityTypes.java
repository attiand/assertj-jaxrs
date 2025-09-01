package se.uhr.assertj.jaxrs.json;

import jakarta.json.JsonStructure;

import se.uhr.assertj.jaxrs.ext.EntityType;

public class EntityTypes {

	public static final EntityType<JsonStructure> JSON = new EntityType<>(JsonStructure.class);
}
