<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V4.0 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
<TITLE>Item Classification</TITLE>
</HEAD>
<%@ page import="javax.sql.RowSet,com.cap.wdf.action.*,com.cap.util.*,java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp"%>
<%! 
 static final String title = "Item Classification";

//Classification Information
static final int TYPEID_COLUMN		= 1;
static final int TYPENAME_COLUMN	= 2;
static final int PARENTID_COLUMN	= 3;
static final int CLSNAME_COLUMN		= 4;
static final int ISLASTLEVEL_COLUMN	= 5;
static final int ACTIVE_COLUMN		= 6;
static final int LEVEL_COLUMN 		= 7;
static final int TOPLEVELID_COLUMN	= 8;

//Classification type information
static final int PCNAME_COLUMN 		= 1;
static final int PCMAXLEVEL_COLUMN	= 2;
static final int PCVARLEVEL_COLUMN	= 3;
static final int PCMULTSLCT_COLUMN	= 4;
static final int PCCTGTYPID_COLUMN	= 5;
%>
<%@ include file="../../ERP_COMMON/header.jspf" %>

<SCRIPT language="JavaScript" src="../erp/fa/js/classification.js"></SCRIPT>

<BODY onLoad="document.myForm.name.focus();">
<% 
FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
RowSet classRS 	= (RowSet) request.getAttribute("classRS");
RowSet pathRS	= (RowSet) request.getAttribute("pathRS");
RowSet typeRS 	= (RowSet) request.getAttribute("typeRS");

classRS.next();
pathRS.next();
//Editing Classification Variables
String classId			= request.getParameter("classId") == null ? "0" : request.getParameter("classId");
String classTypeId		= request.getParameter("classTypeId") == null ? "0" : request.getParameter("classTypeId");
String parentIdSelected	= request.getParameter("parentId")==null?"0":request.getParameter("parentId");

String className		= request.getParameter("name")==null?classRS.getString(CLSNAME_COLUMN):request.getParameter("name");
String isLastLevel		= request.getParameter("isLastLevel")==null?classRS.getString(ISLASTLEVEL_COLUMN):request.getParameter("isLastLevel");
String active 			= request.getParameter("active")==null?classRS.getString(ACTIVE_COLUMN):request.getParameter("active");
String hasChild			= request.getParameter("hasChild")==null? "N":request.getParameter("hasChild");
String maxReached		= request.getParameter("maxReached")==null?"N":request.getParameter("maxReached");
String pag				= request.getParameter("page");
String filter			= request.getParameter("filter");

boolean odd = true;

//Classification Type Variables
String classTypeName = "";
String maxLevels = "";
String multipleSelect = "";
String variableLevels = "";

if(typeRS.next())
{
	classTypeName 	= typeRS.getString(PCNAME_COLUMN);
	maxLevels 		= typeRS.getString(PCMAXLEVEL_COLUMN);
	multipleSelect 	= typeRS.getString(PCMULTSLCT_COLUMN);
	variableLevels 	= typeRS.getString(PCVARLEVEL_COLUMN);
	classTypeId 	= typeRS.getString(PCCTGTYPID_COLUMN);
}

if(active.equals("Y"))
	active="Yes";
else if(active.equals("N"))
	active="No";
	
if(multipleSelect.equals("Y"))
	multipleSelect = "Yes";
else if(multipleSelect.equals("N"))
	multipleSelect = "No";

if(variableLevels.equals("Y"))
	variableLevels = "Yes";
else if(variableLevels.equals("N"))
	variableLevels = "No";	
	
Object errMsg = request.getAttribute("actionErrors"); 
%>
<TABLE width="600">
<TR class="label"><TD><%=pathRS.getString(1)%></TD></TR>
</TABLE>
<jsp:useBean id="errMsgHelper" class="com.cap.util.ErrorMessageHelper" scope="page">
<jsp:setProperty property="errorMessage" value="<%= errMsg%>" name="errMsgHelper" />
<jsp:setProperty property="contextPath" value="<%= request.getContextPath() %>" name="errMsgHelper" />
</jsp:useBean>
<jsp:getProperty property="htmlErrorMessage" name="errMsgHelper" />

<FORM name="myForm" method="post" action="<%=flowManager.createActionURI(flowManager.getActionName(request),request)%>" onsubmit="return class_submit(myForm);">
<INPUT type="hidden" name="_message" value="update">
<INPUT type="hidden" name="classId" value="<%=classId%>">
<INPUT type="hidden" name="parentId" value="<%=parentIdSelected%>">
<INPUT type="hidden" name="classTypeId" value="<%=classTypeId%>">
<INPUT type="hidden" name="page" value="<%=pag%>">
<INPUT type="hidden" name="filter" value="<%=filter%>">
<TABLE width="600" border="0" class="Border" cellpadding="0" cellspacing="1">
    <TR>
      <TD class="tableheader">Edit Classification</TD>
    </TR>
	<TR class="pcrinfo">
      <TD>
      <FIELDSET class="GroupBorder"><LEGEND class="labelGodStyle">&nbsp;Classification Information</LEGEND>
	  <TABLE width="100%" cellspacing="1" cellpadding="0">
		<TR class="pcrinfo">
          <TD class="label" width="25%"><SPAN class="textRed">*</SPAN>Name</TD>
          <TD width="75%"><input type="text" name="name" value="<%=Format.encodeHTML(className)%>" size="50" maxlength="50"> </TD>
		</TR>
<%	if(!hasChild.equals("Y") && !maxReached.equals("Y")) { %>
		<TR class="pcrinfo1">
          <TD class="label">&nbsp;Is Last Level</TD>
          <TD><INPUT type="radio" name="isLastLevel" value="Y" <%=isLastLevel.equals("Y")?"checked":""%>>&nbsp;Yes&nbsp;&nbsp;&nbsp;<INPUT type="radio" name="isLastLevel" value="N" <%=isLastLevel.equals("N")?"checked":""%>>&nbsp;No</TD>
		</TR>          
<%
		odd = !odd;
	} else if(maxReached.equals("Y")) {
%>
		<TR class="pcrinfo1">
          <TD class="label">&nbsp;Is Last Level</TD>
          <TD><INPUT type="radio" name="isLastLevel" value="Y" <%=isLastLevel.equals("Y")?"checked":""%>>&nbsp;Yes</TD>
		</TR>   
<%	
		odd = !odd;
	} 
%>
	  <TR class="<%=odd?"pcrinfo1":"pcrinfo"%>">
	    <TD class="label">&nbsp;Active</TD>
	    <TD><%=active%></TD>     
	  </TR>	
 	</TABLE>
	</FIELDSET>
    </TD>
	</TR>
    <TR>
	  <TD class="pcrinfo">
      <FIELDSET class="GroupBorder"><LEGEND class="labelGodStyle">&nbsp;Classification Type Information</LEGEND>
	  <TABLE width="100%" cellspacing="1" cellpadding="0">
		<TR class="pcrinfo">
          <TD class="label" width="25%">&nbsp;Classification Type</TD>
          <TD width="75%"><%=classTypeName.trim()%></TD>
		</TR>
		<TR class="pcrinfo1">
          <TD class="label">&nbsp;Maximum Level</TD>
          <TD><%=maxLevels%></TD>
		</TR>          
		<TR class="pcrinfo">
          <TD class="label">&nbsp;Allow Multiple Selection</TD>
          <TD><%=multipleSelect%></TD>
		</TR>
		<TR class="pcrinfo1">
          <TD class="label">&nbsp;Allow Variable Levels</TD>
          <TD><%=variableLevels%></TD>
		</TR>
 	  </TABLE>
 	  </FIELDSET>
	  </TD>
	</TR>
</TABLE>
<TABLE width="600" border="0" cellpadding="0" cellspacing="1">
  <TR>
	<TD align="center" height="30">
	<INPUT name="Submit" type="submit" class="button" value="Save">&nbsp;&nbsp;&nbsp;&nbsp; 
	<INPUT name="Reset" type="reset" class="button" value="Reset">&nbsp;&nbsp;&nbsp;&nbsp; 
	<INPUT name="cancel" type="submit" class="button" value="Cancel" onClick="myForm._message.value ='list';">
    </TD>
  </TR>
</TABLE>
</FORM>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>