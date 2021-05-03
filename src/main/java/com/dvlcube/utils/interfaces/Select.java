package com.dvlcube.utils.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define o critério para select de um campo.
 * 
 * @since Oct 10, 2014
 * @author Ulisses
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Select {
	/**
	 * @return critério para select do campo. Ou o nome do campo, caso não
	 *         definido.
	 * @since Oct 10, 2014
	 * @author Ulisses
	 */
	String value() default "";
}
