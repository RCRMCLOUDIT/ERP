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
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.cap.util.*;

public class DepreciationReportPDF
{
	//column index
	static final int ASFLAG_COL		= 1;
	static final int ASNAME_COL	 	= 2;
	static final int ASTAG_COL 	 	= 3;
	static final int ASACQDATE_COL 	= 4;
	static final int ASPRICE_COL 	= 5;
	static final int ASLIFETIME_COL = 6;
	static final int ASDEPRATE_COL  = 7;
	static final int ASDEPAMTCO_COL	= 8;
	static final int ASVALUECO_COL 	= 9;
	static final int ASDEPMNTH_COL 	= 10;
	static final int ASGLACT1_COL 	= 11;
	static final int ASGLACT2_COL 	= 12;
	static final int ASGLACT3_COL 	= 13;
	static final int ASGLACT4_COL 	= 14;
	static final int ASGLACT5_COL 	= 15;
	static final int ASDEPACT1_COL	= 16;
	static final int ASDEPACT2_COL 	= 17;
	static final int ASDEPACT3_COL 	= 18;
	static final int ASDEPACT4_COL 	= 19;
	static final int ASDEPACT5_COL 	= 20;
	static final int ASEXPACT1_COL 	= 21;
	static final int ASEXPACT2_COL 	= 22;
	static final int ASEXPACT3_COL 	= 23;
	static final int ASEXPACT4_COL 	= 24;
	static final int ASEXPACT5_COL 	= 25;
	static final int ASCCTYPE_COL 	= 26;
	static final int ASCCID1_COL 	= 27;
	static final int ASCCID2_COL 	= 28;
	static final int ASCCID3_COL 	= 29;
	static final int ASCCID4_COL 	= 30;
	static final int ASGLNAME_COL 	= 31;
	static final int ASDEPNAME_COL 	= 32;
	static final int ASEXPNAME_COL 	= 33;
	static final int ASCCNAME1_COL	= 34;
	static final int ASCCNAME2_COL	= 35;
	static final int ASCCNAME3_COL	= 36;
	static final int ASCCNAME4_COL	= 37;

	private static final int topMargin = 60; //180;  
	private static final int bottomMargin = 36;  
	private static final int leftMargin = 30;
	private static final int rightMargin = 30;
	
	private ResourceBundle rb;
	
	Document report = new Document(PageSize.LETTER.rotate(), leftMargin, rightMargin, topMargin, bottomMargin);					
	Rectangle page = report.getPageSize();
	PdfWriter myPdfWriter = null;   
	
	// 1" = 72 points
	
	static final int COL_HEAD = 12;

	static final float[] colWidthLineHeader = {25f, 15f, 10f, 10f, 7f, 7f, 10f, 10f, 10f, 10f, 10f, 10f};

	static final String[] lineHeader = new String[12];

	private RowSet detailRS;
	private String reportDate;	

	public ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

	// 1" = 72 points
	final float TABLE_WIDTH = report.getPageSize().getWidth() - report.leftMargin() - report.rightMargin();	
	
	//globle page number
	int pageNumber = 1;
	
	java.math.BigDecimal price, depRate, depAcum, value;
	
	String preGLAcc ="";
	String glAcc ="";
	String glExpAcc ="";
	String glDepAcc ="";
	String ccType ="";
	String cc1 ="";
	String cc2 ="";
	String cc3 ="";
	String cc4 ="";

	String glAccName ="";
	String glExpName ="";
	String glDepName ="";
	String ccName1	 ="";
	String ccName2	 ="";
	String ccName3	 ="";
	String ccName4	 ="";
	
	String flag ="";
	String tag ="";
	String description 	="";
	String acqDate ="";
	String lifetime 	="";
	String depMonths	="";
	
	PdfPTable headerTable =	null;// new PdfPTable(COL_HEAD);
	PdfPTable detailHeaderTable = null; //new PdfPTable(lineHeader.length);
	PdfPTable detailLineTable = null; //new PdfPTable(lineHeader.length);

	public DepreciationReportPDF() {
		super();
		//this.rb = rb;
	}

	public void generate() 
	{
		try {
			
			lineHeader[0]	= rb.getString("P12100026");
			lineHeader[1]	= rb.getString("P12100027");
			lineHeader[2]	= rb.getString("P12100028");
			lineHeader[3]	= rb.getString("P12100029");
			lineHeader[4]	= rb.getString("P12100030");
			lineHeader[5]	= rb.getString("P12100031");
			lineHeader[6]	= rb.getString("P12100032");
			lineHeader[7]	= rb.getString("P12100033");
			lineHeader[8]	= rb.getString("P12100034");
			lineHeader[9]	= rb.getString("P12100035");
			lineHeader[10]	= rb.getString("P12100036");
			lineHeader[11]	= rb.getString("P12100037");
			
			detailRS.beforeFirst();

			myPdfWriter = PdfWriter.getInstance(report, pdfStream);   
			myPdfWriter.setViewerPreferences(PdfWriter.FitWindow);
			myPdfWriter.setEncryption(null,null,PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
			report.addAuthor("Ximple by Ximple Corporation.");
			report.addTitle(rb.getString("P12100038"));

			report.open();	
			
			PdfContentByte cb = myPdfWriter.getDirectContent();
			cb.setLineWidth(ConstantValue.BORDER_WIDTH);

			headerTable = buildHeaderTable();

			//column heading
			detailHeaderTable = buildDetailHeaderTable();

			detailLineTable = new PdfPTable(lineHeader.length);
			detailLineTable.setTotalWidth(TABLE_WIDTH);
			detailLineTable.setWidths(colWidthLineHeader);

			float detailTotalHeight_summary = page.getHeight() - report.topMargin() -	report.bottomMargin() -	detailHeaderTable.getTotalHeight();		

			while (detailRS.next())
			{	
				if(detailLineTable.getTotalHeight() > detailTotalHeight_summary )
				{
					detailRS.previous();
					detailLineTable.deleteLastRow();
					
//					cb.moveTo(report.leftMargin(), page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight());
//					cb.lineTo(report.leftMargin(), page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight() - detailLineTable.getTotalHeight() );
//					cb.stroke();
									
					headerTable.writeSelectedRows(0, -1, report.leftMargin(), page.getHeight() - report.topMargin() + headerTable.getTotalHeight()+ 10f, myPdfWriter.getDirectContent());			
					detailHeaderTable.writeSelectedRows(0, -1, report.leftMargin(), page.getHeight() - report.topMargin(), myPdfWriter.getDirectContent());
					detailLineTable.writeSelectedRows(0, -1, report.leftMargin(), page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight(), myPdfWriter.getDirectContent());

					cb.moveTo(report.leftMargin(), page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight() - detailLineTable.getTotalHeight());
					cb.lineTo(report.leftMargin() + TABLE_WIDTH, page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight() - detailLineTable.getTotalHeight());
					cb.stroke();
										
					//force page break										
					report.newPage();
					pageNumber++;
					
					//rebuild header because of page number change 
					headerTable = buildHeaderTable();
					detailLineTable = new PdfPTable(lineHeader.length);
					detailLineTable.setTotalWidth(TABLE_WIDTH);
					detailLineTable.setWidths(colWidthLineHeader);
				}

				getRowData();	

				if (!glAcc.equals(preGLAcc)){
					addGLNameLine(glAccName);
					preGLAcc=glAcc;
				}

				String headOrDetail = flag.substring(0,1);
				String level = flag.substring(1,2);

				if (headOrDetail.equals("H")){
					addHeaderLine(level);
				}
				if (headOrDetail.equals("L")){
					addDetailLine();
				}
				if (headOrDetail.equals("T")){
					addTotalLine(level);
				}
			}

//			cb.moveTo(report.leftMargin(), page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight());
//			cb.lineTo(report.leftMargin(), page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight() - detailLineTable.getTotalHeight() );
//			cb.stroke();
							
			headerTable.writeSelectedRows(0, -1, report.leftMargin(), page.getHeight() - report.topMargin() + headerTable.getTotalHeight()+ 10f, myPdfWriter.getDirectContent());			
			detailHeaderTable.writeSelectedRows(0, -1, report.leftMargin(), page.getHeight() - report.topMargin(), myPdfWriter.getDirectContent());
			detailLineTable.writeSelectedRows(0, -1, report.leftMargin(), page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight(), myPdfWriter.getDirectContent());

			cb.moveTo(report.leftMargin(), page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight() - detailLineTable.getTotalHeight());
			cb.lineTo(report.leftMargin() + TABLE_WIDTH, page.getHeight() - report.topMargin() - detailHeaderTable.getTotalHeight() - detailLineTable.getTotalHeight());
			cb.stroke();
		
			report.close();
						
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} catch(Exception exp) {
			System.err.println(exp.getMessage());
		}
	}

	public void setData(RowSet detRS, ResourceBundle rb, String rptDate) {
		detailRS = detRS;
		reportDate = rptDate;
		this.rb = rb;
	}

	private void getRowData() {
		try {
			flag = detailRS.getString(ASFLAG_COL);

			tag = detailRS.getString(ASTAG_COL);
			description = detailRS.getString(ASNAME_COL);
			acqDate = Format.displayDate(detailRS.getString(ASACQDATE_COL));
			lifetime 	= detailRS.getString(ASLIFETIME_COL);
			depMonths	= detailRS.getString(ASDEPMNTH_COL);
							
			price 	= detailRS.getBigDecimal(ASPRICE_COL);
			depRate = detailRS.getBigDecimal(ASDEPRATE_COL);
			depAcum = detailRS.getBigDecimal(ASDEPAMTCO_COL);
			value 	= detailRS.getBigDecimal(ASVALUECO_COL);
	
			//TODO for EPN special case 
			/*
			if(depMonths.equals("0") && depAcum.doubleValue()==0d && value.doubleValue()==0d)
			{
				depMonths = lifetime;
				depAcum = depRate.multiply(new java.math.BigDecimal(depMonths));
				value 	= price.subtract(depAcum);
			}
			*/
			glAcc	 = 	detailRS.getString(ASGLACT1_COL) + detailRS.getString(ASGLACT2_COL) + detailRS.getString(ASGLACT3_COL) +
						detailRS.getString(ASGLACT4_COL) + detailRS.getString(ASGLACT5_COL);
			glExpAcc = 	detailRS.getString(ASEXPACT1_COL) + detailRS.getString(ASEXPACT2_COL) + detailRS.getString(ASEXPACT3_COL) +
						detailRS.getString(ASEXPACT4_COL) + detailRS.getString(ASEXPACT5_COL);
			glDepAcc = 	detailRS.getString(ASDEPACT1_COL) + detailRS.getString(ASDEPACT2_COL) + detailRS.getString(ASDEPACT3_COL) +
						detailRS.getString(ASDEPACT4_COL) + detailRS.getString(ASDEPACT5_COL);
			
			ccType = detailRS.getString(ASCCTYPE_COL);
			cc1 = detailRS.getString(ASCCID1_COL);
			cc2 = detailRS.getString(ASCCID2_COL);
			cc3 = detailRS.getString(ASCCID3_COL);
			cc4 = detailRS.getString(ASCCID4_COL);
	
			glAccName = detailRS.getString(ASGLNAME_COL);
			glExpName = detailRS.getString(ASEXPNAME_COL);
			glDepName = detailRS.getString(ASDEPNAME_COL);
			ccName1	 = detailRS.getString(ASCCNAME1_COL);
			ccName2	 = detailRS.getString(ASCCNAME2_COL);
			ccName3	 = detailRS.getString(ASCCNAME3_COL);
			ccName4	 = detailRS.getString(ASCCNAME4_COL);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void addGLNameLine(String glAccName) throws DocumentException, Exception
	{
		PdfPCell cell = null;	
		cell = new PdfPCell(new Phrase("\n" + rb.getString("P12100039") + ": " + glAccName + "\n", ConstantValue.HELV_10_BOLD_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		cell.setColspan(COL_HEAD-1);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase("", ConstantValue.HELV_10_BOLD_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.RIGHT);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
	}
	
	private void addHeaderLine(String level) throws DocumentException, Exception
	{
		PdfPCell cell = null;	
		if (level.equals("1")){
			cell = new PdfPCell(new Phrase(ccName1, ConstantValue.HELV_7_BOLD_FONT));
		}
		if (level.equals("2")){
			cell = new PdfPCell(new Phrase("      " + ccName2, ConstantValue.HELV_7_BOLD_FONT));
		}
		if (level.equals("3")){
			cell = new PdfPCell(new Phrase("            "+ ccName3, ConstantValue.HELV_7_BOLD_FONT));
		}
		if (level.equals("4")){
			cell = new PdfPCell(new Phrase("                  " + ccName4, ConstantValue.HELV_7_BOLD_FONT));
		}
		
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase("", ConstantValue.HELV_7_BOLD_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.RIGHT);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		cell.setColspan(COL_HEAD-1);
		detailLineTable.addCell(cell);

	
	}
	

	private void addTotalLine(String level) throws DocumentException, Exception
	{
		PdfPCell cell = null;	

		if (level.equals("1")){
			cell = new PdfPCell(new Phrase(ccName4 + "  Total: ", ConstantValue.HELV_7_BOLD_FONT));
		}
		if (level.equals("2")){
			cell = new PdfPCell(new Phrase(ccName3 + "  Total: ", ConstantValue.HELV_7_BOLD_FONT));
		}
		if (level.equals("3")){
			cell = new PdfPCell(new Phrase(ccName2 + "  Total: ", ConstantValue.HELV_7_BOLD_FONT));
		}
		if (level.equals("4")){
			cell = new PdfPCell(new Phrase(ccName1 + "  Total: ", ConstantValue.HELV_7_BOLD_FONT));
		}
		if (level.equals("5")){
			cell = new PdfPCell(new Phrase("Total: " , ConstantValue.HELV_8_BOLD_FONT));
		}

		cell.setBorder(Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
//		cell.setBackgroundColor(ConstantValue.BG_COLOR);
		cell.setColspan(6);
		detailLineTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(Format.displayCurrency(price), ConstantValue.HELV_7_BOLD_FONT));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
//		cell.setBackgroundColor(ConstantValue.BG_COLOR);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(Format.displayCurrency(depRate), ConstantValue.HELV_7_BOLD_FONT));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
//		cell.setBackgroundColor(ConstantValue.BG_COLOR);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(Format.displayCurrency(depAcum), ConstantValue.HELV_7_BOLD_FONT));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
//		cell.setBackgroundColor(ConstantValue.BG_COLOR);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(Format.displayCurrency(value), ConstantValue.HELV_7_BOLD_FONT));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
//		cell.setBackgroundColor(ConstantValue.BG_COLOR);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase("", ConstantValue.HELV_7_BOLD_FONT));
		cell.setBorder(Rectangle.RIGHT);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		cell.setColspan(2);
//		cell.setBackgroundColor(ConstantValue.BG_COLOR);
		detailLineTable.addCell(cell);	
	}	


	private PdfPTable buildHeaderTable() throws DocumentException, Exception
	{
		PdfPTable table = new PdfPTable(COL_HEAD);
		table.setTotalWidth(TABLE_WIDTH);
//		table.setWidths(colWidthHeader);

		PdfPCell cell = null;	

		// add: page number
		cell = new PdfPCell(new Phrase(rb.getString("P12100040") + " " + reportDate, ConstantValue.HELV_14_BOLD_FONT));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(COL_HEAD);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(rb.getString("P12100041") + " " + pageNumber, ConstantValue.HELV_8_NORM_FONT));
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

	private void addDetailLine() throws DocumentException, SQLException	
	{
		PdfPCell cell = null;	

		cell = new PdfPCell(new Phrase("", ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(description, ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(tag, ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(acqDate, ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(lifetime, ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase(depMonths, ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(Format.displayCurrency(price), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(Format.displayCurrency(depRate), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(Format.displayCurrency(depAcum), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(Format.displayCurrency(value), ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(glExpName, ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);

		cell = new PdfPCell(new Phrase(glDepName, ConstantValue.HELV_8_NORM_FONT));
		cell.setBorderWidth(ConstantValue.BORDER_WIDTH);
		cell.setBorder(Rectangle.RIGHT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingRight(ConstantValue.PADDING_RIGHT5);
		detailLineTable.addCell(cell);
	}
}
