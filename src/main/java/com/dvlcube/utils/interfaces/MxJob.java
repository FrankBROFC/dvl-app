package com.dvlcube.utils.interfaces;

/**
 * @since 13 de fev de 2019
 * @author Ulisses Lima
 */
public interface MxJob {
	long MINUTE = 1 * 1000 * 60;
	long HOUR = 1 * 1000 * 60 * 60;
	long DAY = 1 * 1000 * 60 * 60 * 24;

	/**
	 * @since 13 de fev de 2019
	 * @author Ulisses Lima
	 */
	void run();
}
