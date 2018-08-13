package com.cap.util;

import java.util.*;
import java.sql.Timestamp;
import java.text.*;
import java.math.BigDecimal;
//import java.net.*;

import javax.servlet.http.HttpServletRequest;

public class Format extends Object
{

/**
 * Formator constructor comment.
 */
public Format()
{
    super();
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=String format = "007"
 * output type=int format = 7
 */
public static String contactIDInt_Str(int id)
{
    if(id < 10)
        return "00" + id;
    else if(id < 100)
        return "0" + id;

    else return "" + id;
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=String format = "007"
 * output type=int format = 7
 */
public static int contactIDStr_Int(String id)
{
    Integer tmp = new Integer(id);
    return tmp.intValue();
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=BigDecimal format = 20010206 (yyyymmdd)
 * output type = String format = 02/06/2001 (mm/dd/yyyy)
 */
public static String dateBigDec_Str(java.math.BigDecimal date)
{
    if(date != null)
    {
        String s = date.toString();
        if(s.length()<8)
            return "";
        else
            return s.substring(4,6) + "/" + s.substring(6,8) + "/" + s.substring(0,4);
    }
    else
        return "";
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=String format = 01/02/2001 (mm/dd/yyyy)
 * output type = String format = 20010102 (yyyymmdd)
 */
public static String dateStr_NumStr(String date)
{
    if(date != null)
    {
        if(date.length() ==10)
            return date.substring(6,10) + date.substring(0,2) + date.substring(3,5);
        else if(date.length() ==9)
            return date.substring(5,9) + "0" + date.substring(0,1) + date.substring(2,4);
		else if(date.length() ==8)
			return date;
        else return "0";
    }
    else
        return "0";
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param des java.lang.String
 * remove "\n" from description
 *
 */
public static String formatDescription(String des)
{
//  System.out.println("before=" + des);
//  System.out.println("after" + des.replace('\n',' ').replace('\r',' '));
    return des.replace('\n', ' ').replace('\r',' ');
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=float format = 12.45 (HH.MM)
 * output type = String format = 12:45 (HH:MM)
 */
public static String formatFloat_Str(float num)
{
    java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("##.##");
    myFormatter.setMinimumFractionDigits(2);
    String tmp = myFormatter.format(num);

    return tmp.replace('.', ':');
}

/**
 * format ID with fix length, eg. len = 5
 * input = "23",    output = "00023"
 * Creation date: (5/24/2001 3:26:32 PM)
 * @return java.lang.String
 * @param id java.lang.String
 * @param len int
 */
public static String formatIDWithFixLength(String id, int len)
{
    String s="";

    if(id == null || id.length() > len || id.length() ==0)
    {
        for(int i=0; i<len; i++)
            s += "0";
        return s;
    }
    else if(id.length() == len)
        return id;

    else
    {
        for(int i=0; i<len-id.length(); i++)
            s+= "0";
        return s + id;
    }

}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type= String format = 1234.567
 * output type = String format = 1234.57
 */
public static String formatNum_Str(String num)
{
    if(num == null || num.length() ==0)
        return "";
    else
    {
        Double tmp = new Double(num);
        java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("#,###.##");
        myFormatter.setMinimumFractionDigits(2);
        return myFormatter.format(tmp.doubleValue());
    }

}

/**
 * format text with fix length. eg. len=7
 * input ="ab",     output="ab     "
 * Creation date: (5/24/2001 3:27:55 PM)
 * @return java.lang.String
 * @param text java.lang.String
 * @param len int
 */
public static String formatTextWithFixLength(String text, int len)
{
    String s="";

    if(text == null || text.length() > len || text.length() ==0)
    {
        for(int i=0; i<len; i++)
            s += " ";
        return s;
    }
    else if(text.length() == len)
        return text;

    else
    {
        for(int i=0; i<len-text.length(); i++)
            s+= " ";
        return text + s;
    }


}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * output date format="mm/dd/yyyy"
 */
public static String getSysDate()
 {
  try{
        Calendar cal = Calendar.getInstance();
    Date today = cal.getTime();
    String now = today.toString(); //dow mon dd hh:mm:ss zzz yyyy

    String month= now.substring(4, 7);
    String date = now.substring(8, 10);
    String year = now.substring(24, 28);

    if(month.equals("Jan"))
        month ="01";
    else if(month.equals("Feb"))
        month ="02";
    else if(month.equals("Mar"))
        month ="03";
    else if(month.equals("Apr"))
        month ="04";
    else if(month.equals("May"))
        month ="05";
    else if(month.equals("Jun"))
        month ="06";
    else if(month.equals("Jul"))
        month ="07";
    else if(month.equals("Aug"))
        month ="08";
    else if(month.equals("Sep"))
        month ="09";
    else if(month.equals("Oct"))
        month ="10";
    else if(month.equals("Nov"))
        month ="11";
    else
        month ="12";

    return month + "/" + date + "/" + year;
  } catch(Exception e)
  {
    System.out.println("catch exception in get system date");
  }
  return "";
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal

 * output type=String format = yyyymmdd
 */
public static String getSysDate_NumStr()
 {
  try{
        Calendar cal = Calendar.getInstance();
    Date today = cal.getTime();
    String now = today.toString(); //dow mon dd hh:mm:ss zzz yyyy

    String month= now.substring(4, 7);
    String date = now.substring(8, 10);
    String year = now.substring(24, 28);

    if(month.equals("Jan"))
        month ="01";
    else if(month.equals("Feb"))
        month ="02";
    else if(month.equals("Mar"))
        month ="03";
    else if(month.equals("Apr"))
        month ="04";
    else if(month.equals("May"))
        month ="05";
    else if(month.equals("Jun"))
        month ="06";
    else if(month.equals("Jul"))
        month ="07";
    else if(month.equals("Aug"))
        month ="08";
    else if(month.equals("Sep"))
        month ="09";
    else if(month.equals("Oct"))
        month ="10";
    else if(month.equals("Nov"))
        month ="11";
    else
        month ="12";

    return year + month + date;
  } catch(Exception e)
  {
    System.out.println("catch exception in get system date");
  }
  return "";
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=String format = "007"
 * output type=int format = 7
 */
public static String getSysTime()
{
    Calendar cal = Calendar.getInstance();
    Date today = cal.getTime();
    String now = today.toString(); //dow mon dd hh:mm:ss zzz yyyy

    String hour= now.substring(11, 13);
    String minute = now.substring(14, 16);
    String second = now.substring(17, 19);

    return hour + ":" + minute + ":" + second;
}

public static String getSysTime(int tmzone)
{
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.HOUR_OF_DAY, tmzone);
    Date today = cal.getTime();
    
    String now = today.toString(); //dow mon dd hh:mm:ss zzz yyyy

    String hour= now.substring(11, 13);
    String minute = now.substring(14, 16);
    String second = now.substring(17, 19);

    return hour + ":" + minute + ":" + second;
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=String format = "007"
 * output type=int format = 7
 */
public static String getSysTime_NumStr()
{
    Calendar cal = Calendar.getInstance();
    Date today = cal.getTime();
    String now = today.toString(); //dow mon dd hh:mm:ss zzz yyyy

    String hour= now.substring(11, 13);
    String minute = now.substring(14, 16);
    String second = now.substring(17, 19);

    return hour + minute + second;
}



/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param
 * input
 * output java.lang.String = hhmmss
 */
public static String getUniqueID()
{
   String now=null;

  try
  {
    Calendar cal = Calendar.getInstance();
    Date today = cal.getTime();
    now = today.toString(); //dow mon dd hh:mm:ss zzz yyyy

  } catch(Exception e)
  {
    System.out.println("catch exception in get system time");
  }

    return now.substring(11, 13) + now.substring(14, 16) + now.substring(17, 19);
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=BigDecimal format = 20010206 (yyyymmdd)
 * output type = String format = 02/06/2001 (mm/dd/yyyy)
 */
public static String lineNumber_Str(String num)
{
    Integer tmp = new Integer(num);
    int i = tmp.intValue();
    if(i < 10)
        return "0" +i;

    else
        return "" + i;
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=BigDecimal format = 82055 (hmmss)
 * output type = String format = 08:20:55 (hh:mm:ss)
 */
public static String timeBigDec_Str(java.math.BigDecimal time)
{
    if(time != null)
    {
        String s = time.toString();

        if(s.length() == 5) // format = hmmss
        {
            return "0" + s.substring(0,1) + ":" + s.substring(1,3) + ":" + s.substring(3,5);
        }
        else if(s.length() ==6)
        {
            return s.substring(0,2) + ":" + s.substring(2,4) + ":" + s.substring(4,6);
        }
        else
            return "";
    }
    else
        return "";
}



/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=BigDecimal format = 9.15 (HH.MM)
 * output type = String format = 09:15 (HH:MM)
 */
public static String timeBigDecFloat_Str(java.math.BigDecimal time)
{
    if(time != null)
    {
        String s = time.toString();
        s = s.replace('.', ':');

        if(s.length()==4)
            return "0" + s;
        else
            return s;
    }
    else
        return "";
}



/**
 * format the number string, decimal point not included in length.
   if integer = true
 *      eg. len=7 fraction =2
 *      input = "20",   output="0002000"
 *      input = "20.5", output="0002050"
 *      input = "12.346", output="0001235"
 * else
 *      input = "20",   output="20.00"
 *      input = "20.5", output="20.50"
 *      input = "12.346", output="12.35"
 *
 * Creation date: (5/24/2001 3:30:56 PM)
 * @return java.lang.String
 * @param num java.lang.String
 * @param len int
 * @param integer boolean
 */
public static String formatFloatWithFixLength(String num, int len, int fraction, boolean integer)
{
    String formatStr = "";
    String tmp = "";

    if(num !=null && num.length()>0)
    {
        for(int i=0; i<len-fraction; i++)
        {
            formatStr += "#";
        }
        formatStr += ".";

        for(int j=0; j< fraction; j++)
        {
            formatStr += "#";
        }

        java.text.DecimalFormat myFormatter = new java.text.DecimalFormat(formatStr);
        myFormatter.setMinimumFractionDigits(fraction);
        String s = myFormatter.format(Double.valueOf(num).doubleValue());

        if(integer)
        {
            s = s.substring(0,s.indexOf(".")) + s.substring(s.indexOf(".")+1, s.length());

            for(int k=0; k<len-s.length(); k++)
                tmp += "0";
            return tmp + s;
        }
        else
            return s;
    }
    else
    {
        if(integer)
        {   for(int i=0; i< len; i++)
                tmp += "0";
            return tmp;
        }
        else
            return "0";
    }

}

public static String formatFloatWithFixLength2(String num, int len, int fraction, boolean integer)
{
    String formatStr = "";
    String tmp = "";

    if(num !=null && num.length()>0)
    {
        for(int i=0; i<len-fraction; i++)
        {
            formatStr += "#";
        }
        formatStr += ".";

        for(int j=0; j< fraction; j++)
        {
            formatStr += "#";
        }

        java.text.DecimalFormat myFormatter = new java.text.DecimalFormat(formatStr);
        myFormatter.setMinimumFractionDigits(fraction);
        String s = myFormatter.format(Double.valueOf(num).doubleValue());

        if(integer)
        {
            s = s.substring(0,s.indexOf(".")) + s.substring(s.indexOf(".")+1, s.length());

            for(int k=0; k<len-s.length(); k++)
                tmp += "0";
            return tmp + s;
        }
        else
            for(int k=0; k<len-s.length(); k++)
                tmp += "0";
            return tmp + s;
    }
    else
    {
        if(integer)
        {   for(int i=0; i< len; i++)
                tmp += "0";
            return tmp;
        }
        else
            return "0";
    }

}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=String format = 09:15 (HH.MM)
 * output type = float format = 9.25 H
 */
public static float timeStr_Float(String time)
{
    if(time != null && time.length() == 5)
    {
        if(time.equals("00:00"))
        {
            return 0f;
        }
        else
        {
            float p1 = (Float.valueOf(time.substring(0,2))).floatValue();
            float p2 = (Float.valueOf(time.substring(3,5))).floatValue()/60;
            p1 = p1 + p2;
            return Float.valueOf(Format.formatFloatWithFixLength(("" +p1), 4,2,false)).floatValue();
        }
    }
    else
        return 0f;
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type = String format = 09:15 (HH.MM)
 * output type = int format = 9*60 + 15 (MM)
 */
public static int timeStr_intMin(String time)
{
    if(time == null || time.length() != 5 )
    {
        return 0;
    }
    else
    {
        if(time.equals("00:00"))
            return 0;
        else
        {
            int h = new Integer(time.substring(0, time.indexOf(':'))).intValue();
            int m = new Integer(time.substring(time.indexOf(':')+1, time.length())).intValue();
            return h*60 + m;
        }
    }

}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=String format = 09:15 (HH.MM)
 * output type = String format = 09.15 (HH:MM)
 */
public static String timeStr_StrFloat(String time)
{
    if(time != null)
    {
        time = time.replace(':', '.');
        return time;
    }
    else
        return "0";
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=String format = 09:15 (HH.MM)
 * output type = String format = 0915 (HH:MM)
 */
public static String timeStr_StrInt(String time)
{
    if(time != null)
    {
        time = time.substring(0, time.indexOf(':')) + time.substring(time.indexOf(':')+1, time.length());
        return time;
    }
    else
        return "0";
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=BigDecimal format = 17.30 (HH.MM)
 * output type = float format = 17.5
 */
public static float timeBigDecFloat_Float(java.math.BigDecimal time)
{
    if(time != null)
    {
        float Time = time.floatValue();

        int Hour = (int) Time;

        float Minute = Time - Hour;

/*      int hour = time.intValue(); //get 17
        float minute = (time.floatValue() - hour) *100/60; //get 0.5
*/
        return Hour + Minute*100/60;

    }
    else
        return 0;
}
/**
 * @return float
 * @param  float
 * input type=float format = 17.30 (HH.MM)
 * output type = float format = 17.5 (H)
 */
public static float timeFloat_Float(float time)
{
    int Hour = (int) time;
    float Minute = time - Hour;
    return Hour + Minute*100/60;
}
/**
 * @return float
 * @param  float
 * input type=float format = 17.5 (H)
 * output type = float format = 17.30 (HH.MM)
 */
public static float timeFloatH_FloatM(float time)
{
    int Hour = (int) time;
    float Minute = (time - Hour)*60;
    if(Minute > 59)
    {
	    if(Minute < 60)
	      Minute = 0;
	    else
		  Minute = Minute - 60;
		Hour++;
	}
    return Hour + Minute/100;
}
/**
 * Insert the method's description here.
 * Creation date: (7/5/2001 5:49:31 PM)
 * @return java.lang.String
 * @param Time float
 * input type=Float format = 09.15 (HH.MM)
 * output type = String format = 0915 (HH:MM)
 */
 public static String timeFloat_Float_Str(float Time)
 {
    String tmp = "";
    int Hour = (int) Time;
    float Minute = (Time - Hour ) *60;
    int minute = (int) java.lang.Math.rint( (float)Minute );
    if (minute == 60 )
    {
        minute = 0;
        Hour = Hour + 1;
        tmp = Hour + ":" + "00";
    } else if (minute == 0 ){
        tmp = Hour + ":" + "00";
    } else {
        tmp = Hour + ":" + minute;
    }

    return tmp;
  }

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type= float format = 17.5
 * output type = String format = 17:30 (HH:MM)
 */
public static String timeFloat_Str(float num)
{

    /*
    String tmp = "";
    int hour = (int)java.lang.Math.rint(num); //get 17

    int minute = (int) java.lang.Math.rint( (num - (float)hour) * 60 );
    if(minute <0)
    {
        hour -= 1;
        minute *=-1;
    }

    tmp += hour + ":";

    tmp += minute>=10?"" +minute:("0" + minute);

    return tmp;
	*/

	//This method implementation is modified by Tim Jian on Feb. 24, 2003.
	//The reason it needs be modified is that the implementation above only handled positive
	//number. It may get wrong return value if we pass in a minus float number.
	StringBuffer tmp = new StringBuffer();
	String preFix = "";
	if ( num < 0 ) {
		preFix ="-";
	}
	num = Math.abs(num);
	int hour = (int)(num);
	int minute = Math.round((num - (float)hour) * 60);
	if(minute == 60) {
		++hour;
		minute = 0;
	}

    tmp.append(hour>=10?hour + ":":"0"+hour+":").append(minute>=10?"" +minute:("0" + minute));

    return preFix + tmp.toString();
}
/**
 * Insert the method's description here.
 * Creation date: (4/4/2003 11:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type= float format = 17.5
 * output type = String format = 17:30 (HH:MM) using a top scale to put prescition
 */
public static String timeFloat_Str(float num,int scale)
{

	StringBuffer tmp = new StringBuffer();

	int hour = (int)num;
	int minute = (int) ( Math.abs((num - (float)hour) * 60) );
	minute = minute%scale==0?minute:(((int)(minute/scale))*scale+scale);
	if(minute == 60) {
		++hour;
		minute = 0;
	}

    tmp.append(hour>=10?hour + ":":"0"+hour+":").append(minute>=10?"" +minute:("0" + minute));

    return tmp.toString();
}
/**
 * Insert the method's description here.
 * Creation date: (12/12/2001 3:24:48 PM)
 * @return java.lang.String
 * @param str java.lang.String
 */
public static String upperFirstLetter(String str) {
    if(str == null || str.length() ==0)
        return "";
    else
        return  str.substring(0,1).toUpperCase() + str.substring(1, str.length());
}


/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=String format = 09:15 (HH.MM)
 * output type = String format = 091500 (HH:MM)
 */
public static String timeStr_StrNum(String time)
{
    if(time != null)
    {
        time = time.substring(0, time.indexOf(':')) + time.substring(time.indexOf(':')+1,time.length()) + "00";
        return time;
    }
    else
        return "0";
}

/**
 * Insert the method's description here.
 * Creation date: (8/17/2001 5:16:03 PM)
 * @return int
 * @param sdate java.lang.String
 * @param edate java.lang.String
 */
public static int calculateDays(String sdate, String edate)
{
    // input sdate format= yyyymmdd
    // input edate format = yyyymmdd, default=current date
    if(sdate == null || sdate.length() !=8)
        return 0;
    else if(edate == null || edate.length() !=8)
        edate = Format.getSysDate_NumStr();

    java.util.Calendar ca1 = java.util.Calendar.getInstance();
    java.util.Calendar ca2 = java.util.Calendar.getInstance();

    int year1 = Integer.parseInt(sdate.substring(0,4));
    int month1 = Integer.parseInt(sdate.substring(4, 6)) -1;
    int date1 = Integer.parseInt(sdate.substring(6,8));

    int year2 = Integer.parseInt(edate.substring(0,4));
    int month2 = Integer.parseInt(edate.substring(4, 6)) -1;
    int date2 = Integer.parseInt(edate.substring(6,8));

    ca1.set(year1,month1,date1);
    ca2.set(year2,month2,date2);

    java.util.Date dt1 = ca1.getTime();

    java.util.Date dt2 = ca2.getTime();

    return (int) ((dt2.getTime() - dt1.getTime()) /86400000); // 1000*60*60*24;

}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.lang.String
 * input type= String format = 20010206 (yyyymmdd)
 * output type = String format = 02/06/2001 (mm/dd/yyyy)
 */
public static String dateStr_Str(String date)
{
    if(date != null)
    {
        //String s = date.toString();
        if(date.length()<8)
            return "";
        else
            return date.substring(4,6) + "/" + date.substring(6,8) + "/" + date.substring(0,4);
    }
    else
        return "";
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.lang.String
 * input type= String format = 02062000 (mmddyyyy)
 * output type = String format = 20000206 (yyyymmdd)
 */
public static String dateStr_Str1(String date)
{
    if(date != null)
    {
        String s = date.toString();
        if(s.length()<8)
            return "";
        else
            return s.substring(4,8) + s.substring(0,2) + s.substring(2,4);
    }
    else
        return "";
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.lang.String
 * input type= String format = 02062000 (mmddyyyy)
 * output type = String format = 02/06/2001 (mm/dd/yyyy)
 */
public static String dateStr_Str2(String date)
{
    if(date != null)
    {
        //String s = date.toString();
        if(date.length()<8) {
			if ( date.length() == 7 ) {
				return date.substring(0,1) + "/" + date.substring(1,3) + "/" + date.substring(3,7);
			}
			else {
            	return "";
			}
		}
        else
            return date.substring(0,2) + "/" + date.substring(2,4) + "/" + date.substring(4,8);
    }
    else
        return "";
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.lang.String
 * input type= String format = 2001-02-06 (yyyy-mm-dd)
 * output type = String format = 02/06/2001 (mm/dd/yyyy)
 */
public static String date_Str(String s)
{
     if(s != null)
     {
         if(s.length() < 10)
             return "";
         else
             return s.substring(5, 7) + "/" + s.substring(8, 10) + "/" + s.substring(0, 4);
     } else
     {
         return "";
     }
}

public static String getFormatedDateStr(String date) {
	if ( date == null ) {
		return "";
	}

	date = date.trim();

	if ( date.length() < 10 ) {
		return dateStr_Str(date);
	}
	else {
		return date_Str(date);
	}
}


/**
 * Insert the method's description here.
 * Creation date: (6/8/2001 9:49:50 AM)
 * @return java.lang.String
 * @param num java.lang.String
 */
public static String formatDecimal(String num)
{
   	if(num == null || num.trim().length() ==0)
        return "";
    else
    {
	    String result = "";
	    
        Double tmp = new Double(num);
        java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("#,###.##");
        myFormatter.setMinimumFractionDigits(2);

		result = myFormatter.format(tmp);;
		
		if(result.equals("-0.00"))
			return "0.00";
		else
			return result;	
    }

}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type=java.math.BigDecimal format = "01012001"
 * output type=String format = Jan01
 */
public static String getMonthStr(java.math.BigDecimal date)
 {
    if(date != null)
    {
        String s = date.toString();
        if(s.length()<8)
            return "";
        else {
        String month = s.substring(4,6);
        String year = s.substring(2,4);

    if(month.equals("01"))
        month ="Jan";
    else if(month.equals("02"))
        month ="Feb";
    else if(month.equals("03"))
        month ="Mar";
    else if(month.equals("04"))
        month ="Apr";
    else if(month.equals("05"))
        month ="May";
    else if(month.equals("06"))
        month ="Jun";
    else if(month.equals("07"))
        month ="Jul";
    else if(month.equals("08"))
        month ="Aug";
    else if(month.equals("09"))
        month ="Sep";
    else if(month.equals("10"))
        month ="Oct";
    else if(month.equals("11"))
        month ="Nov";
    else
        month ="Dec";


     return month + " '" + year;
    }}
    else
        return "";
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * output date format="Month Date,Year"
 */
public static String getSysDateMDY()
 {
  try{
        Calendar cal = Calendar.getInstance();
    Date today = cal.getTime();
    String now = today.toString(); //dow mon dd hh:mm:ss zzz yyyy

    String month= now.substring(4, 7);
    String date = now.substring(8, 10);
    String year = now.substring(24, 28);

    if(month.equals("Jan"))
        month ="January";
    else if(month.equals("Feb"))
        month ="February";
    else if(month.equals("Mar"))
        month ="March";
    else if(month.equals("Apr"))
        month ="April";
    else if(month.equals("May"))
        month ="May";
    else if(month.equals("Jun"))
        month ="June";
    else if(month.equals("Jul"))
        month ="July";
    else if(month.equals("Aug"))
        month ="August";
    else if(month.equals("Sep"))
        month ="September";
    else if(month.equals("Oct"))
        month ="October";
    else if(month.equals("Nov"))
        month ="November";
    else
        month ="December";

    if(date.substring(0,1).equals("0"))
        date = date.substring(1,2);
    return month + " " + date + ", " + year;
  } catch(Exception e)
  {
    System.out.println("catch exception in get system date");
  }
  return "";
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * output date format="mm/dd/yyyy"
 */
public static String getSysDayMonth()
 {
  try{
        Calendar cal = Calendar.getInstance();
    Date today = cal.getTime();
    String now = today.toString();// dow mon dd hh:mm:ss zzz yyyy

    String month= now.substring(4, 7);
    String date = now.substring(8, 10);
    String year = now.substring(24, 28);

    if(month.equals("Jan"))
        month ="01";
    else if(month.equals("Feb"))
        month ="02";
    else if(month.equals("Mar"))
        month ="03";
    else if(month.equals("Apr"))
        month ="04";
    else if(month.equals("May"))
        month ="05";
    else if(month.equals("Jun"))
        month ="06";
    else if(month.equals("Jul"))
        month ="07";
    else if(month.equals("Aug"))
        month ="08";
    else if(month.equals("Sep"))
        month ="09";
    else if(month.equals("Oct"))
        month ="10";
    else if(month.equals("Nov"))
        month ="11";
    else
        month ="12";

    return month  + date + year;
  } catch(Exception e)
  {
    System.out.println("catch exception in get system date");
  }
  return "";
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * return date of first day of the current month
 * output date format="mm/dd/yyyy"
 */
public static String getSysMonth()
 {
  try{
        Calendar cal = Calendar.getInstance();
    Date today = cal.getTime();
    String now = today.toString();// dow mon dd hh:mm:ss zzz yyyy

    String month= now.substring(4, 7);
    //String date = now.substring(8, 10);
    String year = now.substring(24, 28);

    if(month.equals("Jan"))
        month ="01";
    else if(month.equals("Feb"))
        month ="02";
    else if(month.equals("Mar"))
        month ="03";
    else if(month.equals("Apr"))
        month ="04";
    else if(month.equals("May"))
        month ="05";
    else if(month.equals("Jun"))
        month ="06";
    else if(month.equals("Jul"))
        month ="07";
    else if(month.equals("Aug"))
        month ="08";
    else if(month.equals("Sep"))
        month ="09";
    else if(month.equals("Oct"))
        month ="10";
    else if(month.equals("Nov"))
        month ="11";
    else
        month ="12";

    return month  + "/01/" + year;
  } catch(Exception e)
  {
    System.out.println("catch exception in get system date");
  }
  return "";
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * output format="HH:MM:SS"
 */
public static String getSysTimeHMS()
{
    Calendar cal = Calendar.getInstance();
    Date today = cal.getTime();
    String now = today.toString(); //dow mon dd hh:mm:ss zzz yyyy

    String hour= now.substring(11, 13);
    String minute = now.substring(14, 16);
    String second = now.substring(17, 19);

    return hour + ":" + minute + ":" + second;
}

/**
 * Return current system Date in format mm/dd/ccyy
 * Creation date: (11/30/2001 4:34:39 PM)
 * @return java.lang.String
 */
public static String getSysDateStr()
{
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.US);
    return sdf.format(Calendar.getInstance().getTime());
}
/**
 * return current system time in format hh:mm:ss
 * Creation date: (11/30/2001 4:34:39 PM)
 * @return java.lang.String
 */
public static String getSysTimeStr()
{
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.US);
    return sdf.format(Calendar.getInstance().getTime());
}
/**
 * return current system time in format hh:mm:ss
 * Creation date: (11/30/2001 4:34:39 PM)
 * @return java.lang.String
 */
public static String getSysTimestampStr()
{
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss:SS", java.util.Locale.US);
    return sdf.format(Calendar.getInstance().getTime());
}

public static String getSysCurrentTS()
{
	java.util.Date date= new java.util.Date();
	return (new Timestamp(date.getTime())).toString();
    
}
/**
 * Insert the method's description here.
 * Creation date: (11/30/2001 4:34:39 PM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 */
public static String formatDateMY(java.math.BigDecimal date)
{
    //input format =
    if(date == null || date.toString().length() < 5)
        return "";
    else
    {
        String dt = date.toString();
        if(dt.length() == 5)
        {
            return "0" + dt.substring(0,1) + "/" + dt.substring(1,5);
        }
        else if(dt.length() == 6)
        {
            return dt.substring(0,2) + "/" + dt.substring(2,6);
        }
        else if(dt.length() == 8)
        {
            return dt.substring(0,4) + "/" + dt.substring(4,6) + "/" + dt.substring(6);
        }
        else
            return "";
    }
}
/**
 * Functions: format currency to "###.##"
 * Creation date: (6/8/2001 9:49:50 AM)
 * @return java.lang.String
 * @param num double
 */
public static String formatCurrency(java.math.BigDecimal num)
{
	String result = "";
	
	java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("###.##");
	myFormatter.setMinimumFractionDigits(2);
	num = num.divide(ConstantValue.ONE, 2, BigDecimal.ROUND_HALF_UP);

	result = myFormatter.format(num);;
	
	if(result.equals("-0.00"))
		return "0.00";
	else
		return result;	
			
}
/**
 * Functions: format currency to "###.##", if result= zero, show blank
 * Creation date: (6/8/2001 9:49:50 AM)
 * @return java.lang.String
 * @param num double
 */
public static String formatCurrencyShowBlank(java.math.BigDecimal num)
{
	String result = "";
	if(num.doubleValue() == 0d)
		return result;
	
	java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("###.##");
	myFormatter.setMinimumFractionDigits(2);
	num = num.divide(ConstantValue.ONE, 2, BigDecimal.ROUND_HALF_UP);

	if(num.doubleValue() == 0d)
		return result;
	
	result = myFormatter.format(num);;
	return result;	
}

public static String formatDecimal4(java.math.BigDecimal num)
{
	String result = "";
	java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("###.####");
	myFormatter.setMinimumFractionDigits(4);
	num = num.divide(ConstantValue.ONE, 4, BigDecimal.ROUND_HALF_UP);
	
	result = myFormatter.format(num);;
	
	if(result.equals("-0.0000"))
		return "0.0000";
	else
		return result;	
			
}

public static String formatDecimal6(java.math.BigDecimal num)
{
	String result = "";
	java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("#,###.######");
	myFormatter.setMinimumFractionDigits(6);
	num = num.divide(ConstantValue.ONE, 6, BigDecimal.ROUND_HALF_UP);
	
	result = myFormatter.format(num);;
	
	if(result.equals("-0.000000"))
		return "0.000000";
	else
		return result;	
			
}

/**
 * Functions: format currency to "#,###.##"
 * Creation date: (6/8/2001 9:49:50 AM)
 * @return java.lang.String
 * @param num double
 */
public static String displayCurrency(java.math.BigDecimal num)
{
	String result = null;
	
	java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("#,###.##");
	myFormatter.setMinimumFractionDigits(2);
	num = num.divide(ConstantValue.ONE, 2, BigDecimal.ROUND_HALF_UP);

	result = myFormatter.format(num);
	
	if(result.equals("-0.00"))
		return "0.00";
	else
		return result;	

}

public static String displayCurrencyShowBlank(java.math.BigDecimal num)
{
	//return blank if it's zero
	String result = "";
	if(num.doubleValue() == 0d)
		return result;

	java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("#,###.##");
	myFormatter.setMinimumFractionDigits(2);
	num = num.divide(ConstantValue.ONE, 2, BigDecimal.ROUND_HALF_UP);

	if(num.doubleValue() == 0d)
		return result;

	result = myFormatter.format(num);
	return result;	
}

public static String displayCurrency4Desc(java.math.BigDecimal num)
{
	String result = null;
	
	java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("#,###.####");
	myFormatter.setMinimumFractionDigits(4);
	num = num.divide(ConstantValue.ONE, 4, BigDecimal.ROUND_HALF_UP);
        
	result = myFormatter.format(num);
	
	if(result.equals("-0.0000"))
		return "0.0000";
	else
		return result;	

}
/**
 * Functions: format currency to "#,###.##"
 * Creation date: (2/13/2003 )
 * @return java.lang.String
 * @param num double
 */
public static String displayCurrency(float num)
{
        java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("#,###.##");
        myFormatter.setMinimumFractionDigits(2);
        return myFormatter.format(num);
}

public static String displayCurrency(double num)
{
	String result = "";
	
	java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("#,###.##");
	myFormatter.setMinimumFractionDigits(2);
	
	result = myFormatter.format(num);;

	if(result.equals("-0.00"))
		return "0.00";
	else
		return result;
}

/**
 * Insert the method's description here.
 * Creation date: (7/11/2002 7:23:26 PM)
 * @return java.lang.String
 * @param s java.lang.String
 */
public static String encode(String s)
{

    StringBuffer s1 = new StringBuffer(s);

    /* To encode the apostrophe and quotation mark for the name of the bank
       inside the pop up menu to avoid javascript errors
    */
    for (int j = 0;;)
        {
        try
            {
            if (s1.charAt(j) == '\'')
            {
                s1.deleteCharAt(j);
                s1.insert(j, "%27");
            }
            else if (s1.charAt(j) == '"')
            {
                s1.deleteCharAt(j);
                s1.insert(j, "%22");
            }
            j++;
        }
        catch (IndexOutOfBoundsException e)
        {
            break;
        }
    } // end of for

    return s1.toString();
}

/**
 * Insert the method's description here.
 * Creation date: (12/6/2001 1:22:58 PM)
 * @return java.lang.String
 * @param zip java.lang.String
 */
public static String displaySSN(String ssn)
{
	if(ssn == null)
		return "N/A";
	else
	{
		ssn = ssn.trim();

		String newssn = "";
		int length = ssn.length();

		if(length ==0)
			return "N/A";

		if(length >9)
			return ssn;
		else
		{
			switch (length)
			{
				case 1:	newssn = "000-00-000" + ssn;
						break;
				case 2:	newssn = "000-00-00" + ssn;
						break;
				case 3:	newssn = "000-00-0" + ssn;
						break;
				case 4:	newssn = "000-00-" + ssn;
						break;
				case 5:	newssn = "000-0" + ssn.substring(0,1) + "-" + ssn.substring(1,5);
						break;
				case 6:	newssn = "000-" + ssn.substring(0,2) + "-" + ssn.substring(2,6);
						break;
				case 7:	newssn = "00" + ssn.substring(0,1) + "-" + ssn.substring(1,3) +"-" + ssn.substring(3,7);
						break;
				case 8:	newssn = "0" + ssn.substring(0,2) + "-" + ssn.substring(2,4) +"-" + ssn.substring(4,8);
						break;
				case 9:	newssn = ssn.substring(0,3) + "-" + ssn.substring(3, 5) + "-" + ssn.substring(5,9);
						break;

			}
		}
		return newssn;
	}

}

/**
 * Insert the method's description here.
 * Creation date: (12/6/2001 1:22:58 PM)
 * @return java.lang.String
 * @param zip java.lang.String
 */
public static String displaySSN(String ssn, String country)
{
	//country = country==null?"USA":country.trim();
	//country = country.length()==0?"USA":country;
	//ssn = ssn==null?"":ssn.trim();

	country = country==null?"":country.trim();
	ssn = ssn==null?"":ssn.trim();

	if(ssn.length()==0)
		return "N/A";
	else if(ssn.trim().length()>9 || ( !(country.compareTo("USA")==0 || country.compareTo("CAN")==0)))
		return ssn;
	else
	{
		ssn = formatIDWithFixLength(ssn.trim(),9);
		return (country.compareTo("USA")==0?(ssn.substring(0,3) + "-" + ssn.substring(3,5) + "-" + ssn.substring(5,9)):(ssn.substring(0,3) + "-" + ssn.substring(3,6) + "-" + ssn.substring(6,9)));
	}

}

/**
 * Insert the method's description here.
 * Creation date: (12/6/2001 1:22:58 PM)
 * @return java.lang.String
 * @param zip java.lang.String
 */
public static String displaySpSSN(String ssn, String country)
{
		return displaySSN(ssn, country);
}

/**
 * Insert the method's description here.
 * Creation date: (2/8/2001 5:49:31 PM)
 * @return java.lang.String
 * @param date java.lang.String
 * input type= String format = 02062000 (mm/dd/yyyy)
 * output type = BigDecimal format = 20000206 (yyyymmdd)
 */
public static java.math.BigDecimal dateToDecimal(String date)
{
    if(date != null && date.length() != 0)
    {
        String s = date;
        String s1 = null;
        if(s.length() < 10)
            return new java.math.BigDecimal(0);
        else
        {
            s1 = s.substring(6,10) + s.substring(0,2) + s.substring(3,5);

            return new java.math.BigDecimal(s1);
        }
    }
    else
        return new java.math.BigDecimal(0);
}

public static String convertDate(String date)
{
    if(date != null && date.length() != 0)
    {
        String s = date;
        String s1 = null;
        if(s.length() < 10)
            return s;
        else
        {
            s1 = s.substring(6,10) + s.substring(0,2) + s.substring(3,5);

            return s1;
        }
    }
    else
        return "";
}

/**
 * Insert the method's description here.
 * Creation date: (12/19/2001 1:56:37 PM)
 * @return java.util.Vector
 * @param msg java.lang.String
 */
public static Vector getErrorMsgVector(String msg)
{
    java.util.Vector v = new java.util.Vector();
    if(msg == null || msg.length()==0)
        return v;
    else
    {
        msg = msg.trim();

        if(msg.substring(0,1).equals("*"))
            msg = msg.substring(1);//remove first "*"

        int currentIndex =0;
        int len = msg.length();
        for( ; currentIndex<len; )
        {
            int loc = msg.indexOf('*',currentIndex);
            if(loc != -1)
            {
                v.addElement(msg.substring(currentIndex,loc));
                currentIndex = loc+1;
            }
            else
            {
                v.addElement(msg.substring(currentIndex,len));
                currentIndex = len;
            }
        }
        return v;
    }
}

/**
 * Insert the method's description here.
 * Creation date: (7/11/2002 7:23:26 PM)
 * @return java.lang.String
 * @param s java.lang.String
 */
public static String encodeHTML(String s)
{
	if (s == null || s.equals("")) 
		return "";
    
	StringBuffer s1 = new StringBuffer(s);

    /* To encode the apostrophe and quotation mark for free strings
    */
    for (int j = 0;;)
    {
    	try
        {
            if (s1.charAt(j) == '\'')
            {
                s1.deleteCharAt(j);
                s1.insert(j, "&#039;");
            }
            else if (s1.charAt(j) == '"')
            {
                s1.deleteCharAt(j);
                s1.insert(j, "&#034;");
            }
            else if (s1.charAt(j) == '<')
            {
                s1.deleteCharAt(j);
                s1.insert(j, "&#060;");
            }
            else if (s1.charAt(j) == '>')
            {
                s1.deleteCharAt(j);
                s1.insert(j, "&#062;");
            }
            else if (s1.charAt(j) < 32)
            {
                s1.deleteCharAt(j);
                s1.insert(j, "&#032;");
            }
            j++;
        }
        catch (IndexOutOfBoundsException e)
        {
            break;
        }
    } // end of for

    return s1.toString();
}

public static String decodeHTML(String s)
{

    String[][] pat = {{"&#039", "\'"},
	                  {"&#034", "'"},
    				  {"&#032", " "}
					};

    StringBuffer s1 = new StringBuffer(s);
	int len = pat.length;
	int idx = -2;
	for (int i=0; i<len; i++) {
		idx = s.indexOf(pat[i][0]);
		if ( idx != -1 ) {
			int strLen = (pat[i][0]).length();
			s1.delete(idx, idx+strLen);
			s1.insert(idx, pat[i][1]);
			break;
		}
	}

	if ( idx != -1 ) {
		return decodeHTML(s1.toString());
	}
	else {
    	return s1.toString();
	}
}

public static String displayPhone(String ph)
{
	String phret = null;
	if (ph == null || ph.length() == 0)
	{
	  return "";
	}
	if(ph.indexOf('(') >=0)
		return ph;

	if(ph.indexOf('-') >=0)
		return ph;

    if(ph.length() < 10)
    {
    	
	    if (ph.length() <= 4)
	    {
	       phret = ph;
	    }
	    else if (ph.length() > 4)
	    {
	       phret = ph.substring(ph.length()-4,ph.length());
	       if (ph.length() > 4 && ph.length() <= 7)
	       {
	         phret = ph.substring(0,ph.length()-4) + "-" + phret;
	       }
	       else
	       {
	         phret = "(" + ph.substring(0,ph.length()-7) + ")" + ph.substring(ph.length()-7,ph.length()-4) + "-" + phret;
	       }
	    }
    }
    else if(ph.length() == 10)
    {
        phret = "("+ph.substring(0,3)+")" + ph.substring(3,6) + "-" + ph.substring(6,10);
    }
    else if(ph.length() == 11)
    {
        phret = "("+ph.substring(0,3)+")" + ph.substring(3,11);
    }
    else
    	phret = ph;
    	
    return phret;
}

/**
 * Insert the method's description here.
 * Creation date: (9/27/2002 11:17:52 AM)
 * @return java.lang.String[]
 * @param lib int
 */
public static String[] getTableNames(int lib)
{
	/****************************
		lib 1= tstpm_dta/pecpara
		lib 2= tstfi_dta/parpara
		lib 3= tstbr_dta/pbrpara
		lib 4= tstap_dta/pappara
	*****************************/
	switch (lib)
	{
		case 1:
			return new String[]{"APPLICATION-NAME",
								"BANK",
								"BILLABLE",
								"CONTACT-TYPE",
								"HOLIDAY",
								"HOURS-WORKED",
								"INTERNAL-PROJECT",
								"PCR-PRIORITY",
								"PCR-SEQUENCE",
								"PCR-STATUS",
								"PERSONAL-HOLIDAY",
								"PROJECT-TYPE",
								"Regular Error",
								"SICK-OVERFLOW",
								"TASK-LEVEL",
								"TYPE-CONSULTANT",
								"TYPE-PAY",
								"TYPE-RATE",
								"VACATION-OVERFLOW"  };

		case 2:
			return new String[]{"A/R Aging",
								"Application Type",
								"Bank",
								"Batch Status",
								"Batch Type",
								"Company",
								"Customer status",
								"Discount",
								"Document Status",
								"Document Type",
								"Memo Type",
								"Payment Status",
								"Payment Term",
								"Payment Type",
								"Unit Measure",
								"Void Reason" };

		case 3:
			return new String[]{"CATEGORIES",
								"CLASSES",
								"COMPANY-CURRENCY",
								"CURRENCY",
								"DISCOUNT/CHARGE",
								"EXCHANGE",
								"MEMO",
								"VENDOR-ACCOUNTG/L",
								"VENDOR_CATEGORY",
								"VENDOR_CLASS",
								"VENDOR_CONSOLIDATE",
								"VENDOR_STATUS",
								"VENDOR_TAX_RATE",
								"VENDOR_TERMS",
								"VENDOR_TYPE" };

		case 4:
			return new String[]{ "AA", "BB", "CC" };
			//break;

		default:
			return null;

	}

}

/**
 * Insert the method's description here.
 * Creation date: (9/27/2002 10:49:55 AM)
 * @return java.lang.String[]
 * @param lib int
 */
public static String[] getTransactionCodes(int lib)
{
	/****************************
		lib 1= tstpm_dta/pecpara
		lib 2= tstfi_dta/parpara
		lib 3= tstbr_dta/pbrpara
		lib 4= tstap_dta/pappara
	*****************************/
	switch (lib)
	{
		case 1:
			return new String[]{ "A", "C", "D", "E", "F", "H",
								 "I", "IV", "L", "M", "O", "P",
								 "01", "02", "03", "04" };
		case 2: //
			return new String[]{ "AP", "AU", "BA", "BK", "CB", "CC",
								 "CD", "CK", "CL", "CR", "DB", "EA",
								 "ER", "HR", "II", "IN", "IV", "KG",
								 "LT", "MT", "NA", "NF", "OA", "OD",
								 "OI", "ON", "OP", "PD", "PR", "ST",
								 "UN", "VD", "WT", "00", "01", "02",
								 "03", "04", "06", "10", "20", "30" };
		case 3:
			return new String[]{ "AA", "BB", "CC" };

		case 4:
			return new String[]{ "AA", "BB", "CC" };

		default:
			return null;

	}

}

 //******************************************************************
 //* Function to indent the G/L Accounts
 //******************************************************************
public static String indentGLAccount(String gl1,String gl2,String gl3,String gl4,String gl5,String glnm,int glsize)
 {
   final String SEPARATOR = "&nbsp;&nbsp;&nbsp;";
   StringBuffer indent = new StringBuffer("");
   //int g1 = Integer.parseInt(gl1);
   int g2 = Integer.parseInt(gl2);
   int g3 = Integer.parseInt(gl3);
   int g4 = Integer.parseInt(gl4);
   int g5 = Integer.parseInt(gl5);
   int indentCounter = 0;
   if  ( (g2+g3+g4+g5) != 0 )
   {
     if ((g3+g4+g5) == 0)
     {
       indent.append(SEPARATOR);
       indentCounter = 1;
     }
     else if ((g4+g5) == 0)
     {
       indent.append(SEPARATOR).append(SEPARATOR);
       indentCounter = 2;
     }
     else if (g5 == 0)
     {
       indent.append(SEPARATOR).append(SEPARATOR).append(SEPARATOR);
       indentCounter = 3;
     }
     else
     {
       indent.append(SEPARATOR).append(SEPARATOR).append(SEPARATOR).append(SEPARATOR);
       indentCounter = 4;
     }
   }
   glsize = glsize + (SEPARATOR.length() * indentCounter) - indentCounter * 3;
   indent.append(glnm.trim());
   if (indent.length() > glsize)
   {
       indent = new StringBuffer(indent.substring(0,glsize-3));
       indent.append("...");
   }
   else if (indent.length() < glsize)
   {
       for (int i = indent.length();i < glsize;i++)
       		indent.append("&nbsp;");
   }
   for (int j = 0;j < indent.length();j++)
   {
     if (indent.charAt(j) == ' ')
     {
       indent.deleteCharAt(j);
       indent.insert(j, "&nbsp;");
     }
   }
   return escapeOnce(indent.toString());
 }



/*
 * Another GL indentation function
 */
 static public String indentAccount(String gl1,String gl2,String gl3,String gl4,String gl5)
 {
   final String SEPARATOR = "&nbsp;&nbsp;&nbsp;";
   StringBuffer indent = new StringBuffer("");
   //int g1 = Integer.parseInt(gl1);
   int g2 = Integer.parseInt(gl2);
   int g3 = Integer.parseInt(gl3);
   int g4 = Integer.parseInt(gl4);
   int g5 = Integer.parseInt(gl5);

   if  ( (g2+g3+g4+g5) != 0 )
   {
     if ((g3+g4+g5) == 0)
       indent.append(SEPARATOR);
     else if ((g4+g5) == 0)
       indent.append(SEPARATOR).append(SEPARATOR);
     else if (g5 == 0)
       indent.append(SEPARATOR).append(SEPARATOR).append(SEPARATOR);
     else
       indent.append(SEPARATOR).append(SEPARATOR).append(SEPARATOR).append(SEPARATOR);
   }
   return indent.toString();
 }

 /*
  * Another GL indentation function
  */
  static public String indentAccount(String gl)
  {
	final String SEPARATOR = "&nbsp;&nbsp;&nbsp;";
	StringBuffer indent = new StringBuffer("");
	//int g1 = Integer.parseInt(gl.substring(0,3));
	int g2 = Integer.parseInt(gl.substring(3,6));
	int g3 = Integer.parseInt(gl.substring(6,9));
	int g4 = Integer.parseInt(gl.substring(9,12));
	int g5 = Integer.parseInt(gl.substring(12,15));

	if  ( (g2+g3+g4+g5) != 0 )
	{
	  if ((g3+g4+g5) == 0)
		indent.append(SEPARATOR);
	  else if ((g4+g5) == 0)
		indent.append(SEPARATOR).append(SEPARATOR);
	  else if (g5 == 0)
		indent.append(SEPARATOR).append(SEPARATOR).append(SEPARATOR);
	  else
		indent.append(SEPARATOR).append(SEPARATOR).append(SEPARATOR).append(SEPARATOR);
	}
	return indent.toString();
  }

 //******************************************************************
 //* Function to indent the string cat1 until a size of size1
 //* The level is the indentation level
 //*
 //******************************************************************
public static String indent(int level,String cat1,int size1)
 {
   final String SEPARATOR = "&nbsp;&nbsp;&nbsp;";
   StringBuffer indent = new StringBuffer("");
   //int indentCounter = 0;
   if (level > 1 )
   {
     for (int i = 0;i<=level-2;i++)
     {
	   indent.append(SEPARATOR);
     }
   }
   size1 = size1 + (SEPARATOR.length() * (level-1)) - (level-1) * 3;
   indent.append(cat1.trim());
   if (indent.length() > size1)
   {
       indent = new StringBuffer(indent.substring(0,size1-3));
       indent.append("...");
   }
   else if (indent.length() < size1)
   {
       for (int i = indent.length();i < size1;i++)
       		indent.append("&nbsp;");
   }
   for (int j = 0;j < indent.length();j++)
   {
     if (indent.charAt(j) == ' ')
     {
       indent.deleteCharAt(j);
       indent.insert(j, "&nbsp;");
     }
   }
   return escapeOnce(indent.toString());
 }

 public static float floatValue(String time)
 {
	float p1 =0;
	float p2 =0;

	p1 = (Float.valueOf(time.substring(0,2))).floatValue();

	p2 = (Float.valueOf(time.substring(3,5))).floatValue()/100;

	return p1+p2;
 }

 public static double roundTo(double num, String pattern)
 {
	java.text.DecimalFormat myFormat = new java.text.DecimalFormat(pattern);
	String ret = myFormat.format(num);

	return Double.parseDouble(ret);
 }

/**
 * Insert the method's description here.
 * Creation date: (05/27/2003 10:54:00 AM)
 * @return java.lang.String
 * @param date java.math.BigDecimal
 * input type = BigDecimal format = 200102 (yyyymm)
 * output type = String format = 02/06/2001 (mm/yyyy)
 */
public static String dateCreditCard2String(java.math.BigDecimal date)
{
	String s = null;
	int l = 0;

    if(date != null)
    {
	    s = date.toString();
	    l = s.length();
        if(l != 6)
            return "";
        else
            return s.substring(l-2) + "/" + s.substring(0,l-2);
    }
    else
        return "";
}
/**
 * Insert the method's description here.
 * Creation date: (11/30/2001 4:34:39 PM)
 * @return java.lang.String
 * @param date java.sql.SQLData
 */
public static java.sql.Date strDatetoSQLDate(String dt) {
	//input format = mm/dd/ccyy
	//output format = ccyy-mm-dd
	if(dt == null)
		return java.sql.Date.valueOf("0001-01-01");
	else if(dt.length()==10)
		return java.sql.Date.valueOf(dt.substring(6,10)+"-" +dt.substring(0,2)+"-"+dt.substring(3,5));
	else if(dt.length()==8)
		return java.sql.Date.valueOf(dt.substring(4,8)+"-" +dt.substring(0,2)+"-"+dt.substring(2,4));
	else if(dt.length()==9 && dt.indexOf('/')==2)
		return java.sql.Date.valueOf(dt.substring(5,9)+"-" +dt.substring(0,2)+"-"+dt.substring(3,5));
	else if(dt.length()==9 && dt.indexOf('/')==4)
		return java.sql.Date.valueOf(dt.substring(5,9)+"-" +dt.substring(0,2)+"-"+dt.substring(2,4));
	else
		return java.sql.Date.valueOf("0001-01-01");

}

public static Timestamp strTStoSQLTS(String ts) {
	//input format = yyyy-mm-dd hh:mm:ss.fffffffff 
	if(ts == null || ts.length() ==0)
		return ConstantValue.BLANK_TS;
	else 
		try{
			return Timestamp.valueOf(ts);
		}catch(IllegalArgumentException e) {}

	return null;
}

/**
 * Insert the method's description here.
 * Creation date: (05/27/2003 10:54:00 AM)
 * @return java.lang.String
 * @param date java.lang.String
 * input type = String format = 02/2001 (mm/yyyy)
 * output type = String format = 200102 (yyyymm)
 */
public static String dateCreditCard2Numeric(java.lang.String date)
{
	int l = 0;

    if(date != null)
    {
	    l = date.length();
        if(l != 7)
            return "0";
        else
            return date.substring(3) + date.substring(0,2);
    }
    else
        return "0";
}

/**
 * Insert the method's description here.
 * Creation date: (11/30/2001 4:34:39 PM)
 * @return java.lang.String
 * @param date java.sql.SQLData
 */
public static String displayDate(String date) {
	if(date == null || date.length() != 10 || date.equals("0001-01-01"))
		return "";
	else if(date.indexOf("/") >0){
		return date;
	}
	else
	{
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.US);
		return sdf.format(java.sql.Date.valueOf(date));
	}
}

/**
 * Insert the method's description here.
 * Creation date: (11/30/2001 4:34:39 PM)
 * @return java.lang.String
 * @param date java.sql.SQLData
 */
public static String displayDate(java.sql.Date date) {
	if(date == null || date.compareTo(java.sql.Date.valueOf("0001-01-01")) == 0)
		return "";
	else
	{
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.US);
		return sdf.format(date);
	}
}

/**
 * Insert the method's description here.
 * Creation date: (11/30/2001 4:34:39 PM)
 * @return java.lang.String
 * @param ts java.lang.String
 */
public static String displayTimestamp(String ts)
{
	//input format=2002-05-03-10.23.44.348138 / 0001-01-01-00.00.00.000000
	if(ts == null || ts.toString().substring(0,2).equals("1-") || ts.length() < 19)
	{	
		return ""; 
	} 
	else if(ts.indexOf("/") >0){
		return ts;
	}
	else if(ts.length() > 19)
	{
		if(ts.substring(0,4).equals("0001"))
			return "";
		else if(ts.lastIndexOf('-')<10){
			ts = ts.replace('-', '/').replace('.', ':');
			return ts.substring(5, 10) + "/" + ts.substring(0, 4) + " " + ts.substring(11, 19);
		}
		else
		{
			ts = ts.replace('-', '/').replace('.', ':');
			return ts.substring(5, 11) + ts.substring(0, 4) + " " + ts.substring(11, 19);
			//java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss", java.util.Locale.US);
			//return sdf.format(java.sql.Timestamp.valueOf(ts));
		}
	}
	else
		return "";
}

/**
 * Insert the method's description here.
 * Creation date: (11/30/2001 4:34:39 PM)
 * @return java.lang.String
 * @param date java.sql.SQLData
 */
public static String displayTimestamp(java.sql.Timestamp ts)
{
	String str = "";
	
	if(ts == null || ts.toString().substring(0,2).equals("1-") )
		return "";
	else
	{
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss", java.util.Locale.US);
				
		str = sdf.format(ts);
		
		if (str.substring(6,10).equals("0001"))
			return "";
		else return str;	
	}
}

/**
 * Insert the method's description here.
 * Creation date: (6/19/2003 8:01:03 PM)
 * @return java.lang.String
 * @param obj org.omg.CORBA.Object
 */
public static String displayNullObject(Object obj) {

	if(obj == null)
		return "";
	else
		return obj.toString();
}

/**
 * Insert the method's description here.
 * Creation date: (15/7/2003 1:22:58 PM)
 * @return java.lang.String
 * @param zip java.lang.String
 */
public static String displayAddress(String addr1, String addr2, String addr3, String city, String state, String prov, String country, String county, String zip)
{

	addr1 = ((addr1==null?"":addr1.trim()) + (addr2==null?"":(addr2.trim()).length()==0?"":", ") +
			(addr2==null?"":addr2.trim()) + (addr3==null?"":(addr3.trim()).length()==0?"":", ") +
			(addr3==null?"":addr3.trim())).trim();


	//do not display county for USA, Yan 09/06/2009
	if(country.equals("USA"))
		county = "";

	addr2 = ((city==null?"":city.trim()) + (county==null?"":(county.trim()).length()==0?"":", ") +
			(county==null?"":county.trim())).trim();

	addr3 = "";

	country = country==null?"USA":country.trim();

	if(country.equals("USA"))
		addr3 = state==null?"":state.trim() + " ";
	else
		addr3 = prov==null?"":prov.trim() + " ";

	zip = zip==null?"":zip.trim();

	if(zip.length()!=0)
		addr3 = addr3 + displayZip(zip, country);

	if(country.equals("USA"))
		country = "";

	addr3 = (addr3 + (country.length()==0?"":", ") + country).trim();

	StringBuffer address = new StringBuffer("");
	boolean isEmpty = true;

	if ( addr1.length() != 0 ) {
		address.append(addr1);
		isEmpty = false;
	}
	if ( addr2.length() != 0 ) {
		if ( !isEmpty ) {
			address.append(",");
		}
		address.append(addr2);
	}
	if ( addr3.length() != 0 ) {
		if ( !isEmpty ) {
			address.append(",");
		}
		address.append(addr3);
	}

	return address.toString();
}

public static String displayAddress2(String addr1, String addr2, String addr3, String city, String state, String prov, String country, String county, String zip)
{

	addr1 = ((addr1==null?"":addr1.trim()) + "" + (addr2==null?"":(addr2.trim()).length()==0?"":"<BR>") +
			(addr2==null?"":addr2.trim()) + (addr3==null?"":(addr3.trim()).length()==0?"":"<BR>") +
			(addr3==null?"":addr3.trim())).trim();


	addr2 = ((city==null?"":city.trim()) + (county==null?"":(county.trim()).length()==0?"":", ") +
			(county==null?"":county.trim())).trim();


	addr3 = "";

	country = country==null?"USA":country.trim();

	if(country.equals("USA"))
		addr3 = state==null?"":state.trim() + " ";
	else
		addr3 = prov==null?"":prov.trim() + " ";

	zip = zip==null?"":zip.trim();

	if(zip.length()!=0)
		addr3 = addr3 + displayZip(zip, country);

	if(country.equals("USA"))
		country = "";

	addr3 = (addr3 + (country.length()==0?"":"<BR>") + country).trim();

	StringBuffer address = new StringBuffer("");
	boolean isEmpty = true;

	if ( addr1.length() != 0 ) {
		address.append(addr1);
		isEmpty = false;
	}
	if ( addr2.length() != 0 ) {
		if ( !isEmpty ) {
			address.append("<BR>");
		}
		address.append(addr2);
	}
	if ( addr3.length() != 0 ) {
		if ( !isEmpty ) {
			address.append(", ");
		}
		address.append(addr3);
	}

	return address.toString();
}

public static String displayAddressReport(String addr1, String addr2, String addr3, String city, String state, String prov, String country, String county, String zip)
{
	country = country==null?"USA":country.trim();
	//do not display county for USA
	if (country.equals("USA"))
		county = "";
	
	addr1 = ((addr1==null?"":addr1.trim()) + "" + (addr2==null?"":(addr2.trim()).length()==0?"":"\n") +
			(addr2==null?"":addr2.trim()) + (addr3==null?"":(addr3.trim()).length()==0?"":"\n") +
			(addr3==null?"":addr3.trim())).trim();

	addr2 = ((city==null?"":city.trim()) + (county==null?"":(county.trim()).length()==0?"":", ") +
			(county==null?"":county.trim())).trim();

	addr3 = "";

	if(country.equals("USA"))	
		addr3 = state==null?"":state.trim() + " ";
	else
		addr3 = prov==null?"":prov.trim() + " ";

	zip = zip==null?"":zip.trim();

	if(zip.length()!=0)
		addr3 = addr3 + displayZip(zip, country);

	if (country.equals("USA"))
		country = "";

	addr3 = (addr3 + (country.length()==0?"":"\n") + country).trim();

	StringBuffer address = new StringBuffer("");
	boolean isEmpty = true;

	if ( addr1.length() != 0 ) {
		address.append(addr1);
		isEmpty = false;
	}
	if ( addr2.length() != 0 ) {
		if ( !isEmpty ) {
			address.append("\n");
		}
		address.append(addr2);
	}
	if ( addr3.length() != 0 ) {
		if ( !isEmpty ) {
			address.append(", ");
		}
		address.append(addr3);
	}

	return address.toString();
}

public static String displayAddressReport(String addr1, String addr2, String addr3, String city, String state, String prov, String country, String county, String zip, String countryName)
{
	country = country==null?"USA":country.trim();
	//do not display county for USA
	if (country.equals("USA"))
		county = "";
	
	addr1 = ((addr1==null?"":addr1.trim()) + "" + (addr2==null?"":(addr2.trim()).length()==0?"":"\n") +
			(addr2==null?"":addr2.trim()) + (addr3==null?"":(addr3.trim()).length()==0?"":"\n") +
			(addr3==null?"":addr3.trim())).trim();

	addr2 = ((city==null?"":city.trim()) + (county==null?"":(county.trim()).length()==0?"":", ") +
			(county==null?"":county.trim())).trim();

	addr3 = "";

	if(country.compareTo("USA") ==0)
		addr3 = state==null?"":state.trim() + " ";
	else
		addr3 = prov==null?"":prov.trim() + " ";

	zip = zip==null?"":zip.trim();

	if(zip.length()!=0)
		addr3 = addr3 + displayZip(zip, country);

	if (country.equals("USA"))
		countryName = "";

	addr3 = (addr3 + (countryName.length()==0?"":"\n") + countryName).trim();

	StringBuffer address = new StringBuffer("");
	boolean isEmpty = true;

	if ( addr1.length() != 0 ) {
		address.append(addr1);
		isEmpty = false;
	}
	if ( addr2.length() != 0 ) {
		if ( !isEmpty ) {
			address.append("\n");
		}
		address.append(addr2);
	}
	if ( addr3.length() != 0 ) {
		if ( !isEmpty ) {
			address.append(", ");
		}
		address.append(addr3);
	}

	return address.toString();
}

/**
 * Insert the method's description here.
 * Creation date: (15/07/2003 12:44:33 PM)
 * @return java.lang.String
 * @param zip java.lang.String
 * @param country java.lang.String
 */
public static String displayZip(String zip, String country)
{

	/******************************************
	* Valid User input zip code format are:
	* USA: #####, #########
	* CAN: ######
	*
	* Formated output zip code format are:
	* USA: #####,  #####-####
	* CAN: ### ###
	*******************************************/

	int len = 0;

	//check input country
	if(country == null || country.trim().length() != 3)
		return zip.trim();

	//check input zip code
	if(zip == null)
		return "";
	else
		zip = zip.trim();


	if(!(zip.length() == 5 || zip.length() == 6 || zip.length() == 9))

		return zip;
	else
	{
		len = zip.length();

		//format zip code
		if(country.equalsIgnoreCase("USA"))
		{
			if(len == 5)
			{
				//input format= #####
				return zip;
			}
			else if (len == 9)
			{
				//input format= #########
				return zip.substring(0,5) + "-" + zip.substring(5,9);
			}
			else
			{
				//input format= unknown
				return zip;
			}
		}
		else if(country.equalsIgnoreCase("CAN"))
		{
			if(zip.length() ==6)
				//input format = ######
				return (zip.substring(0,3) + " " + zip.substring(3,6)).toUpperCase();
			else
				//input format = unknown
				return zip;
		}
		else
		{
			return zip;
		}
	}
}

/**
 * Insert the method's description here.
 * Creation date: (12/6/2001 1:22:58 PM)
 * @return java.lang.String
 * @param zip java.lang.String
 */
public static String removeChar(String str, char ch)
{
	str = str==null?"":str.trim();
	int len = str.length();
	int sepindex = str.indexOf(ch,0);
	while(sepindex >=0){
		str = str.substring(0,sepindex) + str.substring(sepindex + 1,len);
		len = str.length();
		sepindex = str.indexOf(ch,0);
	}
	return str.trim();

}

/**
 * Insert the method's description here.
 * Creation date: (07/16/2003 1:22:58 PM)
 * @return java.lang.String
 * @param zip java.lang.String
 */
public static String displayPhoneAll(String phctry, String ph, String ext)
{
	String phret = "";
	ph = (ph==null)?"":ph.trim();

    if(ph.length() == 10)
    {
        phret = "("+ph.substring(0,3)+")" + ph.substring(3,6) + "-" + ph.substring(6,10);
    }
    else if(ph.length() == 11)
    {
        phret = "("+ph.substring(0,3)+")" + ph.substring(3,11);
    }
    else
    	phret = ph;

    return ((phctry==null?"":phctry.trim()) + (phctry==null?"":phctry.trim().length()==0?"":"-") + phret + (ext==null?"":ext.trim().length()==0?"":"x") + (ext==null?"":ext.trim()));
}

/**
 * Replace sub string str2 within str1 with a given string str3
 * Creation date: (3/14/2003 2:36:47 PM)
 * @return java.lang.String
 * @param str java.lang.String
 */
public static String replaceString(String str1, String str2, String str3)
{
	if(str1 == null || str2 == null || str3 == null)
		return str1;

	str1 = str1.trim();
	str2 = str2.trim();
	str3 = str3.trim();


	if(str1.length() ==0 || str2.length() ==0 || str3.length() ==0)
		return str1;
	else
	{
		int len2 = str2.length();

		StringBuffer sb = new StringBuffer();
		boolean endOfString = false;

		while ( !endOfString)
		{
			int indexOfStr = str1.indexOf(str2);
			int strIndex = 0;

			if (indexOfStr != -1)
			{
				sb.append(str1.substring(strIndex, indexOfStr));
				sb.append(str3);

				str1 = str1.substring(indexOfStr + len2, str1.length());
			}
			else
			{
				sb.append(str1);
				endOfString = true;
			}

		}

		return sb.toString();
	}
}

/**
 * Replace ' with '' and " with blank to be passed to the SQL SP
 * Creation date: (7/31/2003 2:36:47 PM)
 * @return java.lang.String
 * @param str java.lang.String
 */
public static String encodeString(String s) {
	if (s == null) return "";
    StringBuffer s1 = new StringBuffer(s);
    for (int j = 0;;) {
        try {
            if (s1.charAt(j) == '\'') {
                s1.insert(j, "'");
                j++;
            }else if (s1.charAt(j) == '"') {
				s1.deleteCharAt(j);
                s1.insert(j, "\"");
            }
            /*
            else if (s1.charAt(j) < 32){
                s1.deleteCharAt(j);
                s1.insert(j, " ");
            }
            */
            j++;
        }catch (IndexOutOfBoundsException e) {
            break;
        }
    } // end of for
    return s1.toString();
}

/**
 * To be used in GL Tree
 * Replace ' with \' and " with \" to be passed to the SQL SP
 * Creation date: (8/11/2003 2:36:47 PM)
 * @return java.lang.String
 * @param str java.lang.String
 */
public static String escapeOnce(String s) {
	if (s == null) return "";
    StringBuffer s1 = new StringBuffer(s);
    for (int j = 0;;) {
        try {
            if (s1.charAt(j) == '\\') {
				s1.deleteCharAt(j);
                s1.insert(j, "\\\\");
		    j = j + 2;
			}
            j++;
        }catch (IndexOutOfBoundsException e) {
            break;
        }
    } // end of for
    for (int j = 0;;) {
        try {
            if (s1.charAt(j) == '\'') {
                s1.insert(j, "\\");
		   j++;
            }else if (s1.charAt(j) == '"') {
				s1.deleteCharAt(j);
                s1.insert(j, "\\\"");
		   j++;j++;
            }else if (s1.charAt(j) < 32){
                s1.deleteCharAt(j);
                s1.insert(j, " ");
            }
            j++;
        }catch (IndexOutOfBoundsException e) {
            break;
        }
    } // end of for
    return s1.toString();
}

/**
 * To be used in GL Tree
 * Replace ' with \\' and " with &quot; to be passed to the SQL SP
 * Creation date: (8/11/2003 2:36:47 PM)
 * @return java.lang.String
 * @param str java.lang.String
 */
public static String escapeDouble(String s) {
	if (s == null) return "";
    StringBuffer s1 = new StringBuffer(s);
    for (int j = 0;;) {
        try {
            if (s1.charAt(j) == '\\') {
				s1.deleteCharAt(j);
                s1.insert(j, "\\\\\\\\");
		    j = j + 3;
			}
            j++;
        }catch (IndexOutOfBoundsException e) {
            break;
        }
    } // end of for
    for (int j = 0;;) {
        try {
            if (s1.charAt(j) == '\'') {
				s1.deleteCharAt(j);
                s1.insert(j, "\\\\'");
		   j = j + 2;
            }else if (s1.charAt(j) == '"') {
				s1.deleteCharAt(j);
                s1.insert(j, "&#34;");
		   j++;
            }else if (s1.charAt(j) < 32){
                s1.deleteCharAt(j);
                s1.insert(j, " ");
            }
            j++;
        }catch (IndexOutOfBoundsException e) {
            break;
        }
    } // end of for
    return s1.toString();
}


/**
 * @return float
 * @param  float
 * input type=float format = 17.5 (H)
 * output type = float format = 17.30 (HH.MM)
 */
public static String formatTimeToHHMM(float time)
{
    int Hour = (int) time;
    String s = Float.toString(time);
    s = s.substring(s.indexOf("."));
    float Minute = Float.parseFloat(s);

    Minute = Minute * 60;
    int m = (int) Minute;
    String ms = null;
    if(m < 10)
    {
		ms = "0" + Integer.toString(m);
    }
    else
    {
		ms = Integer.toString(m);
    }
    //return (Integer.toString(Hour) + ":" + ms);
	return ((Hour==0?"00":"") + ":" + ms);
}

public static String escapeSingerDouble(String s) {
	if (s == null) return "";
    StringBuffer s1 = new StringBuffer(s);
    for (int j = 0;;) {
        try {
            if (s1.charAt(j) == '\\') {
				s1.deleteCharAt(j);
                s1.insert(j, "\\\\");
		    j = j + 2;
			}
            j++;
        }catch (IndexOutOfBoundsException e) {
            break;
        }
    } // end of for
    for (int j = 0;;) {
        try {
            if (s1.charAt(j) == '\'') {
                s1.insert(j, "\\");
		   j++;
            }else if (s1.charAt(j) == '"') {
				s1.deleteCharAt(j);
                s1.insert(j, "\\\"");
		   j++;
            }else if (s1.charAt(j) < 32){
                s1.deleteCharAt(j);
                s1.insert(j, " ");
            }
            j++;
        }catch (IndexOutOfBoundsException e) {
            break;
        }
    } // end of for
    return s1.toString();
}

public static String escapeSingleQuote(String s) {
	/* similar to escapeSingerDouble(), but ignor "
	 * It's used in search helper to pass back name with special char
	 */
	if (s == null) return "";
	StringBuffer s1 = new StringBuffer(s);
	for (int j = 0;;) {
		try {
			if (s1.charAt(j) == '\\') {
				s1.deleteCharAt(j);
				s1.insert(j, "\\\\");
			j = j + 2;
			}
			j++;
		}catch (IndexOutOfBoundsException e) {
			break;
		}
	} // end of for
	for (int j = 0;;) {
		try {
			if (s1.charAt(j) == '\'') {
				s1.insert(j, "\\");
		   		j++;
			}
			/*
			else if (s1.charAt(j) == '"') {
				s1.deleteCharAt(j);
				s1.insert(j, "\\\"");
		   		j++;
			}
			*/
			else if (s1.charAt(j) < 32){
				s1.deleteCharAt(j);
				s1.insert(j, " ");
			}
			j++;
		}catch (IndexOutOfBoundsException e) {
			break;
		}
	} // end of for
	return s1.toString();
}

public static String removeZerosIfNotInt(String str, String pattern, String replace) {
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();

        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e+pattern.length();
        }
        result.append(str.substring(s));
        return result.toString();
    }

/**
 * Insert the method's description here.
 * Creation date: (11/30/2001 4:34:39 PM)
 * @return java.lang.String
 * @param date java.sql.SQLData
 */
public static java.util.Date getBlankDate()
{
	try{
		SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
		return df.parse("01010001");
	}catch(ParseException e) {}

	return null;
}

public static Timestamp getBlankTimestamp()
{
	
	try{
		return Timestamp.valueOf("0001-01-01 00:00:00.000000000"); 
	}catch(IllegalArgumentException e) {}

	return null;
}

public static String FormatTime_Str_Str(String entry)
{
 	return entry.replace('.', ':');
}

/**
 * Return IP Address based on passed in request
 * Creation date: (9/28/2004 5:49:31 PM)
 * @return java.lang.String
 * @param request HttpServletRequest
 */
public static String getIPAddress(HttpServletRequest request)
{
	if(request != null)
	{  
		try
		{
			//return java.net.InetAddress.getByName(request.getRemoteHost()).getHostAddress();
			return request.getRemoteAddr();
			
		}catch (Exception e)
		{
			return "Unknow Host";			
		}
	}
	else
		return "";
}

public static String calChangePercent(BigDecimal dec1, BigDecimal dec2)
{
	if(dec1 == null || dec2 == null || (dec1.doubleValue() ==0d && dec2.doubleValue() ==0d))
		return "0.00%";
	if(dec2.doubleValue() == 0d)
		if (dec1.signum() > 0)
			return "100.00%";
		else 
			return "-100.00%";
			
	return Format.formatDecimal("" + dec1.subtract(dec2).doubleValue() * 100d / dec2.doubleValue()) + "%";	
}

public static String calculatePercent(BigDecimal dec1, BigDecimal dec2)
{
	if(dec1 == null || dec2 == null || (dec1.doubleValue() ==0d && dec2.doubleValue() ==0d))
		return "0.00%";
	if(dec2.doubleValue() == 0d)
		if (dec1.signum() > 0)
			return "100.00%";
		else 
			return "-100.00%";
			
	return Format.formatDecimal("" + dec1.doubleValue() * 100d / dec2.doubleValue()) + "%";	
}


/**
 * Complete word at the end of each str.
 * 
 * Creation date: (12/12/2001 3:24:48 PM)
 * @return java.lang.String[]
 * @param str java.lang.String[]
 */
public static String[] completeWord(String[] str) {
	if(str == null || str.length ==0)
		return str;
	else
	{
		for(int i = 0; i<str.length; i++)
		{
			if(i == str.length -1)
			{
				//remove leading spaces
				str[i] = str[i].trim();
				continue; 
			} 
			//if last char is not space
			if(str[i].length() > 0 && str[i+1].length() > 0 && str[i].charAt(str[i].length() -1) != ' ' && str[i+1].charAt(0) != ' ')
			{
				//look for first space in next line
				int index = str[i+1].indexOf(' ');
				if(index >= 0 )
				{
					str[i] = str[i] + str[i+1].substring(0, index);
					str[i+1] = str[i+1].substring(index + 1);				
				}
				//remove leading spaces
				str[i] = str[i].trim();
			}
			else
				//remove leading spaces
				str[i] = str[i].trim();
		}
		return str;		
	}
}

/**
 * check if string is empty or not
 * 
 * Creation date: (12/12/2001 3:24:48 PM)
 * @return java.lang.String
 * @param str java.lang.String
 */

 public static boolean isEmpty(String value) {
	if (value == null || value.trim().length() == 0)
		return true;
	else
		return false;
 }

 /**
  * Replace " with \" 
  * Creation date: (8/11/2003 2:36:47 PM)
  * @return java.lang.String
  * @param str java.lang.String
  */
 public static String escapeDoubleQuote(String s) {
	 if (s == null) return "";
	 StringBuffer s1 = new StringBuffer(s);
	 
	 for (int j = 0;;) {
		 try {
			 if (s1.charAt(j) == '\\') {
				 s1.deleteCharAt(j);
				 s1.insert(j, "\\\\");
			 j = j + 2;
			 }
			 j++;
		 }catch (IndexOutOfBoundsException e) {
			 break;
		 }
	 } // end of for
	 
	 for (int j = 0;;) {
		 try {
 			if (s1.charAt(j) == '"') {
				 s1.deleteCharAt(j);
				 s1.insert(j, "\\\"");
				 j++;
			}else if (s1.charAt(j) < 32){
				 s1.deleteCharAt(j);
				 s1.insert(j, " ");
			}
			j++;
		 }catch (IndexOutOfBoundsException e) {
			 break;
		 }
	 } // end of for
	 return s1.toString();
 }

 /**
  * remove all chars other than letter and digits
  * Creation date: (12/6/2001 1:22:58 PM)
  * @return java.lang.String
  * @param  java.lang.String
  */
 public static String removeSpecialChar(String str)
 {
	 /* "." is not considered a special char here */	
	 if(str == null || str.trim().length() ==0)
	 	return "";
	 else
	 	str = str.trim();
	 	
	 StringBuffer buff = new StringBuffer("");
	 
	 int len = str.length();
	 
	 char myChar = ' ';
	 
	 for(int i =0; i< len; i++)
	 {
	 	myChar = str.charAt(i);
	 	
		if( (myChar >= '0' && myChar <= '9') || (myChar >= 'a' && myChar <= 'z') || (myChar >= 'A' && myChar <= 'Z') || myChar == '.')
			buff.append(myChar);
		else
			continue;
	 }
	 
	 return buff.toString();

 }

 /**
  * remove all chars other than '.' and digits
  * Creation date: (12/6/2001 1:22:58 PM)
  * @return java.lang.String
  * @param  java.lang.String
  */
 public static String removeNonDigitsChar(String str)
 {
	 /* "." is considered a valid char here */	
	 if(str == null || str.trim().length() ==0)
	 	return "";
	 else
	 	str = str.trim();
	 	
	 StringBuffer buff = new StringBuffer("");
	 
	 int len = str.length();
	 
	 char myChar = ' ';
	 
	 for(int i =0; i< len; i++)
	 {
	 	myChar = str.charAt(i);
	 	
		if( (myChar >= '0' && myChar <= '9') || myChar == '.')
			buff.append(myChar);
		else
			continue;
	 }
	 
	 return buff.toString();

 }

 public static boolean isImageExtention(String str)
 {
 	if(str.equalsIgnoreCase(".jpeg") || str.equalsIgnoreCase(".jpg") || str.equalsIgnoreCase(".gif") || 
 	   str.equalsIgnoreCase(".bmp") || str.equalsIgnoreCase(".tiff"))
 	{
 		return true;	
 	}	
 	else
 	{
 		return false;	
 	}
 }
 
 public static boolean isDocumentType(String str)
 {
 	if(str.equalsIgnoreCase(".pdf") || str.equalsIgnoreCase(".txt") || str.equalsIgnoreCase(".doc") || 
 	   str.equalsIgnoreCase(".xls") || str.equalsIgnoreCase(".rtf"))
 	{
 		return true;	
 	}	
 	else
 	{
 		return false;	
 	}
 }

 /**
  * return day of week in char
  * Creation date: (12/6/2001 1:22:58 PM)
  * @return java.lang.String
  * @param  int
  */
 public static String dayOfWeekName(int num)
 {
	 //if(num > 7 || num < 0)
	 //	return "";
	 
	 String name = "";
	 	
	 switch (num) {
		case 1 : name = "Monday";		break;
		case 2 : name = "Tuesday";		break;
		case 3 : name = "Wednesday";	break;
		case 4 : name = "Thursday";		break;
		case 5 : name = "Friday";		break;
		case 6 : name = "Saturday";		break;
		case 7 : name = "Sunday";		break;
		default : name = "All";		break;
	 }
	 return name;		
 }

 public static String formatQty(java.math.BigDecimal num)
 {
	 String result = "";
	
	 java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("###.######");
	 //myFormatter.setMinimumFractionDigits(2);
	 result = myFormatter.format(num);;
	
	 if(result.equals("-0.00"))
		 return "0.00";
	 else
		 return result;	
			
 }

 public static String displayQtyWithBlank(java.math.BigDecimal num)
 {	//display qty, if zero, return ""
	 String result = "";
	 if(num.doubleValue()== 0d)
		 return result;
	 
	 java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("###.######");
	 //myFormatter.setMinimumFractionDigits(2);
	 result = myFormatter.format(num);;
	
	 if(result.equals("-0.00"))
		 return "0.00";
	 else
		 return result;	
			
 }

 public static String formatExchangeRate(java.math.BigDecimal num)
 {
	 String result = "";
	
	 java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("###.###############");
	 //myFormatter.setMinimumFractionDigits(2);
	 result = myFormatter.format(num);;
	
	 if(result.equals("-0.00"))
		 return "0.00";
	 else
		 return result;	
			
 }

 public static String displayPersonName(String fname, String mname, String lname)
 {
 	fname = fname==null?"":fname.trim();
	mname = mname==null?"":mname.trim();
	lname = lname==null?"":lname.trim();
 	
	if(mname.length() > 0)
		return fname + " " + mname + " " + lname; 
	else
		return fname + " " + lname; 
 }
 
 //Used to pad ids with leading zeros so that they are 13 characters in length and can be made into barcode labels
 public static String formatBarcode(String barcode)
 {
 	String paddedBarcode = barcode;
 	for(int i=0; i<(13-barcode.length()); i++)		
 	{
 		paddedBarcode = "0" + paddedBarcode; 
 	}
 	return paddedBarcode;

 }

 public static String fixSqlStr(String sqlStr)
 {
	 //to fix websphere 5.1.2 bug
	StringBuffer buff = new StringBuffer();
	int index =0;
	index = sqlStr.indexOf("'',");
	//System.out.println(index);
	while(index>=0)
	{
		buff.append(sqlStr.substring(0, index));
		buff.append("' ',");
		sqlStr = sqlStr.substring(index + 3);
		//System.out.println(sqlStr);
		index = sqlStr.indexOf("'',");
		//System.out.println(index);
	}
	buff.append(sqlStr);
	//System.out.println(buff.toString());
	return buff.toString();
 }
 /*
  * to display '\n' properly in HTML textArea
  */
 public static String displayTextArea(String str)
 {
	 if(str == null)
		 return "";
	 else
		 return str.replaceAll("\n","\n");
	 
 }
 /*
  * to display '\n' properly in HTML
  */
 public static String displayTextAreaHTML(String str)
 {
	 if(str == null)
		 return "";
	 else
		 return str.replaceAll("\n","<BR>");
	 
 }
 /*
  * get credit card # from a swipt card string
  * input Format = %BCARDNUMBER^LASTNAME/FIRSTNAME ^YYMM#####################?;CARDNUMBER=YYMM###########?
  */
 public static String parseCreditCardNumber(String str)
 {
	 int index =0; 
	 if(str == null)
		 return "";
	 else
	 {
		 index = str.indexOf('^');
		 if(index <=0)
			 return "";
		 else
			 return str.substring(2, index);
	 }
	 
 }

 public static String removeExcelStrQuote(String str)
 {
	 /* remove Excel added unwanted double quote
	  * remove " at both end, change "" in the middle to "
	  */
	str = str.replaceAll("\"\"", "\"");
	//System.out.println(str);
	int len = str.length();
	
	if(len > 0)
		if(str.substring(0, 1).equals("\""))
		{	
			str = str.substring(1);
			//System.out.println(str);
			len = str.length();
		}
	if(len > 0)
		if(str.substring(len-1).equals("\""))
		{	
			str=str.substring(0,len-1);
			//System.out.println(str);
		}
	return str;
 }

 public static boolean isValidDecimal(String value, boolean ignoreBlank) 
 {
	BigDecimal myDec = null;

	if (value.trim().length() == 0 && ignoreBlank == true)
		return true;
	else
	{
		try
		{
			myDec = new BigDecimal(value);
			
		}catch (NumberFormatException e)
		{
			return false;			
		}
		return true;
	}
 }

 public static String calculatePercentND(BigDecimal dec1, BigDecimal dec2)
 {
	 
 	if(dec1 == null || dec2 == null || (dec1.doubleValue() ==0d && dec2.doubleValue() ==0d))
 		return "0%";
 	if(dec2.doubleValue() == 0d)
 		if (dec1.signum() > 0)
 			return "100%";
 		else 
 			return "-100%";
 	
 	double x = (dec1.doubleValue() * 100d / dec2.doubleValue());
 	float  y = Float.valueOf(Double.toString(x)).floatValue();
 	if(y > -1 && y < 1)
 	  return Format.formatDecimal("" + dec1.doubleValue() * 100d / dec2.doubleValue()) + "%";	
	
	int percent = Math.round(y);
 	return ("" + percent + "%");
 }

 public static double calculatePercentInt(BigDecimal dec1, BigDecimal dec2)
 {
	 
 	if(dec1 == null || dec2 == null || (dec1.doubleValue() ==0d && dec2.doubleValue() ==0d))
 		return 0;
 	if(dec2.doubleValue() == 0d)
 		if (dec1.signum() > 0)
 			return 100;
 		else 
 			return 100;
 	
 	double x = (dec1.doubleValue() * 100d / dec2.doubleValue());
 	float  y = Float.valueOf(Double.toString(x)).floatValue();
 	if(y > -1 && y < 1)
 	 	  return (dec1.doubleValue() * 100d / dec2.doubleValue());	
	int percent = Math.round(y);
 	return (percent);
 }

 public static String isAllNumber(String str)
 {
	 /* "." is not considered a special char here */	
	 if(str == null || str.trim().length() ==0)
	 	return "N";
	 	
	 int len = str.length();
	 char myChar = ' ';
	 String isAllNum = "Y";
		 
	 for(int i =0; i< len; i++)
	 {
	 	myChar = str.charAt(i);
	 	
		if( myChar < '0' || myChar > '9')
		{
			isAllNum = "N";
			break;
		}
	 }
	 
	 return isAllNum;
 }

}
