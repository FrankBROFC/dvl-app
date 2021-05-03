package com.dvlcube.utils.ex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.dvlcube.utils.query.MxFile;

/**
 * @since 8 de mar de 2019
 * @author Ulisses Lima
 */
public class Zip {
	private File[] files;
	private int length;
	private int entries;

	/**
	 * @param files
	 * @since 30 de mai de 2019
	 */
	public Zip(File... files) {
		this.files = files;
	}

	/**
	 * @param paths
	 * @since 30 de mai de 2019
	 */
	public Zip(String... paths) {
		File[] arr = new File[paths.length];
		for (int i = 0; i < paths.length; i++) {
			arr[i] = new File(paths[i]);
		}
		this.files = arr;
	}

	/**
	 * @param files
	 * @return ZIP file byte array
	 * @throws Throwable
	 * @since 8 de mar de 2019
	 * @author Ulisses Lima
	 * @throws IOException
	 */
	public byte[] bytes() throws IOException {
		return new MxFile(save(tmp())).bytes();
	}

	/**
	 * @return file created
	 * @throws IOException
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public File save() throws IOException {
		return save(null);
	}

	/**
	 * @param path
	 * @throws IOException
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public File save(File path) throws IOException {
		if (path == null)
			path = tmp();

		try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(path))) {
			for (File file : files) {
				zipOut.putNextEntry(new ZipEntry(file.getName()));
				Files.copy(file.toPath(), zipOut);
			}
		}
		return path;
	}

	/**
	 * @return tmp file.
	 * @throws IOException
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	private File tmp() throws IOException {
		return File.createTempFile("temp", ".zip");
	}

	/**
	 * @return ZIP file length
	 * @since 11 de mar de 2019
	 * @author Ulisses Lima
	 */
	public int length() {
		if (length < 1)
			throw new IllegalStateException("zip not initialized or corrupt");
		return length;
	}

	/**
	 * @return number of file entries.
	 * @since 11 de mar de 2019
	 * @author Ulisses Lima
	 */
	public int getEntries() {
		if (entries < 1)
			throw new IllegalStateException("zip not initialized or corrupt");
		return entries;
	}
}
