//Source file: W:\\Framework-2.1\\source\\com\\cap\\wdf\\command\\CommandFatalException.java

package wdf.command;


public class CommandFatalException extends CommandException 
{
  
   /**
   @param rootCause
   @roseuid 3C0D922B021E
    */
   public CommandFatalException(Throwable rootCause) 
   {
	super(rootCause);    
   }
   
   /**
   @param errorMsg
   @roseuid 3C0D90360079
    */
   public CommandFatalException(String errorMsg) 
   {
	super(errorMsg);
   }

   public CommandFatalException(Throwable rootCause, String errorMsg) 
   {
	super(rootCause, errorMsg);
   }
}
