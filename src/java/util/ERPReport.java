package com.cap.util;

import java.io.*;
import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.ResourceBundle;
import java.awt.Color;
import javax.servlet.http.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.cap.portal.PortalUser;
import com.cap.erp.SPDBBean;
import com.cap.erp.report.DBTable;

public class ERPReport extends Document {

	private final float LEFT 	= 0f;
	private final float RIGHT 	= 0f;
	private final float TOP 	= 54f;
	private final float BOTTOM 	= 54f;

	protected final int m_font = Font.HELVETICA;
	protected final Color BORDER = new Color(0x00, 0x00, 0x00);
	protected final Color BGPAGE = new Color(0xFF, 0xFF, 0xFF);
	protected final Color FRONT = new Color(0x00, 0x00, 0x00);
	protected final Color BGCELL = new Color(0xDA, 0xDB, 0xDC);
	protected final Font m_fontTitle0 = new Font(m_font, 14, Font.BOLD, FRONT);
	protected final Font m_fontTitle1 = new Font(m_font, 14, Font.BOLD, FRONT);
	protected final Font m_fontTitle2 = new Font(m_font, 12, Font.BOLD, FRONT);
	protected final Font m_fontTitle3 = new Font(m_font, 12, Font.BOLD, FRONT);
	protected final Font m_fontTitle4 = new Font(m_font, 12, Font.BOLD, FRONT);
	protected final Font m_fontContent = new Font(m_font, 9, Font.NORMAL, FRONT);
	protected final Font m_fontContentH = new Font(m_font, 10, Font.BOLD, FRONT);
	protected final java.util.Date m_dateTime = new java.util.Date();

	//private final String LOGOIMG = "/erp/ERP_COMMON/images/iCAP_mov.jpg";
	//private final String LINEIMG = "erp/ERP_COMMON/images/lline.jpg";
	//private final String THINLINEIMG = "erp/ERP_COMMON/images/thinLine.jpg";

	//private final String LOGOIMG = "/PortalCompPDFLogo/default.gif";
	private String LOGOIMG = "/PortalCompPDFLogo/default.gif";
	private final String LINEIMG = "/PortalCompPDFLogo/lline.jpg";
	private final String THINLINEIMG = "/PortalCompPDFLogo/thinLine.jpg";
	public ResourceBundle rb = null;
	private ByteArrayOutputStream pdfStream = null;
	private FileOutputStream pdfStreamF = null;

	private PdfWriter writer = null;

	protected String logoPath;
	protected String logoPathRep;
	protected String useSSL;
	
	protected SPDBBean dataBean;
	private String linePath;
	private String thinLinePath;
	private boolean m_isPortrait = true;

	/**
	 * Constructor for ERPReport
	 */

	public ERPReport() {}
	/**
	 * Constructor for ERPReport
	 */
	public ERPReport(Rectangle pageSize) {
		super(pageSize);
	}
	/**
	 * Constructor for ERPReport
	 */
	public ERPReport(
		Rectangle pageSize,
		float marginLeft,
		float marginRight,
		float marginTop,
		float marginBottom) {
		super(pageSize, marginLeft, marginRight, marginTop, marginBottom);
	}
	/**
	* CheckBook constructor comment.
	*/
	public ERPReport(String fname) throws DocumentException
	{
		super(PageSize.A4);

		this.setMargins(LEFT, RIGHT, TOP, BOTTOM);

		try {

			pdfStreamF = new FileOutputStream(fname);
			writer = PdfWriter.getInstance(this, pdfStreamF);
		}
		catch (Exception ioe)
		{
			System.err.println(ioe.getMessage());
			throw new DocumentException("Create PDF stream or PdfWriter object failed.");
		}


	}
	public ERPReport(HttpServletRequest request) throws DocumentException {
		super(PageSize.A4);

		String root = "";
		HttpSession session = request.getSession(false);
		
		PortalUser user = (PortalUser)session.getAttribute("portal_PortalUser");
		logoPath = user.getPDFLogo();
		logoPathRep = user.getRepPDFlogo();
		rb = user.getResourceBundle();
		useSSL = user.getUseSSL();
		
		linePath = root + LINEIMG;
		thinLinePath = root + THINLINEIMG;

		this.setMargins(LEFT, RIGHT, TOP, BOTTOM);
		try {

			pdfStream = new ByteArrayOutputStream();
			writer = PdfWriter.getInstance(this, pdfStream);
		}
		catch (Exception ioe)
		{
			System.err.println(ioe.getMessage());
			throw new DocumentException("Create PDF stream or PdfWriter object failed.");
		}
	}

	public void addLine()throws BadElementException, DocumentException, Exception {
		Image img = null;
		if(useSSL.equals("Y"))
			img = Image.getInstance(new URL("https://"+ConstantValue.SERVER_IP+ linePath));
		else
			img = Image.getInstance(new URL("http://"+ConstantValue.SERVER_IP+ linePath));
		ERPDataTable line = new ERPDataTable(3);
		line.setPicture(img, 5);
		add(line);
	}
	public void addNewLine(float leading) throws Exception {
		add(new Phrase(leading, "\n"));
	}
	public void addNewLines( int numOfLines ) throws BadElementException,DocumentException, Exception {
		ERPDataTable table = new ERPDataTable(1);
		float leading = 5f * (float)numOfLines;
		Cell cell = new Cell("");
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setLeading(leading);
		table.addCell(cell);
		add(table);
	}
	public void addNoDataBlock(String field)throws BadElementException, DocumentException, Exception {
		ERPDataTable block = new ERPDataTable(1);
		Cell cell = new Cell("No Data in " + field);
		cell.setBorder(Rectangle.NO_BORDER);
		block.addCell(cell);
		add(block);
	}
	protected void addSpace(DBTable table, int numOfCols) throws BadElementException{
		Cell empty = new Cell(new Chunk(" ", m_fontContentH));
		empty.setColspan(numOfCols);
		empty.setBorderWidth(0);
		empty.setLeading(20f);
		table.addCell(empty);
	}
	public void addThinLine()throws BadElementException, DocumentException, Exception {
		Image img = null;
		if(useSSL.equals("Y"))
			img = Image.getInstance(new URL("https://"+ConstantValue.SERVER_IP+ thinLinePath));
		else
			img = Image.getInstance(new URL("http://"+ConstantValue.SERVER_IP+ thinLinePath));
			
		ERPDataTable line = new ERPDataTable(3);
		line.setPicture(img, 5);
		add(line);
	}
	public void addUserDefinedLine(String line)throws BadElementException, DocumentException, Exception {
		ERPDataTable table = new ERPDataTable(1);
		Cell cell = new Cell(line);
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);
		add(table);
	}
	//It should be define as ABSTRACT method.
	//We defined an empty definition body method here duo to some class already use this object without using this method.
	public void buildPDFInvoice(String companyName)throws BadElementException, DocumentException, Exception
	{
	}
	public void buildPDFInvoice(String companyName, String opt)throws BadElementException, DocumentException, Exception
	{
	}
	/*
	//It should be define as ABSTRACT method.
	//We defined an empty definition body method here duo to some class already use this object without using this method.
	public void buildPDFInvoice(String companyName,ResourceBundle rb)throws BadElementException,
															DocumentException, Exception
	{
	}
	//It should be define as ABSTRACT method.
	//We defined an empty definition body method here duo to some class already use this object without using this method.
	public void buildPDFInvoice(String companyName,String printOpt,ResourceBundle rb)throws BadElementException,
															DocumentException, Exception
	{
	}
	*/	
	/**
	* Gets a reference to the output stream used by the report.
	*
	* @param	none
	* @return	<CODE>ByteArrayOutputStream</CODE>
	*/

	public ByteArrayOutputStream getStream()
	{
		return pdfStream;
	}
	public FileOutputStream getStreamF()
	{
		return pdfStreamF;
	}
	protected String getTrimedStr( Object originalStr ) {
		if ( originalStr == null ) {
			return "";
		}
		String returnStr = (String)originalStr;
		return Format.decodeHTML(returnStr.trim());
	}
	public PdfWriter getWriter()
	{
		return writer;
	}
	public void setDBBean( SPDBBean dataBean ) {
		this.dataBean = dataBean;
	}
	public void setPageLayout(String header) {
		setPageLayout(header, null);
	}
	public void setPageLayout(String headerTitle, String footerTitle) {

		//Adding a page Header
		Chunk c1 = new Chunk(headerTitle, m_fontTitle2);
		Phrase p = new Phrase(c1);
		HeaderFooter header = new HeaderFooter(p, false);
		header.setAlignment(Element.ALIGN_LEFT);
		header.setBorder(Rectangle.BOTTOM);
		//header.setBorderColor(BORDER);
		setHeader(header);

		Phrase p1 = new Phrase("", m_fontContent);
		String footerStr = null;
		if ( footerTitle != null ) {
			footerStr = footerTitle;
		}
		else {
			footerStr = "                      ";
		}
		Chunk c2 = new Chunk(footerStr, m_fontContent);
		Chunk c3 = null;
		if (m_isPortrait)
		{
			c3 = new Chunk("                                                     " +
			"                                                                      " +
			"                                                     ", m_fontContent);
		}
		else
		{
			c3 = new Chunk("                                                     " +
			"                                                                      " +
			"                                                                      " +
			"                                                                      ", m_fontContent);
		}
		Chunk c4 = new Chunk("Page   ", m_fontContent);
		p1.add(c2);
		p1.add(c3);
		p1.add(c4);
		HeaderFooter footer = new HeaderFooter(p1, true);
		footer.setAlignment(Element.ALIGN_LEFT);
		footer.setBorder(Rectangle.TOP);
		//footer.setBorderColor(m_border);
		setFooter(footer);
	}
	public void setRotation(boolean isPortrait) {
		this.m_isPortrait = isPortrait;
	}
}
