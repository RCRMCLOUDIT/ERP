package com.cap.util;

import java.util.ResourceBundle;

/**
 * @author carlos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NumberSpeller 
{
	private static final String keyWordsi 	   = "P11040000";
	private static final String words1To10i    = "P11040001";
	private static final String words10To20i   = "P11040002";
	private static final String words10To90i   = "P11040003";
	private static final String words100To900i = "P11040004";
	private final static String commaGap = "3333";
	private final static int wordLength = 20;
	

	
	public static String figureToWords(double amount, boolean isCurrency, ResourceBundle rb)
	{
		String keyWords 		= rb.getString(keyWordsi);
		String words1To10 		= rb.getString(words1To10i);
		String words10To20		= rb.getString(words10To20i);
		String words10To90		= rb.getString(words10To90i);
		String words100To900	= rb.getString(words100To900i);
		String pluralMod 		= rb.getString("Pluralmod");
		String amtJoiner 		= rb.getString("Amtjoiner");
		String hundredMod 		= rb.getString("Hundredmd");
		String cntJoiner 		= rb.getString("Cntjoiner");
	
		String result = "";
		int cents = 0;
		long amt = (long)amount;

		if (amount != 0)
		{
			result = getWordsStr((long)amount, 1, keyWords, words1To10, words10To20, words10To90, words100To900, pluralMod, amtJoiner, hundredMod);
			cents = (int)Math.round((amount - amt) * 100);

			if (cents != 0 )
			{
				if (result.length() != 0)
					result = result + " " + cntJoiner + " " + (int)cents + "/100";
				else
					result = (int)cents + "/100";
			}
			if (cents == 0 )
			{
				if (result.length() != 0)
					result = result + " " + cntJoiner + " " + "00/100";
				else
					result = (int)cents + "00/100";
			}
		}
		else
		{
			result = rb.getString("P11040006");
		}

		if (isCurrency == true)
		{
	//		result = result + " Dollars";
		}

		return result;
	}
	/**
	 * @param amount
	 * @param index
	 * @return
	 */
	/**
	 * @param amount
	 * @param index
	 * @param keyWords
	 * @param words1To10
	 * @param words10To20
	 * @param words10To90
	 * @param words100To900
	 * @param pluralMod
	 * @param amtJoiner
	 * @param hundredMod
	 * @return
	 */
	private static String getWordsStr(long amount, int index, String keyWords, String words1To10, String words10To20, String words10To90, String words100To900, String pluralMod, String amtJoiner, String hundredMod)
	{
		String leftStr = "";
		String rightStr = "";
		int power = 0;
		long divisor = 0;
		long remainder;
		int ind = 0;
		String moreOne = "1";

		if (amount > 0)
		{
			power = (new Integer(commaGap.substring(index-1, index))).intValue();
			divisor = (long)Math.pow(10, power);
			//System.out.println("power=" + power);
			//System.out.println("divisor=" + divisor);

			remainder = amount % divisor;
			amount = amount / divisor;
			//System.out.println("remainder=" + remainder);
			//System.out.println("amount=" + amount);
			
			if (remainder < 1000)
			{
				if (remainder == 0)	
					rightStr = "";
				else if (remainder <= 10)
				{
					ind = (int)(remainder * wordLength - (wordLength - 1) - 1);
					rightStr = words1To10.substring(ind, (ind + wordLength) < words1To10.length() ? (ind + wordLength) : words1To10.length()).trim();
				}
				else if (remainder <= 20)
				{
					ind = (int)((remainder - 10) * wordLength - (wordLength - 1) - 1);
					rightStr = words10To20.substring(ind, (ind + wordLength) < words10To20.length() ? (ind + wordLength) : words10To20.length()).trim();
				}
				else if (remainder < 100)
				{
					ind = (int)(remainder/10 * wordLength - (wordLength - 1) - 1);
					rightStr = words10To90.substring(ind, (ind + wordLength) < words10To90.length() ? (ind + wordLength) : words10To90.length()).trim();

					remainder = remainder % 10;			
					
					if (remainder != 0)
					{
						ind = (int)(remainder * wordLength - (wordLength - 1) - 1);
						rightStr = rightStr + " " + amtJoiner+ " " + words1To10.substring(ind, (ind + wordLength) < words1To10.length() ? (ind + wordLength) : words1To10.length()).trim();
					}
				}
				else 
				{
					ind = (int)(remainder/100 * wordLength - (wordLength - 1) - 1);
					//System.out.println("ind=" + ind);
					rightStr = words100To900.substring(ind, (ind + wordLength) < words100To900.length() ? (ind + wordLength) : words100To900.length()).trim();
					/*
					System.out.println("rightStr=" + rightStr);

					System.out.println("amount=" + rightStr);
					System.out.println("index=" + rightStr);
					System.out.println("keyWords=" + keyWords);
					System.out.println("words1To10=" + words1To10);
					System.out.println("words10To20=" + words10To20);
					System.out.println("words10To90=" + words10To90);
					System.out.println("words100To900=" + words100To900);
					System.out.println("pluralMod=" + pluralMod);
					System.out.println("amtJoiner=" + amtJoiner);
					System.out.println("hundredMod=" + hundredMod);
					*/
					rightStr = getStr((long)amount, (int)index, (int)ind, (long)remainder, 
									  (String)rightStr, keyWords, words1To10, words10To20, 
									  words10To90, words100To900, pluralMod, amtJoiner, hundredMod);
					//System.out.println("rightStr=" + rightStr);
				
				}
			}
			else
			{
				rightStr = getWordsStr(remainder, 1, keyWords, words1To10, words10To20, words10To90, words100To900, pluralMod, amtJoiner, hundredMod);
			}  
			leftStr = getWordsStr(amount, index < commaGap.length() ? index + 1 : 1, keyWords, words1To10, words10To20, words10To90, words100To900, pluralMod, amtJoiner, hundredMod);  
			moreOne = getMoreone(amount, index < commaGap.length() ? index + 1 : 1);

			if (index < commaGap.length())
			{
				power = (new Integer(commaGap.substring(index, index + 1))).intValue();
				if (amount % Math.pow(10, power) > 0)
				{
					ind = index * wordLength - (wordLength - 1) - 1;
					if (leftStr.length() != 0)
						leftStr = leftStr + " " + keyWords.substring(ind, (ind + wordLength) < words1To10.length() ? (ind + wordLength) : keyWords.length()).trim();
						if (moreOne == "1" && ind > 18)
						{
							leftStr = leftStr + pluralMod;
						}
					else
						leftStr = leftStr + "";
				}
			}
			else
			{
				ind = index * wordLength - (wordLength - 1) - 1;
				if (leftStr.length() != 0)
					leftStr = leftStr + " " + keyWords.substring(ind, (ind + wordLength) < words1To10.length() ? (ind + wordLength) : keyWords.length()).trim();
					if (moreOne == "1" && ind > 18)
					{
						leftStr = leftStr + pluralMod;
					}
				else
					leftStr = leftStr + "";
			}
			
			if (leftStr.length() != 0)
				return leftStr.trim() + " " + rightStr;
			else
				return leftStr.trim() + rightStr;
		}
		
		return  "";
	}
	
	private static String getStr(long amount, int index, int ind, long remainder, String rightStr, String keyWords, String words1To10, String words10To20, String words10To90, String words100To900, String pluralMod, String amtJoiner, String hundredMod)
	{
		boolean isSpelled = false;

		{
			if (ind == 0 & remainder > 100)
			{	rightStr = rightStr + hundredMod;	
			}

			//System.out.println("remainder 01=" + remainder);
			if (remainder > 99)
			{	
				remainder = remainder % 100;
				//System.out.println("remainder 02=" + remainder);
				
				if (remainder >= 20)
				{
					ind = (int)(remainder/10 * wordLength - (wordLength - 1) - 1);
					rightStr = rightStr + " " + words10To90.substring(ind, (ind + wordLength) < words10To90.length() ? (ind + wordLength) : words10To90.length()).trim();
				}
				else if (remainder >= 11)
				{
					ind = (int)((remainder - 10) * wordLength - (wordLength - 1) - 1);
					rightStr = rightStr + " " + words10To20.substring(ind, (ind + wordLength) < words10To20.length() ? (ind + wordLength) : words10To20.length()).trim();
					isSpelled = true;
				}
				else if (remainder == 10)
				{
					ind = 9;
					if (amtJoiner.equals(""))
						rightStr = rightStr + " " + words1To10.substring(ind*20, words1To10.length()-1).trim();
					else
						rightStr = rightStr + " " + amtJoiner + " " + words1To10.substring(ind*20, words1To10.length()-1).trim();
					//System.out.println("rightStr==" + rightStr);
					isSpelled = true;
				}
					
			}			
			remainder = remainder % 10;
			if ((remainder != 0 && !isSpelled))
			{
				//System.out.println("ind ==" + ind);
				ind = (int)(remainder * wordLength - (wordLength - 1) - 1);
				//System.out.println("ind ==" + ind);
				if (amtJoiner.equals(""))
					rightStr = rightStr + " " + words1To10.substring(ind, (ind + wordLength) < words1To10.length() ? (ind + wordLength) : words1To10.length()).trim();
				else
					rightStr = rightStr + " " + amtJoiner + " " + words1To10.substring(ind, (ind + wordLength) < words1To10.length() ? (ind + wordLength) : words1To10.length()).trim();
			}
		}				
		return  rightStr;
	}
	private static String getMoreone(long amount, int index)
	{
		String moreOne = "1";
		int power = 0;
		long divisor = 0;
		long remainder;
		
		if (amount > 0)
		{
			power = (new Integer(commaGap.substring(index-1, index))).intValue();
			divisor = (long)Math.pow(10, power);
			
			remainder = amount % divisor;
			amount = amount / divisor;
			
			if (remainder ==1)
			{
				moreOne = "0";
			}
		}		
		return  moreOne;
	}
}
