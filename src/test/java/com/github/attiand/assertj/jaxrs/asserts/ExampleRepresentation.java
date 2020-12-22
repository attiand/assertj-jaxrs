package com.github.attiand.assertj.jaxrs.asserts;

public class ExampleRepresentation {

	private String name;
	private int value;

	public ExampleRepresentation() {

	}

	public ExampleRepresentation(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
