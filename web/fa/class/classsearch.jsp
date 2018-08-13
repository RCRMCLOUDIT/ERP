<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<%@ page import="javax.sql.RowSet, com.cap.wdf.command.CachedRowSet, com.cap.wdf.action.*, com.cap.util.*, java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp" %>
<%
FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
RowSet classRS = (RowSet) request.getAttribute("classRS");

int pag = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
String rootPath = flowManager.createActionURI(flowManager.getActionName(request),request);
String classTypeId = request.getParameter("classTypeId") == null ? "" : request.getParameter("classTypeId");
String search = request.getParameter("search") == null ? "" : request.getParameter("search");
String allowParent = request.getParameter("allowParent") == null ? "" : request.getParameter("allowParent");

String f_name = request.getParameter("f_name") == null ? "" : request.getParameter("f_name");
String fld_1 = request.getParameter("fld_1") == null ? "" : request.getParameter("fld_1");
String fld_2 = request.getParameter("fld_2") == null ? "" : request.getParameter("fld_2");
%>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META http-equiv="Content-Style-Type" content="text/css">
<META http-equiv="Cache-Control" content="no-cache">
<META http-equiv="Expires" content="0">
<META http-equiv="Pragma" content="no-cache">
<%@ include file="../../ERP_COMMON/session.jspf" %>
<%@ include file="../../ERP_COMMON/a_utilJsMsg.jspf" %>
<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/util.js"></SCRIPT>

<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
<TITLE>Search Item Classification</TITLE>
<SCRIPT language="JavaScript">
<!--
 var openerDocument;
 var aname = new Array();

 if (parent.openerDocument) {
   openerDocument = parent.openerDocument;
 } else {
   openerDocument = opener.document;
 }

 function passBack(v_id, v_name) 
 {
 	openerDocument.<%=f_name%>.<%=fld_1%>.value = v_id;
	openerDocument.<%=f_name%>.<%=fld_2%>.value = unescape(v_name);
	close();
 }

//-->
</SCRIPT>
</HEAD>
<jsp:useBean id="searchAssetClass" class="com.cap.erp.fa.SearchItemClassHelper" scope="page">
<jsp:setProperty property="classRS" value="<%= classRS %>" name="searchAssetClass" />
<jsp:setProperty property="page" value="<%= pag %>" name="searchAssetClass" />
<jsp:setProperty property="allowParent" value="<%= allowParent %>" name="searchAssetClass" />
</jsp:useBean>
<BODY onload="<jsp:getProperty property="onLoadStr" name="searchAssetClass" />">

<TABLE width="99%" border="0" class="Border" cellpadding="0" cellspacing="1">
    <TR class="textRed">
      <TD nowrap><STRONG>Search Item Classification</STRONG></TD>
    </TR>
  	<TR class="tableheader">
      <TD nowrap>Item Classification Name</TD>
    </TR>
    
<% if (((CachedRowSet)classRS).getRowSetSize() ==0) { %>
	<TR>
      <TH><IMG src="../erp/ERP_COMMON/images/info.gif" width="16" height="16" border="0"> <%=ConstantValue.NO_DATA%></TH>
    </TR>
<% } %>

<jsp:getProperty property="build" name="searchAssetClass" />

</TABLE>

<FORM name="myForm" method="POST" action="<%=rootPath%>">
<INPUT name="_message" type="hidden" value="search">
<INPUT name="page"  type="hidden" value="<%=pag%>">
<INPUT name="classTypeId" type="hidden" value="<%=classTypeId%>">
<INPUT name="allowParent" type="hidden" value="<%=allowParent%>">
<INPUT type="hidden" name="f_name" value="<%=f_name%>">
<INPUT type="hidden" name="fld_1" value="<%=fld_1%>">
<INPUT type="hidden" name="fld_2" value="<%=fld_2%>">

<TABLE border="0" width="99%" cellspacing="1" cellpadding="0">
    <TR>
      <TD width="60%" class="label">Search Classification
      	<INPUT type="text" name="search" value="<%=search%>" size="15" maxlength="50">
	    <INPUT type="submit" name="go" value="Go" class="button" onclick="myForm.page.value='1';">
      </TD>
      <TD width="20%" align="right">
<% if (pag > 1){ %>
	    <INPUT name="prev" type="submit" value="<< Previous" class="button" onclick="myForm.page.value='<%=pag-1%>';">
<% }	%>
      </TD>
      <TD width="20%" align="right">
<% if (!classRS.isAfterLast()) { %>	
		<INPUT name="next" type="submit" value="Next >> " class="button" onclick="myForm.page.value='<%=pag+1%>';">
<% } 	%>
      </TD>
      </TR>
  </TABLE>
</FORM>
</BODY>
</HTML>