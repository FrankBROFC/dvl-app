package com.dvlcube.utils.query;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.dvlcube.utils.AssertionUtils;
import com.dvlcube.utils.ClassUtils;
import com.dvlcube.utils.MxStringUtils;

/**
 * @author Ulisses Lima
 * @since 18/04/2013
 */
public class MxClass<C> {
	public Class<C> o;
	public boolean isAssist;

	/**
	 * @param o
	 * @author Ulisses Lima
	 * @since 18/04/2013
	 */
	public MxClass(Class<C> c) {
		if (c.getSimpleName().contains("assist")) {
			isAssist = true;
		}
		this.o = c;
	}

	/**
	 * @param interfaceClass
	 * @return
	 * @author Ulisses Lima
	 * @since 18/04/2013
	 */
	public boolean doesImplement(Class<?> interfaceClass) {
		return ClassUtils.doesImplement(o, interfaceClass);
	}

	/**
	 * @param interfaceClasses
	 * @return true if class1 implements all interfaces in the list.
	 * @author Ulisses Lima
	 * @since 18/04/2013
	 */
	public boolean doesImplementAll(Class<?>... interfaceClasses) {
		return ClassUtils.doesImplementAll(o, interfaceClasses);
	}

	/**
	 * @return the default naming convention for spring beans.
	 * @author Ulisses Lima
	 * @since 27/10/2013
	 */
	public String beanName() {
		return MxStringUtils.decapitalize(simpleName());
	}

	/**
	 * If the bean name ends with Bean, it's removed
	 * 
	 * @return short bean name
	 * @since 7 de abr de 2019
	 * @author Ulisses Lima
	 */
	public String shortBeanName() {
		String mame = beanName();
		if (mame.endsWith("Bean"))
			return mame.replace("Bean", "");
		else
			return mame;
	}

	/**
	 * @return the simple class name, decapitalized.
	 * @author Ulisses Lima
	 * @since 09/11/2013
	 */
	public String elementName() {
		String className = MxStringUtils.decapitalize(o.getSimpleName());
		return isAssist ? MxStringUtils.decapitalize(className.split("_")[0]) : className;
	}

	public String name() {
		String className = o.getCanonicalName();
		return isAssist ? className.split("_")[0] : className;
	}

	/**
	 * @return the simple class name.
	 * @author Ulisses Lima
	 * @since 27/10/2013
	 */
	public String simpleName() {
		String className = o.getSimpleName();
		return isAssist ? className.split("_")[0] : className;
	}

	/**
	 * @since Feb 26, 2016
	 * @author Ulisses Lima
	 */
	public void assertNotNull() {
		AssertionUtils.notNull("Class", o);
	}

	/**
	 * @param paramName nome do parâmetro exibido na exceção.
	 * @since Feb 26, 2016
	 * @author Ulisses Lima
	 */
	public void assertNotNull(String paramName) {
		AssertionUtils.notNull(paramName, o);
	}

	/**
	 * @param name
	 * @return input stream
	 * @since 29 de mar de 2019
	 * @author Ulisses Lima
	 */
	public InputStream resource(String name) {
		return o.getClassLoader().getResourceAsStream(name);
	}

	/**
	 * @param name
	 * @param property
	 * @return property value from a resource
	 * @since 29 de mar de 2019
	 * @author Ulisses Lima
	 */
	public String prop(String name, String property) {
		return MxStringUtils.prop(resource(name), property);
	}

	/**
	 * @param name
	 * @param property
	 * @return properties from a resource
	 * @throws IOException
	 * @since 29 de mar de 2019
	 * @author Ulisses Lima
	 */
	public Map<String, String> props(String name, String property) throws IOException {
		return MxStringUtils.props(resource(name));
	}

	/**
	 * Will instantiate a new object of this class using the default constructor and
	 * then check if the resulting object is an instance.
	 * 
	 * @param type
	 * @return true if this class can be an instance of type. Will fail silently and
	 *         return false if the class does not have a no0-arg constructor.
	 * @since 30 de abr de 2019
	 * @author Ulisses Lima
	 */
	public boolean instanceOf(Class<?> type) {
		C testInstance = null;
		try {
			testInstance = o.getDeclaredConstructor().newInstance();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return type.isInstance(testInstance);
	}
}
