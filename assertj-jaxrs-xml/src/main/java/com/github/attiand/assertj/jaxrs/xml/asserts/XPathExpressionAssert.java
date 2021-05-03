package com.github.attiand.assertj.jaxrs.xml.asserts;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.BooleanAssert;
import org.assertj.core.api.DoubleAssert;
import org.assertj.core.api.IntegerAssert;
import org.assertj.core.api.StringAssert;
import org.w3c.dom.Document;

public class XPathExpressionAssert extends AbstractAssert<XPathExpressionAssert, XPathExpression> {

	private static final String XPATH_EVALUATION_ERROR = "Could not evaluate xpatch expression";
	private final Document document;

	public XPathExpressionAssert(XPathExpression actual, Document document) {
		super(actual, XPathExpressionAssert.class);
		this.document = document;
	}

	@Override
	public StringAssert asString() {
		isNotNull();

		try {
			String result = actual.evaluateExpression(document, String.class);

			return new StringAssert(result);
		} catch (XPathExpressionException e) {
			throw new AssertionError(XPATH_EVALUATION_ERROR, e);
		}
	}

	public IntegerAssert asInteger() {
		isNotNull();

		try {
			Integer result = actual.evaluateExpression(document, Integer.class);

			return new IntegerAssert(result);
		} catch (XPathExpressionException e) {
			throw new AssertionError(XPATH_EVALUATION_ERROR, e);
		}
	}

	public DoubleAssert asDouble() {
		isNotNull();

		try {
			Double result = actual.evaluateExpression(document, Double.class);

			return new DoubleAssert(result);
		} catch (XPathExpressionException e) {
			throw new AssertionError(XPATH_EVALUATION_ERROR, e);
		}
	}

	public BooleanAssert asBoolean() {
		isNotNull();

		try {
			Boolean result = actual.evaluateExpression(document, Boolean.class);

			return new BooleanAssert(result);
		} catch (XPathExpressionException e) {
			throw new AssertionError(XPATH_EVALUATION_ERROR, e);
		}
	}
}
