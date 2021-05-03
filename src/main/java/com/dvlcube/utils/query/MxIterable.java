package com.dvlcube.utils.query;

import java.util.function.Predicate;
import java.util.stream.StreamSupport;

/**
 * @since Feb 26, 2016
 * @author Ulisses Lima
 */
public class MxIterable<T> {
	public Iterable<T> o;

	public MxIterable(Iterable<T> o) {
		this.o = o;
	}

	/**
	 * @return next item, or null if empty.
	 * @since Aug 10, 2017
	 * @author Ulisses Lima
	 */
	public T next() {
		if (o == null)
			return null;

		return o.iterator().next();
	}

	/**
	 * @param p
	 * @return filtered list
	 * @since 5 de mar de 2019
	 * @author Ulisses Lima
	 */
	public Iterable<T> filter(Predicate<? super T> p) {
		if (o == null)
			return null;

		Iterable<T> list = () -> StreamSupport.stream(o.spliterator(), false).filter(p).iterator();
		return list;
	}
}
