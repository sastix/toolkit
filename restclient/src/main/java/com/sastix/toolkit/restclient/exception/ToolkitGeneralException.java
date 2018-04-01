package com.sastix.toolkit.restclient.exception;

/**
 * Created by iskitsas on 4/4/17.
 */
public class ToolkitGeneralException extends ToolkitBusinessException {
    public ToolkitGeneralException(String message) {
        super(message);
    }

    public ToolkitGeneralException(String message, Throwable cause) {
        super(message, cause);
    }
}
