package com.cap.util.label;
/*
 * Created on Aug 12, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author Junping Zhang
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PackInfo {

	private String orderNo;			//for "SO"
	private String custPO;			//for "SO"
	private String jobName;			//for "SO"
	private String packNo;			//for "SO" or "TO" or "OI"
	private String markCarton;		//for "SO" or "TO" or "OI"
	private String docType;    		// should be "SO" (sales order or "TO" (transfer)or "OI" (other issue)
	private String documentType;  	//for "TO" or "OI"
	private String docId;			//for "TO" or "OI"
	private String info;			

	/**
	 * 
	 */
	public PackInfo(String doctype) {
		docType = doctype;
	}

	/**
	 * @return
	 */
	public String getCustPO() {
		return custPO;
	}

	/**
	 * @return
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @return
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @return
	 */
	public String getPackNo() {
		return packNo;
	}

	/**
	 * @param string
	 */
	public void setPackNo(String string) {
		packNo = string ==null? "" : string;
	}

	/**
	 * @param string
	 */
	public void setCustPO(String string) {
		custPO = string==null? "":string;
	}

	/**
	 * @param string
	 */
	public void setJobName(String string) {
		jobName = string==null? "":string;
	}

	/**
	 * @param string
	 */
	public void setOrderNo(String string) {
		orderNo = string==null? "" : string;
	}


	/**
	 * @return
	 */
	public String getDocId() {
		return docId;
	}

	/**
	 * @return
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @return
	 */
	public String getMarkCarton() {
		return markCarton;
	}

	/**
	 * @param string
	 */
	public void setDocumentType(String string) {
		documentType = string==null? "" : string;
	}

	/**
	 * @param string
	 */
	public void setDocId(String string) {
		docId = string==null? "" : string;
	}

	/**
	 * @param string
	 */
	public void setMarkCarton(String string) {
		markCarton = string==null? "" : string;
	}

	public String getPackInfoAsLabel() {
		//Sales order
		if (docType.equals("SO")){
			info =  "Order#      : " + orderNo + "\n" +
					"Cust PO#    : " + custPO + "\n" +
					"Job Name    : " + jobName + "\n" +
					"Package#    : " + packNo + "\n" +
					"Mark Carton : " + markCarton;
		}
		//Transfer
		if (docType.equals("TO")){
			info =  "Doc Type :    " + documentType + "\n" +
					"Doc ID   :    " + docId + "\n" +
					"Package# :    " + packNo + "\n" +
					"Mark Carton : " + markCarton;
		}
		// Goods Issue
		if (docType.equals("OI")){
			info =  "Doc Type :    " + documentType + "\n" +
					"Doc Ref# :    " + docId + "\n" +
					"Package# :    " + packNo + "\n" +
					"Mark Carton : " + markCarton;
		}		
		
		return info;
		
	}

}
