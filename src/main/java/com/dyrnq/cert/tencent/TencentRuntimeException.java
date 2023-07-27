package com.dyrnq.cert.tencent;

public class TencentRuntimeException extends RuntimeException {
    public TencentRuntimeException(String message) {
        super(message);
    }

    public TencentRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }


    public TencentRuntimeException(Throwable cause) {
        super(cause);
    }
}
