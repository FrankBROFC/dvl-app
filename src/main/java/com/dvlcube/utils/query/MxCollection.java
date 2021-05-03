package com.dvlcube.utils.query;

import static com.dvlcube.utils.query.MxQuery.$;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.dvlcube.utils.AssertionUtils;
import com.dvlcube.utils.interfaces.Identifiable;

/**
 * @since Feb 26, 2016
 * @author Ulisses Lima
 */
@SuppressWarnings("unchecked")
public class MxCollection<T> {
	public Collection<T> o;

	public MxCollection(Collection<T> o) {
		this.o = o;
	}

	public boolean isEmpty() {
		if (o == null)
			return true;

		return o.size() < 1;
	}

	public boolean isNotEmpty() {
		return !isEmpty();
	}

	public MxCollection<T> assertNotEmpty(String paramName) {
		AssertionUtils.notEmpty(paramName, o);
		return this;
	}

	public MxCollection<T> assertNotEmpty() {
		AssertionUtils.notEmpty("collection", o);
		return this;
	}

	/**
	 * @return list size. 0 if null.
	 * @since Aug 8, 2017
	 * @author Ulisses Lima
	 */
	public int size() {
		if (o == null)
			return 0;

		return o.size();
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
	 * @param filter
	 * @return filtered list
	 * @since 5 de mar de 2019
	 * @author Ulisses Lima
	 */
	public MxCollection<T> filter(Predicate<? super T> filter) {
		if ($(o).isEmpty())
			return null;

		o = o.stream().filter(filter).collect(Collectors.toList());
		return this;
	}

	/**
	 * Modifies this list, keeping only the items that match the predicate.
	 * 
	 * @param filter predicate
	 * @return this
	 * @since 14 de mar de 2019
	 * @author Ulisses Lima
	 */
	public MxCollection<T> keep(Predicate<? super T> filter) {
		o.removeIf(filter.negate());
		return this;
	}

	/**
	 * Modifies this list, removing the items that match the predicate.
	 * 
	 * @param filter predicate.
	 * @return this
	 * @since 14 de mar de 2019
	 * @author Ulisses Lima
	 */
	public MxCollection<T> remove(Predicate<? super T> filter) {
		o.removeIf(filter);
		return this;
	}

	/**
	 * @return first item in this list.
	 * @since 14 de mar de 2019
	 * @author Ulisses Lima
	 */
	public T first() {
		if ($(o).isEmpty())
			return null;

		return o.stream().findFirst().get();
	}

	/**
	 * @return last item in this list.
	 * @since 14 de mar de 2019
	 * @author Ulisses Lima
	 */
	public T last() {
		if ($(o).isEmpty())
			return null;

		return o.stream().reduce((first, second) -> second).orElse(null);
	}

	/**
	 * @return ID property of the first item in the list that is
	 *         {@link Identifiable}.
	 * @since 14 de mar de 2019
	 * @author Ulisses Lima
	 */
	public <S extends Serializable> Optional<S> firstId() {
		T first = first();
		if (first instanceof Identifiable<?>) {
			Identifiable<S> fs = (Identifiable<S>) first;
			return Optional.ofNullable(fs.getId());
		}
		return Optional.empty();
	}

	/**
	 * Does not modify the list.
	 * 
	 * @param filter predicate
	 * @return first object matching the predicate.
	 * @since 13 de mar de 2019
	 * @author Ulisses Lima
	 */
	public T filterOne(Predicate<? super T> filter) {
		MxCollection<T> f = filter(filter);
		if (f == null)
			return null;

		Collection<T> collection = f.o;
		if ($(collection).isEmpty())
			return null;

		return collection.iterator().next();
	}

	/**
	 * Null safe helper. Returns null if no underlying list is found.
	 * 
	 * @param consumer
	 * @since 25 de mar de 2019
	 * @author Ulisses Lima
	 */
	public void each(Consumer<T> consumer) {
		if (o == null || o.isEmpty())
			return;

		o.forEach(consumer);
	}

	/**
	 * Builds a string using the consumer.
	 * 
	 * @param consumer  function
	 * @param separator token separator
	 * @return string
	 * @since 25 de mar de 2019
	 * @author Ulisses Lima
	 */
	public String buildString(Function<T, String> f, final String separator) {
		if (o == null || o.isEmpty())
			return null;

		StringBuilder builder = new StringBuilder();
		o.forEach(item -> {
			builder.append(separator).append(f.apply(item));
		});
		return builder.toString().replaceFirst(separator, "");
	}

	/**
	 * Builds a string using the consumer. Items are separated by a ","
	 * 
	 * @param f function
	 * @return tokenized string, separated by ","
	 * @since 25 de mar de 2019
	 * @author Ulisses Lima
	 */
	public String buildString(Function<T, String> f) {
		return buildString(f, ",");
	}

	/**
	 * @param by filter if null, no filter
	 * @return collection IDs
	 * @since 2 de abr de 2019
	 * @author Ulisses Lima
	 */
	public <S extends Serializable> List<S> idsLike(Predicate<Identifiable<S>> by) {
		if (this.isEmpty() || !(o.iterator().next() instanceof Identifiable<?>))
			return null;

		Collection<Identifiable<S>> s = (Collection<Identifiable<S>>) o;
		return s.stream().filter(by == null ? x -> true : by).map(a -> a.getId()).collect(Collectors.toList());
	}

	/**
	 * @return IDs
	 * @since 2 de abr de 2019
	 * @author Ulisses Lima
	 */
	public <S extends Serializable> List<S> ids() {
		return idsLike(null);
	}

	/**
	 * @param f
	 * @return collection filtered by the function
	 * @since 2 de abr de 2019
	 * @author Ulisses Lima
	 */
	public <Any> List<Any> collect(Function<T, Any> f) {
		if (this.isEmpty())
			return null;

		return o.stream().map(f).collect(Collectors.toList());
	}

	/**
	 * @param n
	 * @return sublist with the first n elements
	 * @since 8 de abr de 2019
	 * @author Ulisses Lima
	 */
	public Collection<T> lidvl(int n) {
		if (o == null || n < 1 || o.size() < n || isEmpty())
			return o;

		List<T> sub = new ArrayList<>(o);
		return sub.subList(0, n);
	}
}
