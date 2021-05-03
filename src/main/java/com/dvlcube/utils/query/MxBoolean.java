package com.dvlcube.utils.query;

import com.dvlcube.utils.AssertionUtils;

/**
 * @since Feb 26, 2016
 * @author Ulisses Lima
 */
public class MxBoolean {
	public Boolean o = false;

	public MxBoolean(Boolean o) {
		if (o == null)
			this.o = false;
		else
			this.o = o;
	}

	public boolean isTrue() {
		return o;
	}

	public MxBoolean assertTrue(String paramName) {
		AssertionUtils.isTrue(paramName, o);
		return this;
	}

	public MxBoolean assertTrue() {
		AssertionUtils.isTrue("boolean", o);
		return this;
	}
}
