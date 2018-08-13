//Source file: W:\\Framework-2.1\\source\\com\\cap\\wdf\\inputhelper\\RangeValidator.java

package wdf.inputhelper;

import java.math.BigDecimal;
import java.util.Locale;

/**
A built-in logic validator for checking numeric values by a range.
 */
public class RangeValidator implements Validator 
{
   private BigDecimal lo;
   private BigDecimal hi;
   
   /**
   @param lo
   @param hi
   @roseuid 3C0D5F39004A
    */
   public RangeValidator(float lo, float hi) 
   {
	this.lo = new BigDecimal(new Float(lo).doubleValue());
	this.hi = new BigDecimal(new Float(hi).doubleValue());
   }
   
   /**
   @param lo
   @roseuid 3C0D5F2102D1
    */
   public RangeValidator(float lo) 
   {
	this.lo = new BigDecimal(new Float(lo).doubleValue());
   }
   
   /**
   Use this constructor to perform a "between.. and .." validation.
   @param lo
   @param hi
   @roseuid 3C0D676D0216
    */
   public RangeValidator(BigDecimal lo, BigDecimal hi) 
   {
	this.lo = lo;
	this.hi = hi;
   }
   
   /**
   Use this constructor to perform a "greater or equal" validation.
   @param lo
   @roseuid 3C0D67210040
    */
   public RangeValidator(BigDecimal lo) 
   {
	this.lo = lo;
   }
   
   /**
   @param locale
   @roseuid 3C0D6FA40119
    */
   public void setLocale(Locale locale) 
   {
    
   }
   
   /**
   @param value
   @return boolean
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D6FA400F0
    */
   public void validate(Object value) throws InputException 
   {
	BigDecimal valueBigDecimal = null;

	if (value instanceof BigDecimal)
		valueBigDecimal = (BigDecimal) value;
	else if (value instanceof Number)
		valueBigDecimal = new BigDecimal( ((Number) value).doubleValue() );
	else
		throw new InputException("RangeValidator works only with BigDecimal and Number object types");

	if (lo != null && valueBigDecimal.compareTo(lo) == -1)
		throw new InputException("Value must not be less than " + lo.toString());

	if (hi != null && valueBigDecimal.compareTo(hi) == 1)
		throw new InputException("Value must not be greater than " + hi.toString());
   }
}
