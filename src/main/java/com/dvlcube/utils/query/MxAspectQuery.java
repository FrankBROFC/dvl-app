package com.dvlcube.utils.query;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @since 29 de mai de 2019
 * @author Ulisses Lima
 */
public class MxAspectQuery {
	/**
	 * @param point
	 * @return MxJoinPoint
	 * @since 29 de mai de 2019
	 * @author Ulisses Lima
	 */
	public static MxJoinPoint $(ProceedingJoinPoint point) {
		return new MxJoinPoint(point);
	}
}
