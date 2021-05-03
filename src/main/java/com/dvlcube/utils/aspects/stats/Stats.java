package com.dvlcube.utils.aspects.stats;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @see StatsAspect
 * @since 29/11/2018
 * @author Ulisses Lima
 */
public class Stats {
	public static final Map<String, Stat> data = new HashMap<>();

	public static final long MINUTE = 1 * 1000 * 60;
	public static final long HOUR = 1 * 1000 * 60 * 60;
	public static final long DAY = 1 * 1000 * 60 * 60 * 24;

	/**
	 * @param id
	 * @param start
	 * @return Stat
	 * @since 04/12/2018
	 * @author Ulisses Lima
	 */
	public static Stat split(String id, Long start) {
		if (id == null)
			return null;

		long elapsed = System.currentTimeMillis() - start;
		Stat stat = data.get(id);
		if (stat != null) {
			stat.split(elapsed);
		} else {
			data.put(id, stat = new Stat(id, elapsed));
		}
		return stat;
	}

	/**
	 * @param id
	 * @return Stat
	 * @since 04/12/2018
	 * @author Ulisses Lima
	 */
	public static Stat error(String id) {
		if (id == null)
			return null;

		Stat stat = data.get(id);
		if (stat != null) {
			stat.error();
		} else {
			stat = new Stat(id);
			stat.error();
			data.put(id, stat);
		}
		return stat;
	}

	/**
	 * @return String table
	 * @since 04/12/2018
	 * @author Ulisses Lima
	 */
	public static String table(Double timeFactor) {
		StringBuilder s = new StringBuilder();
		s.append("################### STATS ####################\n");
		s.append("##############################################\n");
		s.append("MIN	MAX	AVG	COUNT	ERRORS	ACTION\n");
		Set<Entry<String, Stat>> entrySet = data.entrySet();
		for (Entry<String, Stat> entry : entrySet) {
			s.append(entry.getValue().table(timeFactor));
			s.append("\n");
		}
		return s.toString();
	}

	/**
	 * @return Stat
	 * @since 11/12/2018
	 * @author Ulisses Lima
	 */
	public static List<Stat> values() {
		List<Stat> s = data.values().stream().collect(Collectors.toList());
		Collections.sort(s);
		return s;
	}

	/**
	 * @param consumer
	 * @since 17 de abr de 2019
	 * @author Ulisses Lima
	 */
	public static void consume(Consumer<Stat> consumer) {
		for (Iterator<Map.Entry<String, Stat>> entries = data.entrySet().iterator(); entries.hasNext();) {
			Map.Entry<String, Stat> entry = entries.next();

			consumer.accept(entry.getValue());
			entries.remove();
		}
	}
}
