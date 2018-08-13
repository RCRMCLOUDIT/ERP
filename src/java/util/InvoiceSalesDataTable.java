package com.cap.util;

import com.lowagie.text.*;
import java.net.*;
import java.awt.Color;
import com.cap.erp.report.ReportCell;

public class InvoiceSalesDataTable extends ERPDataTable {


	protected Font INVOICE_HEADER	= FontFactory.getFont(FontFactory.HELVETICA, 30, Font.BOLD);
	protected Color BGCELL = new Color(0xDA, 0xDB, 0xDC);

    
    private Cell title_cell = null;
    private Cell companyName_cell = null;
    private Cell companyLogo_cell = null;
    private Cell[] addInfo_cell = new Cell[4];
    private Cell blankLine_cell = null;
    private Cell blankTitleCell = null;
    private Cell blankAddressCell = null;
    private Cell blankCell = null;
    private Cell iName_cell = null;
    private Cell iValue_cell = null;
	//private ERPDataTable invoiceInfo_table = null;
    
	private float[] width = {30f, 17f, 6f, 17f, 15f,1f, 15f};
    final int CELL_NUMBER = width.length;
	private String[] temp = {"Invoice #:", "Date:", "Payment Terms:", "Due Date:"};
	
	public InvoiceSalesDataTable() throws BadElementException {
        super(7);

        setWidths(width);
        this.dimension = CELL_NUMBER;
		setBorder(Rectangle.NO_BORDER);
    }
	public InvoiceSalesDataTable(int columns) throws BadElementException {
        super(columns);
    }
    public InvoiceSalesDataTable(int columns, int rows) throws BadElementException {
        super(columns, rows);

        //float[] width = {30f, 17f, 6f, 17f, 1f, 14f,15f};
        //setWidths(width);
        //this.dimension = columns;
		setBorder(Rectangle.NO_BORDER);
    }

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

    public void printInvoiceSalesHeader(String companyName, String title, String[] invoiceName, String[] invoiceInfo, String[] addInfo, String imgPath, String useSSL)
    					throws DocumentException, BadElementException, Exception {
        try {

            /****************************************************************
            * Initialization of Fixed objects to be used within the Header *
            ****************************************************************/
            // Blank Cells to control the cell's high
/*    private Cell billTo_cell = null;
    private Cell shipTo_cell = null;
    private Cell title_cell = null;
    private Cell companyName_cell = null;
    private Cell companyLogo_cell = null;
    private Cell blankTitleCell = null;
    private Cell blankAddressCell = null;
	private ERPDataTable invoiceInfo_table = null;
	*/
            blankTitleCell = new Cell(new Chunk(" ", headerF)); //keep space for Logo
            blankTitleCell.setLeading(70f);
            blankTitleCell.setBorder(Rectangle.NO_BORDER);

			blankAddressCell = new Cell(new Chunk(" ", headerF)); //keep space between address
			blankAddressCell.setColspan(1);
            blankAddressCell.setBorder(Rectangle.NO_BORDER);
            
			blankCell = new Cell(new Chunk(" ", headerF)); //keep space between address
			blankCell.setColspan(1);
            blankCell.setBorder(Rectangle.NO_BORDER);             
            //blankAddressCell.setLeading(80f); 
             
            blankLine_cell = new Cell(new Chunk(" ", headerF)); //a blank line between To and address
            blankLine_cell.setLeading(1f);
            blankLine_cell.setColspan(CELL_NUMBER);
            blankLine_cell.setBorder(Rectangle.NO_BORDER);

        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (Exception ioe1) {
            System.err.println(ioe1.getMessage());
        }
    

        URL	logoURL = null; 
        if(useSSL.equals("Y"))
        	logoURL = new URL("https://"+ConstantValue.SERVER_IP+ imgPath);
        else 
        	logoURL = new URL("http://"+ConstantValue.SERVER_IP+ imgPath);
        	
        if (logoURL == null) {
                companyLogo_cell = new Cell("");
        } else {
                try {
                    Image logo = Image.getInstance(logoURL);
                    logo.scaleToFit(170f, 50f);
                    logo.setAlignment(Image.MIDDLE);
                    float yPos = -45f;//-20f;
                    if (logo.getPlainHeight() > 0)
                        yPos = ((50f - logo.getPlainHeight()) / 2f) + yPos;
                    companyLogo_cell = new Cell(new Chunk(logo, 0, yPos));
                } catch (Exception e_) {
                    companyLogo_cell = new Cell("");
                    System.out.println("PDF Logo rejected.");
                }
        }
        companyLogo_cell.setColspan(1);
        companyLogo_cell.setBorder(Rectangle.NO_BORDER);
        companyLogo_cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        companyLogo_cell.setHorizontalAlignment(Element.ALIGN_LEFT);

        // Company Name
        companyName_cell = new Cell(new Chunk(companyName.trim(), title3F));
        companyName_cell.setColspan(CELL_NUMBER);
        companyName_cell.setMaxLines(1);
        companyName_cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        companyName_cell.setBorder(Rectangle.NO_BORDER);
        companyName_cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        companyName_cell.setLeading(10);

        // Title
        title_cell = new Cell(new Chunk(title.trim(), INVOICE_HEADER));
        title_cell.setColspan(3);
        title_cell.setMaxLines(1);
        title_cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        title_cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        title_cell.setBorder(Rectangle.NO_BORDER);
        title_cell.setLeading(70);

        

        for(int i=0; i<addInfo_cell.length; ){
	        // additional information1 first line
	        addInfo_cell[i] = new Cell(new Chunk(((addInfo != null && i<addInfo.length)?addInfo[i]:" "), headerBF));
	        //addInfo_cell[i].setColspan((CELL_NUMBER-1)/2);
	        addInfo_cell[i].setHorizontalAlignment(Element.ALIGN_LEFT);
	        addInfo_cell[i].setBorder(Rectangle.NO_BORDER);
	        addInfo_cell[i].setVerticalAlignment(Element.ALIGN_BOTTOM);

		    // additional information1 second line
		    addInfo_cell[i+1] = new Cell(new Chunk(((addInfo != null && i+1<addInfo.length)?addInfo[i+1]:" "), headerF));
		    addInfo_cell[i+1].setHorizontalAlignment(Element.ALIGN_LEFT);
		    addInfo_cell[i+1].setBorder(Rectangle.NO_BORDER);
		    addInfo_cell[i+1].setVerticalAlignment(Element.ALIGN_BOTTOM);
	        addInfo_cell[i+1].setLeading(10);

	        i=i+2;
        }

    	String[] name = null;
    	if(invoiceName == null){
	    	name = temp;
    	}
    	else{
	    	name = invoiceName;
    	}
    	String iName = "\n \n \n ";
    	String iValue = "\n \n \n ";
    	for (int i=0; i<name.length; i++) {
	    	iName = iName + "\n" + name[i];
	    	iValue = iValue + "\n" + (i<invoiceInfo.length?invoiceInfo[i]:" ");
    		//invoiceInfo_table.printNameValuePair(name[i], Element.ALIGN_RIGHT, invoiceInfo[i], Element.ALIGN_RIGHT, 10f);
    	}
	    iName_cell = new Cell(new Chunk(iName, headerBF));
	    iName_cell.setColspan(1);
	    iName_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    iName_cell.setBorder(Rectangle.NO_BORDER);
	    iName_cell.setVerticalAlignment(Element.ALIGN_BASELINE);
	    iName_cell.setLeading(10);
	    
	    iValue_cell = new Cell(new Chunk(iValue, headerF));
	    iValue_cell.setColspan(1);
	    iValue_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    iValue_cell.setBorder(Rectangle.NO_BORDER);
	    iValue_cell.setVerticalAlignment(Element.ALIGN_BASELINE);
	    iValue_cell.setLeading(10);
	    
    	this.addCell(companyName_cell); //first line
    		
    	this.addCell(companyLogo_cell); //second line  	
    	this.addCell(title_cell);		//second line
    	this.addCell(iName_cell);		//second line
    	this.addCell(blankTitleCell);		//second line
    	this.addCell(iValue_cell);		//second line  	


    	if(addInfo != null){
	    	addInfo_cell[0].setColspan(2);
	    	this.addCell(addInfo_cell[0],2,0);
	    	this.addCell(blankCell,2,2);
	    	addInfo_cell[2].setColspan(4);
	    	this.addCell(addInfo_cell[2],2,3);

	    	this.addCell(blankLine_cell,3,0);

	    	addInfo_cell[1].setColspan(2);
	    	this.addCell(addInfo_cell[1],4,0);
	    	this.addCell(blankAddressCell,4,2);
	    	addInfo_cell[3].setColspan(4);
	    	this.addCell(addInfo_cell[3],4,3);     	 
    	}

    	blankLine_cell.setLeading(15);
    	this.addCell(blankLine_cell,5,0);
    	
    }
}
