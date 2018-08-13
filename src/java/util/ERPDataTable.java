package com.cap.util;

//import java.util.Properties;
import java.awt.Color;
import com.lowagie.text.*;
import com.cap.erp.report.ReportCell;


public class ERPDataTable extends Table {
	protected Font title1F			= FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
	protected Font title2F			= FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
	protected Font title3F			= FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);
	protected Font timeStampF	   	= FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);
	protected Font tblHeaderF	   	= FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
	protected Font labelF		   	= FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
	protected Font dataCellF	   	= FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL);
	protected Font headerBF		   	= FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
	protected Font headerF	   		= FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL);

	protected Color BGCELL = new Color(218, 219, 220);

	protected int dimension;
	protected int tblWidth;

	/**
	 * Constructor for EPRDataTable
	 */
	public ERPDataTable(int columns) throws BadElementException {
		super(columns);
		this.dimension = columns;
		setPadding(1f);
		setSpacing(1f);
		setBorder(Rectangle.NO_BORDER);
	}
	/**
	 * Constructor for EPRDataTable
	 */
	public ERPDataTable(int columns, int rows) throws BadElementException {
		super(columns, rows);
	}
	/**
	 * Constructor for EPRDataTable
	 */
	/*
	public ERPDataTable(Properties attributes) {
		super(attributes);
	}
	*/
	public void printAmountCount(String[] left, String[] right, float leading) throws Exception {
		if (left.length != dimension || right.length != dimension)
			throw new Exception("String array passed in is overflow.");
		Cell cell = null;
		Chunk chunk = null;
		cell = new Cell(new Chunk("", labelF));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setLeading(leading);
		addCell(cell);
		cell = new Cell(new Chunk("Count", labelF));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setLeading(leading);
		addCell(cell);
		cell = new Cell(new Chunk("Amount", labelF));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setLeading(leading);
		addCell(cell);
		for (int j = 0; j < dimension - 3; j++)
		{
			cell = new Cell(new Chunk("", labelF));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setLeading(leading);
			addCell(cell);
		}

		int align;
		for (int i = 0; i < dimension; i++) {
			if (i == 1 || i == 2 || i == 4 || i == 6){
				chunk = new Chunk(left[i], dataCellF);
				align = Element.ALIGN_RIGHT;
			}else {
				chunk = new Chunk(left[i], labelF);
				align = Element.ALIGN_LEFT;
			}
			cell = new Cell(chunk);
			cell.setHorizontalAlignment(align);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setLeading(leading);
			addCell(cell);
		}
		for (int k = 0; k < right.length; k++) {
			if (k == 1 || k == 2 || k == 4 || k == 6) {
				chunk = new Chunk(right[k], dataCellF);
				align = Element.ALIGN_RIGHT;
			}else {
				chunk = new Chunk(right[k], labelF);
				align = Element.ALIGN_LEFT;
			}
			cell = new Cell(chunk);
			cell.setHorizontalAlignment(align);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setLeading(leading);
			addCell(cell);
		}
	}
	public void printNameValuePair(String name, int nameAlign, String value, int valueAlign, float leading) throws Exception {
		Cell cell = null;
		Chunk chunk = null;

		//add a name cell to table
		chunk = new Chunk(name, headerBF);
		cell = new Cell(chunk);
		cell.setHorizontalAlignment(nameAlign);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setLeading(leading);
		addCell(cell);

		//add a value cell to table
		chunk = new Chunk(value, headerF);
		cell = new Cell(chunk);
		cell.setHorizontalAlignment(valueAlign);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setLeading(leading);
		addCell(cell);

	}
	public void printOutNameValuePaire(ReportCell[] names, Font nameF, ReportCell[] values, Font valueF)
		throws BadElementException
	{
		int i = names.length;
		for(int j = 0; j < i; j++)
		{
			Cell cell = names[j].getFilledCell(nameF);
			cell.setLeading(5F);
			addCell(cell);
			cell = values[j].getFilledCell(valueF);
			cell.setLeading(5F);
			addCell(cell);
		}

	}
	public void printOutRow(ReportCell[] values, boolean isBoldFont, boolean isHeader)
		throws BadElementException
	{
		Font font = null;
		Color color = null;
		if( isHeader )
		{
			if( isBoldFont )
			{
				font = headerBF;
				color = BGCELL;
			}
			else{
				font = headerF;
			}
		}
		else {
			if( isBoldFont )
			{
				font = labelF;
			}
			else {
				font = dataCellF;
			}
		}

		int i = values.length;
		for(int j = 0; j < i; j++)
		{
			Cell cell = values[j].getFilledCell(font, color);
			addCell(cell);
		}

	}
	public void printProperties(String[] propertyName, String[] propertyValue, int[] aligns, float leading) throws Exception {
		int numOfNames = propertyName.length;
		int numOfValues = propertyValue.length;
		if (numOfNames != numOfValues || numOfValues * 2 != dimension)
			throw new Exception("Name/Value pairs passed in does not match table dimension");
		Cell cell = null;
		Chunk chunk = null;
		for (int i = 0; i < numOfNames; i++) {
			chunk = new Chunk(propertyName[i], labelF);
			cell = new Cell(chunk);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setLeading(leading);
			addCell(cell);
			chunk = new Chunk("   " + propertyValue[i], dataCellF);
			cell = new Cell(chunk);
			if ( aligns == null ) {
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			}else {
				cell.setHorizontalAlignment(aligns[i]);
			}
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setLeading(leading);
			addCell(cell);
		}
	}
	public void printProperties(String[] propertyName, String[] propertyValue, float leading) throws Exception {
		printProperties(propertyName, propertyValue, null, leading);
	}
	public void printReportHeader1(String company, String report, String loginName, String date, String time, float leading) throws Exception {
		Cell cell = null;
		Phrase ph = null;

		ph = new Phrase(" ", labelF);
		cell = new Cell(ph);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setLeading(leading);
		addCell(cell);

		ph = new Phrase(company, title2F);
		cell = new Cell(ph);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(dimension - 2);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setLeading(leading);
		addCell(cell);

		ph = new Phrase(" ", labelF);
		cell = new Cell(ph);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setLeading(leading);
		addCell(cell);
		//
		ph = new Phrase(date, labelF);
		cell = new Cell(ph);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setLeading(leading);
		addCell(cell);

		ph = new Phrase(report, labelF);
		cell = new Cell(ph);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(dimension - 2);
		cell.setLeading(leading);
		addCell(cell);

		ph = new Phrase("", labelF);
		cell = new Cell(ph);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setLeading(leading);
		addCell(cell);
		//
		ph = new Phrase(time, labelF);
		cell = new Cell(ph);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setLeading(leading);
		addCell(cell);

		ph = new Phrase("", labelF);
		cell = new Cell(ph);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(dimension - 2);
		cell.setLeading(leading);
		addCell(cell);

		ph = new Phrase(loginName, labelF);
		cell = new Cell(ph);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setLeading(leading);
		addCell(cell);

	}
	public void printTblCells(String[] cells, int[] aligns, float leading) throws Exception {
		int numOfCells = cells.length;
		int numOfAligns = aligns.length;
		if (numOfCells != dimension || numOfAligns != dimension)
			throw new Exception("Number of headers or alignments being passed in does not match table dimension.");
		Cell cell = null;
		Chunk chunk = null;
		for (int i = 0; i < numOfCells; i++) {
			chunk = new Chunk(cells[i], dataCellF);
			cell = new Cell(chunk);
			cell.setHorizontalAlignment(aligns[i]);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setLeading(leading);
			addCell(cell);
		}

	}
	public void printTblHeader(String[] headers, int[] aligns, float leading) throws Exception {
		int numOfHeads = headers.length;
		int numOfAligns = aligns.length;
		if (numOfHeads != dimension || numOfAligns != dimension)
			throw new Exception("Number of headers or alignments being passed in does not match table dimension.");
		Cell cell = null;
		Chunk chunk = null;
		for (int i = 0; i < numOfHeads; i++) {
			chunk = new Chunk(headers[i], tblHeaderF);
			cell = new Cell(chunk);
			cell.setHorizontalAlignment(aligns[i]);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setLeading(leading);
			addCell(cell);
		}

	}
	public void printTimeStamp(String timeStamp, int align, float leading) throws Exception {
		Cell cell = null;
		Phrase ph = new Phrase(timeStamp, timeStampF);
		cell = new Cell(ph);
		cell.setColspan(dimension);
		cell.setHorizontalAlignment(align);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setLeading(leading);
		addCell(cell);

	}
	public void printTitle(String title, int level, float leading) throws Exception {
		Font fnt = null;
		if (level == 1) fnt = title1F;
		else if (level == 2) fnt = title2F;
		else if (level == 3) fnt = title3F;
		else
			throw new Exception("This font level is not supported.");
		Cell cell = null;
		Phrase ph = new Phrase(title, fnt);
		cell = new Cell(ph);
		cell.setColspan(dimension);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setLeading(leading);
		addCell(cell);

	}
	public void setPicture(Image image, float leading) throws BadElementException
	{
		Cell  cell = null;
		cell = new Cell();
		cell.add(image);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(dimension);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setLeading(leading);
		addCell(cell);
	}
public void setPicture(Image image, float leading, float xPoints, float yPoints, float yPos) throws BadElementException {
	Cell cell = null;

	// yPos gives the image offset inside the cell
	image.scaleToFit(xPoints, yPoints);
	image.setAlignment(Image.MIDDLE);
	if (image.getPlainHeight() > 0)
		yPos = ((yPoints - image.getPlainHeight()) / 2f) + yPos;
	cell = new Cell(new Chunk(image, 0, yPos));

	cell.setBorder(Rectangle.NO_BORDER);
	cell.setColspan(dimension);
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setLeading(leading); // it gives the cell hight
	addCell(cell);
}
}
