package it.smartchain.primoesempio.exceptions;

public class CartellaClinicaException extends Exception {

    // No-argument constructor
    public CartellaClinicaException() {
        super();

    }

    // Constructor that accepts a message
    public CartellaClinicaException(String message) {
        super(message);
    }

    // Constructor that accepts a message and a cause
    public CartellaClinicaException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts a cause
    public CartellaClinicaException(Throwable cause) {
        super(cause);
    }

    // Constructor for exception chaining and control over suppression and writable stack trace
    protected CartellaClinicaException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
