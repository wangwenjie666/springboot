package org.springboot.demo.exception;

/**
 * base exception
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
public abstract class BaseException extends RuntimeException {

    private Object errData;

    public Object getErrData() {
        return errData;
    }

    public BaseException setErrData(Object errData) {
        this.errData = errData;
        return this;
    }

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
