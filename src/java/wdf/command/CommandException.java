//Source file: W:\\Framework-2.1\\source\\com\\cap\\wdf\\command\\CommandException.java

package wdf.command;

import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class CommandException extends Exception 
{
   protected Throwable rootCause;
   
   public CommandException(String errorMsg) 
   {
	super(errorMsg);
   }
   
   public CommandException(Throwable rootCause) 
   {
	this.rootCause = rootCause;
   }

   public CommandException(Throwable rootCause, String errorMsg) 
   {
	super(errorMsg);
	this.rootCause = rootCause;
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
   @roseuid 3C0D8F1B009A
    */
   public String toString() 
   {
	if (this.rootCause != null)
		return rootCause.toString();
	else
		return super.toString();
   }
   
   /**
   @roseuid 3C0D8F280071
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
   @roseuid 3C0D8F3602D4
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
   @roseuid 3C0D8F5E0335
    */
   public void printStackTrace(PrintWriter out) 
   {
	if (this.rootCause != null)
		rootCause.printStackTrace(out);
	else
		super.printStackTrace(out);
   }
}
