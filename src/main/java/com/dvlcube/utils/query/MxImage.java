package com.dvlcube.utils.query;

import static com.dvlcube.utils.query.MxQuery.$f;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.dvlcube.utils.ImageUtils;
import com.dvlcube.utils.ImageUtils.RandomMode;
import com.dvlcube.utils.eclipse.JsonToStringBuilder;

/**
 * @since 21/06/2013
 * @author Ulisses Lima
 */
public class MxImage {
	public BufferedImage o;
	public Class<?> origin;
	public final MxString path = new MxString();

	public MxImage(BufferedImage image) {
		o = image;
	}

	public MxImage(InputStream in) {
		try {
			o = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param aClass class to load the resource from.
	 * @param path   path to resource.
	 * @since 27/06/2013
	 * @author Ulisses Lima
	 */
	public MxImage(Class<?> aClass, String path) {
		try {
			this.path.reset(path);
			origin = aClass;
			BufferedImage image = ImageIO.read(aClass.getResourceAsStream(this.path.o));
			o = image;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructs an image using a random HSB pattern.
	 * 
	 * @param width  desired height.
	 * @param height desired width.
	 * @since 08/07/2013
	 * @author Ulisses Lima
	 */
	public MxImage(int width, int height) {
		o = RandomMode.HSB_PATTERN.randomize(new Dimension(width, height));
	}

	/**
	 * @param path path to file.
	 * @since 26/06/2013
	 * @author Ulisses Lima
	 */
	public MxImage(String path) {
		try {
			this.path.reset(path);
			BufferedImage image = ImageIO.read($f(this.path).o);
			o = image;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int h() {
		return o.getHeight();
	}

	/**
	 * Labels this image.
	 * 
	 * @param text label to use.
	 * @return this.
	 * @since 08/07/2013
	 * @author Ulisses Lima
	 */
	public MxImage label(String text) {
		ImageUtils.drawLabel(o, text);
		return this;
	}

	/**
	 * @return image pixels.
	 * @since 26/06/2013
	 * @author Ulisses Lima
	 */
	public int[] pixels() {
		return ImageUtils.pixels(o);
	}

	public byte[] pixelsb() {
		return ImageUtils.pixelsb(o);
	}

	public Object pixelsg() {
		return ImageUtils.pixelsg(o);
	}

	public int[] pixelsi() {
		return ImageUtils.pixelsi(o);
	}

	/**
	 * Fills this buffered image with a random pattern.
	 * 
	 * @return this.
	 * @since 04/07/2013
	 * @author Ulisses Lima
	 */
	public MxImage randomize() {
		if (o == null) {
			throw new IllegalStateException("The BufferedImage was not initialized");
		}

		RandomMode mode = RandomMode.getAny();
		return randomize(mode, o.getWidth(), o.getHeight());
	}

	/**
	 * Fills this buffered image with a random pattern.
	 * 
	 * @param width
	 * @param height
	 * @return this.
	 * @since 04/07/2013
	 * @author Ulisses Lima
	 */
	public MxImage randomize(int width, int height) {
		RandomMode mode = RandomMode.getAny();
		return randomize(mode, width, height);
	}

	public MxImage randomize(RandomMode mode, int width, int height) {
		o = mode.randomize(new Dimension(width, height));
		return this;
	}

	@Override
	public String toString() {
		JsonToStringBuilder builder = new JsonToStringBuilder(this);
		builder.append("origin", origin);
		builder.append("path", path);
		return builder.build();
	}

	public int w() {
		return o.getWidth();
	}

	/**
	 * Writes this image to disc. The image have to be already initialized
	 * 
	 * @param file destination.
	 * @return this.
	 * @since 08/07/2013
	 * @author Ulisses Lima
	 */
	public MxImage write(MxFile file) {
		if (o == null) {
			throw new IllegalStateException("this BufferedImage was not initialized");
		}

		String path = file.o.getPath();
		if (!path.endsWith(".png")) {
			path = path + ".png";
		}

		this.path.reset(path);
		ImageUtils.write(o, file.o);

		return this;
	}

	/**
	 * @param pixels pixels to override.
	 * @since 26/06/2013
	 * @author Ulisses Lima
	 */
	public MxImage write(int[] pixels) {
		if (path.isBlank()) {
			throw new IllegalArgumentException("path was not initialized");
		}

		ImageUtils.write(pixels, new Dimension(o.getWidth(), o.getHeight()), path.o);
		return this;
	}

	/**
	 * @param pixels pixels to override.
	 * @param path   desired file path.
	 * @since 26/06/2013
	 * @author Ulisses Lima
	 */
	public MxImage write(int[] pixels, String path) {
		this.path.reset(path);
		write(pixels);
		return this;
	}

	/**
	 * Writes this image to disc. The image have to be already initialized.
	 * <p>
	 * Only PNG supported ATM.
	 * 
	 * @param path destination.
	 * @return this.
	 * @since 03/07/2013
	 * @author Ulisses Lima
	 */
	public MxImage write(String path) {
		return write($f(path));
	}

	/**
	 * @return ascii representation of this image.
	 * @since Jan 12, 2016
	 * @author Ulisses Lima
	 */
	public String toAscii() {
		return ImageUtils.toAscii(o);
	}

	/**
	 * @return ascii representation of this image.
	 * @since Jan 12, 2016
	 * @author Ulisses Lima
	 */
	public String toAscii(int scale) {
		return ImageUtils.toAscii(o, scale);
	}
}
