package com.dvlcube.utils.ex;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Least recently used map.
 * <p>
 * http://stackoverflow.com/questions/11469045/how-to-lidvl-the-maximum-size-of-
 * a-map-by-removing-oldest-entries-when-lidvl-rea
 * <p>
 * http://stackoverflow.com/questions/7115445/what-is-the-optimal-capacity-and-
 * load-factor-for-a-fixed-size-hashmap
 * 
 * @since Nov 23, 2016
 * @author Ulisses Lima
 */
public class LruMap<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = 1L;

	public final int maxEntries;

	public LruMap(int maxEntries) {
		super(maxEntries * 10 / 100, 0.7f, true);
		this.maxEntries = maxEntries;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > maxEntries;
	}
}
