package com.dvlcube.utils.ex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.dvlcube.utils.MxStringUtils;

/**
 * @since May 15, 2014
 * @author Ulisses
 */
public enum DatePatterns {
	ALPHANUMERIC("yyyyMMddHHmmss"), //
	ALPHANUMERIC_MS("yyyyMMddHHmmssSSS"), //
	DEFAULT("yyyy-MM-dd HH:mm:ss.SSS"), //
	DATE("yyyy-MM-dd"), //
	DATE_TIME("yyyy-MM-dd HH:mm:ss"), //
	DATE_TIME_NO_SECS("yyyy-MM-dd HH:mm"), //
	;
	private String pattern;

	DatePatterns(String padrao) {
		this.pattern = padrao;
	}

	/**
	 * @return o valor de padrao
	 * @since May 15, 2014
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * @return formatter
	 * @since Nov 18, 2014
	 * @author Ulisses
	 */
	public SimpleDateFormat getFormatter() {
		return new SimpleDateFormat(getPattern());
	}

	public String now(Locale locale) {
		return new SimpleDateFormat(pattern, locale).format(new Date());
	}

	/**
	 * @return data atual, como string.
	 * @since May 15, 2014
	 * @author Ulisses
	 */
	public String now() {
		return now(new Locale("pt", "BR"));
	}

	/**
	 * @param date
	 * @return data formatada.
	 * @since Jul 23, 2014
	 * @author Ulisses
	 */
	public String format(Date date) {
		if (date == null)
			return "-";
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * @param data
	 * @return data gerada.
	 * @since Aug 18, 2014
	 * @author Ulisses
	 */
	public Date parse(String data) {
		if (MxStringUtils.isBlank(data))
			return null;

		try {
			return new SimpleDateFormat(pattern).parse(data);
		} catch (ParseException e) {
			return null;
		}
	}
}