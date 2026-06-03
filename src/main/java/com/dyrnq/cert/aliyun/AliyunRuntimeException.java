package com.dyrnq.cert.aliyun;

public class AliyunRuntimeException extends RuntimeException {
    public AliyunRuntimeException(String message) {
        super(message);
    }

    public AliyunRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AliyunRuntimeException(Throwable cause) {
        super(cause);
    }
}
