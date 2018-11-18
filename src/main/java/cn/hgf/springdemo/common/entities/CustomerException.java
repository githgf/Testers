package cn.hgf.springdemo.common.entities;

/**
 * @Author: FanYing
 * @Date: 2018-05-19 17:42
 * @Desciption:
 */
public class CustomerException extends RuntimeException {

    public CustomerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerException() {
        super("cus");
    }
}
