package com.cap.util;

import java.sql.SQLException;
import javax.sql.RowSet;
import com.cap.wdf.command.CachedRowSet;

/**
 * Insert the type's description here.
 * Creation date: (5/10/2004 7:38:51 PM)
 * @author:
 */
public class CostCenterHelper {

    /* With accessors */
    private RowSet costCenterRS = null; // Case cursor in RowSet
    //private String imagesPath = "/erp/ERP_COMMON/images/";

    // To create the output */
	private StringBuffer buff = new StringBuffer();
	
    private String entType = "";
	private String searchStr = "";
	//private String onLoadStr = "";
	    
	private boolean odd = true;
	private int page = 0;
    
    /* Constants */
    //private static final int ENTTYPE_COLUMN = 1;
    private static final int ID1_COLUMN = 2;
    private static final int NAME1_COLUMN = 3;
    private static final int ID2_COLUMN = 4;
    private static final int NAME2_COLUMN = 5;
    private static final int ID3_COLUMN = 6;
    private static final int NAME3_COLUMN = 7;
    private static final int ID4_COLUMN = 8;
	private static final int NAME4_COLUMN = 9;

	private static final String ENTTYPE_CUSTOMER 	= "C";
	private static final String ENTTYPE_PROJECT 	= "P";
	private static final String ENTTYPE_TASK 		= "T";
	private static final String ENTTYPE_EMPLOYEE 	= "E";
	private static final String ENTTYPE_VENDOR 		= "V";
	private static final String ENTTYPE_DIVISION 	= "D";
	private static final String ENTTYPE_BRANCH 		= "B";
	private static final String ENTTYPE_DEPARTMENT 	= "A";
	private static final String ENTTYPE_WORKCENTER 	= "W";

	private static final String DELIMITER1 = "©©© ©©©0©©©0";
	private static final String DELIMITER2 = "©©©0©©©0©©©";
	
    /**
     * DropBoxGL constructor comment.
     */
    public CostCenterHelper() {
        super();
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/10/2004 7:47:26 PM)
     * @return java.lang.String
     */
    public void buildLinesEMP() {

		int rowCount = 0;
		String entName = "";
		String entId = "";

        buff.delete(0, buff.length());
        
		try
		{
			while (costCenterRS.next() && rowCount < ConstantValue.POPUP_PAGE_SIZE)
			{
				entId = costCenterRS.getString(ID1_COLUMN);
				entName = costCenterRS.getString(NAME1_COLUMN);
				
				buildJSArray(rowCount, entName);
				buildHtmlLine(	rowCount++, 
								entType.equals("EMP")?ENTTYPE_EMPLOYEE: ENTTYPE_VENDOR, 
								entId + DELIMITER1, 
								entName, 
								"", "", "");
			}
			
		}catch (SQLException exp)
		{
			System.out.println("Exception in accessing employee/vendor rowSet:" + exp.getMessage());			
		}
    }

	/**
	 * Insert the method's description here.
	 * Creation date: (5/10/2004 7:47:26 PM)
	 * @return java.lang.String
	 */
	public void buildLinesCUS() {

		int rowCount = 0;

		String customerId = "";
		String projectId = "";
		String taskId1 = "";
		String taskId2 = "";
		String customerName = "";
		String projectName = "";
		String taskName = "";
		
		String preCustId = "";
		String preProjId = "";
		//String preTaskId = "";
		
		int lineIndex = 0;
		
		buff.delete(0, buff.length());
        
		try
		{
			while (costCenterRS.next() && rowCount < ConstantValue.POPUP_PAGE_SIZE)
			{
				customerId = costCenterRS.getString(ID1_COLUMN).trim();
				projectId = costCenterRS.getString(ID2_COLUMN).trim();
				taskId1 = costCenterRS.getString(ID3_COLUMN).trim();
				taskId2 = costCenterRS.getString(NAME3_COLUMN).trim();
				
				customerName = costCenterRS.getString(NAME1_COLUMN).trim();
				projectName = costCenterRS.getString(NAME2_COLUMN).trim();
				taskName = costCenterRS.getString(ID4_COLUMN).trim();

				rowCount++;
				
				if( !preCustId.equals(customerId))
				{
					// add customer
					buildJSArray(lineIndex, customerName);
					buildHtmlLine(	lineIndex++, 
									ENTTYPE_CUSTOMER, 
									customerId + DELIMITER1, 
									customerName, "", "", "");
				}
				
				if( (!preCustId.equals(customerId) || !preProjId.equals(projectId)) && projectId.length() != 0)
				{
					//add project
					buildJSArray(lineIndex, customerName + " - " + projectName);
					buildHtmlLine(	lineIndex++, 
									ENTTYPE_PROJECT, 
									customerId + ConstantValue.DELIMITER + projectId + "©©©0©©©0", 
									"", projectName, "", "");
				}
				
				if( !taskId1.equals("0"))
				{
					//add task
					buildJSArray(lineIndex, customerName + " - " + projectName + " - " + taskName);
					buildHtmlLine(	lineIndex++, 
									ENTTYPE_TASK, 
									customerId + ConstantValue.DELIMITER + projectId + ConstantValue.DELIMITER + taskId1 + ConstantValue.DELIMITER + taskId2, 
									"", "", taskName, "");
				}
				
				preCustId = customerId;
				preProjId = projectId;
			}
			
		}catch (SQLException exp)
		{
			System.out.println("Exception in accessing customer rowSet:" + exp.getMessage());			
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (5/10/2004 7:47:26 PM)
	 * @return java.lang.String
	 */
	public void buildLinesLOG() {

		int rowCount = 0;
		String divId = "";
		String brchId = "";
		String deptId = "";
		String wrkcentId = "";
		
		String divName = "";
		String brchName = "";
		String deptName = "";
		String wrkcentName = "";

		String preDivId = "";
		String preBrchId = "";
		String preDeptId = "";
		
		int lineIndex = 0;
		
		buff.delete(0, buff.length());
        
		try
		{
			while (costCenterRS.next() && rowCount < ConstantValue.POPUP_PAGE_SIZE)
			{
				divId = costCenterRS.getString(ID1_COLUMN).trim();
				brchId = costCenterRS.getString(ID2_COLUMN).trim();
				deptId = costCenterRS.getString(ID3_COLUMN).trim();
				wrkcentId = costCenterRS.getString(ID4_COLUMN).trim();
				
				divName = costCenterRS.getString(NAME1_COLUMN).trim();
				brchName = costCenterRS.getString(NAME2_COLUMN).trim();
				deptName = costCenterRS.getString(NAME3_COLUMN).trim();
				wrkcentName = costCenterRS.getString(NAME4_COLUMN).trim();

				rowCount++;
				
				if( !preDivId.equals(divId))
				{
					// add division
					buildJSArray(lineIndex, divName);
					buildHtmlLine(	lineIndex++, 
									ENTTYPE_DIVISION, 
									divId + DELIMITER1, 
									divName, "", "", "");
				}
				
				if( ( !preDivId.equals(divId) || !preBrchId.equals(brchId)) && !brchId.equals("0"))
				{
					//add branch/plant
					buildJSArray(lineIndex, divName + " - " + brchName);
					buildHtmlLine(	lineIndex++, 
									ENTTYPE_BRANCH, 
									divId + ConstantValue.DELIMITER + brchId + "©©©0©©©0",
									//brchId + DELIMITER1, 
									"", brchName, "", "");
				}
				
				if( ( !preDivId.equals(divId) || !preBrchId.equals(brchId) || !preDeptId.equals(deptId)) && !deptId.equals("0"))
				{
					//add department
					buildJSArray(lineIndex, divName + " - " + brchName + " - " + deptName);
					buildHtmlLine(	lineIndex++, 
									ENTTYPE_DEPARTMENT, 
									divId + ConstantValue.DELIMITER + brchId + ConstantValue.DELIMITER + deptId + "©©©0",
									//deptId + DELIMITER1, 
									"", "", deptName, "");
				}
				if( !wrkcentId.equals("0"))
				{
					//add workcenter
					buildJSArray(lineIndex, divName + " - " + brchName + " - " + deptName + " - " + wrkcentName);
					buildHtmlLine(	lineIndex++, 
									ENTTYPE_WORKCENTER, 
									divId + ConstantValue.DELIMITER + brchId + ConstantValue.DELIMITER + deptId + ConstantValue.DELIMITER + wrkcentId, 
									//wrkcentId + DELIMITER1, 
									"", "", "", wrkcentName);
				}
				
				preDivId = divId;
				preBrchId = brchId;
				preDeptId = deptId;
			}
			
		}catch (SQLException exp)
		{
			System.out.println("Exception in accessing logistic unit rowSet:" + exp.getMessage());
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
	public void buildHtmlLine(int index, String type, String id, String name1, String name2, String name3, String name4) {
		if(buff == null)
			return;
		else
		{			
			buff.append("<TR class=\"");
			buff.append(odd?"pcrinfo":"pcrinfo1");
			buff.append("\" onmouseover=\"changeColorBox(this,1);\" onmouseout=\"changeColorBox(this,0);\" ");
			buff.append("onclick=\"passBack('");
			buff.append(type);		
			buff.append(ConstantValue.DELIMITER);
			buff.append(id);
			buff.append(ConstantValue.DELIMITER);
			buff.append("', aname[" + index + "]);\">\n");
			
			buff.append("<TD nowrap>" + name1 + "</TD>\n");
			buff.append("<TD nowrap>" + name2 + "</TD>\n");
			buff.append("<TD nowrap>" + name3 + "</TD>\n");
			buff.append("<TD nowrap>" + name4 + "</TD>\n");
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
			costCenterRS.beforeFirst();
			
		}catch (SQLException e)
		{
			System.out.println("exception!!!! + " + e.getMessage());
			return "";
		}
		
		if(entType.equals("EMP") || entType.equals("VEN"))
			buildLinesEMP();
		else if(entType.equals("CUS"))
			buildLinesCUS();
		else if(entType.equals("LOG"))
			buildLinesLOG();

		//System.out.println(buff.toString());

		return buff.toString();
	}

    /**
     * Insert the method's description here.
     * Creation date: (5/11/2004 4:40:51 PM)
     * @return javax.sql.RowSet
     */
    public javax.sql.RowSet getCostCenterRS() {
        return costCenterRS;
    }

    /**
     * Insert the method's description here.
     * Creation date: (5/11/2004 4:40:51 PM)
     * @param newRowSetCursor javax.sql.RowSet
     */
    public void setCostCenterRS(RowSet newRowSetCursor) {
		costCenterRS = newRowSetCursor;
        try {
			costCenterRS.beforeFirst();
        } catch (Exception e) {
			costCenterRS = null;
        }        
    }

	/**
	 * @return
	 */
	public String getEntType() {
		return entType;
	}

	/**
	 * @param string
	 */
	public void setEntType(String string) {
		entType = string;
	}

	/**
	 * @param string
	 */
	public String getOnLoadStrCUS() {
		if(page != 1)
			return "";
		
		try
		{
			int matchCount = 0;
			int matchIndex = 0;
			searchStr = searchStr.toLowerCase();
			
			buff.delete(0, buff.length());
			
			costCenterRS.beforeFirst();
			costCenterRS.next();
	
			String customerId = costCenterRS.getString(ID1_COLUMN).trim();
			String projectId = costCenterRS.getString(ID2_COLUMN).trim();
			String taskId1 = costCenterRS.getString(ID3_COLUMN).trim();
			String taskId2 = costCenterRS.getString(NAME3_COLUMN).trim();
				
			String customerName = costCenterRS.getString(NAME1_COLUMN).trim();
			String projectName = costCenterRS.getString(NAME2_COLUMN).trim();
			String taskName = costCenterRS.getString(ID4_COLUMN).trim();

			buff.append("passBack('");

			if(searchStr.length() > 0)
			{
				/* find which name match user search */
				if( customerName.toLowerCase().indexOf(searchStr, 0) >= 0)
				{
					matchCount++;
					matchIndex = 1;
				}
				if(projectName.length() > 0 && projectName.toLowerCase().indexOf(searchStr, 0) >= 0)
				{
					matchCount++;
					matchIndex = 2;
				}
				if(taskName.length() > 0 && taskName.toLowerCase().indexOf(searchStr, 0) >= 0)
				{
					matchCount++;
					matchIndex = 3;
				}
				
				if (matchCount == 1)
				{
					switch (matchIndex) {
						case 1 :
							buff.append(ENTTYPE_CUSTOMER);
							buff.append(ConstantValue.DELIMITER);
							buff.append(customerId);
							buff.append(DELIMITER1);
							buff.append(ConstantValue.DELIMITER);
							buff.append("', '");
							buff.append(Format.escapeSingerDouble(customerName));
							break;
						case 2 :
							buff.append(ENTTYPE_PROJECT);
							buff.append(ConstantValue.DELIMITER);
							buff.append(customerId);
							buff.append(ConstantValue.DELIMITER);
							buff.append(projectId);
							buff.append(DELIMITER2);
							buff.append("', '");
							buff.append(Format.escapeSingerDouble(customerName + " - " + projectName));
							break;
						case 3 :
							buff.append(ENTTYPE_TASK);
							buff.append(ConstantValue.DELIMITER);
							buff.append(customerId);
							buff.append(ConstantValue.DELIMITER);
							buff.append(projectId);
							buff.append(ConstantValue.DELIMITER);
							buff.append(taskId1);
							buff.append(ConstantValue.DELIMITER);
							buff.append(taskId2);
							buff.append("', '");
							buff.append(Format.escapeSingerDouble(customerName + " - " + projectName + " - " + taskName));
							break;

						default :
							break;
					}

					buff.append("'); close();");
		
					return buff.toString();
				}
				
			}
			
			return "";
		}catch (Exception e)
		{
			return "";	
		}
	}

	public String getOnLoadStrLOG() {
		if(page != 1)
			return "";
		try
		{
			int matchCount = 0;
			int matchIndex = 0;
			searchStr = searchStr.toLowerCase();
			
			buff.delete(0, buff.length());
			
			costCenterRS.beforeFirst();
			costCenterRS.next();
	
			String divId = costCenterRS.getString(ID1_COLUMN).trim();
			String brchId = costCenterRS.getString(ID2_COLUMN).trim();
			String deptId = costCenterRS.getString(ID3_COLUMN).trim();
			String wrkcentId = costCenterRS.getString(ID4_COLUMN).trim();
				
			String divName = costCenterRS.getString(NAME1_COLUMN).trim();
			String brchName = costCenterRS.getString(NAME2_COLUMN).trim();
			String deptName = costCenterRS.getString(NAME3_COLUMN).trim();
			String wrkcentName = costCenterRS.getString(NAME4_COLUMN).trim();

			buff.append("passBack('");

			if(searchStr.length() > 0)
			{
				/* find which name match user search */
				if( divName.toLowerCase().indexOf(searchStr, 0) >= 0)
				{
					matchCount++;
					matchIndex = 1;
				}
				if(brchName.length() > 0 && brchName.toLowerCase().indexOf(searchStr, 0) >= 0)
				{
					matchCount++;
					matchIndex = 2;
				}
				if(deptName.length() > 0 && deptName.toLowerCase().indexOf(searchStr, 0) >= 0)
				{
					matchCount++;
					matchIndex = 3;
				}
				if(wrkcentName.length() > 0 && wrkcentName.toLowerCase().indexOf(searchStr, 0) >= 0)
				{
					matchCount++;
					matchIndex = 4;
				}
				
				if (matchCount == 1)
				{
					switch (matchIndex) {
						case 1 :
							buff.append(ENTTYPE_DIVISION);
							buff.append(ConstantValue.DELIMITER);
							buff.append(divId);
							buff.append(DELIMITER1);
							buff.append(ConstantValue.DELIMITER);
							buff.append("', '");
							buff.append(Format.escapeSingerDouble(divName));
							break;
						case 2 :
							buff.append(ENTTYPE_BRANCH);
							buff.append(ConstantValue.DELIMITER);
							buff.append(divId);
							buff.append(ConstantValue.DELIMITER);
							buff.append(brchId);
							buff.append(DELIMITER2);
							buff.append("', '");
							buff.append(Format.escapeSingerDouble(divName + " - " + brchName));
							break;
						case 3 :
							buff.append(ENTTYPE_DEPARTMENT);
							buff.append(ConstantValue.DELIMITER);
							buff.append(divId);
							buff.append(ConstantValue.DELIMITER);
							buff.append(brchId);
							buff.append(ConstantValue.DELIMITER);
							buff.append(deptId);
							buff.append(ConstantValue.DELIMITER);
							buff.append("0");
							buff.append(ConstantValue.DELIMITER);
							buff.append("', '");
							buff.append(Format.escapeSingerDouble(divName + " - " + brchName + " - " + deptName));
							break;
						case 4 :
							buff.append(ENTTYPE_WORKCENTER);
							buff.append(ConstantValue.DELIMITER);
							buff.append(divId);
							buff.append(ConstantValue.DELIMITER);
							buff.append(brchId);
							buff.append(ConstantValue.DELIMITER);
							buff.append(deptId);
							buff.append(ConstantValue.DELIMITER);
							buff.append(wrkcentId);
							buff.append(ConstantValue.DELIMITER);
							buff.append("', '");
							buff.append(Format.escapeSingerDouble(divName + " - " + brchName + " - " + deptName + " - " + wrkcentName));
							break;

						default :
							break;
					}

					buff.append("'); close();");
		
					return buff.toString();
				}
				
			}
			
			return "";
		}catch (Exception e)
		{
			return "";	
		}
	}

	public String getOnLoadStr() {
		try
		{
			if(costCenterRS == null || ((CachedRowSet)costCenterRS).getRowSetSize() != 1 || page != 1)
				return "";
			else
			{
				if(entType.equals("EMP") || entType.equals("VEN"))
				{
					buff.delete(0, buff.length());

					costCenterRS.beforeFirst();
					costCenterRS.next();
					
					String Id1 = costCenterRS.getString(ID1_COLUMN).trim();
					String Name1 = costCenterRS.getString(NAME1_COLUMN).trim();

					buff.append("passBack('");

					buff.append(entType.equals("EMP")? ENTTYPE_EMPLOYEE: ENTTYPE_VENDOR);
					buff.append(ConstantValue.DELIMITER);
					buff.append(Id1);
					buff.append(DELIMITER1);
					buff.append(ConstantValue.DELIMITER);
					buff.append("', '");
					buff.append(Format.escapeSingerDouble(Name1));
					buff.append("'); close();");
					return buff.toString();
				}
				else if(entType.equals("CUS"))
				{
					return getOnLoadStrCUS();
				}
				else if(entType.equals("LOG"))
				{
					return getOnLoadStrLOG();
				}
			}
			return "";
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
	 * @param string
	 */
	public static String getEntityTypeCode(String s1) {
		if(s1 == null || s1.trim().length() ==0)
			return "";
		else
		{
			s1 = s1.trim();
			
			if( s1.equalsIgnoreCase(ENTTYPE_CUSTOMER) ||
				s1.equalsIgnoreCase(ENTTYPE_PROJECT) ||
				s1.equalsIgnoreCase(ENTTYPE_TASK))
				return "CUS";
			else if(s1.equalsIgnoreCase(ENTTYPE_EMPLOYEE))
				return 	"EMP";
			else if(s1.equalsIgnoreCase(ENTTYPE_VENDOR))
				return 	"VEN";
			else if(s1.equalsIgnoreCase(ENTTYPE_DIVISION)|| 
					s1.equalsIgnoreCase(ENTTYPE_BRANCH)||
					s1.equalsIgnoreCase(ENTTYPE_DEPARTMENT)||
					s1.equalsIgnoreCase(ENTTYPE_WORKCENTER) )
				return 	"LOG";
			else
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
	public static String getEntityTypeName(String s1) {
		if(s1 == null || s1.trim().length() ==0)
			return "";
		
		s1 = s1.trim();
		String name = "";
		
		if(s1.equals(ENTTYPE_CUSTOMER))	
			name = "Customer";
		else if(s1.equals(ENTTYPE_EMPLOYEE))	
			name = "Employee";
		else if(s1.equals(ENTTYPE_VENDOR))	
			name = "Vendor";
		else if(s1.equals(ENTTYPE_PROJECT))	
			name = "Project";
		else if(s1.equals(ENTTYPE_TASK))	
			name = "Task";
		else if(s1.equals(ENTTYPE_DIVISION))	
			name = "Division";
		else if(s1.equals(ENTTYPE_BRANCH))	
			name = "Branch";
		else if(s1.equals(ENTTYPE_DEPARTMENT))	
			name = "Department";
		else if(s1.equals(ENTTYPE_WORKCENTER))	
			name = "Work Center";
			
		return name;
	}

}