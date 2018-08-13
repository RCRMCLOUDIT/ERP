<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V4.0 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
<TITLE>Relocate Asset</TITLE>
</HEAD>
<%@ page import="javax.sql.RowSet,com.cap.wdf.action.*,com.cap.util.*,java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp"%>
<%! 
 static final String title = "P12100008"; 

 // column index 
 static final int ASIDNAME_COL 	= 1;
 static final int ASNAME_COL 	= 2;
 static final int TAG_COL 		= 3;
 
 static final int LOCDATE_COL = 3;
%>
<%
FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
RowSet assetRS 		= (RowSet) request.getAttribute("assetRS");
RowSet locationRS 	= (RowSet) request.getAttribute("locationRS");
RowSet entTypeRS 	= (RowSet) request.getAttribute("entTypeRS");
assetRS.next();
locationRS.next();

 /* lookup info need to carry */
 String assName 	= request.getParameter("assName") == null? "" : request.getParameter("assName");
 String asTag 		= request.getParameter("asTag") == null? "" : request.getParameter("asTag");
 String lfdt 		= request.getParameter("lfdt") ==null?"" : request.getParameter("lfdt");
 String ltdt 		= request.getParameter("ltdt") ==null?"" : request.getParameter("ltdt");
 String ffdt 		= request.getParameter("ffdt") ==null?"" : request.getParameter("ffdt");
 String ftdt 		= request.getParameter("ftdt") ==null?"" : request.getParameter("ftdt");
 String vendI 		= request.getParameter("vendI") ==null?"" : request.getParameter("vendI");
 String lst 		= request.getParameter("lst") ==null?"" : request.getParameter("lst");
 String presId		= request.getParameter("presId") ==null?"D" : request.getParameter("presId");
 String entType9	= request.getParameter("entType9")==null?"":request.getParameter("entType9");
 String empCustId9	= request.getParameter("empCustId9")==null?ConstantValue.COST_CENTER_BLANK_ID:request.getParameter("empCustId9");
 String lcty 		= request.getParameter("lcty")==null ? "" : request.getParameter("lcty");
 String lcid 		= request.getParameter("lcid")==null ? "" : request.getParameter("lcid");
 String lemp		= request.getParameter("lemp")==null ? "" : request.getParameter("lemp");
 String pag 		= request.getParameter("page") != null ? request.getParameter("page") : "1";	 

//String contextRoot = request.getContextPath();

String assetId = request.getParameter("assetId");

String entTypeId	= request.getParameter("entType0")==null?"":request.getParameter("entType0");
String empCustId	= request.getParameter("empCustId0")==null?ConstantValue.COST_CENTER_BLANK_ID:request.getParameter("empCustId0");
String empCustIdText= request.getParameter("empCustIdText0")==null?"":request.getParameter("empCustIdText0");
String locDate		= request.getParameter("locDate")==null?"":request.getParameter("locDate");

String assetName= assetRS.getString(ASNAME_COL);
String tag 		= assetRS.getString(TAG_COL);
String location = locationRS.getString(ConstantValue.DESC_COL);
String preDate	= Format.displayDate(locationRS.getString(LOCDATE_COL));

Object errMsg = request.getAttribute("actionErrors"); 
%>
<%@ include file="../../ERP_COMMON/header.jspf" %>
<%@ include file="a_jsMsg.jspf" %>
<%@ include file="../../ERP_COMMON/a_utilJsMsg.jspf" %>
<%@ include file="../../ERP_COMMON/a_addSymbolJsMsg.jspf" %>
<%@ include file="../../ERP_COMMON/a_dialogBoxJsMsg.jspf" %>
<%@ include file="../../ERP_COMMON/a_popcalendarJsMsg.jspf" %>

<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/util.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/dialogBox.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/popcalendar.js"></SCRIPT>
<SCRIPT language="Javascript" src="../erp/ERP_COMMON/js/addSymbol.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/fa/js/asset.js"></SCRIPT>

<BODY onLoad="myForm.locDate.focus();">
<jsp:useBean id="errMsgHelper" class="com.cap.util.ErrorMessageHelper" scope="page">
<jsp:setProperty property="errorMessage" value="<%= errMsg%>" name="errMsgHelper" />
<jsp:setProperty property="contextPath" value="<%= request.getContextPath() %>" name="errMsgHelper" />
</jsp:useBean>
<jsp:getProperty property="htmlErrorMessage" name="errMsgHelper" />

<FORM name="myForm" method="post" action="<%=flowManager.createActionURI(flowManager.getActionName(request),request)%>" onsubmit='return relocate_submit(myForm);'>
<INPUT type="hidden" name="_message" value="reloc">
<INPUT type="hidden" name="assetId" value="<%=assetId%>">
<INPUT type="hidden" name="returnEid" id="returnEid" value="Y">
<INPUT type="hidden" name="preDate" value="<%=preDate%>">
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

<TABLE width="600" border="0" class="Border" cellpadding="0" cellspacing="1">
    <TR class="tableheader">
    	<TD colspan="4" ><%=rb.getString("P12100009")%></TD>
    </TR>
    <TR class="pcrinfo">
    	<TD class="label">&nbsp;<%=rb.getString("P12120003")%></TD>
    	<TD colspan="3"><%=assetName%></TD>
    </TR>
    <TR class="pcrinfo1"> 
        <TD class="label">&nbsp;<%=rb.getString("P12120004")%></TD>
      	<TD colspan="3"><%=tag%></TD>
    </TR>
    <TR class="pcrinfo"> 
        <TD class="label">&nbsp;<%=rb.getString("P12100010")%></TD>
      	<TD colspan="3"><%=Format.displayDate(preDate)%></TD>
    </TR>
    <TR class="pcrinfo1"> 
        <TD class="label">&nbsp;<%=rb.getString("P12100011")%></TD>
      	<TD colspan="3"><%=location%></TD>
    </TR>
    <TR class="pcrinfo" >
      <TD class="label"><span class="textRed">*</span><%=rb.getString("P12100012")%></TD>
      <TD colspan="3"><INPUT size="10" maxlength="10" type="text" name="locDate"  value="<%=locDate.equals("")?Format.getSysDate():locDate%>" onkeyup="addSlash(myForm.locDate)" onchange="formatDate(myForm.locDate)" onkeypress="OnlyDigits();addSlash(myForm.locDate)" onkeydown="return checkArrows(this, event)">
            <IMG src="../erp/ERP_COMMON/images/popcalendar/calendar.gif" border="0" onclick="popUpCalendar(this, myForm.locDate, 'mm/dd/yyyy', 350, -1);" alt="Calendar"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </TD>
	</TR>
	<TR class="pcrinfo1">
  		<TD class="label"><span class="textRed">*</span><%=rb.getString("P12100013")%></TD>
	    <TD colspan="3">
			<SELECT size="1" name="entType0" class="text" onchange="myForm.empCustId0.value=''; myForm.empCustIdText0.value='';">
				<OPTION value="" <%=entTypeId.equals("")?"selected":""%>></OPTION>
				<%while(entTypeRS.next()){%>
				<OPTION value="<%=entTypeRS.getString(ConstantValue.CODE_COL)%>" <%=entTypeRS.getString(ConstantValue.CODE_COL).equals(entTypeId)?"selected":""%>><%=entTypeRS.getString(ConstantValue.DESC_COL)==null?"":entTypeRS.getString(ConstantValue.DESC_COL)%></OPTION>
				<%}%>
			</SELECT>
	        <INPUT type="hidden" name="empCustId0" value="<%=empCustId%>">
	        <INPUT type="text" size="40" maxlength="55" name="empCustIdText0" id="empCustIdText0" value="<%=empCustIdText%>" onChange="myForm.empCustId0.value='<%=ConstantValue.COST_CENTER_BLANK_ID%>';" onkeypress='if(isEnterPressed()) openCostCenterDialog("0", "A", "<%= request.getContextPath() %>");'>
	        <INPUT type="button" name="ccButton" value=" v " onclick='openCostCenterDialog("0", "A", "<%= request.getContextPath() %>");'>
		</TD>
    </TR>
  </TABLE>
<TABLE width="600" border="0" cellpadding="0" cellspacing="1">
<TR><TD align="center" height="30">
    <INPUT name="save" type="submit" class="button" value="<%=rb.getString("B00008")%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <INPUT name="Reset" type="reset" class="button" value="<%=rb.getString("B00009")%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<INPUT name="Cancel" type="submit" class="button" value="<%=rb.getString("B00010")%>" onClick="this.form._message.value='list';">
</TD></TR>
</TABLE>
</FORM>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>


