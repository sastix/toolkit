package com.sastix.toolkit.restclient.exception;

import org.springframework.web.client.RestClientException;

public class ToolkitCommonException extends RestClientException {
    private static final long serialVersionUID = -4448435676701823150L;

    /**
     * Constructor that allows a specific error message to be specified.
     *
     * @param message detail message.
     */
    public ToolkitCommonException(String message) {
        super(message);
    }

    /**
     * Creates a {@code ToolkitCommonException} with the specified
     * detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public ToolkitCommonException(String message, Throwable cause) {
        super(message, cause);
    }
}
