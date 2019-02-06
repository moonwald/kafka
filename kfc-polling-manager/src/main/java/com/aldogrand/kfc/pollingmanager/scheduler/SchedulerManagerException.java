package com.aldogrand.kfc.pollingmanager.scheduler;

public class SchedulerManagerException extends Exception {

    /**
     * UID.
     */
    private static final long serialVersionUID = 637241192697297568L;

    public SchedulerManagerException() {
        super();
    }

    public SchedulerManagerException(String message) {
        super(message);
    }

    public SchedulerManagerException(Throwable cause) {
        super(cause);
    }

    public SchedulerManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    protected SchedulerManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
