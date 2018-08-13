package com.cap.util.imageclip;

import java.io.*;
import java.awt.*;
import java.awt.image.*;

public class GIFEncoder {
    short width_, height_;
    int numColors_;
    byte pixels_[], colors_[];

    ScreenDescriptor sd_;
    ImageDescriptor id_;

/**
 * Construct a GIFEncoder. The constructor will convert the image to
 * an indexed color array. <B>This may take some time.</B><P>
 *
 * @param image The image to encode. The image <B>must</B> be
 * completely loaded.
 * @exception AWTException Will be thrown if the pixel grab fails. This
 * can happen if Java runs out of memory. It may also indicate that the image
 * contains more than 256 colors.
 * */
    public GIFEncoder(Image image) throws AWTException {
	width_ = (short)image.getWidth(null);
	height_ = (short)image.getHeight(null);

	int values[] = new int[width_ * height_];
	PixelGrabber grabber = new PixelGrabber(
	    image, 0, 0, width_, height_, values, 0, width_);

	try {
	    if(grabber.grabPixels() != true)
		throw new AWTException("Grabber returned false: " +
				       grabber.status());
	}
	catch (InterruptedException e) { ; }

	byte r[][] = new byte[width_][height_];
	byte g[][] = new byte[width_][height_];
	byte b[][] = new byte[width_][height_];
	int index = 0;
	for (int y = 0; y < height_; ++y)
	    for (int x = 0; x < width_; ++x) {
		r[x][y] = (byte)((values[index] >> 16) & 0xFF);
		g[x][y] = (byte)((values[index] >> 8) & 0xFF);
		b[x][y] = (byte)((values[index]) & 0xFF);
		++index;
	    }
	ToIndexedColor(r, g, b);
    }

/**
 * Construct a GIFEncoder. The constructor will convert the image to
 * an indexed color array. <B>This may take some time.</B><P>
 *
 * Each array stores intensity values for the image. In other words,
 * r[x][y] refers to the red intensity of the pixel at column x, row
 * y.<P>
 *
 * @param r An array containing the red intensity values.
 * @param g An array containing the green intensity values.
 * @param b An array containing the blue intensity values.
 *
 * @exception AWTException Will be thrown if the image contains more than
 * 256 colors.
 * */
    public GIFEncoder(byte r[][], byte g[][], byte b[][]) throws AWTException {
	width_ = (short)(r.length);
	height_ = (short)(r[0].length);

	ToIndexedColor(r, g, b);
    }

/**
 * Writes the image out to a stream in the GIF file format. This will
 * be a single GIF87a image, non-interlaced, with no background color.
 * <B>This may take some time.</B><P>
 *
 * @param output The stream to output to. This should probably be a
 * buffered stream.
 *
 * @exception IOException Will be thrown if a write operation fails.
 * */
    public void Write(OutputStream output) throws IOException {
	BitUtils.WriteString(output, "GIF87a");

	// Logical Screen Descriptor
	ScreenDescriptor sd = new ScreenDescriptor(width_, height_,
						   numColors_);
	sd.Write(output);

	// Global Color Table
	output.write(colors_, 0, colors_.length);

	// Image Descriptor
	ImageDescriptor id = new ImageDescriptor(width_, height_, ',');
	id.Write(output);

	// No local table is included
	byte codesize = BitUtils.BitsNeeded(numColors_);
	if (codesize == 1)
	    ++codesize;
	output.write(codesize);
	// Local Color Table
	//output.write(colors_, 0, colors_.length);

	//Table based image data
	LZWCompressor.LZWCompress(output, codesize, pixels_);
	output.write(0);

	// Trailer finishing with 0x3b
	id = new ImageDescriptor((byte)0, (byte)0, ';');
	id.Write(output);
	output.flush();
    }

    void ToIndexedColor(byte r[][], byte g[][],
			byte b[][]) throws AWTException {
	pixels_ = new byte[width_ * height_];
	colors_ = new byte[256 * 3];
	int colornum = 0;
	for (int x = 0; x < width_; ++x) {
	    for (int y = 0; y < height_; ++y) {
		int search;
		for (search = 0; search < colornum; ++search)
		    if (colors_[search * 3]     == r[x][y] &&
			colors_[search * 3 + 1] == g[x][y] &&
			colors_[search * 3 + 2] == b[x][y])
			break;

		if (search > 255)
		    throw new AWTException("Too many colors.");

		pixels_[y * width_ + x] = (byte)search;

		if (search == colornum) {
		    colors_[search * 3]     = r[x][y];
		    colors_[search * 3 + 1] = g[x][y];
		    colors_[search * 3 + 2] = b[x][y];
		    ++colornum;
		}
	    }
	}
	numColors_ = 1 << BitUtils.BitsNeeded(colornum);
	byte copy[] = new byte[numColors_ * 3];
	System.arraycopy(colors_, 0, copy, 0, numColors_ * 3);
	colors_ = copy;
    }

}

