package se.uhr.assertj.jaxrs.xml;

import org.w3c.dom.Document;

import se.uhr.assertj.jaxrs.ext.EntityType;

public class EntityTypes {

	public static final EntityType<Document> XML = new EntityType<>(Document.class);

}
