package com.sastix.toolkit.demoapp.common.exception;


import com.sastix.toolkit.restclient.exception.ToolkitBusinessException;

public class DemoAppException extends ToolkitBusinessException {

    public DemoAppException(String message) {
        super(message);
    }

    public DemoAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
