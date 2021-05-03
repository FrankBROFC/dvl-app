package com.dvlcube.utils.query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.dvlcube.utils.DateUtils;
import com.dvlcube.utils.ex.Range;

/**
 * 
 * @author Ulisses Lima
 * @since 28/02/2013
 */
public class MxDate {
	public String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
	public String timePattern = "HH:mm:ss";
	private final SimpleDateFormat df = new SimpleDateFormat(pattern);
	private final SimpleDateFormat dfTime = new SimpleDateFormat(timePattern);

	public Calendar o;

	public MxDate() {
		o = Calendar.getInstance();
	}

	public MxDate(String pattern) {
		this();
		this.pattern = pattern;
		df.applyPattern(pattern);
	}

	/**
	 * @param date
	 * @since 2 de mar de 2019
	 */
	public MxDate(Date date) {
		this();
		if (date != null)
			o.setTime(date);
		else {
			o = null;
		}
	}

	/**
	 * @param date
	 * @since 2 de mar de 2019
	 */
	public MxDate(Calendar date) {
		o = date;
	}

	public MxDate newDateInRange(Calendar cStart, Calendar cEnd) {
		o = DateUtils.newDateInRange(cStart, cEnd);
		return this;
	}

	public MxDate newDateInRange(Date dateStart, Date dateEnd) {
		o = DateUtils.newDateInRange(dateStart, dateEnd);
		return this;
	}

	public MxDate newDateInRange(long start, long end) {
		o = DateUtils.newDateInRange(start, end);
		return this;
	}

	public MxDate newDateInRange(Range<Calendar> range) {
		o = DateUtils.newDateInRange(range);
		return this;
	}

	public MxDate setPattern(String pattern) {
		this.pattern = pattern;
		df.applyPattern(pattern);
		return this;
	}

	/**
	 * @return year as string.
	 * @since 09/07/2013
	 * @author Ulisses Lima
	 */
	public String sYear() {
		return String.valueOf(o.get(Calendar.YEAR));
	}

	@Override
	public String toString() {
		if (o != null)
			return df.format(o.getTime());
		else
			return "";
	}

	/**
	 * @return time string in the format "HH:mm:ss"
	 * @since 8 de abr de 2019
	 * @author Ulisses Lima
	 */
	public String timeString() {
		if (o != null)
			return dfTime.format(o.getTime());
		else
			return "";
	}

	/**
	 * @return the year.
	 * @since 09/07/2013
	 * @author Ulisses Lima
	 */
	public int year() {
		return o.get(Calendar.YEAR);
	}
}