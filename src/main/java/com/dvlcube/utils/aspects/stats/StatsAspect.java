package com.dvlcube.utils.aspects.stats;

import static com.dvlcube.utils.query.MxAspectQuery.$;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import com.dvlcube.utils.env.Env;
import com.dvlcube.utils.interfaces.Killable;
import com.dvlcube.utils.query.MxJoinPoint;
import com.dvlcube.utils.query.MxTimed;

/**
 * @see Stats
 * @since 29/11/2018
 * @author Ulisses Lima
 */
@Aspect
@Configuration
public class StatsAspect implements Killable {
	public static final String actionNameIgnoreExpression;

	static {
		actionNameIgnoreExpression = Env.var("dvl.utils.aspect.actionNameIgnoreExpression", "RestComponent");
	}

	/**
	 * @param joinPoint
	 * @return original return.
	 * @since Jan 18, 2018
	 * @author Ulisses Lima
	 */
	@Around("@annotation(timed)")
	public Object timeMethod(ProceedingJoinPoint joinPoint, Timed timed) throws Throwable {
		return time(joinPoint);
	}

	/**
	 * @param joinPoint
	 * @param timed
	 * @return original return.
	 * @throws Throwable
	 * @since 29 de mai de 2019
	 * @author Ulisses Lima
	 */
	@Around("@within(timed)")
	public Object timeClass(ProceedingJoinPoint joinPoint, Timed timed) throws Throwable {
		return time(joinPoint);
	}

	/**
	 * @param joinPoint
	 * @return original return.
	 * @throws Throwable
	 * @since 29 de mai de 2019
	 * @author Ulisses Lima
	 */
	public Object time(ProceedingJoinPoint joinPoint) throws Throwable {
		return timeAround(joinPoint);
	}

	/**
	 * To be used by other aspects.
	 * 
	 * @param joinPoint
	 * @param timed     optional. if specified, used the value of the annotation
	 *                  instead of the joinPoint method name
	 * @return return value from joinPoint
	 * @throws Throwable
	 * @since 17 de abr de 2019
	 * @author Ulisses Lima
	 */
	public static Object timeAround(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		boolean propagateException = false;
		boolean ok = false;
		Object value = null;

		String id = null;
		try {
			id = autoId(joinPoint);

			try {
				value = joinPoint.proceed();
			} catch (Throwable e) {
				propagateException = true;
				Stats.error(id);
				throw e;
			} finally {
				ok = true;
			}
		} catch (Throwable e) {
			if (propagateException) {
				throw e;
			} else {
				if (!ok)
					value = joinPoint.proceed();
				e.printStackTrace();
			}
		} finally {
			Stats.split(id, start);
		}
		return value;
	}

	/**
	 * @param joinPoint
	 * @return ID do método a partir da classe + nome + args
	 * @since 04/12/2018
	 * @author Ulisses Lima
	 */
	public static String autoId(ProceedingJoinPoint joinPoint) {
		MxJoinPoint point = $(joinPoint);
		MxTimed annotation = point.statsInfo();
		if (annotation.shouldSkipStats())
			return null;

		String name = "";
		if (annotation.hasValue()) {
			name = annotation.o.value();
			if (annotation.onMethod())
				return name;
			name += "@";
		}

		return name + point.id();
	}

	/**
	 * Chamado antes de terminar o Java (normalmente)
	 * 
	 * @since 04/12/2018
	 * @author Ulisses Lima
	 */
	@Override
	public void beforeShutdown() {
		try {
			String table = Stats.table(null);
			System.out.println(table);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}