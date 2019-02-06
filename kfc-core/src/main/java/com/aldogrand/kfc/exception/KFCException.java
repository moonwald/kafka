package com.aldogrand.kfc.exception;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * <p>
 * <b>Title</b> KFCException
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
 * @author aldogrand
 *
 */
public class KFCException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1930810502051318482L;
	private String[] messages;

	/**
	 * @param message
	 */
	public KFCException(String message) {
		super(message);
		if (message != null) {
			messages = new String[] { message };
		}
	}

	/**
	 * 
	 * @param messages
	 */
	public KFCException(String[] messages) {
		super(((messages != null) && (messages.length > 0)) ? messages[0]
				: null);
		this.messages = messages;
	}

	/**
	 * 
	 * @param messages
	 */
	public KFCException(List<String> messages) {
		super(((messages != null) && (!messages.isEmpty())) ? messages.get(0)
				: null);
		this.messages = messages.toArray(new String[messages.size()]);
	}

	/**
	 * @param cause
	 */
	public KFCException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public KFCException(String message, Throwable cause) {
		super(message, cause);
		if (message != null) {
			messages = new String[] { message };
		}
	}

	/**
	 * @param message
	 * @param cause
	 */
	public KFCException(String[] messages, Throwable cause) {
		super(((messages != null) && (messages.length > 0)) ? messages[0]
				: null, cause);
		this.messages = messages;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public KFCException(List<String> messages, Throwable cause) {
		super(((messages != null) && (!messages.isEmpty())) ? messages.get(0)
				: null, cause);
		this.messages = messages.toArray(new String[messages.size()]);
	}

	/**
	 * @return the messages
	 */
	public String[] getMessages() {
		if (messages == null) {
			return new String[] { getMessage() };
		}
		return messages;
	}

	/**
	 * @return the messages
	 */
	public List<String> getMessageList() {
		String[] messages = getMessages();
		return Arrays.asList(messages);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (String message : getMessages()) {
			if (builder.length() > 0) {
				builder.append("\n");
			}
			builder.append(message);
		}
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(messages);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		KFCException other = (KFCException) obj;
		if (!Arrays.equals(messages, other.messages)) {
			return false;
		}
		return true;
	}
}
