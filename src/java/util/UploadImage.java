package com.cap.util;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

//import java.math.BigDecimal;
import com.cap.erp.SPDBBean;
import com.cap.erp.common.ActionAdapter;
import com.cap.wdf.action.ActionException;
import com.cap.wdf.action.FlowManager;

public class UploadImage extends ActionAdapter implements Serializable {

	SPDBBean databean = null;
	String ds = "";
	String path = "";

	//public void performTask(HttpServletRequest request, HttpServletResponse response)
	public void perform(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager) throws ActionException 
	{
		try
		{
			InputStream is = request.getInputStream();

			//Create byte holders
			byte[] imageId = new byte[5];
			byte[] mime = new byte[30];
			byte[] imageSize = new byte[10];
			byte[] actionByte = new byte[2];
			byte[] image;

			//Read Data
			is.read(imageId);
			is.read(mime);
			is.read(imageSize);
			is.read(actionByte);

			//Format Data
			//BigDecimal ID 	= new BigDecimal(new String(imageId).trim());
			String compId 	= new String(imageId).trim();
			//String mimetype = new String(mime).trim();
			//BigDecimal size = new BigDecimal(new String(imageSize).trim());
			String action 	= new String(actionByte).trim();
			int sizeInt 	= Integer.parseInt(new String(imageSize).trim());

			//get byte[] of image
			image = new byte[sizeInt];
			int remaining = image.length;
			while (remaining>0)
			{
				remaining -= is.read(image, image.length - remaining, remaining);
			}
			is.close();

			//initialize DBBean
			/*databean = new com.cap.erp.SPDBBean();
			ds = DBLibConstants.DATASOURCE;

			StringBuffer sql = new StringBuffer();
			 	sql.append("{call " + DBLibConstants.FILIB + ".complogos('");
			 	sql.append(compId);
			 	sql.append("','");
			 	sql.append(mimetype);
			 	sql.append("',");
			 	sql.append(sizeInt);
			 	sql.append("',");
			 	sql.append(image);
			 	sql.append("',");
			 	sql.append(action);
			 	sql.append("',");
		 		sql.append("?,?)} ");

		 	System.out.println("upload image sql string = " + sql.toString());

			databean.setSQLString(sql.toString());
			databean.setDataSourceName(ds);
			databean.execute();*/

    		if(action.equals("BN"))
    			path = "/www/webserver/htdocs/PortalCompBanner";
    		if(action.equals("CK"))
    			path = "/www/webserver/htdocs/PortalCompCheckLogo";
    		if(action.equals("LG"))
    			path = "/www/webserver/htdocs/PortalCompLogo";
    		if(action.equals("PD"))
    			path = "/www/webserver/htdocs/PortalCompPDFLogo";

			//write to web folder
			FileOutputStream fos = new FileOutputStream(path + "/" + compId + ".gif");
			fos.write(image);
			fos.close();

			goToPage(request, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}

	private void goToPage(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		ServletOutputStream out = response.getOutputStream();
		out.println(response.encodeRedirectURL("http://" + request.getServerName() + request.getContextPath() + "/erp/setup/logos/autoclose.html"));
	    out.flush();
	    out.close();
  	}

}