<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Page Designer V4.0 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
<TITLE>Fix Asset Depreciation</TITLE>
</HEAD>
<%@ page import="javax.sql.RowSet,com.cap.wdf.action.*,com.cap.util.*,java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp"%>
<%! 
 static final String title = "P12100023"; 

 // column index 
 static final int LSTDATE_COL 	= 1;
%>
<%@ include file="../../ERP_COMMON/header.jspf" %>
<%@ include file="../../ERP_COMMON/a_utilJsMsg.jspf" %>
<%@ include file="../../ERP_COMMON/a_popcalendarJsMsg.jspf" %>
<%@ include file="../../ERP_COMMON/a_addSymbolJsMsg.jspf" %>
<%@ include file="a_jsMsg.jspf" %>

<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/util.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/popcalendar.js"></SCRIPT>
<SCRIPT language="Javascript" src="../erp/ERP_COMMON/js/addSymbol.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/fa/js/asset.js"></SCRIPT>

<BODY onload="myForm.runDate.focus();">
<%
 FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
 RowSet lastRunRS = (RowSet) request.getAttribute("lastRunRS");
 
 String lastRunDate = "" ;
 if (lastRunRS.next()){
      lastRunDate = Format.displayDate(lastRunRS.getString(LSTDATE_COL));
 }
 
 int pag = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
 String runDate 	= request.getParameter("runDate")==null ? lastRunDate : request.getParameter("runDate");
 String rootPath = flowManager.createActionURI(flowManager.getActionName(request),request);

 Object errMsg = request.getAttribute("actionErrors"); 
%>
<jsp:useBean id="errMsgHelper" class="com.cap.util.ErrorMessageHelper" scope="page">
<jsp:setProperty property="errorMessage" value="<%= errMsg%>" name="errMsgHelper" />
<jsp:setProperty property="contextPath" value="<%= request.getContextPath() %>" name="errMsgHelper" />
</jsp:useBean>
<jsp:getProperty property="htmlErrorMessage" name="errMsgHelper" />

<FORM name="myForm" action="<%=rootPath%>" method="post" onsubmit='return deprun_submit(myForm);'>
<INPUT type="hidden" name="_message" value="deprun">
<INPUT type="hidden" name="lastRunDate" value="<%=lastRunDate%>">

<TABLE class="Border" width="600" cellspacing="1" cellpadding="0" border="0">
	<TR>
		<TD width="100%" class="tableheader" colspan="2" align="center"><%=rb.getString("P12100024")%></TD>
	</TR>
	<TR class="pcrinfo">
		<TD width="25%" class="label">&nbsp;<%=rb.getString("P12100016")%></TD>
    	<TD width="75%"> <%=lastRunDate%> </TD>
	</TR>
    <TR class="pcrinfo1" >
      <TD class="label"  width="25%"><span class="textRed">*</span><%=rb.getString("P12100025")%></TD>
      <TD width="75%"><INPUT size="10" maxlength="10" type="text" name="runDate"  value="<%=runDate.equals("")?Format.getSysDate():runDate%>" onkeyup="addSlash(myForm.runDate)" onchange="formatDate(myForm.runDate)" onkeypress="OnlyDigits();addSlash(myForm.runDate)" onkeydown="return checkArrows(this, event)">
            <IMG src="../erp/ERP_COMMON/images/popcalendar/calendar.gif" border="0" onclick="popUpCalendar(this, myForm.runDate, 'mm/dd/yyyy', 350, -1);" alt="Calendar"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </TD>
	</TR>
	<TR>
</TABLE>
<TABLE width="600" border="0" cellpadding="0" cellspacing="1" height="30">
<TR><TD width="100%" align="center" height="30">
		<INPUT name="Submit" type="submit" class="button" value="<%=rb.getString("B00011")%>" onClick="myForm._message.value='deprun';">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		<input name="Cancel" type="submit" class="button" value="<%=rb.getString("B00010")%>" onClick="myForm._message.value='listrun';">
</TD></TR>
</TABLE>
</FORM>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>

