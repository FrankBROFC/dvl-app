package com.dvlcube.utils.i18n;

import java.util.Locale;

import com.dvlcube.utils.i18n.I18n.Bundle;

/**
 * @since Jul 31, 2017
 * @author Ulisses Lima
 */
public class MessageResource extends CachedResource {
	private String key;
	private String value;

	/**
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public MessageResource() {
		super(null, Bundle.DEFAULT);
	}

	/**
	 * @param locale
	 * @param bundle
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public MessageResource(Locale locale, Bundle bundle) {
		super(locale, bundle);
	}

	/**
	 * @return the key
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
