package com.dvlcube.utils.aspects.stats;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marca método para log de tempo de execução.
 * 
 * @see Stat
 * @see Stats
 * @see StatsAspect
 * @since Jan 18, 2018
 * @author Ulisses Lima
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Timed {
	/**
	 * Deixe em branco para o aspecto definir automaticamente.
	 * 
	 * @return nome da estatística.
	 * @since 04/12/2018
	 * @author Ulisses Lima
	 */
	String value() default "";

	/**
	 * @return true if this annotation should skip stats collection.
	 * @since 29 de mai de 2019
	 * @author Ulisses Lima
	 */
	boolean exclude() default false;

	/**
	 * Defaults to no specific type.
	 * 
	 * @return required return type. Skip stats collection otherwise.
	 * @since 29 de mai de 2019
	 * @author Ulisses Lima
	 */
	Class<?> returnType() default Object.class;

	/**
	 * Defaults to no specific parameters.
	 * 
	 * @return required parameter type for the annotated method. Skip stats
	 *         collection if.
	 * @since 29 de mai de 2019
	 * @author Ulisses Lima
	 */
	Class<?> hasArg() default Object.class;
}