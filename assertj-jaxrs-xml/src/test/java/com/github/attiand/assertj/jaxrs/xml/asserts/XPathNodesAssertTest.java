package com.github.attiand.assertj.jaxrs.xml.asserts;

import static com.github.attiand.assertj.jaxrs.xml.asserts.NodeAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class XPathNodesAssertTest {

	@Test
	void shouldAcceptListContent() throws ParserConfigurationException {
		Node node = createDocument();
		assertThat(node).xpath("test/name").asNodeList().hasSize(3).extracting(Node::getTextContent).contains("n1", "n2", "n3");
	}

	@Test
	void shouldAcceptAttributeValue() throws ParserConfigurationException {
		Node node = createDocument();
		assertThat(node).xpath("test/name/@type").asNodeList().hasSize(3).extracting(Node::getTextContent).contains("t1", "t2", "t3");

		assertThat(node).xpath("test/name")
				.asNodeList()
				.hasSize(3)
				.extracting(Node::getAttributes)
				.extracting(a -> a.getNamedItem("type"))
				.extracting(Node::getNodeValue)
				.contains("t1", "t2", "t3");
	}

	@Test
	void shouldAssertListContent() throws ParserConfigurationException {
		Node node = createDocument();

		var cut = assertThat(node).xpath("test/name").asNodeList().hasSize(3).extracting(Node::getTextContent);

		assertThatThrownBy(() -> cut.contains("n1", "n2", "n3", "n4")).isInstanceOf(AssertionError.class)
				.hasMessageContaining("could not find the following element")
				.hasMessageContaining("n4");

	}

	private static Document createDocument() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element root = doc.createElement("test");
		doc.appendChild(root);

		for (int i = 0; i < 3; i++) {
			Element name = doc.createElement("name");
			name.setTextContent("n" + (i + 1));
			name.setAttribute("type", "t" + (i + 1));
			root.appendChild(name);
		}

		return doc;
	}
}
