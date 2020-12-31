package com.github.attiand.assertj.jaxrs.jupiter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Set the URI to the test target. If not specified uses the default URI, see {@link TestTargetBuilder}. 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface AssertjJaxrsSettings {

	String baseUri();
}
