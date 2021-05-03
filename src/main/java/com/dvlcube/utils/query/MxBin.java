package com.dvlcube.utils.query;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @since 27 de mar de 2019
 * @author Ulisses Lima
 */
public class MxBin {
	public byte[] o;
	private boolean silent;

	/**
	 * @param object2
	 * @author Ulisses Lima
	 * @param objects
	 * @since 14/04/2013
	 */
	public MxBin(byte... o) {
		this.o = o;
	}

	/**
	 * Ignore errors;
	 * 
	 * @return this
	 * @since 27 de mar de 2019
	 * @author Ulisses Lima
	 */
	public MxBin silent() {
		silent = true;
		return this;
	}

	/**
	 * Don't ignore errors.
	 * 
	 * @return this
	 * @since 27 de mar de 2019
	 * @author Ulisses Lima
	 */
	public MxBin verbose() {
		silent = false;
		return this;
	}

	/**
	 * @param file save to this file
	 * @return MxFile
	 * @since 27 de mar de 2019
	 * @author Ulisses Lima
	 * @throws IOException
	 */
	public MxFile saveAs(File file) throws IOException {
		try {
			Files.write(file.toPath(), o);
		} catch (Throwable e) {
			if (!silent)
				throw e;
			else
				e.printStackTrace();
		}
		return new MxFile(file.getAbsolutePath());
	}

	/**
	 * @param file save to this path
	 * @return MxFile
	 * @since 27 de mar de 2019
	 * @author Ulisses Lima
	 * @throws IOException
	 */
	public MxFile saveAs(String file) throws IOException {
		return saveAs(new File(file));
	}
}