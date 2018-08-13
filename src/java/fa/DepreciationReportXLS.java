package com.cap.erp.fa;

import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.RowSet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.cap.erp.report.ExcelReportHelper;
import com.cap.util.Format;
import com.cap.wdf.command.CachedRowSet;

public class DepreciationReportXLS 
{
	static final int ANLY_HEADER_ROWS	 = 6;
	static final int ANLY_REPORT_COLUMNS = 16;
	static final int SAFETY_ROW_NUM      = 5;

 	static final float BIG_CELL_HEIGHT = 30f;

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

 	private RowSet detailRS;
	private String reportDate;
	HttpServletRequest request;
	HSSFWorkbook wb = null;	
	
	public void setData(RowSet detRS, HttpServletRequest req, String rptDate) {
		request		= req;
		detailRS 	= detRS;
		reportDate  = rptDate;
	}
	
	public DepreciationReportXLS() {
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
		String DR_TITLE = rb.getString("P12100038");
		HSSFSheet sheet = wb.createSheet(DR_TITLE);
	
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
		headerCells[0][9].setCellValue( "" );
		headerCells[0][10].setCellValue( "" );
		headerCells[0][11].setCellValue( "" );
		headerCells[0][12].setCellValue( "" );
		headerCells[0][13].setCellValue( "" );
		headerCells[0][14].setCellValue( "" );
	
		headerCells[0][15].setCellStyle(xHelper.date);
		headerCells[0][15].setCellValue(Format.getSysDate());
	
		//row2 - report title and Time
		headerCells[1][0].setCellStyle(xHelper.boldCenterTextRed);
		headerCells[1][0].setCellValue(DR_TITLE);
	
		headerCells[1][1].setCellValue( "" );
		headerCells[1][2].setCellValue( "" );
		headerCells[1][3].setCellValue( "" );
		headerCells[1][4].setCellValue( "" );
		headerCells[1][5].setCellValue( "" );
		headerCells[1][6].setCellValue( "" );
		headerCells[1][7].setCellValue( "" );
		headerCells[1][8].setCellValue( "" );
		headerCells[1][9].setCellValue( "" );
		headerCells[1][10].setCellValue( "" );
		headerCells[1][11].setCellValue( "" );
		headerCells[1][12].setCellValue( "" );
		headerCells[1][13].setCellValue( "" );
		headerCells[1][14].setCellValue( "" );
	
		headerCells[1][15].setCellStyle(xHelper.time);
		headerCells[1][15].setCellValue(Format.getSysTime());
	
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
		headerCells[2][9].setCellValue( "" );
		headerCells[2][10].setCellValue( "" );
		headerCells[2][11].setCellValue( "" );
		headerCells[2][12].setCellValue( "" );
		headerCells[2][13].setCellValue( "" );
		headerCells[2][14].setCellValue( "" );
		
		headerCells[2][15].setCellStyle(xHelper.alignRightText);
		headerCells[2][15].setCellValue( puser.getFullName() );
	
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 14));
		sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 14));
		sheet.addMergedRegion(new Region(2, (short) 0, 2, (short) 14));
	
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
		headerCells[3][10].setCellValue( "" );
		headerCells[3][11].setCellValue( "" );
		headerCells[3][12].setCellValue( "" );
		headerCells[3][13].setCellValue( "" );
		headerCells[3][14].setCellValue( "" );
		headerCells[3][15].setCellValue( "" );
	
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
		headerCells[4][10].setCellValue( "" );
		headerCells[4][11].setCellValue( "" );
		headerCells[4][12].setCellValue( "" );
		headerCells[4][13].setCellValue( "" );
		headerCells[4][14].setCellValue( "" );
		headerCells[4][15].setCellValue( "" );
	
		//row6 - table title
		headerRows[5].setHeightInPoints(BIG_CELL_HEIGHT);
		headerCells[5][0].setCellStyle(xHelper.boldCenterText);
		headerCells[5][0].setCellValue( rb.getString("P01530003") );

		headerCells[5][1].setCellStyle(xHelper.boldCenterText);
		headerCells[5][1].setCellValue( rb.getString("P12100026") + " 1" );
	
		headerCells[5][2].setCellStyle(xHelper.boldCenterText);
		headerCells[5][2].setCellValue( rb.getString("P12100026") + " 2");

		headerCells[5][3].setCellStyle(xHelper.boldCenterText);
		headerCells[5][3].setCellValue( rb.getString("P12100026") + " 3");

		headerCells[5][4].setCellStyle(xHelper.boldCenterText);
		headerCells[5][4].setCellValue( rb.getString("P12100026") + " 4");

		headerCells[5][5].setCellStyle(xHelper.boldCenterText);
		headerCells[5][5].setCellValue( rb.getString("P12100027")  );
		
		headerCells[5][6].setCellStyle(xHelper.boldCenterText);
		headerCells[5][6].setCellValue( rb.getString("P12100028") );
	
		headerCells[5][7].setCellStyle(xHelper.boldCenterText);
		headerCells[5][7].setCellValue( rb.getString("P12100029") );
	
		headerCells[5][8].setCellStyle(xHelper.boldCenterText);
		headerCells[5][8].setCellValue( rb.getString("P12100030") );
		
		headerCells[5][9].setCellStyle(xHelper.boldCenterText);
		headerCells[5][9].setCellValue( rb.getString("P12100031") );
	
		headerCells[5][10].setCellStyle(xHelper.boldCenterText);
		headerCells[5][10].setCellValue( rb.getString("P12100032") );
		
		headerCells[5][11].setCellStyle(xHelper.boldCenterText);
		headerCells[5][11].setCellValue( rb.getString("P12100033") );
		
		headerCells[5][12].setCellStyle(xHelper.boldCenterText);
		headerCells[5][12].setCellValue( rb.getString("P12100034") );
		
		headerCells[5][13].setCellStyle(xHelper.boldCenterText);
		headerCells[5][13].setCellValue( rb.getString("P12100035") );
		
		headerCells[5][14].setCellStyle(xHelper.boldCenterText);
		headerCells[5][14].setCellValue( rb.getString("P12100036") );

		headerCells[5][15].setCellStyle(xHelper.boldCenterText);
		headerCells[5][15].setCellValue( rb.getString("P12100037") );

		//set column widths
		sheet.setColumnWidth( 0, (short) ( ( 400 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 1, (short) ( ( 300 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 2, (short) ( ( 300 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 3, (short) ( ( 300 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 4, (short) ( ( 300 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 5, (short) ( ( 350 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 6, (short) ( ( 175 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 7, (short) ( ( 200 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 8, (short) ( ( 175 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 9, (short) ( ( 175 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 10, (short) ( ( 250 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 11, (short) ( ( 250 ) / ( (double) 1 / 20 ) ) );
		sheet.setColumnWidth( 12, (short) ( ( 250 ) / ( (double) 1 / 20 ) ) );	
		sheet.setColumnWidth( 13, (short) ( ( 250 ) / ( (double) 1 / 20 ) ) );	
		sheet.setColumnWidth( 14, (short) ( ( 300 ) / ( (double) 1 / 20 ) ) );	
		sheet.setColumnWidth( 15, (short) ( ( 300 ) / ( (double) 1 / 20 ) ) );	
		
		int numOfRecs = ((CachedRowSet)detailRS).getRowSetSize();
		
		HSSFRow[] dataRows = new HSSFRow[numOfRecs + SAFETY_ROW_NUM];
		HSSFCell[][] dataCells = new HSSFCell[numOfRecs + SAFETY_ROW_NUM][ANLY_REPORT_COLUMNS];
		int excelRecord = 0;
		String hOrD;
		
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
	
			String lifetime 	="";
			String depMonths	="";
			java.math.BigDecimal price, depRate, depAcum, value;

			detailRS.beforeFirst();
			while(detailRS.next())
			{
			   	hOrD   = detailRS.getString(ASFLAG_COL).substring(0,1);
			   	
				if (hOrD.equals("L"))
				{	
					
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
				   	dataCells[excelRecord][0].setCellValue(detailRS.getString(ASGLNAME_COL));
				   	dataCells[excelRecord][0].setCellStyle(xHelper.alignLeftText);
				   	dataCells[excelRecord][1].setCellValue(detailRS.getString(ASCCNAME1_COL));
				   	dataCells[excelRecord][1].setCellStyle(xHelper.alignLeftText);
				   	dataCells[excelRecord][2].setCellValue(detailRS.getString(ASCCNAME2_COL));
				   	dataCells[excelRecord][2].setCellStyle(xHelper.alignLeftText);
				   	dataCells[excelRecord][3].setCellValue(detailRS.getString(ASCCNAME3_COL));
				   	dataCells[excelRecord][3].setCellStyle(xHelper.alignLeftText);
				   	dataCells[excelRecord][4].setCellValue(detailRS.getString(ASCCNAME4_COL));
				   	dataCells[excelRecord][4].setCellStyle(xHelper.alignLeftText);
				   	dataCells[excelRecord][5].setCellValue(detailRS.getString(ASNAME_COL));
				   	dataCells[excelRecord][5].setCellStyle(xHelper.alignLeftText);
				   	dataCells[excelRecord][6].setCellValue(detailRS.getString(ASTAG_COL));
				   	dataCells[excelRecord][6].setCellStyle(xHelper.alignCenterText);
				   	dataCells[excelRecord][7].setCellValue(Format.displayDate(detailRS.getString(ASACQDATE_COL)));
				   	dataCells[excelRecord][7].setCellStyle(xHelper.alignCenterText);
				   	dataCells[excelRecord][8].setCellValue(lifetime);
				   	dataCells[excelRecord][8].setCellStyle(xHelper.alignCenterText);
				   	dataCells[excelRecord][9].setCellValue(depMonths);
				   	dataCells[excelRecord][9].setCellStyle(xHelper.alignCenterText);
				   	dataCells[excelRecord][10].setCellValue(Format.displayCurrency(detailRS.getBigDecimal(ASPRICE_COL)));
				   	dataCells[excelRecord][10].setCellStyle(xHelper.alignRightCurrency);
				   	dataCells[excelRecord][11].setCellValue(Format.displayCurrency(detailRS.getBigDecimal(ASDEPRATE_COL)));
				   	dataCells[excelRecord][11].setCellStyle(xHelper.alignRightCurrency);
				   	dataCells[excelRecord][12].setCellValue(Format.displayCurrency(depAcum));
				   	dataCells[excelRecord][12].setCellStyle(xHelper.alignRightCurrency);
				   	dataCells[excelRecord][13].setCellValue(Format.displayCurrency(value));
				   	dataCells[excelRecord][13].setCellStyle(xHelper.alignRightCurrency);
				   	dataCells[excelRecord][14].setCellValue(detailRS.getString(ASEXPNAME_COL));
				   	dataCells[excelRecord][14].setCellStyle(xHelper.alignLeftText);
				   	dataCells[excelRecord][15].setCellValue(detailRS.getString(ASDEPNAME_COL));
				   	dataCells[excelRecord][15].setCellStyle(xHelper.alignLeftText);
	
					excelRecord++;
				}
			}//End of for
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} catch(Exception exp) {
			System.err.println(exp.getMessage());
		} 
  }  
}
