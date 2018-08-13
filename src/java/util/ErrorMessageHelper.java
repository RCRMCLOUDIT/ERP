/*
 * Created on Feb 25, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cap.util;

import java.util.*;

import com.cap.wdf.action.*;

/**
 * @author yanwan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ErrorMessageHelper {

	private Object errorMessage = null;
	private String contextPath = "";
	private String messageType = ""; //I, W, E
	
	private StringBuffer buff = new StringBuffer();
	
	/**
	 * @param string
	 */
	public void setErrorMessage(Object obj) {
		errorMessage = obj;
	}

	/**
	 * @param string
	 */
	public String getHtmlErrorMessage() {
		if(errorMessage == null)
			return "";

		else if(errorMessage instanceof String)
		{
			//System.out.println("A");
			String msg = (String) errorMessage;
			if(msg.trim().length() ==0)
				return "";
			
			return buildMsgFromString(msg);
		}
		else if(errorMessage instanceof ArrayList)
		{
			//System.out.println("b");
			return buildMsgFromCollection((Collection)errorMessage);			
		}
		else
		{
			//System.out.println("c");
			return buildMsgFromString(errorMessage.toString());
		}
	
	}

	private String buildMsgFromString(String str)
	{
		int severity = 2; //0 =info, 1 = warning, 2 = error
		
		if(errorMessage instanceof ActionError)
		{
			severity = ((ActionError)errorMessage).getSeverity();
			if(severity == 0)
				messageType = "I";
			else if(severity == 1)
				messageType = "W";
		}
		buff.append("<TABLE width=\"600\" cellpadding=\"0\" cellspacing=\"0\" \n");
		if(messageType.equals("I"))
			buff.append("<TR><TD class=\"textInfo\"> <IMG src=\"");
		else if(messageType.equals("W"))
			buff.append("<TR><TD class=\"textWarning\"> <IMG src=\"");
		else
			buff.append("<TR><TD class=\"textRed\"> <IMG src=\"");
			
		buff.append(contextPath);
		if(messageType.equals("I"))
			buff.append("/erp/ERP_COMMON/images/info.gif\" width=\"16\" height=\"16\" border=\"0\"> ");
		else if(messageType.equals("W"))
			buff.append("/erp/ERP_COMMON/images/warning.gif\" width=\"16\" height=\"16\" border=\"0\"> ");
		else
			buff.append("/erp/ERP_COMMON/images/error.gif\" width=\"16\" height=\"16\" border=\"0\"> ");
				
				
		Vector v = Format.getErrorMsgVector(str);
		
		for(int i=0; i< v.size(); i++)
		{
			if (i==0)
			{
				buff.append("* ");
				buff.append(v.elementAt(i));
				buff.append("<br>");
			}
			else
			{
				buff.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* ");
				buff.append(v.elementAt(i));
				buff.append("<br>");
			}
		}
		
		buff.append("\n</TD></TR></TABLE>\n");		
		
		return buff.toString();
	}

	private String buildMsgFromCollection(Collection c)
	{
		Iterator actionErrors = c.iterator();
		ActionError error = null;

		buff.append("<TABLE width=\"600\" cellpadding=\"0\" cellspacing=\"0\" \n");
		buff.append("<TR><TD class=\"textRed\">");

		while (actionErrors.hasNext()) {
			error = (ActionError) actionErrors.next();

			if (error.getMessage() != null)
			{
				Vector v = Format.getErrorMsgVector(error.getMessage());
				
				for(int i=0; i< v.size(); i++)
				{
					if(error.getSeverity() == ActionError.INFO)
					{
						buff.append("<IMG src=\"");
						buff.append(contextPath);
						buff.append("/erp/ERP_COMMON/images/info.gif\" width=\"16\" height=\"16\" border=\"0\"> ");
						buff.append("<SPAN class=\"textInfo\">");
					}
					else if(error.getSeverity() == ActionError.WARNING)
					{
						buff.append("<IMG src=\"");
						buff.append(contextPath);
						buff.append("/erp/ERP_COMMON/images/warning.gif\" width=\"16\" height=\"16\" border=\"0\"> ");
						buff.append("<SPAN class=\"textWarning\">");
					}
					else 
					{
						buff.append("<IMG src=\"");
						buff.append(contextPath);
						buff.append("/erp/ERP_COMMON/images/error.gif\" width=\"16\" height=\"16\" border=\"0\"> ");
					}
					
					if (i==0)
					{
						buff.append("* ");
						buff.append(v.elementAt(i));
						buff.append("<br>");
					}
					else
					{
						buff.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* ");
						buff.append(v.elementAt(i));
						buff.append("<br>");
					}
					
					if(error.getSeverity() == ActionError.INFO || error.getSeverity() == ActionError.WARNING)
						buff.append("</SPAN>");
				}
			}
		}
		
		buff.append("\n</TD></TR></TABLE>\n");		
		
		return buff.toString();
	}


	/**
	 * @param string
	 */
	public void setContextPath(String string) {
		contextPath = string;
	}

	/**
	 * @param string
	 */
	public void setMessageType(String string) {
		messageType = string;
	}

}
