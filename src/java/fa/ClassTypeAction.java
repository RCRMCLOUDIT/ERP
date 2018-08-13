package com.cap.erp.fa;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import java.math.BigDecimal;
import com.cap.wdf.inputhelper.*;
import com.cap.wdf.command.*;
import com.cap.wdf.action.*;
import com.cap.util.*;
import java.util.*;


import com.cap.erp.common.*;


public class ClassTypeAction extends ActionAdapter
{
   private static String VIEW_LIST = "/erp/fa/class/typelist.jsp"; //"ClassTypeList";
   private static String VIEW_NEW  = "/erp/fa/class/typenew.jsp"; //"ClassTypeNew";
   private static String VIEW_EDIT = "/erp/fa/class/typeedit.jsp"; //"ClassTypeEdit";      
   
   private static String SQLSP_NAME = DBLibConstants.FALIB + "_OBJ.FACLSTYPS";

   public ClassTypeAction() 
   {
    
   }

   public void perform(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager) throws ActionException 
   {
		String message = request.getParameter("_message");
		if (message == null)
			message = "list";

		if (message.equals("list"))
			listMessage(request, response, flowManager, message);
		else if (message.equals("new"))
			newMessage(request, response, flowManager, message);
		else if (message.equals("add"))
			addMessage(request, response, flowManager, message);
		else if (message.equals("edit"))
			editMessage(request, response, flowManager, message);
		else if (message.equals("update"))
			updateMessage(request, response, flowManager, message);
		else if (message.equals("deactivate"))
			deactivateMessage(request, response, flowManager, message);
		else if (message.equals("reactivate"))
			deactivateMessage(request, response, flowManager, message);
		else if (message.equals("delete"))
			deleteMessage(request, response, flowManager, message);
   }
   
   public void listMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP_NAME);	
		setSPParameter(request, cmd, message);

		int pag = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

		try {
			cmd.execute();
			RowSet typeRS = cmd.getNextRowSet(pag,ConstantValue.PAGE_SIZE);
			cmd.close();

			request.setAttribute("typeRS", typeRS);
			flowManager.callView(VIEW_LIST, request, response);

		} catch (CommandException cmde) {cmd.close(); throw new ActionException(cmde); }
	} 

   public void editMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP_NAME);	
		setSPParameter(request, cmd, message);

		try {
			cmd.execute();
			RowSet typeRS = cmd.getNextRowSet();
			cmd.close();

			request.setAttribute("typeRS", typeRS);
			flowManager.callView(VIEW_EDIT, request, response);

		} catch (CommandException cmde) {cmd.close(); throw new ActionException(cmde); }
	} 
   
   
   public void newMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		flowManager.callView(VIEW_NEW, request, response);
   }

   public void addMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP_NAME);
		setSPParameter(request, cmd, message);
	
		try {
			cmd.execute();
			cmd.close();
			
			HashMap<String, String>redirectParameters = new HashMap<String, String>();
			redirectParameters.put("_message", "list");
			redirectParameters.put("page", request.getParameter("page"));
			redirectParameters.put("search", request.getParameter("search"));
			redirectParameters.put("filter", request.getParameter("filter")==null?"1":request.getParameter("filter"));
									
			flowManager.redirectAction(flowManager.getActionNameJava(request), redirectParameters, request, response);
			
		} catch (CommandException cmde) {
			cmd.close();

			if (cmde instanceof CommandErrorException) {
				ActionError error = new ActionError();
				error.setMessage(cmde.getMessage());
				error.setSeverity(ActionError.ERROR);
	
				this.addError(error, request);
	
				newMessage(request, response, flowManager, "new");
			} else
				throw new ActionException(cmde);
		}
	}
   

   public void updateMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP_NAME);
		setSPParameter(request, cmd, message);
	
		try {
			cmd.execute();
			cmd.close();
			
			HashMap<String, String>redirectParameters = new HashMap<String, String>();
			redirectParameters.put("_message", "list");
			redirectParameters.put("page", request.getParameter("page"));
			redirectParameters.put("search", request.getParameter("search"));
			redirectParameters.put("filter", request.getParameter("filter")==null?"1":request.getParameter("filter"));						
			flowManager.redirectAction(flowManager.getActionNameJava(request), redirectParameters, request, response);
			
		} catch (CommandException cmde) {
			cmd.close();

			if (cmde instanceof CommandErrorException) {
				ActionError error = new ActionError();
				error.setMessage(cmde.getMessage());
				error.setSeverity(ActionError.ERROR);
	
				this.addError(error, request);
	
				editMessage(request, response, flowManager, "edit");
			} else
				throw new ActionException(cmde);
		}
	}   
   public void deleteMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP_NAME);
		setSPParameter(request, cmd, message);

		try {
			cmd.execute();
			cmd.close();

			listMessage(request, response, flowManager, "list");
			
			/*			
			HashMap<String, String>redirectParameters = new HashMap<String, String>();
			redirectParameters.put("_message", "list");
			redirectParameters.put("customername", request.getParameter("customername")==null?"":java.net.URLDecoder.decode(request.getParameter("customername")));
			redirectParameters.put("customerEid", request.getParameter("customerEid")==null?"0":request.getParameter("customerEid"));						
			flowManager.redirectAction(flowManager.getActionNameJava(request), redirectParameters, request, response);
			*/
		} catch (CommandException cmde) {
			cmd.close();

			if (cmde instanceof CommandErrorException) {
				ActionError error = new ActionError();
				error.setMessage(cmde.getMessage());
				error.setSeverity(ActionError.ERROR);

				this.addError(error, request);

				listMessage(request, response, flowManager, "list");
			} else
				throw new ActionException(cmde);
		}
   }
   
   public void deactivateMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP_NAME);
		setSPParameter(request, cmd, message);

		try {
			cmd.execute();
			cmd.close();
			listMessage(request, response, flowManager, "list");
			/*
			HashMap<String, String>redirectParameters = new HashMap<String, String>();
			redirectParameters.put("_message", "list");
			redirectParameters.put("filter", request.getParameter("filter")==null?"1":request.getParameter("filter"));
			redirectParameters.put("page", request.getParameter("page")==null?"1":request.getParameter("page"));						
			flowManager.redirectAction(flowManager.getActionNameJava(request), redirectParameters, request, response);
			*/
		} catch (CommandException cmde) {
			cmd.close();

			if (cmde instanceof CommandErrorException) {
				ActionError error = new ActionError();
				error.setMessage(cmde.getMessage());
				error.setSeverity(ActionError.ERROR);

				this.addError(error, request);

				listMessage(request, response, flowManager, "list");
			} else
				throw new ActionException(cmde);
		}
   }   

   private void setSPParameter(HttpServletRequest request, Command cmd, String act) throws ActionException
   {
		cmd.setDebug(ConstantValue.DEBUG);
		InputHelper inputHelper = createInputHelper(request);
		try {
			
			BigDecimal typeId 	= (request.getParameter("typeId")==null || request.getParameter("typeId").trim().length()==0)?(ConstantValue.ZERO):(inputHelper.getParameterAsBigDecimal("typeId", new RequiredValidator()));
			String typeName		= request.getParameter("typeName")==null?"":request.getParameter("typeName").trim();			
			BigDecimal maxLevel = (request.getParameter("maxLevel")==null || request.getParameter("maxLevel").trim().length()==0)?(ConstantValue.ZERO):(inputHelper.getParameterAsBigDecimal("maxLevel", new RequiredValidator()));			
			String varLevel 	= request.getParameter("varLevel")==null?"":request.getParameter("varLevel").trim();			
			String multLevel 	= request.getParameter("multLevel")==null?"":request.getParameter("multLevel").trim();			
			String search		= request.getParameter("search")==null?"":request.getParameter("search").trim();
			String filter 		= request.getParameter("filter")==null?"2":request.getParameter("filter");
		
			cmd.addParameter(this.getCompanyId(request));
			cmd.addParameter(this.getCompanyEid(request));
			cmd.addParameter(typeId);
			cmd.addParameter(typeName);
			cmd.addParameter(maxLevel);
			cmd.addParameter(varLevel);
			cmd.addParameter(multLevel);
			cmd.addParameter(search);
			cmd.addParameter(filter);
			cmd.addParameter(act);
			cmd.addParameter(this.getLoggedUserId(request));
			cmd.addParameter(Format.getIPAddress(request));

		}
		catch (InputException e) {
			throw new ActionException(e);
		}
   }

}