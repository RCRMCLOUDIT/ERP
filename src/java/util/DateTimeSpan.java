// Class: DateTimeSpan
// Desciption: Utilities to convert, compare, calculate and display date
// History:	WLIU - created at 3/26/2002

package com.cap.util;

import java.util.*;
import java.text.*;

public class DateTimeSpan
{
	// Date attributes
	protected int m_nYear;
	protected int m_nMonth;
	protected int m_nDay;
	protected Calendar m_calDate = null;
	protected Date m_dDate = null;
	protected DateFormat m_fmt = null;
	private Locale m_locale = Locale.getDefault();
	private int m_style = DateFormat.FULL;

	// constructors
	public DateTimeSpan()
	{
		m_nYear = 1900;
		//int m_nMonth = 1;
		m_nDay = 1;
		m_calDate = null;
		m_dDate = null;
		m_fmt = null;
		m_locale = Locale.US;
		m_style = DateFormat.FULL;
	}
	public DateTimeSpan(int nMonth, int nDay, int nYear)
	{
		m_nMonth = nMonth;
		m_nDay = nDay;
		m_nYear = nYear;
		if (m_calDate != null) m_calDate.clear();
		m_calDate = new GregorianCalendar(m_nYear, m_nMonth -1, m_nDay);
		m_fmt = DateFormat.getDateInstance(m_style, m_locale);
		m_dDate = m_calDate.getTime();
	}
	public DateTimeSpan(String strDate)
	{
		StringTokenizer st = new StringTokenizer(strDate.trim(), "/", false);
		m_nMonth = Integer.valueOf(st.nextToken()).intValue();
		m_nDay = Integer.valueOf(st.nextToken()).intValue();
		m_nYear = Integer.valueOf(st.nextToken()).intValue();
		if (m_calDate != null) m_calDate.clear();
		m_calDate = new GregorianCalendar(m_nYear, m_nMonth -1, m_nDay);
		m_fmt = DateFormat.getDateInstance(m_style, m_locale);
		m_dDate = m_calDate.getTime();
	}
	public DateTimeSpan(Calendar cdate)
	{
		m_nMonth = cdate.get(Calendar.MONTH) + 1;
		m_nDay = cdate.get(Calendar.DATE);
		m_nYear = cdate.get(Calendar.YEAR);
		if (m_calDate != null) m_calDate.clear();
		m_calDate = new GregorianCalendar(m_nYear, m_nMonth -1, m_nDay);
		m_fmt = DateFormat.getDateInstance(m_style, m_locale);
		m_dDate = m_calDate.getTime();
	}

	// Attributes R/W
	public int getMonth()
	{
		return m_nMonth;
	}
	public int getDay()
	{
		return m_nDay;
	}
	public int getYear()
	{
		return m_nYear;
	}
	public Date getDate()
	{
		return m_dDate;
	}

	public void setYear(int nYear)
	{
		m_nYear = nYear;
	}
	public void setDay(int nDay)
	{
		m_nDay = nDay;
	}
	public void setMonth(int nMonth)
	{
		m_nMonth = nMonth;
	}
	public Calendar getCalendar()
	{
		return m_calDate;
	}
	public void setDateFormat(int style, Locale lc)
	{
		m_locale = lc;
		m_style = style;
		m_fmt = DateFormat.getDateInstance(m_style, m_locale);
	}

	// operations
	// 0 - equal, 1 - greater than, -1 - less than
	public int compare(DateTimeSpan ts)
	{
		if (m_nYear > ts.getYear())
			return 1;
		else if (m_nYear < ts.getYear())
			return -1;
		if (m_nMonth > ts.getMonth())
			return 1;
		else if (m_nMonth < ts.getMonth())
			return -1;
		if (m_nDay > ts.getDay())
			return 1;
		else if (m_nDay < ts.getDay())
			return -1;

		return 0;
	}
	public DateTimeSpan add(String flag, int nM)
	{
		int nMonth = 1;
		int nDay = 1;
		int nYear = 1900;

		if (flag.equals("M"))
		{
			nDay = this.m_nDay;
			nYear = this.m_nYear;
			nMonth = this.m_nMonth + nM;
			if (nMonth > 12)
			{
				nMonth %= 12;
				nYear += 1;
			}

			if (nDay > 28 && (nMonth == 2 && nYear %4 == 0))
			{
				nDay = 29;
			}
			if (nDay > 28 && (nMonth == 2 && nYear %4 != 0))
			{
				nDay = 28;
			}
			if (nDay > 30 && (nMonth == 4 || nMonth == 6 || nMonth == 9 || nMonth == 11))
			{
				nDay = 30;
			}

		}

		if (flag.equals("D"))
		{
			nDay = this.m_nDay + nM;
			nYear = this.m_nYear;
			nMonth = this.m_nMonth;

			if (nDay > 29 && (nMonth == 2 && nYear %4 == 0))
			{
				nMonth = nMonth + 1;
				nDay = nDay - 29;
			}
			else if (nDay > 28 && (nMonth == 2 && nYear %4 != 0))
			{
				nMonth = nMonth + 1;
				nDay = nDay - 28;
			}
			else if (nDay > 30 && (nMonth == 4 || nMonth == 6 || nMonth == 9 || nMonth == 11))
			{
				nMonth = nMonth + 1;
				nDay = nDay - 30;
			}
			else
			{
				nMonth = nMonth + 1;
				nDay = nDay - 31;
			}

			if (nMonth > 12)
			{
				nMonth %= 12;
				nYear += 1;
			}

		}

		DateTimeSpan ts = new DateTimeSpan(nMonth, nDay, nYear);

		return ts;
	}
	public String displayDate()
	{
		return m_fmt.format(m_dDate);
	}
	public String toSlashSepDate()
	{
		String relVal = null;
		String strMon = null;
		String strDay = null;

		int mon = getMonth();
		int day = getDay();

		if (mon < 10) strMon = "0" + mon;
		else strMon = "" + mon;

		if (day < 10) strDay = "0" + day;
		else strDay = "" + day;

		relVal = strMon + "/" + strDay + "/" + getYear();

		return relVal;

	}

	public static String getTimeStr()
	{
		Calendar cc = new java.util.GregorianCalendar(Locale.getDefault());
		int hh = cc.get(Calendar.HOUR);
		int mm = cc.get(Calendar.MINUTE);
		//int ss = cc.get(Calendar.SECOND);
		String am = "";
		String hhs = hh == 0?"12":(hh < 10 ? "0"+ hh : "" + hh);
		String mms = mm < 10 ? "0"+ mm : "" + mm;
		//String sss = ss < 10 ? "0"+ ss : "" + ss;
		if (cc.get(Calendar.AM_PM) == Calendar.AM) am = "AM";
		else am = "PM";

		return "" + hhs + ":" + mms + am;

	}

}