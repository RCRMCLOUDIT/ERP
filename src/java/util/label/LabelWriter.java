package com.cap.util.label;

/*
 * Created on Dec 13, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author JunpingZhang
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
//import java.io.IOException;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class LabelWriter {


	private Rectangle pageSize;
	private int startIndex;
	//private int numberOfLabels;
	//private LabelHeader labelHeader;
	//private LabelBody labelBody;
	private int bodyColumn=5;
	private int headerColumn=4;
	


	LabelFormat format;

	public LabelWriter(Rectangle pageSize, int startIndex)
	{
		this.pageSize = pageSize;
		this.startIndex = startIndex;
		this.format = new LabelFormat(pageSize);
		
//		this.format.setLeftMargin(lMargin);
//		this.format.setRightMargin(rMargin);
//		this.format.setTopMargin(tMargin);
//		this.format.setBottomMargin(bMargin);
	}
		
	//generate PdfPtable based on the header information
	
	private PdfPTable labelHeaderTableGenerater(LabelHeader header) {
		
		PdfPCell companyAddress_cell = new PdfPCell();		
		PdfPCell companyName_cell = new PdfPCell();		
		PdfPCell companyLogo_cell = new PdfPCell();	

//		format.setLabelWidths(size);
	
		PdfPCell blank_cell = new PdfPCell(new Phrase(""));
		blank_cell.setBorder(Rectangle.NO_BORDER);
	
		PdfPTable headerTable = new PdfPTable(headerColumn);	
			
		try {
		// Company Name
		companyName_cell = new PdfPCell(new Phrase(header.getCompanyName(), format.companyName_font));
		companyName_cell.setColspan(2);
		companyName_cell.setNoWrap(true);
		companyName_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		companyName_cell.setBorder(Rectangle.NO_BORDER);
		companyName_cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		companyName_cell.setFixedHeight((float) (format.headerHeight*.25));
	
		// Company Address
		companyAddress_cell =
		   new PdfPCell(new Phrase(header.getCompanyAddress().getAddrAsLabel(), format.companyAddress_font));
		companyAddress_cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		companyAddress_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		companyAddress_cell.setBorder(Rectangle.BOTTOM);
		companyAddress_cell.setBorderWidth(2);
		companyAddress_cell.setLeading(5f,1f);
		companyAddress_cell.setFixedHeight((float) (format.headerHeight*.75));
           
		if (header.getLogoURL() == null) {
			companyLogo_cell = new PdfPCell(new Phrase(""));
		}
		else {
			try {
				Image companyLogo = Image.getInstance(header.getLogoURL());
				companyLogo.scaleToFit((float) (format.tableWidth*.45), (float) (format.headerHeight*.65));
				companyLogo_cell = new PdfPCell(companyLogo);
				companyLogo.setAlignment(Element.ALIGN_MIDDLE);
				} catch (Exception e_) {
				companyLogo_cell = new PdfPCell(new Phrase(""));
				System.out.println("PDF Logo rejected.");
				}
		}

		companyLogo_cell.setBorderWidth(2);
		companyLogo_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		companyLogo_cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		companyLogo_cell.setFixedHeight((float) ((format.headerHeight)*.75));
		companyLogo_cell.setBorder(Rectangle.BOTTOM);
	
		headerTable.setWidths(format.headerWidths);
		headerTable.setTotalWidth(format.tableWidth);
	
		headerTable.addCell(blank_cell);
		headerTable.addCell(companyName_cell);
		headerTable.addCell(blank_cell);
		headerTable.addCell(blank_cell);
		headerTable.addCell(companyLogo_cell);
		headerTable.addCell(companyAddress_cell);
		headerTable.addCell(blank_cell);
			
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (Exception ioe1) {
			System.err.println(ioe1.getMessage());
		}
		return headerTable;
	}
	
	//generate PdfPtable based on the body information

	private PdfPTable labelBodyTableGenerater(LabelBody body) {
		
		PdfPCell toAddress_cell = new PdfPCell();		
		PdfPCell to_cell = new PdfPCell();		
		PdfPCell labelCount_cell = new PdfPCell();		

		PdfPCell blank_cell = new PdfPCell(new Phrase(""));
		blank_cell.setBorder(Rectangle.NO_BORDER);

		PdfPTable bodyTable = new PdfPTable(bodyColumn);
			
		try {
	
			String toAddr, orderInfo;
			toAddr = 	body.getAttention() + "\n" + body.getToCompanyName() + "\n" + 
						body.getToAddress().getAddrAsLabel();  

			orderInfo = body.getPackInfo().getPackInfoAsLabel();

			String labelIndex = body.getIndex() + " Of " + body.getCount();	
		// customer "To"
		to_cell = new PdfPCell(new Phrase("To:", format.toName_font));
		to_cell.setVerticalAlignment(Element.ALIGN_TOP);
		to_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		to_cell.setBorder(Rectangle.NO_BORDER);
		to_cell.setFixedHeight(format.bodyHeight);

		//customer address
		Phrase addr = new Phrase(new Chunk(toAddr, format.toAddress_font));
		addr.add(new Chunk("\n" + orderInfo, format.orderInfo_font));	
		toAddress_cell = new PdfPCell(addr);
		
		toAddress_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		toAddress_cell.setVerticalAlignment(Element.ALIGN_TOP);
		toAddress_cell.setBorder(Rectangle.NO_BORDER);
		toAddress_cell.setFixedHeight(format.bodyHeight);
		toAddress_cell.setLeading(2f,1f);
		
		labelCount_cell = new PdfPCell(new Phrase(labelIndex, format.toAddress_font));
		labelCount_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		labelCount_cell.setVerticalAlignment(Element.ALIGN_TOP);
		labelCount_cell.setBorder(Rectangle.NO_BORDER);
		labelCount_cell.setFixedHeight(format.bodyHeight);
		labelCount_cell.setLeading(2f,1f);

		bodyTable.setWidths(format.bodyWidths);
		bodyTable.setTotalWidth(format.tableWidth);
		bodyTable.addCell(blank_cell);
		bodyTable.addCell(to_cell);
		bodyTable.addCell(toAddress_cell);
		bodyTable.addCell(labelCount_cell);
		bodyTable.addCell(blank_cell);

	
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (Exception ioe1) {
			System.err.println(ioe1.getMessage());
		}
		return bodyTable;
	}

//		public void generate(LabelHeader[] h, LabelBody[] b)
		public void generate(ArrayList h, ArrayList b)
		{
			

			//int total = b.size();
			Iterator eh = h.iterator();
			Iterator eb = b.iterator();

			Document document = new Document(pageSize, format.leftMargin, format.rightMargin, 
											 format.topMargin,format.bottomMargin);
			
			
//			ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
			try {
				
				//PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("10.pdf"));
				ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
				PdfWriter writer = PdfWriter.getInstance(document, pdfStream);
				document.open();
				int k = 0;
//				for (int k = 0; k<total; k++){			
				while (eb.hasNext()){
					PdfPTable addressLabel = new PdfPTable(1);
	
					addressLabel.setTotalWidth(format.tableWidth);
					
					PdfPCell headerTable_cell = new PdfPCell(this.labelHeaderTableGenerater((LabelHeader) eh.next()));
					PdfPCell bodyTable_cell = new PdfPCell(this.labelBodyTableGenerater((LabelBody) eb.next()));

					headerTable_cell.setBorder(Rectangle.NO_BORDER);
					bodyTable_cell.setBorder(Rectangle.NO_BORDER);
	
					addressLabel.addCell(headerTable_cell);
					addressLabel.addCell(bodyTable_cell);
					
					addressLabel.setSpacingBefore(0f);
					addressLabel.setSpacingAfter(0f);	
					addressLabel.setLockedWidth(true);
					
									
	//				for (int i = startIndex; i <= numberOfLabels + startIndex; i++){ 
						int p = ( k + startIndex)%6;
						if ( p == 0){
							addressLabel.writeSelectedRows(0, -1, format.xPosition(pageSize, 6), format.yPosition(pageSize, 6), writer.getDirectContent());
							document.newPage();
						}
						else {
							addressLabel.writeSelectedRows(0, -1, format.xPosition(pageSize, p), format.yPosition(pageSize, p), writer.getDirectContent());
							}
					k++; 
				}				
	
			} catch(DocumentException de) {
				System.err.println(de.getMessage());
			} /*catch(IOException ioe) {
				System.err.println(ioe.getMessage());
			}*/
		document.close();
//	return pdfStream;
	}
	
	public Document generate(ArrayList h, ArrayList b, ByteArrayOutputStream pdfStream)
	{

		//int total = b.size();
		Iterator eh = h.iterator();
		Iterator eb = b.iterator();

		Document document = new Document(pageSize, format.leftMargin, format.rightMargin, 
										format.topMargin,format.bottomMargin);
		try {
				
			PdfWriter writer = PdfWriter.getInstance(document, pdfStream);
			document.open();
			int k = 0;
	
			while (eb.hasNext()){
				PdfPTable addressLabel = new PdfPTable(1);
				addressLabel.setTotalWidth(format.tableWidth);
					
				PdfPCell headerTable_cell = new PdfPCell(this.labelHeaderTableGenerater((LabelHeader) eh.next()));
				PdfPCell bodyTable_cell = new PdfPCell(this.labelBodyTableGenerater((LabelBody) eb.next()));
	
				headerTable_cell.setBorder(Rectangle.NO_BORDER);
				bodyTable_cell.setBorder(Rectangle.NO_BORDER);

//				addressLabel.addCell(bodyTable_cell);
				addressLabel.addCell(headerTable_cell);
				addressLabel.addCell(bodyTable_cell);
				addressLabel.setSpacingBefore(0f);
				addressLabel.setSpacingAfter(0f);	
				addressLabel.setLockedWidth(true);
					
				int p = ( k + startIndex)%6;
				if ( p == 0){
					addressLabel.writeSelectedRows(0, -1, format.xPosition(pageSize, 6), format.yPosition(pageSize, 6), writer.getDirectContent());
					document.newPage();
				}else {
					addressLabel.writeSelectedRows(0, -1, format.xPosition(pageSize, p), format.yPosition(pageSize, p), writer.getDirectContent());
				}
				k++; 
			}
			
			document.close();				
			return document;
		
		} catch(DocumentException de) {
			System.err.println(de.getMessage());
		} 
		
		return document;

		}	

//		public void generate(LabelHeader[] h, LabelBody[] b)
		public void generate(LabelHeader h, ArrayList b)
		{
			

			//int total = b.size();
			Iterator eb = b.iterator();

//			int total = h.length;
/*			Object[] hh = null;
			Object[] bb = null;

			hh =  h.toArray();
			bb =  b.toArray();
*/
			Document document = new Document(pageSize, format.leftMargin, format.rightMargin, 
											 format.topMargin,format.bottomMargin);
			
			
//			ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
			try {
				
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("11.pdf"));
				document.open();
				int k = 0;
//				for (int k = 0; k<total; k++){			
				while (eb.hasNext()){
					PdfPTable addressLabel = new PdfPTable(1);
	
					addressLabel.setTotalWidth(format.tableWidth);
					
					PdfPCell headerTable_cell = new PdfPCell(this.labelHeaderTableGenerater(h));
					PdfPCell bodyTable_cell = new PdfPCell(this.labelBodyTableGenerater((LabelBody) eb.next()));
	
					headerTable_cell.setBorder(Rectangle.NO_BORDER);
					bodyTable_cell.setBorder(Rectangle.NO_BORDER);
	
					addressLabel.addCell(headerTable_cell);
					addressLabel.addCell(bodyTable_cell);
					
					addressLabel.setSpacingBefore(0f);
					addressLabel.setSpacingAfter(0f);	
					addressLabel.setLockedWidth(true);
					
									
	//				for (int i = startIndex; i <= numberOfLabels + startIndex; i++){ 
						int p = ( k + startIndex)%6;
						if ( p == 0){
							addressLabel.writeSelectedRows(0, -1, format.xPosition(pageSize, 6), format.yPosition(pageSize, 6), writer.getDirectContent());
							document.newPage();
						}
						else {
							addressLabel.writeSelectedRows(0, -1, format.xPosition(pageSize, p), format.yPosition(pageSize, p), writer.getDirectContent());
							}
					k++; 
				}				
	
			} catch(DocumentException de) {
				System.err.println(de.getMessage());
			} catch(IOException ioe) {
				System.err.println(ioe.getMessage());
			}
		document.close();
//	return pdfStream;
	}

//		public void generate(LabelHeader[] h, LabelBody[] b)
		public void generate(LabelBody b, int repeat)
		{
			

			Document document = new Document(pageSize, format.leftMargin, format.rightMargin, 
											 format.topMargin,format.bottomMargin);
			
			
//			ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
			try {
				
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("12.pdf"));
				document.open();

				PdfPTable addressLabel = new PdfPTable(1);
	
				addressLabel.setTotalWidth(format.tableWidth);
					
				addressLabel = this.labelBodyTableGenerater(b);
	
				addressLabel.setLockedWidth(true);
	
				for (int k = 0; k<repeat; k++){			

				
									
	//				for (int i = startIndex; i <= numberOfLabels + startIndex; i++){ 
						int p = ( k + startIndex)%12;
System.out.println("p=" + p );
						if ( p == 0){
							addressLabel.writeSelectedRows(0, -1, format.xPosition1(pageSize, 12), format.yPosition1(pageSize, 12), writer.getDirectContent());
							document.newPage();
						}
						else {
							addressLabel.writeSelectedRows(0, -1, format.xPosition1(pageSize, p), format.yPosition1(pageSize, p), writer.getDirectContent());
							}
				}				
	
			} catch(DocumentException de) {
				System.err.println(de.getMessage());
			} catch(IOException ioe) {
				System.err.println(ioe.getMessage());
			}
		document.close();
//	return pdfStream;
	}






}