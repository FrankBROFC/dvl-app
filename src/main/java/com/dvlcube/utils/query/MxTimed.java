package com.dvlcube.utils.query;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import com.dvlcube.utils.aspects.stats.Timed;

public class MxTimed extends MxAnnotation<Timed> {
	/**
	 * @param annotation
	 * @param c
	 * @since 30 de mai de 2019
	 */
	public MxTimed(Timed annotation) {
		super(annotation);
	}

	/**
	 * @param annotation
	 * @param method
	 * @since 30 de mai de 2019
	 */
	public MxTimed(Class<Timed> annotation, Method method) {
		super(annotation, method);
	}

	/**
	 * @return true if the annotation sets the value property.
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public boolean hasValue() {
		return present() && !"".equals(o.value());
	}

	/**
	 * Process annotation value, if any. Null safe.
	 * 
	 * @param consumer
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public void value(Consumer<String> consumer) {
		if (!present() || !hasValue())
			return;

		consumer.accept(o.value());
	}

	/**
	 * @return true if thie method should not collect stats.
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public boolean shouldSkipStats() {
		return present() && o.exclude();
	}
}
