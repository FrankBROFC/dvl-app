package com.dvlcube.utils.query;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import com.dvlcube.utils.AssertionUtils;
import com.dvlcube.utils.MxStringUtils;
import com.dvlcube.utils.ObjectUtils;
import com.dvlcube.utils.interfaces.Identifiable;

/**
 * @author Ulisses Lima
 * @since 14/04/2013
 */
public class MxObject {
	public String[] arr;
	public Object o;

	/**
	 * @param object2
	 * @author Ulisses Lima
	 * @param objects
	 * @since 14/04/2013
	 */
	public MxObject(Object object, String... objects) {
		o = object;
		arr = objects;
	}

	/**
	 * @param objects objects to compare.
	 * @return true if this object is equal to any of the other objects.
	 * @since 07/07/2013
	 * @author Ulisses Lima
	 */
	public boolean in(Object... objects) {
		return ObjectUtils.in(o, objects);
	}

	/**
	 * @return a string representing the junction between the objects, separated by
	 *         commas.
	 * @author Ulisses Lima
	 * @since 20/07/2013
	 */
	public String join() {
		return join(",");
	}

	/**
	 * @param separator
	 * @return a string representing the junction between the objects, using the
	 *         provided separator.
	 * @author Ulisses Lima
	 * @since 20/07/2013
	 */
	public String join(String separator) {
		StringBuilder builder = new StringBuilder(separator);
		builder.append(o.toString());
		for (String string : arr) {
			builder.append(separator).append(string);
		}
		return builder.toString().replaceFirst(separator, "");
	}

	/**
	 * Merges this object's attributes with another Object, ignoring null values.
	 * 
	 * @param target This object will have its properties updated, according to this
	 *               object's values.
	 * @return the target object
	 * @author Ulisses Lima
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @since 14/04/2013
	 */
	public <T> T copyTo(T target)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		if (o == null || target == null)
			return null;

		if (target instanceof MxObject) {
			ObjectUtils.copyNonNull(o, ((MxObject) target).o);
		} else {
			ObjectUtils.copyNonNull(o, target);
		}
		return target;
	}

	/**
	 * @return representação string.
	 * @author Ulisses Lima
	 * @since 14/04/2013
	 */
	public String stringify() {
		return MxStringUtils.stringify(o);
	}

	@Override
	public String toString() {
		return o.toString();
	}

	/**
	 * 
	 * @since Feb 26, 2016
	 * @author Ulisses Lima
	 */
	public MxObject assertNotNull() {
		AssertionUtils.notNull("object", o);
		return this;
	}

	/**
	 * 
	 * @since Feb 26, 2016
	 * @author Ulisses Lima
	 */
	public MxObject assertNotNull(String paramName) {
		AssertionUtils.notNull(paramName, o);
		return this;
	}

	/**
	 * @return true if object is {@link Identifiable} and its id property is not
	 *         null. A special case is checked for String ids, where it will return
	 *         false for blank values.
	 * @since 27 de mar de 2019
	 * @author Ulisses Lima
	 */
	public boolean hasId() {
		if (!(o instanceof Identifiable<?>))
			return false;

		Serializable idValue = ((Identifiable<?>) o).getId();
		if (idValue == null)
			return false;

		if (!(idValue instanceof String))
			return true;

		return MxStringUtils.isNotBlank(idValue);
	}

	/**
	 * @param other
	 * @return true if not eq
	 * @since 17 de abr de 2019
	 * @author Ulisses Lima
	 */
	public boolean not(Object other) {
		return !eq(other);
	}

	/**
	 * @param other
	 * @return true if eq
	 * @since 17 de abr de 2019
	 * @author Ulisses Lima
	 */
	public boolean eq(Object other) {
		if (other == null)
			return false;

		return o.equals(other);
	}
}