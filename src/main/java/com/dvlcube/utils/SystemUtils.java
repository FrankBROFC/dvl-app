package com.dvlcube.utils;

import java.util.Locale;

/**
 * @since 25 de fev de 2019
 * @author Ulisses Lima
 */
public class SystemUtils {
	public enum OSType {
		Windows, MacOS, Linux, Other
	}

	// cached result of OS detection
	protected static OSType os;

	/**
	 * detect the operating system from the os.name System property and cache the
	 * result
	 * 
	 * @returns - the operating system detected
	 */
	public static OSType osType() {
		if (os == null) {
			String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
			if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
				os = OSType.MacOS;
			} else if (OS.indexOf("win") >= 0) {
				os = OSType.Windows;
			} else if (OS.indexOf("nux") >= 0) {
				os = OSType.Linux;
			} else {
				os = OSType.Other;
			}
		}
		return os;
	}
}
