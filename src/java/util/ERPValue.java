package com.cap.util;

import java.math.BigDecimal;

public class ERPValue {


	public static String getStrValue(String value) {

		if ( isEmpty(value) ) {
			return "";
		}

		//return Format.encodeString(value.trim());
		return value.trim();

	}

	public static String getStrValue(String value, int maxLength) {

		String tem = getStrValue(value);

		if ( tem.length() > maxLength ) {
			tem = tem.substring(0, maxLength);
		}

		return tem;
	}

	public static String getDecValue(String value) {
		if ( isEmpty(value)) {
			return "0";
		}

		try {
			Double.parseDouble(value);
		}catch ( NumberFormatException e ) {
			return "0";
		}

		return value.trim();
	}

	public static String getBitValue(String value) {
			if ( isEmpty(value) ) {
				return "";
			}

			return value.trim();
	}

	public static String getStrWithDefaultVal(String value, String defaultVal) {
		if ( isEmpty(value) ) {
			return defaultVal.trim();
		}

		//return Format.encodeString(value.trim());
		return value.trim();
	}

	public static boolean isEmpty(String value) {
		if ( value == null || (value.trim()).length() == 0 ) {
			return true;
		}

		return false;
	}

	public static String getDecStrValue(BigDecimal value) {
		if ( value == null ) {
			return "";
		}

		return getDecValue(value.toString());
	}

	public static float getDecValue(BigDecimal value) {
		if ( value == null ) {
			return 0f;
		}

		return value.floatValue();
	}

	public static String getDBStr(String value) {
		if ( isEmpty(value) ) {
			return "";
		}

		//return Format.decodeHTML(value.trim());
		return value.trim();
	}

	public static String getDBDate(String date) {
		if ( isEmpty(date) ) {
			return "";
		}

		return Format.getFormatedDateStr(date);
	}
	public static String getDBDate(java.sql.Date date) {
		if ( date.equals(null) ) {
			return "";
		}
		else
			return Format.getFormatedDateStr(date.toString());
	}

    public static double getDoubleValue(BigDecimal value) {
		if ( value == null ) {
			return 0;
		}

		return value.doubleValue();
	}

	public static int getIntValue(BigDecimal value) {
		if ( value == null ) {
			return 0;
		}

		return value.intValue();
	}

}