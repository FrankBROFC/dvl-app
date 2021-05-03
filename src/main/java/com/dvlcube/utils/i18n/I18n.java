package com.dvlcube.utils.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import com.dvlcube.utils.ex.LruMap;

/**
 * @since Feb 25, 2016
 * @author Ulisses Lima
 */
public class I18n {
	private static final LruMap<CachedResource, ResourceBundle> cache = new LruMap<>(5);

	public enum Response {
		FAIL("label.fail"), SUCCESS("label.success");
		private final String key;

		Response(final String key) {
			this.key = key;
		}

		/**
		 * @return the key
		 */
		public String key() {
			return key;
		}
	}

	/**
	 * Available bundles.
	 * 
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public enum Bundle {
		DEFAULT
	}

	/**
	 * @param key message key.
	 * @return a message from the server locale and default bundle.
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public static String msg(String key) {
		return msg(key, Locale.getDefault(), Bundle.DEFAULT);
	}

	/**
	 * @param key    message key.
	 * @param locale desired locale.
	 * @return a message from the default resource bundle.
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public static String msg(String key, Locale locale) {
		return msg(key, locale, Bundle.DEFAULT);
	}

	/**
	 * @param key    message key.
	 * @param locale desired locale.
	 * @param bundle desired bundle.
	 * @return a message from the requested bundle.
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public static String msg(String key, Locale locale, Bundle bundle) {
		if (bundle == null)
			throw new IllegalArgumentException("bundle can't be null");

		CachedResource resource = new CachedResource(locale, bundle);
		ResourceBundle b = cache.get(resource);
		if (b == null) {
			b = ResourceBundle.getBundle(bundle.name(), locale);
			cache.put(resource, b);
		}
		return b.getString(key);
	}

	/**
	 * @param item
	 * @return a message from the server locale and default bundle.
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public static String msg(I18nable item) {
		return msg(item.getI18nKey(), Locale.getDefault());
	}

	/**
	 * @param item   i18nable item.
	 * @param locale desired locale.
	 * @return a message from the default resource bundle.
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public static String msg(I18nable item, Locale locale) {
		return msg(item.getI18nKey(), locale);
	}

	/**
	 * @param message
	 * @return message from the bundle, or null if the message object is null.
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public static MessageResource msg(MessageResource message) {
		if (message == null)
			return null;

		String msg = msg(message.getKey(), message.getLocale(), message.getBundle());
		message.setValue(msg);
		return message;
	}
}
