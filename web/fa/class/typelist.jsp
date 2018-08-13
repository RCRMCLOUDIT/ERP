<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V4.0 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<META http-equiv="Cache-Control" content="no-cache">
<META http-equiv="Expires" content="0">
<META http-equiv="Pragma" content="no-cache">
<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
<TITLE>Item Classification Types</TITLE>
</HEAD>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<%@ page import="javax.sql.RowSet,com.cap.wdf.action.*,com.cap.util.*,java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp"%>
<%! 
 static final String title = "Item Classification Types"; 
 
 //column index for classTypes
 static final int TYPEID_COLUMN 	= 1;
 static final int NAME_COLUMN 		= 2;
 static final int ACTIVE_COLUMN		= 3;
 static final int MAXLEVEL_COLUMN 	= 4;
 static final int VARLEVEL_COLUMN 	= 5;
 static final int MULTSELECT_COLUMN = 6;
 //static final int SYSTEMSEED_COLUMN = 7;

 static final String anchor1 = "Edit";
 static final String anchor2 = "Reactivate";
 static final String anchor3 = "Deactivate";   
%>
<%@ include file="../../ERP_COMMON/header.jspf" %>
<%@ include file="../../ERP_COMMON/a_utilJsMsg.jspf" %>

<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/util.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/overlib_mini.js"></SCRIPT>

<BODY onload="myForm.search.focus();">
<%
 FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
 RowSet typeRS = (RowSet)request.getAttribute("typeRS");

 String rootPath = flowManager.createActionURI(flowManager.getActionName(request),request);
 boolean odd = true;
 int pag = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
 int rowCount = 0;

 String search = request.getParameter("search")==null?"":request.getParameter("search"); 
 String filter = request.getParameter("filter")==null?"2":request.getParameter("filter"); 
 String isActive = "";
 String typeId = "";
 
 String baseLink = rootPath + "?search=" + Format.encodeHTML(search) + "&page=" + pag + "&filter=" + filter;
 String link1 = baseLink+ "&_message=edit&typeId=";
 String link2 = baseLink+ "&_message=reactivate&typeId=";
 String link3 = baseLink+ "&_message=deactivate&typeId=";
 String links1 = "";
 String links2 = "";
 String links3 = "";
 String link = "";
 
 Object errMsg = request.getAttribute("actionErrors"); 
%>
<jsp:useBean id="errMsgHelper" class="com.cap.util.ErrorMessageHelper" scope="page">
<jsp:setProperty property="errorMessage" value="<%= errMsg%>" name="errMsgHelper" />
<jsp:setProperty property="contextPath" value="<%= request.getContextPath() %>" name="errMsgHelper" />
</jsp:useBean>
<jsp:getProperty property="htmlErrorMessage" name="errMsgHelper" />
<TABLE width="600" border="0" class="Border" cellpadding="0" cellspacing="1">
    <TR class="tableheader"> 
      <td width="45%">Classification Type</td>
      <TD width="15%">Max Levels</TD>
      <TD width="15%">Variable Levels</TD>
      <TD width="15%">Mult. Selection</TD>
      <TD width="10%">Active</TD>
    </TR>
<%	if(!typeRS.next()) {%>
	<TR>
      <TH colspan="5"><IMG src="../erp/ERP_COMMON/images/info.gif" width="16" height="16" border="0"> <%=ConstantValue.NO_DATA%></TH>
    </TR>
<%
	}
	typeRS.beforeFirst();
	while (typeRS.next() && rowCount < ConstantValue.PAGE_SIZE) 
	{
		typeId = typeRS.getString(TYPEID_COLUMN);
		isActive = typeRS.getString(ACTIVE_COLUMN);
    	links1 = link1 + typeId;
    	links2 = link2 + typeId;
    	links3 = link3 + typeId;
		
		if(isActive.equals("Y"))
		{
			link = ConstantValue.PRE_TAG + links1 + ConstantValue.MID_TAG + anchor1 + ConstantValue.ENDPRE_TAG + links3 + ConstantValue.MID_TAG + anchor3 + ConstantValue.END_TAG;
		}
		else if(isActive.equals("N"))
		{
			link = ConstantValue.PRE_TAG + links2 + ConstantValue.MID_TAG + anchor2 + ConstantValue.END_TAG;
		}			
%>		
    <TR class="<%=odd ? "pcrinfo":"pcrinfo1"%>">
      <TD><A href="javascript:void(0);" class="<%=isActive.equals("N")?"link_alt_underline_G":"link_alt_underline"%>" onmouseover="this.className='<%=isActive.equals("N")?"link_alt_underline_G":"link_alt_underline"%>'; return overlib('<%=link%>', STICKY, WIDTH, 80, BORDER, 1, HAUTO, OFFSETY,-30,OFFSETX,20, TIMEOUT,3000);" onmouseout="this.className='<%=isActive.equals("N")?"link_alt_underline_G":"link_alt_underline"%>';return nd();"><%=isActive.equals("N")?"<span class=\"link_alt_underline_G\">" + typeRS.getString(NAME_COLUMN) + "</SPAN>":typeRS.getString(NAME_COLUMN)%></A></TD>
      <TD align="center"><%=typeRS.getString(MAXLEVEL_COLUMN)%></TD>
      <TD align="center"><%=typeRS.getString(VARLEVEL_COLUMN).equals("Y")?YES_STR:NO_STR%></TD>
      <TD align="center"><%=typeRS.getString(MULTSELECT_COLUMN).equals("Y")?YES_STR:NO_STR%></TD>
      <TD align="center"><%=isActive.equals("Y")?YES_STR:NO_STR%></TD>
    </TR>
<%
		rowCount++;
		odd = ! odd;
    }
%>
</TABLE>

<FORM name="myForm" method="post" action="<%=rootPath%>">
<INPUT type="hidden" name="_message" value="list">
<INPUT type="hidden" name="filter" value="<%=filter%>">
<INPUT type="hidden" name="page" value="<%=pag%>">
<TABLE width="600" border="0" cellpadding="0" cellspacing="1">
<TR class="pcrinfo">
  <TD height="20" valign="middle" class="label" align="center" colspan="4">Position To Name:
	<INPUT type="text" name="search" value="<%=search%>" class="text" size="20" maxlength="50">
	<INPUT type="submit" name="go" value="Go" class="button" onclick="myForm.page.value='1';">
  </TD>
</TR>
<TR>
  <TD width="20%">
	<INPUT type="submit" name="Submit" class="button" value="Add New" onclick="myForm._message.value='new';">
  </TD>
  <TD width="40%">
<% if(filter.equals("1")) {%>
	<INPUT type="submit" name="show" value="Show Active" class="button" onclick="myForm.filter.value='2';">
<% }else{ %>
	<INPUT type="submit" name="show" value="Show All" class="button" onclick="myForm.filter.value='1';">
<% } %> 
  </TD>
  <TD width="20%" align="right">
<%	if (pag > 1){	%>
	<INPUT type="submit" name="prev" value="<< Previous" class="button" onclick="myForm.page.value='<%=pag-1%>';">
<% 	}	%>
	</TD>
    <TD width="20%" align="right">
<%	if (!typeRS.isAfterLast()) {	%>
    <INPUT type="submit" name="next" value="Next >>" class="button" onclick="myForm.page.value='<%=pag+1%>';">
<%	}	%>
	</TD>
</TR>
</TABLE>
</FORM>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>