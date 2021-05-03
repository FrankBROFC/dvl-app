package com.dvlcube.utils;

import static com.dvlcube.utils.query.MxQuery.$;

import com.dvlcube.utils.ex.DatePatterns;
import com.dvlcube.utils.query.MxFile;

/**
 * @since 24 de abr de 2019
 * @author Ulisses Lima
 */
public class MxExceptionUtils {
	/**
	 * @param type
	 * @param msg
	 * @return formatted error message
	 * @since 24 de abr de 2019
	 * @author Ulisses Lima
	 */
	public static String log(String type, String msg) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		StackTraceElement el = $(stackTrace) //
				.filter(line -> {
					String className = line.getClassName();
					return !className.contains(MxExceptionUtils.class.getName())
							&& !className.contains(MxFile.class.getName()) //
							&& !className.equals("java.lang.Thread");
				}) //
				.findFirst().orElse(stackTrace[1]);

		String call = el.getClassName() + "." + el.getMethodName() + "#" + el.getLineNumber();
		return String.format("%s %s %s - %s", DatePatterns.DEFAULT.now(), type, call, msg);
	}

	/**
	 * @param e
	 * @return stack trace string
	 * @since 24 de abr de 2019
	 * @author Ulisses Lima
	 */
	public static String stackTrace(Throwable e) {
		if (e == null)
			return null;

		StringBuilder builder = new StringBuilder();

		builder.append(e.getClass() + " - " + e.getMessage() + "\n");
		for (StackTraceElement element : e.getStackTrace()) {
			builder.append(formatStackElement(element));
		}

		Throwable cause = e.getCause();
		while (cause != null) {
			builder.append("caused by ");
			builder.append(stackTrace(cause));
			cause = cause.getCause();
		}

		return builder.toString();
	}

	/**
	 * @param prefix
	 * @param element
	 * @return string.
	 * @since Mar 15, 2018
	 * @author Ulisses Lima
	 */
	private static String formatStackElement(StackTraceElement element) {
		return String.format("\t%s.%s (line %d)\n", element.getClassName(), element.getMethodName(),
				element.getLineNumber());
	}
}
