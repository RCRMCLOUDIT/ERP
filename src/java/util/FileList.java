package com.cap.util;

import java.io.File;
import java.lang.reflect.Array;

import java.util.*;

public class FileList
{

    public FileList()
    {
    }

    private static String extractFileName(String s)
    {
        if(s.indexOf('.') > 0)
            return s.substring(0, s.indexOf('.'));
        else
            return s;
    }

    public static String[] getFileList(String s)
    {
        File file = new File(s);
        if(file.isDirectory())
        {
            String as[] = file.list();
            String as1[] = new String[Array.getLength(as)];
            for(int i = 0; i < Array.getLength(as); i++)
                try
                {
                    String s2 = as[i];
                    String s1 = s2;
                    as1[i] = s1;
                }
                catch(Exception exception)
                {
                    System.out.println(exception.toString());
                }

//sort array
  Locale loc = Locale.ENGLISH;
  sortArray(java.text.Collator.getInstance(loc), as1);
            return as1;
        } else
        {
            return null;
        }
    }

    public static String[] getFileNames(String s)
    {
        File file = new File(s);
        if(file.isDirectory())
        {
            String as[] = file.list();
            String as1[] = new String[Array.getLength(as)];
            for(int i = 0; i < Array.getLength(as); i++)
                try
                {
                    String s2 = as[i];
                    String s1 = extractFileName(s2);
                    as1[i] = s1;
                }
                catch(Exception exception)
                {
                    System.out.println(exception.toString());
                }
			Locale loc = Locale.ENGLISH;
			sortArray(java.text.Collator.getInstance(loc), as1);
            return as1;
        } else
        {
            return null;
        }
    }

    public static String getFileExtension(String url, String name)
    {
		System.out.println("Parameter name = " + name);
		File file = new File(url);
		if(file.isDirectory())
		{
		    String filenames[];
		    String temp1;
		    filenames = file.list();
		    for(int i = 0; i < Array.getLength(filenames); i++)
		        try
		        {
		            temp1 = extractFileName(filenames[i]);
		            System.out.println("File " + i +": " + temp1);
		            if(temp1.equalsIgnoreCase(name))
		            {
						System.out.println("Extension found!");
						return filenames[i];
					}

		        }
		        catch(Exception exception)
		        {
		            System.out.println(exception.toString());
		        }
			System.out.println("Couldn't find extension");
		    return "ERROR";
		}
		else
		{
			System.out.println("URL specified is not a directory");
		    return "ERROR";
        }
	}
	
	public static void sortArray(java.text.Collator collator, String[] strArray) {
	  String tmp;
	  if (strArray.length == 1) return;
	  for (int i = 0; i < strArray.length; i++) {
		for (int j = i + 1; j < strArray.length; j++) {
		  if( collator.compare(strArray[i], strArray[j] ) > 0 ) {
			tmp = strArray[i];
			strArray[i] = strArray[j];
			strArray[j] = tmp;
			}
		  }
		}
	  }

}