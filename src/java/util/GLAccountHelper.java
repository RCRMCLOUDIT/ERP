package com.cap.util;

import java.sql.SQLException;
import javax.sql.RowSet;
import com.cap.wdf.command.CachedRowSet;

/**
 * Insert the type's description here.
 * Creation date: (5/10/2004 7:38:51 PM)
 * @author:
 */
public class GLAccountHelper {

    private RowSet GLAccountRS = null; // Case cursor in RowSet

	private StringBuffer buff = new StringBuffer();
	
	private boolean odd = true;
	private String allowParent = "N";
	private String searchByNumber = "N";
    private int page = 0;
    
    /* Constants */
	static final int TYPECODE_COLUMN = 1;
	static final int TYPENAME_COLUMN = 2;
	static final int LEVEL1_COLUMN = 3;
	static final int LEVEL2_COLUMN = 4;
	static final int LEVEL3_COLUMN = 5;
	static final int LEVEL4_COLUMN = 6;
	static final int LEVEL5_COLUMN = 7;
	static final int ACTNAME1_COLUMN = 8;
	static final int ACTNAME2_COLUMN = 9;
	static final int ACTNAME3_COLUMN = 10;
	static final int ACTNAME4_COLUMN = 11;
	static final int ACTNAME5_COLUMN = 12;
	static final int ACTNUM1_COLUMN = 13;
	static final int ACTNUM2_COLUMN = 14;
	static final int ACTNUM3_COLUMN = 15;
	static final int ACTNUM4_COLUMN = 16;
	static final int ACTNUM5_COLUMN = 17;

	static final int GL_LEVEL1 = 1;
	static final int GL_LEVEL2 = 2;
	static final int GL_LEVEL3 = 3;
	static final int GL_LEVEL4 = 4;
	static final int GL_LEVEL5 = 5;

    /**
     * constructor comment.
     */
    public GLAccountHelper() {
        super();
    }
	/**
	 * Insert the method's description here.
	 * Creation date: (5/10/2004 7:47:26 PM)
	 * @return java.lang.String
	 */
	public void buildLines() {
		
		int lineIndex = 0;
		int rowCount = 0;
		
		String level1 = "";
		String level2 = "";
		String level3 = "";
		String level4 = "";
		String level5 = "";
		String name1 = "";
		String name2 = "";
		String name3 = "";
		String name4 = "";
		String name5 = "";
		String num1 = "";
		String num2 = "";
		String num3 = "";
		String num4 = "";
		String num5 = "";
		
		String typeName = "";

		String preLevel1 = "";
		String preLevel2 = "";
		String preLevel3 = "";
		String preLevel4 = "";
		//String preLevel5 = "";
		
		//int lineIndex = 0;
		
		buff.delete(0, buff.length());
        
		try
		{
			//boolean debug = false;
			
			while (GLAccountRS.next() && rowCount < ConstantValue.POPUP_PAGE_SIZE)
			{
				level1 = GLAccountRS.getString(LEVEL1_COLUMN).trim();
				level2 = GLAccountRS.getString(LEVEL2_COLUMN).trim();
				level3 = GLAccountRS.getString(LEVEL3_COLUMN).trim();
				level4 = GLAccountRS.getString(LEVEL4_COLUMN).trim();
				level5 = GLAccountRS.getString(LEVEL5_COLUMN).trim();
				
				name1 = GLAccountRS.getString(ACTNAME1_COLUMN).trim();
				name2 = GLAccountRS.getString(ACTNAME2_COLUMN).trim();
				name3 = GLAccountRS.getString(ACTNAME3_COLUMN).trim();
				name4 = GLAccountRS.getString(ACTNAME4_COLUMN).trim();
				name5 = GLAccountRS.getString(ACTNAME5_COLUMN).trim();
/*
				if(level1.equals("023") && level2.equals("003") && level3.equals("001"))
				{
					debug = true;
				
				System.out.println("level1=" + level1);
				System.out.println("level2=" + level2);
				System.out.println("level3=" + level3);
				System.out.println("level4=" + level4);
				System.out.println("level5=" + level5);

				System.out.println("name1=" + name1);
				System.out.println("name2=" + name2);
				System.out.println("name3=" + name3);
				System.out.println("name4=" + name4);
				System.out.println("name5=" + name5);

				System.out.println("searchByNumber=" + searchByNumber);

				System.out.println("preLevel1=" + preLevel1);
				System.out.println("preLevel2=" + preLevel2);
				System.out.println("preLevel3=" + preLevel3);
				System.out.println("preLevel4=" + preLevel4);
				System.out.println("preLevel5=" + preLevel5);

				}
				*/
				if(searchByNumber.equalsIgnoreCase("Y"))
				{
					num1 = GLAccountRS.getString(ACTNUM1_COLUMN).trim();
					num2 = GLAccountRS.getString(ACTNUM2_COLUMN).trim();
					num3 = GLAccountRS.getString(ACTNUM3_COLUMN).trim();
					num4 = GLAccountRS.getString(ACTNUM4_COLUMN).trim();
					num5 = GLAccountRS.getString(ACTNUM5_COLUMN).trim();
					
					if (num1.length() > 0)
						name1 = num1 + "-" + name1;
					if (num2.length() > 0)
						name2 = num2 + "-" + name2;
					if (num3.length() > 0)
						name3 = num3 + "-" + name3;
					if (num4.length() > 0)
						name4 = num4 + "-" + name4;
					if (num5.length() > 0)
						name5 = num5 + "-" + name5;
						
				}
				
				typeName = GLAccountRS.getString(TYPENAME_COLUMN).trim();

				rowCount++;
				
				//print level1 gl account
				if(!level1.equals(preLevel1))
				{
					if(level2.equals("000") || allowParent.equalsIgnoreCase("Y"))
					{
						//if(debug)
						//	System.out.println("in Level 1-1");
						
						buildJSArray(lineIndex, name1);
						buildHtmlLine(lineIndex++, level1 + "000000000000", name1, GL_LEVEL1, true, typeName); 
					}
					else
					{
						//if(debug)
						//	System.out.println("in Level 1-2");
						buildHtmlLine(0, "", name1, GL_LEVEL1, false, typeName); 
					}
					preLevel2 = "";
					preLevel3 = "";
					preLevel4 = "";
					//preLevel5 = "";
					
				}
				boolean preLevelPrinted = false;
				
				if(!level2.equals(preLevel2) && !level2.equals("000"))
				{
					//print level2 gl account
					if( allowParent.equalsIgnoreCase("N"))
					{
						if( level3.equals("000"))
						{
							buildJSArray(lineIndex, name1 + " - " + name2);
							buildHtmlLine(lineIndex++, level1 + level2 + "000000000", name2, GL_LEVEL2, true, typeName); 
						}
						else if( !level3.equals("000"))
						{ 
							preLevelPrinted = true;
							buildHtmlLine(0, "", name2, GL_LEVEL2, false, typeName); 
						}
					}
					else if( allowParent.equalsIgnoreCase("Y"))
					{
						buildJSArray(lineIndex, name1 + " - " + name2);
						buildHtmlLine(lineIndex++, level1 + level2 + "000000000", name2, GL_LEVEL2, true, typeName); 
					}
				}
				
				if(( !level3.equals(preLevel3) || preLevelPrinted) && !level3.equals("000"))
				{
					preLevelPrinted = false;
					//print level3 gl account
					if( allowParent.equalsIgnoreCase("N"))
					{
						if( level4.equals("000"))
						{
							buildJSArray(lineIndex, name1 + " - " + name2 + " - " + name3);
							buildHtmlLine(lineIndex++, level1 + level2 + level3 + "000000", name3, GL_LEVEL3, true, typeName); 
						}
						else if( !level4.equals("000"))
						{
							buildHtmlLine(0, "", name3, GL_LEVEL3, false, typeName); 
							preLevelPrinted = true;
						}
					}
					else if( allowParent.equalsIgnoreCase("Y"))
					{
						buildJSArray(lineIndex, name1 + " - " + name2 + " - " + name3);
						buildHtmlLine(lineIndex++, level1 + level2 + level3 + "000000", name3, GL_LEVEL3, true, typeName); 
					}
				}
				
				if((!level4.equals(preLevel4)|| preLevelPrinted) && !level4.equals("000"))
				{
					//print level4 gl account
					if( allowParent.equalsIgnoreCase("N"))
					{
						if( level5.equals("000"))
						{
							buildJSArray(lineIndex, name1 + " - " + name2 + " - " + name3 + " - " +name4);
							buildHtmlLine(lineIndex++, level1 + level2 + level3 + level4 + "000", name4, GL_LEVEL4, true, typeName); 
						}
						else if( !level5.equals("000"))
						{
							buildHtmlLine(0, "", name4, GL_LEVEL4, false, typeName); 
						}
					}
					else if( allowParent.equalsIgnoreCase("Y"))
					{
						buildJSArray(lineIndex, name1 + " - " + name2 + " - " + name3 + " - " +name4);
						buildHtmlLine(lineIndex++, level1 + level2 + level3 + level4 + "000", name4, GL_LEVEL4, true, typeName); 
					}
				}
				
				//print level5 gl account
				if( !level5.equals("000"))
				{
					buildJSArray(lineIndex, name1 + " - " + name2 + " - " + name3 + " - " + name4 + " - " + name5);
					buildHtmlLine(lineIndex++, level1 + level2 + level3 + level4 + level5, name5, GL_LEVEL5, true, typeName); 
				}
				
				preLevel1 = level1;
				preLevel2 = level2;
				preLevel3 = level3;
				preLevel4 = level4;
				//preLevel5 = level5;
				
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
	public void buildHtmlLine(int index, String id, String name, int level, boolean selectableFlag, String typName) {
		if(buff == null)
			return;
		else
		{			
			buff.append("<TR class=\"");
			buff.append(odd?"pcrinfo":"pcrinfo1");
			
			if(selectableFlag == true)
			{
				buff.append("\" onmouseover=\"changeColorBox(this,1);\" onmouseout=\"changeColorBox(this,0);\" ");
				buff.append("onclick=\"passBack('");
				buff.append(id);		
				buff.append("', aname[" + index + "]);\">\n");
				buff.append("<TD width=\"70%\" nowrap>");
			}
			else
			{
				buff.append("\" >\n");
				buff.append("<TD width=\"70%\" nowrap class='link_alt_underline_G'>");
			}

			switch (level) {
				case 1 : buff.append("&nbsp;"); break;
				case 2 : buff.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						 break;
				case 3 : buff.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"); 
						 break;
				case 4 : buff.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"); 
						 break;
				case 5 : buff.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"); 
						 break;
				default :
					break;
			}
			buff.append(name);
			//buff.append("</TD>\n");
			buff.append("</TD>\n <TD width=\"30%\">");
			buff.append(typName);
			buff.append("</TD>\n");
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
			GLAccountRS.beforeFirst();
			
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
    public javax.sql.RowSet getGLAccountRS() {
        return GLAccountRS;
    }

    /**
     * Insert the method's description here.
     * Creation date: (5/11/2004 4:40:51 PM)
     * @param newRowSetCursor javax.sql.RowSet
     */
    public void setGLAccountRS(RowSet newRowSetCursor) {
		GLAccountRS = newRowSetCursor;
        try {
			GLAccountRS.beforeFirst();
        } catch (Exception e) {
			GLAccountRS = null;
        }        
    }

	/**
	 * Insert the method's description here.
	 * Creation date: (10/28/2004 4:40:51 PM)
	 * @param 
	 */
	public String getOnLoadStr() {
		if(page != 1)
			return "";

		try 
		{
			//String tempStr = "";
			//String tempNum = "";
			
			if(((CachedRowSet)GLAccountRS).getRowSetSize() == 1)
			{
				GLAccountRS.beforeFirst();
				GLAccountRS.next();

				String level1 = GLAccountRS.getString(LEVEL1_COLUMN).trim();
				String level2 = GLAccountRS.getString(LEVEL2_COLUMN).trim();
				String level3 = GLAccountRS.getString(LEVEL3_COLUMN).trim();
				String level4 = GLAccountRS.getString(LEVEL4_COLUMN).trim();
				String level5 = GLAccountRS.getString(LEVEL5_COLUMN).trim();
				String name2 = GLAccountRS.getString(ACTNAME2_COLUMN).trim();
				
				/*
				String name1 = GLAccountRS.getString(ACTNAME1_COLUMN).trim();
				String name3 = GLAccountRS.getString(ACTNAME3_COLUMN).trim();
				String name4 = GLAccountRS.getString(ACTNAME4_COLUMN).trim();
				String name5 = GLAccountRS.getString(ACTNAME5_COLUMN).trim();

				String num1 = "";
				String num2 = "";
				String num3 = "";
				String num4 = "";
				String num5 = "";
				if(searchByNumber.equalsIgnoreCase("Y"))
				{
					num1 = GLAccountRS.getString(ACTNUM1_COLUMN).trim();
					num2 = GLAccountRS.getString(ACTNUM2_COLUMN).trim();
					num3 = GLAccountRS.getString(ACTNUM3_COLUMN).trim();
					num4 = GLAccountRS.getString(ACTNUM4_COLUMN).trim();
					num5 = GLAccountRS.getString(ACTNUM5_COLUMN).trim();
				}
				*/
				
				buff.delete(0, buff.length());
				
				if(allowParent.equalsIgnoreCase("N") || name2.length() == 0)
				{	/*
					tempStr = name1 + 
								(name2.length() ==0?"":" - " + name2) +
								(name3.length() ==0?"":" - " + name3) +
								(name4.length() ==0?"":" - " + name4) +
								(name5.length() ==0?"":" - " + name5);
					if(searchByNumber.equalsIgnoreCase("Y"))
					{
						if(name5.length() > 0)
							tempNum = num5.length()>0?(num5 + " - ") : "";
						else if (name4.length() > 0)
							tempNum = num4.length()>0?(num4 + " - ") : "";
						else if (name3.length() > 0)
						tempNum = num3.length()>0?(num3 + " - ") : "";
						else if (name2.length() > 0)
						tempNum = num2.length()>0?(num2 + " - ") : "";
						else if (name1.length() > 0)
						tempNum = num1.length()>0?(num1 + " - ") : "";
					}
					*/
						
					buff.append("passBack('");
					buff.append(level1);
					buff.append(level2);
					buff.append(level3);
					buff.append(level4);
					buff.append(level5);
					buff.append("',");
					buff.append("aname[0]); close();");
					//buff.append(tempNum);
					//buff.append(tempStr);				
					//buff.append("'); close();");
					
					return buff.toString();
				}
				else
				{
					return "";					
				}
				
			}
			else
				return "";
				
		} catch (Exception e) {
			GLAccountRS = null;
			return "";
		}        
	}

	/**
	 * @return
	 */
	public String getAllowParent() {
		return allowParent;
	}

	/**
	 * @param string
	 */
	public void setAllowParent(String str) {
		allowParent = str;
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
	public String getSearchByNumber() {
		return searchByNumber;
	}

	/**
	 * @param string
	 */
	public void setSearchByNumber(String string) {
		searchByNumber = string;
	}

}