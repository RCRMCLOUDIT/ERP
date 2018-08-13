//Source file: W:\\FrameworkProposal\\src\\com\\cap\\wdf\\viewhelper\\treeview\\TreeViewBuilder.java

package com.cap.wdf.viewhelper.treeview;

public class TreeViewBuilder
{
   private String id;
   private String name;
   private StringBuffer scriptBuffer;


   /**
   @roseuid 3C0D26900059
    */
   public TreeViewBuilder(String name,String id)
   {
	this.name = name;
	this.scriptBuffer = new StringBuffer();

	scriptBuffer.append(name);
	scriptBuffer.append("= gFld(\"\", \"\");\n");

	//scriptBuffer.append("\"");
	scriptBuffer.append(this.name);
	scriptBuffer.append(".treeID=\"");
	scriptBuffer.append(id);
	scriptBuffer.append("\";\n");
   }


   /**
   Access method for the id property.

    */
   public void setOption(String option, String value)
   {
	scriptBuffer.append(option + " = " + value + ";\n");
   }


   /**
   Access method for the id property.

   @return   the current value of the id property
    */
   public String getId()
   {
      return id;
   }

   /**
   Sets the value of the id property.

   @param aId the new value of the id property
    */
   public void setId(String aId)
   {
      id = aId;
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
   @param name
   @roseuid 3C0D185801FA
    */
   public void addFolder(String id, String name)
   {
	scriptBuffer.append("aux");
	scriptBuffer.append(id);
	scriptBuffer.append("=insFld(");
	scriptBuffer.append(this.name);
	scriptBuffer.append(",gFld(\"");
	scriptBuffer.append(name);
	scriptBuffer.append("\",\"\"));\n");
   }

   /**
   @param name
   @param parentFolder
   @roseuid 3C0D199E02B7
    */
   public void addFolder(String id, String name, String parentFolderId)
   {
	scriptBuffer.append("aux");
	scriptBuffer.append(id);
	scriptBuffer.append("= insFld(aux");
	scriptBuffer.append(parentFolderId);
	scriptBuffer.append(",gFld(\"");
	scriptBuffer.append(name);
	scriptBuffer.append("\",\"\"));\n");
   }

   /**
   @param title
   @param url
   @roseuid 3C0D19CA012A
    */
   public void addItem(String parentFolderId, String title, String url)
   {
	scriptBuffer.append("insDoc(");
	scriptBuffer.append("aux");
	scriptBuffer.append(parentFolderId);
	scriptBuffer.append(",gLnk(\"Sj\",\"");
	scriptBuffer.append(title);
	scriptBuffer.append("\",\"");
	scriptBuffer.append(url);
	scriptBuffer.append("\"));\n");
   }

   /**
   @return java.lang.String
   @roseuid 3C0D25B6013C
    */
   public String getScript()
   {
	scriptBuffer.append("gllist('');\n"); // gllist('/Portal')
	return scriptBuffer.toString();
   }
}
