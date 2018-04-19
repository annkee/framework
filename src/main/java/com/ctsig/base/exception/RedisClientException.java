package com.ctsig.base.exception;

/**
 * @author wangan
 * @date 2017/05/01
 */
public class RedisClientException extends RuntimeException {

    private static final long serialVersionUID = -1209625392805014995L;

    public RedisClientException(String msg) {
        super(msg);
    }

    public RedisClientException(String msg, Throwable t) {
        super(msg, t);
    }

    public RedisClientException(Throwable cause) {
        super(cause);
    }

}
