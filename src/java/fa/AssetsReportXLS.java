package com.cap.erp.fa;

//import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ResourceBundle;
//import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.RowSet;

import com.cap.erp.report.ExcelReportHelper;
import com.cap.util.*;
import com.cap.wdf.command.CachedRowSet;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;

public class AssetsReportXLS
{
	static final String FA_TITLE = "Fixed Asset Report";

	static final int ANLY_HEADER_ROWS		= 6;
	static final int ANLY_REPORT_COLUMNS	= 10;
	static final int SAFETY_ROW_NUM = 5;

 	static final float BIG_CELL_HEIGHT = 30f;

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
	
	private RowSet detailRS;
	
	HttpServletRequest request;
	HSSFWorkbook wb = null;	
	
	public void setData(RowSet detRS, HttpServletRequest req) {
		request		= req;
		detailRS 	= detRS;
	}
	
	public AssetsReportXLS() {
	}

	public void generate() throws Exception
  	{

		HttpSession session = request.getSession(false);
		com.cap.portal.PortalUser puser = null; //if session time out or not valid, redirect to other pages
		puser = (com.cap.portal.PortalUser) session.getAttribute("portal_PortalUser");
		String compName 	= puser.getCompanyName();
		ResourceBundle rb 	= puser.getResourceBundle();
	
		//create the excel document
		ExcelReportHelper xHelper = new ExcelReportHelper();
		wb = new HSSFWorkbook();
		xHelper.initializeCellStyles( wb );
	
		//create the report sheet
		HSSFSheet sheet = wb.createSheet(FA_TITLE);
	
		//make rows for the header for sheet
		HSSFRow[] headerRows = new HSSFRow[ANLY_HEADER_ROWS];
	
		for(int i=0; i < ANLY_HEADER_ROWS; i++)
		{
			headerRows[i] = sheet.createRow( i );
		}
	
		//make cells for the header an initialize them
		HSSFCell[][] headerCells = new HSSFCell[ANLY_HEADER_ROWS][ANLY_REPORT_COLUMNS];
		for(int i=0; i < headerRows.length; i++)
		{
			for(int j=0; j < ANLY_REPORT_COLUMNS; j++)
			{
				headerCells[i][j] = headerRows[i].createCell( j );
				headerCells[i][j].setCellType( HSSFCell.CELL_TYPE_STRING );
			}
		}
	
		//row1 - companyName and Date
		headerCells[0][0].setCellStyle(xHelper.boldCenterText);
		headerCells[0][0].setCellValue(compName);
		
		headerCells[0][1].setCellValue( "" );
		headerCells[0][2].setCellValue( "" );
		headerCells[0][3].setCellValue( "" );
		headerCells[0][4].setCellValue( "" );
		headerCells[0][5].setCellValue( "" );
		headerCells[0][6].setCellValue( "" );
		headerCells[0][7].setCellValue( "" );
		headerCells[0][8].setCellValue( "" );
	
		headerCells[0][9].setCellStyle(xHelper.date);
		headerCells[0][9].setCellValue(Format.getSysDate());
	
		//row2 - report title and Time
		headerCells[1][0].setCellStyle(xHelper.boldCenterTextRed);
		headerCells[1][0].setCellValue(FA_TITLE);
	
		headerCells[1][1].setCellValue( "" );
		headerCells[1][2].setCellValue( "" );
		headerCells[1][3].setCellValue( "" );
		headerCells[1][4].setCellValue( "" );
		headerCells[1][5].setCellValue( "" );
		headerCells[1][6].setCellValue( "" );
		headerCells[1][7].setCellValue( "" );
		headerCells[1][8].setCellValue( "" );
	
		headerCells[1][9].setCellStyle(xHelper.time);
		headerCells[1][9].setCellValue(Format.getSysTime());
	
		//row3 - date range and user name
		headerCells[2][0].setCellValue( "" );
		headerCells[2][1].setCellValue( "" );
		headerCells[2][2].setCellValue( "" );
		headerCells[2][3].setCellValue( "" );
		headerCells[2][4].setCellValue( "" );
		headerCells[2][5].setCellValue( "" );
		headerCells[2][6].setCellValue( "" );
		headerCells[2][7].setCellValue( "" );
		headerCells[2][8].setCellValue( "" );
		
		headerCells[2][9].setCellStyle(xHelper.alignRightText);
		headerCells[2][9].setCellValue( puser.getFullName() );
	
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 8));
		sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 8));
		sheet.addMergedRegion(new Region(2, (short) 0, 2, (short) 8));
	
		//row4 - blank
		headerCells[3][0].setCellValue( "" );
		headerCells[3][1].setCellValue( "" );
		headerCells[3][2].setCellValue( "" );
		headerCells[3][3].setCellValue( "" );
		headerCells[3][4].setCellValue( "" );
		headerCells[3][5].setCellValue( "" );
		headerCells[3][6].setCellValue( "" );
		headerCells[3][7].setCellValue( "" );
		headerCells[3][8].setCellValue( "" );
		headerCells[3][9].setCellValue( "" );
	
		//row5 - blank
		headerCells[4][0].setCellValue( "" );
		headerCells[4][1].setCellValue( "" );
		headerCells[4][2].setCellValue( "" );
		headerCells[4][3].setCellValue( "" );
		headerCells[4][4].setCellValue( "" );
		headerCells[4][5].setCellValue( "" );
		headerCells[4][6].setCellValue( "" );
		headerCells[4][7].setCellValue( "" );
		headerCells[4][8].setCellValue( "" );
		headerCells[4][9].setCellValue( "" );
	
		//row6 - table title
		headerRows[5].setHeightInPoints(BIG_CELL_HEIGHT);
		headerCells[5][0].setCellStyle(xHelper.boldCenterText);
		headerCells[5][0].setCellValue( rb.getString("P12130002") );
	
		headerCells[5][1].setCellStyle(xHelper.boldCenterText);
		headerCells[5][1].setCellValue( rb.getString("P12130004")  );
		
		headerCells[5][2].setCellStyle(xHelper.boldCenterText);
		headerCells[5][2].setCellValue( rb.getString("P12130010") );
	
		headerCells[5][3].setCellStyle(xHelper.boldCenterText);
		headerCells[5][3].setCellValue( rb.getString("P12130016") );
	
		headerCells[5][4].setCellStyle(xHelper.boldCenterText);
		headerCells[5][4].setCellValue( rb.getString("P12130027") );
		
		headerCells[5][5].setCellStyle(xHelper.boldCenterText);
		headerCells[5][5].setCellValue( rb.getString("P12130013") );
	
		headerCells[5][6].setCellStyle(xHelper.boldCenterText);
		headerCells[5][6].setCellValue( rb.getString("P12130028") );
		
		headerCells[5][7].setCellStyle(xHelper.boldCenterText);
		headerCells[5][7].setCellValue( rb.getString("P12130029") );
		
		headerCells[5][8].setCellStyle(xHelper.boldCenterText);
		headerCells[5][8].setCellValue( rb.getString("P12130017") );
		
		headerCells[5][9].setCellStyle(xHelper.boldCenterText);
		headerCells[5][9].setCellValue( rb.getString("P12130033") );
		
		//set column widths
		sheet.setColumnWidth( 0, (short) ( ( 350 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 1, (short) ( ( 300 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 2, (short) ( ( 120 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 3, (short) ( ( 175 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 4, (short) ( ( 300 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 5, (short) ( ( 300 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 6, (short) ( ( 175 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 7, (short) ( ( 300 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 8, (short) ( ( 300 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 9, (short) ( ( 175 ) / ( (double) 1 / 20 ) ) );	
		
		String price, depAcum, value, salvageAmt;
		/*
		String preGLAcc ="";
		String glAcc ="";
		String glExpAcc ="";
		String glDepAcc ="";
	
		String glAccName ="";
		String glExpName ="";
		String glDepName ="";
		String glLiabName="";
		*/
		String tag			="";
		String description 	="";
		String acqDate		="";
		String lifetime 	="";
		String depMonths	="";
		String statusDescr	="";
	
		/*
		BigDecimal totalAmount1 = ConstantValue.ZERO;
		BigDecimal totalAmount2 = ConstantValue.ZERO;
		BigDecimal totalAmount3 = ConstantValue.ZERO;
		BigDecimal totalAmount4 = ConstantValue.ZERO;
		BigDecimal totalAmount5 = ConstantValue.ZERO;
		BigDecimal totalAmount6 = ConstantValue.ZERO;
		*/
		/* Report Content */
		/*
		HSSFRow  dataRow = null;
		HSSFCell dataCell1 = null;
		HSSFCell dataCell2 = null;
		HSSFCell dataCell3 = null;
		HSSFCell dataCell4 = null;
		HSSFCell dataCell5 = null;
		HSSFCell dataCell6 = null;
		HSSFCell dataCell7 = null;
		HSSFCell dataCell8 = null;
		HSSFCell dataCell9 = null;
		HSSFCell dataCell10 = null;
		*/
		int numOfRecs = ((CachedRowSet)detailRS).getRowSetSize();
		
		HSSFRow[] dataRows = new HSSFRow[numOfRecs + SAFETY_ROW_NUM];
		HSSFCell[][] dataCells = new HSSFCell[numOfRecs + SAFETY_ROW_NUM][ANLY_REPORT_COLUMNS];
		int excelRecord = 0;
	
		try
		{
			//initialize rows and cells
			for(int z=0; z < numOfRecs + SAFETY_ROW_NUM; z++)
			{
				dataRows[z] = sheet.createRow( z + ANLY_HEADER_ROWS );  //to increment from header
			}
			for(int i=0; i<numOfRecs + SAFETY_ROW_NUM; i++)
			{
				for(int j=0; j< ANLY_REPORT_COLUMNS; j++)
				{
					dataCells[i][j] = dataRows[i].createCell( j );
					dataCells[i][j].setCellType( HSSFCell.CELL_TYPE_NUMERIC );
					dataCells[i][j].setCellValue( "" );
				}
			}
	
			detailRS.beforeFirst();
			while(detailRS.next())
			{
				tag 		= detailRS.getString(TAG_COL);
				description = detailRS.getString(ASNAME_COL);
				acqDate 	= Format.displayDate(detailRS.getString(ACQUDATE_COL));
				lifetime 	= detailRS.getString(LIFETIME_COL);
				price		= Format.displayCurrency(detailRS.getBigDecimal(PRICE_COL));
				depAcum		= Format.displayCurrency(detailRS.getBigDecimal(TOTDPAMT_COL));
				value		= Format.displayCurrency(detailRS.getBigDecimal(PRICE_COL).subtract(detailRS.getBigDecimal(TOTDPAMT_COL)));
				salvageAmt	= Format.displayCurrency(detailRS.getBigDecimal(SALVAGE_COL));
				depMonths    = detailRS.getString(TOTMNTH_COL);
				
				/*
				glAcc	 = 	detailRS.getString(ASGLACT1_COL) + detailRS.getString(ASGLACT2_COL) + detailRS.getString(ASGLACT3_COL) +
							detailRS.getString(ASGLACT4_COL) + detailRS.getString(ASGLACT5_COL);
				glExpAcc = 	detailRS.getString(ASEXPACT1_COL) + detailRS.getString(ASEXPACT2_COL) + detailRS.getString(ASEXPACT3_COL) +
							detailRS.getString(ASEXPACT4_COL) + detailRS.getString(ASEXPACT5_COL);
				glDepAcc = 	detailRS.getString(ASDEPACT1_COL) + detailRS.getString(ASDEPACT2_COL) + detailRS.getString(ASDEPACT3_COL) +
							detailRS.getString(ASDEPACT4_COL) + detailRS.getString(ASDEPACT5_COL);
		
				glAccName 	= detailRS.getString(GLACC_COL);
				glLiabName	= detailRS.getString(GLLBLACC_COL);
				glExpName 	= detailRS.getString(GLEXPACC_COL);
				glDepName 	= detailRS.getString(GLDEPACC_COL);
				*/
				statusDescr = detailRS.getString(STDESC_COL);

			   	dataCells[excelRecord][0].setCellValue(description);
			   	dataCells[excelRecord][0].setCellStyle(xHelper.alignLeftText);
			   	dataCells[excelRecord][1].setCellValue(tag);
			   	dataCells[excelRecord][1].setCellStyle(xHelper.alignLeftText);
			   	dataCells[excelRecord][2].setCellValue(acqDate);
			   	dataCells[excelRecord][2].setCellStyle(xHelper.alignLeftText);
			   	dataCells[excelRecord][3].setCellValue(lifetime);
			   	dataCells[excelRecord][3].setCellStyle(xHelper.alignRightCurrency);
			   	dataCells[excelRecord][4].setCellValue(depMonths);
			   	dataCells[excelRecord][4].setCellStyle(xHelper.alignRightCurrency);
			   	dataCells[excelRecord][5].setCellValue(price);
			   	dataCells[excelRecord][5].setCellStyle(xHelper.alignRightCurrency);
			   	dataCells[excelRecord][6].setCellValue(depAcum);
			   	dataCells[excelRecord][6].setCellStyle(xHelper.alignRightCurrency);
			   	dataCells[excelRecord][7].setCellValue(value);
			   	dataCells[excelRecord][7].setCellStyle(xHelper.alignRightCurrency);
			   	dataCells[excelRecord][8].setCellValue(salvageAmt);
			   	dataCells[excelRecord][8].setCellStyle(xHelper.alignRightCurrency);
			   	dataCells[excelRecord][9].setCellValue(statusDescr);
			   	dataCells[excelRecord][9].setCellStyle(xHelper.alignLeftText);

				excelRecord++;
				
			}//End of for
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} catch(Exception exp) {
			System.err.println(exp.getMessage());
		} 
  }  
}
