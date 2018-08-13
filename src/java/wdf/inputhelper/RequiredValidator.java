//Source file: W:\\Framework-2.1\\source\\com\\cap\\wdf\\inputhelper\\RequiredValidator.java

package wdf.inputhelper;

import java.util.Locale;

/**
A built-in logic validator for required parameters of any type. For a parameter 
not passed (null) o empty string (.trim().length() == 0) must be failed 
validation.
 */
public class RequiredValidator implements Validator 
{
   
   /**
   Default constructor of RequiredValidator.
   @roseuid 3C0D6FA203A1
    */
   public RequiredValidator() 
   {
    
   }
   
   /**
   @param locale
   @roseuid 3C0D66A002D0
    */
   public void setLocale(Locale locale) 
   {
    
   }
   
   /**
   @param value
   @return boolean
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D667C035A
    */
   public void validate(Object value) throws InputException 
   {
	if (value == null)
		throw new InputException("Must not be null");
	else
		if (value instanceof String)
			if (((String) value).trim().length() == 0)
				throw new InputException("Must not be empty or blanks");
   }
}
