package com.dvlcube.utils.query;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @param <K>
 * @param <V>
 * @since 17 de abr de 2019
 * @author Ulisses Lima
 */
public class MxMap<K, V> {
	public Map<K, V> o;

	public MxMap(Map<K, V> o) {
		this.o = o;
	}

	public void consume(BiConsumer<K, V> consumer) {
		if (o == null)
			return;

		// TODO testar se não dá concurrent mod exception
		o.entrySet().parallelStream().forEach(entry -> {
			consumer.accept(entry.getKey(), entry.getValue());
			o.remove(entry.getKey());
		});
	}

	/**
	 * Key replacement.
	 * <p>
	 * If key exists, replace it by replacement. The value will be transfered to the
	 * new key, and the original key will be removed.
	 * <p>
	 * e.g.:<br>
	 * map.put("foo", "bar"); <br>
	 * map.get("foo") => "bar" <br>
	 * map.get("zoz") => null
	 * <p>
	 * $(map).replace("foo", "zoz"); <br>
	 * map.get("foo") => null <br>
	 * map.get("zoz") => "bar" <br>
	 * 
	 * @param key         original key
	 * @param replacement new key
	 * @return this
	 * @since 7 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MxMap<K, V> replaceKey(K key, K replacement) {
		boolean found = o.get(key) != null;
		if (found) {
			V val = o.remove(key);
			o.put(replacement, val);
		}
		return this;
	}
}
