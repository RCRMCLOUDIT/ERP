//Source file: W:\\Framework-2.1\\source\\com\\cap\\wdf\\action\\ApplicationContext.java

package wdf.action;

import java.util.Locale;
import java.security.Principal;

/**
Holds session application data, this class may be extended to know specific 
data application, a example of this companyId selected during Log In.
 */
public class ApplicationContext 
{
   private Locale locale;
   private Principal user;
   
   /**
   @roseuid 3C0D6F9C00D1
    */
   public ApplicationContext() 
   {
    
   }
   
   /**
   Access method for the locale property.
   
   @return   the current value of the locale property
    */
   public Locale getLocale() 
   {
      return locale;    
   }
   
   /**
   Sets the value of the locale property.
   
   @param aLocale the new value of the locale property
    */
   public void setLocale(Locale aLocale) 
   {
      locale = aLocale;    
   }
   
   /**
   Access method for the user property.
   
   @return   the current value of the user property
    */
   public Principal getUser() 
   {
      return user;    
   }
   
   /**
   Sets the value of the user property.
   
   @param aUser the new value of the user property
    */
   public void setUser(Principal aUser) 
   {
      user = aUser;    
   }
}
