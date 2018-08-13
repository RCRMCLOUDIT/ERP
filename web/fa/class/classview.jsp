<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V4.0 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
<TITLE>Item Classification Detail</TITLE>
</HEAD>
<BODY>
<%@ page import="javax.sql.RowSet,com.cap.wdf.action.*,com.cap.util.*,java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp"%>
<%! 
static final String title = "Item Classification Detail";

//Classification Information
static final int CLSNAME_COLUMN		= 4;
static final int ISLASTLEVEL_COLUMN	= 5;
static final int ACTIVE_COLUMN		= 6;

//Classification type information
static final int PCNAME_COLUMN 		= 1;
static final int PCMAXLEVEL_COLUMN	= 2;
static final int PCVARLEVEL_COLUMN	= 3;
static final int PCMULTSLCT_COLUMN	= 4;
static final int PCCTGTYPID_COLUMN	= 5;
%>
<%@ include file="../../ERP_COMMON/header.jspf" %>
<% 
FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
RowSet classRS 	= (RowSet) request.getAttribute("classRS");
RowSet typeRS 	= (RowSet) request.getAttribute("typeRS");
RowSet pathRS	= (RowSet) request.getAttribute("pathRS");
classRS.next();
pathRS.next();

String className = classRS.getString(CLSNAME_COLUMN);
String lastlvl	 = classRS.getString(ISLASTLEVEL_COLUMN);
String active 	 = classRS.getString(ACTIVE_COLUMN);

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
}
%>
<TABLE width="600">
<TR class="label"><TD><%=pathRS.getString(1)%></TD></TR>
</TABLE>
<TABLE width="600" border="0" class="Border" cellpadding="0" cellspacing="1">
    <TR>
        <TD class="tableheader">View Item Classification</TD>
    </TR>
	<TR class="pcrinfo">
	  <TD>
	  <FIELDSET class="GroupBorder"><LEGEND class="labelGodStyle">&nbsp;Classification Information</LEGEND>
	  <TABLE width="100%" cellspacing="1" cellpadding="0">
		<TR class="pcrinfo">
		  <TD class="label" width="25%">&nbsp;Name</TD>
          <TD width="75%"><%=className%></TD>
		</TR>
		<TR class="pcrinfo1">
          <TD class="label">&nbsp;Is Last Level</TD>
          <TD><%=lastlvl.equals("Y")?YES_STR:NO_STR%></TD>
        </TR>          
        <TR class="pcrinfo">
          <TD class="label">&nbsp;Active</TD>
          <TD><%=active.equals("Y")?YES_STR:NO_STR%></TD>
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
	  <TR class="pcrinfo1">
        <TD class="label">&nbsp;Maximum Level</TD>
        <TD><%=maxLevels%></TD>
      </TR>          
      <TR class="pcrinfo">
        <TD class="label">&nbsp;Allow Multiple Selection</TD>
        <TD><%=multipleSelect.equals("Y")?YES_STR:NO_STR%></TD>
      </TR>
      <TR class="pcrinfo1">
        <TD class="label">&nbsp;Allow Variable Levels</TD>
        <TD><%=variableLevels.equals("Y")?YES_STR:NO_STR%></TD>
      </TR>
	</TABLE>
	</FIELDSET>
    </TD>
	</TR>
</TABLE>
<TABLE width="600" border="0" cellpadding="0" cellspacing="1">
<TR>
  <TD align="center" height="30">
	<INPUT name="Back" type="button" class="button" value="Back" onClick="window.history.back();">
  </TD>
</TR>
</TABLE>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>