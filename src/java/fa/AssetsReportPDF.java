package com.cap.erp.fa;
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
import java.util.ResourceBundle;

import com.cap.util.*;

public class AssetsReportPDF
{
	//column index

	static final int ASNAME_COL 	= 1;
	static final int TAG_COL 		= 2;
	static final int STDESC_COL 	= 3;
	static final int SERIAL_COL 	= 4;
	static final int MODEL_COL 		= 5;
	static final int CLASSNAME_COL	= 6;
	static final int MANUFNAME_COL	= 7;
	static final int VENDNAME_COL 	= 8;
	static final int ACQUDATE_COL 	= 9;
	static final int PONUM_COL 		= 10;
	static final int WARRANTY_COL 	= 11;
	static final int PRICE_COL 		= 12;
	static final int CURRENCY_COL 	= 13;
	static final int DEPRMETH_COL 	= 14;
	static final int LIFETIME_COL 	= 15;
	static final int SALVAGE_COL 	= 16;
	static final int GLACC_COL 		= 17;
	static final int GLDEPACC_COL 	= 18;
	static final int GLEXPACC_COL 	= 19;
	static final int GLLBLACC_COL 	= 20;
	static final int LSTDATE_COL 	= 21;
	static final int TOTMNTH_COL 	= 22;
	static final int TOTDPAMT_COL 	= 23;
	
	static final int ASGLACT1_COL  	= 24;
	static final int ASGLACT2_COL  	= 25;
	static final int ASGLACT3_COL  	= 26;
	static final int ASGLACT4_COL  	= 27;
	static final int ASGLACT5_COL 	= 28;
	
	static final int ASDEPACT1_COL 	= 29;
	static final int ASDEPACT2_COL 	= 30;
	static final int ASDEPACT3_COL 	= 31;
	static final int ASDEPACT4_COL 	= 32;
	static final int ASDEPACT5_COL 	= 33;
	
	static final int ASEXPACT1_COL 	= 34;
	static final int ASEXPACT2_COL 	= 35;
	static final int ASEXPACT3_COL 	= 36;
	static final int ASEXPACT4_COL 	= 37;
	static final int ASEXPACT5_COL 	= 38;
	static final int ASSTATUS_COL 	= 39;
	static final int DEACNUMBER_COL	= 40;
	static final int EXACNUMBER_COL	= 41;
	static final int LBACNUMBER_COL	= 42;

	private static final int topMargin = 60; //180;  
	private static final int bottomMargin = 36;  
	private static final int leftMargin = 15;
	private static final int rightMargin = 15;
	
	private ResourceBundle rb;
	private String title;
	private String status;
	private String fromDate;
	private String toDate;
	
	Document report = new Document(PageSize.LETTER.rotate(), leftMargin, rightMargin, topMargin, bottomMargin);					
	Rectangle page = report.getPageSize();
	PdfWriter myPdfWriter = null;   
	
	// 1" = 72 points
	
	static final int COL_HEAD = 13;
	static final int TOTAL_HEAD = 13;

	static final float[] colWidthLineHeader = {28f, 8f, 10f, 5f, 7f, 10f, 10f, 8f, 10f, 8f, 10f, 10f, 10f};
	static final String[] lineHeader = new String [13];
	
	private RowSet detailRS;

	public ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

	// 1" = 72 points	
	final float TABLE_WIDTH = report.getPageSize().getWidth() - report.leftMargin() - report.rightMargin();
	final float TABLE_WIDTH_TOTAL = report.getPageSize().getWidth() - report.leftMargin() - report.rightMargin();	
	
	//safety Height
	static float SAFETY_HEIGHT = 10f;
	
	//two columns for receipt cash disc info
	static final int column_disc = 1;
	float[] colWidthDisc = {100f};
	
	//globle page number
	int pageNumber = 1;

	String preGLAcc = "";
	String glAcc 	= "";
	String glName 	= "";
	String preGLName= "";

	//Total Per GL
	BigDecimal purGLTotal	= ConstantValue.ZERO;
	BigDecimal depGLTotal	= ConstantValue.ZERO;
	BigDecimal currGLTotal	= ConstantValue.ZERO;
	BigDecimal salGLTotal	= ConstantValue.ZERO;
	
	//Total for Report
	BigDecimal totalPur 	= ConstantValue.ZERO;
	BigDecimal totalDep		= ConstantValue.ZERO;
	BigDecimal totalCurr	= ConstantValue.ZERO;
	BigDecimal totalSal		= ConstantValue.ZERO;
	
	PdfPTable headerTable 		= null; //new PdfPTable(COL_HEAD);
	PdfPTable detailHeaderTable = null; // new PdfPTable(lineHeader.length);
	PdfPTable detailLineTable 	= null; //new PdfPTable(lineHeader.length);

	/**
	 * 
	 */

	public AssetsReportPDF(ResourceBundle rb) {
		super();
		this.rb = rb;
	}

	public void generate() 
	{
		/*
		{
				"Description", 
				"Tag", 
				"Purch Date",
				"Lifetime",
				"Dep Months", 
				"Purch Price",
				"Accum Dep",
				"Curr value",
				"Salvage Amount",
				"Status",
				"Liab GL ",
				"Exp GL", 
				"Dep GL", 
			};
		*/
		
		lineHeader[0] = rb.getString("P12130002");
		lineHeader[1] = rb.getString("P12130004");
		lineHeader[2] = rb.getString("P12130010");
		lineHeader[3] = rb.getString("P12130016");
		lineHeader[4] = rb.getString("P12130027");
		lineHeader[5] = rb.getString("P12130013");
		lineHeader[6] = rb.getString("P12130028");
		lineHeader[7] = rb.getString("P12130029");
		lineHeader[8] = rb.getString("P12130017");
		lineHeader[9] = rb.getString("P12130033");
		lineHeader[10] = rb.getString("P12130020");
		lineHeader[11] = rb.getString("P12130022");
		lineHeader[12] = rb.getString("P12130021");
			
		try {
			detailRS.beforeFirst();
			
			if(status.equals("DR") || status.equals("RG"))
				title = rb.getString("P12130030");
			else if(status.equals("CL"))
				title = rb.getString("P12130034");
			else if(status.equals("IN"))
				title = rb.getString("P12130035");
			else
				title = rb.getString("P12130030");			

			myPdfWriter = PdfWriter.getInstance(report, pdfStream);   
			myPdfWriter.setViewerPreferences(PdfWriter.FitWindow);
			myPdfWriter.setEncryption(null,null,PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
			report.addAuthor("Ximple by Ximple Corporation.");
			report.addTitle(title);

			report.open();	
			
			PdfContentByte cb = myPdfWriter.getDirectContent();
			cb.setLineWidth(ConstantValue.BORDER_WIDTH);

			headerTable = buildHeaderTable();
		
			//column heading
			detailHeaderTable = buildDetailHeaderTable();

			detailLineTable = new PdfPTable(lineHeader.length);
			detailLineTable.setTotalWidth(TABLE_WIDTH);
			detailLineTable.setWidths(colWidthLineHeader);

			float detailTotalHeight_summary = report.getPageSize().getHeight() - report.topMargin() -	report.bottomMargin() - detailHeaderTable.getTotalHeight() - SAFETY_HEIGHT;		

			int lineNum = 0;
			detailRS.beforeFirst();
			while (detailRS.next())
			{
				int lineCount = 0;	
				
				if(!preGLName.equals(detailRS.getString(GLACC_COL)))
				{	
					if (lineNum > 0)
						lineCount = addDetailLineTable(3);  //asset total, asset & new asset line 3 lines
					else	
						lineCount = addDetailLineTable(2);  //asset & new asset line 2 lines
				}
				else
				{	
					lineCount = addDetailLineTable(1);		//same asset 1 line
				}	

				if(detailLineTable.getTotalHeight() > detailTotalHeight_summary )
				{
					detailRS.previous();
					for(int i =0; i < lineCount; i++)
						detailLineTable.deleteLastRow();
				
					headerTable.writeSelectedRows(0, -1, report.leftMargin(), page.getHeight() - report.topMargin() + headerTable.getTotalHeight()+ 10f, myPdfWriter.getDirectContent());			
					detailHeaderTable.writeSelectedRows(0, -1, report.leftMargin(), page.getHeight() - report.topMargin(), myPdfWriter.getDirectContent());
					detailLineTable.writeSelectedRows(0, -1, report.leftMargin(), page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight(), myPdfWriter.getDirectContent());

					cb.moveTo(report.leftMargin(), page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight() - detailLineTable.getTotalHeight());
					cb.lineTo(report.leftMargin() + TABLE_WIDTH, page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight() - detailLineTable.getTotalHeight());
					cb.stroke();
										
					//force page break										
					report.newPage();
					pageNumber++;
					headerTable = buildHeaderTable();
	
					
					detailLineTable = new PdfPTable(lineHeader.length);
					detailLineTable.setTotalWidth(TABLE_WIDTH);
					detailLineTable.setWidths(colWidthLineHeader);
				}
				else
				{
					if(!preGLName.equals(detailRS.getString(GLACC_COL)))
					{	
						if (lineNum > 0)
						{
							purGLTotal		= ConstantValue.ZERO;
							depGLTotal 		= ConstantValue.ZERO;
							currGLTotal		= ConstantValue.ZERO;
							salGLTotal		= ConstantValue.ZERO;
							purGLTotal		= purGLTotal.add(detailRS.getBigDecimal(PRICE_COL));
							depGLTotal 		= depGLTotal.add(detailRS.getBigDecimal(TOTDPAMT_COL));
							currGLTotal		= purGLTotal.subtract(depGLTotal);
							salGLTotal		= salGLTotal.add(detailRS.getBigDecimal(SALVAGE_COL));
						}
						else
						{
							purGLTotal		= purGLTotal.add(detailRS.getBigDecimal(PRICE_COL));
							depGLTotal 		= depGLTotal.add(detailRS.getBigDecimal(TOTDPAMT_COL));
							currGLTotal		= purGLTotal.subtract(depGLTotal);
							salGLTotal		= salGLTotal.add(detailRS.getBigDecimal(SALVAGE_COL));
						}
					}
					else
					{
						purGLTotal		= purGLTotal.add(detailRS.getBigDecimal(PRICE_COL));
						depGLTotal 		= depGLTotal.add(detailRS.getBigDecimal(TOTDPAMT_COL));
						currGLTotal		= purGLTotal.subtract(depGLTotal);
						salGLTotal		= salGLTotal.add(detailRS.getBigDecimal(SALVAGE_COL));
					}	

					
					glAcc	= 	detailRS.getString(ASGLACT1_COL) + detailRS.getString(ASGLACT2_COL) + detailRS.getString(ASGLACT3_COL) +
								detailRS.getString(ASGLACT4_COL) + detailRS.getString(ASGLACT5_COL);
					glName	= 	detailRS.getString(GLACC_COL);
						
					preGLAcc	= glAcc;
					preGLName	= glName;

					lineNum++;

					totalPur	= totalPur.add(detailRS.getBigDecimal(PRICE_COL));
					totalDep 	= totalDep.add(detailRS.getBigDecimal(TOTDPAMT_COL));
					totalCurr	= totalPur.subtract(totalDep);
					totalSal	= totalSal.add(detailRS.getBigDecimal(SALVAGE_COL));			

				}
			}//End of While
			//add last prodLine total
			buildGLTotalLineTable();
			
			//add report total
			buildTotalTable();

			headerTable.writeSelectedRows(0, -1, report.leftMargin(), page.getHeight() - report.topMargin() + headerTable.getTotalHeight()+ 10f, myPdfWriter.getDirectContent());			
			detailHeaderTable.writeSelectedRows(0, -1, report.leftMargin(), page.getHeight() - report.topMargin(), myPdfWriter.getDirectContent());
			detailLineTable.writeSelectedRows(0, -1, report.leftMargin(), page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight(), myPdfWriter.getDirectContent());

			cb.moveTo(report.leftMargin(), page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight() - detailLineTable.getTotalHeight());
			cb.lineTo(report.leftMargin() + TABLE_WIDTH, page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight() - detailLineTable.getTotalHeight());
			cb.stroke();
						
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} catch(Exception exp) {
			System.err.println(exp.getMessage());
		}
 		report.close();
	}

	public void setData(RowSet detRS, String st, String fDate, String tDate) {
		detailRS = detRS;
		status	= st;
		fromDate = fDate;
		toDate	= tDate;
	}
	
	private PdfPTable buildHeaderTable() throws DocumentException, Exception
	{
		PdfPTable table = new PdfPTable(COL_HEAD);
		table.setTotalWidth(TABLE_WIDTH);

		PdfPCell cell = null;	

		// add: title
		cell = new PdfPCell(new Phrase(title, ConstantValue.HELV_14_BOLD_FONT));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(COL_HEAD);
		table.addCell(cell);
		
		if(!fromDate.equals("") && !toDate.equals(""))
		{
			cell = new PdfPCell(new Phrase(fromDate + " " + rb.getString("P12130036") + " " + toDate, ConstantValue.HELV_14_BOLD_FONT));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setColspan(COL_HEAD);
			table.addCell(cell);
		}

		// add: page number
		cell = new PdfPCell(new Phrase(rb.getString("P12130032") + " " + pageNumber, ConstantValue.HELV_8_NORM_FONT));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(COL_HEAD);
		table.addCell(cell);

		return table;
	}

	private PdfPTable buildDetailHeaderTable() throws DocumentException	
	{  		
		PdfPTable table = new PdfPTable(colWidthLineHeader.length);
		table.setTotalWidth(TABLE_WIDTH);
		table.setWidths(colWidthLineHeader);

		PdfPCell cell = null;	
		
		for(int i =0; i<lineHeader.length; i++ )
		{
			cell = new PdfPCell(new Phrase(lineHeader[i], ConstantValue.HELV_7_BOLD_FONT));
			cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
			cell.setBorder(Rectangle.BOX);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBackgroundColor(ConstantValue.BG_COLOR);
			
			table.addCell(cell);
		}
		return table;
	}

	private int addDetailLineTable(int flag) throws DocumentException, SQLException	
	{
		//this method may adding 1, 2 or 3 lines 
		int lineCount = 0;
		PdfPCell cell = null;	
		
		
		if (flag == 3)
		{
			//Print GL Line total  
			cell = new PdfPCell(new Phrase("Total " + preGLName + ":", ConstantValue.HELV_8_BOLD_FONT));
			cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
			cell.setBorder(Rectangle.LEFT);
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
			detailLineTable.addCell(cell);

			cell = new PdfPCell(new Phrase(Format.displayCurrency(purGLTotal), ConstantValue.HELV_8_BOLD_FONT));
			cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
			detailLineTable.addCell(cell);
			
			cell = new PdfPCell(new Phrase(Format.displayCurrency(depGLTotal), ConstantValue.HELV_8_BOLD_FONT));
			cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
			detailLineTable.addCell(cell);
			
			cell = new PdfPCell(new Phrase(Format.displayCurrency(currGLTotal), ConstantValue.HELV_8_BOLD_FONT));
			cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
			detailLineTable.addCell(cell);
			
			cell = new PdfPCell(new Phrase(Format.displayCurrency(salGLTotal), ConstantValue.HELV_8_BOLD_FONT));
			cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
			detailLineTable.addCell(cell);
			
			cell = new PdfPCell(new Phrase("", ConstantValue.HELV_8_NORM_FONT));
			cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
			cell.setBorder(Rectangle.RIGHT);
			cell.setColspan(4);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
			detailLineTable.addCell(cell);		
			lineCount++;
		}
		
		if (flag == 2 || flag == 3)
		{
			cell = new PdfPCell(new Phrase("\n" + "" + rb.getString("P12130031") + " " + detailRS.getString(GLACC_COL) + "\n", ConstantValue.HELV_9_BOLD_FONT));
			cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
			cell.setBorder(Rectangle.LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
			cell.setColspan(COL_HEAD-1);
			detailLineTable.addCell(cell);

			cell = new PdfPCell(new Phrase("", ConstantValue.HELV_9_BOLD_FONT));
			cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
			cell.setBorder(Rectangle.RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
			detailLineTable.addCell(cell);
			lineCount++;
		}	

		cell = new PdfPCell(new Phrase(detailRS.getString(ASNAME_COL), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(detailRS.getString(TAG_COL), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(Format.displayDate(detailRS.getString(ACQUDATE_COL)), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(detailRS.getString(LIFETIME_COL), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(detailRS.getString(TOTMNTH_COL), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		if(detailRS.getBigDecimal(PRICE_COL).doubleValue() != 0d)
			cell = new PdfPCell(new Phrase(Format.displayCurrency(detailRS.getBigDecimal(PRICE_COL)), ConstantValue.HELV_8_NORM_FONT));
		else
			cell = new PdfPCell(new Phrase("", ConstantValue.HELV_8_NORM_FONT));			
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(Format.displayCurrency(detailRS.getBigDecimal(TOTDPAMT_COL)), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(Format.displayCurrency(detailRS.getBigDecimal(PRICE_COL).subtract(detailRS.getBigDecimal(TOTDPAMT_COL))), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
		
		if(detailRS.getBigDecimal(SALVAGE_COL).doubleValue() != 0d)
			cell = new PdfPCell(new Phrase(Format.displayCurrency(detailRS.getBigDecimal(SALVAGE_COL)), ConstantValue.HELV_8_NORM_FONT));
		else
			cell = new PdfPCell(new Phrase("", ConstantValue.HELV_8_NORM_FONT));	
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(detailRS.getString(STDESC_COL), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(detailRS.getString(GLLBLACC_COL), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(detailRS.getString(GLEXPACC_COL), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(detailRS.getString(GLDEPACC_COL), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.RIGHT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		lineCount++;
		
		return lineCount;
	}
	
	private void buildGLTotalLineTable() throws DocumentException, Exception
	{
		PdfPCell cell = null;	

		//Print GL Line total  
		cell = new PdfPCell(new Phrase("Total " + preGLName + ":", ConstantValue.HELV_8_BOLD_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.LEFT);
		cell.setColspan(5);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(Format.displayCurrency(purGLTotal), ConstantValue.HELV_8_BOLD_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(Format.displayCurrency(depGLTotal), ConstantValue.HELV_8_BOLD_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(Format.displayCurrency(currGLTotal), ConstantValue.HELV_8_BOLD_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(Format.displayCurrency(salGLTotal), ConstantValue.HELV_8_BOLD_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase("", ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.RIGHT);
		cell.setColspan(4);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);		
	}
	
	private void buildTotalTable() throws DocumentException, SQLException	
	{
		PdfPCell cell = null;		
				
		//Build Report Totals
		cell = new PdfPCell(new Phrase("Total:", ConstantValue.HELV_8_BOLD_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.LEFT);
		cell.setColspan(5);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(Format.displayCurrency(totalPur), ConstantValue.HELV_8_BOLD_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.TOP);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(Format.displayCurrency(totalDep), ConstantValue.HELV_8_BOLD_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.TOP);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(Format.displayCurrency(totalCurr), ConstantValue.HELV_8_BOLD_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.TOP);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(Format.displayCurrency(totalSal), ConstantValue.HELV_8_BOLD_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.TOP);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase("", ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.RIGHT);
		cell.setColspan(4);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);	
	}		
}
