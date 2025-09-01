package se.uhr.assertj.jaxrs.xml.ext;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import se.uhr.assertj.jaxrs.ext.EntityExtension;
import se.uhr.assertj.jaxrs.xml.EntityTypes;

public class XmlExtension implements EntityExtension<Document> {

	@Override
	public String id() {
		return EntityTypes.XML.id();
	}

	@Override
	public Class<Document> type() {
		return EntityTypes.XML.type();
	}

	@Override
	public Document create(InputStream is) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			return builder.parse(is);
		} catch (IOException | ParserConfigurationException | SAXException e) {
			throw new AssertionError("Could not parse XML entity", e);
		}
	}
}
