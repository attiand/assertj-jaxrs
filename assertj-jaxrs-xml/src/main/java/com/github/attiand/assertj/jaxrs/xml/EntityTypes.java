package com.github.attiand.assertj.jaxrs.xml;

import org.w3c.dom.Document;

import com.github.attiand.assertj.jaxrs.ext.EntityType;

public class EntityTypes {

	public static final EntityType<Document> XML = new EntityType<>(Document.class);

}
