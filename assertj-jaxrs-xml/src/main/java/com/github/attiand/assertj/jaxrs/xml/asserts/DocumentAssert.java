package com.github.attiand.assertj.jaxrs.xml.asserts;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.assertj.core.api.AbstractAssert;
import org.w3c.dom.Document;

public class DocumentAssert extends AbstractAssert<DocumentAssert, Document> {

	public DocumentAssert(Document actual) {
		super(actual, DocumentAssert.class);
	}

	public static DocumentAssert assertThat(Document document) {
		return new DocumentAssert(document);
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
}
