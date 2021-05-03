package com.dvlcube.utils.aspects.stats;

import java.util.Comparator;

import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @see Stats
 * @see Timed
 * @see StatsAspect
 * @since 04/12/2018
 * @author Ulisses Lima
 */
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stat implements Comparable<Stat> {
	private String action;
	private Long min;
	private Long max;
	private Long total = 0l;
	private Long count = 0l;
	private Long errors = 0l;

	/**
	 * @param action
	 * @since 04/12/2018
	 */
	public Stat(String action) {
		this.action = action.replaceAll(StatsAspect.actionNameIgnoreExpression, "");
	}

	/**
	 * @param elapsed
	 * @since 04/12/2018
	 */
	public Stat(String action, long elapsed) {
		this(action);
		this.min = elapsed;
		this.max = elapsed;
		this.total = elapsed;
		this.count = 1l;
		this.errors = 0l;
	}

	/**
	 * @return the action
	 * @since 04/12/2018
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 * @since 04/12/2018
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the total
	 * @since 04/12/2018
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * @return the count
	 * @since 04/12/2018
	 */
	public Long getCount() {
		return count;
	}

	/**
	 * @return the max
	 * @since 04/12/2018
	 */
	public Long getMax() {
		return max;
	}

	/**
	 * @return the min
	 * @since 04/12/2018
	 */
	public Long getMin() {
		return min;
	}

	/**
	 * @return the errors
	 * @since 04/12/2018
	 */
	public Long getErrors() {
		return errors;
	}

	/**
	 * @param min
	 * @since 17 de abr de 2019
	 * @author Ulisses Lima
	 */
	public void setMin(Long min) {
		this.min = min;
	}

	/**
	 * @param max
	 * @since 17 de abr de 2019
	 * @author Ulisses Lima
	 */
	public void setMax(Long max) {
		this.max = max;
	}

	/**
	 * @param total
	 * @since 17 de abr de 2019
	 * @author Ulisses Lima
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * @param count
	 * @since 17 de abr de 2019
	 * @author Ulisses Lima
	 */
	public void setCount(Long count) {
		this.count = count;
	}

	/**
	 * @param errors
	 * @since 17 de abr de 2019
	 * @author Ulisses Lima
	 */
	public void setErrors(Long errors) {
		this.errors = errors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stat other = (Stat) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return table();
	}

	/**
	 * @return string table
	 * @since 04/12/2018
	 * @author Ulisses Lima
	 */
	@JsonIgnore
	public String table() {
		long avg = total / (count == 0 ? 1 : count);
		return min + "\t" + max + "\t" + avg + "\t" + count + "\t" + errors + "\t" + action;
	}

	/**
	 * @param timeFactor dividir as propriedades de tempo por esse valor
	 * @return string table
	 * @since 04/12/2018
	 * @author Ulisses Lima
	 */
	@JsonIgnore
	public String table(Double timeFactor) {
		if (timeFactor == null)
			return table();

		long avg = total / (count == 0 ? 1 : count);
		return div(min, timeFactor) + "\t" + div(max, timeFactor) + "\t" + div(avg, timeFactor) + "\t" + count + "\t"
				+ errors + "\t" + action;
	}

	@JsonIgnore
	private String div(double v1, double v2) {
		return String.format("%.2f", (v1 / v2));
	}

	/**
	 * @param elapsed
	 * @since 04/12/2018
	 * @author Ulisses Lima
	 */
	@JsonIgnore
	public void split(long elapsed) {
		if (max == null || elapsed > max)
			max = elapsed;

		if (min == null || elapsed < min)
			min = elapsed;

		total += elapsed;
		count++;
	}

	public void error() {
		errors++;
	}

	/**
	 * @return avg
	 * @since 11/12/2018
	 * @author Ulisses Lima
	 */
	public Double avg() {
		return total.doubleValue() / (count == 0 ? 1d : count.doubleValue());
	}

	/**
	 * @return string avg
	 * @since 11/12/2018
	 * @author Ulisses Lima
	 */
	public String savg() {
		return String.format("%.2f", avg());
	}

	@Override
	public int compareTo(Stat o) {
		return Comparator//
				.comparingLong(Stat::getCount) //
				.thenComparingLong(Stat::getTotal) //
				.thenComparingDouble(Stat::avg).reversed() //
				.thenComparingDouble(Stat::getMax).reversed() //
				.thenComparingDouble(Stat::getMin).reversed() //
				.compare(this, o);
	}
}
