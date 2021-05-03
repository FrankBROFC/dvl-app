package com.dvlcube.utils.ex;

/**
 * 
 * @author Ulisses Lima
 * @since 16/09/2012
 */
public class Range<T> {
	private final T end;
	private final T start;

	/**
	 * @param start
	 * @param end
	 * @author Ulisses Lima
	 * @since 16/09/2012
	 */
	public Range(final T start, final T end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * @return the end
	 */
	public T getEnd() {
		return end;
	}

	/**
	 * @return the start
	 */
	public T getStart() {
		return start;
	}
}
