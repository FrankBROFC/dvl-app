package com.dvlcube.utils.ex;

import java.util.Calendar;
import java.util.Date;

/**
 * Classe utilitária para representar intervalos de data.
 * <p>
 * 
 * @author Ulisses
 * @since Dec 15, 2014
 */
public enum Interval {
	YEARS(Calendar.YEAR), //
	MONTHS(Calendar.MONTH), //
	DAYS(Calendar.DAY_OF_YEAR), //
	HOURS(Calendar.HOUR_OF_DAY), //
	MINUTES(Calendar.MINUTE), //
	SECONDS(Calendar.SECOND),;

	private int field;

	private Interval(int field) {
		this.field = field;
	}

	public Date now(int amount) {
		Calendar now = Calendar.getInstance();
		now.add(this.field, amount);
		return now.getTime();
	}
}
