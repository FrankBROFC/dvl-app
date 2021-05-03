package com.dvlcube.utils.query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @since 29 de mai de 2019
 * @author Ulisses Lima
 */
public class MxAnnotation<A extends Annotation> {
	public A o;
	private Location location = Location.NONE;

	/**
	 * Annotation location.
	 * 
	 * @since 29 de mai de 2019
	 * @author Ulisses Lima
	 */
	public enum Location {
		NONE, TYPE, METHOD
	}

	/**
	 * @param o value to wrap
	 * @since 30 de mai de 2019
	 */
	public MxAnnotation(A o) {
		this.o = o;
	}

	/**
	 * @param annotation
	 * @param c          reference class
	 * @since 30 de mai de 2019
	 */
	public MxAnnotation(Class<A> annotation, Class<?> c) {
		this.o = c.getAnnotation(annotation);
		this.location = Location.TYPE;
	}

	/**
	 * @param annotation
	 * @param method     reference method
	 * @since 30 de mai de 2019
	 */
	public MxAnnotation(Class<A> annotation, Method method) {
		A test = method.getAnnotation(annotation);
		if (test != null) {
			this.o = test;
			this.location = Location.METHOD;
		} else {
			this.o = method.getDeclaringClass().getAnnotation(annotation);
			if (o != null)
				this.location = Location.TYPE;
		}
	}

	/**
	 * @see Location
	 * @return annotation location.
	 * @since 29 de mai de 2019
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @return true if this annotation is on a class
	 * @since 29 de mai de 2019
	 * @author Ulisses Lima
	 */
	public boolean onType() {
		return location == Location.TYPE;
	}

	/**
	 * @return true if this annotation is on a method
	 * @since 29 de mai de 2019
	 * @author Ulisses Lima
	 */
	public boolean onMethod() {
		return location == Location.METHOD;
	}

	/**
	 * @return true if an annotation was found
	 * @since 29 de mai de 2019
	 * @author Ulisses Lima
	 */
	public boolean present() {
		return o != null;
	}

	public Optional<Method> method(String name) {
		if (o == null)
			return Optional.empty();

		try {
			return Optional.ofNullable(o.getClass().getMethod(name, (Class<?>[]) null));
		} catch (Throwable e) {
			return Optional.empty();
		}
	}
}
