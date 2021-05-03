package com.dvlcube.utils.ex;

import static com.dvlcube.utils.query.MxQuery.$;

import java.io.File;

/**
 * @author Ulisses Lima
 * @since 31/03/2013
 */
public class MxDebug {
	private static final boolean ENABLED;
	static {
		if (new File(System.getProperty("user.dir"), "debug-enabled").exists()) {
			ENABLED = true;
		} else {
			ENABLED = false;
		}
		log("dvl-core debug is enabled");
	}

	public static void log(String msg, Object... args) {
		if (ENABLED) {
			System.out.printf(msg + "\n", args);
		}
	}

	public static void logIf(Boolean condition, String msg, Object... args) {
		if (condition != null && condition) {
			log(msg, args);
		}
	}

	/**
	 * @param string
	 * @param query
	 * @author Ulisses Lima
	 * @since 01/04/2013
	 */
	public static void logObj(String msg, Object obj) {
		log(msg + "%s", $(obj).stringify());
	}
}
