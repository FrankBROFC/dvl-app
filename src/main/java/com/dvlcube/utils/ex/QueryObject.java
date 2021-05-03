package com.dvlcube.utils.ex;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.dvlcube.utils.interfaces.Exportable;
import com.dvlcube.utils.interfaces.Select;

/**
 * 
 * @since Feb 26, 2016
 * @author Ulisses Lima
 */
public class QueryObject implements Serializable, Exportable {
	private static final long serialVersionUID = -1441451051111318961L;

	public Class<?> getT() {
		return this.getClass();
	}

	/**
	 * @return campos para o select, baseado nos campos com a anotação @Select
	 * @since Oct 10, 2014
	 * @author Ulisses
	 */
	public String select() {
		StringBuilder columns = new StringBuilder();

		Field[] fields = getT().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Select.class)) {
				Select select = field.getAnnotation(Select.class);
				String fieldName = field.getName();
				String criteria = "".equals(select.value()) ? fieldName : select.value();

				columns.append(",").append(criteria).append(" as ").append(fieldName);
			}
		}

		return columns.toString().replaceFirst(",", "");
	}
}
