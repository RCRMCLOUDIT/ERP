<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=ISO-8859-1">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
<TITLE>Item Classification</TITLE>
</HEAD>
<BODY>
<%@ page import="javax.sql.RowSet,com.cap.wdf.action.*,com.cap.util.*,java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp"%>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<%! 	
 static String title = "Item Classification";

 static final int CSCLSSID_COLUMN 	= 1;
 static final int CSPARENTID_COLUMN = 2;
 static final int CSNAME_COLUMN 	= 3;
 static final int CSLASTLVL_COLUMN  = 4;
 static final int CSACTIVE_COLUMN 	= 5;
 static final int CSLEVEL_COLUMN 	= 6;
 static final int MAXLEVELS_COLUMN	= 7;
 static final int TYPEID_COLUMN 	= 8;
 static final int TYPENAME_COLUMN 	= 9;
 static final int HASCHILD_COLUMN 	= 10;
 
 static final String anchor1 = "View";
 static final String anchor2 = "Edit";
 static final String anchor3 = "Deactivate";
 static final String anchor4 = "Delete";
 static final String anchor5 = "Reactivate"; 
 static final String anchor6 = "Add Child";
%>
<%@ include file="../../ERP_COMMON/header.jspf" %>
<%@ include file="../../ERP_COMMON/a_utilJsMsg.jspf" %>

<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/util.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/overlib_mini.js"></SCRIPT>
<%
 FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
 RowSet classRS = (RowSet) request.getAttribute("classRS");

 int pag = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
 int rowCount = 0;
 boolean odd = true;
 String filter	= request.getParameter("filter")==null?"2":request.getParameter("filter");
 String rootPath = flowManager.createActionURI(flowManager.getActionName(request),request);

 String classId 		= ""; 
 String parentId		= "";
 String name 			= "";
 String active			= "";
 
 StringBuffer buffer;
 //String canReactivate 	= "";
 //String canDeactivate 	= "";
 String hasChild	  	= "";
 String lastlvl 		= "";
 String maxReachedEdit	= ""; 

 String classTypeName_	= "";
 //String classTypeId_	= "";
 String oldName			="";
 int levelInt;
 int maxLevelsInt;
 //int k = 0;

 String baseLink = rootPath + "?page=" + pag + "&filter=" + filter;
 String link1 = baseLink+ "&_message=view&classId=";
 String link2 = baseLink+ "&_message=edit&classId=";
 String link3 = baseLink+ "&_message=deactivate&classId=";
 String link4 = baseLink+ "&_message=delete&classId=";
 String link5 = baseLink+ "&_message=reactivate&classId=";
 String link6 = baseLink+ "&_message=new&parentId=";

 Object errMsg = request.getAttribute("actionErrors"); 
%>
<jsp:useBean id="errMsgHelper" class="com.cap.util.ErrorMessageHelper" scope="page">
<jsp:setProperty property="errorMessage" value="<%= errMsg%>" name="errMsgHelper" />
<jsp:setProperty property="contextPath" value="<%= request.getContextPath() %>" name="errMsgHelper" />
</jsp:useBean>
<jsp:getProperty property="htmlErrorMessage" name="errMsgHelper" />

<TABLE width="650" border="0" class="Border" cellpadding="0" cellspacing="1">
    <TR class="tableheader">
      <TD colspan="2">Item Classification</TD>
    </TR>
<%	if(!classRS.next()) {%>
	<TR>
      <TH colspan="2"><IMG src="../erp/ERP_COMMON/images/info.gif" width="16" height="16" border="0"> <%=ConstantValue.NO_DATA%></TH>
    </TR>
<%
	}
	classRS.beforeFirst();
	while (classRS.next() && rowCount < ConstantValue.PAGE_SIZE) 
	{
 		classId 	= classRS.getString(CSCLSSID_COLUMN); 
		parentId	= classRS.getString(CSPARENTID_COLUMN);
 		lastlvl		= classRS.getString(CSLASTLVL_COLUMN);
 		name 		= classRS.getString(CSNAME_COLUMN);
 		active		= classRS.getString(CSACTIVE_COLUMN);
 		hasChild	= classRS.getString(HASCHILD_COLUMN);
		classTypeName_ 	= classRS.getString(TYPENAME_COLUMN);	
		//classTypeId_ 	= classRS.getString(TYPEID_COLUMN);	
  		//canDeactivate	= classRS.getString(CSDEACT_COLUMN);
		//canReactivate	= classRS.getString(CSREACT_COLUMN);
		
		levelInt 	= Integer.parseInt(classRS.getString(CSLEVEL_COLUMN));
		maxLevelsInt = Integer.parseInt(classRS.getString(MAXLEVELS_COLUMN));
		
		if(maxLevelsInt == levelInt)
			maxReachedEdit = "Y";		
		else
			maxReachedEdit = "N";

        String links1 = link1 + classId + "&parentId=" + parentId;
		String links2 = link2 + classId + "&parentId=" + parentId + "&classTypeId=" + classRS.getString(TYPEID_COLUMN) + "&hasChild=" + hasChild + "&maxReached=" + maxReachedEdit;
        String links3 = link3 + classId + "&parentId=" + parentId;
		String links4 = link4 + classId + "&parentId=" + parentId;
        String links5 = link5 + classId + "&parentId=" + parentId;
        String links6 = link6 + classId + "&classTypeId=" + classRS.getString(TYPEID_COLUMN);

		StringBuffer link = new StringBuffer();
		
		//view
		link.append(ConstantValue.PRE_TAG).append(links1).append(ConstantValue.MID_TAG).append(anchor1).append(ConstantValue.END_TAG);

		if(active.equals("Y"))
		{
			link.append(ConstantValue.PRE_TAG).append(links2).append(ConstantValue.MID_TAG).append(anchor2).append(ConstantValue.END_TAG);				
			
			//if(canDeactivate.equals("Y"))
				link.append(ConstantValue.PRE_TAG).append(links3).append(ConstantValue.MID_TAG).append(anchor3).append(ConstantValue.END_TAG);	
			if(hasChild.equals("N"))
				link.append(ConstantValue.PRE_TAG).append(links4).append(ConstantValue.MIDDEL_TAG).append(anchor4).append(ConstantValue.END_TAG);	
			if(!lastlvl.equals("Y") && maxLevelsInt != levelInt)
				link.append(ConstantValue.PRE_TAG).append(links6).append(ConstantValue.MID_TAG).append(anchor6).append(ConstantValue.END_TAG);				
		}
		else if(active.equals("N"))
		{
			//if(canReactivate.equals("Y"))
				link.append(ConstantValue.PRE_TAG).append(links5).append(ConstantValue.MID_TAG).append(anchor5).append(ConstantValue.END_TAG);	
		}

		buffer = new StringBuffer("");
		
		for(int z = 0; z < levelInt; z++)
		{
			buffer.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		}
%>
	<TR class="<%=odd ? "pcrinfo":"pcrinfo1"%>">
<%		if(oldName.equals(classTypeName_)) { %>
	  <TD></TD>   
<%		}else{ %>
	  <TD><%=classTypeName_%></TD>   
<%		}  %>
	  <TD width="70%"><A href="javascript:void(0);" class="<%=active.equals("N")?"link_alt_underline_G":"link_alt_underline"%>" onmouseover="this.className='<%=active.equals("N")?"link_alt_underline_G":"link_alt_underline"%>';return overlib('<%=link.toString()%>', STICKY, WIDTH, 70, BORDER, 1, HAUTO, OFFSETY,-30,OFFSETX,20, TIMEOUT,3000);" onmouseout="this.className='<%=active.equals("N")?"link_alt_underline_G":"link_alt_underline"%>';return nd();"><%=active.equals("N")?"<span class=\"link_alt_underline_G\">" + buffer + name + "</SPAN>":buffer + name%></A></TD>      
    </TR>
<%
		oldName = classTypeName_;
		rowCount++;
		odd = ! odd;
   	}
%>
</TABLE>
<TABLE border="0" width="650" cellspacing="1" cellpadding="0">
<TR>
  <TD width="20%">
 	<FORM>
	<INPUT type="hidden" name="_message" value="new">
	<INPUT type="hidden" name="page" value="<%=pag%>">
	<INPUT type="hidden" name="filter" value="<%=filter%>">
	<INPUT type="submit" name="Submit" class="button" value="Add New">
	</FORM>
  </TD>
  <TD width="40%">
    <FORM action="<%=rootPath%>">
<% if (filter.equals("1")) { %>
	<INPUT type="hidden" name="filter" value="2">
	<INPUT type="hidden" name="page" value="<%=pag%>">	
	<INPUT type="submit" name="show" value="Show Active" class="button">
<% } else { %>
	<INPUT type="hidden" name="filter" value="1">
	<INPUT type="hidden" name="page" value="<%=pag%>">
	<INPUT type="submit" name="show" value="Show All" class="button">
<% } %>
	</FORM>
  </TD>
  <TD width="20%" align="right">
<% if (pag > 1){ %>
    <FORM>
	<INPUT type="hidden" name="page" value="<%=pag-1%>">
 	<INPUT type="hidden" name="filter" value="<%=filter%>">
    <INPUT type="submit" name="prev" value="<< Previous" class="button">
    </FORM>
<% } %>
  </TD>
  <TD width="20%" align="right">
<% if (!classRS.isAfterLast()) { %>
    <FORM>
	<INPUT type="hidden" name="page" value="<%=pag+1%>">
 	<INPUT type="hidden" name="filter" value="<%=filter%>">
    <INPUT type="submit" name="next" value="Next >>" class="button">
    </FORM>
<% } %>
  </TD>
</TR>
</TABLE>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>
