package com.github.attiand.assertj.jaxrs.xml.asserts;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.assertj.core.api.AbstractAssert;
import org.w3c.dom.Node;

public class NodeAssert extends AbstractAssert<NodeAssert, Node> {

	public NodeAssert(Node actual) {
		super(actual, NodeAssert.class);
	}

	public static NodeAssert assertThat(Node document) {
		return new NodeAssert(document);
	}

	public XPathExpressionAssert xpath(String expression) {
		XPath xp = XPathFactory.newInstance().newXPath();
		try {
			XPathExpression expr = xp.compile(expression);

			return new XPathExpressionAssert(expr, actual);
		} catch (XPathExpressionException e) {
			throw new AssertionError("Could not parse xpatch expression", e);
		}
	}

	public NodeAssert hasType(short type) {
		isNotNull();

		if (actual.getNodeType() != type) {
			failWithMessage("Expected node type to be <%s> but was <%s>", type, actual.getNodeType());
		}

		return this;
	}
}
