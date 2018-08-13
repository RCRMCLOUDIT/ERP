<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Page Designer V4.0 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../erp/ERP_COMMON/Master.css" type="text/css">
<%@ page import="javax.sql.RowSet,com.cap.wdf.action.*,com.cap.util.*,java.util.*, java.net.*, java.math.BigDecimal" errorPage="../../ERP_COMMON/error.jsp"%>
<TITLE>Fixed Asset List</TITLE>
</HEAD>
<BODY>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<%! 
 static final String title = "P12100000"; 

 // column index 
 static final int COSCE_COL	 	= 1;
 static final int ASSNA_COL 	= 2;
 static final int STATU_COL 	= 3;
 static final int ASSID_COL		= 4;
 static final int STAID_COL 	= 5;
 static final int TAGNU_COL 	= 6;
 static final int EMPNA_COL 	= 7;
 static final int TOTDEPMONTH_COL = 8;
 static final int LIFETIME_COL 	= 9;
 static final int SALVAMT_COL 	= 10;
%>
<%@ include file="../../ERP_COMMON/header.jspf" %>
<%@ include file="../../ERP_COMMON/a_utilJsMsg.jspf" %>

<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/util.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/ERP_COMMON/js/overlib_mini.js"></SCRIPT>
<SCRIPT language="JavaScript" src="../erp/fa/js/asset.js"></SCRIPT>
<%
 FlowManager flowManager = (FlowManager) request.getAttribute("flowManager");
 RowSet assetRS = (RowSet) request.getAttribute("assetRS");

 String anchor1 = rb.getString("B00023");
 String anchor2 = rb.getString("B00024");
 String anchor3 = rb.getString("B00117");
 String anchor4 = rb.getString("B00170");
 String anchor5 = rb.getString("B00030");
 String anchor6 = rb.getString("B00174");
 String anchor7 = rb.getString("B00175");

 int pag = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
 int rowCount = 0;
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
  
 String rootPath = flowManager.createActionURI(flowManager.getActionName(request),request);

 String baseLink = rootPath + 
							  "?page=" + pag + 
							  "&assName=" + assName + 
 							  "&LKUP=N"+ 
							  "&asTag=" + asTag + 
							  "&lfdt=" + lfdt + 
							  "&ltdt=" + ltdt + 
							  "&ffdt=" + ffdt + 
							  "&ftdt=" + ftdt + 
							  "&vendI=" + vendI + 
							  "&lst=" + lst + 
							  "&spaCurr=" + spaCurr + 
							  "&entType9=" + entType9 + 
							  "&empCustId9=" + empCustId9 + 
							  "&lcty=" + lcty + 
							  "&lcid=" + lcid + 
							  "&lemp=" + lemp ;
 
 String link1 = baseLink+ "&_message=view&assetId=";
 String link2 = baseLink+ "&_message=edit&assetId=";
 String link3 = baseLink+ "&_message=edit&register=Y&assetId=";
 String link4 = baseLink+ "&_message=relocpre&assetId=";
 String link5 = baseLink+ "&_message=delete&assetId=";
 String link6 = baseLink+ "&_message=depallpre&assetId=";
 String link7 = baseLink+ "&_message=removepre&assetId=";
 
 String links1 = "";
 String links2 = "";
 String links3 = "";
 String links4 = "";
 String links5 = "";
 String links6 = "";
 String links7 = "";
 
 String assetId = "";
 String stCode = "";

 Object errMsg = request.getAttribute("actionErrors"); 
%>
<jsp:useBean id="errMsgHelper" class="com.cap.util.ErrorMessageHelper" scope="page">
<jsp:setProperty property="errorMessage" value="<%= errMsg%>" name="errMsgHelper" />
<jsp:setProperty property="contextPath" value="<%= request.getContextPath() %>" name="errMsgHelper" />
</jsp:useBean>
<jsp:getProperty property="htmlErrorMessage" name="errMsgHelper" />

<TABLE width="700" border="0" class="Border" cellpadding="0" cellspacing="1">
    <TR class="tableheader"><TD colspan="6"><%=rb.getString("P12100001")%></TD></tr>
    <TR class="tableheader">
      <TD><%=rb.getString("P12100002")%></TD>
      <TD><%=rb.getString("P12100003")%></TD>
      <TD><%=rb.getString("P12100004")%></TD>
      <TD><%=rb.getString("P12100030")%></TD>
      <TD><%=rb.getString("P12100031")%></TD>
      <TD><%=rb.getString("P12100005")%></TD>
    </tr>
<%	if(!assetRS.next()) {%>
	<TR>
	 <TH colspan="6" class="label"><IMG src="../erp/ERP_COMMON/images/info.gif" width="16" height="16" border="0"> <%=rb.getString("B00007")%></TH>
	</TR>
<%
	}
	
	assetRS.beforeFirst();
	while (assetRS.next() && rowCount < ConstantValue.PAGE_SIZE) 
	{
		assetId	= assetRS.getString(ASSID_COL);
		stCode	= assetRS.getString(STAID_COL);
    	links1 = link1 + assetId;
    	links2 = link2 + assetId;
    	links3 = link3 + assetId;
    	links4 = link4 + assetId;
    	links5 = link5 + assetId;
    	links6 = link6 + assetId;
    	links7 = link7 + assetId;

		StringBuffer link = new StringBuffer();
		link.append(ConstantValue.PRE_TAG + links1 + ConstantValue.MID_TAG + anchor1 + ConstantValue.END_TAG);
		if ( !stCode.equals("CL"))
			link.append(ConstantValue.PRE_TAG + links2 + ConstantValue.MID_TAG + anchor2 + ConstantValue.END_TAG);

		if (stCode.equals("DR"))
		{
			link.append(ConstantValue.PRE_TAG + links3 + ConstantValue.MID_TAG + anchor3 + ConstantValue.END_TAG);
			link.append(ConstantValue.PRE_TAG + links5 + ConstantValue.MIDDEL_TAG + anchor5 + ConstantValue.END_TAG);
		}
		else 
		{
			//link6=depreciate all, apply to registered but not fully deped item
			//link7=remove, 		apply to registered, fully deped item and salvageAmt may or may not be ZERO
			if ( !stCode.equals("CL"))
				link.append(ConstantValue.PRE_TAG + links4 + ConstantValue.MID_TAG + anchor4 + ConstantValue.END_TAG);
			if(stCode.equals("RG") && assetRS.getBigDecimal(TOTDEPMONTH_COL).intValue() != assetRS.getBigDecimal(LIFETIME_COL).intValue())
				link.append(ConstantValue.PRE_TAG + links6 + ConstantValue.MID_TAG + anchor6 + ConstantValue.END_TAG);

			if(stCode.equals("RG") && assetRS.getBigDecimal(TOTDEPMONTH_COL).intValue() == assetRS.getBigDecimal(LIFETIME_COL).intValue())
				link.append(ConstantValue.PRE_TAG + links7 + ConstantValue.MID_TAG + anchor7 + ConstantValue.END_TAG);
		}	
%>
    <TR class="<%=odd?"pcrinfo":"pcrinfo1"%>">
      <TD><A href="javascript:void(0);" class="link_alt_underline" onmouseover="this.className='link_over_alt_underline'; return overlib('<%=link%>', STICKY, WIDTH, 100, BORDER, 1, HAUTO, OFFSETY,-30,OFFSETX,20, TIMEOUT,3000);" onmouseout="this.className='link_alt_underline';return nd();"><%=assetRS.getString(ASSNA_COL)%></A></TD>
      <TD><%=assetRS.getString(TAGNU_COL)%></TD>
      <TD><%=assetRS.getString(COSCE_COL)%></TD>
      <TD><%=assetRS.getBigDecimal(LIFETIME_COL).intValue()%></TD>
      <TD><%=assetRS.getBigDecimal(TOTDEPMONTH_COL).intValue()%></TD>
      <TD><%=assetRS.getString(STATU_COL)%></TD>
    </TR>
<%
		rowCount++;
		odd = ! odd;
    }
%>
</TABLE>

<FORM name="myForm" action="<%=rootPath%>">
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

<TABLE border="0" width="700" cellspacing="0" cellpadding="0">
  <TR>
	<TD width="20%"><INPUT type="submit" name="Submit" class="button" value="<%=rb.getString("B00001")%>" onclick="myForm._message.value='new';"></TD>
    <TD align="center" width="40%" class="label"><INPUT type="submit" name="back" value="<%=rb.getString("B00162")%>" class="button" onclick="myForm._message.value='lookup';"></TD>

    <TD width="20%" align="right">
<%	if (pag > 1){	%>
		<INPUT type="submit" name="prev" value="<%=rb.getString("B00005")%>" class="button" onclick="myForm.page.value='<%=pag-1%>';">
<% 	}	%>
    </TD>
	<TD width="20%" align="right">
<%	if (!assetRS.isAfterLast()) { %>
		<INPUT type="submit" name="next" value="<%=rb.getString("B00006")%>" class="button" onclick="myForm.page.value='<%=pag+1%>';">
<%	}	%>
	</TD>
  </TR>
</TABLE>
</FORM>
<%@ include file="../../ERP_COMMON/footer.jspf" %>
</BODY>
</HTML>

