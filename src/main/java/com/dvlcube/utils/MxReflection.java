package com.dvlcube.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Ulisses Lima
 * @since 16/09/2012
 */
public class MxReflection {

	/**
	 * @return generic types.
	 * @author Ulisses Lima
	 * @since 16/09/2012
	 */
	public static <T> Type[] getGenericTypes(final Class<T> jClass) {
		final ParameterizedType genericSuperclass = (ParameterizedType) jClass
				.getGenericSuperclass();
		return genericSuperclass.getActualTypeArguments();
	}
}
