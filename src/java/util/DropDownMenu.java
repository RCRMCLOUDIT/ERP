//------------------------------------------------------------------------------
// Copyright 2002 iCAP Computer Consultants, Inc.
//
// Owner: WLIU
//
// Class: DropDownMenu.java
// Revision:
//	07/20/2002 - Created
//------------------------------------------------------------------------------
package com.cap.util;
/**
 * Insert the type's description here.
 * Creation date: (7/19/2002 9:20:30 AM)
 * @author:
 */
public class DropDownMenu
{
	/**
 	 * DropDownMenu constructor comment.
 	 */
	public DropDownMenu()
	{
		super();
	}

	public static String printDropDownMenuOpenTag()
	{
		StringBuffer menu = new StringBuffer();
		menu.append("\n<script language=\"JavaScript\">\n");
		menu.append("function Go(){return}\n");
		return menu.toString();

	}
	public static String printDropDownMenuCloseTag()
	{
		StringBuffer menu = new StringBuffer();
		menu.append("</script>\n");
		menu.append("<script type='text/javascript' src='/Portal/dropdownmenu_var.js'></script>\n");
		menu.append("<script type='text/javascript' src='/Portal/dropdownmenu_com.js'></script>\n");
		menu.append("<noscript>Your browser does not support script</noscript>\n");
		return menu.toString();

	}

	public static String printItem(String strParent, int child, String anchor, String link,
								 int numOfOptions, int width, int height)
	{
		StringBuffer menu = new StringBuffer();

  		menu.append(strParent);
  		//System.out.println("strParent = " + strParent);
  		menu.append(child);
  		//System.out.println("child = " + child);
  		menu.append("=new Array(\"");
  		menu.append(anchor);
  		menu.append("\",\"");
  		//menu.append(link);
  		//menu.append(javascript:top.document.location.href='Link.htm';);

		//StringBuffer search = new StringBuffer(link);
		/*
        if (link.indexOf("type=CK") != -1)
		{
			menu.append("javascript:window.open(\'"+link+"\',\'_blank\',\'top=130,left=130,width=650, status=yes, height=450, scrollbars=yes,resizable=yes,toolbar=no\')");
		}
		else
		{
			menu.append("javascript:window.open(\'"+link+"\',\'_blank\',\'top=130,left=130,width=650, status=yes, height=450, scrollbars=yes,resizable=yes,toolbar=yes\')");
		}
		*/
		/* allow menu bar */
		//menu.append("javascript:window.open(\'"+link+"\',\'_blank\',\'top=130,left=130,width=655, status=yes, height=450, scrollbars=yes,resizable=yes,toolbar=yes\')");
		menu.append("javascript:window.open(\'"+link+"\',\'_blank\',\'top=80,left=80,width=800, status=yes, height=500, scrollbars=yes,resizable=yes,toolbar=no\')");

  		menu.append("\",\"\",");
  		menu.append(numOfOptions);
  		menu.append(",");
  		menu.append(height);
  		menu.append(",");
  		menu.append(width);
  		menu.append(");\n");
  		//System.out.println("Str = " + menu.toString());

  		return menu.toString();

	}

	public static String printItemPath(String strParent, int child, String anchor, String link,
								 int numOfOptions, int width, int height)
	{
		StringBuffer menu = new StringBuffer();

  		menu.append(strParent);
  		menu.append(child);
  		menu.append("=new Array(\"");
  		menu.append(anchor);
  		menu.append("\",\"\",\"\",");
  		menu.append(numOfOptions);
  		menu.append(",");
  		menu.append(height);
  		menu.append(",");
  		menu.append(width);
  		menu.append(");\n");

  		return menu.toString();

	}

	public static String printMainPath(String strParent, int child, String anchor, String link,
								 int numOfOptions, int width, int height)
	{
		StringBuffer menu = new StringBuffer();

  		menu.append(strParent);
  		menu.append(child);
  		menu.append("=new Array(\"");
  		menu.append(anchor);
  		menu.append("\",\"" + link + "\",\"\",");
  		menu.append(numOfOptions);
  		menu.append(",");
  		menu.append(height);
  		menu.append(",");
  		menu.append(width);
  		menu.append(");\n");

  		return menu.toString();
	}

static final String removeAllWhitespace(String s){
   String temp = s.trim();
   StringBuffer sb = new StringBuffer(temp);
   for(int i= 0;i < sb.length();i++){
     char c = sb.charAt(i);
     if (c == 0x20 || c == 0x09 || c == 0x0A || c == 0x0D){
       sb.deleteCharAt(i);
     }
   }
   return sb.toString();
 }


}
