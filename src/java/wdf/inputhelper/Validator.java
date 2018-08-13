//Source file: W:\\Framework-2.1\\source\\com\\cap\\wdf\\inputhelper\\Validator.java

package wdf.inputhelper;

import java.util.Locale;

/**
Interface that must be implemented by class for logic validation
 */
public interface Validator 
{
   
   /**
   Peform validation on object, returns FALSE if the validation is failed.
   @param value
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D650C0058
    */
   public void validate(Object value) throws InputException;
   
   /**
   Set locale to be used to text parsing operations.
   @param locale
   @roseuid 3C0D66420248
    */
   public void setLocale(Locale locale);
}
