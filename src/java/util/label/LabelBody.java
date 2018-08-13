package com.cap.util.label;
/*
 * Created on Dec 16, 2004
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
public class LabelBody {

	private String attention;
	private String toCompanyName;
	private Address toAddress;
	private PackInfo packInfo;
	private int count;
	private int index;


	public void setAttention(String attention){
		if(attention == null)
			this.attention = "";
		else
			this.attention = attention;
	}
	public void setToCompanyName(String toCompanyName){
		if(toCompanyName == null)
			this.toCompanyName = "";
		else		
			this.toCompanyName = toCompanyName;
	}
	public void setToAddress(Address toAddress){
		this.toAddress = toAddress;
	}

	public String getAttention(){
		return attention ;
	}
	public String getToCompanyName (){
		return toCompanyName;
	}
	public Address getToAddress(){
		return toAddress;
	}
	/**
	 * @return
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param i
	 */
	public void setCount(int i) {
		count = i;
	}

	/**
	 * @param i
	 */
	public void setIndex(int i) {
		index = i;
	}

	/**
	 * @return
	 */
	public PackInfo getPackInfo() {
		return packInfo;
	}

	/**
	 * @param info
	 */
	public void setPackInfo(PackInfo info) {
		packInfo = info;
	}
	
}
