package com.github.attiand.assertj.jaxrs.asserts;

import org.assertj.core.api.AbstractAssert;
import org.w3c.dom.Document;

public class XmlDomAssert extends AbstractAssert<XmlDomAssert, Document> {

	public XmlDomAssert(Document actual) {
		super(actual, XmlDomAssert.class);
	}
}
