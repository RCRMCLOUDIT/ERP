<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V4.0 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE>FA Lookup</TITLE>
<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
</HEAD>
<%@ page import="javax.sql.RowSet,com.cap.wdf.action.*,com.cap.util.*,java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp"%>
<%! 
 static final String title = "P12100006"; 
%>
<%@ include file="../../ERP_COMMON/header.jspf" %>
<%@ include file="../../ERP_COMMON/a_utilJsMsg.jspf" %>
<%@ include file="../../ERP_COMMON/a_popcalendarJsMsg.jspf" %>
<%@ include file="../../ERP_COMMON/a_addSymbolJsMsg.jspf" %>
<%@ include file="../../ERP_COMMON/a_dialogBoxJsMsg.jspf" %>
<%@ include file="a_jsMsg.jspf" %>

<SCRIPT language="javascript" src="../erp/ERP_COMMON/js/util.js"></SCRIPT>
<SCRIPT language="Javascript" src="../erp/ERP_COMMON/js/addSymbol.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/dialogBox.js"></SCRIPT>
<SCRIPT language="javascript" src="../erp/ERP_COMMON/js/popcalendar.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/fa/js/asset.js"></SCRIPT>

<BODY onload="myForm.asTag.focus();">
<%
 FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
 RowSet entTypeRS	= (RowSet) request.getAttribute("entTypeRS");
 RowSet statusRS = (RowSet) request.getAttribute("statusRS");
 RowSet classTypeRS	= (RowSet) request.getAttribute("classTypeRS");
 RowSet currRS 	 = (RowSet) request.getAttribute("currRS");
 
 String rootPath = flowManager.createActionURI(flowManager.getActionName(request),request);
 String contextRoot = request.getContextPath();
 
 String assName 			= "";// request.getParameter("asName") == null? "" : request.getParameter("asName");
 String asTag 			= "";//request.getParameter("asTag") == null? "" : request.getParameter("asTag");
 String lfdt 			= "";//request.getParameter("lfdt") ==null?"" : request.getParameter("lfdt");
 String ltdt 			= "";//request.getParameter("ltdt") ==null?"" : request.getParameter("ltdt");
 String ffdt 			= "";//request.getParameter("ffdt") ==null?"" : request.getParameter("ffdt");
 String ftdt 			= "";//request.getParameter("ftdt") ==null?"" : request.getParameter("ftdt");
 String vendI 			= "";//request.getParameter("vendI") ==null?"" : request.getParameter("vendI");
 String vendN 			= "";//request.getParameter("vendN") ==null?"" : request.getParameter("vendN");
 String lst 			= "";//request.getParameter("lst") ==null?"" : request.getParameter("lst");
 String presId			= "D";//request.getParameter("presId") ==null?"D" : request.getParameter("presId");
 String entType9		= "";//request.getParameter("entType9")==null?"":request.getParameter("entType9");
 String empCustId9		= ConstantValue.COST_CENTER_BLANK_ID;//request.getParameter("empCustId9")==null?ConstantValue.COST_CENTER_BLANK_ID:request.getParameter("empCustId9");
 String empCustIdText9	= "";//request.getParameter("empCustIdText9")==null?"":request.getParameter("empCustIdText9");
 String lcty 			= "";//request.getParameter("lcty")==null ? "" : request.getParameter("lcty");
 String lcid 			= "";//request.getParameter("lcid")==null ? "" : request.getParameter("lcid");
 String lcnm			= "";//request.getParameter("lcnm")==null ? "" : request.getParameter("lcnm");
 //String lemp			= "";//request.getParameter("lemp")==null ? "" : request.getParameter("lemp");
 //String lempnm			= "";//request.getParameter("lempnm")==null ? "" : request.getParameter("lempnm");
 String spaCurr   		= "";
 
 Object errMsg = request.getAttribute("actionErrors"); 
%>
<jsp:useBean id="errMsgHelper" class="com.cap.util.ErrorMessageHelper" scope="page">
<jsp:setProperty property="errorMessage" value="<%= errMsg%>" name="errMsgHelper" />
<jsp:setProperty property="contextPath" value="<%= request.getContextPath() %>" name="errMsgHelper" />
</jsp:useBean>
<jsp:getProperty property="htmlErrorMessage" name="errMsgHelper" />

<FORM name="myForm" method="POST" action="<%=rootPath%>" onsubmit="return asset_lookup(myForm);">
<INPUT type="hidden" name="LKUP" value="Y">
<INPUT type="hidden" name="returnEid" id="returnEid" value="Y">
<INPUT type="hidden" name="_message" value="list">
<INPUT type="hidden" name="contextRoot" value="<%=contextRoot%>">

<TABLE width="600" class="Border" cellpadding="0" cellspacing="1" border="0">
     <TR>
		<TD class="tableheader" colspan="2" align="center"><%=rb.getString("P12100006")%></TD>
    </TR>
    <TR class="pcrinfo1"> 
        <TD width="25%" class="label">&nbsp;<%=rb.getString("P12110003")%></TD>
      	<TD><INPUT type="text" name="asTag" value="<%=asTag%>" size="20" maxlength="20" onKeyPress="if(isEnterPressed()){ myForm._message.value='list'; myForm.submit();}"></TD>
    </TR>
    <TR class="pcrinfo"> 
	    <TD class="message" align="center"><%=rb.getString("B00109")%></TD>
	    <TD>&nbsp;</TD>
    </TR>
    <TR class="pcrinfo1">
    	<TD class="label">&nbsp;<%=rb.getString("P12110002")%></TD>
    	<TD><INPUT type="text" name="assName" value="<%=assName%>" size="50" maxlength="120"></TD>
    </TR>
    <TR class="pcrinfo">
		<TD class="label">&nbsp;<%=rb.getString("P12110006")%></TD>
		<TD><SELECT name="lcty" id="lcty" class="text" onChange="asset_lookup_change(myForm);">
			<OPTION value="" <%=lcty.length()==0?"selected":""%>></OPTION>
<% 
classTypeRS.beforeFirst();
String code = ""; 
String desc = ""; 
while (classTypeRS.next()) {
	code = classTypeRS.getString(ConstantValue.CODE_COL); 
	desc = classTypeRS.getString(ConstantValue.DESC_COL); 
%>
 			<OPTION value="<%=code%>" <%=lcty.equals(code)?"selected":""%>><%=desc%></OPTION>
<% } %>
		 </SELECT>
		<INPUT type="hidden" name="lcid" value="<%=lcid%>">
        <INPUT type="text" size="40" maxlength="40" name="lcnm" id="lcnm" value="<%=lcnm%>" onChange="myForm.lcid.value='0';" onkeypress='if(isEnterPressed()){asset_class_search(myForm,"lcty","myForm","lcid","lcnm", "<%=request.getContextPath()%>"); }'>
        <INPUT type="button" name="searchClass" value=" v " onclick='asset_class_search(myForm,"lcty","myForm","lcid","lcnm", "<%=request.getContextPath()%>");'>
		</TD>
    </TR>	
	<TR class="pcrinfo1">
		<TD class="label">&nbsp;<%=rb.getString("P12110014")%></TD>
	    <TD>
			<SELECT size="1" name="entType9" class="text" onchange="myForm.empCustId9.value=''; myForm.empCustIdText9.value='';">
				<OPTION value="" <%=entType9.equals("")?"selected":""%>></OPTION>
<%while(entTypeRS.next()){%>
				<OPTION value="<%=entTypeRS.getString(ConstantValue.CODE_COL).trim()%>" <%=entTypeRS.getString(ConstantValue.CODE_COL).trim().equals(entType9)?"selected":""%>><%=entTypeRS.getString(ConstantValue.DESC_COL)==null?"":entTypeRS.getString(ConstantValue.DESC_COL).trim()%></OPTION>
<%}%>
			</SELECT>
	        <INPUT type="hidden" name="empCustId9" value="<%=empCustId9%>">
	        <INPUT type="text" size="40" maxlength="55" name="empCustIdText9" id="empCustIdText9" value="<%=empCustIdText9%>" onChange="myForm.empCustId9.value='';" onkeypress='if(isEnterPressed()) openCostCenterDialog("9", "A", "<%= request.getContextPath() %>");'>
	        <INPUT type="button" name="ccButton" value=" v " onclick='openCostCenterDialog("9", "A", "<%= request.getContextPath() %>");'>
		</TD>
    </TR>    
    <TR class="pcrinfo">
      <TD class="label">&nbsp;<%=rb.getString("P12110009")%></TD>
      <TD>
		<INPUT size="10" type="text" maxlength="10" name="lfdt" value="<%=lfdt%>" onKeyUp="addSlash(this)" onChange="formatDate(this)" onKeyPress="OnlyDigits();addSlash(this)" onKeyDown="return checkArrows(this, event)"><IMG src="../erp/ERP_COMMON/images/popcalendar/calendar.gif" border="0" onclick="popUpCalendar(this, myForm.lfdt, 'mm/dd/yyyy', -1, -1);" alt="Calendar">&nbsp;<%=rb.getString("P00650004")%>&nbsp;
		<INPUT size="10" type="text" maxlength="10" name="ltdt" value="<%=ltdt%>" onKeyUp="addSlash(this)" onChange="formatDate(this)" onKeyPress="OnlyDigits();addSlash(this)" onKeyDown="return checkArrows(this, event)"><IMG src="../erp/ERP_COMMON/images/popcalendar/calendar.gif" border="0" onclick="popUpCalendar(this, myForm.ltdt, 'mm/dd/yyyy', -1, -1);" alt="Calendar">
      </TD>
    </TR>
    <TR class="pcrinfo1">
      <TD class="label">&nbsp;<%=rb.getString("P12110012")%></TD>
      <TD>
		<INPUT size="10" type="text" maxlength="22" name="ffdt" value="<%=ffdt%>" onKeyPress="OnlyDecimal()">&nbsp;<%=rb.getString("P00650004")%>&nbsp;
		<INPUT size="10" type="text" maxlength="22" name="ftdt" value="<%=ftdt%>" onKeyPress="OnlyDecimal()">
        <SELECT name="spaCurr" size="1" class="text">
			<OPTION value="" <%=spaCurr.length()==0?"selected":""%>></OPTION>
			<%		
	 			currRS.beforeFirst();
	 			while(currRS.next()) {
			%>
			<OPTION value="<%=currRS.getString(ConstantValue.CODE_COL)%>" <%=currRS.getString(ConstantValue.CODE_COL).equals(spaCurr)?"selected":" "%>><%=currRS.getString(ConstantValue.DESC_COL)%></OPTION>
			<%}%>
		</SELECT>
      </TD>
    </TR>
    <TR class="pcrinfo">
        <TD class="label">&nbsp;<%=rb.getString("P12110008")%></TD>
	    <TD>
			<INPUT type="hidden" name="vendI" id="vendI" value="<%=vendI%>">
      		<INPUT type="text" name="vendN" id="vendN" size="50" maxlength="40" value="<%=vendN%>" onchange="myForm.vendI.value='';" onkeypress='if(isEnterPressed()) openEntDialog("VEN", "", "myForm", "vendI", "vendN", "<%=contextRoot%>");'>
			<INPUT type="button" name="searchVend" value=" v " onclick='openEntDialog("VEN", "", "myForm", "vendI", "vendN", "<%=contextRoot%>");'></TD>
    </TR>   
	<TR  class="pcrinfo1">
		<TD class="label">&nbsp;<%=rb.getString("P12110024")%></TD>
		<TD class="label" >
		<INPUT type="hidden" name="lemp" id="lemp"> 
		<INPUT size="40" type="text" maxlength="40" name="lempnm" id="lempnm" onchange='document.myForm.lemp.value="";' onkeypress='if(isEnterPressed()) openEntDialog("EMP", "", "myForm", "lemp", "lempnm", "<%=contextRoot%>");'>
		<INPUT type="button" name="searchEmployee" value=" v " onclick='openEntDialog("EMP","","myForm","lemp","lempnm","<%=contextRoot%>");'></TD>
	</TR>
    <TR class="pcrinfo">
		<TD class="label">&nbsp;<%=rb.getString("B00135")%></TD>
		<TD class="label"><SELECT name="lst" class="text">
			<OPTION value="" <%=lst.equals("")?"selected":""%>></OPTION>
<% 
			statusRS.beforeFirst();
			while (statusRS.next()) { %>
			<OPTION value="<%=statusRS.getString(ConstantValue.CODE_COL)%>" <%=lst.equals(statusRS.getString(ConstantValue.CODE_COL))?"selected":""%>><%=statusRS.getString(ConstantValue.DESC_COL)%></option>
<% } %>
      	</SELECT></TD>
    </TR>
	<TR class="pcrinfo1">
	  <TD class="label">&nbsp;<%=rb.getString("B00044")%></TD>
	  <TD class="pcrinfo1">
	  	<input type="radio" name="formato" value="Html" onclick="myForm._message.value='list'" checked><%=rb.getString("B00045")%>&nbsp;
	  	<input type="radio" name="formato" value="Excel" onclick="myForm._message.value='excel'"><%=rb.getString("B00046")%>&nbsp;
	  	<input type="radio" name="formato" value="Pdf" onclick="myForm._message.value='report'"><%=rb.getString("B00047")%>&nbsp;&nbsp;&nbsp;<%=rb.getString("P04080007")%>&nbsp;
		<SELECT size="1" name="presId" class="text">
			<OPTION value="D" <%=presId.equals("D")?"selected":""%>>Description</OPTION>
			<OPTION value="T" <%=presId.equals("T")?"selected":""%>>Tag</OPTION>
		</SELECT>
	  </TD>
	</TR>
</TABLE>
<TABLE width="600" border="0" cellpadding="0" cellspacing="1" height="30">
  <TR>
    <TD width="50%"><INPUT type="submit" class="button" name="addNew" value="<%=rb.getString("B00001")%>" onclick="myForm._message.value='new';"></TD>
    <TD width="50%"><INPUT type="submit" class="button" name="lookup" value="<%=rb.getString("B00041")%>" onclick="myForm._message.value='list';"></TD>
  </TR>
</TABLE>
</FORM>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>
