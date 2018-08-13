//Source file: W:\\Framework-2.1\\source\\com\\cap\\wdf\\command\\CommandErrorException.java

package wdf.command;


public class CommandErrorException extends CommandException 
{
   
   /**
   @param rootCause
   @roseuid 3C0D900001EE
    */
   public CommandErrorException(Throwable rootCause) 
   {
	super(rootCause);
   }
   
   /**
   @param errorMsg
   @roseuid 3C0D8FE60060
    */
   public CommandErrorException(String errorMsg) 
   {
	super(errorMsg);
   }

   public CommandErrorException(Throwable rootCause, String errorMsg) 
   {
	super(rootCause, errorMsg);
   }
}
