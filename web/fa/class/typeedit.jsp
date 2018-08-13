<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V4.0 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<META http-equiv="Cache-Control" content="no-cache">
<META http-equiv="Expires" content="0">
<META http-equiv="Pragma" content="no-cache">
<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
</HEAD>
<TITLE>Edit Item Classification Type</TITLE>
<%@ page import="javax.sql.RowSet,com.cap.wdf.action.*,com.cap.util.*,java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp"%>
<%! 
 static final String title = "Item Classification Type";
 
 //column index
 static final int TYPENAME_COL 	= 1;
 static final int STATUS_COL 	= 2;
 static final int MAXLEVEL_COL 	= 3;
 static final int VARLEVEL_COL 	= 4;
 static final int MULTLEVEL_COL = 5;
 //static final int SYSSEEDCOL = 6;
%>
<%@ include file="../../ERP_COMMON/header.jspf" %>
<%@ include file="../../ERP_COMMON/a_utilJsMsg.jspf" %>
<%@ include file="../../ERP_COMMON/a_addSymbolJsMsg.jspf" %>

<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/util.js"></SCRIPT>
<SCRIPT language="Javascript" src="../erp/ERP_COMMON/js/addSymbol.js"></SCRIPT>
<SCRIPT language="Javascript" src="../erp/fa/js/classtype.js"></SCRIPT>

<BODY onload="myForm.typeName.focus();">
<%
 FlowManager flowManager = (FlowManager	) request.getAttribute("flowManager");
 RowSet typeRS = (RowSet) request.getAttribute("typeRS");
 typeRS.next();
 
 String rootPath = flowManager.createActionURI(flowManager.getActionName(request),request);
 String typeId 	= request.getParameter("typeId");
 String typeName = request.getParameter("typeName")==null?typeRS.getString(TYPENAME_COL):request.getParameter("typeName");
 String maxLevel= request.getParameter("maxLevel")==null?typeRS.getString(MAXLEVEL_COL):request.getParameter("maxLevel");
 String varLevel = request.getParameter("varLevel")==null?typeRS.getString(VARLEVEL_COL):request.getParameter("varLevel");
 String multLevel = request.getParameter("multLevel")==null?typeRS.getString(MULTLEVEL_COL):request.getParameter("multLevel");
 
 Object errMsg = request.getAttribute("actionErrors"); 
%>
<jsp:useBean id="errMsgHelper" class="com.cap.util.ErrorMessageHelper" scope="page">
<jsp:setProperty property="errorMessage" value="<%= errMsg%>" name="errMsgHelper" />
<jsp:setProperty property="contextPath" value="<%= request.getContextPath() %>" name="errMsgHelper" />
</jsp:useBean>
<jsp:getProperty property="htmlErrorMessage" name="errMsgHelper" />

<FORM name="myForm" method="POST" action="<%=rootPath%>" onsubmit="return class_type_submit(myForm);">
<INPUT type="hidden" name="_message" value="update">
<INPUT type="hidden" name="typeId" value="<%=typeId%>">
<INPUT type="hidden" name="filter" value="<%=request.getParameter("filter")%>">
<INPUT type="hidden" name="page" value="<%=request.getParameter("page")%>">
<INPUT type="hidden" name="search" value="<%=request.getParameter("search")%>">

<TABLE width="600" border="0" cellpadding="1" cellspacing="1" class="Border">
	<TR class="tableheader">
	  <TD colspan="2">Edit Item Classification Type</TD>
	</TR>
    <TR class="pcrinfo">
		<TD width="30%" class="label"><span class="textRed">*</span>Name</TD>
		<TD width="70%"><INPUT type="text" name="typeName" value="<%=typeName%>" size="40" maxlength="50"></TD>
    </TR>
    <TR class="pcrinfo1">
    	<TD class="label"><span class="textRed">*</span>Maximum Level</TD>
		<TD><INPUT type="text" name="maxLevel" value="<%=maxLevel%>" onkeypress="OnlyDigits();" size="2" maxlength="2"></TD>
	</TR>	
    <TR class="pcrinfo">
    	<TD class="label">&nbsp;Variable Level</TD>
		<TD> 
		<INPUT type="radio" name="varLevel" value="Y" <%=varLevel.equals("Y")? "checked":""%>> Yes
		<INPUT type="radio" name="varLevel" value="N" <%=varLevel.equals("N")? "checked":""%>> No
		</TD>
    </TR>
    <TR class="pcrinfo1">
    	<TD class="label">&nbsp;Multiple Selection</TD>
		<TD> 
		<INPUT type="radio" name="multLevel" value="Y" <%=multLevel.equals("Y")? "checked":""%>> Yes
		<INPUT type="radio" name="multLevel" value="N" <%=multLevel.equals("N")? "checked":""%>> No
		</TD>
    </TR>
</TABLE>  
<TABLE width="600" border="0" cellpadding="0" cellspacing="1">
<TR><TD width="100%" align="center">
  <INPUT type="submit" name="save" class="button" value="Save">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <INPUT type="reset" name="Reset" class="button" value="Reset">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <INPUT type="submit" name="cancel" class="button" value="Cancel" onclick="myForm._message.value='list';">
</TD></TR>
</TABLE>  
</FORM>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>