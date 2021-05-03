package com.dvlcube.utils.env;

/**
 * @since 26 de fev de 2019
 * @author Ulisses Lima
 */
public class Env {
	/**
	 * @param key
	 * @param fallback
	 * @return env value or vm value or fallback or null. Empty strings are
	 *         considered valid values.
	 * @since 25 de fev de 2019
	 * @author Ulisses Lima
	 */
	public static String var(String key, String fallback) {
		String val = fallback;

		String vm = System.getProperty(key);
		if (vm != null) {
			val = vm;
		} else {
			String env = System.getenv(key);
			if (env != null)
				val = env;
		}

		return val;
	}

	/**
	 * @param key
	 * @return env value or vm value or null. Empty strings are considered valid
	 *         values.
	 * @since 26 de fev de 2019
	 * @author Ulisses Lima
	 */
	public static String var(String key) {
		return var(key, null);
	}

	/**
	 * @return OS name and version
	 * @since 3 de mai de 2019
	 * @author Ulisses Lima
	 */
	public static String os() {
		return System.getProperty("os.name") + " " + System.getProperty("os.version");
	}
}
