package com.dvlcube.utils.i18n;

import java.util.Locale;

/**
 * @since Jul 31, 2017
 * @author Ulisses Lima
 */
public interface I18nable {
	/**
	 * @return resource bundle message key.
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	String getI18nKey();

	/**
	 * @return internationalized message.
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	String getMsg();

	/**
	 * @param locale
	 * @return internationalized message.
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	String getMsg(Locale locale);
}
