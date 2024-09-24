package it.smartchain.primoesempio.exceptions;

public class NoGroupException extends Exception {

    // No-argument constructor
    public NoGroupException () {
        super();

    }

    // Constructor that accepts a message
    public NoGroupException(String message) {
        super(message);
    }

    // Constructor that accepts a message and a cause
    public NoGroupException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts a cause
    public NoGroupException(Throwable cause) {
        super(cause);
    }

    // Constructor for exception chaining and control over suppression and writable stack trace
    protected NoGroupException(String message, Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
