//Source file: W:\\Framework-2.1\\source\\com\\cap\\wdf\\viewhelper\\rowset\\RowSetIteratorTag.java

package com.cap.wdf.viewhelper.rowset;

import javax.servlet.jsp.tagext.TagSupport;
import javax.sql.RowSet;

public class RowSetIteratorTag extends TagSupport 
{
   private RowSet rowSet;
   
   /**
   @roseuid 3C0D835E0334
    */
   public RowSetIteratorTag() 
   {
    
   }
   
   /**
   Access method for the rowSet property.
   
   @return   the current value of the rowSet property
    */
   public RowSet getRowSet() 
   {
      return rowSet;
   }
   
   /**
   Sets the value of the rowSet property.
   
   @param aRowSet the new value of the rowSet property
    */
   public void setRowSet(RowSet aRowSet) 
   {
      rowSet = aRowSet;
   }
}
