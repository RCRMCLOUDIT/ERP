<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V4.0 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
<TITLE>Item Classification </TITLE>
</HEAD>
<%@ page import="javax.sql.RowSet,com.cap.wdf.action.*,com.cap.util.*,java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp"%>
<%! 
 static final String title = "Item Classification"; 
%>
<%@ include file="../../ERP_COMMON/header.jspf" %>

<SCRIPT language="JavaScript" src="../erp/fa/js/classification.js"></SCRIPT>

<BODY onLoad="if(myForm.parentId.value !='0'){myForm.name.focus();}else{myForm.classTypeId.focus();}">
<%
FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
RowSet typeRS = (RowSet) request.getAttribute("typeRS");
RowSet pathRS = (RowSet) request.getAttribute("pathRS");
pathRS.next();

String rootPath = flowManager.createActionURI(flowManager.getActionName(request),request);
boolean odd = true;

String classTypeId	= request.getParameter("classTypeId") == null ? "0" : request.getParameter("classTypeId");
String parentId		= request.getParameter("parentId")==null?"0":request.getParameter("parentId");
String name 		= request.getParameter("name")==null?"":request.getParameter("name");
String pag			= request.getParameter("page")==null?"":request.getParameter("page");
String filter 		= request.getParameter("filter")==null?"":request.getParameter("filter");
String isLastLevel	= request.getParameter("isLastLevel")==null?"N":request.getParameter("isLastLevel");

Object errMsg = request.getAttribute("actionErrors"); 
%>
<jsp:useBean id="errMsgHelper" class="com.cap.util.ErrorMessageHelper" scope="page">
<jsp:setProperty property="errorMessage" value="<%= errMsg%>" name="errMsgHelper" />
<jsp:setProperty property="contextPath" value="<%= request.getContextPath() %>" name="errMsgHelper" />
</jsp:useBean>
<jsp:getProperty property="htmlErrorMessage" name="errMsgHelper" />

<TABLE width="600">
  <TR class="label"><TD><%=pathRS.getString(1)%></TD></TR>
</TABLE>
<FORM name="myForm" method="post" action="<%=rootPath%>" onsubmit="return class_submit(myForm);">
<INPUT type="hidden" name="_message" value="add">
<INPUT type="hidden" name="parentId" value="<%=parentId%>">
<INPUT type="hidden" name="page" value="<%=pag%>">
<INPUT type="hidden" name="filter" value="<%=filter%>">
<% if(!parentId.equals("0")) { %>
<INPUT type="hidden" name="classTypeId" value="<%=classTypeId%>">
<% } %>
<TABLE width="600" border="0" class="Border" cellpadding="0" cellspacing="1">
    <TR>
      <TD colspan="2" class="tableheader">New Item Classification</TD>
    </TR>
<%
  if(parentId.equals("0"))
  {	odd = !odd;
%>
	<TR class="pcrinfo1">
	  <TD class="label"><SPAN class="textRed">*</SPAN>Classification Type</TD>
	  <TD><SELECT name="classTypeId" class="text">
		<OPTION <%=classTypeId.length()==0?"selected":""%>> </OPTION>
<% 
	typeRS.beforeFirst();
	while (typeRS.next()) {
%>
		<OPTION value="<%=typeRS.getString(ConstantValue.CODE_COL)%>" <%=classTypeId.equals(typeRS.getString(1))?"selected":""%>><%=typeRS.getString(ConstantValue.DESC_COL)%></OPTION>
<% 	} %>
		</SELECT></TD>
	</TR>          
<% } %>    
    <TR class="<%=odd?"pcrinfo1" : "pcrinfo" %>">
      <TD width="20%" class="label"><span class="textRed">*</span>Name</TD>
      <TD width="80%"><INPUT type="text" name="name" value="<%=Format.encodeHTML(name)%>" size="50" maxlength="50"></TD>
    </TR>
    <TR class="<%=odd?"pcrinfo" : "pcrinfo1" %>">
      <TD class="label">&nbsp;Is Last Level</TD>
      <TD><INPUT type="radio" name="isLastLevel" value="Y" <%=isLastLevel.equals("Y")?"checked":""%>>&nbsp;Yes&nbsp;&nbsp;&nbsp;<INPUT type="radio" name="isLastLevel" value="N" <%=isLastLevel.equals("N")?"checked":""%>>&nbsp;No</TD>
    </TR>
</TABLE>
<TABLE width="600" border="0" cellpadding="0" cellspacing="1">
<TR>
  <TD height="30" align="center">
  <INPUT name="Submit" type="submit" class="button" value="Save">&nbsp;&nbsp;&nbsp;&nbsp; 
  <INPUT name="Reset" type="reset" class="button" value="Reset">&nbsp;&nbsp;&nbsp;&nbsp; 
  <INPUT name="Cancel" type="submit" class="button" value="Cancel" onClick="myForm._message.value='list';">
  </TD>
</TR>
</TABLE>
</FORM>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>