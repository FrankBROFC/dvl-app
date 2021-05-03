package com.dvlcube.utils.query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.dvlcube.utils.aspects.stats.Timed;

/**
 * @since 29 de mai de 2019
 * @author Ulisses Lima
 */
public class MxJoinPoint {
	public ProceedingJoinPoint o;

	public MxJoinPoint(ProceedingJoinPoint o) {
		this.o = o;
	}

	/**
	 * @param annotation type
	 * @return method annotation or class annotation, whatever comes first.
	 * @since 29 de mai de 2019
	 * @author Ulisses Lima
	 */
	public <A extends Annotation> MxAnnotation<A> find(Class<A> annotation) {
		return new MxAnnotation<A>(annotation, method());
	}

	/**
	 * @see Timed
	 * @return Timed stats annotation.
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MxTimed statsInfo() {
		return new MxTimed(Timed.class, method());
	}

	/**
	 * @param type
	 * @return true if this join point return type is assignable from type
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public boolean returns(Class<?> type) {
		return method().getReturnType().isAssignableFrom(type);
	}

	/**
	 * @param type
	 * @return true if type is part of the method parameters.
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public boolean accepts(Class<?> type) {
		return Arrays.asList(signature().getParameterTypes()).contains(type);
	}

	/**
	 * @return point method
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public Method method() {
		return signature().getMethod();
	}

	/**
	 * @return method signature
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MethodSignature signature() {
		return (MethodSignature) o.getSignature();
	}

	/**
	 * @return join point readable name
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public String id() {
		StringBuilder s = new StringBuilder();
		MethodSignature sig = (MethodSignature) o.getSignature();
		Class<?>[] types = sig.getParameterTypes();
		appendTypes(s, types, true, false);

		return sig.getDeclaringType().getSimpleName() + "." + sig.getName() + "("
				+ s.toString().replace("HttpServlet", "") + ")";
	}

	/**
	 * @param sb
	 * @param types
	 * @param includeArgs
	 * @param useLongTypeNames
	 * @since 04/12/2018
	 * @author Ulisses Lima
	 */
	public static void appendTypes(StringBuilder sb, Class<?>[] types, boolean includeArgs, boolean useLongTypeNames) {
		if (includeArgs) {
			for (int size = types.length, i = 0; i < size; i++) {
				appendType(sb, types[i], useLongTypeNames);
				if (i < size - 1) {
					sb.append(",");
				}
			}
		} else {
			if (types.length != 0) {
				sb.append("..");
			}
		}
	}

	/**
	 * @param sb
	 * @param type
	 * @param useLongTypeName
	 * @since 04/12/2018
	 * @author Ulisses Lima
	 */
	public static void appendType(StringBuilder sb, Class<?> type, boolean useLongTypeName) {
		if (type.isArray()) {
			appendType(sb, type.getComponentType(), useLongTypeName);
			sb.append("[]");
		} else {
			sb.append(useLongTypeName ? type.getName() : type.getSimpleName());
		}
	}
}