package com.cap.util;

import java.util.*;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;

import com.cap.erp.SPDBBean;
import com.lowagie.text.*;
import com.cap.billing.invoice.*;
import com.lowagie.text.pdf.*;
import com.cap.erp.report.ReportCell;
import com.cap.erp.report.DBTable;

public class RegularInvoiceRptHelper extends ERPReport {

	//Columns for cursor #1
	//private final int HRCOM_COL 	= 1;
	private final int CCNAME_COL 	= 2;
	private final int HRINVO_COL 	= 3;
	private final int HRDATE_COL 	= 4;
	//private final int HRTERM_COL 	= 5;
	private final int HRTOTA_COL 	= 6;
	private final int HRTTAX_COL 	= 7;
	private final int HRDDAT_COL 	= 8;
	//private final int HRSTAT_COL 	= 9;
	private final int PPTEXA_COL 	= 10;
	private final int CMNAME_COL	= 11;
	private final int CMADD1_COL 	= 12;
	private final int CMADD2_COL 	= 13;
	private final int CMSTAT_COL	= 14;
	private final int CMCITY_COL	= 15;
	private final int CMZIP_COL    	= 16;
	private final int CMPROV_COL    = 17;
	private final int CMCOUNTRY_COL = 18;
	private final int CURRENCY_COL  = 19;
	private final int CMADD3_COL    = 20;
	private final int CTRYNAME_COL  = 21;

	//Columns for cursor #2
	private final int PMNAME_COL	= 1;
	private final int DTDATE_COL 	= 2;
	private final int CONAME_COL 	= 3;
	private final int DTPRIC_COL 	= 4;
	private final int DTHOUR_COL 	= 5;
	private final int DTTOTA_COL 	= 6;
	private final int DTINVO_COL 	= 7;
	//Columns for cursor #3
	private final int RIINVO_COL	= 1;
	/*
	private final int RICOM1_COL 	= 2;
	private final int RICOM2_COL 	= 3;
	private final int RICOM3_COL 	= 4;
	private final int RICOM4_COL 	= 5;
	private final int RICOM5_COL 	= 6;
	private final int RICOM6_COL 	= 7;
	private final int RICOM7_COL 	= 8;
	*/
	//Columns for cursor #4
	/*
	private final int COCOM1_COL 	= 1;
	private final int COCOM2_COL 	= 2;
	private final int COCOM3_COL 	= 3;
	private final int COCOM4_COL 	= 4;
	private final int COCOM5_COL 	= 5;
	private final int COCOM6_COL 	= 6;
	private final int COCOM7_COL 	= 7;
	*/
	//Columns for cursor #5
	/*
	private final int CPADD1_COL 	= 1;
	private final int CPADD2_COL 	= 2;
	private final int CPCITY_COL 	= 3;
	private final int CPSTAT_COL 	= 4;
	private final int CPZIP_COL 	= 5;
	private final int CPPROV_COL 	= 6;
	private final int CPPHONE_COL 	= 7;
	private final int CPCOUNTRY_COL = 8;
	private final int CPADD3_COL 	= 9;
	private final int CNAME_COL 	= 10;
	*/
	
	private final int headerInfoCursor			= 1;
	private final int detailCursor 				= 2;
	private final int userCommentsCursor		= 3;
	private final int standardCommentsCursor	= 4;
	private final int companyInfoCursor			= 5;


	private boolean invoiceOnly = false;
	private double totalAmnt;
	private double[] totalHours;
	private String comments;

	private SPDBBean dataBean;

	private final float LEFT 	= -54f;
	private final float RIGHT 	= -54f;
	private final float TOP 	= 40f;
	private final float BOTTOM 	= 110f;

    public RegularInvoiceRptHelper(Rectangle pageSize) {
        super(pageSize);
		setMargins(LEFT, RIGHT, TOP, BOTTOM);
    }
    public RegularInvoiceRptHelper(
        Rectangle pageSize,
        float marginLeft,
        float marginRight,
        float marginTop,
        float marginBottom) {
        super(pageSize, marginLeft, marginRight, marginTop, marginBottom);
		setMargins(LEFT, RIGHT, TOP, BOTTOM);
    }
	public RegularInvoiceRptHelper(String fname) throws DocumentException
	{
		super(fname);
		setMargins(LEFT, RIGHT, TOP, BOTTOM);
	}
	public RegularInvoiceRptHelper(HttpServletRequest request) throws DocumentException {
        super(request);
        if( request.getParameter("maintenanceAction") != null && request.getParameter("maintenanceAction").equals("REPREPORT")){
	        invoiceOnly = true;
        }
		setMargins(LEFT, RIGHT, TOP, BOTTOM);
    }

private void addDetails(InvoiceDataTable table, int detailRow, int invoiceRow ) 
throws BadElementException, DocumentException, Exception
{
	String[] value = new String[6];
	value[0] = "";
	BigDecimal a = (BigDecimal)dataBean.valueAtColumnRowResult(DTDATE_COL,detailRow,detailCursor);
	value[1] = Format.dateBigDec_Str(a);
	value[2] = getTrimedStr((String)dataBean.valueAtColumnRowResult(CONAME_COL,detailRow,detailCursor));
	value[3] = Format.displayCurrency((BigDecimal)dataBean.valueAtColumnRowResult(DTPRIC_COL,detailRow,detailCursor));

	BigDecimal hour = (BigDecimal)dataBean.valueAtColumnRowResult(DTHOUR_COL,detailRow,detailCursor);
	totalHours[invoiceRow] += hour.doubleValue();
	value[4] = hour.toString();

	BigDecimal amount =	(BigDecimal)dataBean.valueAtColumnRowResult(DTTOTA_COL,detailRow,detailCursor);
	totalAmnt += amount.doubleValue();
	value[5] = Format.displayCurrency(amount);

	int[] aligns = {Element.ALIGN_CENTER,
				   Element.ALIGN_LEFT,
				   Element.ALIGN_LEFT,
				   Element.ALIGN_RIGHT,
				   Element.ALIGN_RIGHT,
				   Element.ALIGN_RIGHT,};
	table.printTblCells(value, aligns, 10f);

}
	private void addInvoiceTotalLine(InvoiceDataTable table, int row ) throws BadElementException,
    														DocumentException, Exception{
		BigDecimal sub =(BigDecimal)dataBean.valueAtColumnRowResult(HRTOTA_COL,row,headerInfoCursor);
		double subTotal = sub.doubleValue();
		BigDecimal tax =(BigDecimal)dataBean.valueAtColumnRowResult(HRTTAX_COL,row,headerInfoCursor);
		double totalTax = tax.doubleValue();
		double total = subTotal + totalTax;

		String subTotalStr = Format.displayCurrency(new BigDecimal(subTotal));
		String totalTaxStr = Format.displayCurrency(new BigDecimal(totalTax));
		String totalStr = Format.displayCurrency(new BigDecimal(total));


		ReportCell[] subTotalValue = {new ReportCell("", 4, 1),
									  new ReportCell(rb.getString("P12260015"), 1, 1, Element.ALIGN_RIGHT),
									  new ReportCell(subTotalStr, 1, 1, Element.ALIGN_RIGHT)
									 };

		ReportCell[] totalTaxValue = {new ReportCell("", 4, 1),
									  new ReportCell(rb.getString("P12260016"), 1, 1, Element.ALIGN_RIGHT),
									  new ReportCell(totalTaxStr, 1, 1, Element.ALIGN_RIGHT)
									 };

		ReportCell[] totalValue = {new ReportCell("", 4, 1),
									  new ReportCell(rb.getString("P12260017"), 1, 1, Element.ALIGN_RIGHT),
									  new ReportCell(totalStr, 1, 1, Element.ALIGN_RIGHT)
									 };

		table.printOutRow(subTotalValue, true, false);
		table.printOutRow(totalTaxValue, true, false);
		table.printOutRow(totalValue, true, false);
	}
	private void addProjectNameLine(InvoiceDataTable table, String projectName ) throws BadElementException,
    													DocumentException, Exception{
		ReportCell[] value = {new ReportCell(rb.getString("P12260018"), 1, 1, Element.ALIGN_LEFT),
						new ReportCell(projectName, 5, 1, Element.ALIGN_LEFT)
					   };
		table.printOutRow(value, true, false);
	}
	private void addProjectTotalLine(InvoiceDataTable table) throws BadElementException,
    														DocumentException, Exception{
		String totalValue = Format.displayCurrency(new BigDecimal(totalAmnt));
		ReportCell[] value = {new ReportCell("", 3, 1),
							new ReportCell(rb.getString("P12260019"), 2, 1, Element.ALIGN_RIGHT),
							new ReportCell(totalValue, 1, 1, Element.ALIGN_RIGHT)
						   };
		table.printOutRow(value, true, false);
	}
public InvoiceDataTable addReportHeader( String companyName, int row ) 
	throws BadElementException,DocumentException, Exception
{
	String[] invoiceInfo = new String[4];
	Vector info = new Vector();
	String country = getTrimedStr(dataBean.valueAtColumnRowResult(CMCOUNTRY_COL,row,headerInfoCursor));

	String a = getTrimedStr(dataBean.valueAtColumnRowResult(HRDATE_COL,row,headerInfoCursor));
	String b = getTrimedStr(dataBean.valueAtColumnRowResult(HRDDAT_COL,row,headerInfoCursor));

	invoiceInfo[0] = (((java.math.BigDecimal)dataBean.valueAtColumnRowResult(HRINVO_COL,row,headerInfoCursor)).toString()).trim();
	invoiceInfo[1] = Format.getFormatedDateStr(a);
	invoiceInfo[2] = getTrimedStr(dataBean.valueAtColumnRowResult(PPTEXA_COL,row,headerInfoCursor));
	invoiceInfo[3] = Format.getFormatedDateStr(b);

	info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CCNAME_COL,row,headerInfoCursor)));
	info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CMNAME_COL,row,headerInfoCursor)));
	info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD1_COL,row,headerInfoCursor)));
	if(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD2_COL,row,headerInfoCursor)).length()>0){
		info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD2_COL,row,headerInfoCursor)));
	}
	if(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD3_COL,row,headerInfoCursor)).length()>0){
		info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CMADD3_COL,row,headerInfoCursor)));
	}
	
	StringBuffer city = new StringBuffer();

	//city, state/province
	if(getTrimedStr(dataBean.valueAtColumnRowResult(CMSTAT_COL,row,headerInfoCursor)).length()>0)	
		city.append(getTrimedStr(dataBean.valueAtColumnRowResult(CMCITY_COL,row,headerInfoCursor))+ ", " +
					getTrimedStr(dataBean.valueAtColumnRowResult(CMSTAT_COL,row,headerInfoCursor))+	"  " +
					Format.displayZip( getTrimedStr(dataBean.valueAtColumnRowResult(CMZIP_COL,row,headerInfoCursor)), country) );
	else
		city.append(getTrimedStr(dataBean.valueAtColumnRowResult(CMCITY_COL,row,headerInfoCursor))+ ", " +
					getTrimedStr(dataBean.valueAtColumnRowResult(CMPROV_COL,row,headerInfoCursor))+	"  " +
					Format.displayZip( getTrimedStr(dataBean.valueAtColumnRowResult(CMZIP_COL,row,headerInfoCursor)), country) );

	info.addElement(city.toString());
	//03/10/09 Yan, do not show USA country name
	if(country.trim().equals("USA"))
		info.addElement(getTrimedStr(""));
	else
		info.addElement(getTrimedStr(dataBean.valueAtColumnRowResult(CTRYNAME_COL,row,headerInfoCursor)));
	info.trimToSize();

	String [] contactInfo = new String[info.size()];//(String [])info.toArray();
	for(int i=0; i <info.size(); i++){
		contactInfo[i] = (String)info.get(i);
	}

	InvoiceDataTable table = new InvoiceDataTable(rb);
	table.printInvoiceHeader(companyName, "", contactInfo, invoiceInfo, logoPath, "", useSSL, "");
	return table;
}
	public void addSummary(String companyName, int invoices, PdfWriter writer, String[] coInfo, String preComm)throws BadElementException,DocumentException, Exception {
		double totalInvoiceHours = 0.0d;
		double totalAmount = 0.0d;
		double totalTax = 0.0d;

		setPageLayout(companyName);
		resetPageCount();
		writer.setPageEvent(null);
		InvoicePageEvent event = new InvoicePageEvent(coInfo, preComm);
		writer.setPageEvent(event);
		
		newPage();
		writer.setPageEvent(null);
		String[] titles = new String[1];
		titles[0] = rb.getString("P12260000");

		float[] WIDTHS = {8f,8f,8f,25f,14f,10f,10f,17f};
		int numOfCols = WIDTHS.length;
		DBTable table = new DBTable(numOfCols, WIDTHS, null, null);
		table.setTableTitle(titles);
		addSpace(table, numOfCols);
		ReportCell[] header = { new ReportCell(rb.getString("P12260001"), 1, 1,  Element.ALIGN_LEFT),
								new ReportCell(rb.getString("P12260002"), 1, 1, Element.ALIGN_LEFT),
								new ReportCell(rb.getString("P12260003"), 1, 1, Element.ALIGN_LEFT),
								new ReportCell(rb.getString("P12260004"), 1, 1, Element.ALIGN_LEFT),
								new ReportCell(rb.getString("P12260005"), 1, 1, Element.ALIGN_LEFT),
								new ReportCell(rb.getString("P12260006"), 1, 1, Element.ALIGN_RIGHT),
								new ReportCell(rb.getString("P12260007"), 1, 1, Element.ALIGN_RIGHT),
								new ReportCell(rb.getString("P12260008"), 1, 1, Element.ALIGN_RIGHT)
							   };
		table.printOutRow(header, m_fontContentH, BGCELL);
		table.endHeaders();

		int preInvoNum = -1;
		for ( int i=0; i<invoices; i++ ) {
			int invoNum = ((BigDecimal)dataBean.valueAtColumnRowResult(
										HRINVO_COL, i, headerInfoCursor)).intValue();
			if ( invoNum == preInvoNum ) {
				continue;
			}
			preInvoNum = invoNum;

			totalInvoiceHours += totalHours[i];
			String invDate = Format.getFormatedDateStr((String)dataBean.valueAtColumnRowResult(HRDATE_COL,i,headerInfoCursor));
			String dueDate = Format.getFormatedDateStr((String)dataBean.valueAtColumnRowResult(HRDDAT_COL,i,headerInfoCursor));
			String custName = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(CMNAME_COL,i,headerInfoCursor));
			String payTerm = ERPValue.getDBStr((String)dataBean.valueAtColumnRowResult(PPTEXA_COL,i,headerInfoCursor));
			String hours = Format.formatDecimal(String.valueOf(totalHours[i]));

			BigDecimal tax  = (BigDecimal)dataBean.valueAtColumnRowResult(HRTTAX_COL,i,headerInfoCursor);
			totalTax += tax.doubleValue();
			String taxes = Format.displayCurrency(tax);

			BigDecimal amount = (BigDecimal)dataBean.valueAtColumnRowResult(HRTOTA_COL,i,headerInfoCursor);
			totalAmount += amount.doubleValue() + totalTax;
			String amnt = Format.displayCurrency(amount);

			ReportCell[] content = { new ReportCell(String.valueOf(invoNum), 1, 1,  Element.ALIGN_LEFT),
									new ReportCell(invDate, 1, 1,  Element.ALIGN_LEFT),
									new ReportCell(dueDate, 1, 1,  Element.ALIGN_LEFT),
									new ReportCell(custName, 1, 1,  Element.ALIGN_LEFT),
									new ReportCell(payTerm, 1, 1,  Element.ALIGN_LEFT),
									new ReportCell(hours, 1, 1,  Element.ALIGN_RIGHT, true),
									new ReportCell(taxes, 1, 1,  Element.ALIGN_RIGHT,true),
									new ReportCell(amnt, 1, 1,  Element.ALIGN_RIGHT,true)
							   		};
			table.printOutRow(content, m_fontContent);
			}
			ReportCell[] content = { new ReportCell(rb.getString("P12260009") + " ", 2, 1, Element.ALIGN_RIGHT),
									new ReportCell(Format.formatDecimal(String.valueOf(totalInvoiceHours)), 4, 1, Element.ALIGN_RIGHT, true),
									new ReportCell(Format.displayCurrency(new BigDecimal(totalTax)), 1, 1, Element.ALIGN_RIGHT, true),
									new ReportCell(Format.displayCurrency(new BigDecimal(totalAmount)), 1, 1, Element.ALIGN_RIGHT, true)
							   		};
			table.printOutRow(content, m_fontContent);
			add(table);

	}
	private void addTblHeader(InvoiceDataTable table, int row) throws BadElementException,
    										DocumentException, Exception {


		String currencyName = getTrimedStr(dataBean.valueAtColumnRowResult(CURRENCY_COL,row,headerInfoCursor));
		ReportCell[] currency = {new ReportCell("", 3, 1),
								 new ReportCell(currencyName, 3, 1, Element.ALIGN_RIGHT)
								};
		ReportCell[] headerText = {new ReportCell("", 1, 1),
								new ReportCell(rb.getString("P12260010"), 1, 1, Element.ALIGN_LEFT),
								new ReportCell(rb.getString("P12260011"), 1, 1, Element.ALIGN_LEFT),
								new ReportCell(rb.getString("P12260012"), 1, 1),
								new ReportCell(rb.getString("P12260013"), 1, 1),
								new ReportCell(rb.getString("P12260014"), 1, 1)
						   	};

		table.printOutRow(currency, true, false);
		table.printOutRow(headerText, true, true);
	}
	public void buildPDFInvoice(String companyName)throws BadElementException, DocumentException, Exception 
	{
		int numOfInvoices = dataBean.RowCountResult(headerInfoCursor);
		int numOfDetails = dataBean.RowCountResult(detailCursor);
		int numOfUserComments = dataBean.RowCountResult(userCommentsCursor);

		comments = "";
		String comInfo[] = getComInfo();
		String compComm = getStandardComments();
		PdfWriter writer = getWriter();
		String prevComm = "";

		String preProjName;
		totalHours = new double[numOfInvoices];
		float[] width = {16f,16f,28f,10f,10f,16f};

		int previousInvoNum = -1;
		for( int row=0; row<numOfInvoices; row++ ) {
			int headerInvoNum = ((BigDecimal)dataBean.valueAtColumnRowResult(HRINVO_COL,row, headerInfoCursor)).intValue();

			if ( headerInvoNum == previousInvoNum ) {
				continue;
			}
			previousInvoNum = headerInvoNum;

			InvoiceDataTable table = new InvoiceDataTable(6);
			table.setWidths(width);
			addTblHeader(table, row);
			table.endHeaders();

			//build a new header for each invoice.
			Phrase p = new Phrase();
			p.add(addReportHeader(companyName, row));

			HeaderFooter header = new HeaderFooter(p, false);
			header.setBorder(Rectangle.NO_BORDER);
			header.setAlignment(Element.ALIGN_CENTER);

			HeaderFooter footer = new HeaderFooter(new Phrase(""), false);
			footer.setAlignment(Rectangle.ALIGN_CENTER);
			footer.setBorder(Rectangle.NO_BORDER);
			setFooter(footer);

			boolean hasUserComments = false;

			for ( int j=0; j<numOfUserComments; j++ ) {

				int userCommentsInvoNum = ((BigDecimal)dataBean.valueAtColumnRowResult(RIINVO_COL, j, userCommentsCursor)).intValue();
				if ( userCommentsInvoNum == headerInvoNum ) {
					comments = getUserComments(j);
					hasUserComments = true;
				}
			}

			if ( !hasUserComments ) {
				
				comments = compComm;
			}

			//set footer contents for each invoice
			//InvoicePageEvent event = new InvoicePageEvent(comInfo, comments);
			//writer.setPageEvent(event);

			if ( row == 0 ) {
				// we set header for the first invoice, then open the document
				//to accept content of invoice.

				//set header
				setHeader(header);
				open();
			}else {
				//We set a new header for each new invoice.
				resetHeader();
				setHeader(header);
			}
			
			if ( row == 0 )
			{
				writer.setPageEvent(null);
				InvoicePageEvent event = new InvoicePageEvent(comInfo, prevComm);
				writer.setPageEvent(event);
			}	

			preProjName = "";
			newPage();

			for ( int i=0; i<numOfDetails; i++ ) {
				
				int detailInvoNum = ((BigDecimal)dataBean.valueAtColumnRowResult(
																DTINVO_COL,i, detailCursor)).intValue();

				if ( headerInvoNum == detailInvoNum ) {

					String projName = getTrimedStr(dataBean.valueAtColumnRowResult(PMNAME_COL,i, detailCursor));
					if ( preProjName.equals("")) {
						 // The first project in the invoice, we just print out project name line
						 addProjectNameLine(table, projName);
						 preProjName = projName;
					}else if ( !preProjName.equals(projName) ) {
						//The other projects. If we got here, it means a project ends.
						//we print out total line for each project and start next project with
						//printing out its project name line.
						preProjName = projName;
						addProjectTotalLine(table);
						totalAmnt = 0.0d;
						addProjectNameLine(table, projName);
					}

					//add content to invoice
					addDetails(table, i, row);
				}

				if ( i == numOfDetails -1 ) {
					//last item in the loop, we print out total line for last project.
					addProjectTotalLine(table);
					totalAmnt = 0.0;
				}

			}
			//add a total amount for each invoice.
			addInvoiceTotalLine(table, row);
		    add(table);
		    prevComm = comments;
		}

		if(!invoiceOnly){
			resetHeader();
			resetFooter();
			setPageSize(PageSize.A4.rotate());
			setMargins(30f, 30f, 40f, 40f);
			setRotation(false);

			addSummary(companyName, numOfInvoices, writer, comInfo, prevComm);
		}
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
private String getStandardComments()throws BadElementException, DocumentException, Exception
{
	String standardComments = "";
	
	if(dataBean.RowCountResult(standardCommentsCursor) ==0)
	{
		return standardComments;
	}
	else
	{	
		standardComments = (String)dataBean.valueAtColumnRowResult(1, 0, standardCommentsCursor);
	}

	return standardComments;
}
	private String getUserComments( int row )throws BadElementException, DocumentException, Exception 
	{
		StringBuffer userComments = new StringBuffer();
		
		for(int i=0; i< 7; i++) {
			//do not trim comments here!
			userComments.append(((String)dataBean.valueAtColumnRowResult(i+2, row, userCommentsCursor)));
		}
		return userComments.toString();

		/*
		String[] userComments = new String[7];

		for(int i=0; i<userComments.length; i++) {
			//do not trim comments here!
			userComments[i] = (String)dataBean.valueAtColumnRowResult(i+2, row, userCommentsCursor);
		}
		return userComments;
		*/
	}
    public void setDBBean( SPDBBean dataBean ) {
	    this.dataBean = dataBean;
    }
}
