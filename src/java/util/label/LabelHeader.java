package com.cap.util.label;
/*
 * Created on Dec 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import java.net.URL;


public class LabelHeader {

	private URL logoURL;
	private String companyName;
	private Address companyAddress;



/*	public java.net.URL getLogoURL() {
		return logoURL;
	}
*/
public void setLogoURL(URL logoURL){
			this.logoURL = logoURL;
	}

public void setCompanyName(String companyName){
		if(companyName == null)
			this.companyName = "";
		else
			this.companyName = companyName;
	}

public void setCompanyAddress(Address companyAddress){
		this.companyAddress = companyAddress;
	}

public URL getLogoURL(){
		return logoURL ;
	}
public String getCompanyName (){
		return companyName;
	}
public Address getCompanyAddress(){
		return companyAddress;
	}
}
