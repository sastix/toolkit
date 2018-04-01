package com.sastix.toolkit.restclient.exception;

public class ToolkitBusinessException extends ToolkitCommonException {

    private static final long serialVersionUID = -3938693970637933884L;

    /**
     * Constructor that allows a specific error message to be specified.
     *
     * @param message detail message.
     */
    public ToolkitBusinessException(String message) {
        super(message);
    }

    /**
     * Creates a {@code ToolkitBusinessException} with the specified
     * detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public ToolkitBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
