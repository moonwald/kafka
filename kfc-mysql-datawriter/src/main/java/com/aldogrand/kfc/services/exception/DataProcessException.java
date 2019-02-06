package com.aldogrand.kfc.services.exception;

import com.aldogrand.kfc.exception.KFCException;

/**
 * 
 * <p>
 * <b>Title</b> DataProcessException
 * </p>
 * <p>
 * <b>Description</b>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * 
 * @author aldogrand
 *
 */
public class DataProcessException extends KFCException {

	public DataProcessException(String message, Throwable cause) {
		super(message);
	}

	public DataProcessException(String message) {
		super(message);
	}
}
