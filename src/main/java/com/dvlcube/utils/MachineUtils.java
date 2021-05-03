package com.dvlcube.utils;

import java.lang.management.ManagementFactory;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.dvlcube.utils.ex.MacAddress;
import com.dvlcube.utils.ex.Network;

/**
 * @since 31 de mai de 2019
 * @author Ulisses Lima
 */
public class MachineUtils {
	/**
	 * https://stackoverflow.com/questions/18489273/how-to-get-percentage-of-cpu-usage-of-os-from-java/21962037
	 * 
	 * @param type
	 * @return load
	 * @since 31 de mai de 2019
	 * @author Ulisses Lima
	 */
	public static Double load(String type) {
		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
			AttributeList list = mbs.getAttributes(name, new String[] { type });

			if (list.isEmpty())
				return 0d;

			Attribute att = (Attribute) list.get(0);
			Double value = (Double) att.getValue();

			// usually takes a couple of seconds before we get real values
			if (value < 0)
				return value;

			// returns a percentage value with 1 decimal point precision
			return ((int) (value * 1000) / 10.0);
		} catch (Throwable e) {
			return -1d;
		}
	}

	/**
	 * https://stackoverflow.com/questions/18489273/how-to-get-percentage-of-cpu-usage-of-os-from-java/21962037
	 * 
	 * @return vm load
	 * @throws Exception
	 * @since 31 de mai de 2019
	 * @author Ulisses Lima
	 */
	public static Double jvmLoad() {
		return load("ProcessCpuLoad");
	}

	/**
	 * https://stackoverflow.com/questions/18489273/how-to-get-percentage-of-cpu-usage-of-os-from-java/21962037
	 * 
	 * @return system load
	 * @throws Exception
	 * @since 31 de mai de 2019
	 * @author Ulisses Lima
	 */
	public static Double systemLoad() {
		return load("SystemCpuLoad");
	}

	/**
	 * @return network address
	 * @since 31 de mai de 2019
	 * @author Ulisses Lima
	 */
	public static String networkAddress() {
		try {
			return Network.firstIf();
		} catch (Throwable e) {
			return null;
		}
	}

	/**
	 * @return first mac address
	 * @since 31 de mai de 2019
	 * @author Ulisses Lima
	 */
	public static String macAddress() {
		return MacAddress.findFirst();
	}
}
