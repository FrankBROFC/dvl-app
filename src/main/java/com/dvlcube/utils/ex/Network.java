package com.dvlcube.utils.ex;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Consumer;

/**
 * @since 25 de fev de 2019
 * @author Ulisses Lima
 */
public class Network {
	/**
	 * @return bound ipv4 addresses excluding localhost.
	 * @throws SocketException
	 * @since 27 de fev de 2019
	 * @author Ulisses Lima
	 */
	public static List<String> ifAddresses() {
		List<String> interfaces = new ArrayList<String>();
		interfaces(i -> {
			String address = i.getHostAddress();
			if (address.contains(":") || address.startsWith("127"))
				return;

			interfaces.add(address);
		});
		Collections.sort(interfaces);
		return interfaces;
	}

	/**
	 * @return first interface address
	 * @since 12 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static String firstIf() {
		return ifAddresses().iterator().next();
	}

	/**
	 * @return list
	 * @since 12 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static List<String> ifHosts() {
		List<String> interfaces = new ArrayList<String>();
		interfaces(i -> interfaces.add(i.getHostName()));
		return interfaces;
	}

	/**
	 * @param processor
	 * @since 12 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static void interfaces(Consumer<InetAddress> processor) {
		try {
			Enumeration<?> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration<?> ee = n.getInetAddresses();
				while (ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();
					processor.accept(i);
				}
			}
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
	}
}
