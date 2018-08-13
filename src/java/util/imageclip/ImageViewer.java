package com.cap.util.imageclip;

import java.awt.*;
import java.awt.image.*;
/*
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
*/
public class ImageViewer extends Panel {

	private Image image;
	private int maxWidth = 1;
	private int maxHeight = 1;

	public ImageViewer() {
		super();
	}

	public Image getImage() {
		return this.image;
	}

	public void setMaxSize(int width, int height) {
		this.maxWidth = width;
		this.maxHeight = height;
	}

	public void setImage(Image image)
	{
		this.image = image;
  		repaint();
	}
	public void setImage(int palette[], int pixels[][]) {
	    int w = pixels.length;
	    int h = pixels[0].length;
	    int pix[] = new int[w * h];

	    // convert to RGB
	    for (int x = w; x-- > 0; ) {
	        for (int y = h; y-- > 0; ) {
	            pix[y * w + x] = palette[pixels[x][y]];
	        }
	    }

	    setImage(w, h, pix);
    }
    public void setImage(int w, int h, int pix[]) {
	        setImage(createImage(new MemoryImageSource(w, h, pix, 0, w)));
    }


	public Dimension getPreferredSize() {

		if (this.image == null) {
			return super.getPreferredSize();
		}

		return adjustImage();
	}


	public void paint (Graphics g)
	{
		super.paint(g);

	if (this.image != null)
		{
			Dimension size = this.getPreferredSize();
			//g.drawImage (this.image, 0, 0,new Double(size.getWidth()).intValue(),new Double(size.getHeight()).intValue(), this);
			//g.drawImage(image, 0, 0, this);


			int x = (getWidth() - image.getWidth(this))/2;
			int y = (getHeight() - image.getHeight(this))/2;
			g.drawImage(image, x, y, this);


  		}
	}


	private Dimension adjustImage()
	{
		Dimension adjusted = null;

		float imageWidth = new Integer(this.image.getWidth(this)).floatValue();
		float imageHeight = new Integer(this.image.getHeight(this)).floatValue();

		float ratio = imageWidth / imageHeight;

		if (this.maxWidth < this.maxHeight)
		{
			adjusted = new Dimension(this.maxWidth,	ratio > 1.0 ? Math.round(this.maxWidth * (1/ratio)) : Math.round(this.maxWidth * ratio));
		}
		else
		{
			adjusted = new Dimension(ratio > 1.0 ? Math.round(this.maxHeight * (1/ratio)) : Math.round(this.maxHeight * ratio), this.maxHeight);
		}
		return adjusted;
	}

}