package com.dvlcube.utils.ex;

/**
 * @since 18 de mar de 2019
 * @author Ulisses Lima
 */
public interface MachineResource {

	/**
	 * @return total in bytes
	 * @since 18 de mar de 2019
	 * @author Ulisses Lima
	 */
	Long total();

	/**
	 * @return free in bytes
	 * @since 18 de mar de 2019
	 * @author Ulisses Lima
	 */
	Long free();

	/**
	 * @return total in MB
	 * @since 18 de mar de 2019
	 * @author Ulisses Lima
	 */
	Long totalMb();

	/**
	 * @return free in MB
	 * @since 18 de mar de 2019
	 * @author Ulisses Lima
	 */
	Long freeMb();

	/**
	 * @return free in percent
	 * @since 18 de mar de 2019
	 * @author Ulisses Lima
	 */
	Double freePercentage();

	/**
	 * @return used in percentage
	 * @since 18 de mar de 2019
	 * @author Ulisses Lima
	 */
	Double usedPercentage();
}
