package com.cap.util;

/**
 * Constants for Ximple Front End
 * Creation date: (2/17/2004 11:35:40 AM)
 * @author: Yan Wan
 */
//import java.io.FileNotFoundException;
//import java.net.InetAddress;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;


public class ConstantValue
{
	public static final int PAGE_SIZE;
	public static final int BIG_PAGE_SIZE;
	public static final int POPUP_PAGE_SIZE;
	public static final int TAB_PAGE_SIZE;
	public static final int CONTACTINFO_PAGE_SIZE;
	public static final int PAGE_SIZE_50;
	public static final int PAGE_SIZE_20;
	public static final int PAGE_SIZE_30;
	
	public static final java.math.BigDecimal ZERO;
	public static final java.math.BigDecimal ONE;
	public static final String NO_DATA;
	public static final boolean DEBUG;
	public static final String COST_CENTER_BLANK_ID;
	public static final String DELIMITER;
	
   	public static final int MAX_FILE_SIZE;
   	public static final int MAX_BLOB_SIZE;
   	
   	public static final int BARCODE_TYPE;
	
	public static Date BLANK_DATE = null;
	public static Timestamp BLANK_TS = null;
	
	public static final String PRE_TAG;
	public static final String PRENEW_TAG;
	public static final String PREJS_TAG;
	public static final String MID_TAG;
	public static final String MIDDEL_TAG;
	public static final String MIDWAT_TAG;
	public static final String END_TAG;
	public static final String ENDPRE_TAG;

	public static final int CODE_COL;
	public static final int DESC_COL;

	public static final float IMAGE_SIZE;
	//public static Image PDFLOGO;
	//public static Image PDFLabelLOGO;
	
	public static final float PADDING_RIGHT2;
	public static final float PADDING_RIGHT5;
	
	public static final float PADDING_LEFT2;
	public static final float PADDING_LEFT5;
	public static final float PADDING_LEFT20;
	public static final float PADDING_LEFT40;
	public static final float PADDING_LEFT50;
	public static final float PADDING_LEFT75;
	
	public static final float BORDER_WIDTH;
	
	public static final java.awt.Color BG_COLOR;
	
	public static final Font HELV_5_BOLD_FONT;
	public static final Font HELV_6_BOLD_FONT;
	public static final Font HELV_7_BOLD_FONT;
	public static final Font HELV_8_BOLD_FONT;
	public static final Font HELV_9_BOLD_FONT;
	public static final Font HELV_10_BOLD_FONT;
	public static final Font HELV_11_BOLD_FONT;
	public static final Font HELV_12_BOLD_FONT;
	public static final Font HELV_13_BOLD_FONT;
	public static final Font HELV_14_BOLD_FONT;

	public static final Font HELV_6_NORM_FONT;
	public static final Font HELV_7_NORM_FONT;
	public static final Font HELV_8_NORM_FONT;
	public static final Font HELV_9_NORM_FONT;
	
	public static final Font COURIER_8_NORM_FONT;
	public static final Font COURIER_9_NORM_FONT;

	public static String SERVER_IP = "";

	static
	{
		PAGE_SIZE = 12;
		BIG_PAGE_SIZE = 200;
		POPUP_PAGE_SIZE = 12;
		TAB_PAGE_SIZE = 6;
		CONTACTINFO_PAGE_SIZE = 6;
		PAGE_SIZE_50 = 50;
		PAGE_SIZE_20 = 20;
		PAGE_SIZE_30 = 30;
		
		BARCODE_TYPE = com.lowagie.text.pdf.BarcodeEAN.CODE128;
		
		ZERO = new java.math.BigDecimal(0);
		ONE = new java.math.BigDecimal(1);
		
		NO_DATA = "No data was found.";
		DEBUG = true;
		COST_CENTER_BLANK_ID = " ©©© ©©© ©©©0©©©0©©©";
		DELIMITER = "©©©";
		MAX_FILE_SIZE = 15360;//10240;//5120;
		MAX_BLOB_SIZE = 25;
		
		BLANK_DATE = Format.getBlankDate();
		BLANK_TS = Format.getBlankTimestamp();
		
		PRE_TAG = "<a href=&#034;";
		PRENEW_TAG = "<a target=_new href=&#034;";
		PREJS_TAG = "<a href=&#034;javascript:";
		MID_TAG = "&#034;>";
		MIDDEL_TAG = "&#034; onClick=&#034;return deleteConfirm()&#034;>";
		MIDWAT_TAG = "&#034; onClick=&#034;showProgressBar()&#034;>";
		END_TAG = "</a><br>";
		ENDPRE_TAG = "</a><br><a href=&#034;";

		CODE_COL = 1;
		DESC_COL = 2;
		
		IMAGE_SIZE = 0.8f;

		PADDING_RIGHT2 = 2f;
		PADDING_RIGHT5 = 5f;
		
		PADDING_LEFT2 = 2f;
		PADDING_LEFT5 = 5f;
		PADDING_LEFT20 = 20f;
		PADDING_LEFT40 = 40f;
		PADDING_LEFT50 = 50f;
		PADDING_LEFT75 = 75f;
		
		BORDER_WIDTH = 0.6f;
		
		BG_COLOR = new java.awt.Color(0xED, 0xEE, 0xEF);

		//COURIER, HELVETICA
		HELV_5_BOLD_FONT = FontFactory.getFont(BaseFont.HELVETICA, 5, Font.BOLD);
		HELV_6_BOLD_FONT = FontFactory.getFont(BaseFont.HELVETICA, 6, Font.BOLD);
		HELV_7_BOLD_FONT = FontFactory.getFont(BaseFont.HELVETICA, 7, Font.BOLD);
		HELV_8_BOLD_FONT = FontFactory.getFont(BaseFont.HELVETICA, 8, Font.BOLD);
		HELV_9_BOLD_FONT = FontFactory.getFont(BaseFont.HELVETICA, 9, Font.BOLD);
		HELV_10_BOLD_FONT = FontFactory.getFont(BaseFont.HELVETICA, 10, Font.BOLD);
		HELV_11_BOLD_FONT = FontFactory.getFont(BaseFont.HELVETICA, 11, Font.BOLD);
		HELV_12_BOLD_FONT = FontFactory.getFont(BaseFont.HELVETICA, 12, Font.BOLD);
		HELV_13_BOLD_FONT = FontFactory.getFont(BaseFont.HELVETICA, 13, Font.BOLD);
		HELV_14_BOLD_FONT = FontFactory.getFont(BaseFont.HELVETICA, 14, Font.BOLD);

		HELV_6_NORM_FONT = FontFactory.getFont(BaseFont.HELVETICA, 6, Font.NORMAL);
		HELV_7_NORM_FONT = FontFactory.getFont(BaseFont.HELVETICA, 7, Font.NORMAL);
		HELV_8_NORM_FONT = FontFactory.getFont(BaseFont.HELVETICA, 8, Font.NORMAL);
		HELV_9_NORM_FONT = FontFactory.getFont(BaseFont.HELVETICA, 9, Font.NORMAL);

		COURIER_8_NORM_FONT = FontFactory.getFont(BaseFont.COURIER, 8, Font.NORMAL);
		COURIER_9_NORM_FONT = FontFactory.getFont(BaseFont.COURIER, 9, Font.NORMAL);
		
		
		try{
			
			// TODO FOR HITE ONLY URL, FOR INNOVATION comp = 00017	
			//SERVER_IP = "10.1.1.8";
			
			if(SERVER_IP.equals(""))
				SERVER_IP = InetAddress.getLocalHost().getHostAddress();
		}
		catch (Exception e) {
			SERVER_IP = "";
		}
				
	}

	/**
     * ConstantValue constructor comment.
	 */
	public ConstantValue()
	{
		super();
	}
	public static Image getPDFLogo(String imgPath, String repImgPath, String useSSL, String repId)
	{
		Image myPDFLogo = null;
		
		try {
			URL imgUrl = null;

			if(useSSL.equals("Y"))
			{
				if( !repId.equals("0") && !repId.equals(""))
					imgUrl = new URL("https://" + SERVER_IP + repImgPath + repId + ".gif");
				else	
					imgUrl = new URL("https://" + SERVER_IP + imgPath);
			}	
			else {

				if( !repId.equals("0") && !repId.equals(""))
					imgUrl = new URL("http://" + SERVER_IP + repImgPath + repId + ".gif");
				else	
					imgUrl = new URL("http://" + SERVER_IP + imgPath);
			}
			//System.out.println("url=" + imgUrl.toString());
			//**************	
			myPDFLogo = Image.getInstance(imgUrl);
			myPDFLogo.setAbsolutePosition(0,0);
			myPDFLogo.scaleAbsolute(myPDFLogo.getPlainWidth() * ConstantValue.IMAGE_SIZE,  myPDFLogo.getPlainHeight() * ConstantValue.IMAGE_SIZE);
			myPDFLogo.setAlignment(Image.MIDDLE);
		}
		catch (Exception e) {
			myPDFLogo = null;
		}
		return myPDFLogo;
	}
	
	public static Image getPDFSig(String imgPath)
	{
		
		Image myPDFSig = null;
		
		try {
			myPDFSig = Image.getInstance(imgPath);
			myPDFSig.setAbsolutePosition(0,0);
			myPDFSig.scaleToFit(150f, 60f);
			//myPDFSig.scaleAbsolute(myPDFSig.getPlainWidth() * ConstantValue.IMAGE_SIZE,  myPDFSig.getPlainHeight() * ConstantValue.IMAGE_SIZE);
			myPDFSig.setAlignment(Image.MIDDLE);
		}
		catch (Exception e) {
			myPDFSig = null;
		}
		return myPDFSig;
	}

	public static Image getPDFSigUrl(String imgPath, String useSSL)
	{
		Image myPDFSigUrl = null;
		
		try {
			URL imgUrl = null;
			
			if(useSSL.equals("Y"))
				imgUrl = new URL("https://" + SERVER_IP + imgPath);
			else
				imgUrl = new URL("http://" + SERVER_IP + imgPath);
			
			myPDFSigUrl = Image.getInstance(imgUrl);
			myPDFSigUrl.setAbsolutePosition(0,0);
			myPDFSigUrl.scaleToFit(150f, 60f);
			//myPDFSigUrl.scaleAbsolute(myPDFSigUrl.getPlainWidth() * ConstantValue.IMAGE_SIZE,  myPDFSigUrl.getPlainHeight() * ConstantValue.IMAGE_SIZE);
			myPDFSigUrl.setAlignment(Image.MIDDLE);
		}
		catch (Exception e) {
			myPDFSigUrl = null;
		}
		return myPDFSigUrl;
	}
	
	public static Image getPDFLabelLogo(String imgPath)
	{
		Image myLabelLogo = null;
		
		try {
			URL	imgUrl = new URL("http://" + SERVER_IP + imgPath);
			
			myLabelLogo = Image.getInstance(imgUrl);
			myLabelLogo.scaleToFit(170f, 50f);
			myLabelLogo.setAlignment(Image.MIDDLE);
		}
		catch (Exception e) {
			myLabelLogo = null;
		}
		return myLabelLogo;
	}
	public static Image getPDFLabelLogoByURL(URL urlStr)
	{
		Image myLabelLogo = null;
		
		try {
			myLabelLogo = Image.getInstance(urlStr);
			myLabelLogo.scaleToFit(170f, 50f);
			myLabelLogo.setAlignment(Image.MIDDLE);
		}
		catch (Exception e) {
			myLabelLogo = null;
		}
		return myLabelLogo;
	}
}
