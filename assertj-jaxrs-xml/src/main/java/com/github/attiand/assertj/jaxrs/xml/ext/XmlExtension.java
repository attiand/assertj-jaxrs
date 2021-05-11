package com.github.attiand.assertj.jaxrs.xml.ext;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.attiand.assertj.jaxrs.ext.EntityExtension;

public class XmlExtension implements EntityExtension<Document> {

	@Override
	public Class<Document> type() {
		return Document.class;
	}

	@Override
	public Document load(InputStream is) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			return builder.parse(is);
		} catch (IOException | ParserConfigurationException | SAXException e) {
			throw new AssertionError("Could not parse XML entity", e);
		}
	}
}
