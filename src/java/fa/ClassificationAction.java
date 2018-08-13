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


public class ClassificationAction extends ActionAdapter
{
   private static String VIEW_LIST = "/erp/fa/class/classlist.jsp"; //"ClassList";
   private static String VIEW_NEW  = "/erp/fa/class/classnew.jsp"; //"ClassNew";
   private static String VIEW_VIEW  = "/erp/fa/class/classview.jsp"; //"ClassView";
   private static String VIEW_EDIT  = "/erp/fa/class/classedit.jsp"; //"ClassEdit";
   private static String VIEW_SEARCH = "/erp/fa/class/classsearch.jsp"; //"ClassSearch";      
   

   private static String SQLSP_NAME = DBLibConstants.FALIB + "_OBJ.FACLASS";
   private static String SQLSP1_NAME = DBLibConstants.FALIB + "_OBJ.FACLSSRCHS";

   public ClassificationAction() 
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
		else if (message.equals("edit"))
			editMessage(request, response, flowManager, message);
		else if (message.equals("add"))
			addMessage(request, response, flowManager, message);
		else if (message.equals("delete"))
			deleteMessage(request, response, flowManager, message);
		else if (message.equals("view"))
			viewMessage(request, response, flowManager, message);		
		else if (message.equals("update"))
			updateMessage(request, response, flowManager, message);				
		else if (message.equals("reactivate"))
			reactivateMessage(request, response, flowManager, message);	
		else if (message.equals("deactivate"))
			deactivateMessage(request, response, flowManager, message);
		else if (message.equals("search"))
			searchMessage(request, response, flowManager, message);								
   }
   
   public void listMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP_NAME);	
		setSPParameter(request, cmd, message);

		int pag = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

		try {
			cmd.execute();
			RowSet classRS = cmd.getNextRowSet(pag,ConstantValue.PAGE_SIZE);
			cmd.close();

			request.setAttribute("classRS", classRS);
			flowManager.callView(VIEW_LIST, request, response);

		} catch (CommandException cmde) {cmd.close(); throw new ActionException(cmde); }
	} 
   
   public void viewMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP_NAME);	
		setSPParameter(request, cmd, message);

		try {
			cmd.execute();
			RowSet classRS 	= cmd.getNextRowSet();
			RowSet pathRS	= cmd.getNextRowSet();
			RowSet typeRS = cmd.getNextRowSet();
			cmd.close();

			request.setAttribute("classRS", classRS);
			request.setAttribute("pathRS", pathRS);		
			request.setAttribute("typeRS", typeRS);
	
			flowManager.callView(VIEW_VIEW, request, response);

		} catch (CommandException cmde) {cmd.close(); throw new ActionException(cmde); }
	} 
	
	public void newMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
	   	Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP_NAME);	
		setSPParameter(request, cmd, message);

		try {
			cmd.execute();
			RowSet typeRS = cmd.getNextRowSet();
			RowSet pathRS = cmd.getNextRowSet();
			cmd.close();

			request.setAttribute("typeRS", typeRS);
			request.setAttribute("pathRS", pathRS);			
			flowManager.callView(VIEW_NEW, request, response);

		} 
		catch (CommandException cmde) 
		{
			cmd.close(); 
			throw new ActionException(cmde); 
		}
   }
   
	public void editMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
	   	Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP_NAME);	
		setSPParameter(request, cmd, message);

		try {
			cmd.execute();
			RowSet classRS = cmd.getNextRowSet();
			RowSet pathRS = cmd.getNextRowSet();
			RowSet typeRS = cmd.getNextRowSet();
			cmd.close();

			request.setAttribute("classRS", classRS);
			request.setAttribute("pathRS", pathRS);			
			request.setAttribute("typeRS", typeRS);	
			flowManager.callView(VIEW_EDIT, request, response);

		} 
		catch (CommandException cmde) 
		{
			cmd.close(); 
			throw new ActionException(cmde); 
		}
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
			redirectParameters.put("filter", request.getParameter("filter"));
			redirectParameters.put("page", request.getParameter("page"));
			
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
			redirectParameters.put("filter", request.getParameter("filter"));
			redirectParameters.put("page", request.getParameter("page"));
			
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
			
			HashMap<String, String>redirectParameters = new HashMap<String, String>();
			redirectParameters.put("_message", "list");
			redirectParameters.put("filter", request.getParameter("filter"));
			redirectParameters.put("page", request.getParameter("page"));
			
			flowManager.redirectAction(flowManager.getActionNameJava(request), redirectParameters, request, response);

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
			
			HashMap<String, String>redirectParameters = new HashMap<String, String>();
			redirectParameters.put("_message", "list");
			redirectParameters.put("filter", request.getParameter("filter"));
			redirectParameters.put("page", request.getParameter("page"));
			flowManager.redirectAction(flowManager.getActionNameJava(request), redirectParameters, request, response);

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
   
   public void reactivateMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP_NAME);
		setSPParameter(request, cmd, message);

		try {
			cmd.execute();
			cmd.close();
			
			HashMap<String, String>redirectParameters = new HashMap<String, String>();
			redirectParameters.put("_message", "list");
			redirectParameters.put("filter", request.getParameter("filter"));
			redirectParameters.put("page", request.getParameter("page"));
			
			flowManager.redirectAction(flowManager.getActionNameJava(request), redirectParameters, request, response);

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
			BigDecimal classId = (request.getParameter("classId")==null || request.getParameter("classId").trim().length()==0)?(ConstantValue.ZERO):(inputHelper.getParameterAsBigDecimal("classId", new RequiredValidator()));
			BigDecimal classTypeId = (request.getParameter("classTypeId")==null || request.getParameter("classTypeId").trim().length()==0)?(ConstantValue.ZERO):(inputHelper.getParameterAsBigDecimal("classTypeId", new RequiredValidator()));
			BigDecimal parentId = (request.getParameter("parentId")==null || request.getParameter("parentId").trim().length()==0)?(ConstantValue.ZERO):(inputHelper.getParameterAsBigDecimal("parentId", new RequiredValidator()));			
			String name	= request.getParameter("name")==null?"":request.getParameter("name").trim();
			String isLastLevel	= request.getParameter("isLastLevel")==null?"":request.getParameter("isLastLevel").trim();						
			//BigDecimal level = (request.getParameter("level")==null || request.getParameter("level").trim().length()==0)?(ConstantValue.ZERO):(inputHelper.getParameterAsBigDecimal("level", new RequiredValidator()));
			String filter = request.getParameter("filter")==null?"2":request.getParameter("filter");
		
			cmd.addParameter(this.getCompanyId(request));
			cmd.addParameter(classId);
			cmd.addParameter(classTypeId);
			cmd.addParameter(parentId);
			cmd.addParameter(name);
			cmd.addParameter(isLastLevel);
			cmd.addParameter(filter);

			cmd.addParameter(act);
			cmd.addParameter(this.getLoggedUserId(request));
			cmd.addParameter(Format.getIPAddress(request));

		}
		catch (InputException e) {
			throw new ActionException(e);
		}
   }
   public void searchMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
	 {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP1_NAME);	
		setSP1Parameter(request, cmd, message);
		int pag = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

		 try 
		 {
			 cmd.execute();
			 RowSet classRS = cmd.getNextRowSet(pag,ConstantValue.POPUP_PAGE_SIZE);
			 cmd.close();

			 request.setAttribute("classRS", classRS);
			 flowManager.callView(VIEW_SEARCH, request, response);
		 } 
		 catch (CommandException cmde) 
		 {
			 cmd.close(); 
			 throw new ActionException(cmde); 
		 }
	 }

	 private void setSP1Parameter(HttpServletRequest request, Command cmd, String act) throws ActionException
	 {
		cmd.setDebug(ConstantValue.DEBUG);
	 	InputHelper inputHelper = createInputHelper(request);
		
		try 
		{
			String search = request.getParameter("search") == null ? "" : request.getParameter("search");
			BigDecimal classTypeId = (request.getParameter("classTypeId")==null || request.getParameter("classTypeId").trim().length()==0)?(ConstantValue.ZERO):(inputHelper.getParameterAsBigDecimal("classTypeId", new RequiredValidator()));						
						  
			cmd.addParameter(this.getCompanyId(request));
			//cmd.addParameter(this.getCompanyEid(request));
			cmd.addParameter(classTypeId);
			cmd.addParameter(search);
			cmd.addParameter(act);
		 }
		 catch (Exception e) {
			 throw new ActionException(e);
		 }
	}	

}