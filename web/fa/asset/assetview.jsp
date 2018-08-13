<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Page Designer V4.0 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
<TITLE>Fixed Asset View</TITLE>
</HEAD>
<BODY>
<%@ page import="javax.sql.RowSet,com.cap.wdf.action.*,com.cap.util.*,java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp"%>
<%! 
 static final String title = "P12130000"; 

 // column index 
 static final int ASNAME_COL 	= 1;
 static final int TAG_COL 		= 2;
 static final int STDESC_COL 	= 3;
 static final int SERIAL_COL 	= 4;
 static final int MODEL_COL 	= 5;
 static final int CLASSNAME_COL = 6;
 static final int MANUFNAME_COL	= 7;
 static final int VENDNAME_COL 	= 8;
 static final int ACQUDATE_COL 	= 9;
 static final int PONUM_COL 	= 10;
 static final int WARRANTY_COL 	= 11;
 static final int PRICE_COL 	= 12;
 static final int CURRENCY_COL 	= 13;
 //static final int LOCATION_COL 	= 14;
 static final int DEPRMETH_COL 	= 14;
 static final int LIFETIME_COL 	= 15;
 static final int SALVAGE_COL 	= 16;
 static final int GLACC_COL 	= 17;
 static final int GLDEPACC_COL 	= 18;
 static final int GLEXPACC_COL 	= 19;
 static final int GLLBLACC_COL 	= 20;
 static final int LSTDATE_COL 	= 21;
 static final int TOTMNTH_COL 	= 22;
 static final int TOTDPAMT_COL 	= 23;
 static final int EMPID_COL 	= 24;
 static final int EMPNAME_COL 	= 25;
 static final int COLOR_COL 	= 26;
 static final int CLOSEDATE_COL = 27;
 static final int STATUSCODE_COL= 28;

 // column index for location 
 static final int CCNAME_COL = 1;
 static final int DATE_COL 	= 2;
%>
<%@ include file="../../ERP_COMMON/header.jspf" %>
<%
 FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
 RowSet assetViewRS = (RowSet) request.getAttribute("assetViewRS");
 RowSet locationRS = (RowSet) request.getAttribute("locationRS");
 int pag = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
 
 boolean odd = true;

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

 assetViewRS.next();
%>
<FORM name="myForm" method="post" action="<%=flowManager.createActionURI(flowManager.getActionName(request),request)%>">
<INPUT type="hidden" name="_message" value="list">
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

<TABLE width="700" border="0" cellpadding="0" cellspacing="1" class="Border">
	<TR class="tableheader">
	  <TD colspan="4"><%=rb.getString("P12130001")%></TD>
	</TR>
    <TR class="pcrinfo"> 
        <TD class="label">&nbsp;<%=rb.getString("P12130002")%></TD>
        <TD colspan="3"><%=assetViewRS.getString(ASNAME_COL)%></TD>
    </TR>
    <TR class="pcrinfo1"> 
  		<TD class="label">&nbsp;<%=rb.getString("P12130003")%></TD>
  		<TD><%=assetViewRS.getString(STDESC_COL)%>
<%	if(assetViewRS.getString(STATUSCODE_COL).equals("CL")) { %>
			&nbsp;&nbsp;(<%=Format.displayDate(assetViewRS.getString(CLOSEDATE_COL))%>)
<%	} %>
  		</TD>
        <TD class="label"><%=rb.getString("P12130004")%></TD>
        <TD><%=assetViewRS.getString(TAG_COL)%></TD>
    </TR>
    <TR class="pcrinfo">
        <TD class="label">&nbsp;<%=rb.getString("P12130005")%></TD>
        <TD><%=assetViewRS.getString(SERIAL_COL)%></TD>
        <TD class="label"><%=rb.getString("P12130006")%></TD>
        <TD><%=assetViewRS.getString(MODEL_COL)%></TD>
    </TR>
    <TR class="pcrinfo1"> 
        <TD class="label">&nbsp;<%=rb.getString("P12130007")%></TD>
        <TD colspan="3"><%=assetViewRS.getString(CLASSNAME_COL)%></TD>
    </TR>
    <TR class="pcrinfo"> 
        <TD class="label">&nbsp;<%=rb.getString("P12130008")%></TD>
        <TD colspan="3"><%=assetViewRS.getString(MANUFNAME_COL)%></TD>
    </TR>
    <TR class="pcrinfo1"> 
        <TD class="label">&nbsp;<%=rb.getString("P12130009")%></TD>
        <TD colspan="3"><%=assetViewRS.getString(VENDNAME_COL)%></TD>
    </TR>
    <TR class="pcrinfo"> 
        <TD width="20%" class="label">&nbsp;<%=rb.getString("P12130010")%></TD>
        <TD width="30%"><%=Format.displayDate(assetViewRS.getString(ACQUDATE_COL))%></TD>
        <TD width="20%" class="label"><%=rb.getString("P12130011")%></TD>
        <TD width="30%"><%=assetViewRS.getString(PONUM_COL)%></TD>
    </TR>                  
    <TR class="pcrinfo1">
        <TD class="label">&nbsp;<%=rb.getString("P12130012")%></TD>
        <TD><%=Format.displayDate(assetViewRS.getString(WARRANTY_COL))%></TD>
        <TD class="label"><%=rb.getString("P12110025")%></TD>
        <TD><%=assetViewRS.getString(COLOR_COL)%></TD>
    </TR>   
    <TR class="pcrinfo">
        <TD class="label">&nbsp;<%=rb.getString("P12130013")%></TD>
        <TD><%=Format.displayCurrency(assetViewRS.getBigDecimal(PRICE_COL))%> &nbsp;<%=assetViewRS.getString(CURRENCY_COL)%> </TD>
        <TD class="label"><%=rb.getString("P12110024")%></TD>
        <TD><%=assetViewRS.getString(EMPNAME_COL)%></TD>
    </TR>   
	<TR><TD colspan="4">         
        <FIELDSET class="GroupBorder"><LEGEND class="labelGodStyle">&nbsp;<%=rb.getString("P12130014")%>&nbsp;</LEGEND>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR class="pcrinfo">
      	 			<TD class="label" width="25%"> <%=rb.getString("P12130015")%></TD>
      				<TD colspan="3"><%=assetViewRS.getString(DEPRMETH_COL)%></TD>
    			</TR>
				<TR class="pcrinfo1">
      	 			<TD width="25%" class="label"><%=rb.getString("P12130016")%></TD>
      				<TD width="25%"><%=assetViewRS.getString(LIFETIME_COL)%></TD>
      				<TD width="25%" class="label"><%=rb.getString("P12130017")%></TD>
      				<TD width="25%"><%=Format.displayCurrency(assetViewRS.getBigDecimal(SALVAGE_COL))%>&nbsp;<%=assetViewRS.getString(CURRENCY_COL)%></TD>
    			</TR>
 				<TR class="pcrinfo">
      	 			<TD class="label" width="25%"> <%=rb.getString("P12130026")%></TD>
      				<TD colspan="3"><%=Format.displayDate(assetViewRS.getString(LSTDATE_COL))%></TD>
    			</TR>
				<TR class="pcrinfo1">
      	 			<TD width="25%" class="label"><%=rb.getString("P12100007")%></TD>
      				<TD width="25%"><%=assetViewRS.getString(TOTMNTH_COL)%></TD>
      				<TD width="25%" class="label"><%=rb.getString("P12130028")%></TD>
      				<TD width="25%"><%=Format.displayCurrency(assetViewRS.getBigDecimal(TOTDPAMT_COL))%>&nbsp;<%=assetViewRS.getString(CURRENCY_COL)%></TD>
    			</TR>
 			</TABLE>
		</FIELDSET>
	</TD></TR>
	<TR><TD colspan="4">         
        <FIELDSET class="GroupBorder"><LEGEND class="labelGodStyle">&nbsp;<%=rb.getString("P12130018")%>&nbsp;</LEGEND>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR class="pcrinfo">
      	 			<TD width="20%" class="label"> <%=rb.getString("P12130019")%></TD>
      				<TD><%=assetViewRS.getString(GLACC_COL)%></TD>
    			</TR>
				<TR class="pcrinfo1">
      	 			<TD class="label">  <%=rb.getString("P12130020")%></TD>
      				<TD><%=assetViewRS.getString(GLLBLACC_COL)%></TD>
    			</TR>
				<TR class="pcrinfo">
      	 			<TD class="label"> <%=rb.getString("P12130021")%></TD>
      				<TD><%=assetViewRS.getString(GLDEPACC_COL)%></TD>
    			</TR>
				<TR class="pcrinfo1">
      	 			<TD class="label"> <%=rb.getString("P12130022")%></TD>
      				<TD><%=assetViewRS.getString(GLEXPACC_COL)%></TD>
    			</TR>
			</TABLE>
		</FIELDSET>
	</TD></TR>
	<TR><TD colspan="4">         
        <FIELDSET class="GroupBorder"><LEGEND class="labelGodStyle">&nbsp;<%=rb.getString("P12130023")%>&nbsp;</LEGEND>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR class="tableheader">
      	 			<TD width="20%"><%=rb.getString("P12130024")%></TD>
      				<TD><%=rb.getString("P12130025")%></TD>
    			</TR>
<%	if(!locationRS.next()) {%>
				<TR class="label">
				 <TH colspan="2"><IMG src="../erp/ERP_COMMON/images/info.gif" width="16" height="16" border="0"> <%=rb.getString("B00007")%></TH>
				</TR>
<%
	}
    			
	locationRS.beforeFirst();
	while (locationRS.next()) {
%>
			    <TR class="<%=odd?"pcrinfo":"pcrinfo1"%>">
			      <TD align="center"><%=Format.displayDate(locationRS.getString(DATE_COL))%></TD>
			      <TD><%=locationRS.getString(CCNAME_COL)%></TD>
			    </TR>
<%
		odd = ! odd;
    }
%>
			</TABLE>
		</FIELDSET>
	</TD></TR>
</TABLE>  
<TABLE width="700" border="0" cellpadding="0" cellspacing="1">
<TR><TD align="center" height="30">
  <INPUT type="submit" name="back" class="button" value="<%=rb.getString("B00012")%>" onclick="myForm._message.value='list';">
</TD></TR>
</TABLE>  
</FORM>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>