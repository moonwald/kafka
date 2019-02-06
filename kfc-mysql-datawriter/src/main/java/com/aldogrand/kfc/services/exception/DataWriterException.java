package com.aldogrand.kfc.services.exception;

import com.aldogrand.kfc.exception.KFCException;

/**
 * 
 * <p>
 * <b>Title</b> DataWriterException
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
public class DataWriterException extends KFCException {

	public DataWriterException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataWriterException(String message) {
		super(message);
	}
}
