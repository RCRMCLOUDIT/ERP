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
 static final String title = "P12120032"; 

 // column index 
 static final int ASNAME_COL 	= 1;
 static final int ASTAG_COL 	= 2;
 static final int DEPMONTH_COL 	= 3;
 static final int SALVAMT_COL 	= 4;
 static final int LIFETIME_COL 	= 5;
 static final int LASTDATE_COL 	= 6;

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

<BODY onload="myForm.depDate.focus();">
<%
 FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
 RowSet assetRS = (RowSet) request.getAttribute("assetRS");
 assetRS.next();
 
 String rootPath = flowManager.createActionURI(flowManager.getActionName(request),request);
 
 /* lookup info need to carry */
 String assName 		= request.getParameter("assName") == null? "" : request.getParameter("assName");
 String asTag 			= request.getParameter("asTag") == null? "" : request.getParameter("asTag");
 String lfdt 			= request.getParameter("lfdt") ==null?"" : request.getParameter("lfdt");
 String ltdt 			= request.getParameter("ltdt") ==null?"" : request.getParameter("ltdt");
 String ffdt 			= request.getParameter("ffdt") ==null?"" : request.getParameter("ffdt");
 String ftdt 			= request.getParameter("ftdt") ==null?"" : request.getParameter("ftdt");
 String vendI 			= request.getParameter("vendI") ==null?"" : request.getParameter("vendI");
 String lst 			= request.getParameter("lst") ==null?"" : request.getParameter("lst");
 String presId			= request.getParameter("presId") ==null?"D" : request.getParameter("presId");
 String entType9		= request.getParameter("entType9")==null?"":request.getParameter("entType9");
 String empCustId9		= request.getParameter("empCustId9")==null?ConstantValue.COST_CENTER_BLANK_ID:request.getParameter("empCustId9");
 String lcty 			= request.getParameter("lcty")==null ? "" : request.getParameter("lcty");
 String lcid 			= request.getParameter("lcid")==null ? "" : request.getParameter("lcid");
 String lemp			= request.getParameter("lemp")==null ? "" : request.getParameter("lemp");
 String spaCurr			= request.getParameter("spaCurr")==null ? "" : request.getParameter("spaCurr");
 String pag 			= request.getParameter("page") == null? "1": request.getParameter("page");
 
 String assetId 	= request.getParameter("assetId");
 String depDate 	= request.getParameter("depDate")==null ? "" : request.getParameter("depDate");
 String incSalvAmt 	= request.getParameter("incSalvAmt")==null ? "" : request.getParameter("incSalvAmt");
 String comm	 	= request.getParameter("comm")==null ? "" : request.getParameter("comm");

 String lastRunDate = Format.displayDate(assetRS.getString(LASTDATE_COL));

 Object errMsg = request.getAttribute("actionErrors"); 
%>
<jsp:useBean id="errMsgHelper" class="com.cap.util.ErrorMessageHelper" scope="page">
<jsp:setProperty property="errorMessage" value="<%= errMsg%>" name="errMsgHelper" />
<jsp:setProperty property="contextPath" value="<%= request.getContextPath() %>" name="errMsgHelper" />
</jsp:useBean>
<jsp:getProperty property="htmlErrorMessage" name="errMsgHelper" />

<FORM name="myForm" action="<%=rootPath%>" method="post" onsubmit='return depall_submit(myForm);'>
<INPUT type="hidden" name="_message" value="depall">
<INPUT type="hidden" name="assetId" value="<%=assetId%>">
<INPUT type="hidden" name="lastRunDate" value="<%=lastRunDate%>">
<INPUT type="hidden" name="page" value="<%=pag%>">
<INPUT type="hidden" name="assName" value="<%=assName%>">
<INPUT type="hidden" name="asTag" value="<%=asTag%>">
<INPUT type="hidden" name="lfdt" value="<%=lfdt%>">
<INPUT type="hidden" name="ltdt" value="<%=ltdt%>">
<INPUT type="hidden" name="ffdt" value="<%=ffdt%>">
<INPUT type="hidden" name="ftdt" value="<%=ftdt%>">
<INPUT type="hidden" name="vendI" value="<%=vendI%>">
<INPUT type="hidden" name="lst" value="<%=lst%>">
<INPUT type="hidden" name="presId" value="<%=presId%>">
<INPUT type="hidden" name="entType9" value="<%=entType9%>">
<INPUT type="hidden" name="empCustId9" value="<%=empCustId9%>">
<INPUT type="hidden" name="lcty" value="<%=lcty%>">
<INPUT type="hidden" name="lcid" value="<%=lcid%>">
<INPUT type="hidden" name="lemp" value="<%=lemp%>">
<INPUT type="hidden" name="LKUP" value="N">
<INPUT type="hidden" name="spaCurr" value="<%=spaCurr%>">

<TABLE class="Border" width="600" cellspacing="1" cellpadding="0" border="0">
	<TR>
		<TD width="100%" class="tableheader" colspan="4" align="center"><%=rb.getString("P12100024")%></TD>
	</TR>
	<TR class="pcrinfo">
		<TD width="20%" class="label">&nbsp;<%=rb.getString("P12120027")%></TD>
    	<TD width="30%"><%=assetRS.getString(ASNAME_COL)%></TD>
		<TD width="25%" class="label"><%=rb.getString("P12120028")%></TD>
    	<TD width="25%"><%=assetRS.getString(LIFETIME_COL)%></TD>
	</TR>
	<TR class="pcrinfo1">
		<TD class="label">&nbsp;<%=rb.getString("P12120004")%></TD>
    	<TD><%=assetRS.getString(ASTAG_COL)%></TD>
		<TD class="label"><%=rb.getString("P12120028")%></TD>
    	<TD><%=assetRS.getString(DEPMONTH_COL)%></TD>
	</TR>
	<TR class="pcrinfo">
		<TD class="label">&nbsp;<%=rb.getString("P12120019")%></TD>
    	<TD><%=Format.displayCurrency(assetRS.getBigDecimal(SALVAMT_COL))%></TD>
		<TD class="label"><%=rb.getString("P12120029")%></TD>
    	<TD><%=lastRunDate%></TD>
	</TR>
    <TR class="pcrinfo1">
      <TD class="label"><span class="textRed">*</span><%=rb.getString("P12120034")%></TD>
      <TD colspan="3"><INPUT size="10" maxlength="10" type="text" name="depDate"  value="<%=depDate%>" onkeyup="addSlash(myForm.depDate)" onchange="formatDate(myForm.depDate)" onkeypress="OnlyDigits();addSlash(myForm.depDate)" onkeydown="return checkArrows(this, event)"><IMG src="../erp/ERP_COMMON/images/popcalendar/calendar.gif" border="0" onclick="popUpCalendar(this, myForm.depDate, 'mm/dd/yyyy', 350, -1);" alt="Calendar"></TD>
	</TR>
    <TR class="pcrinfo">
      <TD class="label">&nbsp;<%=rb.getString("P12120035")%></TD>
      <TD colspan="3"><input type="checkbox" name="incSalvAmt" value="Y" <%=incSalvAmt.equals("Y")?"checked":""%>></TD>
	</TR>
    <TR class="pcrinfo1">
      <TD class="label"><span class="textRed">*</span><%=rb.getString("P12120031")%></TD>
      <TD colspan="3"><INPUT size="70" maxlength="255" type="text" name="comm" value="<%=comm%>"></TD>
	</TR>
	<TR>
</TABLE>
<TABLE width="600" border="0" cellpadding="0" cellspacing="1" height="30">
<TR><TD width="100%" align="center" height="30">
	<INPUT name="Submit" type="submit" class="button" value="<%=rb.getString("B00011")%>" onClick="myForm._message.value='depall';">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
    <INPUT name="Reset" type="reset" class="button" value="<%=rb.getString("B00009")%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<INPUT name="Cancel" type="submit" class="button" value="<%=rb.getString("B00010")%>" onClick="myForm._message.value='list';">
</TD></TR>
</TABLE>
</FORM>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>

