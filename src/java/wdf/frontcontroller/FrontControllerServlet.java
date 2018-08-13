package wdf.frontcontroller;

//import com.ibm.servlet.PageListServlet;

import wdf.action.BaseAction;
import wdf.action.FlowManager;
import wdf.action.ApplicationContext;
import wdf.action.ActionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.*;

/**
Receive request for web clients and maps to corresponding Action, also is 
Action dispatcher and View dispatcher. The Views are dispatched according to 
Locale (language + country) and Mime-Type information.
 */
public class FrontControllerServlet extends BaseAction implements FlowManager 
{
	protected static final String KEY_USER = "portal_PortalUser";

	private static final String KEY_CONTROLLER_PATH = "com.cap.wdf.frontcontroller.FrontControllerServlet.controllerPath";
	private static final String KEY_ACTION_NAME = "com.cap.wdf.frontcontroller.FrontControllerServlet.actionName";
   
   /**
   @param name
   @param request
   @param response
   @throws com.cap.wdf.action.ActionException
   @roseuid 3C0D507D0000
    */
	public void callPageNamed(String pageName, HttpServletRequest request, HttpServletResponse response) throws ActionException
	{
		//this.dispatchAction(pageName, request, response);
  		RequestDispatcher dispatcher = request.getRequestDispatcher(pageName); 
  		try {
			dispatcher.forward(request, response);
		
  		} catch (ServletException e) {
			this.log("FrontController callPageNamed failed");
		} catch (IOException e) {
			this.log("FrontController callPageNamed failed");
			e.printStackTrace();
		}  

	}
	
   public void callView(String name, HttpServletRequest request, HttpServletResponse response) throws ActionException 
   {
	 //this.dispatchAction(name, request, response);    
	   this.callPageNamed(name, request, response);
   }
	
   public void callTimeoutView( HttpServletRequest request, HttpServletResponse response) throws ActionException
   {
		callView("/erp/ERP_COMMON/timeout.jsp", request, response);	
   }
   public void callErrorView( HttpServletRequest request, HttpServletResponse response) throws ActionException
   {
		callView("/erp/ERP_COMMON/error.jsp", request, response);	
   }
   public void callNoDataView( HttpServletRequest request, HttpServletResponse response) throws ActionException
   {
		callView("/erp/ERP_COMMON/noData.jsp", request, response);	
   }

   /**
   @param name
   @param request
   @throws com.cap.wdf.action.ActionException
    */
   public String createActionURI(String name, HttpServletRequest request) throws ActionException
   {
    return request.getContextPath() + this.getControllerPath(request) + name;
   }
   /**
   @param request
   @param response
   @throws com.cap.wdf.action.ActionException
   @roseuid 3C0D56810375
    */
   protected void dispatchAction(String name, HttpServletRequest request, HttpServletResponse response) throws ActionException 
   {
		request.setAttribute("flowManager", this);

		try {
			this.callView(name, request, response);
		} catch (Exception e) { throw new ActionException(e,"Error dispatching action " + name); }
   }

   /**
   @param name
   @param request
   @param response
   @throws com.cap.wdf.action.ActionException
   @roseuid 3C0D1E8A0001
    */
   public void forwardAction(String name, HttpServletRequest request, HttpServletResponse response) throws ActionException 
   {
	this.dispatchAction(name, request, response);    
   }
   /**
   @param request
   @throws com.cap.wdf.action.ActionException
    */
   public String getActionName(HttpServletRequest request) throws ActionException
   {
	return (String) request.getAttribute(KEY_ACTION_NAME);
   }
   public String getActionNameJava(HttpServletRequest request) throws ActionException
   {
	return "";
   }
   /**
   @return com.cap.wdf.action.ApplicationContext
   @roseuid 3C0D535901ED
    */
   public ApplicationContext getApplicationContext() 
   {
    return null;
   }
   /**
   @param request
   @throws com.cap.wdf.action.ActionException
    */
   public String getControllerPath(HttpServletRequest request) throws ActionException
   {
	return (String) request.getAttribute(KEY_CONTROLLER_PATH);
   }
   /**
   @param throwable
   @param request
   @param response
   @roseuid 3C0D163F02E7
    */
   
   public void handleError(Throwable throwable, HttpServletRequest request, HttpServletResponse response) 
   {
	this.log(this.getServletName() + ": Error processing request URI: " + request.getRequestURI(), throwable);
	try {
		request.setAttribute("javax.servlet.jsp.jspException", throwable);
		//this.callErrorPage(request, response);
		this.callView("ErrorPage", request, response);
		
	} catch (Exception e) { 
		this.log(this.getServletName() + ": Recursive error: Error calling error page.");
	}
   }

   /**
   @param servletConfig
   @roseuid 3C0D3F70038B
    */
   public void init(ServletConfig servletConfig) throws ServletException
   {
	   super.init(servletConfig);
   }
   /**
   @param name
   @param parameters
   @param request
   @param response
   @throws com.cap.wdf.action.ActionException
   @roseuid 3C0D23E301C2
    */
   public void redirectAction(String name, Map parameters, HttpServletRequest request, HttpServletResponse response) throws ActionException 
   {
	StringBuffer redirectURL = new StringBuffer();

	redirectURL.append(request.getContextPath());
	redirectURL.append(this.getControllerPath(request));
	redirectURL.append(name);

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
   /**
   @param name
   @param request
   @param response
   @throws com.cap.wdf.action.ActionException
   @roseuid 3C0D522900E2
    */
   public void redirectAction(String name, HttpServletRequest request, HttpServletResponse response) throws ActionException 
   {
	String redirectURL = request.getContextPath() + this.getControllerPath(request) + name;

	try {
		response.sendRedirect(redirectURL);
	} catch (IOException e) { throw new ActionException(e,"Error redirecting to action " + name); }
   }
   /**
   @param request
   @param response
   @throws java.io.IOException
   @throws javax.servlet.ServletException
   @roseuid 3C0D36540356
    */
   public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
   {
		System.out.println("FrontControllerServlet in service");

		String servletPath = request.getServletPath();
		String controllerPath = servletPath.substring(0,servletPath.lastIndexOf('/')+1);
		String actionName = servletPath.substring(servletPath.lastIndexOf('/')+1);

		request.setAttribute(KEY_CONTROLLER_PATH , controllerPath);
		request.setAttribute(KEY_ACTION_NAME, actionName);

		System.out.println("servletPath = " + servletPath);
		System.out.println("controllerPath = " + controllerPath);
		System.out.println("actionName = " + actionName);
        
	   	try {
	   		
	   		request.setAttribute("flowManager", this);

			this.dispatchAction(actionName, request, response);
			/*
	   		RequestDispatcher rd = request.getRequestDispatcher(servletPath);   
	        rd.forward(request, response);
	   		*/
	   	} catch (Throwable e) { 
			this.handleError(e, request, response);
		} 
		   
   }
   /*
	public void service (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("I am here in service");
		//FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
		try {
			if (!isLogged(request))
			{
				if( request.getParameter("_act") != null && 
					(request.getParameter("_act").equals("LOGOUT") || request.getParameter("_act").equals("Login")))
				{//logout or load login entry page, 

					super.service(request, response); //proceed logout
				}
				else if(request.getParameter("LGTS") != null &&  request.getParameter("portallogin") != null &&
						request.getParameter("portalpassword") != null )
				{
					super.service(request, response); //proceed 
				}
				else
				{
					//System.out.println("I am here:" + flowManager.toString());
					//flowManager.callView(VIEW_TIMEOUT, request, response);
				   RequestDispatcher dispatcher = request.getRequestDispatcher("/erp/ERP_COMMON/timeout.jsp");   
				   dispatcher.forward(request, response);  
				}
			}
			else
			{
				System.out.println("I am here b");

				super.service(request, response);
				System.out.println("I am here c");
			}

		} //catch (ActionException e) { handleError( request, response, e); }
		catch (Exception e) { handleError( request, response, e); }
	}
   */
    /*
    public void performTask(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
    {
    	
    }
	
    public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
    {
    	System.out.println("Do get called");
        performTask(httpservletrequest, httpservletresponse);
    }

    public void doPost(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
    {
    	System.out.println("Do post called");
        performTask(httpservletrequest, httpservletresponse);
    }
	*/
   public String getParameterString(HttpServletRequest request, String paramName)
   {	//get parameter from request as String
	   return request.getParameter(paramName)==null?"":request.getParameter(paramName).trim();
   }
   
   public void handleError(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object obj)
   {
       if(obj instanceof Throwable)
       {
           Throwable throwable = (Throwable)obj;
           System.out.println("[" + getServletName() + "-error]" + throwable.toString());
       }
       else
       {
           System.out.println("[" + getServletName() + "-error]" + obj.toString());
       }
       
       try
       {
           //callErrorPage(httpservletrequest, httpservletresponse, obj);
			RequestDispatcher dispatcher=httpservletrequest.getRequestDispatcher("/erp/ERP_COMMON/error.jsp"); 
			dispatcher.forward(httpservletrequest, httpservletresponse);  

       }
       catch(Exception exception)
       {
           try
           {
               if(obj == null)
               {
               		//HttpServletResponse _tmp = httpservletresponse;
                   httpservletresponse.sendError(500);
               }
               else
               {
                   //HttpServletResponse _tmp1 = httpservletresponse;
                   httpservletresponse.sendError(500, obj.toString());
               }
           }
           catch(IOException ioexception)
           {
               System.out.println("[IO error]" + ioexception.getMessage());
           }
           return;
       }
   }

	protected boolean isLogged(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute(KEY_USER) != null)
			return true;
		else
			return false;
	}

}
