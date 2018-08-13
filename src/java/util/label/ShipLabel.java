package com.cap.util.label;

import javax.sql.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.*;
import java.sql.SQLException;
import com.cap.util.*;

public class ShipLabel {

	//header column index
	private static int FRNAME_COL 	= 1;
	private static int FRDIR1_COL 	= 2;
	private static int FRDIR2_COL 	= 3;
	private static int FRDIR3_COL 	= 4;
	private static int FRCITY_COL	= 5;
	private static int FRSTAT_COL 	= 6;
	private static int FRZIP_COL 	= 7;
	private static int FRPROV_COL 	= 8;
	private static int FRCOTY_COL 	= 9;
	private static int FRCTRY_COL 	= 10;
	private static int FRPHON_COL	= 11;
	private static int TONAME_COL 	= 12;
	private static int TODIR1_COL 	= 13;
	private static int TODIR2_COL 	= 14;
	private static int TODIR3_COL 	= 15;
	private static int TOCITY_COL	= 16;
	private static int TOSTAT_COL 	= 17;
	private static int TOZIP_COL 	= 18;
	private static int TOPROV_COL 	= 19;
	private static int TOCOTY_COL 	= 20;
	private static int TOCTRY_COL 	= 21;
	private static int TOPHON_COL	= 22;
	private static int PACQTY_COL	= 23;
	private static int SHPNBR_COL	= 24;
	private static int SOID_COL		= 25;
	private static int HDOCTYPE_COL	= 26;
	private static int TRIPID_COL	= 27;
	private static int ROUTENBR_COL	= 28;
	private static int ROUTEDES_COL	= 29;
	private static int BLSHIP_COL	= 30;
	private static int PRTCNTCT_COL = 31;
	private static int SHPCNTCT_COL = 32;
	private static int CUSTNUM_COL  = 33;
	private static int PRTCUSTNUM_COL  = 34;
	private static int REPEID_COL   = 35;
	private static int PRTPCKSLTNUMBCODE_COL   = 36;
	
	//detail column index
	private static int DOCID_COL 	= 1;
	private static int CUSTPO_COL 	= 2;
	private static int JOBNAM_COL 	= 3;
	private static int DOCTYPE_COL 	= 4;
	private static int MRKCTN_COL 	= 5;
	private static int PACKGE_COL 	= 6;
	private static int PCKCNT_COL 	= 7;
	private static int RELNUM_COL 	= 8;

	private int column = 4; // one blank cell on each side of normal cell
	private int page   = 0; // one blank cell on each side of normal cell
	private int cntdet = 0;
	private RowSet headRS;
	private RowSet dataRS;
	//private String lblType;
	
	private String imgPathRep;
	private String imgPath;
	private String useSSL;

	private static Font to_font       = new Font(Font.HELVETICA, 8, Font.NORMAL);
	private static Font title_B_font  = new Font(Font.HELVETICA, 12, Font.BOLD);
	private static Font title_font    = new Font(Font.HELVETICA, 1, Font.NORMAL);
	private static Font title1_font   = new Font(Font.HELVETICA, 16, Font.NORMAL);
	private static Font title2_B_font = new Font(Font.HELVETICA, 16, Font.BOLD);
	private static Font title2_Big_font = new Font(Font.HELVETICA, 28, Font.BOLD);
	private static Font title3_Big_font = new Font(Font.HELVETICA, 32, Font.BOLD);
	private static Font title3_font   = new Font(Font.HELVETICA, 10, Font.NORMAL);
	public ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

	// these parameters are for landscape format letter size 

	private int topMargin    = 2;  
	private int bottomMargin = 0;  

	private final static float[] xPos = {4f};
	private final static float[] yPos = {413f};
	
	private float tableWidth   = 278f;		// 1" = 72 points
	private float cellWidths[] = new float[]{5f, 35f, 55f, 5f};
	
	private PdfWriter myPdfWriter = null;   
	
	
	/**
	 * 
	 */
	public ShipLabel() {
		super();
	}

	public void generate() {
		PdfPTable myLabelTable = null;
		PdfPCell blank_cell = new PdfPCell(new Phrase(""));
		blank_cell.setBorder(Rectangle.NO_BORDER);

		try {

			Document wireLabel = new Document(PageSize.A6, topMargin, bottomMargin, topMargin, bottomMargin);					
			myPdfWriter = PdfWriter.getInstance(wireLabel, pdfStream);   
			wireLabel.open();	
			// prepare data
			try {
				headRS.beforeFirst();
				headRS.next();
				
				dataRS.beforeFirst();
				while (dataRS.next()){
					cntdet = Integer.parseInt(dataRS.getString(PCKCNT_COL));
					
					for (int j =1; j<=cntdet; j++){	
						page++;
						myLabelTable = getLabelTable(blank_cell);
						myLabelTable.writeSelectedRows(0, -1, xPos[0], yPos[0], myPdfWriter.getDirectContent());
						wireLabel.newPage();
						myLabelTable = null;
					}
				}
			} catch (SQLException e) {
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

		//PdfPCell blank_cell = new PdfPCell(new Phrase(""));
		//blank_cell.setBorder(Rectangle.NO_BORDER);

		try {
			
			String docType = dataRS.getString(DOCTYPE_COL);
			String info = "";
			
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
			spcell.setBorder(Rectangle.TOP);
			
			
			if(headRS.getString(PRTCUSTNUM_COL).equals("Y") && docType.equals("SO"))
			{
			  //add Customer Number
			  label_table.addCell(blank_cell);		
			  cell = new PdfPCell(new Phrase(headRS.getString(CUSTNUM_COL), title2_Big_font));
			  cell.setBorder(Rectangle.NO_BORDER);
			  cell.setColspan(2);
			  cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			  label_table.addCell(cell);		
			  label_table.addCell(blank_cell);		
			}
			else
			{
				//add Wh Name
				label_table.addCell(blank_cell);		
				cell = new PdfPCell(new Phrase(headRS.getString(BLSHIP_COL).equals("Y")?"":headRS.getString(FRNAME_COL), title_B_font));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				label_table.addCell(cell);		
				label_table.addCell(blank_cell);		
				
				//add Comp logo			Wh address
				label_table.addCell(blank_cell);
				
				Image PDFLOGO = ConstantValue.getPDFLogo(imgPath, imgPathRep, useSSL, headRS.getString(REPEID_COL));
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
				
				if( !headRS.getString(BLSHIP_COL).equals("Y"))
					Format.displayAddressReport(
						headRS.getString(FRDIR1_COL),
						headRS.getString(FRDIR2_COL),
						headRS.getString(FRDIR3_COL),
						headRS.getString(FRCITY_COL),
						headRS.getString(FRSTAT_COL),
						headRS.getString(FRPROV_COL),
						headRS.getString(FRCTRY_COL),
						headRS.getString(FRCOTY_COL),
						headRS.getString(FRZIP_COL));
				
				cell = new PdfPCell(new Phrase(info, to_font));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				label_table.addCell(cell);		
				label_table.addCell(blank_cell);		
				
				//add Wh Phone
				label_table.addCell(blank_cell);		
				cell = new PdfPCell(new Phrase("Phone: " + Format.displayPhone(headRS.getString(FRPHON_COL)), to_font));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				label_table.addCell(cell);		
				label_table.addCell(blank_cell);		
				label_table.addCell(blank_cell);
				
			}	

			//add Ship To 			Count
			String labelIndex = page + " Of " + headRS.getString(PACQTY_COL);	
			label_table.addCell(blcell);		
			if(headRS.getString(HDOCTYPE_COL).equals("RT"))
				cell = new PdfPCell(new Phrase("Pick-up From:", title_B_font));
			else	
				cell = new PdfPCell(new Phrase(headRS.getString(BLSHIP_COL).equals("Y")?"":"Ship To:", title_B_font));
			cell.setBorder(Rectangle.TOP);
			label_table.addCell(cell);		
			cell = new PdfPCell(new Phrase(labelIndex, title_B_font));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.TOP);
			label_table.addCell(cell);		
			label_table.addCell(blcell);		

			//add Customer Name
			label_table.addCell(blank_cell);		
			cell = new PdfPCell(new Phrase(headRS.getString(BLSHIP_COL).equals("Y")?"":headRS.getString(TONAME_COL), title1_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			label_table.addCell(cell);		

			if(headRS.getString(PRTCNTCT_COL).equals("Y") && !headRS.getString(SHPCNTCT_COL).trim().equals(""))
			{	
				//contac name
				label_table.addCell(blank_cell);		
				cell = new PdfPCell(new Phrase(headRS.getString(SHPCNTCT_COL), title3_font));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(3);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				label_table.addCell(cell);		
			}	
			
			//add Customer direcc
			label_table.addCell(blank_cell);		
			info = Format.displayAddressReport(
					headRS.getString(TODIR1_COL),
					headRS.getString(TODIR2_COL),
					headRS.getString(TODIR3_COL),
					"",
					"",
					headRS.getString(TOPROV_COL),
					headRS.getString(TOCTRY_COL),
					headRS.getString(TOCOTY_COL),
					"");
					
			//if print logo, keep small size
			if(headRS.getString(PRTCUSTNUM_COL).equals("Y") && docType.equals("SO"))
				cell = new PdfPCell(new Phrase(info, title2_B_font));
			else
				cell = new PdfPCell(new Phrase(info, to_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);		

			//add Customer City, state....
			label_table.addCell(blank_cell);		
			String direc = headRS.getString(TOCITY_COL)==null?"":headRS.getString(TOCITY_COL);
			
			if (direc.length() != 0)
				direc = headRS.getString(TOSTAT_COL)==null?"":direc + ", " + headRS.getString(TOSTAT_COL);
			else
				direc = headRS.getString(TOSTAT_COL)==null?"":headRS.getString(TOSTAT_COL);
			
			if (direc.length() != 0)
				direc = headRS.getString(TOZIP_COL)==null?"":direc + " " + headRS.getString(TOZIP_COL);
			else
				direc = headRS.getString(TOZIP_COL)==null?"":headRS.getString(TOZIP_COL);


			cell = new PdfPCell(new Phrase(direc, title2_B_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);		

			//add Customer Phone
			label_table.addCell(blank_cell);		
			cell = new PdfPCell(new Phrase(Format.displayPhone(headRS.getString(TOPHON_COL)), to_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);		

			//add space
			//label_table.addCell(spcell);		
			//label_table.addCell(spcell);		
			//label_table.addCell(spcell);		
			//label_table.addCell(spcell);		

			//trip number
			label_table.addCell(blcell);		
			cell = new PdfPCell(new Phrase("Trip Number: " + (headRS.getString(TRIPID_COL).equals("0")?"":headRS.getString(TRIPID_COL)), title3_font));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			label_table.addCell(cell);		
			label_table.addCell(blcell);		
			
			//route
			label_table.addCell(blank_cell);		
			cell = new PdfPCell(new Phrase("Route: " + (headRS.getString(ROUTENBR_COL).equals("0")?"":headRS.getString(ROUTENBR_COL)) + "  " + headRS.getString(ROUTEDES_COL), title3_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);		
			
			//add space
			label_table.addCell(spcell);		
			label_table.addCell(spcell);		
			label_table.addCell(spcell);		
			label_table.addCell(spcell);		

			//add docId label & docId bar code
			label_table.addCell(blank_cell);
			
			
			if(docType.equals("SO"))
				cell = new PdfPCell(new Phrase("Sales Order Number", title_B_font));
			else if(docType.equals("TO"))
				cell = new PdfPCell(new Phrase("Transfer Number", title_B_font));
			else if(docType.equals("OI"))
				cell = new PdfPCell(new Phrase("Goods Issue Number", title_B_font));
			else if(docType.equals("RT"))
				cell = new PdfPCell(new Phrase("Customer Return Number", title_B_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			label_table.addCell(cell);		

			//do not prt barCode for SO only, print large order#, per Michaels request
			//code128.setCode(dataRS.getString(DOCID_COL));
			//image128 = code128.createImageWithBarcode(cb, null, null);
			//cell = new PdfPCell(image128);
			if (docType.equals("SO"))
				cell = new PdfPCell(new Phrase(dataRS.getString(DOCID_COL), title2_Big_font));
			else
			{
				code128.setCode(dataRS.getString(DOCID_COL));
				image128 = code128.createImageWithBarcode(cb, null, null);
				cell = new PdfPCell(image128);
			}
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

			if(headRS.getString(PRTPCKSLTNUMBCODE_COL).equals("Y"))
			{
				//add pack slip number label & pack slip bar code
				label_table.addCell(blank_cell);		
				cell = new PdfPCell(new Phrase("Packing Slip" + "\n" + "Number", title_B_font));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				label_table.addCell(cell);		
				code128.setCode(headRS.getString(SHPNBR_COL));
				image128 = code128.createImageWithBarcode(cb, null, null);
				cell = new PdfPCell(image128);
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				label_table.addCell(cell);		
				label_table.addCell(blank_cell);
			} 
			else
			{
				label_table.addCell(blank_cell);		
				cell = new PdfPCell(new Phrase("Packing Slip" + "\n" + "Number", title_B_font));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				label_table.addCell(cell);		
				cell = new PdfPCell(new Phrase(headRS.getString(SHPNBR_COL), title3_Big_font));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				label_table.addCell(cell);		
				label_table.addCell(blank_cell);
			}
				
			
			//add customer po for SO and SOTransfer
			label_table.addCell(blcell);		
			if(docType.equals("SO") || (docType.equals("TO") && !headRS.getString(SOID_COL).equals("0") ))
				cell = new PdfPCell(new Phrase("Cust PO / Rel. #: " + dataRS.getString(CUSTPO_COL) + " / " + dataRS.getString(RELNUM_COL), title3_font));
			else
				cell = new PdfPCell(new Phrase(" ", title3_font));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			label_table.addCell(cell);		
			label_table.addCell(blcell);		
			
			//add package number
			label_table.addCell(blank_cell);		
			cell = new PdfPCell(new Phrase("Package Number: " + dataRS.getString(PACKGE_COL), title_B_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);		

			//add Job name label for SO and SOTransfer
			label_table.addCell(blcell);		

			if(docType.equals("SO") || (docType.equals("TO") && !headRS.getString(SOID_COL).equals("0") ))
				cell = new PdfPCell(new Phrase("Job:", title_B_font));
			else
				cell = new PdfPCell(new Phrase(" ", title_B_font));
			cell.setBorder(Rectangle.TOP);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			label_table.addCell(cell);		
			label_table.addCell(blcell);
			
			//add Job name desc
			label_table.addCell(blank_cell);		
			if(docType.equals("SO") || (docType.equals("TO") && !headRS.getString(SOID_COL).equals("0") ))
				cell = new PdfPCell(new Phrase(dataRS.getString(JOBNAM_COL), title3_font));
			else
				cell = new PdfPCell(new Phrase(" ", title3_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);		
			
			//add mark Carton label
			label_table.addCell(blcell);		
			cell = new PdfPCell(new Phrase("Mark Carton:", title_B_font));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			label_table.addCell(cell);		
			label_table.addCell(blcell);		

			//add carton mark desc
			label_table.addCell(blank_cell);		
			cell = new PdfPCell(new Phrase(dataRS.getString(MRKCTN_COL), title3_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);		
			
		} catch (Exception ioe1) {
			System.err.println(ioe1.getMessage());
		}

		return label_table;
	}

	/**
	 * @param set
	 */
	public void setDataRS(RowSet head, RowSet set, String lType, String img, String imgRep, String ssl) {
		headRS  = head;
		dataRS  = set;
		//lblType = lType;
		imgPathRep = imgRep; 
		imgPath = img;    //Logo Image
		useSSL = ssl;
	}

}