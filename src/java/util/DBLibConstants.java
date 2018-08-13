package com.cap.util;

public class DBLibConstants
{

	public static final String FILIB;
	//public static final String BDLIB;
	public static final String UNLIB;
	public static final String IMLIB;
	public static final String POLIB;
	public static final String MFLIB;
	public static final String WDLIB;
	public static final String FALIB;
	public static final String PRLIB;
	public static final String UPLIB;
	public static final String EXLIB;
	public static final String PTLIB;	//Port
	
	public static final String DATASOURCE;

	static
	{
		FILIB = "CAPFI";
		//BDLIB = "CAPBD";
		UNLIB = "CAPUN";
		IMLIB = "CAPIM";
		POLIB = "CAPPORTAL";
		MFLIB = "CAPMF";
		WDLIB = "CAPWD";
		FALIB = "CAPFA";
		PRLIB = "CAPPR";
		UPLIB = "CAPUP";
		EXLIB = "CAPEX";
		PTLIB = "CAPPO";
		//DATASOURCE = "jdbc/capnet";
                DATASOURCE = "com.mysql.jdbc.Driver";

	}

}
