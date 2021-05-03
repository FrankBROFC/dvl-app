package com.dvlcube.utils.query;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

import com.dvlcube.utils.MxStringUtils;
import com.dvlcube.utils.ex.MxDebug;
import com.dvlcube.utils.ex.Range;
import com.dvlcube.utils.interfaces.Identifiable;

/**
 * Exemplos de uso:
 * <p>
 * ________
 * <p>
 * Strings:
 * <p>
 * $("foo").isNotBlank();<br>
 * $("foo").escapeHtml().capitalize();<br>
 * $("foo").assertNotBlank();<br>
 * <p>
 * _________
 * <p>
 * Arquivos:
 * <p>
 * $f("/home/arquivo").mkdirs().newFile();<br>
 * <p>
 * _________
 * <p>
 * Imagens:
 * <p>
 * 
 * @author Ulisses Lima
 * @since 14/04/2013
 */
@SuppressWarnings("unchecked")
public class MxQuery {
	public static final Random random = new Random();

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static MxImage $(BufferedImage image) {
		return new MxImage(image);
	}

	public static MxString $(char... string) {
		if (string != null && string.length > 0) {
			return new MxString(new String(string));
		} else {
			return new MxString();
		}
	}

	public static MxString $(char[] string, int... indexes) {
		return new MxString(string, indexes);
	}

	public static <T> MxClass<T> $(Class<T> c) {
		return new MxClass<T>(c);
	}

	public static MxNumber $(double n) {
		return new MxNumber(n);
	}

	public static MxNumber $(double n, double range0, double range1) {
		return new MxNumber(n, range0, range1);
	}

	public static MxNumber $(double n, Range<Double> range) {
		return new MxNumber(n, range);
	}

	public static MxNumber $(int n, Range<Double> range) {
		return new MxNumber(n, range);
	}

	public static MxObject $(Object object, String... objects) {
		return new MxObject(object, objects);
	}

	public static MxString $(String string) {
		return new MxString(string);
	}

	public static MxString $(String string, int times) {
		return new MxString(string, times);
	}

	public static <T> MxArray<T> $(T[] array) {
		return new MxArray<T>(array);
	}

	@SafeVarargs
	public static <T> MxArray<T> $(T[] array, T[]... arrays) {
		return new MxArray<T>(array, arrays);
	}

	public static MxDate $date() {
		return new MxDate();
	}

	public static MxDate $date(String pattern) {
		return new MxDate(pattern);
	}

	/**
	 * @param date
	 * @return MxDate
	 * @since 2 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static MxDate $(Date date) {
		return new MxDate(date);
	}

	/**
	 * @param date
	 * @return MxDate
	 * @since 2 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static MxDate $(Calendar date) {
		return new MxDate(date);
	}

	public static MxDebug $debug() {
		return new MxDebug();
	}

	public static MxFile $f(MxString path) {
		return new MxFile(path);
	}

	public static MxFile $f(String path) {
		return new MxFile(path);
	}

	/**
	 * @param file
	 * @return MxFile
	 * @since 12 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static MxFile $(File file) {
		return new MxFile(file.getAbsolutePath());
	}

	/**
	 * @param path
	 * @return MxFile
	 * @since 24 de abr de 2019
	 * @author Ulisses Lima
	 */
	public static MxFile $(Path path) {
		return new MxFile(path);
	}

	/**
	 * @param path
	 * @return temp file
	 * @since 12 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static MxTmpFile $tmp(String path) {
		return new MxTmpFile(path);
	}

	public static MxImage $img(Class<?> origin, String path) {
		return new MxImage(origin, path);
	}

	public static MxImage $img(int width, int height) {
		return new MxImage(width, height);
	}

	public static MxImage $img(String path) {
		return new MxImage(path);
	}

	public static void $out(Object object) {
		System.out.println(object);
	}

	public static void $printf(String message, Object... objects) {
		System.out.printf(message, objects);
	}

	public static MxBoolean $(Boolean bool) {
		return new MxBoolean(bool);
	}

	public static <T> MxCollection<T> $(Collection<T> c) {
		return new MxCollection<T>(c);
	}

	/**
	 * @param map
	 * @return MxMap
	 * @since 17 de abr de 2019
	 * @author Ulisses Lima
	 */
	public static <K, V> MxMap<K, V> $(Map<K, V> map) {
		return new MxMap<K, V>(map);
	}

	/**
	 * @param c
	 * @return iterable
	 * @since 5 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static <T> MxIterable<T> $(Iterable<T> c) {
		return new MxIterable<T>(c);
	}

	/**
	 * @param list
	 * @return random list item
	 * @since 2 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static <T> T rnd(List<T> list) {
		if (list == null || list.isEmpty())
			return null;

		return list.get(random.nextInt(list.size()));
	}

	/**
	 * @param array
	 * @return random array item
	 * @since 2 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static <T> T rnd(Object[] array) {
		if (array == null || array.length < 1)
			return null;

		return (T) array[random.nextInt(array.length)];
	}

	/**
	 * @param bytes
	 * @return MxString decoded from b64 data.
	 * @since 11 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static MxString $b64(byte[] bytes) {
		return new MxString(MxStringUtils.decodeB64(bytes));
	}

	/**
	 * @param bytes
	 * @return MxBin binary data
	 * @since 27 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static MxBin $(byte[] bytes) {
		return new MxBin(bytes);
	}

	/**
	 * @param bytes
	 * @return MxString decoded from a b64 string.
	 * @since 11 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static MxString $b64(String string) {
		return new MxString(MxStringUtils.decodeB64(string));
	}

	/**
	 * @param identifiable
	 * @return optional id.
	 * @since 15 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static <S extends Serializable> Optional<S> id(Identifiable<S> identifiable) {
		if (identifiable == null)
			return Optional.empty();

		return Optional.ofNullable(identifiable.getId());
	}

	/**
	 * @param criteria criteria for accepting a value as non null
	 * @param arr      array of values
	 * @return first non null object, according to a criteria, if provided
	 * @since 26 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static <O> O coalesce(Predicate<O> criteria, O... arr) {
		if (arr == null || arr.length < 1)
			return null;

		for (O o : arr) {
			if (o != null && (criteria == null || criteria.test(o)))
				return o;
		}
		return null;
	}

	/**
	 * @param arr array of values
	 * @return first non null object
	 * @since 26 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static <O> O coalesce(O... arr) {
		return coalesce(null, arr);
	}
}
