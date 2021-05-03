package com.dvlcube.utils.query;

import com.dvlcube.utils.AssertionUtils;

/**
 * @since Aug 10, 2017
 * @author Ulisses Lima
 */
public abstract class MxUtils {
	/**
	 * @param paramName
	 *            nome do parâmetro, exibido na exceção.
	 * @since Feb 26, 2016
	 * @author Ulisses Lima
	 */
	public MxUtils assertNotNull(String paramName) {
		AssertionUtils.notNull("\"" + paramName + "\"", val());
		return this;
	}

	/**
	 * @since Feb 26, 2016
	 * @author Ulisses Lima
	 */
	public MxUtils assertNotNull() {
		AssertionUtils.notNull("string", val());
		return this;
	}

	/**
	 * @return the original object this wrapper represents.
	 * @since Aug 10, 2017
	 * @author Ulisses Lima
	 */
	public abstract Object val();
}
