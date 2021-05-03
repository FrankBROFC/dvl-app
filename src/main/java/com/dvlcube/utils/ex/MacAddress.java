package com.dvlcube.utils.ex;

import static com.dvlcube.utils.query.MxQuery.$;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dvlcube.utils.SystemUtils;
import com.dvlcube.utils.SystemUtils.OSType;

/**
 * @since 25 de fev de 2019
 * @author Ulisses Lima
 */
public class MacAddress {
	/**
	 * @param mac
	 * @return true if the passed MAC id is a registered interface on this machine
	 * @throws IOException
	 * @since 25 de fev de 2019
	 * @author Ulisses Lima
	 */
	public static boolean exists(String mac) throws IOException {
		List<String> foundList = find();
		for (String foundId : foundList) {
			if (foundId.equalsIgnoreCase(mac))
				return true;
		}
		return false;
	}

	/**
	 * @return o hash code do identificador único desta máquina.
	 * @since Oct 15, 2014
	 * @author Ulisses
	 * @throws IOException
	 */
	public static long hashcode() throws IOException {
		int identificador = -1;
		List<String> macAddresses = find();
		if (macAddresses != null && macAddresses.size() > 0) {
			String mac = macAddresses.iterator().next();
			if ($(mac).isNotBlank()) {
				identificador = mac.hashCode();
			}
		}

		return identificador;
	}

	/**
	 * @return registered MAC addresses
	 * @throws IOException
	 * @since 25 de fev de 2019
	 * @author Ulisses Lima
	 */
	public static List<String> find() throws IOException {
		OSType os = SystemUtils.osType();
		switch (os) {
		case MacOS:
		case Linux:
			return unixMacAddress();
		case Windows:
			return winMacAddress();
		default:
			throw new RuntimeException("unabled to find MAC Address for " + os);
		}
	}

	/**
	 * @return first registered MAC address
	 * @since 12 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static String findFirst() {
		try {
			List<String> list = find();
			if ($(list).isEmpty())
				return null;

			Collections.sort(list);
			return list.iterator().next();
		} catch (Throwable e) {
			e.addSuppressed(e);
			return null;
		}
	}

	/**
	 * @return Windows MAC Address
	 * @throws IOException
	 * @since 25 de fev de 2019
	 * @author Ulisses Lima
	 */
	private static List<String> winMacAddress() throws IOException {
		List<String> macList = new ArrayList<String>();
		Shell.process("ipconfig /all", line -> {
			line = line.toLowerCase();

			if ((line.contains("endere") && line.contains("sico"))
					|| (line.contains("address") && line.contains("physical"))) {
				macList.add(line.substring(line.indexOf(":") + 2).trim());
			}
		});

		return macList;
	}

	/**
	 * @return Unix MAC Address
	 * @throws IOException
	 * @since 25 de fev de 2019
	 * @author Ulisses Lima
	 */
	private static List<String> unixMacAddress() throws IOException {
		List<String> macList = new ArrayList<>();
		Shell.process("/sbin/ifconfig -a", line -> {
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
}
