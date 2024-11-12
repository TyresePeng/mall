
package com.macro.mall.common.exception;

import lombok.Getter;

import java.util.Formatter;

/**
 * @author peng.guo
 */
@Getter
public class BusinessException extends RuntimeException {
    private Object data;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Object data) {
        super(message);
        this.data = data;
    }

    public BusinessException(String message, Object... args) {
        super(message);
        this.data = (new Formatter()).format(message, args).toString();
    }


}
