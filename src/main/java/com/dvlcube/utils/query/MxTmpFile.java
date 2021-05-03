package com.dvlcube.utils.query;

import java.io.File;
import java.io.IOException;

/**
 * @since 25/06/2013
 * @author Ulisses Lima
 */
public class MxTmpFile extends MxFile {
	/**
	 * @param path
	 * @since 04/07/2013
	 * @author Ulisses Lima
	 */
	public MxTmpFile(MxString path) {
		super(path.o, true);
	}

	public MxTmpFile(String path) {
		super(path, true);
	}

	/**
	 * @param path
	 * @since 25/06/2013
	 * @author Ulisses Lima
	 */
	public MxTmpFile(String path, boolean create) {
		super(path, create);
	}

	@Override
	protected void create() throws IOException {
		super.o = File.createTempFile(super.o.getName(), null);
	}
}
