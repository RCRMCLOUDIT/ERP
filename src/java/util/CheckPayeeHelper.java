package com.cap.util;

import java.sql.SQLException;
import javax.sql.RowSet;

import com.cap.wdf.command.CachedRowSet;
/**
 * Insert the type's description here.
 * Creation date: (5/10/2004 7:38:51 PM)
 * @author:
 */
public class CheckPayeeHelper {

    /* With accessors */
    private RowSet payeeRS = null; // Case cursor in RowSet

    // To create the output */
	private StringBuffer buff = new StringBuffer();
	
	private String searchStr = "";
	//private String onLoadStr = "";
	    
	private boolean odd = true;
	private int page = 0;
	    
    /* Constants */
    private static final int ENTID_COLUMN = 1;
    private static final int ENTNAME_COLUMN = 2;
    private static final int ENTTYPE_COLUMN = 3;

	//private static final String ENTTYPE_EMPLOYEE = "E";
	private static final String ENTTYPE_CUSTOMER = "C";
	private static final String ENTTYPE_VENDOR = "V";

    /**
     * DropBoxGL constructor comment.
     */
    public CheckPayeeHelper() {
        super();
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 7:47:26 PM)
     * @return java.lang.String
     */
    public void buildLines() {

		int rowCount = 0;
		
		String entId = "";
		String entName = "";
		String entType = "";

        buff.delete(0, buff.length());
        
		try
		{
			while (payeeRS.next() && rowCount < ConstantValue.POPUP_PAGE_SIZE)
			{
				entId = payeeRS.getString(ENTID_COLUMN);
				entName = payeeRS.getString(ENTNAME_COLUMN);
				entType = payeeRS.getString(ENTTYPE_COLUMN).trim();
				
				buildJSArray(rowCount, entName);
				buildHtmlLine(	rowCount++, entType, entId, entName); 
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

	/**
	 * Insert the method's description here.
	 * Creation date: (5/10/2004 7:47:26 PM)
	 * @return 
	 */
	public void buildHtmlLine(int index, String type, String id, String name) {
		if(buff == null)
			return;
		else
		{			
			String entityType = type.equals(ENTTYPE_CUSTOMER)?"Customer":type.equals(ENTTYPE_VENDOR)?"Vendor":"Employee";
			 
			buff.append("<TR class=\"");
			buff.append(odd?"pcrinfo":"pcrinfo1");
			buff.append("\" onmouseover=\"changeColorBox(this,1);\" onmouseout=\"changeColorBox(this,0);\" ");
			buff.append("onclick=\"passBack('");
			buff.append(type);		
			buff.append(ConstantValue.DELIMITER);
			buff.append(id);
			buff.append(ConstantValue.DELIMITER);
			buff.append("', aname[" + index + "]);\">\n");
			buff.append("<TD nowrap>" + name + "</TD>\n");
			buff.append("<TD nowrap>" + entityType + "</TD>\n");
			buff.append("</TR>\n");

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
			payeeRS.beforeFirst();
			
		}catch (SQLException e)
		{
			System.out.println("exception!!!! + " + e.getMessage());
			return "";
		}
		
		buildLines();

		//System.out.println(buff.toString());

		return buff.toString();
	}

    /**
     * Insert the method's description here.
     * Creation date: (5/11/2004 4:40:51 PM)
     * @return javax.sql.RowSet
     */
    public javax.sql.RowSet getCostCenterRS() {
        return payeeRS;
    }

    /**
     * Insert the method's description here.
     * Creation date: (5/11/2004 4:40:51 PM)
     * @param newRowSetCursor javax.sql.RowSet
     */
    public void setPayeeRS(RowSet newRowSetCursor) {
		payeeRS = newRowSetCursor;
        try {
			payeeRS.beforeFirst();
        } catch (Exception e) {
			payeeRS = null;
        }        
    }

	public String getOnLoadStr() {
		try
		{
			if(payeeRS == null || ((CachedRowSet)payeeRS).getRowSetSize() != 1 || page != 1)
				return "";
			else
			{
				buff.delete(0, buff.length());

				payeeRS.beforeFirst();
				payeeRS.next();
				
				String Id = payeeRS.getString(ENTID_COLUMN).trim();
				String Name = payeeRS.getString(ENTNAME_COLUMN).trim();
				String Type = payeeRS.getString(ENTTYPE_COLUMN).trim();

				buff.append("passBack('");

				buff.append(Type);
				buff.append(ConstantValue.DELIMITER);
				buff.append(Id);
				buff.append(ConstantValue.DELIMITER);
				buff.append("', '");
				buff.append(Format.escapeSingerDouble(Name));
				buff.append("'); close();");
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