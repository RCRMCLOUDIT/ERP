package com.cap.util;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import com.cap.erp.SPDBBean;
import com.lowagie.text.*;
import com.cap.billing.invoice.*;
import com.lowagie.text.pdf.*;
import com.cap.erp.report.ReportCell;

public class RecurrentInvoiceRptHelper extends ERPReport {

	//Columns for header
	static final int IVINVO_COL 	= 1;
	static final int CMNAME_COL 	= 2;
	static final int TERMNAME_COL 	= 3;
	static final int CURRNAME_COL 	= 4;
	static final int IVTOLI_COL 	= 5;
	static final int IVTOTA_COL 	= 6;
	static final int IVTTAX_COL 	= 7;
	static final int IVCUCHP_COL 	= 8;
	static final int IVSTRDATE_COL 	= 9;
	static final int IVENDDATE_COL 	= 10;
	static final int IVREFER_COL 	= 11; 
	static final int REPNAME_COL	= 14;
	static final int REPEID_COL		= 15;


	//Columns for item
	static final int IDLIN_COL = 1;
	static final int IDITEM_COL = 2;
	static final int IDDES1_COL = 3;
	static final int IDDES2_COL = 4;  
	static final int IDDES3_COL = 5;
	static final int IDDES4_COL = 6;
	static final int IDDES5_COL = 7;
	static final int IDDES6_COL = 8;
	static final int UOMNAME_COL = 9;  
	static final int IDQUAN_COL = 10;
	static final int IDPRIC_COL = 11;
	static final int IDCTAX_COL = 12;
	static final int IDTOTA_COL = 13;

	// cust address
	static final int CMADD1_COL 	= 1;
	static final int CMADD2_COL 	= 2;
	static final int CMADD3_COL 	= 3;
	static final int CMCITY_COL 	= 4;
	static final int CMSTAT_COL 	= 5;
	static final int CMZIP_COL 		= 6;
	static final int CMPROV_COL 	= 7;
	static final int CMCOUNTRY_COL 	= 8;
	static final int CONTACTNAME_COL= 12;

	static final int headerInfoCursor		= 1;
	static final int detailCursor 			= 2;
	static final int standardCommentsCursor	= 3;
	static final int userCommentsCursor		= 4;
	static final int companyInfoCursor		= 5;
	static final int custInfoCursor			= 6;

	int[] detailAligns = {Element.ALIGN_CENTER,
 						  Element.ALIGN_LEFT,
						  Element.ALIGN_CENTER,
						  Element.ALIGN_RIGHT,
						  Element.ALIGN_RIGHT,
						  Element.ALIGN_RIGHT};

	float[] detailWidth = {10f,40f,10f,10f,15f,15f};

	static final float LEFT 	= -54f;
	static final float RIGHT 	= -54f;
	static final float TOP 		= 40f;
	static final float BOTTOM 	= 110f;

	private InvoiceDataTable table;
	private String comments;
	private SPDBBean dataBean;
	//private boolean hasUserComments = false;

    public RecurrentInvoiceRptHelper(Rectangle pageSize) {
        super(pageSize);
        setMargins(LEFT, RIGHT, TOP, BOTTOM);
    }
    public RecurrentInvoiceRptHelper(
        Rectangle pageSize,
        float marginLeft,
        float marginRight,
        float marginTop,
        float marginBottom) {
        super(pageSize, marginLeft, marginRight, marginTop, marginBottom);
        setMargins(LEFT, RIGHT, TOP, BOTTOM);
    }
	public RecurrentInvoiceRptHelper(String fname) throws DocumentException
	{
		super(fname);
		setMargins(LEFT, RIGHT, TOP, BOTTOM);
	}
	public RecurrentInvoiceRptHelper( HttpServletRequest request ) throws DocumentException {
        super(request);
        setMargins(LEFT, RIGHT, TOP, BOTTOM);
    }
	private void addDetails( InvoiceDataTable table, int detailRow ) throws BadElementException,
    														DocumentException, Exception {
   		String[] value = new String[6];
		String[] descs = new String[5];

   		value[0] = getTrimedStr(dataBean.valueAtColumnRowResult(IDLIN_COL,detailRow,detailCursor));
   		value[1] = getTrimedStr(dataBean.valueAtColumnRowResult(IDDES1_COL,detailRow,detailCursor));
   		value[2] = getTrimedStr(dataBean.valueAtColumnRowResult(UOMNAME_COL,detailRow,detailCursor));
		//value[3] = getTrimedStr(dataBean.valueAtColumnRowResult(IDQUAN_COL,detailRow,detailCursor));
		value[3] = Format.formatQty(((BigDecimal)dataBean.valueAtColumnRowResult(IDQUAN_COL,detailRow,detailCursor)));
		value[4] = Format.displayCurrency4Desc((BigDecimal)dataBean.valueAtColumnRowResult(IDPRIC_COL,detailRow,detailCursor));
		String tem = getTrimedStr(dataBean.valueAtColumnRowResult(IDTOTA_COL,detailRow,detailCursor));
		value[5] = Format.displayCurrency(new BigDecimal(tem));

		descs[0] = getTrimedStr(dataBean.valueAtColumnRowResult(IDDES2_COL,detailRow,detailCursor));
		descs[1] = getTrimedStr(dataBean.valueAtColumnRowResult(IDDES3_COL,detailRow,detailCursor));
		descs[2] = getTrimedStr(dataBean.valueAtColumnRowResult(IDDES4_COL,detailRow,detailCursor));
		descs[3] = getTrimedStr(dataBean.valueAtColumnRowResult(IDDES5_COL,detailRow,detailCursor));
		descs[4] = getTrimedStr(dataBean.valueAtColumnRowResult(IDDES6_COL,detailRow,detailCursor));

		table.printTblCells(value, detailAligns, 15f);

		for ( int i=0; i<descs.length; i++ ) {
			if ( descs[i].length() != 0 ) {
				for ( int j=0; j<value.length; j++) {
					if ( j == 1 ) {
						value[j] = descs[i];
					}else {
						value[j] = "";
					}
				}
				table.printTblCells(value, detailAligns, 8f);
			}
		}

	}
	private void addInvoiceTotalLine(InvoiceDataTable table, ResourceBundle rb) throws BadElementException, DocumentException, Exception
	{
		
		
	  String subTotal =Format.formatDecimal((String)dataBean.valueAtColumnRowResult(IVTOLI_COL,0,headerInfoCursor));
	  String tax =Format.formatDecimal((String)dataBean.valueAtColumnRowResult(IVTTAX_COL,0,headerInfoCursor));
	  String total =Format.formatDecimal((String)dataBean.valueAtColumnRowResult(IVTOTA_COL,0,headerInfoCursor));
	  String currency = (String)dataBean.valueAtColumnRowResult(CURRNAME_COL,0,headerInfoCursor);
	  String amount = (String)dataBean.valueAtColumnRowResult(IVTOTA_COL,0,headerInfoCursor); 
  	  
  	  double amt = Double.parseDouble(amount);
  	  
	  String numberWords = NumberSpeller.figureToWords(amt, false, rb) + " " + currency;
	  
  	  ReportCell[] amountWords = {new ReportCell(numberWords,6,1,Element.ALIGN_LEFT)};
	   
	  ReportCell[] subTotalValue = {new ReportCell("", 4, 1),
	  							  new ReportCell(rb.getString("P12260015"), 1, 1, Element.ALIGN_RIGHT),
	  							  new ReportCell(subTotal, 1, 1, Element.ALIGN_RIGHT)
	  							 };

	  ReportCell[] totalTaxValue = {new ReportCell("", 4, 1),
	  							  new ReportCell(rb.getString("P12260016"), 1, 1, Element.ALIGN_RIGHT),
	  							  new ReportCell(tax, 1, 1, Element.ALIGN_RIGHT)
	  							 };

	  ReportCell[] totalValue = {new ReportCell("", 4, 1),
	  							  new ReportCell(rb.getString("P12260017"), 1, 1, Element.ALIGN_RIGHT),
	  							  new ReportCell(total, 1, 1, Element.ALIGN_RIGHT)
	  							 };

	  table.printOutRow(amountWords, true, false);
	  table.printOutRow(subTotalValue, true, false);
	  table.printOutRow(totalTaxValue, true, false);
	  table.printOutRow(totalValue, true, false);
	}

	private void addSignature(InvoiceDataTable table) throws BadElementException, DocumentException, Exception
	{
		  ReportCell[] signature = {new ReportCell(rb.getString("P12260027") + " __________________", 2, 1),
									new ReportCell("", 2, 1),
									new ReportCell(rb.getString("P12260028") + " __________________", 2, 1)};
									
		  ReportCell[] signature2 = {new ReportCell(rb.getString("P12260029") + " _________________", 2, 1),
									 new ReportCell("", 2, 1),
									 new ReportCell(rb.getString("P12260030") + " _________________", 2, 1) };				  					

		  table.printOutRow(signature, true, false);
		  table.printOutRow(signature2, true, false);
	}

	public InvoiceDataTable addReportHeader( String companyName) throws BadElementException, DocumentException, Exception
	{	
		String[] invoiceInfo = new String[4];
		Vector info = new Vector();
		String country = getTrimedStr(dataBean.valueAtColumnRowResult(CMCOUNTRY_COL,0,custInfoCursor));
	
		invoiceInfo[0] = getTrimedStr(dataBean.valueAtColumnRowResult(IVREFER_COL,0,headerInfoCursor));
		invoiceInfo[1] = Format.getFormatedDateStr(getTrimedStr(dataBean.valueAtColumnRowResult(IVSTRDATE_COL, 0, headerInfoCursor)));
		invoiceInfo[2] = getTrimedStr(dataBean.valueAtColumnRowResult(TERMNAME_COL,0,headerInfoCursor));
		invoiceInfo[3] = Format.getFormatedDateStr(getTrimedStr(dataBean.valueAtColumnRowResult(IVENDDATE_COL, 0, headerInfoCursor)));

		//info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CONTACTNAME_COL,0,custInfoCursor)));
		info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CMNAME_COL,0,headerInfoCursor)));
		info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD1_COL,0,custInfoCursor)));
		
		if(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD2_COL,0,custInfoCursor)).length()>0){
			info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD2_COL,0,custInfoCursor)));
		}
		if(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD3_COL,0,custInfoCursor)).length()>0){
			info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD3_COL,0,custInfoCursor)));
		}
		
		StringBuffer city = new StringBuffer();
		
		String repId = getTrimedStr(dataBean.valueAtColumnRowResult(REPEID_COL, 0, headerInfoCursor));
		String repName = getTrimedStr(dataBean.valueAtColumnRowResult(REPNAME_COL, 0, headerInfoCursor));

		//city, state/province
		if(getTrimedStr(dataBean.valueAtColumnRowResult(CMSTAT_COL,0,custInfoCursor)).length()>0)	
			city.append(getTrimedStr(dataBean.valueAtColumnRowResult(CMCITY_COL,0,custInfoCursor))+ ", " +
						getTrimedStr(dataBean.valueAtColumnRowResult(CMSTAT_COL,0,custInfoCursor))+	"  " +
						Format.displayZip( getTrimedStr(dataBean.valueAtColumnRowResult(CMZIP_COL,0,custInfoCursor)), country) );
		else
			city.append(getTrimedStr(dataBean.valueAtColumnRowResult(CMCITY_COL,0,custInfoCursor))+ ", " +
						getTrimedStr(dataBean.valueAtColumnRowResult(CMPROV_COL,0,custInfoCursor))+	"  " +
						Format.displayZip( getTrimedStr(dataBean.valueAtColumnRowResult(CMZIP_COL,0,custInfoCursor)), country) );
	
		info.addElement(city.toString());
		info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CMCOUNTRY_COL,0,custInfoCursor)));
		info.trimToSize();
	
		String [] contactInfo = new String[info.size()];//(String [])info.toArray();
		for(int i=0; i <info.size(); i++){
			contactInfo[i] = (String)info.get(i);
		}
	
		InvoiceDataTable table = new InvoiceDataTable(rb); 
	
		table.printInvoiceHeader(companyName, repName, contactInfo, invoiceInfo, logoPath, logoPathRep, useSSL, repId);
		return table;
	}
	
	private void addTblHeader(InvoiceDataTable table) throws BadElementException,
    										DocumentException, Exception {

        String currencyName = getTrimedStr(dataBean.valueAtColumnRowResult(CURRNAME_COL,0,headerInfoCursor));
        ReportCell[] currency= {new ReportCell("", 3, 1),
        						new ReportCell(currencyName, 3, 1, Element.ALIGN_RIGHT)
        					   };
        ReportCell[] headerText = {	new ReportCell(rb.getString("P12260022"), 1, 1, Element.ALIGN_LEFT),
        							new ReportCell(rb.getString("P12260023"), 1, 1, Element.ALIGN_LEFT),
        							new ReportCell(rb.getString("P12260024"), 1, 1, Element.ALIGN_LEFT),
        							new ReportCell(rb.getString("P12260025"), 1, 1, Element.ALIGN_RIGHT),
        							new ReportCell(rb.getString("P12260026"), 1, 1, Element.ALIGN_RIGHT),
        							new ReportCell(rb.getString("P12260014"), 1, 1, Element.ALIGN_RIGHT)
 							       };
        table.printOutRow(currency, true, false);
        table.printOutRow(headerText, true, true);

	}
	public void buildPDFInvoice(String companyName)throws BadElementException, DocumentException, Exception 
	{

		//build a header for each invoice.
		Phrase p = new Phrase();
		p.add(addReportHeader(companyName));
		HeaderFooter header = new HeaderFooter(p, false);
		header.setBorder(Rectangle.NO_BORDER);
		header.setAlignment(Element.ALIGN_CENTER);
		setHeader(header);

		//Build a footer for each invoice
		HeaderFooter footer = new HeaderFooter(new Phrase(""), false);
		footer.setAlignment(Rectangle.ALIGN_CENTER);
		footer.setBorder(Rectangle.NO_BORDER);
		setFooter(footer);

		table = new InvoiceDataTable(6);
		table.setWidths(detailWidth);
		addTblHeader(table);
		table.endHeaders();
		
		open();
		comments = getComments();
		String[] comInfo = getComInfo();

		InvoicePageEvent event = new InvoicePageEvent(comInfo, comments);
		PdfWriter writer = getWriter();
		writer.setPageEvent(event);

		newPage();

		int numOfDetails = dataBean.RowCountResult(detailCursor);
		
		for ( int row = 0; row < numOfDetails; row++) {
			addDetails(table, row);
		}
		
		addInvoiceTotalLine(table,rb);
		addSignature(table);		
		add(table);
		//close the document.
		close();
	}
	public String[] getComInfo () {
		
		int numOfCol = 10;
		String[] temp = new String[numOfCol];
		Vector info = new Vector();
		String[] comInfo = new String[6];

		for(int i=0; i<numOfCol; i++) {
			temp[i] = getTrimedStr(dataBean.valueAtColumnRowResult(i+1, 0, companyInfoCursor));
		}

		info.addElement(temp[0]); //address1
		if(temp[1].length()>0)
			info.addElement(temp[1]); //address2
		if(temp[8].length()>0)
			info.addElement(temp[8]); //address3			
		
        if(temp[3].length()>0)
		    info.addElement(temp[2] + ", " + temp[3] + " " + Format.displayZip(temp[4], temp[7])); //City + State + zip
		else
		    info.addElement(temp[2] + ", " + temp[5] + " " + Format.displayZip(temp[4], temp[7])); //City + Prov + zip
		
		info.addElement(temp[9]); //country name

		if(temp[6].length()>0)
        {
		   info.addElement(rb.getString("P12260020") + "  " + Format.displayPhone(temp[6])); //phone
	    }
        info.trimToSize();

        for(int i=0; i<comInfo.length;i++){
	        if(i < info.size())
	        	comInfo[i] = (String)info.get(i);
	        else
	        	comInfo[i] = "";
        }

		return comInfo;
	}
	private String getComments()throws BadElementException, DocumentException, Exception {
		int totalRows = dataBean.RowCountResult(userCommentsCursor);

		if ( totalRows != 0 ) {
			//return Format.completeWord(getUserComments());
			return getUserComments();
		}

		//return Format.completeWord(getStandardComments());
		return getStandardComments();
	}
	private String getStandardComments() throws BadElementException, DocumentException, Exception
	{
		String standardComments = "";
		if(dataBean.RowCountResult(standardCommentsCursor) ==0)
		{
			return standardComments;
		}
		else
		{	
			standardComments = (String)dataBean.valueAtColumnRowResult(1, 0, standardCommentsCursor);
			return standardComments;
		}
		
		/*
		String[] standardComments = new String[8];
	
		if(dataBean.RowCountResult(standardCommentsCursor) ==0)
		{
			return standardComments;
		}
		else
		{	
			for(int i=0; i<standardComments.length -1; i++) {
				//do not trim comments here!
				standardComments[i] = (String)dataBean.valueAtColumnRowResult(i+1, 0, standardCommentsCursor);
			}
			standardComments[7] = "";
	
			return standardComments;
		}
		*/
		
	}
	private String getUserComments()throws BadElementException, DocumentException, Exception {
		
		StringBuffer userComments = new StringBuffer();
		
		for(int i=0; i< 7; i++) {
			userComments.append(((String)dataBean.valueAtColumnRowResult(i+1, 0, userCommentsCursor)));
		}
		return userComments.toString();
		/*
		String[] userComments = new String[8];
		for(int i=0; i<userComments.length - 1; i++) {
			//do not trim comments here!
			
			userComments[i] = (String)dataBean.valueAtColumnRowResult(i+1, 0, userCommentsCursor);
		}
		userComments[7] = "";
		return userComments;
		*/
	}
    public void setDBBean( SPDBBean dataBean ) {
	    this.dataBean = dataBean;
    }
}
