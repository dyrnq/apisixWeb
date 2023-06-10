package com.dyrnq.apisix;

public class ApisixSDKException extends Exception {
    private static final long serialVersionUID = 1L;

    private final String errorCode;

    public ApisixSDKException(String message) {
        this(message, "");
    }


    public ApisixSDKException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String toString() {
        return "[ApisixSDKException]"
                + "message:"
                + this.getMessage()
                + " errorCode:"
                + this.getErrorCode();
    }
}