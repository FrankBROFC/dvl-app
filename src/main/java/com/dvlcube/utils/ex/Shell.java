package com.dvlcube.utils.ex;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.dvlcube.utils.FileUtils;
import com.dvlcube.utils.SystemUtils;
import com.dvlcube.utils.SystemUtils.OSType;

/**
 * @since 26 de fev de 2019
 * @author Ulisses Lima
 */
public class Shell {
	/**
	 * @param command   command to process.
	 * @param processor
	 * @since 26 de fev de 2019
	 * @author Ulisses Lima
	 */
	public static void process(String command, Consumer<String> processor) {
		Runtime runtime = Runtime.getRuntime();
		Process process;
		try {
			if (SystemUtils.osType() == OSType.Windows) {
				process = runtime.exec(command);
			} else {
				process = runtime.exec(new String[] { "/bin/bash", "-c", command });
			}

			try (InputStream stream = process.getInputStream()) {
				FileUtils.process(stream, line -> {
					processor.accept(line);
				});
			}
		} catch (Throwable e) {
			e.printStackTrace();
			processor.accept(e.getMessage());
		}
	}

	/**
	 * @param command
	 * @return command result as list.
	 * @since 26 de fev de 2019
	 * @author Ulisses Lima
	 */
	public static List<String> list(String command) {
		List<String> list = new ArrayList<String>();
		process(command, line -> {
			list.add(line.replaceAll("\\s+", " ").trim());
		});
		return list;
	}

	/**
	 * @param command
	 * @return command result.
	 * @since 26 de fev de 2019
	 * @author Ulisses Lima
	 */
	public static String exec(String command) {
		StringBuilder builder = new StringBuilder();
		process(command, line -> {
			builder.append(line.replaceAll("\\s+", " ") + "\n");
		});
		return builder.toString();
	}
}
