package com.github.attiand.assertj.jaxrs.json;

import jakarta.json.JsonStructure;

import com.github.attiand.assertj.jaxrs.ext.EntityType;

public class EntityTypes {

	public static final EntityType<JsonStructure> JSON = new EntityType<>(JsonStructure.class);
}
