package com.aldogrand.kfc.pollingmanager.service;

/**
 * 
 * <p>
 * <b>Title</b> RuleException.
 * </p>
 * <p>
 * <b>Description</b> An exception while handling a Rule.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * @author Aldo Grand
 *
 */
public class RuleException extends Exception {

    private static final long serialVersionUID = -2141904965009569442L;

    public RuleException() {
        super();
    }

    public RuleException(String message) {
        super(message);
    }

    public RuleException(Throwable cause) {
        super(cause);
    }

    public RuleException(String message, Throwable cause) {
        super(message, cause);
    }

    protected RuleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
