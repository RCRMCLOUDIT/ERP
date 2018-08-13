//Source file: W:\\FrameworkProposal\\src\\com\\cap\\wdf\\viewhelper\\treeview\\glTreeViewBuilderTag.java

package com.cap.wdf.viewhelper.treeview;

import javax.servlet.jsp.tagext.TagSupport;
import javax.sql.RowSet;
import javax.servlet.jsp.JspException;

public class glTreeViewBuilderTag extends TagSupport 
{
   private RowSet treeData;
   private TreeViewBuilder treeViewBuilder;
   
   /**
   @roseuid 3C0D268F02D9
    */
   public glTreeViewBuilderTag() 
   {
    
   }
   
   /**
   Access method for the treeData property.
   
   @return   the current value of the treeData property
    */
   public RowSet getTreeData() 
   {
      return treeData;
   }
   
   /**
   Sets the value of the treeData property.
   
   @param aTreeData the new value of the treeData property
    */
   public void setTreeData(RowSet aTreeData) 
   {
      treeData = aTreeData;
   }
   
   /**
   @return int
   @throws javax.servlet.jsp.JspException
   @roseuid 3C0D1F4200B5
    */
   public int doStartTag() throws JspException 
   {
    return 0;
   }
   
   /**
   @return java.lang.String
   @roseuid 3C0D265D03DB
    */
   public String setId() 
   {
    return null;
   }
   
   /**
   @return java.lang.String
   @roseuid 3C0D2669007B
    */
   public String setName() 
   {
    return null;
   }
}
