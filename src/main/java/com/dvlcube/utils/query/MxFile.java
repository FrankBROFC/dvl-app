package com.dvlcube.utils.query;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import com.dvlcube.utils.FileUtils;
import com.dvlcube.utils.MxExceptionUtils;
import com.dvlcube.utils.MxStringUtils;
import com.dvlcube.utils.ex.Shell;
import com.dvlcube.utils.ex.Zip;

/**
 * @since 25/06/2013
 * @author Ulisses Lima
 */
public class MxFile {
	public File o;

	/**
	 * Temp file.
	 * 
	 * @throws IOException
	 * @since 30 de mai de 2019
	 */
	public MxFile() throws IOException {
		o = File.createTempFile("tmp-string", ".dvl");
	}

	/**
	 * @param path
	 * @since 04/07/2013
	 * @author Ulisses Lima
	 */
	public MxFile(MxString path) {
		this(path.o, true);
	}

	public MxFile(String path) {
		this(path, true);
	}

	/**
	 * @param f
	 * @since 24 de abr de 2019
	 */
	public MxFile(File f) {
		this(f.getAbsolutePath(), true);
	}

	/**
	 * @param p
	 * @since 24 de abr de 2019
	 */
	public MxFile(Path p) {
		this(p.toFile());
	}

	/**
	 * @param path
	 * @since 25/06/2013
	 * @author Ulisses Lima
	 */
	public MxFile(String path, boolean create) {
		o = new File(path);
		if (!o.isFile()) {
			if (create) {
				if (!o.exists()) {
					try {
						if (path.endsWith("/")) {
							o.mkdir();
						} else {
							create();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				throw new IllegalArgumentException("not a file: '" + path + "'");
			}
		}
	}

	/**
	 * @throws IOException
	 * @since 12 de mar de 2019
	 * @author Ulisses Lima
	 */
	protected void create() throws IOException {
		o.createNewFile();
	}

	/**
	 * Clear file contents.
	 * 
	 * @return this
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MxFile clear() {
		if (!o.isFile())
			return this;

		try (PrintWriter writer = new PrintWriter(o)) {
			writer.print("");
		} catch (FileNotFoundException e) {
		}
		return this;
	}

	/**
	 * @param expression
	 * @return grepped lines
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MxString grep(String expression) {
		String s = Shell.exec(String.format("cat '%s' | grep %s", o.getAbsolutePath(), expression));
		return new MxString(s);
	}

	/**
	 * Appends a line to the file, line ends with line break.
	 * 
	 * @param line line to append.
	 * @return this.
	 * @since 07/07/2013
	 * @author Ulisses Lima
	 */
	public MxFile append(String line) {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(o, true)))) {
			writer.append(line + "\n");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * @param e
	 * @return this
	 * @since 24 de abr de 2019
	 * @author Ulisses Lima
	 */
	public MxFile append(Throwable e) {
		return append(MxExceptionUtils.stackTrace(e));
	}

	/**
	 * @param msg
	 * @return this
	 * @since 24 de abr de 2019
	 * @author Ulisses Lima
	 */
	public MxFile log(String type, String msg, Throwable e) {
		append(MxExceptionUtils.log(type, msg));

		if (e != null)
			append(e);
		return this;
	}

	/**
	 * @param msg
	 * @return this
	 * @since 24 de abr de 2019
	 * @author Ulisses Lima
	 */
	public MxFile info(String msg) {
		return log("INFO", msg, null);
	}

	/**
	 * @param msg
	 * @return this
	 * @since 24 de abr de 2019
	 * @author Ulisses Lima
	 */
	public MxFile warn(String msg) {
		return log("WARN", msg, null);
	}

	/**
	 * @param msg
	 * @param e
	 * @return this
	 * @since 24 de abr de 2019
	 * @author Ulisses Lima
	 */
	public MxFile error(String msg, Throwable e) {
		log("ERROR", msg, e);
		return this;
	}

	public String firstLine() {
		return FileUtils.firstLine(o);
	}

	/**
	 * @param extension the desired extension.
	 * @return true if the file has the passed extension.
	 * @since 07/07/2013
	 * @author Ulisses Lima
	 */
	public boolean is(String extension) {
		if (o == null) {
			throw new IllegalStateException("file was not initialized");
		}

		if (o.getPath().endsWith(extension)) {
			return true;
		}

		return false;
	}

	public boolean isDir() {
		return o.isDirectory();
	}

	/**
	 * @param dir directory to create.
	 * @return this.
	 * @since 08/07/2013
	 * @author Ulisses Lima
	 */
	public MxFile mkdirs(String dir) {
		if (o == null) {
			throw new IllegalStateException("file was not initialized");
		}

		if (!o.isDirectory()) {
			throw new IllegalStateException("this is not a dir");
		}

		File newDir = new File(o.getPath() + "/" + dir);
		o = newDir;
		newDir.mkdirs();
		return this;
	}

	/**
	 * @return cria os diretórios necessários para o arquivo.
	 * @since Feb 26, 2016
	 * @author Ulisses Lima
	 */
	public MxFile mkdirs() {
		File parentFile = o.getParentFile();
		if (!parentFile.isDirectory())
			parentFile.mkdirs();
		return this;
	}

	/**
	 * Moves this file to another location.
	 * 
	 * @param dir destination.
	 * @return this.
	 * @since 09/07/2013
	 * @author Ulisses Lima
	 */
	public MxFile mv(String dir) {
		o = FileUtils.moveFileToDirectory(o, new File(dir));
		return this;
	}

	/**
	 * @return this file name, without the extension.
	 * @since 09/07/2013
	 * @author Ulisses Lima
	 */
	public String nameSansExtension() {
		return FileUtils.removeExtension(o.getPath());
	}

	/**
	 * @return MxString without the file extension
	 * @since 29 de mar de 2019
	 * @author Ulisses Lima
	 */
	public MxString removeExtension() {
		return new MxString(nameSansExtension());
	}

	/**
	 * @param name file to create.
	 * @return this.
	 * @since 08/07/2013
	 * @author Ulisses Lima
	 */
	public MxFile newFile(String name) {
		if (o == null) {
			throw new IllegalStateException("file was not initialized");
		}

		if (!o.isDirectory()) {
			throw new IllegalStateException("this is not a dir");
		}

		try {
			File newFile = new File(o.getPath() + "/" + name);
			o = newFile;
			newFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public MxFile newFile() throws IOException {
		mkdirs();
		create();
		return this;
	}

	/**
	 * 
	 * @since 09/07/2013
	 * @author Ulisses Lima
	 */
	public void rm() {
		o.delete();
	}

	@Override
	public String toString() {
		return o.toString();
	}

	/**
	 * Overwrites a file.
	 * 
	 * @param line line to write.
	 * @return this.
	 * @since 08/07/2013
	 * @author Ulisses Lima
	 */
	public MxFile write(String line) {
		try (PrintWriter writer = new PrintWriter(o)) {
			writer.write(line);
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * @return file lines.
	 * @since 26 de fev de 2019
	 * @author Ulisses Lima
	 */
	public List<String> lines() {
		return FileUtils.lines(o);
	}

	/**
	 * Process file lines.
	 * 
	 * @param processor
	 * @since 26 de fev de 2019
	 * @author Ulisses Lima
	 */
	public void lines(Consumer<String> processor) {
		FileUtils.process(o, processor);
	}

	/**
	 * @return file data
	 * @throws IOException
	 * @since 12 de mar de 2019
	 * @author Ulisses Lima
	 */
	public byte[] bytes() throws IOException {
		return Files.readAllBytes(o.toPath());
	}

	/**
	 * @return zipped bytes
	 * @throws Throwable
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public byte[] zip() throws Throwable {
		return new Zip(o).bytes();
	}

	/**
	 * @return file length
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public long length() {
		if (!o.isFile())
			return -1;

		try {
			return bytes().length;
		} catch (IOException e) {
			e.printStackTrace();
			return -2;
		}
	}

	/**
	 * @param property
	 * @return if this file represents a properties file, returns the property value
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @since 29 de mar de 2019
	 * @author Ulisses Lima
	 */
	public String prop(String property) throws FileNotFoundException, IOException {
		return MxStringUtils.prop(new FileInputStream(o), property);
	}

	/**
	 * @param pattern file pattern (accepts wildcards like *.log)
	 * @param c       file consumer
	 * @return this
	 * @since 24 de abr de 2019
	 * @author Ulisses Lima
	 */
	public MxFile find(String pattern, Consumer<File> c) {
		if (!o.isDirectory() || c == null)
			return this;

		Iterator<Path> files;
		try {
			files = Files.newDirectoryStream(o.toPath(), pattern).iterator();
			if (files == null) {
				return this;
			}

			while (files.hasNext()) {
				try {
					c.accept(files.next().toFile());
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * @param pattern
	 * @return first file that matches the pattern.
	 * @since 24 de abr de 2019
	 * @author Ulisses Lima
	 */
	public MxFile findFirst(String pattern) {
		find(pattern, f -> {
			o = f;
			return;
		});
		return this;
	}

	/**
	 * XXX OS specific. Unix only. Lidvls to 1000 lines.
	 * 
	 * @param head
	 * @param tail
	 * @param from
	 * @return range of lines from file.
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MxString range(Integer head, Integer tail, Integer from) {
		StringBuilder cmd = new StringBuilder();

		String absolutePath = o.getAbsolutePath();
		if (from == null) {
			cmd.append(head == null ? "tail " : "head ");

			if (head != null) {
				if (head > 1000)
					tail = 1000;

				cmd.append(String.format("-%d ", head));
			} else if (tail != null) {
				if (tail > 1000)
					tail = 1000;

				cmd.append(String.format("-%d ", tail));
			}
			cmd.append(absolutePath);
		} else {
			if (head == null) {
				head = 100;
			} else if (head > 1000) {
				head = 1000;
			}
			int range = from + head;
			cmd.append(String.format("cat %s | sed -ne '%s,%dp;%dq'", //
					absolutePath, //
					from, //
					range, range));
		}

		return new MxString(Shell.exec(cmd.toString()));
	}

	/**
	 * @return number of lines
	 * @since 31 de mai de 2019
	 * @author Ulisses Lima
	 */
	public int wc() {
		return wc(null);
	}

	/**
	 * @param args defaults to -l
	 * @return number
	 * @since 31 de mai de 2019
	 * @author Ulisses Lima
	 */
	public int wc(String args) {
		if (args == null)
			args = "-l";

		String n = Shell.exec(String.format("cat '%s' | wc %s | cut -d' ' -f1", o.getAbsolutePath(), args));
		if (n == null)
			return -1;

		return Integer.valueOf(n);
	}

	/**
	 * @param n lines to show
	 * @return last n lines from the file
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MxString tail(Integer n) {
		if (n == null)
			n = 10;

		return range(null, n, null);
	}

	/**
	 * @return last 10 lines from file.
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MxString tail() {
		return tail(null);
	}

	/**
	 * @param n lines to show
	 * @return first n lines from the file
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MxString head(Integer n) {
		if (n == null)
			n = 10;

		return range(n, null, null);
	}

	/**
	 * @return first 10 lines from file.
	 * @since 30 de mai de 2019
	 * @author Ulisses Lima
	 */
	public MxString head() {
		return head(null);
	}
}
