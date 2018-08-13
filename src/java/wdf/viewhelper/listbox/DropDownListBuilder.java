//Source file: W:\\Framework-2.1\\servlet\\com\\cap\\wdf\\viewhelper\\listbox\\DropDownListBuilder.java

package com.cap.wdf.viewhelper.listbox;


public class DropDownListBuilder 
{
   private String name;
   private StringBuffer scriptBuffer;
   
   /**
   @param name
   @roseuid 3C0D51E2031B
    */
   public DropDownListBuilder(String name) 
   {
	this.name=name;

	this.scriptBuffer = new StringBuffer();

	scriptBuffer.append("listBoxValues['");
	scriptBuffer.append(this.name);
	scriptBuffer.append("'] = new Array();\n");
 
	scriptBuffer.append("listBoxText1['");
	scriptBuffer.append(this.name);
	scriptBuffer.append("'] = new Array();");
 
	scriptBuffer.append("listBoxText2['");
	scriptBuffer.append(this.name);
	scriptBuffer.append("'] = new Array();");

	scriptBuffer.append("listBoxOffSetX['");
	scriptBuffer.append(this.name);
	scriptBuffer.append("'] = -113;");
  
   }
   
   /**
   @roseuid 3C0D51A90265
    */
   public DropDownListBuilder() 
   {
    
   }
   
   /**
   Access method for the name property.
   
   @return   the current value of the name property
    */
   public String getName() 
   {
      return name;    
   }
   
   /**
   Sets the value of the name property.
   
   @param aName the new value of the name property
    */
   public void setName(String aName) 
   {
      name = aName;    
   }
   
   /**
   @param title
   @param description
   @roseuid 3C0D50B300A8
    */
   public void addElement(String title, String description) 
   {

	scriptBuffer.append("addData('0','");
	scriptBuffer.append(title);
	scriptBuffer.append("','");
	scriptBuffer.append(description);
	scriptBuffer.append("','");
	scriptBuffer.append(this.name);
	scriptBuffer.append("');");
    
   }
   
   /**
   @param value
   @param title
   @param description
   @roseuid 3C0D50B8034E
    */
   public void addElement(String value, String title, String description) 
   {
	scriptBuffer.append("addData('");
	scriptBuffer.append(value);
	scriptBuffer.append("','");
	scriptBuffer.append(title);
	scriptBuffer.append("','");
	scriptBuffer.append(description);
	scriptBuffer.append("','");
	scriptBuffer.append(this.name);
	scriptBuffer.append("');");
    
   }
   
   /**
   @return java.lang.String
   @roseuid 3C0D5B5903BF
    */
   public String getScript() 
   {
	scriptBuffer.append("writeTable('");
	scriptBuffer.append(this.name);
	scriptBuffer.append("');");
	return scriptBuffer.toString();
   }
}
