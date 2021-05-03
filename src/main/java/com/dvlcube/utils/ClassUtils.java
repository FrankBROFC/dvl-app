package com.dvlcube.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ulisses Lima
 * @since 05/04/2013
 */
public class ClassUtils {
	/**
	 * @param class1
	 * @param interfaceClass
	 * @return true if class1 implements interfaceClass.
	 * @author Ulisses Lima
	 * @since 05/04/2013
	 */
	public static boolean doesImplement(Class<?> class1, Class<?> interfaceClass) {
		Class<?>[] interfaces = class1.getInterfaces();
		for (Class<?> class2 : interfaces) {
			if (class2 == interfaceClass) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param class1
	 * @param interfaceClasses
	 * @return true if class1 implements all interfaces in the list.
	 * @author Ulisses Lima
	 * @since 05/04/2013
	 */
	public static boolean doesImplementAll(Class<?> class1, Class<?>... interfaceClasses) {
		Set<Class<?>> set = new HashSet<>(Arrays.asList(interfaceClasses));
		for (Class<?> class2 : interfaceClasses) {
			if (doesImplement(class1, class2)) {
				set.remove(class2);
			}
		}
		return set.isEmpty();
	}

	/**
	 * @param classe
	 * @param annotation
	 * @return class getters annotated with the specified annotation.
	 * @since Oct 9, 2014
	 * @author Ulisses
	 * @throws IntrospectionException
	 */
	public static List<Method> getGetters(Class<?> classe, Class<? extends Annotation> annotation)
			throws IntrospectionException {
		List<Method> getters = new ArrayList<Method>();
		for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(classe, Object.class)
				.getPropertyDescriptors()) {
			Method getter = propertyDescriptor.getReadMethod();
			if (getter == null)
				continue;

			if (annotation != null) {
				if (getter.isAnnotationPresent(annotation)) {
					getters.add(getter);
				}
			} else {
				getters.add(getter);
			}
		}
		return getters;
	}

	/**
	 * Pesquisa uma classe por nome, ignorando erros.
	 * 
	 * @param name
	 * @return a classe com o nome. ou null se não encontrada.
	 * @since Aug 14, 2014
	 * @author Ulisses
	 */
	public static Class<?> forName(String name) {
		Class<?> classe = null;
		try {
			classe = Class.forName(name);
		} catch (ClassNotFoundException e) {
		}
		return classe;
	}

	/**
	 * @param name
	 * @return uma nova instância da classe passada. Ou null, caso qualquer problema
	 *         ocorra.
	 * @since Aug 15, 2014
	 * @author Ulisses
	 */
	public static Object newInstanceForName(String name) {
		Class<?> classe = null;
		try {
			classe = Class.forName(name);
			if (classe != null)
				return classe.newInstance();
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * @param idInstancia
	 * @return stack trace atual, incluindo apenas as classes dvl-core.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static String stackTrace(String idInstancia) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		return stackTrace(stack, idInstancia);
	}

	/**
	 * @param t
	 * @return stack trace da exceção.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static String stackTrace(Throwable t) {
		return stackTrace(t.getStackTrace(), "");
	}

	/**
	 * @param idInstancia
	 * @return stack trace atual, incluindo apenas as classes dvl-core.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static String stackTrace(StackTraceElement[] stack, String idInstancia) {
		StringBuilder builder = new StringBuilder();

		for (StackTraceElement element : stack) {
			if (element.getClassName().startsWith("com.dvlcube")) {
				builder.append(formatStackElement(idInstancia, element));
			}
		}

		return builder.toString();
	}

	/**
	 * @param prefix
	 * @param element
	 * @return
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	private static String formatStackElement(String prefix, StackTraceElement element) {
		return String.format("%s %s.%s (line %d)\n", prefix, element.getClassName(), element.getMethodName(),
				element.getLineNumber());
	}

	/**
	 * @return stack trace atual, incluindo apenas as classes dvl-core.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static String stackTrace() {
		return stackTrace("");
	}

	/**
	 * @param prefix
	 *            prefixo para filtragem das classes. Pode ser null.
	 * @return stack trace atual, incluindo apenas as classes com o prefixo.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static String stackTraceFilter(String prefix) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		return stackTraceFilter(stack, prefix);
	}

	/**
	 * @param t
	 * @param prefix
	 * @return stack trace da exceção, incluindo apenas as classes com o prefixo.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static String stackTraceFilter(Throwable t, String prefix) {
		return stackTraceFilter(t.getStackTrace(), prefix);
	}

	/**
	 * @param stack
	 * @param prefix
	 *            prefixo para filtragem das classes. Pode ser null.
	 * @return stack trace, incluindo apenas as classes com o prefixo.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static String stackTraceFilter(StackTraceElement[] stack, String prefix) {
		if (prefix == null)
			prefix = "";

		StringBuilder builder = new StringBuilder();

		for (StackTraceElement element : stack) {
			if (element.getClassName().startsWith(prefix)) {
				builder.append(formatStackElement(prefix, element));
			}
		}

		return builder.toString();
	}

	/**
	 * @param c
	 * @return verdadeiro se a classe estiver no stack atual.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static boolean onStack(Class<?> c) {
		return onStackAny(c);
	}

	/**
	 * @param t
	 * @param c
	 * @return verdadeiro se a classe estiver no stack da exceção.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static boolean onStack(Throwable t, Class<?> c) {
		return onStackAny(t, c);
	}

	/**
	 * @param stack
	 * @param cs
	 * @return verdadeiro se todas as classes estiverem no stack atual.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static boolean onStackAll(Class<?>... cs) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		return onStackAll(stack, cs);
	}

	/**
	 * @param e
	 * @param cs
	 * @return verdadeiro se todas as classes estiverem da exceção.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static boolean onStackAll(Throwable e, Class<?>... cs) {
		return onStackAll(e.getStackTrace(), cs);
	}

	/**
	 * @param cs
	 * @return verdadeiro se todas as classes estiverem no stack.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static boolean onStackAll(StackTraceElement[] stack, Class<?>... cs) {
		ArrayList<String> stackClasses = new ArrayList<String>();
		for (StackTraceElement stackTraceElement : stack) {
			stackClasses.add(stackTraceElement.getClassName());
		}

		for (Class<?> c : cs) {
			if (!stackClasses.contains(c.getName())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param cs
	 * @return verdadeiro se qualquer uma das classes estiver no stack atual.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static boolean onStackAny(Class<?>... cs) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		return onStackAny(stack, cs);
	}

	/**
	 * @param stack
	 * @param cs
	 * @return verdadeiro se qualquer uma das classes estiver no stack.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static boolean onStackAny(StackTraceElement[] stack, Class<?>... cs) {
		for (int i = 0; i < stack.length; i++) {
			Class<?> stackClass = forName(stack[i].getClassName());

			for (int j = 0; j < cs.length; j++) {
				Class<?> matchingClass = cs[j];
				if (matchingClass == stackClass) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * @param t
	 * @param cs
	 * @return verdadeiro se qualquer uma das classes estiver no stack da exceção.
	 * @since Aug 9, 2016
	 * @author Ulisses Lima
	 */
	public static boolean onStackAny(Throwable t, Class<?>... cs) {
		return onStackAny(t.getStackTrace(), cs);
	}

}
