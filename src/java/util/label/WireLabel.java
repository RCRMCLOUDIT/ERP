package com.cap.util.label;
/*
 * Created on Feb 2, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author jpzhang
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import javax.sql.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import com.cap.util.*;

public class WireLabel {

	//item column index
	private static int PRONAME_COL 	= 1;
	private static int UPCCODE_COL 	= 2;
	private static int LOTNMBR_COL 	= 3;
	private static int LENGTH_COL 	= 4;
	private static int UOM_COL 		= 5;
	private static int LOTID_COL 	= 6;

	private int column=4; // one blank cell on each side of normal cell
	private int startPosition = 1; //which position(1--4) to start label print

	private RowSet dataRS;
	private String lblType;
		
	private static Font to_font = new Font(Font.HELVETICA, 12, Font.BOLD);
	public ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

	// these parameters are for landscape format letter size 

	private int topMargin = 5;  
	private int bottomMargin = 5;  
	private int leftMargin = 38;
	private int rightMargin = 38;

	private final static float[] xPos = { 38f, 396f, 38f, 396f };
	private final static float[] yPos = { 552f, 552f, 251f, 251f };
	
	private float tableWidth = 358f;		// 1" = 72 points,   358=(72*11-38*2)/2
	private float cellWidths[] = new float[]{5f, 45f, 45f, 5f};
	
	private PdfWriter myPdfWriter = null;   
	
	
	/**
	 * 
	 */
	public WireLabel() {
		super();
	}

	public void generate() {
		PdfPTable myLabelTable = null;
		PdfPCell blank_cell = new PdfPCell(new Phrase(""));
		blank_cell.setBorder(Rectangle.NO_BORDER);

		try {

			Document wireLabel = new Document(PageSize.LETTER.rotate(), topMargin, bottomMargin, topMargin, bottomMargin);					
			myPdfWriter = PdfWriter.getInstance(wireLabel, pdfStream);   
			wireLabel.open();	
			// prepare data
			try {
				dataRS.beforeFirst();
				int m = 0;
				while (dataRS.next()){
					//System.out.println("m count=" + m);
					//pass in parameters here when get result set data

					myLabelTable = getLabelTable(blank_cell,
									dataRS.getString(PRONAME_COL), dataRS.getString(UPCCODE_COL), dataRS.getString(LOTNMBR_COL),
									dataRS.getBigDecimal(LENGTH_COL), dataRS.getString(UOM_COL), dataRS.getString(LOTID_COL));
					int p = ( m + startPosition)%4;
					if ( p == 0){
						myLabelTable.writeSelectedRows(0, -1, xPos[3], yPos[3], myPdfWriter.getDirectContent());
						wireLabel.newPage();
					}else{	
						myLabelTable.writeSelectedRows(0, -1, xPos[p-1], yPos[p-1], myPdfWriter.getDirectContent());
					}
					m++;
					myLabelTable = null;
				}
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			wireLabel.close();
	
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		}
	}

	private PdfPTable getLabelTable(PdfPCell blank_cell, String type, String id, String reelNo, BigDecimal length, String uom, String lotId){

		PdfPTable label_table = new PdfPTable(column); 

		PdfPCell cell = null; //new PdfPCell();		

		//PdfPCell blank_cell = new PdfPCell(new Phrase(""));
		//blank_cell.setBorder(Rectangle.NO_BORDER);

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
			
			label_table.addCell(blank_cell);		
			cell = new PdfPCell(new Phrase("\n", to_font));
			cell.setBorder(Rectangle.NO_BORDER);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);		
			label_table.addCell(blank_cell);		

			label_table.addCell(blank_cell);		
			cell = new PdfPCell(new Phrase("\n", to_font));
			cell.setBorder(Rectangle.NO_BORDER);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);		
			label_table.addCell(blank_cell);		

			label_table.addCell(blank_cell);		
			cell = new PdfPCell(new Phrase("Product: " + type + "\n\n", to_font));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.BOTTOM);
			cell.setColspan(2);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);		

			label_table.addCell(blank_cell);		
			label_table.addCell(blank_cell);		
			label_table.addCell(blank_cell);		
			label_table.addCell(blank_cell);		

			//add barcode upc #
			code128.setCode(id);
			image128 = code128.createImageWithBarcode(cb, null, null);
			label_table.addCell(blank_cell);		
			cell = new PdfPCell(image128);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setColspan(2);
			label_table.addCell(cell);
			label_table.addCell(blank_cell);		

			label_table.addCell(blank_cell);		
			cell = new PdfPCell(new Phrase(lblType + ": " + id + "\n\n" , to_font));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);		

			label_table.addCell(blank_cell);		
			label_table.addCell(blank_cell);		
			label_table.addCell(blank_cell);		
			label_table.addCell(blank_cell);		

			label_table.addCell(blank_cell);		
			//add barcode lot #
			code128.setCode(reelNo);
			image128 = code128.createImageWithBarcode(cb, null, null);
			cell = new PdfPCell(image128);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			label_table.addCell(cell);
			//add barcode length #
			code128.setCode(Format.formatQty(length));
			image128 = code128.createImageWithBarcode(cb, null, null);
			cell = new PdfPCell(image128);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			label_table.addCell(cell);
			label_table.addCell(blank_cell);		

			label_table.addCell(blank_cell);		
			cell = new PdfPCell(new Phrase("Lot #: " + reelNo + "\n", to_font));
			cell.setBorder(Rectangle.NO_BORDER);
			label_table.addCell(cell);
			cell = new PdfPCell(new Phrase("Length: " + Format.formatQty(length) + " " + uom, to_font));
			cell.setBorder(Rectangle.NO_BORDER);
			label_table.addCell(cell);		
			label_table.addCell(blank_cell);		

		} catch (Exception ioe1) {
			System.err.println(ioe1.getMessage());
		}

		return label_table;
	}

	/**
	 * @param i
	 */
	public void setStartPosition(int i) {
		startPosition = i;
	}

	/**
	 * @param set
	 */
	public void setDataRS(RowSet set, String lType) {
		dataRS  = set;
		lblType = lType;
	}

}
