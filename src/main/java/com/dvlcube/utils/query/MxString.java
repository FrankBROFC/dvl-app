package com.dvlcube.utils.query;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.dvlcube.utils.AssertionUtils;
import com.dvlcube.utils.FileUtils;
import com.dvlcube.utils.MxStringUtils;
import com.dvlcube.utils.ex.DatePatterns;
import com.dvlcube.utils.ex.Shell;

/**
 * @author Ulisses Lima
 * @since 28/02/2013
 */
public class MxString extends MxUtils {
	public String o = "";

	/**
	 * @see com.dvlcube.utils.query.MxUtils#val()
	 */
	@Override
	public String val() {
		return o;
	}

	public MxString() {

	}

	public MxString(Object o) {
		if (o != null)
			this.o = o.toString();
	}

	/**
	 * @param string
	 * @param indexes
	 * @author Ulisses Lima
	 * @since 13/07/2013
	 */
	public MxString(char[] string, int... indexes) {
		if (string != null) {
			StringBuilder builder = new StringBuilder();
			for (int i : indexes) {
				if (i < string.length) {
					builder.append(string[i]);
				}
			}
			reset(builder.toString());
		} else {
			reset("");
		}
	}

	/**
	 * @param string
	 * @author Ulisses Lima
	 * @since 28/02/2013
	 */
	public MxString(final String string) {
		reset(string);
	}

	/**
	 * @param string string.
	 * @param times  times to repeat.
	 * @author Ulisses Lima
	 * @since 13/07/2013
	 */
	public MxString(String string, int times) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < times; i++) {
			builder.append(string);
		}
		reset(builder.toString());
	}

	public MxString escapeHTML() {
		return new MxString(MxStringUtils.escapeHtml(o));
	}

	public Integer i() {
		try {
			return Integer.parseInt(o);
		} catch (Throwable e) {
			return null;
		}
	}

	public boolean isBlank() {
		return MxStringUtils.isBlank(o);
	}

	public boolean isNotBlank() {
		return !isBlank();
	}

	public final void reset(String string) {
		if (string == null)
			string = "";
		o = string;
	}

	public MxString scramble() {
		o = MxStringUtils.scramble(o);
		return this;
	}

	@Override
	public String toString() {
		return o;
	}

	/**
	 * @param paramName nome do parâmetro, exibido na exceção.
	 * @since Feb 26, 2016
	 * @author Ulisses Lima
	 */
	public MxString asserNotBlank(String paramName) {
		AssertionUtils.notBlank("\"" + paramName + "\"", o);
		return this;
	}

	/**
	 * @since Feb 26, 2016
	 * @author Ulisses Lima
	 */
	public MxString asserNotBlank() {
		AssertionUtils.notBlank("string", o);
		return this;
	}

	public MxString capitalize() {
		o = MxStringUtils.capitalize(o);
		return this;
	}

	public MxString decapitalize() {
		o = MxStringUtils.decapitalize(o);
		return this;
	}

	public MxString replace(String regex, String replacement) {
		o = o.replaceAll(regex, replacement);
		return this;
	}

	/**
	 * @return converts to long. returns null in case of exception.
	 * @author Ulisses Lima
	 * @since 26/10/2013
	 */
	public Long l() {
		try {
			return Long.parseLong(o);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param delidvler
	 * @param strings
	 * @return single string joined by delidvler.
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public MxString join(String delidvler, String... strings) {
		o = String.join(delidvler, strings);
		return this;
	}

	/**
	 * Transforms this string into lower case and then capitalizes the first
	 * character.
	 * <p>
	 * If there are underscores, they are stripped into different words.
	 * <p>
	 * e.g.: <br>
	 * $("NEW_TYPE").camelCase()<br>
	 * -> NewType
	 * 
	 * @since Jul 31, 2017
	 * @author Ulisses Lima
	 */
	public MxString camelCase() {
		if (o == null)
			return null;

		o = String.join("", MxStringUtils.capitalize(o.toLowerCase().split("_")));
		return this;
	}

	/**
	 * @param property will be env value or vm value or null
	 * @param fallback
	 * @return this
	 * @since 25 de fev de 2019
	 * @author Ulisses Lima
	 */
	public MxString env(String fallback) {
		String val = fallback;

		String env = System.getenv(o);
		if (env != null) {
			val = env;
		} else {
			String vm = System.getProperty(o);
			if (vm != null)
				val = vm;
		}

		o = val;
		return this;
	}

	/**
	 * @param property will be env value or vm value or null
	 * @return this
	 * @since 25 de fev de 2019
	 * @author Ulisses Lima
	 */
	public MxString env() {
		return env(null);
	}

	/**
	 * @return this as b64 encoded value.
	 * @since 11 de mar de 2019
	 * @author Ulisses Lima
	 */
	public MxString b64() {
		o = MxStringUtils.b64(o);
		return this;
	}

	/**
	 * @return this
	 * @since 11 de mar de 2019
	 * @author Ulisses Lima
	 */
	public MxString decodeB64() {
		o = MxStringUtils.decodeB64(o);
		return this;
	}

	/**
	 * @return bytes
	 * @since 12 de mar de 2019
	 * @author Ulisses Lima
	 */
	public byte[] decodeB64raw() {
		return MxStringUtils.decodeB64raw(o);
	}

	/**
	 * @return md5 checksum
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @since 14 de mar de 2019
	 * @author Ulisses Lima
	 */
	public String md5sum() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return MxStringUtils.md5sum(o);
	}

	/**
	 * Reverses the current string.
	 * 
	 * @return this
	 * @since 15 de mar de 2019
	 * @author Ulisses Lima
	 */
	public MxString rev() {
		o = MxStringUtils.reverse(o);
		return this;
	}

	/**
	 * @param delidvler
	 * @param field
	 * @return field with the delidvler
	 * @since 15 de mar de 2019
	 * @author Ulisses Lima
	 */
	public MxString cut(String delidvler, int field) {
		o = o.split(delidvler)[field];
		return this;
	}

	/**
	 * @param part part to remove
	 * @return string sans part
	 * @since 29 de mar de 2019
	 * @author Ulisses Lima
	 */
	public MxString del(String part) {
		o = o.replace(part, "");
		return this;
	}

	/**
	 * @param delidvler
	 * @return split array
	 * @since 29 de mar de 2019
	 * @author Ulisses Lima
	 */
	public String[] cut(String delidvler) {
		return o.split(delidvler);
	}

	/**
	 * @return this string sans file extension
	 * @since 29 de mar de 2019
	 * @author Ulisses Lima
	 */
	public MxString removeExtension() {
		o = FileUtils.removeExtension(o);
		return this;
	}

	/**
	 * @param other
	 * @return true if this does not equal other
	 * @since 5 de abr de 2019
	 * @author Ulisses Lima
	 */
	public boolean not(Object other) {
		return !eq(other);
	}

	/**
	 * @param other
	 * @return true if this equals other
	 * @since 5 de abr de 2019
	 * @author Ulisses Lima
	 */
	public boolean eq(Object other) {
		if (other == null && o == null)
			return true;

		if (other == null)
			return false;

		return o.trim().equals(other.toString().trim());
	}

	/**
	 * @return guessed type
	 * @since 16 de abr de 2019
	 * @author Ulisses Lima
	 */
	public Object guess() {
		if (o == null)
			return null;

		if (o.matches("\\d\\d\\d\\d-.*")) {
			if (o.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d")) {
				return DatePatterns.DATE.parse(o);
			}

			if (o.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d")) {
				return DatePatterns.DATE_TIME_NO_SECS.parse(o);
			}

			if (o.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d")) {
				return DatePatterns.DATE_TIME.parse(o);
			}
		} else if (o.matches("\\d+")) {
			return Long.valueOf(o);
		} else if (o.matches("\\d+.\\d+")) {
			return Double.valueOf(o);
		} else if (o.matches("true|false")) {
			return Boolean.valueOf(o);
		}

		return o.equals("null") ? null : o;
	}

	/**
	 * @param expression
	 * @return grepped lines
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MxString grep(String expression) {
		String cmd = "echo";
		String input = o;

		if (o.length() > 10000) {
			try {
				cmd = "cat";
				String tmp = new MxFile().write(o).o.getAbsolutePath();
				input = tmp;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		o = Shell.exec(String.format("%s '%s' | grep %s", cmd, input, expression));
		return this;
	}

	/**
	 * @param n
	 * @return first n lines
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MxString head(Integer n) {
		range("head", n);
		return this;
	}

	/**
	 * @return this
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MxString head() {
		return head(null);
	}

	/**
	 * @return this.
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MxString tail() {
		return tail(null);
	}

	/**
	 * @param n
	 * @return this
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MxString tail(Integer n) {
		range("tail", n);
		return this;
	}

	/**
	 * @param op
	 * @param n
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	private void range(String op, Integer n) {
		if (n == null)
			n = 10;

		String cmd = "echo";
		String input = o;

		if (o.length() > 10000) {
			try {
				cmd = "cat";
				input = new MxFile().write(o).o.getAbsolutePath();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		o = Shell.exec(String.format("%s '%s' | %s -%d", cmd, input, op, n));
	}
}
