package com.cap.util;


import java.awt.Font;
import java.awt.font.*;
import java.awt.geom.AffineTransform;


public class StringSize {

	TextLayout textLayout;
	Font font;
	FontRenderContext frc;

	public StringSize(String fontFamily, String style, String pointerSize) {
		String fontStr = fontFamily + "-" + style + "-" + pointerSize;
		init(fontStr);
   }

	public StringSize(String fontFormat) {
		init(fontFormat);
	}

	private void init(String fontStr) {
		font = Font.decode(fontStr);
		AffineTransform tx = font.getTransform();
		frc = new FontRenderContext(tx, true, true);
	}
   
   public float getAdvance(String text) {
   		textLayout = new TextLayout(text, font, frc);
   		return textLayout.getAdvance();
   }
} 				 
