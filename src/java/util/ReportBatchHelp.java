package com.cap.util;

import javax.servlet.http.HttpServletRequest;

import com.cap.erp.SPDBBean;
import com.lowagie.text.*;

public class ReportBatchHelp extends ERPReport {

	//Columns for cursor #1
	private final int WBCOMP_COL 	= 1;
	private final int WBCNAM_COL 	= 2;
	private final int WBBTCH_COL 	= 3;
	private final int WBDATE_COL 	= 4;
	private final int WBSTAT_COL 	= 5;
	private final int WBPAYM_COL 	= 6;
	private final int WBBANK_COL 	= 7;
	private final int WBACNM_COL 	= 8;
	private final int WBCOIN_COL 	= 9;
	private final int WBCCNT_COL	= 10;
	private final int WBCAMN_COL 	= 11;
	private final int WSDAMN_COL 	= 12;
	private final int WSAAMN_COL	= 13;
	private final int WSVCNT_COL	= 14;
	private final int WSVAMN_COL    = 15;
	private final int WSUAMN_COL    = 16;
	private final int WSCOMM_COL    = 17;

	//Columns fro cursor #2
	//private final int WHCOMP_COL	= 1;
	//private final int WHBTCH_COL 	= 2;
	private final int WHONUM_COL 	= 3;
	private final int WHDATE_COL 	= 4;
	private final int WHOTYP_COL 	= 5;
	private final int WHCUS_COL 	= 6;
	private final int WHNAME_COL 	= 7;
	private final int WHSTATR_COL 	= 8;
	private final int WHPTYP_COL 	= 9;
	private final int WHDOCN_COL 	= 10;
	private final int WHPDAT_COL 	= 11;
	private final int WHBANK_COL 	= 12;
	private final int WHACCN_COL 	= 13;
	private final int WHAMNT_COL 	= 14;
	private final int WHDAMNR_COL 	= 15;
	private final int WHPDAP_COL 	= 16;
	private final int WHVDRS_COL 	= 17;
	private final int WHDISC_COL 	= 18;

	//Columns fro cursor #3
	//private final int WRCOMP_COL	= 1;
	private final int WRONUM_COL 	= 2;
	//private final int WRRTYP_COL 	= 3;
	private final int WRRNUM_COL 	= 4;
	private final int WRDDAT_COL 	= 5;
	private final int WRTOTA_COL 	= 6;
	private final int WRAMNT_COL 	= 7;
	private final int WRGNDS_COL 	= 8;
	private final int WRTODU_COL 	= 9;
	private final int WRTATD_COL 	= 10;

	private final int reportHeaderCursor	= 1;
	private final int receiptHeaderCursor 	= 2;
	private final int receiptDetailCursor	= 3;

	private SPDBBean dataBean;

    /**
     * Constructor for ReportBatch
     */
    public ReportBatchHelp(Rectangle pageSize) {
        super(pageSize);
    }
    /**
     * Constructor for ReportBatch
     */
    public ReportBatchHelp(
        Rectangle pageSize,
        float marginLeft,
        float marginRight,
        float marginTop,
        float marginBottom) {
        super(pageSize, marginLeft, marginRight, marginTop, marginBottom);
    }
	public ReportBatchHelp(String fname) throws DocumentException
	{
		super(fname);
	}
	public ReportBatchHelp(HttpServletRequest request) throws DocumentException {
        super(request);
    }
	public void addReceipt() throws BadElementException,DocumentException, Exception {
	   int numOfReceipt = dataBean.RowCountResult(receiptHeaderCursor);
	   int numOfRecord  = dataBean.RowCountResult(receiptDetailCursor);
		String doubleLine = "===================================================================";
	   String receiptID, recordID;
	   for ( int i=0; i<numOfReceipt; i++ ) {
			receiptID = ((java.math.BigDecimal)dataBean.valueAtColumnRowResult(
									WHONUM_COL, i, receiptHeaderCursor)).toString();
			if ( i != 0 ) {
				addUserDefinedLine(doubleLine);
			}
			addReceiptHeader(i);
			addNewLines(2);
			
			for ( int j=0; j<numOfRecord; j++ ) {
				recordID = ((java.math.BigDecimal)dataBean.valueAtColumnRowResult(
										WRONUM_COL, j, receiptDetailCursor)).toString();
				if ( j == 0 ) {
					addTblHeader();
				}

				if ( receiptID.equals(recordID) ) {
					addReceiptDetail(j);
				}
			}
		}
	}
	public void addReceiptDetail( int detailRow ) throws BadElementException,DocumentException, Exception {

		String[] values = new String[7];

		values[0] = ((java.math.BigDecimal)dataBean.valueAtColumnRowResult(
								WRRNUM_COL, detailRow, receiptDetailCursor)).toString();
		values[1] = Format.dateBigDec_Str(
							(java.math.BigDecimal)dataBean.valueAtColumnRowResult(
											WRDDAT_COL, detailRow, receiptDetailCursor));
		values[2] = Format.displayCurrency((java.math.BigDecimal)dataBean.valueAtColumnRowResult(
											WRTOTA_COL, detailRow, receiptDetailCursor));
		values[3] = Format.displayCurrency((java.math.BigDecimal)dataBean.valueAtColumnRowResult(
											WRAMNT_COL, detailRow, receiptDetailCursor));
		values[4] = Format.displayCurrency((java.math.BigDecimal)dataBean.valueAtColumnRowResult(
											WRGNDS_COL, detailRow, receiptDetailCursor));
		values[5] = Format.displayCurrency((java.math.BigDecimal)dataBean.valueAtColumnRowResult(
											WRTODU_COL, detailRow, receiptDetailCursor));
		values[6] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(
											WRTATD_COL, detailRow, receiptDetailCursor));

		int[] valueAligns 	= {Element.ALIGN_CENTER,
 							   Element.ALIGN_CENTER,
							   Element.ALIGN_RIGHT,
							   Element.ALIGN_RIGHT,
							   Element.ALIGN_RIGHT,
							   Element.ALIGN_RIGHT,
							   Element.ALIGN_CENTER};

	   ERPDataTable table = new ERPDataTable(7);
	   float[] widths = {15f, 12f, 17f, 17f, 15f, 15f, 9f};
	   table.setWidths(widths);
	   table.printTblCells(values, valueAligns, 10f);
	   add(table);

	}
	public void addReceiptHeader  ( int headerRow )throws BadElementException, DocumentException, Exception {
		String[] topLineNam = {"Customer:", "Discount:"};
		String[] topLineVal = new String[2];

		String[] firstLineNam = {"Receipt No:", "Payment Type:", "Check/Card No:"};
		String[] secondLineNam = {"Receipt Date:", "Payment Date:", "Bank:"};
		String[] thirdLineNam = {"Receipt Type:", "Status:", "Account No:"};
		String[] forthLineNam = {"Total Amount:", "On Documents:", "On Accounts:"};
		String[] fifthLineNam = {"Void Description:"};

		String[] firstLineVal = new String[3];
		String[] secondLineVal = new String[3];
		String[] thirdLineVal = new String[3];
		String[] forthLineVal = new String[3];
		String[] fifthLineVal = new String[1];

		String temp = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WHCUS_COL, headerRow, receiptHeaderCursor)) +
					  "  " + ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WHNAME_COL, headerRow, receiptHeaderCursor));
		topLineVal[0] = temp;
		topLineVal[1] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WHDISC_COL, headerRow, receiptHeaderCursor));

		ERPDataTable table = new ERPDataTable(4);
		float[] widths = {15f, 47f, 16f, 22f};
		table.setWidths(widths);
		table.printProperties(topLineNam, topLineVal, 10f);
		add(table);

		firstLineVal[0] = ((java.math.BigDecimal)dataBean.valueAtColumnRowResult(
								WHONUM_COL, headerRow, receiptHeaderCursor)).toString();
		firstLineVal[1] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WHPTYP_COL, headerRow, receiptHeaderCursor));
		firstLineVal[2] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WHDOCN_COL, headerRow, receiptHeaderCursor));



		java.math.BigDecimal b = (java.math.BigDecimal)dataBean.valueAtColumnRowResult(WHDATE_COL, headerRow, receiptHeaderCursor);
		secondLineVal[0] = Format.dateBigDec_Str(b);
		java.math.BigDecimal c = (java.math.BigDecimal)dataBean.valueAtColumnRowResult(WHPDAT_COL, headerRow, receiptHeaderCursor);
		secondLineVal[1] = Format.dateBigDec_Str(c);
		secondLineVal[2] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WHBANK_COL, headerRow, receiptHeaderCursor));

		thirdLineVal[0] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WHOTYP_COL, headerRow, receiptHeaderCursor));
		thirdLineVal[1] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WHSTATR_COL, headerRow, receiptHeaderCursor));
		thirdLineVal[2] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WHACCN_COL, headerRow, receiptHeaderCursor));

		forthLineVal[0] = Format.displayCurrency((java.math.BigDecimal)dataBean.valueAtColumnRowResult(
											WHAMNT_COL, headerRow, receiptHeaderCursor));
		forthLineVal[1] = Format.displayCurrency((java.math.BigDecimal)dataBean.valueAtColumnRowResult(
											WHDAMNR_COL, headerRow, receiptHeaderCursor));
		forthLineVal[2] = Format.displayCurrency((java.math.BigDecimal)dataBean.valueAtColumnRowResult(
											WHPDAP_COL, headerRow, receiptHeaderCursor));

		fifthLineVal[0] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WHVDRS_COL, headerRow, receiptHeaderCursor));
		//fifthLineVal[1] = (String)dataBean.valueAtColumnRowResult(WHDISC_COL, headerRow, receiptHeaderCursor);

		table = new ERPDataTable(6);
		widths = new float[]{14f,11f,15f,22f,16f,22f};
		table.setWidths(widths);
		table.printProperties(firstLineNam, firstLineVal, 10f);
		table.printProperties(secondLineNam, secondLineVal, 10f);
		table.printProperties(thirdLineNam, thirdLineVal, 10f);

		int[] aligns = {Element.ALIGN_RIGHT,
					    Element.ALIGN_RIGHT,
					    Element.ALIGN_RIGHT};

		table.printProperties(forthLineNam, forthLineVal, aligns, 10f);
		add(table);

		table = new ERPDataTable(2);
		widths = new float[]{20f,80f};
		

		table.setWidths(widths);
		table.printProperties(fifthLineNam, fifthLineVal, 10f);
		add(table);
		addNewLine(1);
	}
    public void addReportHeader() throws BadElementException, DocumentException, Exception {	
		String[] header1 = { "Batch Number:", "Date:", "Batch Status:"};
		String[] header2 = { "Bank:", "Payment Type:"};
		String[] header3 = { "Account Name:", "Currency:"};
		String[] hValue1 = new String[3];
		String[] hValue2 = new String[2];
		String[] hValue3 = new String[2];

		String [] firstLine = new String[7];
		String [] secondLine = new String[7];

		String [] commentsName = {"Comments:"};
		String [] commentsValue = new String[1];

		hValue1[0] = ((java.math.BigDecimal)dataBean.valueAtColumnRowResult(WBBTCH_COL,0,reportHeaderCursor)).toString();
		java.math.BigDecimal a = (java.math.BigDecimal)dataBean.valueAtColumnRowResult(WBDATE_COL,0,reportHeaderCursor);
		hValue1[1] = Format.dateBigDec_Str(a);
		hValue1[2] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WBSTAT_COL,0,reportHeaderCursor));


		hValue2[0] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WBBANK_COL,0,reportHeaderCursor));
		hValue2[1] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WBPAYM_COL,0,reportHeaderCursor));

		hValue3[0] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WBACNM_COL,0,reportHeaderCursor));
		hValue3[1] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WBCOIN_COL,0,reportHeaderCursor));

		ERPDataTable table = new ERPDataTable(6);
		float[] widths = {15f, 23f, 7f, 20f, 15f, 20f};
		table.setWidths(widths);
		//addNewLines(1);
		table.printProperties(header1, hValue1, 10f);
		add(table);

		table = new ERPDataTable(4);
		widths = new float[] {18f,47f,15f,20f};
		table.setWidths(widths);
		table.printProperties(header2, hValue2, 10f);
		table.printProperties(header3, hValue3, 10f);
		add(table);

		firstLine[0] = "Entered:";
		firstLine[1] = ((java.math.BigDecimal)dataBean.valueAtColumnRowResult(WBCCNT_COL,0,reportHeaderCursor)).toString();
		firstLine[2] = Format.displayCurrency((java.math.BigDecimal)dataBean.valueAtColumnRowResult(WBCAMN_COL,0,reportHeaderCursor));
		firstLine[3] = "On Document:";
		firstLine[4] = Format.displayCurrency((java.math.BigDecimal)dataBean.valueAtColumnRowResult(WSDAMN_COL,0,reportHeaderCursor));
		firstLine[5] = "On Accounts:";
		firstLine[6] = Format.displayCurrency((java.math.BigDecimal)dataBean.valueAtColumnRowResult(WSAAMN_COL,0,reportHeaderCursor));

		secondLine[0] = "Voided:";
		secondLine[1] = ((java.math.BigDecimal)dataBean.valueAtColumnRowResult(WSVCNT_COL,0,reportHeaderCursor)).toString();
		secondLine[2] = Format.displayCurrency((java.math.BigDecimal)dataBean.valueAtColumnRowResult(WSVAMN_COL,0,reportHeaderCursor));
		secondLine[3] = "Unidentified:";
		secondLine[4] = Format.displayCurrency((java.math.BigDecimal)dataBean.valueAtColumnRowResult(WSUAMN_COL,0,reportHeaderCursor));
		secondLine[5] = secondLine[6] = "";

		table = new ERPDataTable(7);
		widths = new float[] {13f,13f,13f,19f,13f,16f,13f};
		table.setWidths(widths);
		//addNewLines(1);
		table.printAmountCount(firstLine, secondLine, 10f);
		add(table);

		commentsValue[0] = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WSCOMM_COL,0,reportHeaderCursor));
		table = new ERPDataTable(2);
		widths = new float[]{15f, 85f};
		table.setWidths(widths);
		table.printProperties(commentsName, commentsValue, 10f);
		add(table);
		addNewLine(1);
		addThinLine();
	}
	public void addReportTitle(String loginName)throws BadElementException,DocumentException, Exception {

		String date = Format.getSysDate();
		String company = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(WBCNAM_COL, 0, reportHeaderCursor));
		String time = Format.getSysTime();
		String report = "Receipt Batch Listing";
		ERPDataTable fileHeader = new ERPDataTable(5);
		fileHeader.printReportHeader1(company, report, loginName, date, time, 15f);
		add(fileHeader);
		addLine();
	}
	public void addTblHeader() throws BadElementException,DocumentException, Exception {
	   String[] headers = {"Document", "Due Date", "Original Amnt", "Applied Amnt", "Gained Disc.", "Balance Due", "Status"};

	   int[] headerAligns 	= {Element.ALIGN_LEFT,
 							   Element.ALIGN_LEFT,
							   Element.ALIGN_RIGHT,
							   Element.ALIGN_RIGHT,
							   Element.ALIGN_RIGHT,
							   Element.ALIGN_RIGHT,
							   Element.ALIGN_RIGHT};

	   ERPDataTable table = new ERPDataTable(7);
	   float[] widths = {15f, 12f, 17f, 17f, 15f, 15f, 9f};
	   table.setWidths(widths);
	   table.printTblHeader(headers, headerAligns, 10f);
	   add(table);
	}
    public void setDBBean( SPDBBean dataBean ) {
	    this.dataBean = dataBean;
    }
}
