package com.cap.erp.fa;

import java.sql.SQLException;
import javax.sql.RowSet;
import com.cap.wdf.command.CachedRowSet;
import com.cap.util.*;

/**
 * Insert the type's description here.
 * Creation date: (5/10/2004 7:38:51 PM)
 * @author:
 */
public class SearchItemClassHelper {

	/* With accessors */
	private RowSet classRS = null; // Case cursor in RowSet

	// To create the output */
	private StringBuffer buff = new StringBuffer();
	
	private String onLoadStr = "";
	
	//private String classType = "";
	    
	private boolean odd = true;
	private int page = 0;
	private String allowParent = "Y";
	    
	/* column index */
	private static final int CLASSID_COLUMN = 1;
	private static final int CLSNAME_COLUMN = 2;
	private static final int CLSPATH_COLUMN = 3;
	private static final int SELECT_COLUMN 	= 4;

	/**
	 * DropBoxGL constructor comment.
	 */
	public SearchItemClassHelper() {
		super();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (5/10/2004 7:47:26 PM)
	 * @return java.lang.String
	 */
	public void buildLines() {

		int rowCount = 0;
		int lineIndex = 0;
		
		String cateId = "";
		String cateName = "";
		String catePath = "";
		String select = "";
		
		buff.delete(0, buff.length());
        
		try
		{
			while (classRS.next() && rowCount < ConstantValue.POPUP_PAGE_SIZE)
			{
				cateId = classRS.getString(CLASSID_COLUMN);
				cateName = classRS.getString(CLSNAME_COLUMN);
				catePath = classRS.getString(CLSPATH_COLUMN);
				select = classRS.getString(SELECT_COLUMN);
				
				if(allowParent.equals("Y"))
					select = "Y";
					
				if(select.equals("Y"))
				{
					buildJSArray(lineIndex, cateName);
					buildHtmlLine( lineIndex++, cateId, cateName, catePath, true);
				}
				else
				{
					buildHtmlLine( 0, cateId, cateName, catePath, false);
				}
				
				rowCount++;
			}
			
		}catch (SQLException exp)
		{
			System.out.println("Exception in accessing product category rowSet:" + exp.getMessage());			
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (5/10/2004 7:47:26 PM)
	 * @return 
	 */
	public void buildJSArray(int index, String name) {
		if(buff == null)
			return;
		else
		{			
			buff.append("<SCRIPT LANGUAGE=\"JavaScript1.2\">\n");
			buff.append("aname[" + index + "]=escape('" + Format.escapeSingleQuote(name) + "');\n");
			buff.append("</SCRIPT>\n");
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (5/10/2004 7:47:26 PM)
	 * @return 
	 */
	public void buildHtmlLine(int index, String id, String name, String path, boolean selectable) {
		if(buff == null)
			return;
		else
		{			
			buff.append("<TR class=\"");
			buff.append(odd?"pcrinfo":"pcrinfo1");

			if(selectable == true)
			{
				buff.append("\" onmouseover=\"changeColorBox(this,1);\" onmouseout=\"changeColorBox(this,0);\" ");
				buff.append("onclick=\"passBack('");
				buff.append(id);		
				buff.append("', aname[" + index + "]);\">\n");
				buff.append("<TD nowrap>");
			}
			else
			{
				buff.append("\" >\n");
				buff.append("<TD nowrap class='link_alt_underline_G'>");
			}
			buff.append(path);
			buff.append("</TD>\n</TR>\n");
			
			odd = !odd;
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (5/10/2004 7:47:26 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getBuild() {
		try{
			classRS.beforeFirst();
			
		}catch (SQLException e)
		{
			System.out.println("exception!!!! + " + e.getMessage());
			return "";
		}
		
		buildLines();

		return buff.toString();
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (5/11/2004 4:40:51 PM)
	 * @param newRowSetCursor javax.sql.RowSet
	 */
	public void setClassRS(RowSet newRowSetCursor) {
		classRS = newRowSetCursor;
		try {
			classRS.beforeFirst();
		} catch (Exception e) {
			classRS = null;
		}        
	}

	public String getOnLoadStr() {
		try
		{
			if(classRS == null || ((CachedRowSet)classRS).getRowSetSize() != 1 || page != 1)
				return "";
			else
			{
				buff.delete(0, buff.length());

				classRS.beforeFirst();
				classRS.next();
				
				String cateId = classRS.getString(CLASSID_COLUMN);
				//String cateName = classRS.getString(CLSNAME_COLUMN);
				//String catePath = classRS.getString(CLSPATH_COLUMN);
				String select = classRS.getString(SELECT_COLUMN);

				if(select.equals("N") && !allowParent.equals("Y"))
					return "";
					
				buff.append("passBack('");
				buff.append(cateId);
				buff.append("', aname[0]); close();");
				//buff.append(Format.escapeSingerDouble(cateName));
				//buff.append("'); close();");
				return buff.toString();
			}
		}catch (Exception e)
		{
			return "";	
		}
	}

	/**
	 * @return
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param i
	 */
	public void setPage(int i) {
		page = i;
	}


	/**
	 * @param string
	 */
	public void setAllowParent(String string) {
		allowParent = string;
	}

}