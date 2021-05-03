package com.dvlcube.utils.query;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.dvlcube.utils.ArrayUtils;
import com.dvlcube.utils.AssertionUtils;

/**
 * @author Ulisses Lima
 * @since 15/04/2013
 */
public class MxArray<T> {
	public T[] o;

	/**
	 * @param array
	 * @author Ulisses Lima
	 * @since 15/04/2013
	 */
	public MxArray(T[] array) {
		this.o = array;
	}

	@SafeVarargs
	public MxArray(T[] array, T[]... arrays) {
		this.o = array;
		concat(arrays);
	}

	/**
	 * @return
	 * @author Ulisses Lima
	 * @since 15/04/2013
	 */
	public Set<T> asSet() {
		return ArrayUtils.asSet(o);
	}

	@SafeVarargs
	public final MxArray<T> concat(T[]... rest) {
		o = ArrayUtils.concat(o, rest);
		return this;
	}

	public Set<T> concatIntoSet(Collection<T> collection) {
		return ArrayUtils.concatIntoSet(o, collection);
	}

	@Override
	public String toString() {
		return Arrays.toString(o);
	}

	/**
	 * 
	 * @since Feb 26, 2016
	 * @author Ulisses Lima
	 */
	public MxArray<T> assertNotNull() {
		AssertionUtils.notNull(o);
		return this;
	}

	public MxArray<T> forEach(Consumer<? super T> action) {
		if (o == null)
			return this;

		List<T> list = Arrays.asList(o);
		if (list.isEmpty())
			return this;

		list.forEach(action);
		return this;
	}

	/**
	 * @return array as list
	 * @since 17 de abr de 2019
	 * @author Ulisses Lima
	 */
	public MxCollection<T> asList() {
		return MxQuery.$(Arrays.asList(o));
	}

	/**
	 * @param predicate
	 * @return stream
	 * @since 24 de abr de 2019
	 * @author Ulisses Lima
	 */
	public Stream<T> filter(Predicate<T> predicate) {
		return Arrays.stream(o).filter(predicate);
	}
}
