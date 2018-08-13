package fa;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

import java.math.BigDecimal;

import wdf.command.*;
import wdf.action.*;

import util.*;
import com.cap.erp.common.*;


public class AssetAction extends ActionAdapter
{
   private static String VIEW_LIST = "/erp/fa/asset/assetlist.jsp"; // "AssetList";
   private static String VIEW_NEW  = "/erp/fa/asset/assetnew.jsp"; // "AssetNew";
   private static String VIEW_VIEW = "/erp/fa/asset/assetview.jsp"; // "AssetView";
   private static String VIEW_EDIT = "/erp/fa/asset/assetedit.jsp"; // "AssetEdit";      
   private static String VIEW_RELOC = "/erp/fa/asset/assetrelocate.jsp"; // "AssetReloc";      
   private static String VIEW_RUNLIST = "/erp/fa/asset/runlist.jsp"; // "AssetRunList";      
   private static String VIEW_RUNNEW = "/erp/fa/asset/runnew.jsp"; // "AssetRunNew";      
   private static String VIEW_REPORT = "/erp/fa/asset/assetreport.jsp"; // "AssetReport";      
   private static String VIEW_LOOKUP = "/erp/fa/asset/assetlookup.jsp"; // "AssetLookup";
   private static String VIEW_REMOVE = "/erp/fa/asset/assetremove.jsp"; // "AssetRemove";
   private static String VIEW_DEPALL = "/erp/fa/asset/assetdepall.jsp"; // "AssetDepAll";
   
   
   private static String SQLSP1_NAME = DBLibConstants.FALIB + "_OBJ.FAASSETS";
   private static String SQLSP2_NAME = DBLibConstants.FALIB + "_OBJ.FAASTADDS";
   private static String SQLSP3_NAME = DBLibConstants.FALIB + "_OBJ.FAASTDEPS";
   private static String SQLSP4_NAME = DBLibConstants.FALIB + "_OBJ.FADEPRUNS";
   private static String SQLSP5_NAME = DBLibConstants.FALIB + "_OBJ.FADEPRPTS";
   private static String SQLSP6_NAME = DBLibConstants.FALIB + "_OBJ.FARELOCS";
   private static String SQLSP7_NAME = DBLibConstants.FALIB + "_OBJ.FALKUPS";
   private static String SQLSP8_NAME = DBLibConstants.FALIB + "_OBJ.FAASTDELS";
   //private static String SQLSP9_NAME = DBLibConstants.FALIB + "_OBJ.FAREMOVES";
   private static String SQLSP9_NAME = DBLibConstants.UPLIB + "_OBJ.FAREMOVES"; //ONLY EPN
   private static String SQLSP10_NAME = DBLibConstants.FALIB + "_OBJ.FADEPALLS";
   
   public AssetAction() 
   {
    
   }

   public void perform(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager) throws ActionException 
   {
		String message = request.getParameter("_message");
		if (message == null)
			message = "lookup";

		if (message.equals("lookup"))
			lookupMessage(request, response, flowManager, message);
		else if (message.equals("list"))
			listMessage(request, response, flowManager, message);
		else if (message.equals("new"))
			newMessage(request, response, flowManager, message);
		else if (message.equals("add"))
			addMessage(request, response, flowManager, message);
		else if (message.equals("view"))
			viewMessage(request, response, flowManager, message);
		else if (message.equals("edit"))
			editMessage(request, response, flowManager, message);
		else if (message.equals("update"))
			updateMessage(request, response, flowManager, message);
		else if (message.equals("register"))
			registerMessage(request, response, flowManager, message);
		else if (message.equals("relocpre"))
			relocpreMessage(request, response, flowManager, message);
		else if (message.equals("reloc"))
			relocMessage(request, response, flowManager, message);
		else if (message.equals("listrun"))
			listrunMessage(request, response, flowManager, message);
		else if (message.equals("runnew"))
			runnewMessage(request, response, flowManager, message);
		else if (message.equals("deprun"))
			deprunMessage(request, response, flowManager, message);
		else if (message.equals("rptpre"))
			rptpreMessage(request, response, flowManager, message);
		else if (message.equals("report"))
			printrptMessage(request, response, flowManager, message);
		else if (message.equals("pripdf"))
			pripdfMessage(request, response, flowManager, message);
		else if (message.equals("delete"))
			deleteMessage(request, response, flowManager, message);
		else if (message.equals("prtExcel"))
			prtExcelMessage(request, response, flowManager, message);
		else if (message.equals("depallpre"))
			depreciateAllPreMessage(request, response, flowManager, message);
		else if (message.equals("removepre"))
			removePreMessage(request, response, flowManager, message);
		else if (message.equals("depall"))
			depreciateAllMessage(request, response, flowManager, message);
		else if (message.equals("remove"))
			removeMessage(request, response, flowManager, message);
		else if (message.equals("repXLS")) //XLS Deprecition report
			printXLSMessage(request, response, flowManager, message);
   }
   public void lookupMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String act) throws ActionException
   {
	   Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP7_NAME);
	   setSP7Parameter(request, cmd, act);
		
	   try {
		   cmd.execute();
		   RowSet entTypeRS = cmd.getNextRowSet();
		   RowSet statusRS = cmd.getNextRowSet();
 		   RowSet classTypeRS = cmd.getNextRowSet();
		   RowSet currRS = cmd.getNextRowSet();
		   cmd.close();

		   request.setAttribute("entTypeRS", entTypeRS);
		   request.setAttribute("statusRS", statusRS);
		   request.setAttribute("classTypeRS", classTypeRS);
		   request.setAttribute("currRS", currRS);

		   flowManager.callView(VIEW_LOOKUP, request, response);
	   } catch (CommandException cmde) {cmd.close(); throw new ActionException(cmde); }
   }
   public void listMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP7_NAME);	
		setSP7Parameter(request, cmd, message);
		
		try {
			cmd.execute();
			RowSet assetRS = cmd.getNextRowSet();
			cmd.close();
			request.setAttribute("assetRS", assetRS);
			flowManager.callView(VIEW_LIST, request, response);

		} catch (CommandException cmde) {cmd.close(); throw new ActionException(cmde); }
	} 

	//view detail
	public void viewMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException
	{
		 Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request),  SQLSP1_NAME);

		 setSP1Parameter(request, cmd, message);

		 try {
			cmd.execute();
			RowSet assetViewRS = cmd.getNextRowSet();
			RowSet locationRS = cmd.getNextRowSet();
			cmd.close();

			request.setAttribute("assetViewRS", assetViewRS);
			request.setAttribute("locationRS", locationRS);

			flowManager.callView(VIEW_VIEW, request, response);

		 } catch (CommandException cmde) { cmd.close(); throw new ActionException(cmde); }

	}

   public void editMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP1_NAME);	
		setSP1Parameter(request, cmd, message);

		try {
			cmd.execute();
			RowSet assetRS = cmd.getNextRowSet();
			RowSet currencyRS = cmd.getNextRowSet();
			RowSet classTypeRS = cmd.getNextRowSet();
			RowSet depreciationRS = cmd.getNextRowSet();
			RowSet entTypeRS = cmd.getNextRowSet();
			RowSet locationRS = cmd.getNextRowSet();
			//RowSet defaultGlRS = cmd.getNextRowSet();

			cmd.close();

			request.setAttribute("assetRS", assetRS);
			request.setAttribute("currencyRS", currencyRS);
			request.setAttribute("classTypeRS", classTypeRS);
			request.setAttribute("depreciationRS", depreciationRS);
			request.setAttribute("entTypeRS", entTypeRS);
			request.setAttribute("locationRS", locationRS);
			//request.setAttribute("defaultGlRS", defaultGlRS);
			
			flowManager.callView(VIEW_EDIT, request, response);

		} catch (CommandException cmde) {cmd.close(); throw new ActionException(cmde); }
	} 
   
   public void newMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP1_NAME);
		setSP1Parameter(request, cmd, message);
	
		try {
		   cmd.execute();
		   RowSet entTypeRS = cmd.getNextRowSet();
		   RowSet currencyRS = cmd.getNextRowSet();
		   RowSet classTypeRS = cmd.getNextRowSet();
		   RowSet depreciationRS = cmd.getNextRowSet();
		   RowSet defaultGlRS = cmd.getNextRowSet();
		   cmd.close();
	
		   request.setAttribute("entTypeRS", entTypeRS);
		   request.setAttribute("currencyRS", currencyRS);
		   request.setAttribute("classTypeRS", classTypeRS);
		   request.setAttribute("depreciationRS", depreciationRS);
		   request.setAttribute("defaultGlRS", defaultGlRS);
		   		
		   flowManager.callView(VIEW_NEW, request, response);
	
		} catch (CommandException cmde) { 
			cmd.close(); 
			throw new ActionException(cmde);
		}	
   }

   public void addMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP2_NAME);
		setSP2Parameter(request, cmd, message);
	
		java.sql.Connection conn = null;

		try{
			try{
				conn = ConnectionFactory.getConnection();
				conn.setAutoCommit(false);
		
				cmd.setConnection(conn);
				cmd.execute();
				conn.commit();
				conn.setAutoCommit(true);
				cmd.close();

				listMessage(request, response, flowManager, "list");
			} 
			catch (CommandException cmde) 
			{
				conn.rollback();
				conn.setAutoCommit(true);
				cmd.close();

				if (cmde instanceof CommandErrorException) {
					ActionError error = new ActionError();
					error.setMessage(cmde.getMessage());
					error.setSeverity(ActionError.ERROR);
					this.addError(error, request);
				
					newMessage(request, response, flowManager, "new");
					
				} else
					throw new ActionException(cmde);
			}
		} catch (Exception exp) { exp.printStackTrace(); throw new ActionException(exp); }
	}

   public void updateMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP2_NAME);
		setSP2Parameter(request, cmd, message);
		
		java.sql.Connection conn = null;
	
		try{
			try{
				conn = ConnectionFactory.getConnection();
				conn.setAutoCommit(false);
			
				cmd.setConnection(conn);
				cmd.execute();
				conn.commit();
				conn.setAutoCommit(true);
				cmd.close();
	
				listMessage(request, response, flowManager, "list");
			} 
			catch (CommandException cmde) 
			{
				conn.rollback();
				conn.setAutoCommit(true);
				cmd.close();
	
				if (cmde instanceof CommandErrorException) {
					ActionError error = new ActionError();
					error.setMessage(cmde.getMessage());
					error.setSeverity(ActionError.ERROR);
					this.addError(error, request);
					
					editMessage(request, response, flowManager, "edit");
						
				} else
					throw new ActionException(cmde);
			}
		} catch (Exception exp) { exp.printStackTrace(); throw new ActionException(exp); }
	}   

	public void registerMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
	{
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP2_NAME);
		setSP2Parameter(request, cmd, message);
		java.sql.Connection conn = null;
	
		try{
			try{
				conn = ConnectionFactory.getConnection();
				conn.setAutoCommit(false);
			
				cmd.setConnection(conn);
				cmd.execute();
				conn.commit();
				conn.setAutoCommit(true);
				cmd.close();
	
				listMessage(request, response, flowManager, "list");
			} 
			catch (CommandException cmde) 
			{
				conn.rollback();
				conn.setAutoCommit(true);
				cmd.close();
	
				if (cmde instanceof CommandErrorException) {
					ActionError error = new ActionError();
					error.setMessage(cmde.getMessage());
					error.setSeverity(ActionError.ERROR);
					this.addError(error, request);
					
					editMessage(request, response, flowManager, "edit");
						
				} else
					throw new ActionException(cmde);
			}
		} catch (Exception exp) { exp.printStackTrace(); throw new ActionException(exp); }
	}   

	public void relocpreMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
	{
		 Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP6_NAME);	
		 setSP6Parameter(request, cmd, message);

		 try {
			 cmd.execute();
			 RowSet assetRS = cmd.getNextRowSet();
			 RowSet locationRS = cmd.getNextRowSet();
			 RowSet entTypeRS = cmd.getNextRowSet();

			 cmd.close();

			 request.setAttribute("assetRS", assetRS);
			 request.setAttribute("locationRS", locationRS);
			 request.setAttribute("entTypeRS", entTypeRS);
			
			 flowManager.callView(VIEW_RELOC, request, response);

		 } catch (CommandException cmde) {cmd.close(); throw new ActionException(cmde); }
	 } 
   
	public void relocMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
	{
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP6_NAME);	
		setSP6Parameter(request, cmd, message);

		try {
			cmd.execute();
			cmd.close();

			listMessage(request, response, flowManager, "list");
		} 
		catch (CommandException cmde) 
		{
			cmd.close();

			if (cmde instanceof CommandErrorException) {
				ActionError error = new ActionError();
				error.setMessage(cmde.getMessage());
				error.setSeverity(ActionError.ERROR);
				this.addError(error, request);

				relocpreMessage(request, response, flowManager, "relocpre");
					
			} else
				throw new ActionException(cmde);
			}
	 } 
   
    public void listrunMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
    {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP3_NAME);	
		setSP3Parameter(request, cmd, message);

		int pag = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
	
		try {
			cmd.execute();
			RowSet depRunRS = cmd.getNextRowSet(pag,ConstantValue.PAGE_SIZE);
			cmd.close();
	
			request.setAttribute("depRunRS", depRunRS);
			flowManager.callView(VIEW_RUNLIST, request, response);
		} catch (CommandException cmde) {cmd.close(); throw new ActionException(cmde); }
	} 
	
	public void runnewMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
	{
		 Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP3_NAME);
		 setSP3Parameter(request, cmd, message);
	
		 try {
			cmd.execute();
			RowSet lastRunRS = cmd.getNextRowSet();
			cmd.close();
	
			request.setAttribute("lastRunRS", lastRunRS);
		   		
			flowManager.callView(VIEW_RUNNEW, request, response);
	
		 } catch (CommandException cmde) { 
			 cmd.close(); 
			 throw new ActionException(cmde);
		 }	
	}

	public void deprunMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
	{
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP4_NAME);
		setSP4Parameter(request, cmd, message);
		java.sql.Connection conn = null;
		
		try{
			try{
				conn = ConnectionFactory.getConnection();
				conn.setAutoCommit(false);
				
				cmd.setConnection(conn);
				cmd.execute();
				conn.commit();
				conn.setAutoCommit(true);
				cmd.close();
		
				listrunMessage(request, response, flowManager, "listrun");
			} 
			catch (CommandException cmde) 
			{
				conn.rollback();
				conn.setAutoCommit(true);
				cmd.close();
	
				if (cmde instanceof CommandErrorException) {
					ActionError error = new ActionError();
					error.setMessage(cmde.getMessage());
					error.setSeverity(ActionError.ERROR);
					this.addError(error, request);
					
					listrunMessage(request, response, flowManager, "listrun");						
				} else
					throw new ActionException(cmde);
				}
		} catch (Exception exp) { exp.printStackTrace(); throw new ActionException(exp); }
	}   

	public void rptpreMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
	{
		 Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP3_NAME);
		 setSP3Parameter(request, cmd, "runnew");
	
		 try {
			cmd.execute();
			RowSet lastRunRS = cmd.getNextRowSet();
			cmd.close();
	
			request.setAttribute("lastRunRS", lastRunRS);
		   		
			flowManager.callView(VIEW_REPORT, request, response);
	
		 } catch (CommandException cmde) { 
			 cmd.close(); 
			 throw new ActionException(cmde);
		 }	
	}

	public void printrptMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String act) throws ActionException
	{
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP5_NAME);
		setSP5Parameter(request, cmd, act);
		String rptDate 	= request.getParameter("rptDate")==null ? "" : request.getParameter("rptDate");

		try {
			cmd.execute();
			RowSet detailRS = cmd.getNextRowSet();
			cmd.close();

			if(!detailRS.next())
				response.sendRedirect(request.getContextPath() + "/erp/ERP_COMMON/noData.jsp?errMsg=No Data was found.");
			else
			{
				//HttpSession session = request.getSession(false);
				
				DepreciationReportPDF DepreciationReportPDF = new DepreciationReportPDF();
				//DepreciationReportPDF.setData(detailRS, ((PortalUser)session.getAttribute("portal_PortalUser")).getResourceBundle(), rptDate);
				DepreciationReportPDF.setData(detailRS, this.getLoggedUserRB(request), rptDate);				
				DepreciationReportPDF.generate();
											
				response.setContentType("application/pdf");
				response.setContentLength(DepreciationReportPDF.pdfStream.size());
				ServletOutputStream out = response.getOutputStream();
				DepreciationReportPDF.pdfStream.writeTo(out);
				out.flush();
				DepreciationReportPDF = null;
			}
		} 
		catch (CommandException cmde) {
			cmd.close(); 
			if (cmde instanceof CommandErrorException) {
			   try{
				   String err = cmde.getMessage();
				   response.sendRedirect(request.getContextPath() + "/erp/ERP_COMMON/noData.jsp?errMsg=" + err);
			   }
			   catch ( IOException e ) {
					System.out.println("IOExpception occured when trying to produce a report: " + e.getMessage());
			   }
			}
			else
				throw new ActionException(cmde);
		}
		catch ( IOException e ) {
			  System.out.println("IOExpception occured when trying to produce a report: " + e.getMessage());
		}
		catch (Exception e) { 
		  System.out.println(e.toString());
		}	
	}

	public void printXLSMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String act) throws ActionException
	{
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP5_NAME);
		setSP5Parameter(request, cmd, act);
		String rptDate 	= request.getParameter("rptDate")==null ? "" : request.getParameter("rptDate");
		try {
			cmd.execute();
			RowSet detailRS = cmd.getNextRowSet();
			cmd.close();
			
			if (detailRS.next() == false)
			{
				String err = "No data available for this report.";
				response.sendRedirect(request.getContextPath() + "/erp/ERP_COMMON/noData.jsp?errMsg=" + err);
			}
			else
			{
				detailRS.beforeFirst();
				DepreciationReportXLS deprReportXLS = new DepreciationReportXLS();
				deprReportXLS.setData(detailRS,request, rptDate);
				deprReportXLS.generate();
				
				//write to output stream
				OutputStream os = response.getOutputStream();
				//response.setContentType( "application/vnd.ms-excel" );
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition","attachment;filename=AssetReport.xls");
				deprReportXLS.wb.write(os);
				os.close();
				detailRS = null;
				deprReportXLS = null;
			}
		} 
		catch (CommandException cmde) {
			cmd.close(); 
			if (cmde instanceof CommandErrorException) {
			   try{
				   String err = cmde.getMessage();
				   response.sendRedirect(request.getContextPath() + "/erp/ERP_COMMON/noData.jsp?errMsg=" + err);
			   }
			   catch ( IOException e ) {
					System.out.println("IOExpception occured when trying to produce a report: " + e.getMessage());
			   }
			}
			else
				throw new ActionException(cmde);
		}
		catch ( IOException e ) {
			  System.out.println("IOExpception occured when trying to produce a report: " + e.getMessage());
		}
		catch (Exception e) { 
		  System.out.println(e.toString());
		}	
	}
	
	public void pripdfMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String act) throws ActionException
	{
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP7_NAME);
		setSP7Parameter(request, cmd, act);
		
		String lst 		= request.getParameter("lst") ==null?"":request.getParameter("lst");
		String lfdt 	= request.getParameter("lfdt") ==null?Format.getSysDate():request.getParameter("lfdt");
		String ltdt 	= request.getParameter("ltdt") ==null?Format.getSysDate():request.getParameter("ltdt");		

		try {
			cmd.execute();
			RowSet detailRS = cmd.getNextRowSet();
			cmd.close();
			
			if(!detailRS.next())
				response.sendRedirect(request.getContextPath() + "/erp/ERP_COMMON/noData.jsp?errMsg=No Data was found.");
			else
			{
				//HttpSession session = request.getSession(false);
				AssetsReportPDF AssetsReportPDF = new AssetsReportPDF(this.getLoggedUserRB(request));
				AssetsReportPDF.setData(detailRS,lst,lfdt,ltdt);
				AssetsReportPDF.generate();
											
				response.setContentType("application/pdf");
				response.setContentLength(AssetsReportPDF.pdfStream.size());
				ServletOutputStream out = response.getOutputStream();
				AssetsReportPDF.pdfStream.writeTo(out);
				out.flush();
				AssetsReportPDF = null;
			}
		} 
		catch (CommandException cmde) {
			cmd.close(); 
			if (cmde instanceof CommandErrorException) {
			   try{
				   String err = cmde.getMessage();
				   response.sendRedirect(request.getContextPath() + "/erp/ERP_COMMON/noData.jsp?errMsg=" + err);
			   }
			   catch ( IOException e ) {
					System.out.println("IOExpception occured when trying to produce a report: " + e.getMessage());
			   }
			}
			else
				throw new ActionException(cmde);
		}
		catch ( IOException e ) {
			  System.out.println("IOExpception occured when trying to produce a report: " + e.getMessage());
		}
		catch (Exception e) { 
		  System.out.println(e.toString());
		}	
	}
	
	public void prtExcelMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String act) throws ActionException
	{
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP7_NAME);
		setSP7Parameter(request, cmd, "pripdf");
		try {
			cmd.execute();
			RowSet detailRS = cmd.getNextRowSet();
			cmd.close();
			
			if (detailRS.next() == false)
			{
				String err = "No data available for this report.";
				response.sendRedirect(request.getContextPath() + "/erp/ERP_COMMON/noData.jsp?errMsg=" + err);
			}
			else
			{
				detailRS.beforeFirst();
				AssetsReportXLS AssetsReportXLS = new AssetsReportXLS();
				AssetsReportXLS.setData(detailRS,request);
				AssetsReportXLS.generate();
				
				//write to output stream
				OutputStream os = response.getOutputStream();
				//response.setContentType( "application/vnd.ms-excel" );
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition","attachment;filename=AssetReport.xls");
				AssetsReportXLS.wb.write(os);
				os.close();
				detailRS = null;
				AssetsReportXLS = null;
			}
		} 
		catch (CommandException cmde) {
			cmd.close(); 
			if (cmde instanceof CommandErrorException) {
			   try{
				   String err = cmde.getMessage();
				   response.sendRedirect(request.getContextPath() + "/erp/ERP_COMMON/noData.jsp?errMsg=" + err);
			   }
			   catch ( IOException e ) {
					System.out.println("IOExpception occured when trying to produce a report: " + e.getMessage());
			   }
			}
			else
				throw new ActionException(cmde);
		}
		catch ( IOException e ) {
			  System.out.println("IOExpception occured when trying to produce a report: " + e.getMessage());
		}
		catch (Exception e) { 
		  System.out.println(e.toString());
		}	
	}

	public void deleteMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
	{
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP8_NAME);
		setSP8Parameter(request, cmd, message);
		java.sql.Connection conn = null;
		
		try{
			try{
				conn = ConnectionFactory.getConnection();
				conn.setAutoCommit(false);
				
				cmd.setConnection(conn);
				cmd.execute();
				conn.commit();
				conn.setAutoCommit(true);
				cmd.close();
		
				listMessage(request, response, flowManager, "list");
			} 
			catch (CommandException cmde) 
			{
				conn.rollback();
				conn.setAutoCommit(true);
				cmd.close();
	
				if (cmde instanceof CommandErrorException) {
					ActionError error = new ActionError();
					error.setMessage(cmde.getMessage());
					error.setSeverity(ActionError.ERROR);
					this.addError(error, request);
					
					listMessage(request, response, flowManager, "list");
				} else
					throw new ActionException(cmde);
				}
		} catch (Exception exp) { exp.printStackTrace(); throw new ActionException(exp); }
	}   

	private void setSP1Parameter(HttpServletRequest request, Command cmd, String act) throws ActionException
	{
		cmd.setDebug(ConstantValue.DEBUG);
		try {

			String assetId 	= request.getParameter("assetId")==null ? "" : request.getParameter("assetId");
			String search 	= request.getParameter("search")==null ? "" : request.getParameter("search");
			String filter 	= request.getParameter("filter")==null?"DR":request.getParameter("filter");
			String orderBy 	= request.getParameter("orderBy")==null?"1":request.getParameter("orderBy");
			
			cmd.addParameter(this.getCompanyId(request));
			cmd.addParameter(this.getCompanyEid(request));
			cmd.addParameter(assetId.length() != 0 ? new BigDecimal(assetId): ConstantValue.ZERO);
			cmd.addParameter(search);
			cmd.addParameter(filter);
			cmd.addParameter(orderBy);
			cmd.addParameter(act);
			cmd.addParameter(this.getLoggedUserId(request));
			cmd.addParameter(Format.getIPAddress(request));

		}
		catch (Exception e) {
			throw new ActionException(e);
		}
   }

   private void setSP2Parameter(HttpServletRequest request, Command cmd, String act) throws ActionException
   {
		cmd.setDebug(ConstantValue.DEBUG);

		try {

			String assetId 	= request.getParameter("assetId")==null ? "" : request.getParameter("assetId");
			String prodId 	= request.getParameter("prodId")==null ? "" : request.getParameter("prodId");
			String assetName= request.getParameter("assetName")==null ? "" : request.getParameter("assetName");
			String tag 		= request.getParameter("tag")==null ? "" : request.getParameter("tag");
			String classId 	= request.getParameter("classId")==null ? "" : request.getParameter("classId");
			String docRef	= request.getParameter("docRef")==null ? "" : request.getParameter("docRef");
			String price 	= request.getParameter("price")==null ? "" : request.getParameter("price");
			String currency = request.getParameter("currency")==null ? "" : request.getParameter("currency");
			String acqDate 	= request.getParameter("acqDate")==null ? "" : request.getParameter("acqDate");
			String vendId 	= request.getParameter("vendId")==null ? "" : request.getParameter("vendId");
			String serial 	= request.getParameter("serial")==null ? "" : request.getParameter("serial");
			String model 	= request.getParameter("model")==null ? "" : request.getParameter("model");
			String manuId 	= request.getParameter("manuId")==null ? "" : request.getParameter("manuId");
			String warranty = request.getParameter("warranty")==null ? "" : request.getParameter("warranty");
			String empCustId= request.getParameter("empCustId0")==null?"":request.getParameter("empCustId0");
			String empId	= request.getParameter("empId")==null?"":request.getParameter("empId");
			String asColor	= request.getParameter("asColor")==null?"":request.getParameter("asColor");

			String depMethod= request.getParameter("depMethod")==null ? "" : request.getParameter("depMethod");
			String lifeTime = request.getParameter("lifeTime")==null ? "" : request.getParameter("lifeTime");
			String salvage 	= request.getParameter("salvage")==null ? "" : request.getParameter("salvage");
			String totDepMnths 	= request.getParameter("totDepMnths")==null ? "" : request.getParameter("totDepMnths");

			String glAstAcct= request.getParameter("level0")==null ? "" : request.getParameter("level0");
			String glDepAcct= request.getParameter("level1")==null ? "" : request.getParameter("level1");
			String glExpAcct= request.getParameter("level2")==null ? "" : request.getParameter("level2");
			String glLblAcct= request.getParameter("level3")==null ? "" : request.getParameter("level3");
			String locDate	= request.getParameter("locDate")==null ? "" : request.getParameter("locDate");

			cmd.addParameter(this.getCompanyId(request));
			cmd.addParameter(this.getCompanyEid(request));
			cmd.addParameter(assetId.length() != 0 ? new BigDecimal(assetId): ConstantValue.ZERO);
			cmd.addParameter(assetName);
			cmd.addParameter(prodId.length() != 0 ? new BigDecimal(prodId): ConstantValue.ZERO);
			cmd.addParameter(tag);
			cmd.addParameter(classId.length() != 0 ? new BigDecimal(classId): ConstantValue.ZERO);
			cmd.addParameter("");
			cmd.addParameter(ConstantValue.ZERO);
			cmd.addParameter(docRef);
			cmd.addParameter(price.length() != 0 ? new BigDecimal(price): ConstantValue.ZERO);
			cmd.addParameter(currency);
			cmd.addParameter(acqDate.length() !=0? Format.strDatetoSQLDate(acqDate): Format.getBlankDate());
			cmd.addParameter(vendId.length() != 0 ? new BigDecimal(vendId): ConstantValue.ZERO);
			cmd.addParameter(serial);
			cmd.addParameter(model);
			cmd.addParameter(manuId.length() != 0 ? new BigDecimal(manuId): ConstantValue.ZERO);
			cmd.addParameter(warranty.length() !=0? Format.strDatetoSQLDate(warranty): Format.getBlankDate());
			cmd.addParameter(empCustId);
			cmd.addParameter(depMethod);
			cmd.addParameter(lifeTime.length() != 0 ? new BigDecimal(lifeTime): ConstantValue.ZERO);
			cmd.addParameter(salvage.length() != 0 ? new BigDecimal(salvage): ConstantValue.ZERO);
			cmd.addParameter(totDepMnths.length() != 0 ? new BigDecimal(totDepMnths): ConstantValue.ZERO);
			cmd.addParameter(glAstAcct);
			cmd.addParameter(glDepAcct);
			cmd.addParameter(glExpAcct);
			cmd.addParameter(glLblAcct);
			cmd.addParameter(locDate.length() !=0? Format.strDatetoSQLDate(locDate): Format.getBlankDate());
			cmd.addParameter(empId.length() != 0 ? new BigDecimal(empId): ConstantValue.ZERO);
			cmd.addParameter(asColor);
			
			cmd.addParameter(act);
			cmd.addParameter(this.getLoggedUserId(request));
			cmd.addParameter(Format.getIPAddress(request));

		}
		catch (Exception e) {
			throw new ActionException(e);
		}

   }

   private void setSP3Parameter(HttpServletRequest request, Command cmd, String act) throws ActionException
   {
	  cmd.setDebug(ConstantValue.DEBUG);

	  try {
		  cmd.addParameter(this.getCompanyId(request));
		  cmd.addParameter(act);
	  }
	  catch (Exception e) {
		  throw new ActionException(e);
	  }
   }
   private void setSP4Parameter(HttpServletRequest request, Command cmd, String act) throws ActionException
   {
	   cmd.setDebug(ConstantValue.DEBUG);

	   try {
		   String runDate 	= request.getParameter("runDate")==null ? "" : request.getParameter("runDate");

		   cmd.addParameter(this.getCompanyId(request));
		   cmd.addParameter(this.getCompanyEid(request));
		   cmd.addParameter(this.getLoggedUserEid(request));
		   cmd.addParameter(runDate.length() !=0? Format.strDatetoSQLDate(runDate): Format.getBlankDate());
		   cmd.addParameter(act);
		   cmd.addParameter(this.getLoggedUserId(request));
		   cmd.addParameter(Format.getIPAddress(request));
	   }
	   catch (Exception e) {
		   throw new ActionException(e);
	   }
    }
	private void setSP5Parameter(HttpServletRequest request, Command cmd, String act) throws ActionException
	{
		cmd.setDebug(ConstantValue.DEBUG);

		try {
			String rptDate 	= request.getParameter("rptDate")==null ? "" : request.getParameter("rptDate");

			cmd.addParameter(this.getCompanyId(request));
			cmd.addParameter(rptDate.length() !=0? Format.strDatetoSQLDate(rptDate): Format.getBlankDate());
			cmd.addParameter(this.getLoggedUserId(request));
			cmd.addParameter(Format.getIPAddress(request));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	 }
	private void setSP6Parameter(HttpServletRequest request, Command cmd, String act) throws ActionException
	{
		cmd.setDebug(ConstantValue.DEBUG);

		try {
			String locDate 	= request.getParameter("locDate")==null ? "" : request.getParameter("locDate");
			String empCustId= request.getParameter("empCustId0")==null?"":request.getParameter("empCustId0");
			String assetId 	= request.getParameter("assetId")==null ? "" : request.getParameter("assetId");
		
			cmd.addParameter(this.getCompanyId(request));
			cmd.addParameter(this.getCompanyEid(request));
			cmd.addParameter(assetId.length() != 0 ? new BigDecimal(assetId): ConstantValue.ZERO);
			cmd.addParameter(empCustId);
			cmd.addParameter(locDate.length() !=0? Format.strDatetoSQLDate(locDate): Format.getBlankDate());
			cmd.addParameter(act);
			cmd.addParameter(this.getLoggedUserId(request));
			cmd.addParameter(Format.getIPAddress(request));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	 }


   private void setSP7Parameter(HttpServletRequest request, Command cmd, String act) throws ActionException
   {
		cmd.setDebug(ConstantValue.DEBUG);
	
		try {
			String assName 	= request.getParameter("assName") == null? "" : request.getParameter("assName");
			String asTag 	= request.getParameter("asTag") == null? "" : request.getParameter("asTag");
			String entType9 	= request.getParameter("entType9") == null? "" : request.getParameter("entType9");
			String empCustId9 	= request.getParameter("empCustId9") == null? "" : request.getParameter("empCustId9");
			String lfdt 	= request.getParameter("lfdt") ==null?Format.getSysDate():request.getParameter("lfdt");
			String ltdt 	= request.getParameter("ltdt") ==null?Format.getSysDate():request.getParameter("ltdt");
			String ffdt 	= request.getParameter("ffdt") ==null?"":request.getParameter("ffdt").trim();
			String ftdt 	= request.getParameter("ftdt") ==null?"":request.getParameter("ftdt").trim();
			String vendI 	= request.getParameter("vendI") ==null?"":request.getParameter("vendI");
			String lst 		= request.getParameter("lst") ==null?"":request.getParameter("lst");
			String pag 		= request.getParameter("page") != null ? request.getParameter("page") : "1";
			String lcty 	= request.getParameter("lcty") ==null?"":request.getParameter("lcty");
			String lcid 	= request.getParameter("lcid") ==null?"":request.getParameter("lcid");
			String lemp 	= request.getParameter("lemp") ==null?"":request.getParameter("lemp");
			String presId	= request.getParameter("presId") ==null?"D":request.getParameter("presId");		
			String spaCurr	= request.getParameter("spaCurr") ==null?"":request.getParameter("spaCurr");	
			cmd.addParameter(this.getCompanyId(request));
		    cmd.addParameter(this.getCompanyEid(request));
		    cmd.addParameter(assName);
			cmd.addParameter(asTag);
			cmd.addParameter(lcty.length() != 0 ? new BigDecimal(lcty): ConstantValue.ZERO);
			cmd.addParameter(lcid.length() != 0 ? new BigDecimal(lcid): ConstantValue.ZERO);
			cmd.addParameter(lemp.length() != 0 ? new BigDecimal(lemp): ConstantValue.ZERO);
			cmd.addParameter(entType9);
			cmd.addParameter(empCustId9);
			cmd.addParameter(lfdt.length() ==0?Format.getBlankDate(): Format.strDatetoSQLDate(lfdt));
			cmd.addParameter(ltdt.length() ==0?Format.getBlankDate(): Format.strDatetoSQLDate(ltdt));
			cmd.addParameter(ffdt.length() != 0 ? new BigDecimal(ffdt): ConstantValue.ZERO);
			cmd.addParameter(ftdt.length() != 0 ? new BigDecimal(ftdt): ConstantValue.ZERO);
			cmd.addParameter(vendI.length() != 0 ? new BigDecimal(vendI): ConstantValue.ZERO);
			cmd.addParameter(lst);
			cmd.addParameter(new BigDecimal(ConstantValue.PAGE_SIZE));
			cmd.addParameter(new BigDecimal(pag));
			cmd.addParameter(presId);
			cmd.addParameter(spaCurr);
			cmd.addParameter(act);
					    
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
   }

   private void setSP8Parameter(HttpServletRequest request, Command cmd, String act) throws ActionException
   {
	   cmd.setDebug(ConstantValue.DEBUG);

	   try {
			String assetId 	= request.getParameter("assetId")==null ? "" : request.getParameter("assetId");

			cmd.addParameter(this.getCompanyId(request));
			cmd.addParameter(assetId.length() != 0 ? new BigDecimal(assetId): ConstantValue.ZERO);
	   }
	   catch (Exception e) {
		   throw new ActionException(e);
	   }
    }

   private void setSP9Parameter(HttpServletRequest request, Command cmd, String act) throws ActionException
   {
	   cmd.setDebug(ConstantValue.DEBUG);

	   try {
			String assetId 	= request.getParameter("assetId")==null ? "" : request.getParameter("assetId");
			String removeDate = request.getParameter("removeDate")==null ? "" : request.getParameter("removeDate");
			String empCustId= request.getParameter("empCustId0")==null?"":request.getParameter("empCustId0");
			String comm 	= request.getParameter("comm")==null ? "" : request.getParameter("comm");

			cmd.addParameter(this.getCompanyId(request));
			cmd.addParameter(this.getCompanyEid(request));
			cmd.addParameter(assetId.length() != 0 ? new BigDecimal(assetId): ConstantValue.ZERO);
			cmd.addParameter(removeDate.length() ==0?Format.getBlankDate(): Format.strDatetoSQLDate(removeDate));
			cmd.addParameter(empCustId);
			cmd.addParameter(comm);

			cmd.addParameter(this.getLoggedUserId(request));
			cmd.addParameter(Format.getIPAddress(request));
	   }
	   catch (Exception e) {
		   throw new ActionException(e);
	   }
    }

   private void setSP10Parameter(HttpServletRequest request, Command cmd, String act) throws ActionException
   {
	   cmd.setDebug(ConstantValue.DEBUG);

	   try {
			String assetId 	= request.getParameter("assetId")==null ? "" : request.getParameter("assetId");
			String depDate 	= request.getParameter("depDate")==null ? "" : request.getParameter("depDate");
			String incSalvAmt 	= request.getParameter("incSalvAmt")==null ? "" : request.getParameter("incSalvAmt");
			String comm 	= request.getParameter("comm")==null ? "" : request.getParameter("comm");

			cmd.addParameter(this.getCompanyId(request));
			cmd.addParameter(this.getCompanyEid(request));
			cmd.addParameter(assetId.length() != 0 ? new BigDecimal(assetId): ConstantValue.ZERO);
			cmd.addParameter(depDate.length() ==0?Format.getBlankDate(): Format.strDatetoSQLDate(depDate));
			cmd.addParameter(incSalvAmt);
			cmd.addParameter(comm);

			cmd.addParameter(this.getLoggedUserId(request));
			cmd.addParameter(Format.getIPAddress(request));
	   }
	   catch (Exception e) {
		   throw new ActionException(e);
	   }
    }

   public void depreciateAllPreMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP1_NAME);	
		setSP1Parameter(request, cmd, message);

		try {
			cmd.execute();
			RowSet assetRS = cmd.getNextRowSet();
			cmd.close();

			request.setAttribute("assetRS", assetRS);
			
			flowManager.callView(VIEW_DEPALL, request, response);

		} catch (CommandException cmde) {cmd.close(); throw new ActionException(cmde); }
	} 

   public void removePreMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
   {
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP1_NAME);	
		setSP1Parameter(request, cmd, message);

		try {
			cmd.execute();
			RowSet assetRS = cmd.getNextRowSet();
			RowSet entTypeRS = cmd.getNextRowSet();
			cmd.close();

			request.setAttribute("assetRS", assetRS);
			request.setAttribute("entTypeRS", entTypeRS);
			
			flowManager.callView(VIEW_REMOVE, request, response);

		} catch (CommandException cmde) {cmd.close(); throw new ActionException(cmde); }
	} 

	public void depreciateAllMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
	{
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP10_NAME);
		setSP10Parameter(request, cmd, message);
		java.sql.Connection conn = null;
		
		try{
			try{
				conn = ConnectionFactory.getConnection();
				conn.setAutoCommit(false);
				cmd.setConnection(conn);
				cmd.execute();
				conn.commit();
				conn.setAutoCommit(true);
				cmd.close();
		
				listMessage(request, response, flowManager, "list");
			} 
			catch (CommandException cmde) 
			{
				conn.rollback();
				conn.setAutoCommit(true);
				cmd.close();
	
				if (cmde instanceof CommandErrorException) {
					ActionError error = new ActionError();
					error.setMessage(cmde.getMessage());
					error.setSeverity(ActionError.ERROR);
					this.addError(error, request);
					
					depreciateAllPreMessage(request, response, flowManager, "depallpre");						
				} else
					throw new ActionException(cmde);
				}
		} catch (Exception exp) { exp.printStackTrace(); throw new ActionException(exp); }
	}   

	public void removeMessage(HttpServletRequest request, HttpServletResponse response, FlowManager flowManager, String message) throws ActionException 
	{
		Command cmd = new Command(this.getLoggedUserLanguage(request),this.getLoggedUserTimezone(request), SQLSP9_NAME);
		setSP9Parameter(request, cmd, message);
		java.sql.Connection conn = null;
		
		try{
			try{
				conn = ConnectionFactory.getConnection();
				conn.setAutoCommit(false);
				cmd.setConnection(conn);
				cmd.execute();
				conn.commit();
				conn.setAutoCommit(true);
				cmd.close();
		
				listMessage(request, response, flowManager, "list");
			} 
			catch (CommandException cmde) 
			{
				conn.rollback();
				conn.setAutoCommit(true);
				cmd.close();
	
				if (cmde instanceof CommandErrorException) {
					ActionError error = new ActionError();
					error.setMessage(cmde.getMessage());
					error.setSeverity(ActionError.ERROR);
					this.addError(error, request);
					
					removePreMessage(request, response, flowManager, "removepre");						
				} else
					throw new ActionException(cmde);
				}
		} catch (Exception exp) { exp.printStackTrace(); throw new ActionException(exp); }
	}   

}