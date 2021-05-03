package com.dvlcube.utils;

import static com.dvlcube.utils.query.MxQuery.$;

import java.util.Collection;

/**
 * 
 * @since Feb 26, 2016
 * @author Ulisses Lima
 */
public class AssertionUtils {
	/**
	 * Valida parâmetros obrigatórios.
	 * 
	 * @throws IllegalArgumentException se qualquer um dos itens estiver nulo.
	 * @param args
	 * @author Ulisses
	 * @since Mar 1, 2012
	 */
	public static void notNull(Object... args) {
		for (Object param : args) {
			if (param == null) {
				throw new IllegalArgumentException("nenhum dos itens pode ser nulo.");
			}
		}
	}

	/**
	 * Valida um parâmetro obrigatório.
	 * 
	 * @param paramName
	 * @param param
	 * @author Ulisses
	 * @since Mar 1, 2012
	 */
	public static void notNull(String paramName, Object param) {
		if (param == null) {
			throw new IllegalArgumentException("O parâmetro '" + paramName + "' é obrigatório.");
		}
	}

	/**
	 * @param args
	 * @author Ulisses
	 * @since Mar 4, 2012
	 */
	public static void notBlank(String... args) {
		for (String param : args) {
			if (MxStringUtils.isBlank(param)) {
				throw new IllegalArgumentException("Um parâmetro obrigatório está vazio.");
			}
		}
	}

	/**
	 * @param paramName
	 * @param param
	 * @author Ulisses
	 * @since Mar 4, 2012
	 */
	public static void notBlank(String paramName, String param) {
		if (MxStringUtils.isBlank(param)) {
			throw new IllegalArgumentException("O parâmetro '" + paramName + "' é obrigatório.");
		}
	}

	/**
	 * @param paramName
	 * @param collection
	 * @author Ulisses
	 * @since Mar 4, 2012
	 */
	public static void notEmpty(String paramName, Collection<?> collection) {
		if ($(collection).isEmpty()) {
			throw new IllegalArgumentException("A lista '" + paramName + "' não pode estar vazia.");
		}
	}

	/**
	 * Lança uma exceção caso a expressão não seja verdadeira.
	 * 
	 * @param paramName
	 * @param expression
	 * @since Mar 17, 2014
	 * @author Ulisses
	 */
	public static void isTrue(String paramName, boolean expression) {
		if (!expression) {
			throw new IllegalArgumentException("A condição '" + paramName + "' deve ser verdadeira.");
		}
	}
}
