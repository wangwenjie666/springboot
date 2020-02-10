package org.springboot.demo.exception;

/**
 * service exception
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
public class ServiceException extends BaseException {

    private static final String defaultMsg = "service 异常";

    public ServiceException() {
        super(defaultMsg);
    }

    public ServiceException(String message){
        super(message);
    }
}
