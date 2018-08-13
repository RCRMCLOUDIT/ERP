package com.cap.util;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.cap.wdf.command.CachedRowSet;

public class GLTagHelper //extends ERPReport
{
    /* With accessors */
    private RowSet tagsRS = null; // Case cursor in RowSet

    // To create the output */
	private StringBuffer buff = new StringBuffer();
	
	private String searchStr = "";
	    
	private boolean odd = true;
	private int page = 0;
	    
    /* Constants */
    private static final int TAGID_COLUMN 	   = 1;
    private static final int TAGNUMBER_COLUMN  = 2;
    private static final int TAGNAME_COLUMN    = 3;
    private static final int TAGSTATUS_COLUMN  = 4;
    //private static final int TAGDISPLAY_COLUMN = 5;

	public GLTagHelper() {
        super();
    }

    public void buildLines() {

		int rowCount = 0;
		
		String tagId      = "";
		String tagNumber  = "";
		String tagName    = "";
		String tagStatus  = "";
		//String tagDisplay = "";
		
        buff.delete(0, buff.length());
        
		try
		{
			while (tagsRS.next() && rowCount < ConstantValue.POPUP_PAGE_SIZE)
			{
				tagId 	   = tagsRS.getString(TAGID_COLUMN);
				tagNumber  = tagsRS.getString(TAGNUMBER_COLUMN);
				tagName    = tagsRS.getString(TAGNAME_COLUMN);
				tagStatus  = tagsRS.getString(TAGSTATUS_COLUMN);
				//tagDisplay = tagsRS.getString(TAGDISPLAY_COLUMN);
				
				buildJSArray(rowCount, tagNumber);
				buildHtmlLine(rowCount++, tagId, tagNumber, tagName, tagStatus);
			}
			
		}catch (SQLException exp)
		{
			System.out.println("Exception in accessing employee/vendor rowSet:" + exp.getMessage());			
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
			buff.append("aname[" + index + "]=escape('" + Format.escapeSingerDouble(name) + "');\n");
			buff.append("</SCRIPT>\n");
		}
	}

	public void buildHtmlLine(int index, String id, String number, String name, String status){
		if(buff == null)
			return;
		String st = status.equals("Y")?"Active":"Inactive";
		buff.append("<TR class=\"");
		buff.append(odd?"pcrinfo":"pcrinfo1");
		buff.append("\" onmouseover=\"changeColorBox(this,1);\" onmouseout=\"changeColorBox(this,0);\" ");
		buff.append("onclick=\"passBack('");
		buff.append(id);
		buff.append("', aname[" + index + "]);\">\n");
		buff.append("<TD>" + number + "</TD>\n");
		buff.append("<TD>" + name + "</TD>\n");
		buff.append("<TD>" + st + "</TD>\n");
		buff.append("</TR>\n");

		odd = !odd;
	}

	public java.lang.String getBuild() {
		try{
			tagsRS.beforeFirst();
			
		}catch (SQLException e)
		{
			System.out.println("exception!!!! + " + e.getMessage());
			return "";
		}
		
		buildLines();

		//System.out.println(buff.toString());

		return buff.toString();
	}

    public void settagsRS(RowSet newRowSetCursor) {
		tagsRS = newRowSetCursor;
        try {
			tagsRS.beforeFirst();
        } catch (Exception e) {
			tagsRS = null;
        }        
    }

	public String getOnLoadStr() {
		try
		{
			if(tagsRS == null || ((CachedRowSet)tagsRS).getRowSetSize() != 1 || page != 1)
				return "";
			else
			{
				buff.delete(0, buff.length());

				tagsRS.beforeFirst();
				tagsRS.next();
				
				String tagId 	  = tagsRS.getString(TAGID_COLUMN);

				buff.append("passBack('");
				buff.append(tagId);
				buff.append("', aname[0]);");
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
	public String getSearchStr() {
		return searchStr;
	}

	/**
	 * @param string
	 */
	public void setSearchStr(String string) {
		searchStr = string;
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
}
