package com.dvlcube.utils.interfaces;

/**
 * @since 5 de abr de 2019
 * @author Ulisses Lima
 */
public interface Sanitizable {

	/**
	 * Remove any values that might not make sense. e.g.: clear user input
	 * 
	 * @since 5 de abr de 2019
	 * @author Ulisses Lima
	 */
	void sanitize();
}
