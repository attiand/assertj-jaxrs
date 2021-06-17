package com.github.attiand.assertj.jaxrs.xml.asserts;

import javax.xml.xpath.XPathNodes;

import org.assertj.core.api.IterableAssert;
import org.w3c.dom.Node;

public class XPathNodesAssert extends IterableAssert<Node> {

	public XPathNodesAssert(XPathNodes actual) {
		super(actual);
	}

	public static XPathNodesAssert assertThat(XPathNodes list) {
		return new XPathNodesAssert(list);
	}
}
