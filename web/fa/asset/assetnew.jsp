<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V4.0 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
<TITLE>New Asset</TITLE>
</HEAD>
<%@ page import="javax.sql.RowSet,com.cap.wdf.action.*,com.cap.util.*,java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp"%>
<%! 
 static final String title = "P12110000"; 
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

<BODY onLoad="myForm.assetName.focus();">
<%
FlowManager flowManager	= (FlowManager) request.getAttribute("flowManager");
RowSet entTypeRS	= (RowSet) request.getAttribute("entTypeRS");
RowSet currencyRS	= (RowSet) request.getAttribute("currencyRS");
RowSet classTypeRS	= (RowSet) request.getAttribute("classTypeRS");
RowSet depreciationRS= (RowSet) request.getAttribute("depreciationRS");
RowSet defaultGlRS	 = (RowSet) request.getAttribute("defaultGlRS");

String contextRoot = request.getContextPath();

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
 String LKUP		= request.getParameter("LKUP")==null ? "" : request.getParameter("LKUP");
 String spaCurr		= request.getParameter("spaCurr")==null ? "" : request.getParameter("spaCurr");
 
 //String vendN 			= request.getParameter("vendN") ==null?"" : request.getParameter("vendN");
 //String empCustIdText9	= request.getParameter("empCustIdText9")==null?"":request.getParameter("empCustIdText9");
 //String lcnm			= request.getParameter("lcnm")==null ? "" : request.getParameter("lcnm");
 //String lempnm			= request.getParameter("lempnm")==null ? "" : request.getParameter("lempnm");
 /*--------*/
String entTypeId	= request.getParameter("entType0")==null?"":request.getParameter("entType0");
String empCustId	= request.getParameter("empCustId0")==null?ConstantValue.COST_CENTER_BLANK_ID:request.getParameter("empCustId0");
String empCustIdText= request.getParameter("empCustIdText0")==null?"":request.getParameter("empCustIdText0");
String empId		= request.getParameter("empId")==null?"":request.getParameter("empId");
String empName		= request.getParameter("empName")==null?"":request.getParameter("empName");

String vendId		= request.getParameter("vendId")==null?"":request.getParameter("vendId");
String vendName		= request.getParameter("vendName")==null?"":request.getParameter("vendName");

//String assetId 	= request.getParameter("assetId")==null ? "" : request.getParameter("assetId");
String assetName 	= request.getParameter("assetName")==null ? "" : request.getParameter("assetName");
String tag 		= request.getParameter("tag")==null ? "" : request.getParameter("tag");

String classTypeId = request.getParameter("classTypeId")==null ? "" : request.getParameter("classTypeId");
String classId 	= request.getParameter("classId")==null ? "" : request.getParameter("classId");
String className= request.getParameter("className")==null ? "" : request.getParameter("className");

String docRef 	= request.getParameter("docRef")==null ? "" : request.getParameter("docRef");
String price 	= request.getParameter("price")==null ? "" : request.getParameter("price");
String currency = request.getParameter("currency")==null ? "" : request.getParameter("currency");
String acqDate 	= request.getParameter("acqDate")==null ? "" : request.getParameter("acqDate");
String asColor 	= request.getParameter("asColor")==null ? "" : request.getParameter("asColor");

String serial 	= request.getParameter("serial")==null ? "" : request.getParameter("serial");
String model 	= request.getParameter("model")==null ? "" : request.getParameter("model");
String manuId 	= request.getParameter("manuId")==null ? "" : request.getParameter("manuId");
String manuName	= request.getParameter("manuName")==null?"":request.getParameter("manuName");
String warranty = request.getParameter("warranty")==null ? "" : request.getParameter("warranty");
String depMethod= request.getParameter("depMethod")==null ? "" : request.getParameter("depMethod");
String lifeTime = request.getParameter("lifeTime")==null ? "" : request.getParameter("lifeTime");
String salvage 	= request.getParameter("salvage")==null ? "" : request.getParameter("salvage");
String totDepMnths 	= request.getParameter("totDepMnths")==null ? "" : request.getParameter("totDepMnths");

defaultGlRS.next();
String levelText0 = request.getParameter("levelText0")==null? defaultGlRS.getString(ConstantValue.DESC_COL):request.getParameter("levelText0");
String glAstAcct= request.getParameter("level0")==null ? defaultGlRS.getString(ConstantValue.CODE_COL) : request.getParameter("level0");

defaultGlRS.next();
String levelText1 = request.getParameter("levelText1")==null?defaultGlRS.getString(ConstantValue.DESC_COL):request.getParameter("levelText1");
String glDepAcct= request.getParameter("level1")==null ? defaultGlRS.getString(ConstantValue.CODE_COL) : request.getParameter("level1");

defaultGlRS.next();
String levelText2 = request.getParameter("levelText2")==null?defaultGlRS.getString(ConstantValue.DESC_COL):request.getParameter("levelText2");
String glExpAcct= request.getParameter("level2")==null ? defaultGlRS.getString(ConstantValue.CODE_COL): request.getParameter("level2");

defaultGlRS.next();
String levelText3 = request.getParameter("levelText3")==null?defaultGlRS.getString(ConstantValue.DESC_COL):request.getParameter("levelText3");
String glLblAcct= request.getParameter("level3")==null ? defaultGlRS.getString(ConstantValue.CODE_COL) : request.getParameter("level3");

Object errMsg = request.getAttribute("actionErrors"); 
%>
<jsp:useBean id="errMsgHelper" class="com.cap.util.ErrorMessageHelper" scope="page">
<jsp:setProperty property="errorMessage" value="<%= errMsg%>" name="errMsgHelper" />
<jsp:setProperty property="contextPath" value="<%= contextRoot %>" name="errMsgHelper" />
</jsp:useBean>
<jsp:getProperty property="htmlErrorMessage" name="errMsgHelper" />

<FORM name="myForm" method="post" action="<%=flowManager.createActionURI(flowManager.getActionName(request),request)%>" onsubmit='return asset_submit(myForm);'>
<INPUT type="hidden" name="_message" value="add">
<INPUT type="hidden" name="returnEid" id="returnEid" value="Y">
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
<INPUT type="hidden" name="LKUP" value="<%=LKUP%>">
<INPUT type="hidden" name="page" value="<%=pag%>">
<INPUT type="hidden" name="spaCurr" value="<%=spaCurr%>">

<TABLE width="700" border="0" class="Border" cellpadding="0" cellspacing="1">
    <TR>
    	<TD colspan="4" class="tableheader"><%=rb.getString("P12110001")%></TD>
    </TR>
    <tr class="pcrinfo">
    	<TD width="25%" class="label"><span class="textRed">*</span><%=rb.getString("P12110002")%></TD>
    	<TD colspan="3"><INPUT type="text" name="assetName" value="<%=assetName%>" size="50" maxlength="120"></TD>
    </tr>
    <TR class="pcrinfo1"> 
        <TD class="label">&nbsp;<%=rb.getString("P12110003")%></TD>
      	<TD colspan="3"><INPUT type="text" name="tag" value="<%=tag%>" size="20" maxlength="20"></TD>
    </TR>
    <TR class="pcrinfo">
        <TD width="25%" class="label">&nbsp;<%=rb.getString("P12110004")%></TD>
        <TD width="25%"><INPUT type="text" name="serial" value="<%=serial%>" size="20" maxlength="20"></TD>
        <TD width="25%" class="label">&nbsp;<%=rb.getString("P12110005")%></TD>
        <TD width="25%"><INPUT type="text" name="model" value="<%=model%>" size="20" maxlength="20"></TD>
    </TR>
    <TR class="pcrinfo1">
		<TD class="label">&nbsp;<%=rb.getString("P12110006")%></TD>
		<TD colspan="3"><SELECT name="classTypeId" id="classTypeId" class="text" onChange="asset_class_change(myForm);">
			<OPTION value="" <%=classTypeId.length()==0?"selected":""%>></OPTION>
<% 
classTypeRS.beforeFirst();
String code = ""; 
String desc = ""; 
while (classTypeRS.next()) {
	code = classTypeRS.getString(ConstantValue.CODE_COL); 
	desc = classTypeRS.getString(ConstantValue.DESC_COL); 
%>
 			<OPTION value="<%=code%>" <%=classTypeId.equals(code)?"selected":""%>><%=desc%></OPTION>
<% } %>
		 </SELECT>
		<INPUT type="hidden" name="classId" value="<%=classId%>">
        <INPUT type="text" size="40" maxlength="40" name="className" id="className" value="<%=className%>" onChange="myForm.classId.value='0';" onkeypress='if(isEnterPressed()){asset_class_search(myForm,"classTypeId","myForm","classId","className","<%=contextRoot%>"); }'>
        <INPUT type="button" name="searchClass" value=" v " onclick='asset_class_search(myForm,"classTypeId","myForm","classId","className","<%=contextRoot%>");'>
		</TD>
    </TR>
    <TR class="pcrinfo">
        <TD class="label">&nbsp;<%=rb.getString("P12110007")%></TD>
	    <TD colspan="3">
			<INPUT type="hidden" name="manuId" id="manuId" value="<%=manuId%>">
      		<INPUT type="text" name="manuName" id="manuName" size="30" maxlength="40" value="<%=manuName%>" onchange="myForm.manuId.value='';" onkeypress='if(isEnterPressed()) openEntDialog("MAN", "", "myForm", "manuId", "manuName", "<%=contextRoot%>");'>
			<INPUT type="button" name="searchMan" value=" v " onclick='openEntDialog("MAN", "", "myForm", "manuId", "manuName", "<%=contextRoot%>");'></TD>
    </TR>   
    <TR class="pcrinfo1">
        <TD class="label">&nbsp;<%=rb.getString("P12110008")%></TD>
	    <TD colspan="3">
			<INPUT type="hidden" name="vendId" id="vendId" value="<%=vendId%>">
      		<INPUT type="text" name="vendName" id="vendName" size="30" maxlength="40" value="<%=vendName%>" onchange="myForm.vendId.value='';" onkeypress='if(isEnterPressed()) openEntDialog("VEN", "", "myForm", "vendId", "vendName", "<%=contextRoot%>");'>
			<INPUT type="button" name="searchVend" value=" v " onclick='openEntDialog("VEN", "", "myForm", "vendId", "vendName", "<%=contextRoot%>");'></TD>
    </TR>   
    <tr class="pcrinfo">
		<TD class="label">&nbsp;<%=rb.getString("P12110009")%></TD>
		<TD><INPUT type="text" name="acqDate" size="10" maxlength="10" value="<%=acqDate%>" onkeyup="addSlash(myForm.acqDate)" onchange="formatDate(myForm.acqDate)" onkeypress="OnlyDigits();addSlash(myForm.acqDate)" onkeydown="return checkArrows(this, event)"><IMG border="0" onclick="popUpCalendar(this, myForm.acqDate, 'mm/dd/yyyy', -1, -1);" alt="Calendar" src="../erp/ERP_COMMON/images/popcalendar/calendar.gif"></TD>
	    <TD class="label">&nbsp;<%=rb.getString("P12110010")%></TD>
		<TD><INPUT type="text" name="docRef" value="<%=docRef%>" size="20" maxlength="20"></TD>
    </TR>              
    <TR class="pcrinfo1">
        <TD class="label">&nbsp;<%=rb.getString("P12110011")%></TD>
		<TD><INPUT type="text" name="warranty" size="10" maxlength="10" value="<%=warranty%>" onkeyup="addSlash(myForm.warranty)" onchange="formatDate(myForm.warranty)" onkeypress="OnlyDigits();addSlash(myForm.warranty)" onkeydown="return checkArrows(this, event)"><IMG border="0" onclick="popUpCalendar(this, myForm.warranty, 'mm/dd/yyyy', -1, -1);" alt="Calendar" src="../erp/ERP_COMMON/images/popcalendar/calendar.gif"></TD>
	    <TD class="label">&nbsp;<%=rb.getString("P12110025")%></TD>
		<TD><INPUT type="text" name="asColor" value="<%=asColor%>" size="20" maxlength="50"></TD>
    </TR>              
    <TR class="pcrinfo">
		<TD class="label">&nbsp;<%=rb.getString("P12110012")%></TD>
		<TD colspan="3"><INPUT type="text" name="price" value="<%=price%>" size="10" maxlength="22" onKeyPress="OnlyDecimal();">
			<SELECT size="1" name="currency" class="text">
				<OPTION value="" <%=currency.equals("")?"selected":""%>></OPTION>
				<%while(currencyRS.next()){%>
				<OPTION value="<%=currencyRS.getString(ConstantValue.CODE_COL)%>" <%=currencyRS.getString(ConstantValue.CODE_COL).equals(currency)?"selected":""%>><%=currencyRS.getString(ConstantValue.DESC_COL)==null?"":currencyRS.getString(ConstantValue.DESC_COL)%></OPTION>
				<%}%>
			</SELECT>
      	</TD>
    </TR>
	<TR  class="pcrinfo">
        <TD colspan="4">         
	        <FIELDSET class="GroupBorder"><LEGEND class="labelGodStyle">&nbsp;<%=rb.getString("P12110013")%>&nbsp;</LEGEND>
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
					<TR class="pcrinfo1">
	      	 			<TD class="label" width="25%">&nbsp;<%=rb.getString("P12110014")%></TD>
					    <TD>
							<SELECT size="1" name="entType0" class="text" onchange="myForm.empCustId0.value=''; myForm.empCustIdText0.value='';">
								<OPTION value="" <%=entTypeId.equals("")?"selected":""%>></OPTION>
								<%while(entTypeRS.next()){%>
								<OPTION value="<%=entTypeRS.getString(ConstantValue.CODE_COL).trim()%>" <%=entTypeRS.getString(ConstantValue.CODE_COL).trim().equals(entTypeId)?"selected":""%>><%=entTypeRS.getString(ConstantValue.DESC_COL)==null?"":entTypeRS.getString(ConstantValue.DESC_COL).trim()%></OPTION>
								<%}%>
							</SELECT>
					        <INPUT type="hidden" name="empCustId0" value="<%=empCustId%>">
					        <INPUT type="text" size="40" maxlength="55" name="empCustIdText0" id="empCustIdText0" value="<%=empCustIdText%>" onChange="myForm.empCustId0.value='';" onkeypress='if(isEnterPressed()) openCostCenterDialog("0", "A", "<%= contextRoot %>");'>
					        <INPUT type="button" name="ccButton" value=" v " onclick='openCostCenterDialog("0", "A", "<%= contextRoot %>");'>
						</TD>
				    </TR>
					<TR class="pcrinfo">
	      	 		  <TD class="label">&nbsp;<%=rb.getString("P12110024")%></TD>
					  <TD>
						<INPUT type="hidden" name="empId" id="empId" value="<%=empId%>">
						<INPUT type="text" size="25" maxlength="50" name="empName" id="empName" value="<%=empName%>" onchange="myForm.empId.value='0';" onkeypress='if(isEnterPressed()) openEntDialog("EMP", "A", "myForm", "empId", "empName", "<%= contextRoot%>");'>
						<INPUT type="button" name="searchEmployee" value=" v " onclick='openEntDialog("EMP", "A", "myForm", "empId", "empName","<%=contextRoot %>");'></TD>
				    </TR>
	 			</TABLE>
			</FIELDSET>
		</TD>
    </TR>
	<TR class="pcrinfo">
        <TD colspan="4">         
	        <FIELDSET class="GroupBorder"><LEGEND class="labelGodStyle">&nbsp;<%=rb.getString("P12110015")%>&nbsp;</LEGEND>
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
					<TR class="pcrinfo1">
	      	 			<TD class="label" width="20%">&nbsp;<%=rb.getString("P12110016")%></TD>
	      				<TD width="30%"><SELECT size="1" name="depMethod" class="text">
							<OPTION value="" <%=depMethod.equals("")?"selected":""%>></OPTION>
							<%while(depreciationRS.next()){%>
							<OPTION value="<%=depreciationRS.getString(ConstantValue.CODE_COL)%>" <%=depreciationRS.getString(ConstantValue.CODE_COL).equals(depMethod)?"selected":""%>><%=depreciationRS.getString(ConstantValue.DESC_COL)==null?"":depreciationRS.getString(ConstantValue.DESC_COL)%></OPTION>
							<%}%>
							</SELECT>
						</TD>
	      				<TD width="30%" class="label">&nbsp;<%=rb.getString("P12110018")%></TD>
					    <TD width="20%"><INPUT type="text" name="salvage" value="<%=salvage%>" size="10" maxlength="22" onKeyPress="OnlyDecimal();"></TD>
	    			</TR>
					<TR class="pcrinfo">
	      	 			<TD width="20%" class="label">&nbsp;<%=rb.getString("P12110017")%></TD>
					    <TD width="30%"><INPUT type="text" name="lifeTime" value="<%=lifeTime%>" size="3" maxlength="3" onKeyPress="OnlyDecimal();"></TD>
	      				<TD width="30%" class="label">&nbsp;<%=rb.getString("P12100007")%></TD>
					    <TD width="20%"><INPUT type="text" name="totDepMnths" value="<%=totDepMnths%>" size="3" maxlength="3" onKeyPress="OnlyDecimal();"></TD>
	    			</TR>
	 			</TABLE>
			</FIELDSET>
		</TD>
    </TR>
	<TR class="pcrinfo">
        <TD colspan="4">         
	        <FIELDSET class="GroupBorder"><LEGEND class="labelGodStyle">&nbsp;<%=rb.getString("P12110019")%>&nbsp;</LEGEND>
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
					<TR class="pcrinfo1">
	      	 			<TD width="25%" class="label">&nbsp;<%=rb.getString("P12110020")%></TD>
						<TD>
						    <INPUT type="hidden" name="level0" value="<%=glAstAcct%>"> 
						    <INPUT type="text" size="50" name="levelText0" maxlength="55" value="<%=Format.encodeHTML(levelText0)%>" onChange="myForm.level0.value='';" onkeypress='if(isEnterPressed()) openGLDialog("0", "", "A", "N", "<%= contextRoot %>");'>
						   	<INPUT type="button" name="glButton0" value=" v " onclick='openGLDialog("0", "", "A", "N", "<%= contextRoot %>");'>
						</TD>
	    			</TR>
					<TR class="pcrinfo">
	      	 			<TD class="label">&nbsp;<%=rb.getString("P12110021")%></TD>
						<TD>
						    <INPUT type="hidden" name="level3" value="<%=glLblAcct%>"> 
						    <INPUT type="text" size="50" name="levelText3" maxlength="55" value="<%=Format.encodeHTML(levelText3)%>" onChange="myForm.level3.value='';" onkeypress='if(isEnterPressed()) openGLDialog("3", "", "A", "N", "<%= contextRoot %>");'>
						   	<INPUT type="button" name="glButton3" value=" v " onclick='openGLDialog("3", "", "A", "N", "<%= contextRoot %>");'>
						</TD>
	    			</TR>
					<TR class="pcrinfo1">
	      	 			<TD class="label">&nbsp;<%=rb.getString("P12110022")%></TD>
						<TD>
						    <INPUT type="hidden" name="level1" value="<%=glDepAcct%>"> 
						    <INPUT type="text" size="50" name="levelText1" maxlength="55" value="<%=Format.encodeHTML(levelText1)%>" onChange="myForm.level1.value='';" onkeypress='if(isEnterPressed()) openGLDialog("1", "", "A", "N", "<%= contextRoot %>");'>
						   	<INPUT type="button" name="glButton1" value=" v " onclick='openGLDialog("1", "", "A", "N", "<%= contextRoot %>");'>
						</TD>
	    			</TR>
					<TR class="pcrinfo">
	      	 			<TD class="label">&nbsp;<%=rb.getString("P12110023")%></TD>
						<TD>
						    <INPUT type="hidden" name="level2" value="<%=glExpAcct%>"> 
						    <INPUT type="text" size="50" name="levelText2" maxlength="55" value="<%=Format.encodeHTML(levelText2)%>" onChange="myForm.level2.value='';" onkeypress='if(isEnterPressed()) openGLDialog("2", "", "A", "N", "<%= contextRoot %>");'>
						   	<INPUT type="button" name="glButton2" value=" v " onclick='openGLDialog("2", "", "A", "N", "<%= contextRoot %>");'>
						</TD>
	    			</TR>
	 			</TABLE>
			</FIELDSET>
		</TD>
    </TR>
  </TABLE>
<TABLE width="700" border="0" cellpadding="0" cellspacing="1">
<TR><TD align="center" height="30">
<INPUT name="Submit" type="submit" class="button" value="<%=rb.getString("B00008")%>" onClick="myForm._message.value='add';">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
<input name="Reset" type="reset" class="button" value="<%=rb.getString("B00009")%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input name="Cancel" type="submit" class="button" value="<%=rb.getString("B00010")%>" onclick="myForm._message.value='<%=LKUP.equals("Y")?"lookup":"list"%>';">
</TD></TR>
</TABLE>
</FORM>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>

