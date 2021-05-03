package com.github.attiand.assertj.jaxrs.xml.asserts;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XPathExpressionAssertTest {

	@Test
	void shouldAcceptDomAsString() throws ParserConfigurationException {
		Document doc = createDocument();
		DocumentAssert.assertThat(doc).xpath("/test/person/name").asString().isEqualTo("John");
	}

	@Test
	void shouldAcceptDomAsInteger() throws ParserConfigurationException {
		Document doc = createDocument();
		DocumentAssert.assertThat(doc).xpath("/test/person/age").asInteger().isEqualTo(20).isGreaterThan(19);
	}

	@Test
	void shouldAcceptDomAsDouble() throws ParserConfigurationException {
		Document doc = createDocument();
		DocumentAssert.assertThat(doc).xpath("/test/real").asDouble().isEqualTo(10.3).isBetween(10.2, 10.4);
	}

	@Test
	void shouldAcceptDomAsBoolean() throws ParserConfigurationException {
		Document doc = createDocument();
		DocumentAssert.assertThat(doc).xpath("boolean(/test/person/name)").asBoolean().isTrue();
		DocumentAssert.assertThat(doc).xpath("not(boolean(/test/person/name))").asBoolean().isFalse();
	}

	private static Document createDocument() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element root = doc.createElement("test");
		doc.appendChild(root);

		Element person = doc.createElement("person");
		root.appendChild(person);

		Element name = doc.createElement("name");
		name.setTextContent("John");
		person.appendChild(name);

		Element age = doc.createElement("age");
		age.setTextContent(Integer.toString(20));
		person.appendChild(age);

		Element real = doc.createElement("real");
		real.setTextContent(Double.toString(10.3));
		root.appendChild(real);

		return doc;
	}
}
