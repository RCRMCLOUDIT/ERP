//Source file: W:\\Framework-2.1\\source\\com\\cap\\wdf\\action\\FlowManager.java

package wdf.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface FlowManager 
{
   
   /**
   @param name
   @param request
   @param response
   @throws com.cap.wdf.action.ActionException
   @roseuid 3C0D0CE20110
    */
   public void callView(String name, HttpServletRequest request, HttpServletResponse response) throws ActionException;
   /**
   @param name
   @param request
   @param response
   @throws com.cap.wdf.action.ActionException
   @roseuid 3C0D0D6A0379
    */
   public void forwardAction(String name, HttpServletRequest request, HttpServletResponse response) throws ActionException;
   
   /**
   @param name
   @param request
   @param response
   @throws com.cap.wdf.action.ActionException
   @roseuid 3C0D0DC8031A
    */
   public void redirectAction(String name, HttpServletRequest request, HttpServletResponse response) throws ActionException;
   
   /**
   @param name
   @param parameters
   @param request
   @param response
   @throws com.cap.wdf.action.ActionException
   @roseuid 3C0D2322008F
    */
   public void redirectAction(String name, Map parameters, HttpServletRequest request, HttpServletResponse response) throws ActionException;

   /**
   @param request
   @throws com.cap.wdf.action.ActionException
    */
   public String getControllerPath(HttpServletRequest request) throws ActionException;

   /**
   @param request
   @throws com.cap.wdf.action.ActionException
    */
   public String getActionName(HttpServletRequest request) throws ActionException;


   public String getActionNameJava(HttpServletRequest request) throws ActionException;

   /**
   @param name
   @param request
   @throws com.cap.wdf.action.ActionException
    */
   public String createActionURI(String name, HttpServletRequest request) throws ActionException;

  
   /**
   @param throwable
   @param request
   @param response
   @roseuid 3C0D88FF0158
    */
   public void handleError(Throwable throwable, HttpServletRequest request, HttpServletResponse response);
}
