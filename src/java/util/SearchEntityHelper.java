package com.cap.util;

import java.sql.SQLException;
import javax.sql.RowSet;
import com.cap.wdf.command.CachedRowSet;
/**
 * Insert the type's description here.
 * Creation date: (5/10/2004 7:38:51 PM)
 * @author:
 */
public class SearchEntityHelper {

    /* With accessors */
    private RowSet entityRS = null; // Case cursor in RowSet
    //private RowSet infoRS = null; // Case cursor in RowSet

    // To create the output */
	private StringBuffer buff = new StringBuffer();
	
	private String searchStr = "";
	private String onLoadStr = "";
	private String custShowRel = ""; // to control cust search, if find a parent
	private String srchType = "";

	private boolean odd = true;
	private int page = 0;
	private int pageSize = 0;
	private boolean returnEid = false;
	private boolean hasVendor = false;
	    
    /* Constants */
    private static final int ENTID_COLUMN = 1;
    private static final int ENTNAME_COLUMN = 2;
    private static final int ENTTYPE_COLUMN = 3;
	private static final int ENTEID_COLUMN = 4;
	private static final int ENTNUM_COLUMN = 5;
	private static final int POFLAG_COLUMN = 6;	// addr for customer
	private static final int ENTSTATUS_COLUMN = 7;
	private static final int UCC_COLUMN = 8;
	private static final int ENTRELA_COLUMN = 9; // Relation for Customer:C/P/blank

	private static final int EMPENDDATE_COLUMN = 5; //FOR  employee only

	private static final int CUSTRELAFLAG_COLUMN = 1;
	
	private static final char ENTTYPE_EMPLOYEE = 'E';
	private static final char ENTTYPE_CUSTOMER = 'C';
	private static final char ENTTYPE_VENDOR = 'V';
	private static final char ENTTYPE_MANUF = 'M';
	private static final char ENTTYPE_WHOUSE = 'W';
	private static final char ENTTYPE_SALESPERSON = 'S';
	private static final char ENTTYPE_BRANCH = 'B';
	private static final char ENTTYPE_CUSTFAMILY = 'F';
	private static final char ENTTYPE_REPCOMP = 'R';

    /**
     * DropBoxGL constructor comment.
     */
    public SearchEntityHelper() {
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
		String entEid = "";
		String entName = "";
		String entRela = "";
		String entType = "";
		String entNum = "";
		String poFlag = "";
		String entStatus = "";
		String ucc = "";
		String addr = "";
		//String crFlag = ""; //customer related flag
		
		
        buff.delete(0, buff.length());
        
		try
		{
			while (entityRS.next() && rowCount < pageSize)
			{
				entId 	= entityRS.getString(ENTID_COLUMN);
				entEid 	= entityRS.getBigDecimal(ENTEID_COLUMN).toString();
				entName = entityRS.getString(ENTNAME_COLUMN);
				entType = entityRS.getString(ENTTYPE_COLUMN).trim();
				
				if(entType.equals("C") && !srchType.equals("CUSEDI"))
					entRela = entityRS.getString(ENTRELA_COLUMN);
				
				/*
				if(entType.equals("C"))
				{	
					crFlag = infoRS.getString(CUSTRELAFLAG_COLUMN);
				}
				*/
				ucc 	= "";
				poFlag 	= "";
				entStatus = "";
				
				if(	entType.equals("C") || entType.equals("V") || 
					entType.equals("M") || entType.equals("F") || 
					entType.equals("R"))
					entNum = entityRS.getString(ENTNUM_COLUMN);	
				
				if(entType.equals("V"))
				{	
					poFlag = entityRS.getString(POFLAG_COLUMN);
					ucc = entityRS.getString(UCC_COLUMN).trim();
				}

				if(entType.equals("C") || entType.equals("V") || entType.equals("F"))
					entStatus = entityRS.getString(ENTSTATUS_COLUMN);
				else if(entType.equals("E"))
					entStatus = entityRS.getString(EMPENDDATE_COLUMN);
				else if(entType.equals("R")) //repComp
					entStatus = entityRS.getString(POFLAG_COLUMN);

				
				if(entType.equals("C") ||  entType.equals("F"))
					addr = entityRS.getString(POFLAG_COLUMN);
						
				buildJSArray(rowCount, entName);
				
				if(returnEid) 
				  buildHtmlLine( rowCount++, entType, entEid, entName, entRela, entNum, poFlag, entStatus, ucc, addr, entEid );
				else
				  buildHtmlLine( rowCount++, entType, entId, entName, entRela, entNum, poFlag, entStatus, ucc, addr, entEid );  
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
	public void buildHtmlLine(int index, String type, String id, String name,String custPorC, String num, String poFlag, String status, String ucc, String addr, String Eid) {
		if(buff == null)
			return;
		else
		{			
			String entityType = "";
			String st = "";
			switch (type.charAt(0)) {
				case ENTTYPE_CUSTOMER :
					entityType = "Customer";
					if(status.equals("OP"))		
						st = "Yes";
					else if(status.equals("CS"))		
						st = "Cash";
					else
						st = "No";
					break;
				case ENTTYPE_VENDOR :
					entityType = "Vendor";
					if(status.equals("A"))		st = "Yes";
					else						st = "No";
					break;
				case ENTTYPE_EMPLOYEE :
					entityType = "Employee";
					//status = empEndDate
					if(status.equals("0"))		st = "Yes";
					else						st = "No";
					break;
				case ENTTYPE_MANUF :
					entityType = "Manufacturer";
					break;
				case ENTTYPE_WHOUSE :
					entityType = "Warehouse/Store";
					break;
				case ENTTYPE_SALESPERSON :
					entityType = "Sales Person";
					break;
				case ENTTYPE_BRANCH :
					entityType = "Branch";
					break;
				case ENTTYPE_REPCOMP :
					entityType = "Rep Company";
					if(status.equals("Y"))		
						st = "Yes";
					else
						st = "No";
					
					break;
				case ENTTYPE_CUSTFAMILY :	
					entityType = "Customer Family";
					if(status.equals("OP"))		
						st = "Yes";
					else if(status.equals("CS"))		
						st = "Cash";
					else
						st = "No";
					break;
				default :
					entityType = "Entity";
					st = status;
					break;
			}
			//type.equals(ENTTYPE_CUSTOMER)?"Customer":type.equals(ENTTYPE_VENDOR)?"Vendor":"Employee";
			 
			if(!type.equals("C"))
			{
				buff.append("<TR class=\"");
				buff.append(odd?"pcrinfo":"pcrinfo1");
				buff.append("\" onmouseover=\"changeColorBox(this,1);\" onmouseout=\"changeColorBox(this,0);\" ");
				buff.append("onclick=\"passBack('");
				buff.append(id);
				buff.append("', aname[" + index + "],'" + poFlag + "','','" + status + "');\">\n");
				buff.append("<TD nowrap>" + name + "</TD>\n");
				buff.append("<TD nowrap>" + num + "</TD>\n");
				if(hasVendor)
				{
					buff.append("<TD nowrap>" + ucc + "</TD>\n");
				}
				
				if(type.equals("F"))
					buff.append("<TD nowrap>" + addr + "</TD>\n");
				else
					buff.append("<TD nowrap>" + entityType + "</TD>\n");
				buff.append("<TD nowrap align=\"center\">" + st + "</TD>\n");
				buff.append("</TR>\n");
			}
			else
			{
				String markRelCust = (custPorC.equals("P") || custPorC.equals("C"))?"Y":"";
				
				buff.append("<TR class=\"");
				buff.append(odd?"pcrinfo":"pcrinfo1");
				buff.append("\">\n");
			
				buff.append("<TD nowrap	onmouseover=\"changeColorBox(this,1);\" onmouseout=\"changeColorBox(this,0);\" ");
				buff.append("onclick=\"passBack('");
				buff.append(id);
				buff.append("', aname[" + index + "],'" + poFlag + "','" + markRelCust +"','" + status + "');\">\n");
				buff.append(name);
				buff.append("</TD>\n");
				
				if( !srchType.equals("CUSEDI"))
				{
					if (custPorC.equals("P"))
					{	
						buff.append("<TD nowrap align='center' class=\"pcrinfo4\" onmouseover=\"changeColorBox(this,1);\" onmouseout=\"changeColorBox(this,0);\" ");
						buff.append("onclick=\"loadFamily('");
						buff.append(Eid);
						buff.append("');\">View</TD>\n");
						
					}else{
					    buff.append("<TD nowrap></TD>\n");	
					}
				}				
				buff.append("<TD nowrap onmouseover=\"changeColorBox(this,1);\" onmouseout=\"changeColorBox(this,0);\" ");
				buff.append("onclick=\"passBack('");
				buff.append(id);
				buff.append("', aname[" + index + "],'" + poFlag + "','" + markRelCust + "','" + status + "');\">\n");
				buff.append(num);
				buff.append("</TD>\n");
				
				buff.append("<TD nowrap onmouseover=\"changeColorBox(this,1);\" onmouseout=\"changeColorBox(this,0);\" ");
				buff.append("onclick=\"passBack('");
				buff.append(id);
				buff.append("', aname[" + index + "],'" + poFlag + "','" + markRelCust + "','" + status + "');\">\n");
				buff.append(addr);
				buff.append("</TD>\n");

				buff.append("<TD nowrap align=\"center\"");
				buff.append(" onmouseover=\"changeColorBox(this,1);\" onmouseout=\"changeColorBox(this,0);\" ");
				buff.append("onclick=\"passBack('");
				buff.append(id);
				buff.append("', aname[" + index + "],'" + poFlag + "','" + markRelCust + "','" + status + "');\">\n");
				buff.append(st);
				buff.append("</TD>\n");
				
				buff.append("</TR>\n");
			
			}	
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
			entityRS.beforeFirst();
			
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
        return entityRS;
    }

    /**
     * Insert the method's description here.
     * Creation date: (5/11/2004 4:40:51 PM)
     * @param newRowSetCursor javax.sql.RowSet
     */
    public void setEntityRS(RowSet newRowSetCursor) {
		entityRS = newRowSetCursor;
        try {
			entityRS.beforeFirst();
        } catch (Exception e) {
			entityRS = null;
        }        
    }
    
	public String getOnLoadStr() {
		try
		{
			if(srchType.equals("CUSFAM") || entityRS == null || ((CachedRowSet)entityRS).getRowSetSize() != 1 ||	page != 1 )
				return "";
			else
			{
				buff.delete(0, buff.length());

				entityRS.beforeFirst();
				entityRS.next();
				
				String custPorC = "";	//parent or child flag
				/*
				if(entityRS.getString(ENTTYPE_COLUMN).trim().equals("C"))
				{	
					infoRS.next();
					crFlag = infoRS.getString(CUSTRELAFLAG_COLUMN);
				}
				*/
				if(entityRS.getString(ENTTYPE_COLUMN).trim().equals("C") && !srchType.equals("CUSEDI"))
				{
					//if single match cust and it's parent, 
					//if sys param setup to show RelatedCust, do not return
					custPorC = entityRS.getString(ENTRELA_COLUMN).trim();
					if(custShowRel.equals("Y") && custPorC.equals("P"))
						return ""; 
				}

				String poFlag = "";
				String status = "";
				String entType = entityRS.getString(ENTTYPE_COLUMN).trim();

				if(entType.equals("V"))
					poFlag = entityRS.getString(POFLAG_COLUMN);

				if(entType.equals("C") || entType.equals("V") || entType.equals("F"))
					status = entityRS.getString(ENTSTATUS_COLUMN);

				buff.append("passBack('");
				if(returnEid)
					buff.append(entityRS.getBigDecimal(ENTEID_COLUMN).toString());
				else
					buff.append(entityRS.getString(ENTID_COLUMN).trim());

				buff.append("', aname[0],'");
				buff.append(poFlag + "','");
				buff.append(custPorC + "','" + status + "');");
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
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * @param i
	 */
	public void setPageSize(int i) {
		pageSize = i;
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
	 * @return
	 */
	public boolean isReturnEid() {
		return returnEid;
	}

	/**
	 * @param b
	 */
	public void setReturnEid(boolean b) {
		returnEid = b;
	}
	public void setHasVendor(boolean hasVendor) {
		this.hasVendor = hasVendor;
	}
	public void setCustShowRel(String custShowRel) {
		this.custShowRel = custShowRel;
	}
	public void setSrchType(String srchType) {
		this.srchType = srchType;
	}

}