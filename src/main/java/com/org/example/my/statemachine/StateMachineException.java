package com.org.example.my.statemachine;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/9/12 5:25 下午
 * @description：异常
 */
public class StateMachineException extends RuntimeException{
    public StateMachineException(String message){
        super(message);
    }
}
