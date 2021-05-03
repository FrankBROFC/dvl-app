package com.dvlcube.utils.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Basic annotation properties.
 * 
 * @since 30 de mai de 2019
 * @author Ulisses Lima
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
	/**
	 * @return value
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	String value() default "";
}