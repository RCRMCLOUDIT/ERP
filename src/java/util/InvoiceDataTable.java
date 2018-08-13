package com.cap.util;

import java.util.ResourceBundle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;

import java.io.FileNotFoundException;
import java.net.*;
import java.awt.Color;
import com.cap.erp.report.ReportCell;

public class InvoiceDataTable extends ERPDataTable {


	static final Font INVOICE_HEADER	= FontFactory.getFont(FontFactory.HELVETICA, 30, Font.BOLD);
	static final Color BGCELL = new Color(0xDA, 0xDB, 0xDC);
	static final float CELL_LEADING = 10f;
	private ResourceBundle rb;
	
	public InvoiceDataTable(ResourceBundle rb) throws BadElementException {
        super(3, 3);

        float[] width = {30f, 40f, 30f};
        setWidths(width);
        this.dimension = 3;
		setBorder(Rectangle.NO_BORDER);
		this.rb = rb;
    }
	public InvoiceDataTable(int columns) throws BadElementException {
        super(columns);
    }
    public InvoiceDataTable(int columns, int rows) throws BadElementException {
        super(columns, rows);

        float[] width = {30f, 40f, 30f};
        setWidths(width);
        this.dimension = columns;
		setBorder(Rectangle.NO_BORDER);
    }
    /*
    public InvoiceDataTable(Properties attributes) {
        super(attributes);
    }
    */
    public void printInvoiceHeader(String companyName, String repName, String[] contactInfo, String[] invoiceInfo, String imgPath, String imgPathRep, String useSSL, String repId) throws DocumentException, BadElementException, Exception 
    {

    	ERPDataTable table1 = null;
    	ERPDataTable table2 = null;
    	ERPDataTable table3 = null;
    	ERPDataTable table4 = null;

    	table1 = new ERPDataTable(1);


		// In case of image with troubles the blank cell is printed
		// If the image is not rejected it is resized
    	
		Image PDFLOGO = ConstantValue.getPDFLogo(imgPath, imgPathRep, useSSL, repId);
		if(PDFLOGO !=null)
		{
			table1.setPicture(PDFLOGO, 70f, 170f,50f,-20f);
		}
		else
		{
			URL	imgUrl = null;


			if (repId.equals("0"))
			{	
				if(useSSL.equals("Y"))
				{
					imgUrl = new URL("https://"+ConstantValue.SERVER_IP+imgPath);
				}
				else
				{
					imgUrl = new URL("http://"+ConstantValue.SERVER_IP+	imgPath);
				}
			} else
			{
				if(useSSL.equals("Y"))
				{
					imgUrl = new URL("https://"+ConstantValue.SERVER_IP+imgPathRep);
				}
				else
				{
					imgUrl = new URL("http://"+ConstantValue.SERVER_IP+	imgPathRep);
				}
			}
			try {
				
				Image aImage = Image.getInstance(imgUrl);
				table1.setPicture(aImage, 70f, 170f,50f,-20f);
			} 
			catch (BadElementException e) {
		    	Cell cell = new Cell("\n\n");
		    	cell.setBorder(Rectangle.NO_BORDER);
		    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	cell.setLeading(CELL_LEADING);
		    	table1.addCell(cell);
				System.out.println("PDF Logo rejected.");
			}
			catch (FileNotFoundException e) {
		    	Cell cell = new Cell("\n\n");
		    	cell.setBorder(Rectangle.NO_BORDER);
		    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	cell.setLeading(CELL_LEADING);
		    	table1.addCell(cell);
				System.out.println("PDF FileNotFoundException.");
				/*
				cell = new PdfPCell(new Phrase(compName));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				imgTable.addCell(cell);
				*/
			}
		}	
    	table2 = new ERPDataTable(1);
    	Phrase ph = null;
    	
    	if(repName.equals(""))
    		ph = new Phrase(companyName, title3F);
    		
		else
			ph = new Phrase(repName, title3F);
    	
    	//Phrase ph = new Phrase(companyName, title3F);
    	Cell cell = new Cell(ph);
    	cell.setBorder(Rectangle.NO_BORDER);
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	cell.setLeading(CELL_LEADING);
    	table2.addCell(cell);

    	ph = new Phrase(rb.getString("P12200005"), INVOICE_HEADER);
    	cell = new Cell(ph);
    	cell.setBorder(Rectangle.NO_BORDER);
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	cell.setLeading(CELL_LEADING);
    	table2.addCell(cell);

    	table3 = new ERPDataTable(2);

		for ( int idx=0; idx<2; idx++ ) {
			ph = new Phrase("", title1F);
	    	cell = new Cell(ph);
	    	cell.setBorder(Rectangle.NO_BORDER);
	    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	cell.setLeading(CELL_LEADING);
	    	table3.addCell(cell);
		}
		String[] name = {rb.getString("P12200021")+":", rb.getString("P12200007")+":", rb.getString("P12200008")+":", rb.getString("P12200009")+":"};
    	
    	for (int i=0; i<name.length; i++) {
    		table3.printNameValuePair(name[i], Element.ALIGN_RIGHT, invoiceInfo[i], Element.ALIGN_RIGHT, 10f);
    	}

    	table4 = new ERPDataTable(1);
		ph = new Phrase(" \n\n" + rb.getString("P12200022")+":", headerBF);
    	cell = new Cell(ph);
    	cell.setBorder(Rectangle.NO_BORDER);
    	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    	cell.setLeading(CELL_LEADING);
    	table4.addCell(cell);
    	
    	boolean isFirst = true;
    	float leading;
    	for ( int j=0; j<contactInfo.length; j++ ) {
    		if ( contactInfo[j].length() != 0 ) {
				if ( isFirst ) {
					leading = 20f;
					isFirst = false;
				}
				else {
					leading = 10f;
				}
				ph = new Phrase(contactInfo[j], headerF);
    			cell = new Cell(ph);
    			cell.setBorder(Rectangle.NO_BORDER);
    			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    			cell.setLeading(leading);
    			table4.addCell(cell);
			}
    	}


    	insertTable(table1, 0, 0);
    	insertTable(table2, 0, 1);
    	insertTable(table4, 1, 0);
		insertTable(table3, 0, 2);
    	//insertTable(table3, 1, 2);

    }
    /*
	public void printInvoiceHeader2(String companyName, String[] contactInfo, String[] invoiceInfo)
						throws DocumentException, BadElementException, Exception {
	
		System.out.println("Print Invoice PreDefined Format Header");
	
		ERPDataTable table1 = null;
		ERPDataTable table2 = null;
		ERPDataTable table3 = null;
		ERPDataTable table4 = null;

		table1 = new ERPDataTable(1);

		// In case of image with troubles the blanck cell is printed
		// If the image is not rejected it is resized
		table1.addCell(new Cell(""));

		table2 = new ERPDataTable(1);
		//Phrase ph = new Phrase(companyName, title3F);
		Phrase ph = new Phrase("", title3F);
		Cell cell = new Cell(ph);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setLeading(CELL_LEADING);
		table2.addCell(cell);

		ph = new Phrase("INVOICE IN", INVOICE_HEADER);
		cell = new Cell(ph);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setLeading(CELL_LEADING);
		table2.addCell(cell);

		table3 = new ERPDataTable(2);

		for ( int idx=0; idx<2; idx++ ) {
		ph = new Phrase("", title1F);
		cell = new Cell(ph);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setLeading(CELL_LEADING);
		table3.addCell(cell);
		}

		String[] name = {"", "", "", ""};
		for (int i=0; i<name.length; i++) {
			table3.printNameValuePair(name[i], Element.ALIGN_RIGHT, invoiceInfo[i], Element.ALIGN_RIGHT, 10f);
		}

		table4 = new ERPDataTable(1);
		ph = new Phrase("", headerBF);
		cell = new Cell(ph);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setLeading(CELL_LEADING);
		table4.addCell(cell);
		boolean isFirst = true;
		float leading;
		for ( int j=0; j<contactInfo.length; j++ ) {
			if ( contactInfo[j].length() != 0 ) {
				if ( isFirst ) {
					leading = 20f;
					isFirst = false;
				}
				else {
					leading = 10f;
				}
				ph = new Phrase(contactInfo[j], headerF);
				cell = new Cell(ph);
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setLeading(leading);
				table4.addCell(cell);
			}
		}

		insertTable(table1, 0, 0);
		insertTable(table2, 0, 1);
		insertTable(table4, 1, 0);
		insertTable(table3, 0, 2);

	}
	*/	
	public void printOutRow (ReportCell[] rowData) throws BadElementException
    {

    	int numOfCol = rowData.length;
    	for ( int i=0; i<numOfCol; i++ )
    	{
    		Cell cell = rowData[i].getFilledCell(tblHeaderF);

    		addCell(cell);

    	}

    }
	public void printTblHeader(String[] headers, int[] aligns, float leading) throws Exception {
    	int numOfHeads = headers.length;
    	int numOfAligns = aligns.length;
    	if (numOfHeads != dimension || numOfAligns != dimension)
    		throw new Exception("Number of headers or alignments being passed in does not match table dimension.");

    	Cell cell = null;
    	Chunk chunk = null;

    	for (int i = 0; i < numOfHeads; i++) {
    		chunk = new Chunk(headers[i], tblHeaderF);
    		cell = new Cell(chunk);
     		cell.setHorizontalAlignment(aligns[i]);
    		cell.setBorder(Rectangle.NO_BORDER);
			cell.setBackgroundColor(BGCELL);
			cell.setLeading(leading);
			addCell(cell);
    	}

    }
}
