package com.asseco.cas.parameters.exceptions.checked;

public class ParameterListNotFoundException extends Exception{

    private static final long serialVersionUID = -3267767723319370494L;

    public ParameterListNotFoundException(String message) {
        super(message);
    }

    public ParameterListNotFoundException(String message, Throwable exception) {
        super(message, exception);
    }

}
