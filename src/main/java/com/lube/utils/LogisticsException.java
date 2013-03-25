package com.lube.utils;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-2-1
 * Time: 上午10:32
 * To change this template use File | Settings | File Templates.
 */
public class LogisticsException extends Exception {
    public LogisticsException(){}

    public LogisticsException(String message){
        super(message);
    }

    public LogisticsException(String message, Throwable throwable){
        super(message, throwable);
    }

    public LogisticsException(Throwable throwable){
        super(throwable);
    }
}
