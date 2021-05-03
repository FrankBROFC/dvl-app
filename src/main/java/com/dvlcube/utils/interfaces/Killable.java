package com.dvlcube.utils.interfaces;

/**
 * @since 30 de mai de 2019
 * @author Ulisses Lima
 */
public interface Killable {
	/**
	 * What to do before terminating normally.
	 * 
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	void beforeShutdown();
}
