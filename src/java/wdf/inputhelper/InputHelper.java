
package wdf.inputhelper;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.math.BigDecimal;
import java.text.*;

/**
Perform text parsing to convert actions parameters to specified datatypes, also 
make parameter validations easy using classes that implements Validator 
interface.
 */
public class InputHelper 
{
	private HttpServletRequest parameters;
	private Locale locale;
   
   /**
   Creates a new InputHelper with a specified locale
   @param parameters - A parameter list to validate
   @param locale - A locale to use for parsing texts
   @roseuid 3C0D645D0287
    */
   public InputHelper(HttpServletRequest parameters, Locale locale) 
   {
	this.parameters = parameters;
	this.locale = locale;
   }
   
   /**
   Creates a new InputHelper with JVM defaults locale.
   @param request - A parameter list to validate
   @roseuid 3C0D6423003F
    */
   public InputHelper(HttpServletRequest request) 
   {
	//this.parameters = parameters;
	this.parameters = request;
   }
   
   /**
   Get a string parameter
   @param name
   @return java.lang.String
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D63550165
    */
   public String getParameterAsString(String name) throws InputException 
   {
	return this.parameters.getParameter(name);
   }
   
   /**
   Get a parameter as int (primitive)
   @param name
   @return int
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D637E01D2
    */
   public int getParameterAsInt(String name) throws InputException 
   {
	int value = 0;
	try {
		if (this.parameters.getParameter(name) != null)
			value = Integer.parseInt( this.parameters.getParameter(name) );
	} catch (NumberFormatException e) { throw new InputException("Error parsing '" + name + "' to int"); }

	return value;
   }
   
   /**
   @param name
   @return java.lang.Integer
   @roseuid 3C0D193400C5
    */
   public Integer getParameterAsInteger(String name) throws InputException 
   {
	Integer value = null;
	try {
		if (this.parameters.getParameter(name) != null)
			value = new Integer( this.parameters.getParameter(name) );
	} catch (NumberFormatException e) { throw new InputException("Error parsing '" + name + "' to Integer"); }

	return value;
   }
   
   /**
   Get a parameter as BigDecimal
   @param name
   @return java.math.BigDecimal
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D6388014A
    */
   public BigDecimal getParameterAsBigDecimal(String name) throws InputException 
   {
	BigDecimal value = null;
	try {
		if (this.parameters.getParameter(name) != null)
			value = new BigDecimal( this.parameters.getParameter(name) );
	} catch (NumberFormatException e) { throw new InputException("Error parsing '" + name + "' to BigDecimal"); }

	return value;
   }
   
   /**
   Get a parameter as Date
   @param name
   @return java.util.Date
   @roseuid 3C0D63E601BD
    */
   public java.util.Date getParameterAsDate(String name) throws InputException 
   {
	DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, this.locale);
	Date value = null;
	try {
		if (this.parameters.getParameter(name) != null)
			value = df.parse( this.parameters.getParameter(name) );
	} catch (ParseException e) { throw new InputException("Error parsing '" + name + "' to Date"); }

	return value;
   }
   
   /**
   @param name
   @param validator
   @return java.lang.String
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D19920193
    */
   public String getParameterAsString(String name, Validator validator) throws InputException 
   {
	String value = this.getParameterAsString(name);
	validator.setLocale(this.locale);
	try {
		validator.validate(value);
	} catch (InputException e) { 
		throw new InputException("Validation failed on '" + name + "': " + e.getMessage());
	}
	return value;
   }
   
   /**
   @param name
   @param validator
   @return int
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D19B9012B
    */
   public int getParameterAsInt(String name, Validator validator) throws InputException 
   {
	Integer value = this.getParameterAsInteger(name);
	validator.setLocale(this.locale);
	try {
		validator.validate(value);
	} catch (InputException e) { 
		throw new InputException("Validation failed on '" + name + "': " + e.getMessage());
	}
	return value.intValue();
   }
   
   /**
   @param name
   @param validator
   @return java.lang.Integer
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D1A0100A2
    */
   public Integer getParameterAsInteger(String name, Validator validator) throws InputException 
   {
	Integer value = this.getParameterAsInteger(name);
	validator.setLocale(this.locale);
	try {
		validator.validate(value);
	} catch (InputException e) { 
		throw new InputException("Validation failed on '" + name + "': " + e.getMessage());
	}
	return value;
   }
   
   /**
   @param name
   @param validator
   @return java.math.BigDecimal
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D19C30035
    */
   public BigDecimal getParameterAsBigDecimal(String name, Validator validator) throws InputException 
   {
	BigDecimal value = this.getParameterAsBigDecimal(name);
	validator.setLocale(this.locale);
	try {
		validator.validate(value);
	} catch (InputException e) { 
		throw new InputException("Validation failed on '" + name + "': " + e.getMessage());
	}
	return value;
   }
   
   /**
   @param name
   @param validator
   @return java.util.Date
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D19D902DF
    */
   public Date getParameterAsDate(String name, Validator validator) throws InputException 
   {
	Date value = this.getParameterAsDate(name);
	validator.setLocale(this.locale);
	try {
		validator.validate(value);
	} catch (InputException e) { 
		throw new InputException("Validation failed on '" + name + "': " + e.getMessage());
	}
	return value;
   }
   
   /**
   @param name
   @param index
   @return java.lang.String
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D1BEF03D8
    */
   public String getParameterAsString(String name, int index) throws InputException 
   {
	String values[] = this.parameters.getParameterValues(name);
	if (index < values.length)
		return values[index];
	else
		return null;
   }
   
   /**
   @param name
   @param index
   @return int
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D1BF902CE
    */
   public int getParameterAsInt(String name, int index) throws InputException 
   {
	String values[] = this.parameters.getParameterValues(name);

	if (index < values.length) {
		try {
			return Integer.parseInt( values[index] );
		} catch (NumberFormatException e) { 
			throw new InputException("Error parsing '" + name + "' [" + index + "] to int");
		}
	} else {
		return 0;
	}
   }
   
   /**
   @param name
   @param index
   @return java.lang.Integer
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D1C090078
    */
   public Integer getParameterAsInteger(String name, int index) throws InputException 
   {
	String values[] = this.parameters.getParameterValues(name);

	if (index < values.length) {
		try {
			return new Integer( values[index] );
		} catch (NumberFormatException e) {
			throw new InputException("Error parsing '" + name + "' [" + index + "] to Integer");
		}
	} else {
		return null;
	}
   }
   
   /**
   @param name
   @param index
   @return java.math.BigDecimal
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D1C150396
    */
   public BigDecimal getParameterAsBigDecimal(String name, int index) throws InputException 
   {
	String values[] = this.parameters.getParameterValues(name);

	if (index < values.length) {
		try {
			return new BigDecimal( values[index] );
		} catch (NumberFormatException e) {
			throw new InputException("Error parsing '" + name + "' [" + index + "] to BigDecimal");
		}
	} else {
		return null;
	}
   }
   
   /**
   @param name
   @param index
   @return java.util.Date
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D1C23035A
    */
   public Date getParameterAsDate(String name, int index) throws InputException 
   {
	String values[] = this.parameters.getParameterValues(name);

	if (index < values.length) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, this.locale);
		try {
			return df.parse( values[index] );
		} catch (ParseException e) {
			throw new InputException("Error parsing '" + name + "' [" + index + "] to Date");
		}
	} else {
		return null;
	}

   }
   
   /**
   @param name
   @param index
   @param validator
   @return java.lang.String
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D1C290348
    */
   public String getParameterAsString(String name, int index, Validator validator) throws InputException 
   {
	String value = this.getParameterAsString(name, index);
	validator.setLocale(this.locale);
	try {
		validator.validate(value);
	} catch (InputException e) { 
		throw new InputException("Validation failed on '" + name + "' [" + index + "]: " + e.getMessage());
	}
	return value;
   }
   
   /**
   @param name
   @param index
   @param validator
   @return int
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D1C5B03AE
    */
   public int getParameterAsInt(String name, int index, Validator validator) throws InputException 
   {
	Integer value = this.getParameterAsInteger(name, index);
	validator.setLocale(this.locale);
	try {
		validator.validate(value);
	} catch (InputException e) { 
		throw new InputException("Validation failed on '" + name + "' [" + index + "]: " + e.getMessage());
	}
	return value.intValue();
   }
   
   /**
   @param name
   @param index
   @param validator
   @return java.lang.Integer
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D1C83004E
    */
   public Integer getParameterAsInteger(String name, int index, Validator validator) throws InputException 
   {
	Integer value = this.getParameterAsInteger(name, index);
	validator.setLocale(this.locale);
	try {
		validator.validate(value);
	} catch (InputException e) { 
		throw new InputException("Validation failed on '" + name + "' [" + index + "]: " + e.getMessage());
	}
	return value;
   }
   
   /**
   @param name
   @param index
   @param validator
   @return java.math.BigDecimal
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D1CBB0316
    */
   public BigDecimal getParameterAsBigDecimal(String name, int index, Validator validator) throws InputException 
   {
	BigDecimal value = this.getParameterAsBigDecimal(name, index);
	validator.setLocale(this.locale);
	try {
		validator.validate(value);
	} catch (InputException e) { 
		throw new InputException("Validation failed on '" + name + "' [" + index + "]: " + e.getMessage());
	}
	return value;
   }
   
   /**
   @param name
   @param index
   @param validator
   @return java.util.Date
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D1CEE0233
    */
   public Date getParameterAsDate(String name, int index, Validator validator) throws InputException 
   {
	Date value = this.getParameterAsDate(name, index);
	validator.setLocale(this.locale);
	try {
		validator.validate(value);
	} catch (InputException e) { 
		throw new InputException("Validation failed on '" + name + "' [" + index + "]: " + e.getMessage());
	}
	return value;
   }
   
   /**
   Perform validation on specified parameter with provided validator, if the 
   validation fails throws a InputException
   @param name
   @param validator
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D65790221
    */
   public void validate(String name, Validator validator) throws InputException 
   {
	String value = this.getParameterAsString(name);
	validator.setLocale(this.locale);
	try {
		validator.validate(value);
	} catch (InputException e) { 
		throw new InputException("Validation failed on '" + name + "': " + e.getMessage());
	}
   }
   
   /**
   @param name
   @param index
   @param validator
   @throws com.cap.wdf.inputhelper.InputException
   @roseuid 3C0D1B6B0088
    */
   public void validate(String name, int index, Validator validator) throws InputException 
   {
	String value = this.getParameterAsString(name, index);
	validator.setLocale(this.locale);
	try {
		validator.validate(value);
	} catch (InputException e) { 
		throw new InputException("Validation failed on '" + name + "' [" + index + "]: " + e.getMessage());
	}
   }
   
   /**
   Test validation on specified parameter with privided validator, a difference 
   from validate method is that isValid doesn't throws any exception, usefull for 
   a "if" predicate
   @param name
   @param validator
   @return boolean
   @roseuid 3C0D679C0282
    */
   public boolean isValid(String name, Validator validator) 
   {
	try {
		this.validate(name, validator);
	} catch (InputException e) { 
		return false;
	}

	return true;
   }
   
   /**
   @param name
   @param index
   @param validator
   @return boolean
   @roseuid 3C0D1BB9015C
    */
   public boolean isValid(String name, int index, Validator validator) 
   {
	try {
		this.validate(name, index, validator);
	} catch (InputException e) { 
		return false;
	}

	return true;
   }
}
