package com.dvlcube.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Ulisses Lima
 * @since 06/04/2013
 */
public class ObjectUtils {
	/**
	 * Adapted from http://stackoverflow.com/questions/835416/how-to-copy-properties
	 * -from-one-java-bean-to-another
	 * 
	 * @param from       an object
	 * @param to         to another object
	 * @param ignoreNull whether null values should be copied.
	 * @author Ulisses Lima
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @since 06/04/2013
	 */
	public static void copy(Object from, Object to, boolean ignoreNull)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (to == null)
			return;

		Class<? extends Object> fromClass = from.getClass();
		Class<? extends Object> toClass = to.getClass();

		BeanInfo fromBean = Introspector.getBeanInfo(fromClass);
		BeanInfo toBean = Introspector.getBeanInfo(toClass);

		PropertyDescriptor[] fromDescriptors = fromBean.getPropertyDescriptors();
		HashMap<String, PropertyDescriptor> toDescriptors = map(toBean.getPropertyDescriptors());

		for (PropertyDescriptor fromDescriptor : fromDescriptors) {
			PropertyDescriptor toDescriptor = toDescriptors.get(fromDescriptor.getDisplayName());
			if (toDescriptor != null) {
				if (toDescriptor.getWriteMethod() != null) {
					Object value = get(fromDescriptor, from);
					if (value == null && ignoreNull) {
					} else {
						String packageName = value.getClass().getPackage().getName();
						if (packageName.startsWith("java")
								|| packageName.startsWith("org.hibernate.collection.internal")
								|| value instanceof Enum) {
							toDescriptor.getWriteMethod().invoke(to, get(fromDescriptor, from));
						} else {
							copy(get(fromDescriptor, from), get(toDescriptor, to), ignoreNull);
						}
					}
				}
			}
		}
	}

	/**
	 * @param property the property descriptor.
	 * @param from     the object to call the getter on.
	 * @return the result of calling the getter of the property represented by the
	 *         property descriptor on object "from".
	 * @author Ulisses Lima
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @since 06/04/2013
	 */
	private static Object get(PropertyDescriptor property, Object from)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (property != null && property.getReadMethod() != null) {
			return property.getReadMethod().invoke(from);
		} else
			return null;
	}

	/**
	 * @param propertyDescriptors the array of property descriptors.
	 * @return a hash map composed by a property name and a property descriptor.
	 *         created from an array of property descriptors.
	 * @author Ulisses Lima
	 * @since 06/04/2013
	 */
	private static HashMap<String, PropertyDescriptor> map(PropertyDescriptor[] propertyDescriptors) {
		HashMap<String, PropertyDescriptor> map = new HashMap<>();
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String displayName = descriptor.getDisplayName();
			if (!"class".equals(displayName)) {
				map.put(displayName, descriptor);
			}
		}
		return map;
	}

	public static void safeCopy(Object from, Object to, boolean ignoreNull)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<? extends Object> fromClass = from.getClass();
		Class<? extends Object> toClass = to.getClass();

		BeanInfo fromBean = Introspector.getBeanInfo(fromClass);
		BeanInfo toBean = Introspector.getBeanInfo(toClass);

		PropertyDescriptor[] toPd = toBean.getPropertyDescriptors();
		List<PropertyDescriptor> fromPd = Arrays.asList(fromBean.getPropertyDescriptors());

		for (PropertyDescriptor property : toPd) {
			int index = fromPd.indexOf(property);
			if (index < 0) {
				continue;
			}

			PropertyDescriptor descriptor = fromPd.get(index);
			String displayName = descriptor.getDisplayName();
			if (displayName.equals(property.getDisplayName()) && !displayName.equals("class")) {
				if (property.getWriteMethod() != null) {
					Object value = get(property, from);
					if (value == null && ignoreNull) {
					} else {
						String packageName = value.getClass().getPackage().getName();
						if (!packageName.startsWith("java")) {
							copy(get(property, from), get(property, to), ignoreNull);
						} else {
							property.getWriteMethod().invoke(to, get(descriptor, from));
						}
					}
				}
			}
		}
	}

	/**
	 * Copy but ignore null values.
	 * 
	 * @param from an object
	 * @param to   another object
	 * @author Ulisses Lima
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @since 06/04/2013
	 */
	public static void copyNonNull(Object from, Object to)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		copy(from, to, true);
	}

	/**
	 * @param o       object to compare.
	 * @param objects objects available.
	 * @return true if the object is equal to any of the other objects.
	 * @since 07/07/2013
	 * @author Ulisses Lima
	 */
	public static boolean in(Object o, Object... objects) {
		for (Object object : objects) {
			if (o.equals(object))
				return true;
		}
		return false;
	}
}
