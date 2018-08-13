<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Page Designer V4.0 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
<TITLE>Fix Asset Depreciation</TITLE>
</HEAD>
<BODY>
<%@ page import="javax.sql.RowSet,com.cap.wdf.action.*,com.cap.util.*,java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp"%>
<%! 
 static final String title = "P12100018"; 

 // column index 
 static final int LSTDATE_COL 	= 1;
 static final int RUNBY_COL 	= 2; 
 static final int RUNDATE_COL 	= 3; 
%>
<%@ include file="../../ERP_COMMON/header.jspf" %>
<%@ include file="../../ERP_COMMON/a_utilJsMsg.jspf" %>

<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/util.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/fa/js/asset.js"></SCRIPT>
<%
 FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
 RowSet depRunRS = (RowSet) request.getAttribute("depRunRS");
 
 int pag = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
 int rowCount = 0;
 boolean odd = true;
 
 String rootPath = flowManager.createActionURI(flowManager.getActionName(request),request);

 Object errMsg = request.getAttribute("actionErrors"); 
%>
<jsp:useBean id="errMsgHelper" class="com.cap.util.ErrorMessageHelper" scope="page">
<jsp:setProperty property="errorMessage" value="<%= errMsg%>" name="errMsgHelper" />
<jsp:setProperty property="contextPath" value="<%= request.getContextPath() %>" name="errMsgHelper" />
</jsp:useBean>
<jsp:getProperty property="htmlErrorMessage" name="errMsgHelper" />

<TABLE width="600" border="0" class="Border" cellpadding="0" cellspacing="1">
    <TR class="tableheader">
    <TD colspan="3"><%=rb.getString("P12100019")%></TD></tr>
    <TR class="tableheader">
      <TD width="40%"><%=rb.getString("P12100020")%></TD>
      <TD width="30%"><%=rb.getString("P12100021")%></TD>
      <TD width="30%"><%=rb.getString("P12100022")%></TD>   
    </tr>
<%	if(!depRunRS.next()) {%>
	<TR>
	 <TH colspan="3"><IMG src="../erp/ERP_COMMON/images/info.gif" width="16" height="16" border="0"> <%=rb.getString("B00007")%></TH>
	</TR>
<%
	}
	
	depRunRS.beforeFirst();
	while (depRunRS.next() && rowCount < ConstantValue.PAGE_SIZE) 
	{
%>
    <TR class="<%=odd?"pcrinfo":"pcrinfo1"%>">
      <TD><%=Format.displayDate(depRunRS.getString(LSTDATE_COL))%></TD>
      <TD><%=depRunRS.getString(RUNBY_COL)%></TD>
      <TD><%=Format.displayTimestamp(depRunRS.getString(RUNDATE_COL))%></TD>
    </TR>
<%
		rowCount++;
		odd = ! odd;
    }
%>
</TABLE>

<FORM name="myForm" action="<%=rootPath%>">
<INPUT type="hidden" name="_message" value="listrun">
<INPUT type="hidden" name="page" value="<%=pag%>">

<TABLE border="0" width="600" cellspacing="0" cellpadding="0">
  <TR>
	<TD width="20%"><INPUT type="submit" name="Submit" class="button" value="<%=rb.getString("P12100023")%>" onclick="myForm._message.value='runnew';"></TD>
    <TD width="40%" align="center">
    </TD>
    <TD width="20%" align="right">
<%	if (pag > 1){	%>
		<INPUT type="submit" name="prev" value="<%=rb.getString("B00005")%>" class="button" onclick="myForm.page.value='<%=pag-1%>';">
<% 	}	%>
    </TD>
	<TD width="20%" align="right">
<%	if (!depRunRS.isAfterLast()) { %>
		<INPUT type="submit" name="next" value="<%=rb.getString("B00006")%>" class="button" onclick="myForm.page.value='<%=pag+1%>';">
<%	}	%>
	</TD>
  </TR>
</TABLE>
</FORM>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>

