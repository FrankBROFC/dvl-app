package com.dvlcube.utils.i18n;

import java.util.Locale;

import com.dvlcube.utils.i18n.I18n.Bundle;

/**
 * @since Jul 31, 2017
 * @author Ulisses Lima
 */
public class CachedResource {
	private Bundle bundle;
	private Locale locale;

	/**
	 * @param bundle
	 * @param locale
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public CachedResource(Locale locale, Bundle bundle) {
		this.bundle = bundle;
		this.locale = locale;
	}

	/**
	 * @return the bundle
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public Bundle getBundle() {
		return bundle;
	}

	/**
	 * @param bundle
	 *            the bundle to set
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return the locale
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundle == null) ? 0 : bundle.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CachedResource other = (CachedResource) obj;
		if (bundle != other.bundle)
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		return true;
	}
}
