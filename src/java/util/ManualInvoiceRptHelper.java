package com.cap.util;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import com.cap.erp.SPDBBean;
import com.lowagie.text.*;
import com.cap.billing.invoice.*;
import com.lowagie.text.pdf.*;
import com.cap.erp.report.ReportCell;

public class ManualInvoiceRptHelper extends ERPReport {

	//Columns for cursor #1
	private final int IVINVO_COL 	= 1;
	private final int CMNAME_COL 	= 2;
	private final int IVDATE_COL 	= 3;
	private final int PPTEXA_A1_COL	= 4;
	private final int IVDDAT_COL 	= 5;	
	private final int PPTEXA_A2_COL	= 6;
	private final int IVTOLI_COL 	= 7;
	private final int IVTOTA_COL 	= 8;
	private final int IVTTAX_COL 	= 9;
	//private final int IVCUCHP_COL 	= 10;
	private final int CMADD1_COL 	= 11;
	private final int CMADD2_COL 	= 12;
	private final int CMCITY_COL	= 13;
	private final int CMSTAT_COL	= 14;
	private final int CMZIP_COL		= 15;
	private final int CMPROV_COL	= 16;
	private final int CMCOUNTRY_COL = 17;
	//private final int INCUST_COL  	= 18;
	//private final int IVTERM_COL    = 19;
	//private final int IVORCU_COL    = 20;
	private final int CCNAME_COL 	= 21;
	private final int CMADD3_COL    = 24;
	private final int CTRYNAME_COL 	= 25;
	private final int FEDID_COL 	= 30;

	//Columns for cursor #2
	//private final int IDINVO_COL	= 1;
	private final int IDLIN_COL 	= 2;
	//private final int IDITEM_COL 	= 3;
	private final int IDDES1_COL 	= 4;
	private final int IDDES2_COL 	= 5;
	private final int IDDES3_COL 	= 6;
	private final int IDDES4_COL 	= 7;
	private final int IDDES5_COL 	= 8;
	private final int IDDES6_COL 	= 9;
	private final int PPTEXA_COL 	= 10;
	private final int IDQUAN_COL 	= 11;
	private final int IDPRIC_COL 	= 12;
	//private final int IDCTAX_COL 	= 13;
	private final int IDTOTA_COL 	= 14;
	//private final int IDDUOM_COL 	= 15;
	//private final int IDTXAM_COL 	= 16;

	private final int headerInfoCursor			= 1;
	private final int detailCursor 				= 2;
	private final int standardCommentsCursor	= 3;
	private final int userCommentsCursor		= 4;
	private final int companyInfoCursor			= 5;
	
	HttpServletRequest MIRequest 	= null;
	private String compId 			= null;

	int[] detailAligns = {Element.ALIGN_CENTER,
 						  Element.ALIGN_LEFT,
						  Element.ALIGN_CENTER,
						  Element.ALIGN_RIGHT,
						  Element.ALIGN_RIGHT,
						  Element.ALIGN_RIGHT};

	float[] detailWidth = {10f,42f,10f,10f,14f,14f};

	static final float LEFT 	= -27;
	static final float RIGHT 	= -27;
	//static final float LEFT 	= 0;
	//static final float RIGHT 	= 0;
	static final float TOP 		= 40;
	static final float BOTTOM 	= 110;

	private InvoiceDataTable table;
	//private String[] comments;
	private String comments;
	private SPDBBean dataBean;
	//private boolean hasUserComments = false;

    public ManualInvoiceRptHelper(Rectangle pageSize) {
        super(pageSize);
        setMargins(LEFT, RIGHT, TOP, BOTTOM);
    }
    public ManualInvoiceRptHelper(
        Rectangle pageSize,
        float marginLeft,
        float marginRight,
        float marginTop,
        float marginBottom) {
        super(pageSize, marginLeft, marginRight, marginTop, marginBottom);
        setMargins(LEFT, RIGHT, TOP, BOTTOM);
    }
	public ManualInvoiceRptHelper(String fname) throws DocumentException
	{
		super(fname);
		setMargins(LEFT, RIGHT, TOP, BOTTOM);
	}
	public ManualInvoiceRptHelper( HttpServletRequest request ) throws DocumentException {
        super(request);
        setMargins(LEFT, RIGHT, TOP, BOTTOM);
	  	this.MIRequest = request;
	  
	  	HttpSession session = this.MIRequest.getSession(false);
	  	com.cap.portal.PortalUser puser = (com.cap.portal.PortalUser) session.getAttribute("portal_PortalUser");

	  	this.compId = puser.getComp();        
    }
	private void addDetails( InvoiceDataTable table, int detailRow ) throws BadElementException,
    														DocumentException, Exception {
   		String[] value = new String[6];
		//String[] descs = new String[5];
   		String prodDescr = (String)dataBean.valueAtColumnRowResult(IDDES1_COL,detailRow,detailCursor) + 
   						dataBean.valueAtColumnRowResult(IDDES2_COL,detailRow,detailCursor) +
   						dataBean.valueAtColumnRowResult(IDDES3_COL,detailRow,detailCursor) +
   						dataBean.valueAtColumnRowResult(IDDES4_COL,detailRow,detailCursor) +
   						dataBean.valueAtColumnRowResult(IDDES5_COL,detailRow,detailCursor) +
   						dataBean.valueAtColumnRowResult(IDDES6_COL,detailRow,detailCursor);
   						
   		value[0] = getTrimedStr(dataBean.valueAtColumnRowResult(IDLIN_COL,detailRow,detailCursor));
   		value[1] = getTrimedStr(prodDescr);
   		value[2] = getTrimedStr(dataBean.valueAtColumnRowResult(PPTEXA_COL,detailRow,detailCursor));
		value[3] =	Format.formatQty((java.math.BigDecimal)dataBean.valueAtColumnRowResult(IDQUAN_COL,detailRow,detailCursor));
				
		value[4] = Format.displayCurrency4Desc((BigDecimal)(dataBean.valueAtColumnRowResult(IDPRIC_COL,detailRow,detailCursor)));
		String tem = getTrimedStr(dataBean.valueAtColumnRowResult(IDTOTA_COL,detailRow,detailCursor));
		value[5] = Format.displayCurrency(new BigDecimal(tem));
		
		/*
		descs[0] = getTrimedStr(dataBean.valueAtColumnRowResult(IDDES2_COL,detailRow,detailCursor));
		descs[1] = getTrimedStr(dataBean.valueAtColumnRowResult(IDDES3_COL,detailRow,detailCursor));
		descs[2] = getTrimedStr(dataBean.valueAtColumnRowResult(IDDES4_COL,detailRow,detailCursor));
		descs[3] = getTrimedStr(dataBean.valueAtColumnRowResult(IDDES5_COL,detailRow,detailCursor));
		descs[4] = getTrimedStr(dataBean.valueAtColumnRowResult(IDDES6_COL,detailRow,detailCursor));
		*/
		table.printTblCells(value, detailAligns, 15f);
		/*
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
		*/
	}
private void addInvoiceTotalLine(InvoiceDataTable table) throws BadElementException,
	    														DocumentException, Exception{
	System.out.println("Entro a construir el InvoiceTotal PDF 1");

	  String subTotal =Format.formatDecimal((String)dataBean.valueAtColumnRowResult(IVTOLI_COL,0,headerInfoCursor));
	  String tax =Format.formatDecimal((String)dataBean.valueAtColumnRowResult(IVTTAX_COL,0,headerInfoCursor));
	  String total =Format.formatDecimal((String)dataBean.valueAtColumnRowResult(IVTOTA_COL,0,headerInfoCursor));
	  String currency = getTrimedStr(this.dataBean.valueAtColumnRowResult(PPTEXA_A2_COL, 0, headerInfoCursor));		
	  String amount = (String)dataBean.valueAtColumnRowResult(IVTOTA_COL,0,headerInfoCursor);	
		System.out.println("Entro a construir el InvoiceTotal PDF 2");
	  	  String numberWords = AmountToWords.nletra(Double.parseDouble(amount));
	  
	        int cents = 0;
	        long amt = (long)Double.parseDouble(amount);
	    	System.out.println("Entro a construir el InvoiceTotal PDF 3");
	  		cents = (int)Math.round((Double.parseDouble(amount) - amt) * 100);
	  		//System.out.println("Centavo:" + cents);
	  		if (cents != 0 )
	  		{
	  			if (numberWords.length() != 0)
	  				numberWords = numberWords + " " + (currency.trim().equals("C$")?"Cordobas":"Dolares") + " con " + (int)cents + "/100";
	  			else
	  				numberWords = (int)cents + "/100";
	  		}
	  		else if (cents == 0 )
	  		{
	  			if (numberWords.length() != 0)
	  				numberWords = numberWords + " " + (currency.trim().equals("C$")?"Cordobas":"Dolares") + " con " + "00/100";
	  			else
	  				numberWords = (int)cents + "/100";
			} 
	  		System.out.println("Entro a construir el InvoiceTotal PDF 4");
	  ReportCell[] subTotalValue = {new ReportCell("", 4, 1),
	  							  new ReportCell(rb.getString("P12260015"), 1, 1, Element.ALIGN_RIGHT),
	  							  new ReportCell(subTotal, 1, 1, Element.ALIGN_RIGHT)
	  							 };

	  ReportCell[] totalTaxValue = {new ReportCell("", 4, 1),
			  					  new ReportCell("Impuesto(15%)", 1, 1, Element.ALIGN_RIGHT),
	  							  new ReportCell(tax, 1, 1, Element.ALIGN_RIGHT)
	  							 };

		ReportCell[] totalValue = {new ReportCell(numberWords, 4, 1, Element.ALIGN_LEFT),
				  new ReportCell(rb.getString("P12260017"), 1, 1, Element.ALIGN_RIGHT),
				  new ReportCell(total, 1, 1, Element.ALIGN_RIGHT),
		 		  //new ReportCell("", 1, 1),
				 };
		System.out.println("Entro a construir el InvoiceTotal PDF 5");
	  table.printOutRow(subTotalValue, true, false);
	  table.printOutRow(totalTaxValue, true, false);
	  table.printOutRow(totalValue, true, false);
		System.out.println("Entro a construir el InvoiceTotal PDF 6");
	}
public InvoiceDataTable addReportHeader( String companyName) throws BadElementException, DocumentException, Exception
{

	String[] invoiceInfo = new String[4];
	Vector info = new Vector();
	String country = getTrimedStr(dataBean.valueAtColumnRowResult(CMCOUNTRY_COL,0,headerInfoCursor));

	invoiceInfo[0] = getTrimedStr(dataBean.valueAtColumnRowResult(IVINVO_COL,0,headerInfoCursor));
	invoiceInfo[1] = Format.getFormatedDateStr(
        					getTrimedStr(dataBean.valueAtColumnRowResult(IVDATE_COL, 0, headerInfoCursor)));

	invoiceInfo[2] = getTrimedStr(dataBean.valueAtColumnRowResult(PPTEXA_A1_COL,0,headerInfoCursor));
	invoiceInfo[3] = Format.getFormatedDateStr(
        					getTrimedStr(dataBean.valueAtColumnRowResult(IVDDAT_COL, 0, headerInfoCursor)));
/*
	contactInfo[0] = getTrimedStr(dataBean.valueAtColumnRowResult(CCNAME_COL,0,headerInfoCursor));
	contactInfo[1] = getTrimedStr(dataBean.valueAtColumnRowResult(CMNAME_COL,0,headerInfoCursor));
	contactInfo[2] = getTrimedStr(dataBean.valueAtColumnRowResult(CMADD1_COL,0,headerInfoCursor));
	contactInfo[3] = getTrimedStr(dataBean.valueAtColumnRowResult(CMADD2_COL,0,headerInfoCursor));
*/
	//info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CCNAME_COL,0,headerInfoCursor)));
	info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CMNAME_COL,0,headerInfoCursor)));
	info.addElement("RUC: " + getTrimedStr(dataBean.valueAtColumnRowResult(FEDID_COL,0,headerInfoCursor)));
	info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD1_COL,0,headerInfoCursor)));
	if(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD2_COL,0,headerInfoCursor)).length()>0){
		info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD2_COL,0,headerInfoCursor)));
	}
	if(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD3_COL,0,headerInfoCursor)).length()>0){
		info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD3_COL,0,headerInfoCursor)));
	}
	
	StringBuffer city = new StringBuffer();

	//city, state/province
	if(getTrimedStr(dataBean.valueAtColumnRowResult(CMSTAT_COL,0,headerInfoCursor)).length()>0)	
		city.append(getTrimedStr(dataBean.valueAtColumnRowResult(CMCITY_COL,0,headerInfoCursor))+ ", " +
					getTrimedStr(dataBean.valueAtColumnRowResult(CMSTAT_COL,0,headerInfoCursor))+	"  " +
					Format.displayZip( getTrimedStr(dataBean.valueAtColumnRowResult(CMZIP_COL,0,headerInfoCursor)), country) );
	else
		city.append(getTrimedStr(dataBean.valueAtColumnRowResult(CMCITY_COL,0,headerInfoCursor))+ ", " +
					getTrimedStr(dataBean.valueAtColumnRowResult(CMPROV_COL,0,headerInfoCursor))+	"  " +
					Format.displayZip( getTrimedStr(dataBean.valueAtColumnRowResult(CMZIP_COL,0,headerInfoCursor)), country) );

	info.addElement(city.toString());
	//03/10/09 Yan, do not show USA country name
	if(country.trim().equals("USA"))
		info.addElement(getTrimedStr(""));
	else
		info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CTRYNAME_COL,0,headerInfoCursor)));
	info.trimToSize();

	String [] contactInfo = new String[info.size()];//(String [])info.toArray();
	for(int i=0; i <info.size(); i++){
		contactInfo[i] = (String)info.get(i);
	}

	InvoiceDataTable table = new InvoiceDataTable(rb);
	table.printInvoiceHeader(companyName, "", contactInfo, invoiceInfo, logoPath, "", "N", "");
	
	return table;
}
	private void addTblHeader(InvoiceDataTable table) throws BadElementException,
    										DocumentException, Exception {

        String currencyName =
        		getTrimedStr(dataBean.valueAtColumnRowResult(PPTEXA_A2_COL,0,headerInfoCursor));
        ReportCell[] currency= {new ReportCell("", 3, 1),
        						new ReportCell(currencyName, 3, 1, Element.ALIGN_RIGHT)
        					   };
        ReportCell[] headerText = {	new ReportCell(rb.getString("P00130019"), 1, 1, Element.ALIGN_LEFT),
        							new ReportCell(rb.getString("P00130004"), 1, 1, Element.ALIGN_LEFT),
        							new ReportCell(rb.getString("P00130016"), 1, 1, Element.ALIGN_LEFT),
        							new ReportCell(rb.getString("P00130017"), 1, 1, Element.ALIGN_RIGHT),
        							new ReportCell(rb.getString("P00130018"), 1, 1, Element.ALIGN_RIGHT),
        							new ReportCell(rb.getString("P00130013"), 1, 1, Element.ALIGN_RIGHT)
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
		//System.out.println("Detalle de Cursor" +numOfDetails);
		for ( int row=0; row<numOfDetails; row++) {
			addDetails(table, row);
		}
		
		addInvoiceTotalLine(table);
		if(!compId.equals("00001") && !compId.equals("00002") && !compId.equals("00003"))
			addSignature(table);		
		add(table);
		//close the document.
		close();
	}
	
	private void addSignature(InvoiceDataTable table) throws BadElementException, DocumentException, Exception
	{
		/*
		  	ReportCell[] signatureLine = {new ReportCell(rb.getString("P12260027") + " __________________", 2, 1),
					new ReportCell("", 1, 1),
					new ReportCell(rb.getString("P12260028") + " __________________", 3, 1)};
			
			ReportCell[] signature2 = {new ReportCell(rb.getString("P12260029") + " _________________", 2, 1),
					 new ReportCell("", 1, 1),
					 new ReportCell(rb.getString("P12260030") + " _________________", 3, 1) };*/			
		
		  ReportCell[] signatureLine = {new ReportCell("__________________ ", 2, 1, Element.ALIGN_CENTER),
										new ReportCell("__________________ ", 2, 1, Element.ALIGN_LEFT),
										new ReportCell("__________________ ", 2, 1, Element.ALIGN_RIGHT)};

		  ReportCell[] signatureText = {new ReportCell(rb.getString("P12260027"), 2, 1, Element.ALIGN_CENTER),
										new ReportCell(rb.getString("P12260028"), 2, 1, Element.ALIGN_CENTER),
										new ReportCell(rb.getString("P12260029"), 2, 1, Element.ALIGN_CENTER)};		  
		  
		  ReportCell[] blank 	  = {new ReportCell("\n", 6, 1)};	

		  table.printOutRow(blank, true, false);
		  table.printOutRow(blank, true, false);
		  table.printOutRow(blank, true, false);
		  table.printOutRow(blank, true, false);
		  table.printOutRow(blank, true, false);
		  //table.printOutRow(blank, true, false);
		  //table.printOutRow(blank, true, false);
		  //table.printOutRow(blank, true, false);
		  //table.printOutRow(blank, true, false);
		  //table.printOutRow(blank, true, false);
		  //table.printOutRow(blank, true, false);
		  table.printOutRow(signatureLine, true, false);
		  table.printOutRow(signatureText, true, false);		  
		  //table.printOutRow(blank, true, false);
		  //table.printOutRow(blank, true, false);
		  //table.printOutRow(signatureText, true, false);			  
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
		   info.addElement("Phone  " + Format.displayPhone(temp[6])); //phone
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
	/*
	private String[] getComments() throws BadElementException, DocumentException, Exception 
	{
		int totalRows = dataBean.RowCountResult(userCommentsCursor);

		if ( totalRows != 0 ) {
			return getUserComments();
		}

		return getStandardComments();
	}
	*/
	private String getComments() throws BadElementException, DocumentException, Exception 
	{
		int totalRows = dataBean.RowCountResult(userCommentsCursor);

		if ( totalRows != 0 ) {
			return getUserComments();
		}

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
			return ((String)dataBean.valueAtColumnRowResult(1, 0, standardCommentsCursor)).trim();
		}
			
	}

	private String getUserComments() throws BadElementException, DocumentException, Exception
	{
		StringBuffer userComments = new StringBuffer();

		for(int i = 0; i < 7; i++)
			userComments.append((String)dataBean.valueAtColumnRowResult(i + 1, 0, 4));

		//EPN CORINTO y SANDINO
		if(compId.equals("00001") || compId.equals("00002"))
		{
			return userComments.toString()+ "\nAutorización DGI: AFC-DGC-SCC-012-06-2009";
		}
		//EPN DEMAS PUERTOS
		else
		{
			return userComments.toString() + "\nAutorización DGI: AFC-DGC-SCC-002-01-2012";
		}        	
		//CZF
        //return userComments.toString()+ "\nAutorización #: AFC-DGC-SCC-006-04-2011";
	}
	
    public void setDBBean( SPDBBean dataBean ) {
	    this.dataBean = dataBean;
    }
}
