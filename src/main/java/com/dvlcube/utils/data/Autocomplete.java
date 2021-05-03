package com.dvlcube.utils.data;

import java.io.Serializable;

import com.dvlcube.utils.interfaces.Identifiable;

/**
 * Based on https://materializecss.com/autocomplete.html
 * 
 * @since 2 de abr de 2019
 * @author Ulisses Lima
 */
public class Autocomplete {
	private String value;
	private String icon;

	public Autocomplete() {
	}

	public Autocomplete(String value) {
		this.value = value;
	}

	public Autocomplete(Identifiable<? extends Serializable> id) {
		this.value = String.valueOf(id.getId());
	}

	public Autocomplete(Serializable id) {
		this.value = String.valueOf(id);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
