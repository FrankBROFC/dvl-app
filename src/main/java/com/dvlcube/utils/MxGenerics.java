package com.dvlcube.utils;

import java.util.List;
import java.util.Set;

/**
 * @author Ulisses Lima
 * @since 19/03/2013
 */
@SuppressWarnings("unchecked")
public class MxGenerics {
	public static <T> T unchecked(Object object) {
		return (T) object;
	}

	public static <T> List<T> uncheckedList(Object object) {
		return (List<T>) object;
	}
	
	public static <T> Set<T> uncheckedSet(Object object) {
		return (Set<T>) object;
	}
}
