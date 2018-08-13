//Source file: W:\\Framework-2.1\\source\\com\\cap\\wdf\\action\\ActionException.java

package wdf.action;

import java.io.*;


/**
Exceptional situations not handled by Actions. Contrary to situations reported 
by ActionError, an ActionException is a situation not handled by an Action.
 */
public class ActionException extends Exception 
{
   
	private Throwable rootCause;

   /**
   @param rootCause
   @param message
   @roseuid 3C0D557D0147
    */
   public ActionException(Throwable rootCause, String message) 
   {
	super(message);
	this.rootCause = rootCause;    
   }
   
   /**
   @param rootCause
   @roseuid 3C0D556602A3
    */
   public ActionException(Throwable rootCause) 
   {
	this.rootCause = rootCause;    
   }
   
   /**
   @param message
   @roseuid 3C0D284C01E8
    */
   public ActionException(String message) 
   {
	super(message);    
   }

   /**
   @return java.lang.String
   @roseuid 3C0D8EF4024D
    */
   public String getLocalizedMessage() 
   {
	if (this.rootCause != null)
		return rootCause.getLocalizedMessage();
	else
		return super.getLocalizedMessage();
   }
   
   /**
   @return java.lang.String
   @roseuid 3C0D8F100167
    */
   public String getMessage() 
   {
	if (this.rootCause != null)
		return rootCause.getMessage();
	else
		return super.getMessage();
   }
   
   /**
   @return java.lang.String
    */
   public String toString() 
   {
	if (this.rootCause != null)
		return rootCause.toString();
	else
		return super.toString();
   }
   
   /**
    */
   public void printStackTrace() 
   {
	if (this.rootCause != null)
		rootCause.printStackTrace();
	else
		super.printStackTrace();
   }
   
   /**
   @param out
    */
   public void printStackTrace(PrintStream out) 
   {
	if (this.rootCause != null)
		rootCause.printStackTrace(out);
	else
		super.printStackTrace(out);
   }
   
   /**
   @param out
    */
   public void printStackTrace(PrintWriter out) 
   {
	if (this.rootCause != null)
		rootCause.printStackTrace(out);
	else
		super.printStackTrace(out);
   
   }
}
