/*
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * ---------------------------
 * NoDataException.java
 * ---------------------------
 * (C) Copyright 2002, by Richard Atkinson.
 *
 * Original Author:  Richard Atkinson;
 */
package com.cap.util;

public class NoDataException extends Exception {

	public NoDataException(String s)
    {
		super(s);
    }
    public NoDataException() {
		super();
    }
}