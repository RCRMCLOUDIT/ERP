package com.cap.util.label;

import javax.sql.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.*;
import java.sql.SQLException;
import com.cap.util.*;


public class WireQueueLabel {
                     
	//data column index
	private static int CUSTNUM_COL 	= 1;
	private static int CUSTNAME_COL	= 2;
	private static int LINENUM_COL 	= 3;
	private static int PRODNAME_COL	= 4;
	private static int MFGCATID_COL = 5;
	private static int WHNAME_COL 	= 6;
	private static int WHPHONE_COL 	= 7;	
	private static int PRTCNTCT_COL = 8;
	private static int SHPCNTCT_COL = 9;
	private static int TODIR1_COL 	= 10;
	private static int TOCITY_COL	= 11;
	private static int TOSTAT_COL 	= 12;
	private static int TOZIP_COL 	= 13;
	private static int TOPROV_COL 	= 14;
	private static int TOCTRY_COL 	= 15;
	private static int TOCOTY_COL 	= 16;
	private static int SHIPTOPHONE_COL 	= 17;
	private static int PRTCUSTNUM_COL 	= 18;
	private static int WHCOMMENTS_COL 	= 19;
	private static int ROUTENUM_COL 	= 20;
	private static int ROUTEDESCR_COL 	= 21;
	private static int REPEID_COL 		= 22;
	
	private int column = 4; // one blank cell on each side of normal cell
	//private int page   = 0; // one blank cell on each side of normal cell
	private RowSet dataRS;
	private String qty;
	private String docId;
	private String tripId;
	private String docType;
	private String nLabel;
	
	private String imgPathRep;
	private String imgPath;
	private String useSSL;

	private static Font to_font       = new Font(Font.HELVETICA, 8, Font.NORMAL);
	private static Font title_B_font  = new Font(Font.HELVETICA, 12, Font.BOLD);
	private static Font title_font    = new Font(Font.HELVETICA, 1, Font.NORMAL);
	private static Font title1_font   = new Font(Font.HELVETICA, 16, Font.NORMAL);
	private static Font title2_B_font = new Font(Font.HELVETICA, 16, Font.BOLD);
	private static Font title2_Big_font = new Font(Font.HELVETICA, 28, Font.BOLD);
	private static Font title3_font   = new Font(Font.HELVETICA, 10, Font.NORMAL);
	private static Font title4_B_font = new Font(Font.HELVETICA, 18, Font.BOLD);
	public ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

	// these parameters are for landscape format letter size 

	private int topMargin    = 2;  
	private int bottomMargin = 0;  

	private final static float[] xPos = {4f};
	private final static float[] yPos = {413f};
	
	private float tableWidth   = 278f;		// 1" = 72 points
	private float cellWidths[] = new float[]{5f, 45f, 45f, 5f};
	
	private PdfWriter myPdfWriter = null;   
	
	
	/**
	 * 
	 */
	public WireQueueLabel() {
		super();
	}

	public void generate() {
	
	PdfPTable myLabelTable = null;
	PdfPCell blank_cell = new PdfPCell(new Phrase(""));
	blank_cell.setBorder(Rectangle.NO_BORDER);

	try {

		Document wireLabel = new Document(PageSize.A6, topMargin, bottomMargin, topMargin, bottomMargin);					
		myPdfWriter = PdfWriter.getInstance(wireLabel, pdfStream);   
		
		wireLabel.addAuthor("Ximple by Ximple Corporation.");
		wireLabel.addTitle("Wire Label");
		wireLabel.open();	
		// prepare data
		
		//TODO silent print, remove for test, print number of Labels copies  --blank or zero
		/*
		if( nLabel.equals("1") || nLabel.equals("") || nLabel.equals("0"))
			myPdfWriter.addJavaScript("this.print(false);", false);
		else
		{
			int numlbl = Integer.parseInt(nLabel);
			String strprints = "";
			
			for(int i =0; i < numlbl ; i++)
			{
				strprints = strprints  +  "this.print(false);";
			}
			
			myPdfWriter.addJavaScript(strprints, false);
		}
		*/
		
		try {
			dataRS.beforeFirst();
			dataRS.next();
			
			myLabelTable = getLabelTable(blank_cell);
			myLabelTable.writeSelectedRows(0, -1, xPos[0], yPos[0], myPdfWriter.getDirectContent());
			wireLabel.newPage();
			myLabelTable = null;
		
		}	catch (SQLException e) {
		System.err.println(e.getMessage());
	    }
		
		wireLabel.close();
		
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		}
	}

	private PdfPTable getLabelTable(PdfPCell blank_cell){

		PdfPTable label_table = new PdfPTable(column); 

		PdfPCell cell = null; //new PdfPCell();
		
		java.util.Date dt = new java.util.Date();
		String strTimeStamp =  java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(dt);
		java.util.Calendar cal = new java.util.GregorianCalendar();
		cal.clear();
		cal.setTime(dt);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss aaa", java.util.Locale.US);
	
		String strDateTime = strTimeStamp + "\n" + sdf.format(java.util.Calendar.getInstance().getTime());
		

		try {
			label_table.setTotalWidth(tableWidth);
			label_table.setSpacingBefore(0f);
			label_table.setSpacingAfter(0f);	
			label_table.setLockedWidth(true);
			label_table.setWidths(cellWidths);

			PdfContentByte cb = myPdfWriter.getDirectContent();
			Barcode128 code128 = new Barcode128();
			code128.setCodeType(BarcodeEAN.EAN13);
			Image image128 = null;	

			PdfPCell blcell = new PdfPCell(new Phrase(""));
			blcell.setBorder(Rectangle.TOP);
			
			PdfPCell spcell = new PdfPCell(new Phrase(" ",title_font));
			spcell.setBorder(Rectangle.NO_BORDER);
			
			if(dataRS.getString(PRTCUSTNUM_COL).equals("Y") && docType.equals("SO"))
			{
				//add Customer Number
				label_table.addCell(blank_cell);		
				cell = new PdfPCell(new Phrase(dataRS.getString(CUSTNUM_COL), title2_Big_font));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				label_table.addCell(cell);		
				label_table.addCell(blank_cell);		
				
			}
			else
			{	
				//	date and time
				label_table.addCell(blank_cell);
				cell = new PdfPCell(new Phrase(strDateTime, to_font)); 
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setColspan(2);
				label_table.addCell(cell);
				label_table.addCell(blank_cell);
			
				//add Comp logo
			
				label_table.addCell(blank_cell);		
				Image PDFLOGO = ConstantValue.getPDFLogo(imgPath, imgPathRep, useSSL, dataRS.getString(REPEID_COL));
				if(PDFLOGO !=null)
				{
					cell = new PdfPCell(PDFLOGO);			
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(Rectangle.NO_BORDER);
					label_table.addCell(cell);
				}
				else
				{
					cell = new PdfPCell(new Phrase(""));
					cell.setBorder(Rectangle.NO_BORDER);
					label_table.addCell(cell);
				}
				
				// add wh name
				cell = new PdfPCell(new Phrase(dataRS.getString(WHNAME_COL), title_B_font));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				label_table.addCell(cell);		
				label_table.addCell(blank_cell);
							
				//add Wh Phone
				label_table.addCell(blank_cell);		
				cell = new PdfPCell(new Phrase("Phone: " + Format.displayPhone(dataRS.getString(WHPHONE_COL)), to_font));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				label_table.addCell(cell);		
				label_table.addCell(blank_cell);		
				label_table.addCell(blank_cell);
			}
			
			//add Ship To 	
			label_table.addCell(blank_cell);	
			cell = new PdfPCell(new Phrase("Ship To:", title_B_font));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(2);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);	
			
			if(dataRS.getString(PRTCUSTNUM_COL).equals("Y") && docType.equals("SO"))
			{
				//customer number does not print
			} 
			else
			{
				//	customer number
				label_table.addCell(blank_cell);
				cell = new PdfPCell(new Phrase(dataRS.getString(CUSTNUM_COL), title4_B_font));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setColspan(2);
				label_table.addCell(cell);
				label_table.addCell(blank_cell);	
			}	
			
			//	customer name
			label_table.addCell(blank_cell);		
			cell = new PdfPCell(new Phrase(dataRS.getString(CUSTNAME_COL),  title1_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			label_table.addCell(cell);	
			label_table.addCell(blank_cell);
			
			if(dataRS.getString(PRTCNTCT_COL).equals("Y") && !dataRS.getString(SHPCNTCT_COL).trim().equals(""))
			{	
				//contac name
				label_table.addCell(blank_cell);		
				cell = new PdfPCell(new Phrase(dataRS.getString(SHPCNTCT_COL), title3_font));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(3);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				label_table.addCell(cell);		
			}	
			
			// add Customer direcc
			String info = "";
			label_table.addCell(blank_cell);		
			info = Format.displayAddressReport(
					dataRS.getString(TODIR1_COL),
					"",
					"",
					"",
					"",
					dataRS.getString(TOPROV_COL),
					dataRS.getString(TOCTRY_COL),
					dataRS.getString(TOCOTY_COL),
					"");
			cell = new PdfPCell(new Phrase(info, to_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);		

			//add Customer City, state....
			label_table.addCell(blank_cell);		
			String direc = dataRS.getString(TOCITY_COL)==null?"":dataRS.getString(TOCITY_COL);
			
			if (direc.length() != 0)
				direc = dataRS.getString(TOSTAT_COL)==null?"":direc + ", " + dataRS.getString(TOSTAT_COL);
			else
				direc = dataRS.getString(TOSTAT_COL)==null?"":dataRS.getString(TOSTAT_COL);
			
			if (direc.length() != 0)
				direc = dataRS.getString(TOZIP_COL)==null?"":direc + " " + dataRS.getString(TOZIP_COL);
			else
				direc = dataRS.getString(TOZIP_COL)==null?"":dataRS.getString(TOZIP_COL);


			cell = new PdfPCell(new Phrase(direc, title2_B_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);
						
			//	add Ship To Contact Phone
			label_table.addCell(blank_cell);		
			cell = new PdfPCell(new Phrase(Format.displayPhone(dataRS.getString(SHIPTOPHONE_COL)), to_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);		
						
			//add space
			label_table.addCell(spcell);		
			label_table.addCell(spcell);		
			label_table.addCell(spcell);		
			label_table.addCell(spcell);	
			
			//Trip & Route
			
			if (!dataRS.getString(ROUTENUM_COL).equals("0") || !tripId.equals("0"))
			{	
				if (!tripId.equals("0"))
				{	
					label_table.addCell(blank_cell);
					cell = new PdfPCell(new Phrase("Trip #: " + tripId, title_B_font));
					cell.setBorder(Rectangle.TOP);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setColspan(2);
					label_table.addCell(cell);		
					label_table.addCell(blank_cell);
				}	
				
				if(!dataRS.getString(ROUTENUM_COL).equals("0"))
				{
					label_table.addCell(blank_cell);
					cell = new PdfPCell(new Phrase("Route: " + dataRS.getString(ROUTENUM_COL) + " - " + dataRS.getString(ROUTEDESCR_COL), title_B_font));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setColspan(2);
					label_table.addCell(cell);		
					label_table.addCell(blank_cell);
				}	

			}
			
			label_table.addCell(blank_cell);
			if(docType.equals("SO"))
				cell = new PdfPCell(new Phrase("Sales Order #", title_B_font));
			else if(docType.equals("TO"))
				cell = new PdfPCell(new Phrase("Transfer #", title_B_font));
			else if(docType.equals("OI"))
				cell = new PdfPCell(new Phrase("Goods Issue #", title_B_font));
			//else if(docType.equals("RT"))
			//	cell = new PdfPCell(new Phrase("Customer Return #", title_B_font));
			
			cell.setBorder(Rectangle.TOP);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			label_table.addCell(cell);		
			cell = new PdfPCell(new Phrase("Line #: " + dataRS.getString(LINENUM_COL), title3_font));
			cell.setBorder(Rectangle.TOP);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);	

			
			label_table.addCell(blank_cell);
			//add docId number & doc line number
			cell = new PdfPCell(new Phrase(docId, title2_B_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			label_table.addCell(cell);		
			code128.setCode(docId);
			image128 = code128.createImageWithBarcode(cb, null, null);
			cell = new PdfPCell(image128);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);
			
			//add space
			label_table.addCell(spcell);		
			label_table.addCell(spcell);		
			label_table.addCell(spcell);		
			label_table.addCell(spcell);		

			//add catalogId & pick qty
			label_table.addCell(blank_cell);
			cell = new PdfPCell(new Phrase(dataRS.getString(MFGCATID_COL), title_B_font));
			cell.setBorder(Rectangle.TOP);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			label_table.addCell(cell);		
			cell = new PdfPCell(new Phrase("(" + qty + ")", title_B_font));
			cell.setBorder(Rectangle.TOP);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);	

			//product name
			label_table.addCell(blank_cell);		
			cell = new PdfPCell(new Phrase(dataRS.getString(PRODNAME_COL), title_B_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			label_table.addCell(cell);	
			label_table.addCell(blank_cell);
			
			if (!dataRS.getString(WHCOMMENTS_COL).trim().equals(""))
			{
				//add space
				label_table.addCell(spcell);		
				label_table.addCell(spcell);		
				label_table.addCell(spcell);		
				label_table.addCell(spcell);		

				//add wh comments 
				label_table.addCell(blank_cell);
				cell = new PdfPCell(new Phrase(dataRS.getString(WHCOMMENTS_COL), title_B_font));
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				label_table.addCell(cell);		
				label_table.addCell(blank_cell);
			}	
			
		} catch (Exception ioe1) {
			System.err.println(ioe1.getMessage());
		}

		return label_table;
	}

	/**
	 * @param set
	 */
	public void setDataRS(RowSet set, String img, String imgRep, String ssl, String ordId, String wrQty, String dType, String nlbl, String trip) {
		dataRS  = set;
		imgPathRep = imgRep;   
		imgPath = img;    //Logo Image
		useSSL = ssl;
		docId = ordId;
		tripId = trip;
		qty = wrQty;
		docType = dType;
		nLabel = nlbl;
	}

}
