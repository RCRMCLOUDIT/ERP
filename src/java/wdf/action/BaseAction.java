package wdf.action;

import wdf.inputhelper.InputHelper;
import java.util.*;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Basic functionality implementation of Action. The application Actions must 
 * extend this class.
 */
public abstract class BaseAction extends HttpServlet implements FlowManager
{
	@Override
	public void callView(String name, HttpServletRequest request,
			HttpServletResponse response) throws ActionException {
		// TODOx Auto-generated method stub
	}

	@Override
	public void forwardAction(String name, HttpServletRequest request, HttpServletResponse response) throws ActionException 
	{
		//this.dispatchAction(name, request, response);
		try {
	  		RequestDispatcher dispatcher = request.getRequestDispatcher(name); 
	  		try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				this.log("ActionAdapter callPageNamed failed");
			} catch (IOException e) {
				this.log("ActionAdapter callPageNamed failed");
				e.printStackTrace();
			}  

		} catch (Exception e) { throw new ActionException(e,"Error dispatching action " + name); }
	}

	public void redirectAction(String name, HttpServletRequest request, HttpServletResponse response) throws ActionException 
	{
		String redirectURL = request.getContextPath() + name;
		
		//System.out.println("redirectAction wo map=" + redirectURL);
		try {
			response.sendRedirect(redirectURL);
		} catch (IOException e) { throw new ActionException(e,"Error redirecting to action " + name); }
	}

	public void redirectAction(String name, Map parameters, HttpServletRequest request, HttpServletResponse response) throws ActionException 
	{
		StringBuffer redirectURL = new StringBuffer();

		redirectURL.append(request.getContextPath());
		//redirectURL.append(this.getControllerPath(request));
		//redirectURL.append(createActionURI(null, request));
		redirectURL.append(name);
		//System.out.println("redirectURL=" + redirectURL.toString());
		
		if (!parameters.isEmpty()) {
			redirectURL.append("?");

			boolean isFirst = true;
			String parmName = null;
			Iterator parmNames = parameters.keySet().iterator();

			while (parmNames.hasNext()) {
				parmName = (String) parmNames.next();
				if (!isFirst)
					redirectURL.append('&');
				redirectURL.append(parmName);
				redirectURL.append('=');
				redirectURL.append(parameters.get(parmName));
				isFirst = false;
			}
		}

		
		try {
			response.sendRedirect(redirectURL.toString());
		} catch (IOException e) { throw new ActionException(e,"Error redirecting to action " + name); }
	}

	@Override
	public String getControllerPath(HttpServletRequest request)
			throws ActionException {

		return (String) request.getAttribute("Ximple_Action");
	}

	@Override
	public String getActionName(HttpServletRequest request)
			throws ActionException {
		// Return "", called from JSP page
		return ""; 
	}
	
	public String getActionNameJava(HttpServletRequest request) throws ActionException {
		// called from java
		return  request.getServletPath();
	}
	@Override
	public String createActionURI(String name, HttpServletRequest request) throws ActionException
	{
		
		if(name.equals(""))
			return request.getContextPath() + (String)request.getAttribute("Ximple_Action");
		else
			return request.getContextPath() + name;
	}

	@Override
	public void handleError(Throwable throwable, HttpServletRequest request, HttpServletResponse response) 
	{
		this.log(this.getServletName() + ": Error processing request URI: " + request.getRequestURI(), throwable);
		try {
			request.setAttribute("javax.servlet.jsp.jspException", throwable);
			//this.callErrorPage(request, response);
			//this.callView("ErrorPage", request, response);
			
	  		RequestDispatcher dispatcher = request.getRequestDispatcher("/erp/ERP_COMMON/error.jsp"); 
	  		try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				this.log("BaseAction call error page failed");
			} catch (IOException e) {
				this.log("BaseAction call error page failed");
				e.printStackTrace();
			}  
			
		} catch (Exception e) { 
				this.log(this.getServletName() + ": Recursive error: Error calling error page.");
		}
	}

	private static final String PARM_ERROR_MSG = "_errorMessage";
	private static final String PARM_ERROR_SEVERITY = "_errorSeverity";

    
    /**
     * @roseuid 3C0DA859004B
     * @J2EE_METHOD  --  createInputHelper
     * A facility to create a new InputHelper instance using Locale defined in ApplicationContext.
     */
    protected InputHelper createInputHelper    (HttpServletRequest request)  
    { 
	 	InputHelper inputHelper = new InputHelper(request, Locale.getDefault());
		return inputHelper;
    }
    
    /**
     * @roseuid 3C0D16290350
     * @J2EE_METHOD  --  addError
     */
    protected void addError (ActionError actionError, HttpServletRequest request)  
    { 
	 	Collection actionErrors = (Collection) request.getAttribute("actionErrors");
	
		if (actionErrors == null) {
			actionErrors = new ArrayList();
			request.setAttribute("actionErrors", actionErrors);
		}
	
		if (actionError != null)
			actionErrors.add(actionError);
	    
    }
    
    /**
     * @roseuid 3C0D16CC01E2
     * @J2EE_METHOD  --  getErrors
     */
    protected Collection getErrors (HttpServletRequest request)  
    { 
	 	Collection actionErrors = (Collection) request.getAttribute("actionErrors");
		return actionErrors;    
    }
    
    /**
     * @roseuid 3EA9CDEA006C
     * @J2EE_METHOD  --  perform
     */
    public void perform (HttpServletRequest request, HttpServletResponse response, FlowManager flowManager) throws ActionException 
    { 

    }

    public void perform (HttpServletRequest request, HttpServletResponse response) throws ActionException 
    { 

    }

    /**
     * @roseuid 3C0D51E80293
     * @J2EE_METHOD  --  service
     * Called by the servlet container to allow the servlet to respond to a request. 
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	//System.out.println("In Base Action service");
		if (request.getParameter(PARM_ERROR_MSG) != null) 
		{
			ActionError actionError = new ActionError();
			actionError.setMessage(request.getParameter(PARM_ERROR_MSG));
	
			if (request.getParameter(PARM_ERROR_SEVERITY) != null)
				actionError.setSeverity(Integer.parseInt(request.getParameter(PARM_ERROR_SEVERITY)));
	
			this.addError(actionError, request);
		}


		FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
    	//System.out.println("In Base Action service, servletPath=" + request.getServletPath());
    	request.setAttribute("Ximple_Action", request.getServletPath());
    	
		try {
			this.perform(request, response, flowManager);
	
		} catch (ActionException e) { flowManager.handleError(e, request, response); }    
    }
    
    /**
     * @roseuid 3C0D52FF001D
     * @J2EE_METHOD  --  BaseAction
     */
    public BaseAction ()  
    { 

    }
}