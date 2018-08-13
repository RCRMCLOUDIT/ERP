<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Page Designer V4.0 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
<TITLE>Fixed Asset Depreciation Report</TITLE>
</HEAD>
<%@ page import="javax.sql.RowSet,com.cap.wdf.action.*,com.cap.util.*,java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp"%>
<%! 
 static final String title = "P12100014"; 
%>

<%@ include file="../../ERP_COMMON/header.jspf" %>
<%@ include file="../../ERP_COMMON/a_utilJsMsg.jspf" %>
<%@ include file="../../ERP_COMMON/a_popcalendarJsMsg.jspf" %>
<%@ include file="../../ERP_COMMON/a_addSymbolJsMsg.jspf" %>
<%@ include file="../../ERP_COMMON/a_dialogBoxJsMsg.jspf" %>

<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/util.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/popcalendar.js"></SCRIPT>
<SCRIPT language="Javascript" src="../erp/ERP_COMMON/js/addSymbol.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/fa/js/asset.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/dialogBox.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/overlib_mini.js"></SCRIPT>

<BODY onload="myForm.rptDate.focus();">
<%
 FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
 RowSet lastRunRS = (RowSet) request.getAttribute("lastRunRS");

 String lastRunDate = "" ;
 if (lastRunRS.next()){
      lastRunDate = Format.displayDate(lastRunRS.getString(ConstantValue.CODE_COL));
 }

 String rptDate 	= request.getParameter("rptDate")==null ? Format.getSysDate() : request.getParameter("rptDate");
 String rootPath = flowManager.createActionURI(flowManager.getActionName(request),request);

 Object errMsg = request.getAttribute("actionErrors"); 
%>
<jsp:useBean id="errMsgHelper" class="com.cap.util.ErrorMessageHelper" scope="page">
<jsp:setProperty property="errorMessage" value="<%= errMsg%>" name="errMsgHelper" />
<jsp:setProperty property="contextPath" value="<%= request.getContextPath() %>" name="errMsgHelper" />
</jsp:useBean>
<jsp:getProperty property="htmlErrorMessage" name="errMsgHelper" />

<FORM name="myForm" action="<%=rootPath%>" method="post" onsubmit="return asset_report(myForm)">
<INPUT type="hidden" name="_message" value="report">
<INPUT type="hidden" name="contextRoot" value="<%=request.getContextPath()%>">

<TABLE class="Border" width="600" cellspacing="1" cellpadding="0" border="0">
	<TR>
		<TD width="100%" class="tableheader" colspan="2" align="center"><%=rb.getString("P12100015")%></TD>
	</TR>
    <TR class="pcrinfo1">
      <TD width="25%" class="label">&nbsp;<%=rb.getString("P12100016")%></TD>
      <TD width="75%"><%=lastRunDate%></TD>
	</TR>
    <TR class="pcrinfo" >
      <TD class="label"><span class="textRed">*</span><%=rb.getString("P12100017")%></TD>
      <TD><INPUT size="10" maxlength="10" type="text" name="rptDate"  value="<%=rptDate.equals("")?Format.getSysDate():rptDate%>" onkeyup="addSlash(myForm.rptDate)" onchange="formatDate(myForm.rptDate)" onkeypress="OnlyDigits();addSlash(myForm.rptDate)" onkeydown="return checkArrows(this, event)"><IMG src="../erp/ERP_COMMON/images/popcalendar/calendar.gif" border="0" onclick="popUpCalendar(this, myForm.rptDate, 'mm/dd/yyyy', 350, -1);" alt="Calendar"></TD>
	</TR>
    <TR class="pcrinfo1">
		<TD class="label">&nbsp;<%=rb.getString("P05520007")%></TD>
		<TD>
			<INPUT type="radio" name="format" value="pdf" checked onclick="myForm._message.value='report'">&nbsp;&nbsp;<%=rb.getString("B00047")%>
			<INPUT type="radio" name="format" value="xls" onclick="myForm._message.value='repXLS'">&nbsp;&nbsp;<%=rb.getString("B00046")%>
		</TD>
    </TR>    
</TABLE>
<TABLE width="600" border="0" cellpadding="0" cellspacing="1" height="30">
<TR><TD width="100%" align="center" height="30">
	<INPUT name="Submit" type="submit" class="button" value="<%=rb.getString("B00011")%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
	<INPUT name="Cancel" type="button" class="button" value="<%=rb.getString("B00010")%>" onClick="window.close();">
</TD></TR>
</TABLE>
</FORM>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>
