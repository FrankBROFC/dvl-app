package com.dvlcube.utils.interfaces;

import java.io.Serializable;

import com.dvlcube.utils.MxStringUtils;

/**
 * @since 14 de mar de 2019
 * @author Ulisses Lima
 */
public interface Identifiable<S extends Serializable> extends Serializable {
	/**
	 * @return id
	 * @since 14 de mar de 2019
	 * @author Ulisses Lima
	 */
	S getId();

	/**
	 * @param s
	 * @return true if this ID is like another ID
	 * @since 2 de abr de 2019
	 * @author Ulisses Lima
	 */
	default boolean like(S s) {
		if (s == null)
			return false;

		if (s instanceof String && getId() instanceof String) {
			String me = (String) getId();
			String that = (String) s;
			return MxStringUtils.containsIgnoreCase(me, that);
		}

		return s.equals(s);
	}

	/**
	 * @return an id value compatible with the autocomplete component.
	 * @since 2 de abr de 2019
	 * @author Ulisses Lima
	 */
	default String autocompleteField() {
		if (getId() == null || !(getId() instanceof String))
			return null;

		return (String) getId();
	}
}
