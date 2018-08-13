package com.cap.util.label;
/**
 * @author Junping Zhang
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Address {

	private String address1 = "";
	private String address2 = "";
	private String address3 = "";
	private String city = "";
	private String county= "";
	private String state = "";
	private String province = "";
	private String zip ="";
	private String country = "";
	private String phone = "";
	private boolean printCountry = true ; 
	
	public void setAddress1(String address1){
		if (address1 == null)
			this.address1 = "";
		else
			this.address1 = address1;
	}
	public void setAddress2(String address2){
		if (address2 == null)
			this.address2 ="";
		else	
			this.address2 = address2;
	}
	public void setAddress3(String address3){
		if (address3 == null)
			this.address3 = "";
		else
			this.address3 = address3;
	}
	public void setCity(String city){
		if (city == null)
			this.city = "";
		else
			this.city = city;
	}
	public void setState(String state){
		if (state == null)
			this.state = "";
		else
			this.state = state;
	}
	public void setProvince(String province){
		if (province == null)
			this.province = "";
		else
			this.province = province;
	}
	public void setCountry(String country){
		if (address3 == null)
			this.country = "";
		else
			this.country = country;
	}
	public void setPhone(String phone){
		if (phone == null)
			this.phone = "";
		else
			this.phone = phone;
	}
	public void setZip(String zip){
		if (zip == null)
			this.zip = "";
		else
			this.zip = zip;
	}

	public void setPrintCountry(boolean printCountry){
		this.printCountry = printCountry;
	}

	public String getAddress1(){
		return address1;
	}
	public String getAddress2(){
		return address2;
	}
	public String getAddress3(){
		return address3;
	}
	public String getCity(){
		return city;
	}
	public String getCounty(){
		return county;
	}
	public String getState(){
		return state;
	}
	public String getProvince(){
		return province;
	}
	public String getCountry(){
		return country;
	}
	public String getPhone(){
		return phone;
	}
	public String getZip(){
		return zip;
	}
	boolean getPrintCountry(){
		return printCountry; 
	}
	
	public String getAddrAsLabel(){

		StringBuffer addr_buffer = new StringBuffer(address1.trim() + "\n");

		if (address2.trim().length() > 0) {
			addr_buffer.append(address2.trim() + "\n");
		}
		if (address3.trim().length() > 0) {
			addr_buffer.append(address3.trim() + "\n");
		}                
		addr_buffer.append(city.trim() + ", ");
		if (state.trim().length() > 0)
			addr_buffer.append(state.trim() + " " + zip.trim() + "\n");
		else
			addr_buffer.append(province.trim()  + " " + zip.trim() + "\n");
		if (printCountry) {
			addr_buffer.append(country + "\n");
		}
			addr_buffer.append(phone.trim()); 
			addr_buffer.append("\n");
	return addr_buffer.toString();
	}		
}
