package com.kenton.elemei.common;

/**
 * @author: Kenton
 * @description 自定义业务异常
 * @date: 2022/9/4 7:00
 */
public class CustomException extends RuntimeException {

    public CustomException(String message){
        super(message);
    }
}
