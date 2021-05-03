import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class Mac {
	public static void process(String command, Consumer<String> processor) {
		Runtime runtime = Runtime.getRuntime();
		Process process;
		try {
			if (osType() == OSType.Windows) {
				process = runtime.exec(command);
			} else {
				process = runtime.exec(new String[] { "/bin/bash", "-c", command });
			}

			try (InputStream stream = process.getInputStream()) {
				process(stream, line -> {
					processor.accept(line);
				});
			}
		} catch (Throwable e) {
			e.printStackTrace();
			processor.accept(e.getMessage());
		}
	}

	public static void process(InputStream inputStream, Consumer<String> processor) {
		try (BufferedReader r = new BufferedReader(new InputStreamReader(inputStream))) {
			String line = null;
			while ((line = r.readLine()) != null) {
				processor.accept(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> find() throws IOException {
		OSType os = osType();
		switch (os) {
		case MacOS:
		case Linux:
			System.out.println("unix detectado");
			return unixMacAddress();
		case Windows:
			System.out.println("windows detectado");
			return winMacAddress();
		default:
			throw new RuntimeException("unabled to find MAC Address for " + os);
		}
	}

	private static List<String> winMacAddress() throws IOException {
		List<String> macList = new ArrayList<String>();
		process("ipconfig /all", line -> {
			line = line.toLowerCase();

			if ((line.contains("endere") && line.contains("sico"))
					|| (line.contains("address") && line.contains("physical"))) {
				macList.add(line.substring(line.indexOf(":") + 2).trim());
			}
		});

		return macList;
	}

	private static List<String> unixMacAddress() throws IOException {
		List<String> macList = new ArrayList<>();
		process("/sbin/ifconfig -a", line -> {
			line = line.toLowerCase();
			if (line.contains("hwaddr")) {
				macList.add(line.substring(line.indexOf("hwaddr") + 7).trim());
			} else if (line.contains("hw")) {
				if (line.indexOf("hw") + 3 < line.length()) {
					macList.add(line.substring(line.indexOf("hw") + 3).trim());
				} else {
					System.out.println("sem match para linha.indexOf(\"hw\") + 3 < linha.length() - "
							+ (line.indexOf("hw") + 3) + " < " + line.length());
				}
			} else if (line.contains("ether")) {
				macList.add(line.trim().split(" ")[1]);
			}
		});

		return macList;
	}

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

	public static void main(String[] args) throws IOException {
		System.out.println(Mac.find());
	}
}
