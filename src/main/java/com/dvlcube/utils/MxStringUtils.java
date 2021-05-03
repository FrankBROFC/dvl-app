package com.dvlcube.utils;

import java.beans.Introspector;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.Random;
import java.util.function.BiFunction;

import com.dvlcube.utils.query.MxQuery;

/**
 * @author Ulisses Lima
 * @since 28/02/2013
 */
public class MxStringUtils {
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * Behavior identical to CSS text-transform: uppercase. Only the first character
	 * will be transformed, for performance purposes.
	 * <p>
	 * e.g.: <br>
	 * capitalize("foo") <br>
	 * -> Foo
	 * <p>
	 * capitalize("baR") <br>
	 * -> BaR <br>
	 * 
	 * @param string string.
	 * @return the string with the first letter in uppercase.
	 * @author Ulisses Lima
	 * @since 26/10/2013
	 */
	public static String capitalize(String string) {
		if (string == null)
			return null;

		Character capital = string.charAt(0);
		String initial = capital.toString().toUpperCase();
		if (string.length() > 1)
			return initial + string.substring(1, string.length());
		else
			return initial;
	}

	/**
	 * e.g.: <br>
	 * capitalize("foo", "baR", "BAZ") <br>
	 * -> Foo, BaR, BAZ
	 * 
	 * @param strings strings.
	 * @return an array containing every item capitalized.
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public static String[] capitalize(String... strings) {
		String[] capitalized = new String[strings.length];
		for (int i = 0; i < strings.length; i++) {
			capitalized[i] = capitalize(strings[i]);
		}
		return capitalized;
	}

	/**
	 * @param string
	 * @return the string, with the first letter decapitalized.
	 * @author Ulisses Lima
	 * @since 26/10/2013
	 */
	public static String decapitalize(String string) {
		return Introspector.decapitalize(string);
	}

	/**
	 * @param string the string to escape.
	 * @return a string formatted for use in web pages.
	 * @author Ulisses Lima
	 * @since 15/03/2013
	 */
	public static String escapeHtml(final String string) {
		return string.replace("\"", "&quot;");
	}

	/**
	 * @param string the string to check.
	 * @return whether a string contains no printable characters.
	 * @author Ulisses Lima
	 * @since 25/04/2013
	 */
	public static boolean isBlank(final String string) {
		if (string == null) {
			return true;
		}
		return string.trim().isEmpty();
	}

	/**
	 * @param o the toString value will be considered
	 * @return true if null or blank
	 * @since 27 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static boolean isBlank(final Object o) {
		if (o == null)
			return true;
		return isBlank(o.toString());
	}

	/**
	 * @param o the toString value will be considered
	 * @return true if not null and not blank
	 * @since 27 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static boolean isNotBlank(final Object o) {
		if (o == null)
			return false;

		return isNotBlank(o.toString());
	}

	/**
	 * @param string
	 * @return true if string is not blank
	 * @since 26 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static boolean isNotBlank(final String string) {
		return !isBlank(string);
	}

	/**
	 * @param strings
	 * @return true if any of the strings are blank.
	 * @since 13 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static boolean isAnyBlank(final String... strings) {
		if (strings == null) {
			return true;
		}
		for (String string : strings) {
			if (isBlank(string))
				return true;
		}
		return false;
	}

	/**
	 * @param strings
	 * @return true if any of the strings are blank.
	 * @since 13 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static boolean isAllBlank(final String... strings) {
		if (strings == null) {
			return true;
		}
		int blanks = 0;
		for (String string : strings) {
			if (isBlank(string))
				blanks++;
		}

		return blanks == strings.length;
	}

	/**
	 * @param array the array to randomize.
	 * @return an array that is a result of mixing the index values of the first
	 *         array.
	 * @author Ulisses Lima
	 * @since 23/04/2013
	 */
	private static char[] randomizeIndex(char[] array) {
		char[] randomArray = new char[array.length];
		List<Integer> set = new ArrayList<>();
		while (set.size() < array.length) {
			int index = (int) (Math.random() * array.length);
			if (!set.contains(index)) {
				set.add(index);
			}
		}
		Queue<Integer> queue = new LinkedList<>(set);
		int i = 0;
		while (!queue.isEmpty()) {
			randomArray[i++] = array[queue.poll()];
		}
		return randomArray;
	}

	/**
	 * Will alter the order of the letters in the String representation of an
	 * Object.
	 * 
	 * @param object the object.
	 * @return a scrambled string representation of the object.
	 * @author Ulisses Lima
	 * @since 23/04/2013
	 */
	public static String scramble(Object object) {
		String string = object.toString();
		char[] array = string.toCharArray();
		char[] randomArray = randomizeIndex(array);
		return new String(randomArray);
	}

	/**
	 * Created to generate a valid JSON output, but was not tested against all
	 * circumstances.
	 * 
	 * @param object object to stringify.
	 * @return A stringified representation of the Object.
	 * @author Ulisses Lima
	 * @since 29/03/2013
	 */
	public static String stringify(Object object) {
		try {
			StringBuilder builder = new StringBuilder();
			Field[] fields = object.getClass().getDeclaredFields();
			builder.append("{");
			for (Field field : fields) {
				builder.append(", ");
				field.setAccessible(true);
				builder.append("\n\t\"" + field.getName() + "\": ");
				try {
					Object val = field.get(object);
					if (field.getType().isArray()) {
						// TODO colocar entre aspas cada item do array
						builder.append(Arrays.toString((Object[]) val));
					} else if (val != null && val instanceof Date) {
						// @warning(ulisses) time zone desconsiderado
						builder.append("\"" + df.format((Date) val) + "\"");
					} else if (val != null && val instanceof Calendar) {
						// @warning(ulisses) time zone desconsiderado
						builder.append("\"" + df.format(((Calendar) val).getTime()) + "\"");
					} else {
						boolean notObject = val != null && !val.toString().contains("{");
						if (notObject)
							builder.append("\"" + val + "\"");
						else
							builder.append(val);
					}
				} catch (Exception e) {
					builder.append("n/a");
				}
			}
			builder.append("\n}");
			return builder.toString().replaceFirst(", ", "");
		} catch (Exception e) {
			return "Couldn't stringify: " + e.getMessage();
		}
	}

	/**
	 * @param o
	 * @return
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public static String camelCase(String o) {
		return null;
	}

	/**
	 * @param nWords
	 * @return random words
	 * @since 14 de fev de 2019
	 * @author Ulisses Lima
	 */
	public static String[] randomWords(int nWords) {
		String[] randomStrings = new String[nWords];
		Random random = new Random();
		for (int i = 0; i < nWords; i++) {
			char[] word = new char[random.nextInt(8) + 3];
			// words of length 3 through 10. (1 and 2 letter words are
			// boring.)
			for (int j = 0; j < word.length; j++) {
				word[j] = (char) ('a' + random.nextInt(26));
			}
			randomStrings[i] = new String(word);
		}
		return randomStrings;
	}

	public static String randomWord() {
		return randomWords(1)[0];
	}

	/**
	 * @param string
	 * @return b64 encoded string.
	 * @since 11 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static String b64(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	/**
	 * @param string
	 * @return b64 encoded string.
	 * @since 11 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static String b64(String string) {
		return b64(string.getBytes());
	}

	/**
	 * @param bytes
	 * @return decoded b64 data
	 * @since 11 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static String decodeB64(byte[] bytes) {
		return new String(Base64.getDecoder().decode(bytes));
	}

	/**
	 * @param bytes
	 * @return byte array
	 * @since 12 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static byte[] decodeB64raw(byte[] bytes) {
		return Base64.getDecoder().decode(bytes);
	}

	/**
	 * @param string
	 * @return byte array
	 * @since 12 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static byte[] decodeB64raw(String string) {
		return decodeB64raw(string.getBytes());
	}

	/**
	 * @param string
	 * @return decoded data
	 * @since 11 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static String decodeB64(String string) {
		return decodeB64(string.getBytes());
	}

	/**
	 * @param string
	 * @return md5 checksum
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @since 14 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static String md5sum(String source) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] bytes = md.digest(source.getBytes("UTF-8"));
		return getString(bytes);
	}

	private static String getString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			String hex = Integer.toHexString((int) 0x00FF & b);
			if (hex.length() == 1) {
				sb.append("0");
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	/**
	 * @param string
	 * @return reversed string.
	 * @since 15 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static String reverse(String string) {
		return new StringBuilder(string).reverse().toString();
	}

	/**
	 * @param strings
	 * @return fist non blank string
	 * @since 26 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static String coalesce(String... strings) {
		return MxQuery.coalesce(string -> isNotBlank(string), strings);
	}

	/**
	 * @param input properties format
	 * @param key   property to get
	 * @return property from a properties input
	 * @since 29 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static String prop(InputStream input, Object key) {
		try {
			return props(input).get(key.toString());
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param input    input stream ou nome de um arquivo do classpath
	 * @param function
	 * @return props map
	 * @throws IOException
	 * @since 29 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static Map<String, String> props(InputStream input, BiFunction<String, String, String> function)
			throws IOException {
		if (input == null)
			return null;

		Properties props = new Properties();
		props.load(input);

		Map<String, String> map = new HashMap<String, String>();
		props.forEach((k, v) -> {
			String value = (String) v;
			if (function != null)
				value = function.apply((String) k, (String) v);
			map.put((String) k, value);
		});
		return map;
	}

	/**
	 * @param input
	 * @return props map
	 * @throws IOException
	 * @since 29 de mar de 2019
	 * @author Ulisses Lima
	 */
	public static Map<String, String> props(InputStream input) throws IOException {
		return props(input, null);
	}

	/**
	 * <p>
	 * Checks if CharSequence contains a search CharSequence irrespective of case,
	 * handling {@code null}. Case-insensitivity is defined as by
	 * {@link String#equalsIgnoreCase(String)}.
	 *
	 * <p>
	 * A {@code null} CharSequence will return {@code false}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.contains(null, *) = false
	 * StringUtils.contains(*, null) = false
	 * StringUtils.contains("", "") = true
	 * StringUtils.contains("abc", "") = true
	 * StringUtils.contains("abc", "a") = true
	 * StringUtils.contains("abc", "z") = false
	 * StringUtils.contains("abc", "A") = true
	 * StringUtils.contains("abc", "Z") = false
	 * </pre>
	 *
	 * @param str       the CharSequence to check, may be null
	 * @param searchStr the CharSequence to find, may be null
	 * @return true if the CharSequence contains the search CharSequence
	 *         irrespective of case or false if not or {@code null} string input
	 * @since 3.0 Changed signature from containsIgnoreCase(String, String) to
	 *        containsIgnoreCase(CharSequence, CharSequence)
	 */
	public static boolean containsIgnoreCase(final CharSequence str, final CharSequence searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		final int len = searchStr.length();
		final int max = str.length() - len;
		for (int i = 0; i <= max; i++) {
			if (regionMatches(str, true, i, searchStr, 0, len)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Green implementation of regionMatches.
	 *
	 * @param cs         the {@code CharSequence} to be processed
	 * @param ignoreCase whether or not to be case insensitive
	 * @param thisStart  the index to start on the {@code cs} CharSequence
	 * @param substring  the {@code CharSequence} to be looked for
	 * @param start      the index to start on the {@code substring} CharSequence
	 * @param length     character length of the region
	 * @return whether the region matched
	 */
	static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
			final CharSequence substring, final int start, final int length) {
		if (cs instanceof String && substring instanceof String) {
			return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
		} else {
			int index1 = thisStart;
			int index2 = start;
			int tmpLen = length;

			while (tmpLen-- > 0) {
				char c1 = cs.charAt(index1++);
				char c2 = substring.charAt(index2++);

				if (c1 == c2) {
					continue;
				}

				if (!ignoreCase) {
					return false;
				}

				// The same check as in String.regionMatches():
				if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
						&& Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
					return false;
				}
			}

			return true;
		}
	}
}
