package com.cap.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ERPRequest {

	private HttpServletRequest  request;

	public ERPRequest( HttpServletRequest request ) {
		this.request = request;
	}

	public String getParameter( String param ) {
		return ERPValue.getStrValue(request.getParameter(param));
	}

	public String getParameter( String param, String defaultVal ) {
		return ERPValue.getStrWithDefaultVal(request.getParameter(param), defaultVal);
	}

	public String getAttribute( String name ) {
		return ERPValue.getStrValue((String)request.getAttribute(name));
	}

	public HttpSession getSession(boolean create) {
		return request.getSession(create);
	}

	public HttpSession getSession() {
		return request.getSession();
	}

    public String getDate( String param, String defaultValue ) {
		String date = Format.convertDate(request.getParameter(param));
		if ( date.length() == 0 && defaultValue != null ) {
			date = defaultValue;
		}

		return date; 
	}

	public String getDate( String param ) {
		return getDate(param, null); 
	}

	public HttpServletRequest getHttpRequest() {
		return request;
	}
}