package com.dvlcube.utils.query;

import com.dvlcube.utils.NumberUtils;
import com.dvlcube.utils.ex.Range;

/**
 * 
 * @author Ulisses Lima
 * @since 28/02/2013
 */
public class MxNumber extends MxUtils {
	public Double o;

	/**
	 * @see com.dvlcube.utils.query.MxUtils#val()
	 */
	@Override
	public Object val() {
		return o;
	}

	public Range<Double> range = new Range<>(Double.MIN_VALUE, Double.MAX_VALUE);

	public MxNumber(double n) {
		o = n;
	}

	public MxNumber(double n, double range0, double range1) {
		o = n;
		range = new Range<>(range0, range1);
	}

	public MxNumber(double n, Range<Double> range) {
		o = n;
		this.range = range;
	}

	public float f() {
		return o.floatValue();
	}

	public int i() {
		return o.intValue();
	}

	public long l() {
		return o.longValue();
	}

	public MxNumber lidvl(double l1, double l2) {
		if (o < l1) {
			o = l1;
		}

		if (o > l2) {
			o = l2;
		}

		return this;
	}

	public MxNumber map(double range0, double range1) {
		map(range, new Range<>(range0, range1));
		return this;
	}

	public MxNumber map(Range<Double> newRange) {
		map(range, newRange);
		return this;
	}

	/**
	 * http://stackoverflow.com/questions/345187/math-mapping-numbers
	 * <p>
	 * Linear Transform
	 * <p>
	 * Y = (X-A)/(B-A) * (D-C) + C
	 * 
	 * @param originalRange
	 * @param newRange
	 * @return (X-A)/(B-A) * (D-C) + C
	 * @since 04/07/2013
	 * @author Ulisses Lima
	 */
	public MxNumber map(Range<Double> originalRange, Range<Double> newRange) {
		o = NumberUtils.map(o, originalRange, newRange);
		return this;
	}

	@Override
	public String toString() {
		return o.toString();
	}

	/**
	 * @param other
	 * @return true if this does not equal other
	 * @since 2019-05-07
	 * @author Ulisses Lima
	 */
	public boolean not(Object other) {
		return !eq(other);
	}

	/**
	 * @param other
	 * @return true if this equals other
	 * @since 2019-05-07
	 * @author Ulisses Lima
	 */
	public boolean eq(Object other) {
		if (other == null)
			return false;

		Double valueOf = null;
		try {
			valueOf = Double.valueOf(other.toString());
		} catch (Throwable e) {
			return false;
		}
		return o.equals(valueOf);
	}
}