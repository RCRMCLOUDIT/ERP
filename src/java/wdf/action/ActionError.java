//Source file: W:\\Framework-2.1\\source\\com\\cap\\wdf\\action\\ActionError.java

package wdf.action;


/**
Message errors sent by Actions to be showed by View components
 */
public class ActionError 
{
   public static int INFO = 0;
   public static int WARNING = 1;
   public static int ERROR = 2;
   private int severity;
   private String message;
   
   /**
   @roseuid 3C0D6F9B02CE
    */
   public ActionError() 
   {
	this.severity = ERROR;
   }


   public ActionError(String aMessage) 
   {
	this.message = aMessage;
	this.severity = ERROR;
   }
   
   /**
   Access method for the severity property.
   
   @return   the current value of the severity property
    */
   public int getSeverity() 
   {
      return severity;    
   }
   
   /**
   Sets the value of the severity property.
   
   @param aSeverity the new value of the severity property
    */
   public void setSeverity(int aSeverity) 
   {
      severity = aSeverity;    
   }
   
   /**
   Access method for the message property.
   
   @return   the current value of the message property
    */
   public String getMessage() 
   {
      return message;    
   }
   
   /**
   Sets the value of the message property.
   
   @param aMessage the new value of the message property
    */
   public void setMessage(String aMessage) 
   {
      message = aMessage;    
   }
}
